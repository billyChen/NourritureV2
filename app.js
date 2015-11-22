var express = require('express')
, passport = require('passport')
, util = require('util')
, FacebookStrategy = require('passport-facebook').Strategy
, GoogleStrategy = require('passport-google-oauth').OAuth2Strategy
, logger = require('morgan')
, session = require('express-session')
, bodyParser = require("body-parser")
, cookieParser = require("cookie-parser")
, methodOverride = require('method-override');

var mongo = require('mongodb');
var monk = require('monk');
var db = monk('mongodb://root:123456@ds049624.mongolab.com:49624/nourriture');

var FACEBOOK_APP_ID = "322980324492560";
var FACEBOOK_APP_SECRET = "745cc0ed81f3de714e42d6fd086abff5";
var GOOGLE_CLIENT_ID = "961840791432-kmmtn60o69622kgl2gsdia8d3kpdc6j4.apps.googleusercontent.com";
var GOOGLE_CLIENT_SECRET = "vQJtPVgD6E7HpDzFC7Y96k_Y";

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

passport.use(new GoogleStrategy({
  clientID: GOOGLE_CLIENT_ID,
  clientSecret: GOOGLE_CLIENT_SECRET,
  callbackURL: "https://nourritureapi.herokuapp.com/auth/google/callback"
},
function(accessToken, refreshToken, profile, done) {
    // asynchronous verification, for effect...
    process.nextTick(function () {

      // To keep the example simple, the user's Google profile is returned to
      // represent the logged-in user.  In a typical application, you would want
      // to associate the Google account with a user record in your database,
      // and return that user instead.
      return done(null, profile);
    });
  }
  ));


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


  app.use(function(req,res,next){
    req.db = db;
    next();
  });

  app.get('/', function(req, res){
    res.render('index', { user: req.user });
  });

  app.get('/account', ensureAuthenticated, function(req, res){
    res.render('account', { user: req.user });
  });

  app.get('/login', function(req, res){
    res.render('login', { user: req.user });
  });


// GET /auth/google
//   Use passport.authenticate() as route middleware to authenticate the
//   request.  The first step in Google authentication will involve
//   redirecting the user to google.com.  After authorization, Google
//   will redirect the user back to this application at /auth/google/callback
app.get('/auth/google',
        passport.authenticate('google', { scope: ['https://www.googleapis.com/auth/plus.login'] }),
        function(req, res){
    // The request will be redirected to Google for authentication, so this
    // function will not be called.
  });

// GET /auth/google/callback
//   Use passport.authenticate() as route middleware to authenticate the
//   request.  If authentication fails, the user will be redirected back to the
//   login page.  Otherwise, the primary route function function will be called,
//   which, in this example, will redirect the user to the home page.
app.get('/auth/google/callback',
        passport.authenticate('google', { successRedirect: '/success',
                              failureRedirect: '/login' }),
        function(req, res) {
          res.redirect('/');
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


// ************************************ INGREDIENTS ************************************

// List all ingredients
app.get('/listIngredients', function(req, res) {
  var db = req.db;
  var collection = db.get('ingredients');

  collection.find({},{},function(e,docs){
    console.log(docs);
    res.end(JSON.stringify(docs));
  });
});

// Add ingredients
app.post('/addIngredients', function (req, res) {
  var info = {'calories' : req.body.calories};
  var db = req.db;
  var collection = db.get('ingredients');
  var obj = {};

  collection.insert(req.body, function(err, result){
    res.send(
             (err === null) ? { msg: 'Success' } : { msg: err }
             );
  });
});

// Show ingredients
app.get('/showIngredients/:id', function (req, res) {
  var db = req.db;
  var collection = db.get('ingredients');

  collection.find({"_id" : req.params.id},{},function(e,docs){
    res.end(JSON.stringify(docs));
  });
});

// Delete ingredients
app.get('/deleteIngredients/:id', function (req, res) {
  var db = req.db;
  var collection = db.get('ingredients');

  collection.remove({"_id" : req.params.id},{},function(e,docs){
    res.end(JSON.stringify(docs));
  });
});

// ************************************ RECIPES ************************************

// List all Recipes
app.get('/listRecipes', function(req, res) {
  var db = req.db;
  var collection = db.get('recipes');

  collection.find({},{},function(e,docs){
    res.end(JSON.stringify(docs));
  });
});

// Add Recipes
app.post('/addRecipes', function (req, res) {
  var db = req.db;
  var collection = db.get('recipes');
  var obj = {};

  collection.insert(req.body, function(err, result){
    res.send(
             (err === null) ? { msg: '' } : { msg: err }
             );
  });
});

// Show Recipes
app.get('/showRecipes/:id', function (req, res) {
  var db = req.db;
  var collection = db.get('recipes');

  collection.find({"_id" : req.params.id},{},function(e,docs){
    res.end(JSON.stringify(docs));
  });
});

// Delete Recipes
app.get('/deleteRecipes/:id', function (req, res) {
  var db = req.db;
  var collection = db.get('recipes');

  collection.remove({"_id" : req.params.id},{},function(e,docs){
    res.end(JSON.stringify(docs));
  });
});

app.get('/form_add_ingredients', function(req, res, next) {
  res.render('form_add_ingredients');
});

// ************************************ PRODUCTS ************************************

// List all products
app.get('/listProducts', function(req, res) {
  var db = req.db;
  var collection = db.get('products');

  collection.find({},{},function(e,docs){
    res.end(JSON.stringify(docs));
  });
});

// Add Products
app.post('/addProducts', function (req, res) {
  var db = req.db;
  var collection = db.get('products');
  var obj = {};

  collection.insert(req.body, function(err, result){
    res.send(
             (err === null) ? { msg: '' } : { msg: err }
             );
  });
});

// Show products
app.get('/showProducts/:id', function (req, res) {
  var db = req.db;
  var collection = db.get('products');

  collection.find({"_id" : req.params.id},{},function(e,docs){
    res.end(JSON.stringify(docs));
  });
});

// Delete Recipes
app.get('/deleteProducts/:id', function (req, res) {
  var db = req.db;
  var collection = db.get('products');

  collection.remove({"_id" : req.params.id},{},function(e,docs){
    res.end(JSON.stringify(docs));
  });
});

// ********************************** ALLERGENS **************************************
// List all products
app.get('/listAllergens', function(req, res) {
  var db = req.db;
  var collection = db.get('allergens');

  collection.find({},{},function(e,docs){
    res.end(JSON.stringify(docs));
  });
});

// Add Products
app.post('/addAllergens', function (req, res) {
  var db = req.db;
  var collection = db.get('allergens');
  var obj = {};

  collection.insert(req.body, function(err, result){
    res.send(
             (err === null) ? { msg: '' } : { msg: err }
             );
  });
});

// Show products
app.get('/showAllergens/:id', function (req, res) {
  var db = req.db;
  var collection = db.get('allergens');

  collection.find({"_id" : req.params.id},{},function(e,docs){
    res.end(JSON.stringify(docs));
  });
});

// Delete Recipes
app.get('/deleteAllegens/:id', function (req, res) {
  var db = req.db;
  var collection = db.get('allergens');

  collection.remove({"_id" : req.params.id},{},function(e,docs){
    res.end(JSON.stringify(docs));
  });
});

// ********************************** USERS **************************************
// List all products
app.get('/listUsers', function(req, res) {
  var db = req.db;
  var collection = db.get('users');

  collection.find({},{},function(e,docs){
    res.end(JSON.stringify(docs));
  });
});

// Add Products
app.post('/addUsers', function (req, res) {
  var db = req.db;
  var collection = db.get('users');
  var obj = {};

  collection.insert(req.body, function(err, result){
    res.send(
             (err === null) ? { msg: '' } : { msg: err }
             );
  });
});

// Show products
app.get('/showUsers/:id', function (req, res) {
  var db = req.db;
  var collection = db.get('users');

  collection.find({"_id" : req.params.id},{},function(e,docs){
    res.end(JSON.stringify(docs));
  });
});

// Delete Recipes
app.get('/deleteUsers/:id', function (req, res) {
  var db = req.db;
  var collection = db.get('users');

  collection.remove({"_id" : req.params.id},{},function(e,docs){
    res.end(JSON.stringify(docs));
  });
});

// ********************************** WEBSITE MAIN FEATURE **************************************

app.post('/search', function (req, res, next) {
  var db = req.db;
  var collection = db.get('recipes');
  var search = req.body.search;
  var result = [];

  console.log(search);
  collection.find({'name' : new RegExp(search)}, {}, function (e, docs) {
    res.end(JSON.stringify(docs));
  });
});

app.post('/ensureAuth', function (req, res, next) {
  var db = req.db;
  var collection = db.get('users');
  var session;

  collection.find({'username' : req.body.username, 'password' : req.body.password},
                  {},
                  function (e, docs) {
                    res.send(docs);
                  });
});

app.get('/getAlternativeProducts', function (req, res) {
  var sess;
  var db = req.db;
  var collection = db.get('products');

  sess = req.session;
  if (sess) {
  }
  else {

  }
  collection.find({"_id" : req.params.id},{},function(e,docs){
    res.end(JSON.stringify(docs));
  });
});

app.post('/getSuitability', function(req, res, next){
  var db = req.db;
  var product = db.get('products');
  var obj = {};

  if (req.body._user && req.body._id) {
    var user = req.body._user;
    var allergens = user[0]['allergens'];

    product.find({'_id' : req.body._id}, {}, function (err, docs) {
      var allergens_product = [];
      var _is_allergens = [];

      for (var i = 0; i < docs[0]['allergens'].length; i++) {
        allergens_product.push(docs[0]['allergens'][i]['name']);
      };

      for (var i = 0; i < allergens.length; i++) {
        if (allergens_product.indexOf(allergens[i]) > -1) {
          console.log('in the array');
          _is_allergens.push(allergens[i]);
        }
      }
      res.send(_is_allergens);
    });
  }
});



module.exports = app;
