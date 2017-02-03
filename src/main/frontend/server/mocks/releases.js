/*jshint node:true*/
module.exports = function(app) {
  var express = require('express');
  var idCounter = 6;
  var bodyParser = require('body-parser');
  var releasesRouter = express.Router();

  var RELEASES = [{
            "type": "releases",
            "id": "1",
            "attributes": {
                "name":"EMA + InAppGaming - Desktop",
                "description":"Edit My ACCA + Inapp gaming"
            },
            "relationships": {
                "builds" : {
                  "data": [
                      {"type" : "builds", "id": "14"},
                      {"type" : "builds", "id": "13"},
                      {"type" : "builds", "id": "12"}
                      ]
                }
            }
          },{
            "type": "releases",
            "id": "2",
            "attributes": {
                "name":"Sprint 18 - Desktop",
                "description":"Sprint 18 Release for Desktop",

            },
            "relationships": {}
          },{
            "type": "releases",
            "id": "3",
            "attributes": {
              "name":"Sprint 18 - Mobile",
              "description":"Sprint 18 for Mobile"
            },
            "relationships": {
                "builds" : {
                    "data": [
                        {"type" : "builds", "id": "4"},
                        {"type" : "builds", "id": "5"},
                        {"type" : "builds", "id": "12"}
                        ]
                  }
            }
          },{
            "type": "releases",
            "id": "4",
            "attributes": {
               "name":"EMA-Desktop",
               "description":"Desktop Edit My ACCA patch release",
               "builds" : []
            },
            "relationships": {
            "builds" : {
                  "data": [
                      {"type" : "builds", "id": "1"},
                      {"type" : "builds", "id": "2"},
                      {"type" : "builds", "id": "3"}
                      ]
                }
            }
          },{
            "type": "releases",
            "id": "5",
            "attributes": {
                "name":"S17 Euro Desktop + InAppGaming",
                "description":"Sprint 17 Euros desktop release with In app gaming features"
            },
            "relationships": {
                "builds" : {
                  "data": [
                      {"type" : "builds", "id": "9"},
                      {"type" : "builds", "id": "10"}
                      ]
                }
            }
          },{
            "type": "releases",
            "id": "6",
            "attributes": {
                "name":"Sprint 17 Euros Mobile",
                "description":"Sprint 17 Euros mobile release"

            },
            "relationships": {
                "builds" : {
                  "data": [
                      {"type" : "builds", "id": "6"},
                      {"type" : "builds", "id": "7"},
                      {"type" : "builds", "id": "8"}
                      ]
                }
            }
          }];


  releasesRouter.get('/', function(req, res) {
    res.setHeader('Content-Type','application/vnd.api+json');
    res.send({ "data" : RELEASES });
  });

  releasesRouter.post('/', function(req, res) {
    res.setHeader('Content-Type','application/vnd.api+json');
    idCounter++;
    req.body.data.id = idCounter;
    RELEASES[idCounter - 1] = req.body.data;
    res.status(201).send(req.body);
  });

  releasesRouter.get('/:id', function(req, res) {
    res.setHeader('Content-Type','application/vnd.api+json');
    res.send({"data" : RELEASES[req.params.id - 1]});
  });


  releasesRouter.put('/:id', function(req, res) {
    res.setHeader('Content-Type','application/vnd.api+json');
    RELEASES[req.params.id - 1] = req.body.data;
    res.send(req.body);
  });

  releasesRouter.patch('/:id', function(req, res) {
      res.setHeader('Content-Type','application/vnd.api+json');
      RELEASES[req.params.id - 1] = req.body.data;
      res.send(req.body);
    });

  releasesRouter.delete('/:id', function(req, res) {
    res.setHeader('Content-Type','application/vnd.api+json');
    res.status(204).end();
  });

  // The POST and PUT call will not contain a request body
  // because the body-parser is not included by default.
  // To use req.body, run:

  //    npm install --save-dev body-parser

  // After installing, you need to `use` the body-parser for
  // this mock uncommenting the following line:
  //
  //app.use('/api/releases', require('body-parser').json());
  app.use('/api/releases', bodyParser.json({ type: 'application/*+json' }), releasesRouter);
};
