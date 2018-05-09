function displayPatients(patients) {
    var $tbody = $('tbody');
    $tbody.empty();
    for(var i in patients) {
        var patient = patients[i];
        var $row = $('<tr>');
        $('<td>').html(patient.id).appendTo($row);
        $('<td>').html(patient.name).appendTo($row);
        $('<td>').html(patient.pnc).appendTo($row);
        $('<td>').html(new Date(patient.dob).toDateString()).appendTo($row);
        $('<td>').html(patient.address).appendTo($row);
        $('<td>').html(patient.idInfo).appendTo($row);
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
function refreshPatients() {
    $.get('/secretary/patients', {}, function(result) {
        displayPatients(result);
    });
}

function addPatient(patient) {
    $.ajax('/secretary/createPatient', {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: 'POST',
        data: JSON.stringify(patient),
        dataType: 'text',
        success: function(result) {
            refreshPatients();
            $('#createName,#createPNC,#createAddress,#createIdInfo').val('');
            displayResult(result);
        }
    });
}

function updatePatient(patient) {
    $.ajax('/secretary/updatePatient', {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: 'POST',
        data: JSON.stringify(patient),
        dataType: 'text',
        success: function(result) {
            refreshPatients();
            $('#updateName,#updatePNC,#updateAddress,#updateIdInfo').val('');
            displayResult(result);
        }
    });
}


$(function() {
    refreshPatients();
    $('button').click(function() {
        switch(this.id) {
            case "createPatient":
                addPatient({
                    'name': $('#createName').val(),
                    'pnc': $('#createPNC').val(),
                    'dob':  $('#createBirthday').val(),
                    'address': $('#createAddress').val(),
                    'idInfo': $('#createIdInfo').val()
                });
                break;
            case "updatePatient":
                updatePatient({
                    'id':  $('#updateIdPatient').val(),
                    'name': $('#updateName').val(),
                    'pnc': $('#updatePNC').val(),
                    'dob':  $('#updateBirthday').val(),
                    'address': $('#updateAddress').val(),
                    'idInfo': $('#updateIdInfo').val()
                });
        }
        return false;
    });
});