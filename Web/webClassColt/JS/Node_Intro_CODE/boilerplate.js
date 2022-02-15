const fs = require('fs');

//default값이 Project이고, 터미널에서  따로 폴더명을 지정해줄 수 있다.
const folderName = process.argv[2] || 'Project'

// fs.mkdir('Dogs', { recursive: true }, (err) => {
//     console.log("IN THE CALLBACK!!")
//     if (err) throw err;
// });

try {
    //폴더의 이름을 만든다.
    fs.mkdirSync(folderName);

    //폴더에 파일들을 만든다.
    fs.writeFileSync(`${folderName}/index.html`, '')
    fs.writeFileSync(`${folderName}/app.js`, '')
    fs.writeFileSync(`${folderName}/styles.css`, '')
} catch (e) {
    console.log("SOMETHING WENT WRONG!!!");
    console.log(e);
}