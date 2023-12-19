const form = document.getElementById('roles-form');
form.addEventListener('submit', (e) => {
    e.preventDefault();
    const res = []
    const selects = document.querySelectorAll(".role-select");

    for (let index = 0; index < selects.length; index++) {
        let curr = selects[index];
        res.push({id: curr.id, roles: [...curr.options].filter(x => x.selected).map(x => x.value)})
    }

    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/api/manage-roles", true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.send(JSON.stringify(res));
    if (xhr.status === 200)
        window.location.reload();
})
