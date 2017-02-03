import Ember from 'ember';

export default Ember.Route.extend({

  dashboard: Ember.inject.service(),

  model: function(){
      var dashboardService = this.get("dashboard");
      if(dashboardService.get("empty")){
          dashboardService.refreshDashboardItems();
      }

      return dashboardService.get("dashboardItems");
  },

  actions: {
    newEnvironment: function(){
      this.transitionTo("environment","0");
    }
  }

});
