const express = require('express');
const app = express();
const morgan = require('morgan');

//middleware는 req와 res 사이에 끼어있는 함수라고 생각하면 된다.
app.use(morgan('common'));

app.use((req, res, next) => {
    // req.method = 'GET'; //이렇게 method를 지정해준다면 method를 바꿔도 다 GET으로 된다.
    req.requestTime = Date.now();
    console.log(req.method, req.path);
    next();
})

app.use('/dogs', (req, res, next) => {
    console.log("I love Dogs!!!")
    next();
})

//이렇게 함수로 만들어버리면 모든 곳에서 password를 받으며 사용할 수 있다.
const verifyPassword = (req, res, next) => {
    // console.log(req.query); //query형식을 url에서 받아올 수 있다.
    const {password} = req.query;
    //password가 틀리면 next를 실행시킬 수 없다.
    //이런 식으로 query를 이용해 로그인을 만들지는 않는다.
    if (password === 'chickennugget') {
        next();
    }
    // res.send('SORRY YOU NEED A PASSWORD');
    throw new Error('Password required!')
}
//next를 사용하지 않는다면 다음 middleware 함수를 호출하지 못한다.
// app.use((req, res, next) => {
//     console.log('This is my first middleware');
//     //여기에 next()가 없다면 다른 함수를 호출하지 못하고 여기서 프로그램이 끝나버린다.
//     next();
//     // return next(); 이렇게 return을 쓴다면 이 지역에서 이 다음의 코드는 실행되지 않는다.
//     //위의 next()가 다음 것을 호출하고 다음 것이 작업이 끝나면 마지막으로 호출된다.
//     console.log('This is my third middleware after nex()!!!')
// });
//
// app.use((req, res, next) => {
//     console.log('This is my second middleware');
//     next();
// });

app.get('/', (req, res) => {
    console.log(`REQUEST DATE: ${req.requestTime}`);
    res.send('HOME PAGE!');
})

app.get('/error', (req, res) => {
    chicken.fly();
})

app.get('/dogs',(req, res) => {
    console.log(`REQUEST DATE: ${req.requestTime}`);
    res.send('WOOF WOOF!');
})

//callback function을 두개를 쓰면 순서대로 수행된다., verifyPassword의 next가 실행되지 않으면 다음 callback함수는 실행되지 않는다.
app.get('/secret', verifyPassword, (req, res) => {
    res.send('My Secret page');
})

app.use((req, res) => {
    // res.send('NOT FOUND');
    res.status(404).send('NOT FOUND');
})

//error handling은 인자가 4개이다.
app.use((err, req, res, next) => {
    console.log('++++++++++++++++++++++++++++++')
    console.log('+++++++++++++error++++++++++++')
    console.log('++++++++++++++++++++++++++++++')
    // console.log(err);
    //error는 next()에 인자를 넣어줘야 한다.
    next(err)
})

app.listen(3000, () => {
    console.log('App is running on localhost:3000');
})