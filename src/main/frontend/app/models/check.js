import Model from 'ember-data/model';
import attr from 'ember-data/attr';
import { belongsTo } from 'ember-data/relationships';

export default Model.extend({
    build :  belongsTo('build'),
    checked : attr('date'),
    error : attr('boolean'),
    environment : belongsTo('environment')
});
