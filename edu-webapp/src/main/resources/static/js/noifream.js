/**
 * 跳转页面不用iframe框架
 * @type {string[]}
 */
var noIfreamUrl = [
    'login',
    'index'
];

function toPageNoIframe(url) {
    for (var i = 0; i < noIfreamUrl.length; i++) {
        if (url.indexOf(noIfreamUrl[i]) != -1) {
            window.parent.location = url;
        }
    }
}