////////////////          LINK <<USER>>         ////////////////
function getUser() {
    fetch("/api/user",
        {method: 'GET', dataType: 'json'})
        .then((res) => res.json())
        .then((user) => {
            let temp = "";
            temp += `<tr>
            <td>${user.id}</td>
            <td>${user.username}</td>
            <td>${user.lastName}</td>
            <td>${user.age}</td>
            <td>${user.email}</td>
            <td>${user.roles.map(e => e.name).join(", ")}</td>
            </tr>`;
            document.getElementById("userInfo").innerHTML = temp;
        });
}

getUser();

////////////////          LINK <<ADMIN>>         ////////////////


function getAllUsers() {
    let temp = '';
    fetch("/api/users",
        {method: 'GET', dataType: 'json'})
        .then(res => res.json())
        .then(users => {

            users.forEach(function (user) {
                temp += `
                <tr>
                <td>${user.id}</td>
                <td>${user.username}</td>
                <td>${user.lastName}</td>
                <td>${user.age}</td>
                <td>${user.email}</td>
                <td>${user.roles.map(e => " " + e.name)}</td>
                <td><button type="submit" onclick="modalWindowEditUser(${user.id})"
                class="btn btn-info" data-toggle="modal" data-target="#updateUser">Edit</button></td>
                <td><button type="submit" onclick="modalWindowDeleteUser(${user.id})" 
                class="btn btn-danger" data-toggle="modal" data-target="#deleteUser">Delete</button></td>
              </tr>`;
            });
            document.getElementById("users").innerHTML = temp;
        });
}

getAllUsers()

////////////////         MODAL WINDOW <<EDIT>>         ////////////////
function modalWindowEditUser(id) {
    fetch("/api/users/" + id,
        {method: 'GET', dataType: 'json'})
        .then(res => {
            res.json().then(user => {
                $('#updateId').val(user.id)
                $('#updateUsername').val(user.username)
                $('#updateLastName').val(user.lastName)
                $('#updateAge').val(user.age)
                $('#updateEmail').val(user.email)
                $('#updatePassword').val(user.password)
                $('#updateRoles').val(user.roles.map(e => " " + e.name))
            })
        })
}

////////////////          EDIT USER         ////////////////
async function updateUser() {
    let user = {
        id: document.getElementById('updateId').value,
        username: document.getElementById('updateUsername').value,
        lastName: document.getElementById('updateLastName').value,
        age: document.getElementById('updateAge').value,
        email: document.getElementById('updateEmail').value,
        password: document.getElementById('updatePassword').value,
        rolesId: $('#updateRoles').val(),
    }

    await fetch("/api/users",
        {
            method: 'PUT',
            headers: {'Accept': 'application/json', 'Content-Type': 'application/json;charset=UTF-8'},
            body: JSON.stringify(user)
        })

    $("#updateUser .close").click();
    getAllUsers();
    getUser();

}

////////////////         MODAL WINDOW <<DELETE>>         ////////////////
function modalWindowDeleteUser(id) {
    fetch("/api/users/" + id,
        {method: 'GET', dataType: 'json'})
        .then(res => {
            res.json().then(user => {
                $('#deleteId').val(user.id)
                $('#deleteUsername').val(user.username)
                $('#deleteLastName').val(user.lastName)
                $('#deleteAge').val(user.age)
                $('#deleteEmail').val(user.email)
                $('#deleteRoles').val(user.roles.map(e => " " + e.name))
            })
        })
}

////////////////          DELETE USER         ////////////////
async function deleteUser() {
    await fetch("/api/users/" + document.getElementById("deleteId").value,
        {method: 'DELETE', dataType: 'json'})
    $("#deleteUser .close").click();

    getAllUsers();
    getUser();
}

////////////////          NEW USER         ////////////////
async function addNewUser() {
    let user = {
        // id: document.getElementById('newId').value,
        username: document.getElementById('newUsername').value,
        lastName: document.getElementById('newLastName').value,
        age: document.getElementById('newAge').value,
        email: document.getElementById('newEmail').value,
        password: document.getElementById('newPassword').value,
        rolesId: $('#newRoles').val(),
    }
    console.log("check_1")
    await fetch("/api/users",
        {
            method: 'POST',
            headers: {'Accept': 'application/json', 'Content-Type': 'application/json;charset=UTF-8'},
            body: JSON.stringify(user)
        })
    console.log("check_2")

    getAllUsers();
    getUser();
}