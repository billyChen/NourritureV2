var express = require('express');
var router = express.Router();
var app = express();
var request = require('request');

/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'Express' });
});

router.get('/all_ingredients', function(req, res, next) {
  var list = [];
  request('http://localhost:3000/api/listIngredients', function (error, response, body) {
    if (!error && response.statusCode == 200) {
      var result = body;

      ingredients = JSON.parse(result);
      for (var key in ingredients) {
        console.log(ingredients[key]);
        for (var obj in ingredients[key])
        {
          var informations = [ingredients[key][obj]]
          console.log(ingredients[key][obj]);
          list.push(informations);
        }
      }
    }
    res.render('all_ingredients', { "ingredients" : list });
  });
});

router.get('/ingredient/:id', function(req, res, next) {
  var id = req.params.id
  var address = 'http://localhost:3000/api/showIngredients/'.concat(id);
  request(address , function (error, response, body) {
    if (!error && response.statusCode == 200) {
      var ingredient = JSON.parse(body);
      console.log(ingredient);
    }
    res.render('ingredient', { "ingredient": ingredient});
  });
});

router.get('/delete_ingredient/:id', function(req, res, next) {
  var id = req.params.id
  var address = 'http://localhost:3000/api/deleteIngredients/:id/'.concat(id);
  request(address , function (error, response, body) {
    if (!error && response.statusCode == 200) {
      var ingredient = JSON.parse(body);
      console.log(ingredient);
    }
    res.render('ingredient', { "ingredient": ingredient});
  });
});


router.get('/form_add_ingredients', function(req, res, next) {
  var id = req.params.id;
  res.render('form_add_ingredients');
});

router.get('/add_ingredients', function(req, res, next) {
  var id = req.params.id;
  request("http://localhost:3000/api/addIngredients" , function (error, response, body) {
    if (!error && response.statusCode == 200) {
      var result = body;
      ingredient = JSON.parse(result);
    }
    res.render('form_add_ingredients');
  });
});

router.get('/recipes', function(req, res) {
  var db = req.db;
  var collection = db.get('recettes');
  collection.find({},{},function(e,docs){
    res.render('recipes', {
      "recipes" : docs
    });
  });
});

router.get('/add_recipes', function(req, res) {
  res.render('form_add_recipes');
});

module.exports = router;
