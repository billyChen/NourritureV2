var express = require('express');
var router = express.Router();

/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'Express' });
});

/* GET nourriture page. */
router.get('/nourriture', function(req, res, next){
	res.render('nourriture', { title: 'Nourriture' });
});

module.exports = router;
