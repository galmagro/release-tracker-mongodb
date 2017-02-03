import Ember from 'ember';

export default Ember.Controller.extend({

    issueNumber : "",

    notFoundNumbers : "",

    release: null,

    store: Ember.inject.service(),

    numberValidation: function(){
          if(this.get("model.errors.number")){
              return "error";
          }
    }.property("model.errors.number.@each"),

    releaseValidation: function(){
          if(this.get("model.errors.release")){
              return "error";
          }
    }.property("model.errors.release.@each"),

    findAndAddIssue: function(number){
        let build = this.get("model");
        let self = this;

        this.get("store").queryRecord("issue",{filter: {number: number}})
            .then(function(issue){
            if(issue != undefined){
                build.addIssue(issue);
                self.set("issueNumber","");
            } else {
                let notFoundNumbers = self.get("notFoundNumbers");
                self.set("notFoundNumbers", notFoundNumbers.concat(" ").concat(number));
            }
        }, function(){
            let notFoundNumbers = self.get("notFoundNumbers");
            self.set("notFoundNumbers", notFoundNumbers.concat(" ").concat(number));
        });
    },

    issueFound: function(){
        return this.get("notFoundNumbers").trim() == "";
    }.property("notFoundNumbers"),

    actions: {
        addIssue: function(){
            let number = this.get("issueNumber");
            if(number!=""){
                let splitNumbers = number.split(",");
                let self = this;
                self.set("notFoundNumbers","");
                splitNumbers.forEach(function(number,index,arr){
                    number = number.trim();
                    self.findAndAddIssue(number);
                });
            }
        },

        removeIssue: function(number){
            console.debug("removing issue " + number);
            let issue = this.get("model").findBuildIssue(number);
            if(issue!=null){
                this.get("model").removeIssue(issue);
            }
        }
    }



});
