import Model from 'ember-data/model';
import attr from 'ember-data/attr';
import { belongsTo, hasMany } from 'ember-data/relationships';

export default Model.extend({
      number: attr(),
      description: attr(),
      release : belongsTo("release"),
      issues : hasMany("issue"),

      unallocated: Ember.computed("release", function() {
          return this.get("release.id") == undefined;
      }),

      addIssue: function(issue){
          if(this.findBuildIssue(issue.get("number")) == undefined){
               this.get("issues").pushObject(issue);
          }
      },

      findBuildIssue: function(number){
          return this.get("issues").findBy("number",number);
      },

      removeIssue : function(issue){
          this.get("issues").removeObject(issue);
      }
});
