function refresh(view) {

    $.getJSON('/view/' + view + '/feed', function(statuses) {

        var jobs = $('<ol/>', { id: 'jobs' });

        statuses = statuses.sort(statusComparator);

        $.each(statuses, function(key, status) {
            var job = buildStatusMarkup(status);
            jobs.append(job);
        });

        $('#jobs').remove();
        $('body').append(jobs);

        var color = getScreenColor(statuses);
        $('body').removeClass('red');
        $('body').removeClass('yellow');
        $('body').addClass(color);

        var invokation = "refresh('" + view + "')";
        setTimeout(invokation, 1000);
    });
}

function getScreenColor(statuses) {

    var color = 'green';

    $.each(statuses, function(key, status) {

        if (status.state == 'failure') {
            color = 'red';
        } else {
            if (color == 'green' && status.state != 'success') {
                color = 'yellow';
            }
        }
    });

    return color;
}

function buildStatusMarkup(status) {

    var job = $('<li/>', { id: status.name, 'class': status.state });

    var name = buildNameMarkup(status);
    job.append(name);

    var timestamp = buildTimestampMarkup(status);
    job.append(timestamp);

    var progress = buildProgressMarkup(status);
    timestamp.append(progress);

    var duration = buildDurationMarkup(status);
    job.append(duration);

    var causes = buildCausesMarkup(status);
    job.append(causes);

    if (status.changes.length > 0) {
        var changes = buildChangesMarkup(status);
        job.append(changes);
    }

    return job;
}

function buildNameMarkup(status) {
    return $('<h2>' + status.name + '</h2>');
}

function buildTimestampMarkup(status) {

    var timestamp = $('<div/>', { 'class': 'timestamp' });

    if (status.state == 'building') {
        timestamp.append('&nbsp;');
        return timestamp;
    }

    if (status.timestamp != 0) {
        var date = new Date(status.timestamp);
        timestamp.append(buildTimestampFormat(date));
    }

    return timestamp;
}

var daysOfWeek  = [ 'Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat' ];
var monthsOfYear = [ 'Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec' ];

function buildTimestampFormat(date) {

    var meredium = 'AM';
    var hours = date.getHours();
    if (hours > 12) {
        hours = hours - 12;
        meredium = 'PM';
    }

    var timestamp = hours + ':';
    var minutes = date.getMinutes();
    if (minutes < 10) {
        timestamp += '0';
    }
    timestamp += date.getMinutes() + ' ' + meredium;

    if (!isToday(date)) {
        timestamp = timestamp + ' ' + daysOfWeek[date.getDay()] + ', ' + date.getDate() + ' ' + monthsOfYear[date.getMonth()] + ' ' + date.getFullYear();
    }

    return timestamp;
}

function isToday(date) {

    var today = new Date();

    return today.getDate() == date.getDate() && today.getMonth() == date.getMonth() && today.getFullYear() == date.getFullYear();
}

function buildProgressMarkup(status) {

    var progress = $('<div/>', { 'class': 'progress' });
    var completed = $('<div/>', { 'class': 'completed' });

    var percentage = 100.0 / status.estimate * status.duration;
    if (status.estimate == -1 || percentage > 100.0) {
        percentage = 95.0;
    }

    completed.width(percentage + '%');
    progress.append(completed);

    return progress
}


function buildDurationMarkup(status) {

    var duration = $('<div/>', { 'class': 'duration' });
    if (status.duration != 0) {
        duration.append(makeDuration(status.duration));
    }

    return duration;
}

function buildCausesMarkup(status) {

    var causes = $('<ol/>', { 'class': 'causes' });
    $.each(status.causes, function(key, cause) {
        causes.append($('<li/>').append(cause));
    });

    return causes;
}

function buildChangesMarkup(status) {

    var changes = $('<ol/>', { 'class': 'changes' });
    $.each(status.changes, function(key, change) {
        changes.append($('<li/>').append(change));
    });

    return changes;
}

function statusComparator(s1, s2) {

    if (s1.state == 'building' && s2.state != 'building') { return -1; }
    if (s1.state != 'building' && s2.state == 'building') { return  1; }

    if (s1.state == 'failure'  && s2.state != 'failure')  { return -1; }
    if (s1.state != 'failure'  && s2.state == 'failure')  { return  1; }

    if (s1.state == 'aborted'  && s2.state != 'aborted')  { return -1; }
    if (s1.state != 'aborted'  && s2.state == 'aborted')  { return  1; }

    if (s1.state == 'unstable' && s2.state != 'unstable') { return -1; }
    if (s1.state != 'unstable' && s2.state == 'unstable') { return  1; }

    if (s1.state == 'disabled' && s2.state != 'disabled') { return -1; }
    if (s1.state != 'disabled' && s2.state == 'disabled') { return  1; }

    if (s1.state == 'notbuilt' && s2.state != 'notbuilt') { return -1; }
    if (s1.state != 'notbuilt' && s2.state == 'notbuilt') { return  1; }

    if (s1.state == 'unknown'  && s2.state != 'unknown')  { return -1; }
    if (s1.state != 'unknown'  && s2.state == 'unknown')  { return  1; }

    return s2.timestamp - s1.timestamp;
}

function makeDuration(milliseconds) {

    var seconds = Math.floor(milliseconds /    1000) % 60;
    var minutes = Math.floor(milliseconds /   60000) % 60;
    var hours   = Math.floor(milliseconds / 3600000);

    if (milliseconds > 0 && seconds == 0) {
        seconds = 1;
    }

    var duration = "";
    if (hours != 0) {
        duration += hours + ":";
        if (minutes < 10) {
            duration += '0';
        }
    }

    duration += minutes + ":";

    if (seconds < 10) {
        duration += '0';
    }
    duration += seconds;

    return duration;
}