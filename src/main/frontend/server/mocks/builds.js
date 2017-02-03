/*jshint node:true*/
module.exports = function(app) {
  var express = require('express');
  var buildsRouter = express.Router();
  var bodyParser = require('body-parser');
  var idCounter = 14;

  var BUILDS = [
      {
          "type" : "builds",
          "id" : "1",
          "attributes" : {
              "number" : "EMA_328",
              "description" : "Build #10"
          },
          "relationships" : {
            "release" : {"data": {"type":"releases", "id": "4"}},
            "issues" : {"data": [
                {"type": "issues", "id": "1"},
                {"type": "issues", "id": "2"}
              ]
            }
          }
      },{
          "type" : "builds",
          "id" : "2",
          "attributes" : {
              "number" : "EMA_150",
              "description" : "Build #9"
          },
          "relationships" : {
               "release" : {"data": {"type":"releases", "id": "4"}},
               "issues" : {
                            "data": [
                               {"type": "issues", "id": "3"},
                               {"type": "issues", "id": "1"}
                             ]
                          }
          }
      },{
          "type" : "builds",
          "id" : "3",
          "attributes" : {
              "number" : "EMA_143",
              "description" : "Build #8"

          },
          "relationships" : {
               "release" : {"data": {"type":"releases", "id": "4"}},
               "issues" : {
                             "data": [
                                {"type": "issues", "id": "2"},
                                {"type": "issues", "id": "5"}
                              ]
                           }
          }
      },{
          "type" : "builds",
          "id" : "4",
          "attributes" : {
              "number" : "M322",
              "description" : "Build #1",
              "release" : "3"
          },
          "relationships" : {
                 "release" : {"data": {"type":"releases", "id": "3"}},
                 "issues" : {
                               "data": [
                                  {"type": "issues", "id": "3"},
                                  {"type": "issues", "id": "9"}
                                ]
                             }
          }
      },{
         "type" : "builds",
         "id" : "5",
         "attributes" : {
             "number" : "M137",
             "description" : "Build #2"
         },
         "relationships" : {
                "release" : {"data": {"type":"releases", "id": "3"}},
                "issues" : {
                              "data": [
                                 {"type": "issues", "id": "8"},
                                 {"type": "issues", "id": "10"}
                               ]
                            }
         }
     },{
       "type" : "builds",
       "id" : "6",
       "attributes" : {
           "number" : "M212",
           "description" : "Build #14"
       },
       "relationships" : {
          "release" : {"data": {"type":"releases", "id": "6"}},
          "issues" : {
                        "data": [
                           {"type": "issues", "id": "7"},
                           {"type": "issues", "id": "4"}
                         ]
                      }
       }
     },{
       "type" : "builds",
       "id" : "7",
       "attributes" : {
           "number" : "M7 - Betscanner",
           "description" : "Build #13"
       },
       "relationships" : {
           "release" : {"data": {"type":"releases", "id": "6"}},
           "issues" : {
                         "data": [
                            {"type": "issues", "id": "6"},
                            {"type": "issues", "id": "2"}
                          ]
                       }
       }
     },{
       "type" : "builds",
       "id" : "8",
       "attributes" : {
           "number" : "M5 - Betscanner",
           "description" : "Build #12"
       },

       "relationships" : {
            "release" : {"data": {"type":"releases", "id": "6"}},
            "issues" : {
                          "data": [
                             {"type": "issues", "id": "10"},
                             {"type": "issues", "id": "9"}
                           ]
                        }
       }
     },{
       "type" : "builds",
       "id" : "9",
       "attributes" : {
           "number" : "D238",
           "description" : "Build #5",
           "release" : "5"
       },
       "relationships" : {
            "release" : {"data": {"type":"releases", "id": "5"}},
            "issues" : {
                          "data": [
                             {"type": "issues", "id": "8"},
                             {"type": "issues", "id": "4"}
                           ]
                        }
       }
     },{
       "type" : "builds",
       "id" : "10",
       "attributes" : {
           "number" : "D311",
           "description" : "Build #4"
       },
       "relationships" : {
            "release" : {"data": {"type":"releases", "id": "5"}},
            "issues" : {
                          "data": [
                             {"type": "issues", "id": "7"},
                             {"type": "issues", "id": "3"}
                           ]
                        }
       }
     },{
      "type" : "builds",
      "id" : "11",
      "attributes" : {
          "number" : "EMA_153",
          "description" : "Build #3"
      },
      "relationships" : {
              "release" : {"data": {"type":"releases", "id": "1"}},
              "issues" : {
                            "data": [
                               {"type": "issues", "id": "2"},
                               {"type": "issues", "id": "4"}
                             ]
                          }
      }
     },{
       "type" : "builds",
       "id" : "12",
       "attributes" : {
           "number" : "EMA_144",
           "description" : "Build #5"
       },
       "relationships" : {
           "release" : {"data": {"type":"releases", "id": "1"}},
           "issues" : {
                         "data": [
                            {"type": "issues", "id": "5"},
                            {"type": "issues", "id": "10"}
                          ]
                       }
       }
    },{
    "type" : "builds",
    "id" : "13",
    "attributes" : {
        "number" : "EMA_142",
        "description" : "Build #3"
    },

    "relationships" : {
         "release" : {"data": {"type":"releases", "id": "1"}},
         "issues" : {
                       "data": [
                          {"type": "issues", "id": "5"},
                          {"type": "issues", "id": "9"}
                        ]
                     }
    }
  },{
     "type" : "builds",
     "id" : "14",
     "attributes" : {
         "number" : "EMA_137",
         "description" : "Build #2"
     },

     "relationships" : {
          "release" : {"data": {"type":"releases", "id": "1"}},
          "issues" : {
                        "data": [
                           {"type": "issues", "id": "2"},
                           {"type": "issues", "id": "8"}
                         ]
                      }
     }
  },{
         "type" : "builds",
         "id" : "15",
         "attributes" : {
             "number" : "EMA_139",
             "description" : "Build #4"
         },

         "relationships" : {
              "release" : {"data": null},
              "issues" : {"data": null}
         }
  }
  ];


  buildsRouter.get('/', function(req, res) {
    res.setHeader('Content-Type','application/vnd.api+json');
    res.send({'data': BUILDS });
  });

  buildsRouter.post('/', function(req, res) {
    res.setHeader('Content-Type','application/vnd.api+json');
    idCounter++;
    req.body.data.id = idCounter;
    BUILDS[idCounter -1 ] = req.body.data;
    res.status(201).send(req.body.data);
  });

  buildsRouter.get('/:id', function(req, res) {
    res.setHeader('Content-Type','application/vnd.api+json');
    res.send({'data':  BUILDS[req.params.id -1]});
  });

  buildsRouter.put('/:id', function(req, res) {
    res.setHeader('Content-Type','application/vnd.api+json');
    BUILDS[req.params.id - 1] = req.body.data;
    res.send({'data': req.body.data});
  });

  buildsRouter.patch('/:id', function(req, res) {
      res.setHeader('Content-Type','application/vnd.api+json');
      BUILDS[req.params.id - 1] = req.body.data;
      res.send({'data': req.body.data});
  });

  buildsRouter.delete('/:id', function(req, res) {
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
  //app.use('/api/builds', require('body-parser').json());
  app.use('/api/builds', bodyParser.json({ type: 'application/*+json' }), buildsRouter);
};
