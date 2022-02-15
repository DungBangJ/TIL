//파일을 가져온다.(npm 이름)
const jokes = require("give-me-a-joke");
const colors = require("colors");

jokes.getRandomDadJoke(function (joke) {
    console.log(joke.rainbow);
});