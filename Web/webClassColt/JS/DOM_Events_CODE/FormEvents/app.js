

const tweetForm = document.querySelector('#tweetForm');
const tweetsContainer = document.querySelector('#tweets');
tweetForm.addEventListener('submit', function (e) {
    e.preventDefault();

    // const usernameInput = document.querySelectorAll('input')[0];
    // const tweetInput = document.querySelectorAll('input')[1];
    const usernameInput = tweetForm.elements.username;
    const tweetInput = tweetForm.elements.tweet;
    addTweet(usernameInput.value, tweetInput.value)
    usernameInput.value = '';
    tweetInput.value = '';
});

const addTweet = (username, tweet) => {
    const newTweet = document.createElement('li');
    const bTag = document.createElement('b');
    bTag.append(username)
    newTweet.append(bTag);
    newTweet.append(`- ${tweet}`)
    tweetsContainer.append(newTweet);
}


// Leave the next line, the form must be assigned to a variable named 'form' in order for the exercise test to pass
// const form = document.querySelector('form');
// const ul = document.querySelector("#list");
//
// form.addEventListener("submit", function(e){
//     e.preventDefault();
//
//     const productInput = form.elements.product.value;
//     const quantityInput = form.elements.qty.value;
//     addPQ(productInput, quantityInput);
//     productInput.value = '';
//     quantityInput.value = '';
// });
//
// function addPQ(pro, qua){
//     const li = document.createElement("LI");
//     const b = document.createElement("b");
//     b.appendChild(qua);
//     li.appendChild(b);
//     li.appendChild(` ${pro}`);
//     ul.appendChild(li);
// }


