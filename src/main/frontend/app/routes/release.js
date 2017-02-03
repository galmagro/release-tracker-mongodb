import Ember from 'ember';

export default Ember.Route.extend({

  model: function(params){
      if(params.release_id == '0'){
          return this.store.createRecord('release');
      } else {
          return this.store.find('release',params.release_id);
      }
  },

  actions : {
    save: function(){
      let self = this;
      let model = this.get("controller.model");
      let postSave = function(){
          self.transitionTo("release-details", model.get("id"));
      };
      model.save().then(postSave);
    }
  }

});
