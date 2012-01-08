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

        var invokation = "refresh('" + view + "')";
        setTimeout(invokation, 2000);
    });
}

function buildStatusMarkup(status) {

    var job = $('<li/>', { id: status.name, 'class': status.state });

    var name = buildNameMarkup(status);
    job.append(name);

    var timestamp = buildTimestampMarkup(status);
    job.append(timestamp);

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
    if (status.timestamp != 0) {
        var date = new Date(status.timestamp);
        timestamp.append(date.toString());
    }

    return timestamp;
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