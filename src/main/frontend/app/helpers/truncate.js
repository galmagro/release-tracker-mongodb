import Ember from 'ember';

export function truncate(params/*, hash*/) {
  let truncated = params[0];
  let length = params[1];

  if(truncated == undefined){
      return truncated;
  }

  truncated = $("<p>" + truncated + "</p>").text();

  if(truncated.length < length){
      return truncated;
  } else {
      return truncated.substring(0,length-4).concat("...");
  }
}

export default Ember.Helper.helper(truncate);
