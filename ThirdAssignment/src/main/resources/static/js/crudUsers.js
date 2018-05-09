function displayUsers(users) {
    var $tbody = $('tbody');
    $tbody.empty();
    for(var i in users) {
        var user = users[i];
        var $row = $('<tr>');
        $('<td>').html(user.username).appendTo($row);
        $('<td>').html(user.role).appendTo($row);
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
function refreshUsers() {
    $.get('/admin/users', {}, function(result) {
        displayUsers(result);
    });
}

function addUser(user) {
    $.ajax('/admin/createUser', {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: 'POST',
        data: JSON.stringify(user),
        dataType: 'text',
        success: function(result) {
            refreshUsers();
            $('#username,#password,#role').val('');
            displayResult(result);
        }
    });
}

function updateUser(user) {
    $.ajax('/admin/updateUser', {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: 'POST',
        data: JSON.stringify(user),
        dataType: 'text',
        success: function(result) {
            refreshUsers();
            $('#username,#password,#role').val('');
            displayResult(result);
        }
    });
}

function deleteUser(delUsername) {
    $.ajax('/admin/deleteUser', {
        type: 'POST',
        data: delUsername,
        success: function() {
            refreshUsers();
            $('#delUsername').val('');
        }
    });
}

$(function() {
    refreshUsers();
    $('button').click(function() {
        switch(this.id) {
            case "createUser":
                addUser({
                    'username': $('#username').val(),
                    'password': $('#password').val(),
                    'role': $('#role').val()
                });
                break;
            case "deleteUser":
                deleteUser($('#delUsername').val());
                break;
            case "updateUser":
                updateUser({
                    'username': $('#username').val(),
                    'password': $('#password').val(),
                    'role': $('#role').val()
                });
        }
        return false;
    });
});