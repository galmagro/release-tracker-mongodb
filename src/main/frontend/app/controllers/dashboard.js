import Ember from 'ember';

export default Ember.Controller.extend({

    dashboardRows: function(){
        var rows = Ember.A([]);
        var currentRow;
        var fullList = this.get("model");

        fullList.forEach(function(item,index){
            if(index % 4 === 0){
                currentRow = Ember.A([]);
                rows.pushObject(currentRow);
            }
            currentRow.pushObject(item);
        });

        return rows;
    }.property("model.@each")
});
