//npm init -y
//npm i mongoose로 mongoose를 설치한다.
const mongoose = require('mongoose');
//다음의 코드 한줄은 mongoose 사이트에서 가져온다.
mongoose.connect('mongodb://localhost:27017/movieApp', { useNewUrlParser: true, useUnifiedTopology: true })
    //connection에 대한 코드는 그냥 이렇게 직접 만들어도 된다.
    .then(() => {
        console.log("CONNECTION OPEN!!!")
    })
    .catch(err => {
        console.log("OH NO ERROR!!!!")
        console.log(err)
    })

const movieSchema = new mongoose.Schema({
    title: String,
    year: Number,
    score: Number,
    rating: String
});

//mongoose의 model 만들기
const Movie = mongoose.model('Movie', movieSchema);
// const amadeus = new Movie({ title: 'Amadeus', year: 1986, score: 9.2, rating: 'R' });
//model(인스턴스)를 만들어서 값을 초기화 한 후에 '.load index.js'를 cmd에 친다면 model을 조회할 수 있다.
//이 model을 database에 저장하려면 'model의 이름.save()'를 쳐주면 된다.
//값을 바꿔주고 싶다면 'model의 이름.type명=값'을 친 후에 'model의 이름.save()'를 하면 update가 된다.

//여러 개 넣기
// Movie.insertMany([
//     { title: 'Amelie', year: 2001, score: 8.3, rating: 'R' },
//     { title: 'Alien', year: 1979, score: 8.1, rating: 'R' },
//     { title: 'The Iron Giant', year: 1999, score: 7.5, rating: 'PG' },
//     { title: 'Stand By Me', year: 1986, score: 8.6, rating: 'R' },
//     { title: 'Moonrise Kingdom', year: 2012, score: 7.3, rating: 'PG-13' }
// ])
//     .then(data => {
//         console.log("IT WORKED!")
//         console.log(data);
//     })






