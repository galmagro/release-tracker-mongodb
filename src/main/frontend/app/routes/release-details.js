import Ember from 'ember';

export default Ember.Route.extend({

    model: function(params){
        return this.store.findRecord("release",params.release_id);
    },

    actions: {

        edit: function(){
            this.transitionTo("release", this.get("controller.model.id"));
        },

        newBuild: function(){
            this.transitionTo("build","0");
        }
    }



});
