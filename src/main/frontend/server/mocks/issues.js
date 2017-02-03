/*jshint node:true*/
module.exports = function(app) {
  var express = require('express');
  var bodyParser = require('body-parser');
  var issuesRouter = express.Router();
  var idCounter = 10;

  var ISSUES = [
      {
          "id": 1,
          "type" : "issues",
          "attributes" : {
              "number": "SSP-9900",
              "title" : "EMA Desktop: On refreshing My accas after suspension of (EMA and CO) the UI distorts for a few seconds",
              "description" : "Steps: 1. Place an acca\n 2. Go to My accas\n 3. Suspend one of the Acca leg\n 4. Refresh the page or go back to same acca through My acca"
          }
      },{
          "id": 2,
          "type" : "issues",
          "attributes" : {
              "number": "SSP-9979",
              "title" : "EMA Desktop - after getting suspended - confirm button is getting green but not clickable",
              "description" : "Tested on preprod FF (testcory23 / Password1)\n 1. Placed an Acca 3 \n 2. Navigate to EMA \n3. Click on selection removal button 'X' \nActual: Selections are showing Susp for few seconds and comes back and then the Conform button is green but not clickable and the new potential returns are not shown \n Intermittent issue: yes \nPlease see attached video"
          }
      },{
        "id": 3,
        "type" : "issues",
        "attributes" : {
            "number": "SSP-9914",
            "title" : "EMA Destop - Preprop - intermittent - Clicking Cashout - leads to a distorted UI in betslip",
            "description" : "Tested on Preprod - Chrome and FF:1. Place an acca bet\n2. Go to My Accas via My bets\n3. Click on Cashout button\nActual: Be on the page few (~30) seconds before clicking Decline or Accept - leads to a distorted UI in betslip"
        }
      },{
        "id": 4,
        "type" : "issues",
        "attributes" : {
            "number": "SSP-9752",
            "title" : "Mobile - Cash Out 2.0 additions for Mobenga to parse in requests #mcashoutv2",
            "description" : "As pert of cashout 2.0 change , openbet introduced ladder to calculate the cashout value. Openbet need ladder information passed from Mobenga/EIS to calculate the cashout offer value. Mobenga will get cashout ladder and type from GetBetHistory and SearchBet and pass same information to GetCashoutDetails request.To make Cashout2.0 change backward compatible, Mobenga need to send and new data filter Cashout2 in GetBetHistoryRequest and SearchBetRequest as given in example. If the Cashout2 data filter is available, API/DX will return cashoutLadder and cashoutType in in GetBetHistoryResponse and SearchBetResponse."
        }
      },{
        "id": 5,
        "type" : "issues",
        "attributes" : {
            "number": "SSP-9839",
            "title" : "Update WSDL/XSD #mcashoutv2",
            "description" : ""
        }
      },{
        "id": 6,
        "type" : "issues",
        "attributes" : {
            "number": "SSP-9840",
            "title" : "Add new datafilter to GetBetHistoryRequest and SearchBetsRequest #mcashoutv2",
            "description" : ""
        }
      },{
        "id": 7,
        "type" : "issues",
        "attributes" : {
            "number": "SSP-9844",
            "title" : "create unitTest for new behaviour #mcashoutv2",
            "description" : ""
        }
      },{
        "id": 8,
        "type" : "issues",
        "attributes" : {
            "number": "SSP-9158",
            "title" : "Cashout Patch: 15 or more accumulator shows CO icon on bet receipt and betslip although Cashout is not offered  #mcashoutv2",
            "description" : "Steps:\n1. Place a >= 15 fold acca\n2. Check the betslip and bet receipt after placing a bet\nIssue: 15 or more accumulator shows CO icon on bet receipt and betslip although Cashout is not offered"
        }
      },{
        "id": 9,
        "type" : "issues",
        "attributes" : {
            "number": "SSP-9248",
            "title" : "Incorrect selection displayed on coupons page",
            "description" : "We have been reported that one of the selection was incorrectly displayed on the coupons page. The selection \"Dundee United\" was appearing under Home section of RUSH marketType when Home was \"Dundee\". Also from the application investigator the data is rendered from Couchbase as expected."
        }
      },{
        "id": 10,
        "type" : "issues",
        "attributes" : {
            "number": "SSP-9723",
            "title" : "Prod - Ipad & Android Tab - HR - on roller deck - left/right arrows are not displayed and scrolling its blocked",
            "description" : "Seen the issue on Prod and Preprod - web and App:\n1. click on HR via LHM\n2. click on any racecard\n3. click on Roller deck\nIssues:\n1. No left/right arrows on roller deck\n2. not able to scroll left/right\n3. Intermittently blocking when scrolling up/down"
        }
      }
  ];

  function findByNumber(number){
      var i;
      for (i = 0; i < ISSUES.length; i++){
          var issue = ISSUES[i];
          if(issue.attributes.number == number){
              return issue;
          }
      }

      return null;
  }

  issuesRouter.get('/', function(req, res) {
    res.setHeader('Content-Type','application/vnd.api+json');
    if(req.query.filter){
      var number = req.query.filter.number;
      var issue = findByNumber(number);
      res.send({
            'data': issue
      });
    } else {
       res.send({
             'data': ISSUES
       });
    }
  });

  issuesRouter.post('/', function(req, res) {
    res.setHeader('Content-Type','application/vnd.api+json');
    idCounter++;
    req.body.data.id = idCounter;
    ISSUES[idCounter -1] = req.body;
    res.status(201).send({'data': req.body.data});
  });

  issuesRouter.get('/:id', function(req, res) {
    res.setHeader('Content-Type','application/vnd.api+json');
    res.send({
      'data': ISSUES[req.params.id -1]
    });
  });

  issuesRouter.put('/:id', function(req, res) {
    res.setHeader('Content-Type','application/vnd.api+json');
    ISSUES[req.params.id -1] = req.body.data;
    res.send({
      'data': req.body.data
    });
  });

  issuesRouter.patch('/:id', function(req, res) {
      res.setHeader('Content-Type','application/vnd.api+json');
      ISSUES[req.params.id -1] = req.body.data;
      res.send({
        'data': req.body.data
      });
    });

  issuesRouter.delete('/:id', function(req, res) {
    res.status(204).end();
  });

  // The POST and PUT call will not contain a request body
  // because the body-parser is not included by default.
  // To use req.body, run:

  //    npm install --save-dev body-parser

  // After installing, you need to `use` the body-parser for
  // this mock uncommenting the following line:
  //
  //app.use('/api/issues', require('body-parser').json());
  app.use('/api/issues', bodyParser.json({ type: 'application/*+json' }), issuesRouter);
};
