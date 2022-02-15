const add = (x, y) => x + y;

const PI = 3.14159;

const square = x => x * x;

//이 이름으로 다른 파일에서 쓸 수 있게 한다.
exports.square = square;
exports.PI = PI;

// =========================
// module.exports.add = add;
// module.exports.PI = PI;
// module.exports.square = square;


