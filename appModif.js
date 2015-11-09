var express = require('express');
var path = require('path');
var app = express();

var myLogger = function(req, res, next) {
	console.log('LOGGED');
	next();
};

app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'ejs');

app.use(myLogger);

app.get('/', function(req, res){
	//res.send('salut tout le monde');
	res.render('index', {title : 'accueil'});
})
.get('/nourriture', function(req, res){
	res.render('nourriture', {title: 'nourriture'});
});

app.listen(3000);