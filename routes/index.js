var express = require('express');
var router = express.Router();
var request = require('request');

/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'Express' });
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
    url: 'https://nourritureapi.herokuapp.com/search',
    method: 'POST',
    form: {
        search: req.body.q
    }
}, function (error, response, body) {
    if (!error && response.statusCode == 200) {
      var result = body;
      console.log("==============================BODY==============================")
      console.log(body);
    }
    res.send(body));
  });
});


module.exports = router;
