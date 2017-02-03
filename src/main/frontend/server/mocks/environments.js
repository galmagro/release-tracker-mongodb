/*jshint node:true*/
module.exports = function(app) {
  var express = require('express');
  var idCounter = 6;
  var bodyParser = require('body-parser');
  var environmentsRouter = express.Router();

  var ENVIRONMENTS = [
      {
           "type": "environments",
           "id": "1",
           "attributes": {
             "name": "PROD MOBILE",
             "description": "Production environment for Mobile sportsbook. Use with caution!",
             "url": "https://m.ladbrokes.com"
           }
         },{
             "type": "environments",
             "id": "2",
             "attributes": {
               "name": "PROD DESKTOP",
               "description": "Production environment for Desktop sportsbook. Use with caution!",
               "url": "https://sports.ladbrokes.com",
             }
           },{
            "type": "environments",
            "id": "3",
            "attributes": {
              "name": "PREPROD MOBILE",
              "description": "General QA / Signoff environment",
              "url": "https://preprod-m.ladbrokes.com",
            }
          }, {
            "type": "environments",
            "id": "4",
            "attributes": {
              "name": "DR MOBILE",
              "description": "Disaster recovery environment",
              "url": "https://preprod-m.ladbrokes.com",
            }
          }, {
              "type": "environments",
              "id": "5",
              "attributes": {
                "name": "PERF DESKTOP",
                "description": "Performance testing environment",
                "url": "https://perf-d.ladbrokes.com",
              }
          }, {
             "type": "environments",
             "id": "6",
             "attributes": {
               "name": "STAGE MOBILE",
               "description": "Testing environment",
               "url": "https://m-ladbrokes-staging.mobenga.com",
             }
         }


  ];

  environmentsRouter.get('/', function(req, res) {
    res.setHeader('Content-Type','application/vnd.api+json');
    res.send({
       "data": ENVIRONMENTS
    });
  });


  function sendError(res){
      res.setHeader('Content-Type','application/vnd.api+json');
      res.status(422).send({"errors": [{
           "status" : "422",
           "source": { "pointer": "data/attributes/name" },
           "title":  null,
           "detail": "Environment name is repeated"
        }
      ]});
  }

  environmentsRouter.post('/', function(req, res) {
    console.log("new environment ---------" + req.body.data.name);
    if(req.body.data.attributes.name == 'ERROR TEST'){
        console.log("is an error");
        sendError(res);
        return;
    }

    idCounter++;
    req.body.data.id = idCounter;
    ENVIRONMENTS[idCounter-1] = req.body.data;
    res.setHeader('Content-Type','application/vnd.api+json');
    res.status(201).send({"data": req.body.data});
  });

  environmentsRouter.get('/:id', function(req, res) {
    res.setHeader('Content-Type','application/vnd.api+json');
    res.send({
      'data': ENVIRONMENTS[req.params.id]
    });
  });

  environmentsRouter.put('/:id', function(req, res) {
    res.setHeader('Content-Type','application/vnd.api+json');
    ENVIRONMENTS[req.params.id] = req.body.data;
    res.send(req.body);
  });

  environmentsRouter.patch('/:id', function(req, res) {
      res.setHeader('Content-Type','application/vnd.api+json');
      ENVIRONMENTS[req.params.id] = req.body.data;
      res.send(req.body);
    });

  environmentsRouter.delete('/:id', function(req, res) {
    res.status(204).end();
  });

  // The POST and PUT call will not contain a request body
  // because the body-parser is not included by default.
  // To use req.body, run:

  //    npm install --save-dev body-parser

  // After installing, you need to `use` the body-parser for
  // this mock uncommenting the following line:
  //
  //app.use('/api/environments', require('body-parser').json());
  app.use('/api/environments', bodyParser.json({ type: 'application/*+json' }), environmentsRouter);
};
