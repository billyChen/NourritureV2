var express = require('express');
var session = require('express-session');
var path = require('path');
var favicon = require('serve-favicon');
var logger = require('morgan');
var cookieParser = require('cookie-parser');
var bodyParser = require('body-parser');
var request = require('request');


var routes = require('./routes/index');
var users = require('./routes/users');
var mongo = require('mongodb');
var monk = require('monk');
var db = monk('mongodb://root:123456@ds049624.mongolab.com:49624/nourriture');

var app = express();

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'ejs');

// uncomment after placing your favicon in /public
//app.use(favicon(path.join(__dirname, 'public', 'favicon.ico')));
app.use(logger('dev'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));
app.use(session({secret: 'NourritureWebInterfaceSecret2015'}));

app.use(function(req,res,next){
  req.db = db;
  next();
});

app.get('/createUser', function(req, res, next){
  var db = req.db;
  var collection = db.get('allergens');
  var allergens = [''];
  var ingredients = [''];
  request.get({
    url: 'http://nourritureapi.herokuapp.com/listAllergens',
    method: 'GET',
  }, function (error, response, body) {
    if (!error && response.statusCode == 200) {
      allergens = JSON.parse(body);
      request.get({
        url: 'http://nourritureapi.herokuapp.com/listIngredients',
        method: 'GET',
      }, function (error, response, body) {
        if (!error && response.statusCode == 200) {
          ingredients = JSON.parse(body);
          res.render('createUser', { title: 'Create your profil',
                     allergens: allergens,
                     ingredients: ingredients});
        }
      });
    }
  });
});

app.post('/UserAuthentication', function(req, res, next){
  var sess;

  request.post({
    url: 'http://nourritureapi.herokuapp.com/ensureAuth',
    method: 'POST',
    form: {
      username: req.body.username,
      password: req.body.password
    }
  }, function (error, response, body) {
    sess = req.session;

    sess.user = JSON.parse(body);
    console.log(body);
    res.redirect('/');
  });
});

app.get('/logOut', function (req, res, next) {
  req.session.destroy();
});



app.use('/', routes);
app.use('/users', users);
app.use('/welcome', users);
app.use('/addIngredients', users);
app.use('/addRecipe', users);
app.use('/logIn', users);
// app.use('/createUser', users);
//app.use('/nourriture', routes);

// catch 404 and forward to error handler
app.use(function(req, res, next) {
  var err = new Error('Not Found');
  err.status = 404;
  next(err);
});

// error handlers

// development error handler
// will print stacktrace
if (app.get('env') === 'development') {
  app.use(function(err, req, res, next) {
    res.status(err.status || 500);
    res.render('error', {
      message: err.message,
      error: err
    });
  });
}

// production error handler
// no stacktraces leaked to user
app.use(function(err, req, res, next) {
  res.status(err.status || 500);
  res.render('error', {
    message: err.message,
    error: {}
  });
});

module.exports = app;
