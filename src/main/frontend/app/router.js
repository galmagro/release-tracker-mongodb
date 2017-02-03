import Ember from 'ember';
import config from './config/environment';

const Router = Ember.Router.extend({
  location: config.locationType
});

Router.map(function() {
  this.route('dashboard', {path: "/"});
  this.route('environment', {path: "/environment/:environment_id"});
  this.route('releases');
  this.route('release-details', {path: "/release/:release_id/details"});
  this.route('build-details', {path: "/build/:build_id/details"});
  this.route('release', {path: "/release/:release_id"});
  this.route('build', {path: "/build/:build_id"});
});

export default Router;
