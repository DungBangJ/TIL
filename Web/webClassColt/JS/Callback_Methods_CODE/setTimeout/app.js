console.log("HELLO!!!...")
setTimeout(() => {
    console.log("...are you still there?")
}, 3000)

console.log("GOODBYE!!")


const id = setInterval(() => {
    console.log(Math.random())
}, 2000);

// clearInterval(id);

const id = setInterval(() => {
    console.log(Math.random())
}, 1000);

setTimeout(() => {
    console.log("TimeOut")
    clearInterval(id);
}, 10000)