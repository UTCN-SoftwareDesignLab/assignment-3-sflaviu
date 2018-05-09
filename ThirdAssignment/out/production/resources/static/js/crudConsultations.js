function displayConsultations(consultations) {
    var $tbody = $('tbody');
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
    $.get('/secretary/futureConsultations', {}, function(result) {
        displayConsultations(result);
    });

}

function addConsultation(consultation) {
    $.ajax('/secretary/createConsultation', {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: 'POST',
        data: JSON.stringify(consultation),
        dataType: 'text',
        success: function(result) {
            refreshConsultations();
            $('#idConsultation,#date').val('');
            displayResult(result);
        }
    });
}

function updateConsultation(consultation) {
    $.ajax('/secretary/updateConsultation', {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: 'POST',
        data: JSON.stringify(consultation),
        dataType: 'text',
        success: function(result) {
            refreshConsultations();
            $('#idConsultation,#date').val('');
            displayResult(result);
        }
    });
}

function deleteConsultation(delID) {
    $.ajax('/secretary/deleteConsultation', {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: 'POST',
        data: JSON.stringify(delID),
        dataType: 'text',
        success: function() {
            refreshConsultations();
            $('#delID').val('');
        }
    });
}

function checkin(announcement) {
    $.ajax('/secretary/checkin', {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: 'POST',
        data: JSON.stringify(announcement),
        dataType: 'text',
        success: function() {}
    });
}

var stompClient = null;


function connect() {
    var socket = new SockJS('/exampleEndpoint');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
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
            case "createConsultation":
                addConsultation({
                    'date': $('#createDate').val(),
                    'doctorUsername': $('#createDoctor').val(),
                    'patientId': $('#createPatient').val()
                });
                break;
            case "deleteConsultation":
                deleteConsultation($('#delID').val());
                break;
            case "updateConsultation":
                updateConsultation({
                    'id': $('#idConsultation').val(),
                    'date': $('#updateDate').val(),
                    'doctorUsername': $('#updateDoctor').val(),
                    'patientId': $('#updatePatient').val()

                });
                break;
            case "checkin":
                checkin({
                    'doctorUsername': $('#checkedInDoctor').val(),
                    'patientId': $('#checkedInPatient').val()

                });
        }
        return false;
    });
});