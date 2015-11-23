var express = require('express');
var router = express.Router();
var request = require('request');

/* GET home page. */
router.get('/', function(req, res, next) {
  var sess = req.session;
  res.render('index', { title: 'Home', sess: sess });
});

/* GET nourriture page. */
router.get('/nourriture', function(req, res, next){
	res.render('nourriture', { title: 'Nourriture' });
});

router.get('/result', function (req, res) {
  res.render('search_result');
});

router.post('/query_search', function (req, res) {
  request.post({
    url: 'http://nourritureapi.herokuapp.com/search',
    method: 'POST',
    form: {
      search: req.body.q
    }
  }, function (error, response, body) {
    if (!error && response.statusCode == 200) {
      var result = body;
    }
    res.send(body);
  });
});

/* GET connection page. */
router.get('/welcome', function(req, res, next){
	res.render('welcome', { title: 'bienvenu' });
});

router.get('/addIngredients', function(req, res, next){
	res.render('addIngredients', { title: 'Add your ingredients !!!' });
});

router.get('/addRecipe', function(req, res, next){
	res.render('addRecipe', { title: 'Add your recipe !!!' });
});

router.get('/logIn', function(req, res, next) {
	res.render('logIn', { title: 'LogIn'});
});

router.post('/insertUserData', function(req, res, next){
  var db = req.db;
  var collection = db.get('users');
  var obj = {};

  collection.insert(req.body, function(err, result){
    res.render('inscriptionSuccess',
               (err === null) ? { msg: 'Success' } : { msg: err }
               );
  });
});

router.get('/getSuitability/:id', function(req, res, next){
  if (req.session['user']) {
    request.post({
      url: 'http://nourritureapi.herokuapp.com/getSuitability',
      method: 'POST',
      form: {
        _user: req.session['user'],
        _id: req.params.id
      }
    }, function (error, response, body) {
      if (!error && response.statusCode == 200) {
        var result = body;
      }
      res.send(body);
    });
  }
});

module.exports = router;
