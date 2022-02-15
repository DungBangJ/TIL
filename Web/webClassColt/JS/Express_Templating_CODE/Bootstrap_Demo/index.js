const express = require('express');
const app = express();
const path = require('path');
const redditData = require('./data.json');

//css는 static으로
app.use(express.static(path.join(__dirname, 'public')))

//현재 경로(__dirname)에다가 '/views'를 더해주는 경로를 사용하기 위해 쓴 것
app.set('view engine', 'ejs');
app.set('views', path.join(__dirname, '/views'))

//main home page
app.get('/', (req, res) => {
    res.render('home')
})

// app.listen(3000, () =>{
//     console.log("LISTENING ON PORT 3000");
// })

app.get('/cats', (req, res) => {
    const cats = [
        'Blue', 'Rocket', 'Monty', 'Stephanie', 'Winston'
    ]
    res.render('cats', { cats })
})

app.get('/r/:subreddit', (req, res) => {
    const { subreddit } = req.params;
    const data = redditData[subreddit];
    if (data) {
        res.render('subreddit', { ...data });
    } else {
        res.render('notfound', { subreddit })
    }
})

//random number 출력
app.get('/rand', (req, res) => {
    const num = Math.floor(Math.random() * 10) + 1;
    res.render('random', { num })
})

app.listen(3000, () => {
    console.log("LISTENING ON PORT 3000")
})