const numbers = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20];

numbers.filter(n => {
    return n < 10
})


const movies = [
    {
        title: 'Amadeus',
        score: 99,
        year: 1984
    },
    {
        title: 'Sharknado',
        score: 35,
        year: 2013
    },
    {
        title: '13 Going On 30',
        score: 70,
        year: 2004
    },
    {
        title: 'Stand By Me',
        score: 85,
        year: 1986
    },
    {
        title: 'Waterworld',
        score: 62,
        year: 1995
    },
    {
        title: 'Jingle All The Way',
        score: 71,
        year: 1996
    },
    {
        title: 'Parasite',
        score: 95,
        year: 2019
    },
    {
        title: 'Notting Hill',
        score: 77,
        year: 1999
    },
    {
        title: 'Alien',
        score: 90,
        year: 1979
    }
]


// 조건에 맞는 원소들은 filtered Array에 담기게 되고, 변수에 저장된다.
const badMovies = movies.filter(m => m.score < 70)

const recentMovies = movies.filter(m => m.year > 2000)

const goodMovies = movies.filter(m => m.score > 80)
const goodTitles = goodMovies.map(m => m.title)

//filter는 배열 원소의 모든 정보를 다 가져오므로, map과 함께 써서 title만 가져오게 할 수도 있다.
movies.filter(m => m.score > 80).map(m => m.title);
