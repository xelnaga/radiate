function refresh(view) {

    $.getJSON('/view/' + view + '/feed', function(data) {

        var jobs = $('<ol/>', { id: 'jobs' });

        $.each(data, function(key, status) {

            var job = $('<li/>', { id: status.name, 'class': status.state });

            var heading = $('<h2>' + status.name + '</h2>');
            job.append(heading);


            var timestamp = $('<div/>', { 'class': 'timestamp' })
            if (status.timestamp != 0) {
                var date = new Date(status.timestamp);
                timestamp.append(date.toString());
            }
            job.append(timestamp);

            var duration = $('<div/>', { 'class': 'duration' })
            if (status.duration != 0) {
                duration.append(makeDuration(status.duration));
            }
            job.append(duration);

            var causes = $('<ol/>', { 'class': 'causes' });
            $.each(status.causes, function(key, cause) {
                causes.append($('<li/>').append(cause));
            });
            job.append(causes);

            var changes = $('<ol/>', { 'class': 'changes' });
            if (status.changes.length == 0) {
                var element = $('<li/>').append('No SCM changes');
                changes.append(element);
            } else {
                $.each(status.changes, function(key, change) {
                    changes.append($('<li/>').append(change));
                });
            }
            job.append(changes);

            jobs.append(job);
        });

        $('#jobs').remove();
        $('body').append(jobs);

        var invokation = "refresh('" + view + "')";
        setTimeout(invokation, 5000);
    });
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