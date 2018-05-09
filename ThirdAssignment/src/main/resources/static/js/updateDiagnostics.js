function displayPastConsultations(consultations) {
    var $tbody = $('#pastConsultations tbody');
    $tbody.empty();
    for(var i in consultations) {
        var consultation = consultations[i];
        var $row = $('<tr>');
        $('<td>').html(consultation.id).appendTo($row);
        $('<td>').html(consultation.doctor.username).appendTo($row);
        $('<td>').html(consultation.patient.name).appendTo($row);
        $('<td>').html(new Date(consultation.date).toDateString()).appendTo($row);
        $('<td>').html(consultation.diagnostic).appendTo($row);
        $('<td>').html(consultation.recommendation).appendTo($row);
        $row.appendTo($tbody);
    }
}

function displayFutureConsultations(consultations) {
    var $tbody = $('#futureConsultations tbody');
    $tbody.empty();
    for(var i in consultations) {
        var consultation = consultations[i];
        var $row = $('<tr>');
        $('<td>').html(consultation.id).appendTo($row);
        $('<td>').html(consultation.doctor.username).appendTo($row);
        $('<td>').html(consultation.patient.name).appendTo($row);
        $('<td>').html(new Date(consultation.date).toDateString()).appendTo($row);
        $('<td>').html(consultation.diagnostic).appendTo($row);
        $('<td>').html(consultation.recommendation).appendTo($row);
        $row.appendTo($tbody);
    }
}

function displayResult(result)
{
    $("#result").empty();
    var list = "";
    var rez=JSON.parse(result);
    for (var i = 0; i < rez.length; i++) {
        list +="<li>"+rez[i]+"</li>";
    }

    $("#result").append(list);

}
function refreshConsultations() {
    $.get('/doctor/pastConsultations', {}, function(result) {
        displayPastConsultations(result);
    });

    $.get('/doctor/futureConsultations', {}, function(result) {
        displayFutureConsultations(result);
    });
}

function updateConsultation(consultation) {
    $.ajax('/doctor/updateConsultation', {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: 'POST',
        data: JSON.stringify(consultation),
        dataType: 'text',
        success: function(result) {
            refreshConsultations();
            $('#idConsultation,#diagnostic,#recommendation').val('');
            displayResult(result);
        }
    });
}
var stompClient = null;


function connect() {
    var socket = new SockJS('/exampleEndpoint');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/user/queue/reply', function (message) {
            var msg="<li>"+JSON.parse(message.body).text+"</li>";
            $("#result").append(msg);
        });
    });
}

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
}

$(function() {
    refreshConsultations();
    $('button').click(function() {
        switch(this.id) {
            case "updateConsultation":
                updateConsultation({
                    'id': $('#idConsultation').val(),
                    'diagnostic': $('#diagnostic').val(),
                    'recommendation': $('#recommendation').val(),
                });
        }
        return false;
    });
});