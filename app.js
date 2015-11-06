var express = require('express')
  , passport = require('passport')
  , util = require('util')
  , FacebookStrategy = require('passport-facebook').Strategy
  , logger = require('morgan')
  , session = require('express-session')
  , bodyParser = require("body-parser")
  , cookieParser = require("cookie-parser")
  , methodOverride = require('method-override');

var FACEBOOK_APP_ID = "322980324492560"
var FACEBOOK_APP_SECRET = "745cc0ed81f3de714e42d6fd086abff5";

// Passport session setup.
//   To support persistent login sessions, Passport needs to be able to
//   serialize users into and deserialize users out of the session.  Typically,
//   this will be as simple as storing the user ID when serializing, and finding
//   the user by ID when deserializing.  However, since this example does not
//   have a database of user records, the complete Facebook profile is serialized
//   and deserialized.

passport.serializeUser(function(user, done) {
  done(null, user);
});

passport.deserializeUser(function(obj, done) {
  done(null, obj);
});

// Use the FacebookStrategy within Passport.
//   Strategies in Passport require a `verify` function, which accept
//   credentials (in this case, an accessToken, refreshToken, and Facebook
//   profile), and invoke a callback with a user object.
passport.use(new FacebookStrategy({
    clientID: FACEBOOK_APP_ID,
    clientSecret: FACEBOOK_APP_SECRET,
    callbackURL: "https://nourritureapi.herokuapp.com/auth/facebook/callback"
  },
  function(accessToken, refreshToken, profile, done) {
    // asynchronous verification, for effect...
    process.nextTick(function () {

      // To keep the example simple, the user's Facebook profile is returned to
      // represent the logged-in user.  In a typical application, you would want
      // to associate the Facebook account with a user record in your database,
      // and return that user instead.
      return done(null, profile);
    });
  }
));




var app = express();

// configure Express
  app.set('views', __dirname + '/views');
  app.set('view engine', 'ejs');
  app.use(logger());
  app.use(cookieParser());
  app.use(bodyParser());
  app.use(methodOverride());
  app.use(session({ secret: 'keyboard cat' }));
  // Initialize Passport!  Also use passport.session() middleware, to support
  // persistent login sessions (recommended).
  app.use(passport.initialize());
  app.use(passport.session());
  app.use(express.static(__dirname + '/public'));


app.get('/', function(req, res){
  res.render('index', { user: req.user });
});

app.get('/account', ensureAuthenticated, function(req, res){
  res.render('account', { user: req.user });
});

app.get('/login', function(req, res){
  res.render('login', { user: req.user });
});

// GET /auth/facebook
//   Use passport.authenticate() as route middleware to authenticate the
//   request.  The first step in Facebook authentication will involve
//   redirecting the user to facebook.com.  After authorization, Facebook will
//   redirect the user back to this application at /auth/facebook/callback
app.get('/auth/facebook',
  passport.authenticate('facebook'),
  function(req, res){
    // The request will be redirected to Facebook for authentication, so this
    // function will not be called.
  });

// GET /auth/facebook/callback
//   Use passport.authenticate() as route middleware to authenticate the
//   request.  If authentication fails, the user will be redirected back to the
//   login page.  Otherwise, the primary route function function will be called,
//   which, in this example, will redirect the user to the home page.
app.get('/auth/facebook/callback',
  passport.authenticate('facebook', { successRedirect: '/success',
                                      failureRedirect: '/failure' }),
  function(req, res) {
    res.redirect('/');
  });

app.get('/success', function(req, res) {
  res.send('SUCCESS');
});

app.get('/failure', function(req, res) {
  res.send('Connection failed !');
});

app.get('/logout', function(req, res){
  req.logout();
  res.redirect('/');
});
 app.listen(process.env.PORT || 5000);


// Simple route middleware to ensure user is authenticated.
//   Use this route middleware on any resource that needs to be protected.  If
//   the request is authenticated (typically via a persistent login session),
//   the request will proceed.  Otherwise, the user will be redirected to the
//   login page.
function ensureAuthenticated(req, res, next) {
  if (req.isAuthenticated()) { return next(); }
  res.redirect('/login')
}


// var express = require('express');
// var path = require('path');
// var favicon = require('serve-favicon');
// var logger = require('morgan');
// var cookieParser = require('cookie-parser');
// var bodyParser = require('body-parser');

// var mongo = require('mongodb');
// var monk = require('monk');
// var db = monk('localhost:27017/test');

// var routes = require('./routes/index');
// var users = require('./routes/users');
// var server = require('./routes/server')

// var app = express();
// var fs = require("fs");
// var util = require('util');
// var session = require('express-session');
// var passport = require('passport');
// var FacebookStrategy = require('passport-facebook').Strategy;
// var methodOverride = require('method-override');

// var FACEBOOK_APP_ID = "497234817102475"
// var FACEBOOK_APP_SECRET = "ace80a995d3306cf9cc7fd0bd339839d";


// // Passport session setup.
// //   To support persistent login sessions, Passport needs to be able to
// //   serialize users into and deserialize users out of the session.  Typically,
// //   this will be as simple as storing the user ID when serializing, and finding
// //   the user by ID when deserializing.  However, since this example does not
// //   have a database of user records, the complete Facebook profile is serialized
// //   and deserialized.
// passport.serializeUser(function(user, done) {
//   done(null, user);
// });

// passport.deserializeUser(function(obj, done) {
//   done(null, obj);
// });


// // Use the FacebookStrategy within Passport.
// //   Strategies in Passport require a `verify` function, which accept
// //   credentials (in this case, an accessToken, refreshToken, and Facebook
// //   profile), and invoke a callback with a user object.
// passport.use(new FacebookStrategy({
//   clientID: FACEBOOK_APP_ID,
//   clientSecret: FACEBOOK_APP_SECRET,
//   callbackURL: "http://localhost:3000/auth/facebook/callback"
// },
// function(accessToken, refreshToken, profile, done) {
//     // asynchronous verification, for effect...
//     process.nextTick(function () {

//       // To keep the example simple, the user's Facebook profile is returned to
//       // represent the logged-in user.  In a typical application, you would want
//       // to associate the Facebook account with a user record in your database,
//       // and return that user instead.
//       return done(null, profile);
//     });
//   }
//   ));


// // view engine setup
// app.set('views', path.join(__dirname, 'views'));
// app.set('view engine', 'ejs');

// // uncomment after placing your favicon in /public
// //app.use(favicon(path.join(__dirname, 'public', 'favicon.ico')));
// app.use(logger('dev'));
// app.use(bodyParser.json());
// app.use(bodyParser.urlencoded({ extended: false }));
// app.use(cookieParser());
// app.use(express.static(path.join(__dirname, 'public')));
// app.use(passport.initialize());
// app.use(passport.session());

// app.use(function(req,res,next){
//   req.db = db;
//   next();
// });

// // app.use('/', routes);
// // app.use('/users', users);
// // app.use('/api', server);

// app.get('/', function(req, res){
//   res.render('index', { user: req.user });
// });

// app.get('/account', ensureAuthenticated, function(req, res){
//   res.render('account', { user: req.user });
// });

// app.get('/login', function(req, res){
//   res.render('login', { user: req.user });
// });

// // GET /auth/facebook
// //   Use passport.authenticate() as route middleware to authenticate the
// //   request.  The first step in Facebook authentication will involve
// //   redirecting the user to facebook.com.  After authorization, Facebook will
// //   redirect the user back to this application at /auth/facebook/callback
// app.get('/auth/facebook',
//   passport.authenticate('facebook'),
//   function(req, res){
//     // The request will be redirected to Facebook for authentication, so this
//     // function will not be called.
//   });

// // GET /auth/facebook/callback
// //   Use passport.authenticate() as route middleware to authenticate the
// //   request.  If authentication fails, the user will be redirected back to the
// //   login page.  Otherwise, the primary route function function will be called,
// //   which, in this example, will redirect the user to the home page.
// app.get('/auth/facebook/callback',
//   passport.authenticate('facebook', { failureRedirect: '/login' }),
//   function(req, res) {
//     res.redirect('/');
//   });

// app.get('/logout', function(req, res){
//   req.logout();
//   res.redirect('/');
// });

// // catch 404 and forward to error handler
// app.use(function(req, res, next) {
//   var err = new Error('Not Found');
//   err.status = 404;
//   next(err);
// });

// // error handlers

// // development error handler
// // will print stacktrace
// if (app.get('env') === 'development') {
//   app.use(function(err, req, res, next) {
//     res.status(err.status || 500);
//     res.render('error', {
//       message: err.message,
//       error: err
//     });
//   });
// }

// // production error handler
// // no stacktraces leaked to user
// app.use(function(err, req, res, next) {
//   res.status(err.status || 500);
//   res.render('error', {
//     message: err.message,
//     error: {}
//   });
// });


// app.listen(process.env.PORT || 5000);

// function ensureAuthenticated(req, res, next) {
//   if (req.isAuthenticated()) { return next(); }
//   res.redirect('/login')
// }

module.exports = app;
