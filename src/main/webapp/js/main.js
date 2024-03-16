const users = document.getElementById("users");

getUsers();
async function getUsers() {
    let response = await fetch("UsersData");
    let root = await response.json();

    for (const user of root.result) {
        users.innerHTML += `${user.id}:${user.name},${user.age}<br>`;
    }
}