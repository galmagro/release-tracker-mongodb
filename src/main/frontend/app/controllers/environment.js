import Ember from 'ember';

export default Ember.Controller.extend({

  nameValidation: function(){
      if(this.get("model.errors.name")){
          return "error";
      }
  }.property("model.errors.name.@each"),

  urlValidation: function(){
      if(this.get("model.errors.url")){
          return "error";
      }
  }.property("model.errors.url.@each")

});
