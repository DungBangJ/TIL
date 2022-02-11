const button = document.querySelector('#changeColor');
const container = document.querySelector('#container');
const pbutton = document.querySelector(('p button'));

button.addEventListener('click', function (e) {
    container.style.backgroundColor = makeRandColor();
    e.stopPropagation();
})
container.addEventListener('click', function () {
    container.classList.toggle('hide');
})

pbutton.addEventListener('click', function (e) {
    e.stopPropagation();
})

const makeRandColor = () => {
    const r = Math.floor(Math.random() * 255);
    const g = Math.floor(Math.random() * 255);
    const b = Math.floor(Math.random() * 255);
    return `rgb(${r}, ${g}, ${b})`;
}