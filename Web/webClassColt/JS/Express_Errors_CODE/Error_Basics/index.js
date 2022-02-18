const express = require('express');
const app = express();
const morgan = require('morgan');

const AppError = require('./AppError');

app.use(morgan('tiny'));

app.use((req, res, next) => {
    req.requestTime = Date.now();
    console.log(req.method, req.path);
    next();
})

app.use('/dogs', (req, res, next) => {
    console.log("I LOVE DOGS!!")
    next();
})

const verifyPassword = (req, res, next) => {
    const { password } = req.query;
    if (password === 'chickennugget') {
        next();
    }
    throw new AppError('password required', 401);
    // res.send("PASSWORD NEEDED!")
    // throw new AppError('Password required!', 400)
}

app.get('/', (req, res) => {
    console.log(`REQUEST DATE: ${req.requestTime}`)
    res.send('HOME PAGE!')
})

app.get('/error', (req, res) => {
    chicken.fly()//얘는 app error가 아니고 syntax error이므로 따로 error를 지정해줘야 한다. (500)
})

app.get('/dogs', (req, res) => {
    console.log(`REQUEST DATE: ${req.requestTime}`)
    res.send('WOOF WOOF!')
})

app.get('/secret', verifyPassword, (req, res) => {
    res.send('MY SECRET IS: Sometimes I wear headphones in public so I dont have to talk to anyone')
})

//error handler를 확인해보기 위한 함수(403)
app.get('/admin', (req, res) => {
    throw new AppError('You are not an Admin!', 403)
})

app.use((req, res) => {
    res.status(404).send('NOT FOUND!')
})


// app.use((err, req, res, next) => {
//     console.log("******************************************")
//     console.log("*****************ERROR*****************")
//     console.log("******************************************")
//     console.log(err)
//     next(err)
// })

app.use((err, req, res, next) => {
    //syntax error를 받아주는 error handler, 초기값을 500으로 주는 것이다.
    const { status = 500, message = 'Something Went Wrong' } = err;
    res.status(status).send(message)
})


app.listen(3000, () => {
    console.log('App is running on localhost:3000')
})