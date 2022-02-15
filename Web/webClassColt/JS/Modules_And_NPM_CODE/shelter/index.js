//require는 해당 파일(디렉토리)에 가져온다고 생각하자.
const blue = require('./blue')
const sadie = require('./sadie')
const janet = require('./janet')

const allCats = [blue, sadie, janet]

module.exports = allCats;