const input = document.querySelector('input');
const h1 = document.querySelector('h1');

// input.addEventListener('change', function (e) {
//     console.log("CASKDJASKJHD")
// })

input.addEventListener('input', function (e) {
    h1.innerText = "Welcome, " + input.value;
    if (h1.innerText === "Welcome,") {
        h1.innerText = "Enter Your Username";
    }
})
