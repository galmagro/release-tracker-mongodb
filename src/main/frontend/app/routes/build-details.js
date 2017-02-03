import Ember from 'ember';

export default Ember.Route.extend({

  model: function(params){
      return this.store.find('build',params.build_id);
  },

  actions: {
      edit: function(){
          this.transitionTo("build",this.get("controller.model.id"));
      }
  }

});
