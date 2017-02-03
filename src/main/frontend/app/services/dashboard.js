import Ember from 'ember';

export default Ember.Service.extend({

    store : Ember.inject.service('store'),

    dashboardItems: Ember.A([]),

    refreshDashboardItems : function(){
        var dashboardItems = this.get("dashboardItems");
        dashboardItems.clear();
        var store = this.get("store");
        var environments = store.findAll("environment");
        environments.then(function(){
          environments.forEach(function(item){
              if(!item.get("isError") && !item.get("isNew")){
                  var dashboardItem = Ember.Object.create();
                  dashboardItem.set("environment",item);
                  var lastDeployment = store.queryRecord("check", {"environment": item.get("id"), "type": "last_deploy"});
                  dashboardItem.set("deployment",lastDeployment);
                  dashboardItems.pushObject(dashboardItem);
              }

          });
        });
    },

    dashboardRefresh: function(){
        var interval = 10000;
        Ember.run.later(this,function(){
            console.debug("executing refresh");
            this.refreshDashboardItems();
            this.dashboardRefresh();
        }, interval);
    }.on('init'),

    empty : function(){
        return this.get("dashboardItems.length") === 0;
    }.property("dashboardItems.length")

});
