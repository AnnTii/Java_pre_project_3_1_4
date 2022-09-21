$(async function () {
    await getTableWithUsers();
    await getAuthorUser();
    await showRolesInNewUserForm()
});

const userFetchService = {
    head: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Referer': null
    },
    findAllUsers: async () => await fetch('restApi'),
    findAllRoles: async () => await fetch('restApi/roles'),
    showAuthUser: async () => await fetch('restApi/authUser'),
    findOneUser: async (id) => await fetch(`restApi/${id}`),
    addNewUser: async (user) => await fetch('restApi', {
        method: 'POST',
        headers: userFetchService.head,
        body: JSON.stringify(user)
    }),
    updateUserById: async (user) => await fetch(`restApi`, {
        method: 'PATCH',
        headers: userFetchService.head,
        body: JSON.stringify(user)
    }),
    deleteUser: async (id) => await fetch(`restApi/${id}`, {method: 'DELETE', headers: userFetchService.head})
}

function getTableWithUsers() {
    let table = $('#AllUsersTable');
    userFetchService.findAllUsers()
        .then(res => res.json())
        .then(users => {
            table.empty();
            users.forEach(user => {
                let tableFilling = `$(
                        <tr>
                            <td>${user.id}</td>
                            <td>${user.name}</td>
                            <td>${user.lastName}</td>
                            <td>${user.age}</td>
                            <td>${user.email}</td>
                            <td>${user.createdAt}</td>
                            <td>${user.createdBy}</td>
                            <td>${user.roles.map(role => " " + role.roleName.substring(5))}</td>
                            <td>
                                <button type="button" onclick="editUser(${user.id})" data-action="edit" class="btn btn-info"
                                data-toggle="modal" data-target="#edit">Edit</button>
                            </td>
                            <td>
                                <button type="button" onclick="deleteUser(${user.id})" data-action="delete" class="btn btn-danger"
                                data-toggle="modal" data-target="#delete">Delete</button>
                            </td>
                        </tr>
                )`;
                table.append(tableFilling);
            })
        })
}

function getAuthorUser() {
    userFetchService.showAuthUser()
        .then(res => res.json())
        .then(data => {
            let user = `$(
            <tr>
                <td>${data.id}</td>
                <td>${data.name}</td>
                <td>${data.lastName}</td>
                <td>${data.age}</td>
                <td>${data.email}</td>
                <td>${data.createdAt}</td>
                <td>${data.createdBy}</td>
                <td>${data.roles.map(role => " " + role.roleName.substring(5))}</td>)`;
            $('#singleUserTable').append(user);
        })
}

function editUser(id) {
    userFetchService.findOneUser(id)
        .then(res => {
            res.json().then(user => {

                $('#editId').val(user.id)
                $('#editName').val(user.name)
                $('#editLastName').val(user.lastName)
                $('#editAge').val(user.age)
                $('#editEmail').val(user.email)
                $('#editPassword').val()
                $('#editRoles').empty();

                userFetchService.findAllRoles()
                    .then(res => res.json())
                    .then(roles => {
                        roles.forEach(role => {
                            let selectedRole = false;
                            for (let i = 0; i < user.roles.length; i++) {
                                if (user.roles[i].roleName === role.roleName) {
                                    selectedRole = true;
                                    break;
                                }
                            }
                            let el = document.createElement("option");
                            el.text = role.roleName.substring(5);
                            el.value = role.id;
                            if (selectedRole) el.selected = true;
                            $('#editRoles')[0].appendChild(el);
                        })
                    })
            })
        })
}

document.forms["editForm"].addEventListener("submit", ev => {
    ev.preventDefault();
    let editUserRoles = [];
    for (let i = 0; i < document.forms["editForm"].roles.options.length; i++) {
        if (document.forms["editForm"].roles.options[i].selected) editUserRoles.push({
            id: document.forms["editForm"].roles.options[i].value,
            name: "ROLE_" + document.forms["editForm"].roles.options[i].text
        })
    }
    let user = {
        id: document.getElementById('editId').value,
        name: document.getElementById('editName').value,
        lastName: document.getElementById('editLastName').value,
        age: document.getElementById('editAge').value,
        email: document.getElementById('editEmail').value,
        password: document.getElementById('editPassword').value,
        roles: editUserRoles
    };


    userFetchService.updateUserById(user).then(() => {
        $('#editModalCloseButton').click();
        getTableWithUsers()
    })

})

function deleteUser(id) {
    userFetchService.findOneUser(id)
        .then(res => {
            res.json().then(user => {
                $('#deleteId').val(user.id)
                $('#deleteName').val(user.name)
                $('#deleteLastName').val(user.lastName)
                $('#deleteAge').val(user.age)
                $('#deleteEmail').val(user.email)
                $('#deleteRoles').empty();

                userFetchService.findAllRoles()
                    .then(res => res.json())
                    .then(roles => {
                        roles.forEach(role => {
                            let selectedRole = false;
                            for (let i = 0; i < user.roles.length; i++) {
                                if (user.roles[i].roleName === role.roleName) {
                                    selectedRole = true;
                                    break;
                                }
                            }
                            let el = document.createElement("option");
                            el.text = role.roleName.substring(5);
                            el.value = role.id;
                            if (selectedRole) el.selected = true;
                            $('#deleteRoles')[0].appendChild(el);
                        })
                    });
            })
        })
}

function deleteUserById() {
    let id = document.getElementById('deleteId').value;
    document.forms["deleteForm"].addEventListener("submit", ev => {
        ev.preventDefault();
        userFetchService.deleteUser(id)
            .then(() => {
                getTableWithUsers()
                $('#deleteModalCloseButton').click();
            })
    })
}


document.forms["newUserForm"].addEventListener("submit", ev => {
    ev.preventDefault();
    let newUserRoles = [];
    for (let i = 0; i < document.forms["newUserForm"].roles.options.length; i++) {
        if (document.forms["newUserForm"].roles.options[i].selected) newUserRoles.push({
            id: document.forms["newUserForm"].roles.options[i].value,
            name: document.forms["newUserForm"].roles.options[i].name
        })
    }
    let user = {
        name: document.getElementById('addName').value,
        lastName: document.getElementById('addLastName').value,
        age: document.getElementById('addAge').value,
        email: document.getElementById('addEmail').value,
        password: document.getElementById('addPassword').value,
        roles: newUserRoles
    };
    userFetchService.addNewUser(user)
        .then(() => {
            document.forms["newUserForm"].reset();
            getTableWithUsers();
            $('.nav-tabs a[href="#tableUsers"]').tab('show')
        })
})

function showRolesInNewUserForm() {
    userFetchService.findAllRoles()
        .then(res => res.json())
        .then(roles => {
            roles.forEach(role => {
                let el = document.createElement("option");
                el.text = role.roleName.substring(5);
                el.value = role.id;
                $('#addRoles')[0].appendChild(el);
            })
        })
}