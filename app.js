var express = require('express');
var mongo = require('mongodb');
var monk = require('monk');
var db = monk('localhost:27017/ingredients');

var app = express();

app.use(function(req,res,next){
    req.db = db;
    next();
});

app.get('/', function (req, res) {
  res.render('home.ejs')
});

app.get('/test', function (req, res) {
  res.send('test')
});

router.get('/userlist', function(req, res) {
    var db = req.db;
    var collection = db.get('usercollection');
    collection.find({},{},function(e,docs){
        res.render('userlist', {
            "userlist" : docs
        });
    });
});

app.listen(process.env.PORT || 5000);

module.exports = app;
