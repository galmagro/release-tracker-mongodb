import Ember from 'ember';

export default Ember.Route.extend({

    model: function(params){
        if(params.build_id == '0'){
            let build = this.store.createRecord("build");
            let release = this.controllerFor("release-details").get("model");
            if(release!=undefined){
                build.set("release",release);
            }
            return build;
        } else {
            return this.store.find("build", params.build_id);
        }
    },

    setupController: function(controller,model){
        this._super(controller,model);
        controller.set("releases",this.store.findAll("release"));
        controller.set("release",model.get("release"));
    },

    actions: {
      save: function(){
         let build = this.get("controller.model");
         build.set("release",this.get("controller.release"));
         let self = this;
         build.save().then(function(){
            self.transitionTo("build-details",build.get("id"));
         });
      }
    }

});
