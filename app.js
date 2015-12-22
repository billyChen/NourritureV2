var express = require('express')
, passport = require('passport')
, util = require('util')
, FacebookStrategy = require('passport-facebook').Strategy
, FacebookTokenStrategy = require('passport-facebook-token')
, GoogleStrategy = require('passport-google-oauth').OAuth2Strategy
, logger = require('morgan')
, session = require('express-session')
, bodyParser = require("body-parser")
, cookieParser = require("cookie-parser")
, methodOverride = require('method-override');

var mongo = require('mongodb');
var monk = require('monk');
var db = monk('mongodb://root:123456@ds049624.mongolab.com:49624/nourriture');
var request = require('request');

var FACEBOOK_APP_ID = "322980324492560";
var FACEBOOK_APP_SECRET = "745cc0ed81f3de714e42d6fd086abff5";
var GOOGLE_CLIENT_ID = "961840791432-kmmtn60o69622kgl2gsdia8d3kpdc6j4.apps.googleusercontent.com";
var GOOGLE_CLIENT_SECRET = "vQJtPVgD6E7HpDzFC7Y96k_Y";
var removeDiacritics = require('diacritics').remove;

// *************************************** PASSPORT **************************************************
passport.serializeUser(function(user, done) {
  done(null, user);
});

passport.deserializeUser(function(obj, done) {
  done(null, obj);
});

passport.use(new FacebookTokenStrategy({
  clientID: FACEBOOK_APP_ID,
  clientSecret: FACEBOOK_APP_SECRET
}, function(accessToken, refreshToken, profile, done) {
  User.findOrCreate({facebookId: profile.id}, function (error, user) {
    return done(error, user);
  });
}
));

passport.use(new GoogleStrategy({
  clientID: GOOGLE_CLIENT_ID,
  clientSecret: GOOGLE_CLIENT_SECRET,
  callbackURL: "https://nourritureapi.herokuapp.com/auth/google/callback"
},
function(accessToken, refreshToken, profile, done) {
    // asynchronous verification, for effect...
    process.nextTick(function () {
      var collection = db.get('users');

      collection.find({'profile.id': profile.id}, {}, function (e, user)
      {
        if (isEmpty(user) === false)
        {
          return done(null, user);
        }
        else {
         request.post(
         {
          url: 'http://nourritureapi.herokuapp.com/addUsers',
          method: 'POST',
          form:
          {
            _access_token: accessToken,
            profile: profile
          }
        },
        function (error, response, body)
        {
          return done(null, profile);
        });

       }
     });
    });
  }
  ));

passport.use(new FacebookStrategy({
  clientID: FACEBOOK_APP_ID,
  clientSecret: FACEBOOK_APP_SECRET,
  callbackURL: "https://nourritureapi.herokuapp.com/auth/facebook/callback"
},
function(accessToken, refreshToken, profile, done) {
  process.nextTick(function () {
    var collection = db.get('users');

    collection.find({'_facebook_id': profile.id}, {}, function (e, user)
    {
      if (user)
      {
        return done(null, user);
      }
      else
      {

       request.post(
       {
        url: 'http://nourritureapi.herokuapp.com/addUsers',
        method: 'POST',
        form:
        {
          _access_token: accessToken,
          profile: profile,
        }
      },
      function (error, response, body)
      {
        return done(null, profile);
      });

     }
   });
  });
}
));
var app = express();

app.set('views', __dirname + '/views');
app.set('view engine', 'ejs');
app.use(logger());
app.use(cookieParser());
app.use(bodyParser());
app.use(methodOverride());
app.use(session({ secret: 'keyboard cat' }));
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

app.post('/auth/facebook/token',
         passport.authenticate('facebook-token'),
         function (req, res) {
    // do something with req.user
    res.send(req.user? 200 : 401);
  }
  );

app.get('/auth/google',
        passport.authenticate('google', { scope: ['https://www.googleapis.com/auth/plus.login'] }),
        function(req, res){
        });

app.get('/auth/google/callback',
        passport.authenticate('google', { successRedirect: '/success',
                              failureRedirect: '/login' }),
        function(req, res) {
          res.redirect('/');
        });


app.get('/auth/facebook',
        passport.authenticate('facebook'),
        function(req, res){
        });

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

function ensureAuthenticated(req, res, next) {
  if (req.isAuthenticated()) { return next(); }
  res.redirect('/login')
}

function isEmpty(obj) {
  for (var prop in obj) {
    if (obj.hasOwnProperty(prop))
      return false;
  }

  return true;
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

// Update Recipes
app.post('/updateRecipes', function (req, res) {
  var db = req.db;
  var collection = db.get('recipes');
  var obj = {};
  var _id = req.body.id;

  delete req.body.id
  collection.update(_id, req.body, function(err, result){
    res.send(
             (err === null) ? { msg: 'Update complete !' } : { msg: err }
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

app.post('/showProducts', function (req, res) {
  var db = req.db;
  var collection = db.get('products');
  var ingredients = req.body.ingredients;
  var JSON_ingredients = [];

  if (typeof ingredients === 'string' ) {
    ingredients = ingredients.split();
  }
  for (var i = 0; i < ingredients.length; i++) {
    var json_obj = {};

    json_obj['name'] = ingredients[i];
    JSON_ingredients.push(json_obj);
  };

  collection.find({"ingredients" : { $all: JSON_ingredients}},{},function(e,docs){
    res.end(JSON.stringify(docs));
  });
});


app.get('/showProductsByName/:name', function (req, res) {
  var db = req.db;
  var collection = db.get('products');
  var name = req.params.name;
  name = name.toLowerCase();
  collection.find({"name" : name },{},function(e,docs){
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

app.post('/advancedSearchRecipes', function (req, res, next) {
  var db = req.db;
  var collection = db.get('recipes');
  var result = [];
  var ingredients = req.body.ingredients;
  var JSON_ingredients = [];

  if (typeof ingredients === 'string' ) {
    ingredients = ingredients.split();
  }
  if (typeof ingredients === 'undefined') {
    ingredients = [];
  }
  for (var i = 0; i < ingredients.length; i++) {
    JSON_ingredients.push(ingredients[i]);
  };

  collection.find({ $and: [{ "country": req.body.country},
                  {"cost": {$gte: parseInt(req.body.cost1), $lte: parseInt(req.body.cost2)} },
                  {'name' : new RegExp(req.body.name)},
                  {"calories": {$gte: parseInt(req.body.calories1), $lte: parseInt(req.body.calories2)}},
                  {"ingredients.name" : { $all: JSON_ingredients}}
                  ]
                }, {}, function (e, docs) {
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

app.post('/getAlternativeProducts', function (req, res) {
  var sess;
  var db = req.db;
  var collection = db.get('products');

  if (req.body._user) {
    var user = req.body._user;
    var preferences = user[0]['preferences'];
    var random_preference = preferences[Math.floor(Math.random() * preferences.length)];

    collection.find({'types' : random_preference}, {}, function (err, docs) {
      res.send(docs);
    });
  }
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
