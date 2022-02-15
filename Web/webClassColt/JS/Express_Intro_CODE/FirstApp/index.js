// This download does not include the node_modules folder
// REMEMBER TO RUN "npm install" first, to tell NPM to download the needed packages
//nodemon을 사용하면 서버를 재부팅하지 않아도된다. 그냥 Ctrl+s만 누르면 알아서 재부팅해준다.
const express = require("express");
const app = express();

// 요청을 받으면 출력을 해준다.
// app.use((req, res) => {
//     console.log("WE GOT A NEW REQUEST!!")
// end는 HTTP요청에 응답한다.
//     res.send('<h1>This is my webpage!</h1>')
// })


app.get('/', (req, res) => {
    res.send('Welcome to the home page!')
})

///r다음의 모든 형식이 해당된다.
app.get('/r/:subreddit', (req, res) => {
    const {subreddit} = req.params;//여기서는 /r뒤에 오는 것이 params
    res.send(`<h1>Browsing the ${subreddit} subreddit</h1>`)
})

///r/:subreddit다음의 모든 형식이 해당된다.
app.get('/r/:subreddit/:postId', (req, res) => {
    const {subreddit, postId} = req.params;//여기서는 /r/:subreddit 뒤에 나오는 것이 params
    res.send(`<h1>Viewing Post ID: ${postId} on the ${subreddit} subreddit</h1>`)
})

app.post('/cats', (req, res) => {
    res.send('POST REQUEST TO /cats!!!! THIS IS DIFFERENT THAN A GET REQUEST!')
})

app.get('/cats', (req, res) => {
    res.send('MEOW!!')
})

app.get('/dogs', (req, res) => {
    res.send('WOOF!')
})

//query '?q=' 사용
//'/search?q='
app.get('/search', (req, res) => {
    const {q} = req.query;
    if (!q) {
        res.send('NOTHING FOUND IF NOTHING SEARCHED!')
    } else {
        res.send(`<h1>Search results for: ${q}</h1>`)
    }
})

//이게 제일 위로 간다면 모든 단어에 걸리기 때문에 마지막의 gibberish만 처리하려면 마지막에 위치시킨다.
app.get('*', (req, res) => {
    res.send(`I don't know that path!`)
})


// /cats => 'meow'
// /dogs => 'woof'
// '/' 


app.listen(8080, () => {
    console.log("LISTENING ON PORT 8080")
})
