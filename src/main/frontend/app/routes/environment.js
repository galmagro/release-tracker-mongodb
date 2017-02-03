import Ember from 'ember';

export default Ember.Route.extend({

    dashboard : Ember.inject.service(),

    model: function(params){
        if(params.environment_id === '0'){
            return this.store.createRecord('environment');
        } else {
            return this.store.find('environment', params.environment_id);
        }
    },

    actions: {
      save: function(){
          var self = this;
          var postSave = function(){
                 self.get("dashboard").refreshDashboardItems();
                 self.transitionTo("dashboard");
          };
          this.get("controller.model").save().then(postSave);
      }

    }

});
