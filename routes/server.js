var express = require('express');
var router = express.Router();
var fs = require("fs");
var bodyParser = require('body-parser');

router.use(bodyParser.urlencoded({extended: false}));
router.use(bodyParser.json());


// ************************************ INGREDIENTS ************************************

// List all ingredients
router.get('/listIngredients', function(req, res) {
  var db = req.db;
  var collection = db.get('ingredients');

  collection.find({},{},function(e,docs){
    res.end(JSON.stringify(docs));
  });
});

// Add ingredients
router.post('/addIngredients', function (req, res) {
  var info = {'calories' : req.body.calories};
  var db = req.db;
  var collection = db.get('ingredients');
  var obj = {};

  obj["ingredient"] = {'name': req.body.name, 'calories' : req.body.calories};

  var _toInsert = obj;
  console.log(JSON.stringify(obj));

  collection.insert(_toInsert, function (err, doc) {
    if (err) {
      res.send('An error occured when adding an ingredients, please try again...');
    }
    else {
      res.send('Sucess');
    }
  });
});

// Show ingredients
router.get('/showIngredients/:id', function (req, res) {
  var db = req.db;
  var collection = db.get('ingredients');

  collection.find({"_id" : req.params.id},{},function(e,docs){
    res.end(JSON.stringify(docs));
  });
});

// Delete ingredients
router.get('/deleteIngredients/:id', function (req, res) {
  var db = req.db;
  var collection = db.get('ingredients');

  collection.remove({"_id" : req.params.id},{},function(e,docs){
    res.end(JSON.stringify(docs));
  });
});

// ************************************ RECIPES ************************************

// List all Recipes
router.get('/listRecipes', function(req, res) {
  var db = req.db;
  var collection = db.get('recipes');

  collection.find({},{},function(e,docs){
    res.end(JSON.stringify(docs));
  });
});

// Add Recipes
router.post('/addRecipes', function (req, res) {
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
router.get('/showRecipes/:id', function (req, res) {
  var db = req.db;
  var collection = db.get('recipes');

  collection.find({"_id" : req.params.id},{},function(e,docs){
    res.end(JSON.stringify(docs));
  });
});

// Delete Recipes
router.get('/deleteRecipes/:id', function (req, res) {
  var db = req.db;
  var collection = db.get('recipes');

  collection.remove({"_id" : req.params.id},{},function(e,docs){
    res.end(JSON.stringify(docs));
  });
});

module.exports = router;
