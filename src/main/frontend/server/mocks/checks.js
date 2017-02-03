/*jshint node:true*/
module.exports = function(app) {
  var express = require('express');
  var checksRouter = express.Router();
  var bodyParser = require('body-parser');
  var DEPLOYMENTS = [
    {
      "type": "checks",
      "id" : "1",
      "attributes" : {
          "checked" : "2016-06-10T12:00:15.352+01:00",
          "error" : false
      },

      "relationships" : {
          "environment" : {"data" : {"type": "environments", "id": "1"}},
          "build" : {"data": {"type": "builds", "id": "8"}}
      }
    },{
      "type": "checks",
      "id" : "2",
      "attributes" : {
          "checked" : "2016-06-30T12:15:15.352+01:00",
          "error" : false
      },

      "relationships" : {
          "environment" : {"data" : {"type": "environments", "id": "2"}},
          "build" : {"data": {"type": "builds", "id": "9"}}
      }
    },{
      "type": "checks",
      "id" : "3",
      "attributes" : {
          "checked" : "2016-06-28T11:13:00.123+01:00",
          "error" : false
      },

      "relationships" : {
          "environment" : {"data" : {"type": "environments", "id": "3"}},
          "build" : {"data": {"type": "builds", "id": "4"}}
      }
    },{
      "type": "checks",
      "id" : "4",
      "attributes" : {
          "checked" : "2016-06-27T15:20:49.002+01:00",
          "error" : true
      },

      "relationships" : {
          "environment" : {"data" : {"type": "environments", "id": "4"}},
          "build" : {"data": null}
      }
    },{
      "type": "checks",
      "id" : "5",
      "attributes" : {
          "checked" : "2016-06-25T07:48:33.234+01:00",
          "error" : false
      },

      "relationships" : {
          "environment" : {"data" : {"type": "environments", "id": "5"}},
          "build" : {"data": {"type": "builds", "id": "1"}}
      }
    },{
      "type": "checks",
      "id" : "6",
      "attributes" : {
          "checked" : "2016-07-01T13:56:15.352+01:00",
          "error" : false
      },

      "relationships" : {
          "environment" : {"data" : {"type": "environments", "id": "6"}},
          "build" : {"data": {"type": "builds", "id": "15"}}
      }
    }


  ];


  checksRouter.get('/', function(req, res) {

    if(req.query.type == 'last_deploy' && req.query.environment !=null){
        var depl = null;
        switch (req.query.environment){
            case '1':
                depl = DEPLOYMENTS[0];
                break;
            case '2':
                depl = DEPLOYMENTS[1];
                break;
            case '3':
                 depl = DEPLOYMENTS[2];
                 break;
            case '4':
                 depl = DEPLOYMENTS[3];
                 break;
            case '5':
                 depl = DEPLOYMENTS[4];
                 break;
            case '6':
                depl = DEPLOYMENTS[5];
                break;

        }

        res.send({'data': depl});

    } else {
        res.send({'data': DEPLOYMENTS});
    }



  });

  checksRouter.post('/', function(req, res) {
    res.status(201).end();
  });

  checksRouter.get('/:id', function(req, res) {
    res.send({
      'checks': {
        id: req.params.id
      }
    });
  });

  checksRouter.put('/:id', function(req, res) {
    res.send({
      'checks': {
        id: req.params.id
      }
    });
  });

  checksRouter.delete('/:id', function(req, res) {
    res.status(204).end();
  });

  // The POST and PUT call will not contain a request body
  // because the body-parser is not included by default.
  // To use req.body, run:

  //    npm install --save-dev body-parser

  // After installing, you need to `use` the body-parser for
  // this mock uncommenting the following line:
  //
  //app.use('/api/checks', require('body-parser').json());
  app.use('/api/checks', bodyParser.json({ type: 'application/*+json' }), checksRouter);
};
