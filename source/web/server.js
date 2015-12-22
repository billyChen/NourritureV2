var express = require('express');
var app = express();
var path = require('path');

/*var bodyParser = require('body-parser');
var morgan = require('morgan');
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

app.use(function(req, res, next) {
	res.setHeader('Access-Control-Allow-Origin', '*');
	res.setHeader('Access-Control-Allow-Methods', 'GET, POST');
	res.setHeader('Access-Control-Allow-Headers', 'X-Requested-With,content-type, Authorization');
	next();
});*/



// set the public folder to serve public assets
app.use(express.static(__dirname + '/public'));
//app.use(express.static(__dirname + '/views'));

// set up our one route to the index.html file
app.get('*', function(req, res) {
	res.sendFile(path.join(__dirname + '/public/views/index.html'));
	//res.sendFile(path.join(__dirname + '/views/index.html'));
});

// start the server on port 8080 (http://localhost:8080)
//app.listen(config.port);
app.listen(5000);
console.log('Magic happens on port 5000.');



































/*var express = require('express');
var app = express();
var bodyParser = require('body-parser');
var morgan = require('morgan');
var mongoose = require('mongoose');
var config = require('./config');
var path = require('path');

// APP CONFIGURATION ==================
// ====================================
// use body parser so we can grab information from POST requests
app.use(bodyParser.urlencoded({ extended : true }));
app.use(bodyParser.json());

// configure our app to handle CORS requests
app.use(function(req, res, next) {
	res.setHeader('Access-Control-Allow-Origin', '*');
	res.setHeader('Access-Control-Allow-Methods', 'GET, POST');
	res.setHeader('Access-Control-Allow-Headers', 'X-Requested-With,content-type, Authorization');
	next();
});

// log all requests to the console
app.use(morgan('dev'));

// connect to our database (hosted on modulus.io)
mongoose.connect(config.database);

// set static files location
// used for requests that our frontend will make
app.use(express.static(__dirname + '/public'));


// ROUTES FOR OUR API =================
// ====================================

// API ROUTES ------------------------
//var apiRoutes = require('./app/routes/api')(app, express);
var apiRoutes = require('../api/nourriture/app')(app, express);
app.use('/api', apiRoutes);

// MAIN CATCHALL ROUTE ---------------
// SEND USERS TO FRONTEND ------------
// has to be registered after API ROUTES
/*app.get('*', function(req, res) {
res.sendFile(path.join(__dirname + '/public/app/views/index.html'));
});*

app.get('*', function(req, res) {
	res.sendFile(path.join(__dirname + '/public/views/index.html'));
	//res.sendFile(path.join(__dirname + '/views/index.html'));
});

// START THE SERVER
// ====================================
app.listen(config.port);
console.log('Magic happens on port ' + config.port);
*/


