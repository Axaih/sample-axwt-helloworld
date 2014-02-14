goog.provide('helloworld.SiteProfile')
/**
 * @constructor
 */
helloworld.SiteProfile = function() {
	this.map_ = {};
	this.boot_ = null;
	this.initialized_ = false;

	this.document_ = goog.dom.getDocument(); 

}
goog.addSingletonGetter(helloworld.SiteProfile);

helloworld.SiteProfile.prototype.init = function(boot) {
	if (!this.initialized_) {

		this.initialized_ = true;
		this.boot_ = boot;
	}
}

helloworld.SiteProfile.prototype.getL0Path = function() {
	return this.boot_.l0Path;
}
helloworld.SiteProfile.prototype.isInitialized = function() {
	return this.initialized_;
}

helloworld.SiteProfile.prototype.addServiceProfile = function(serviceNameId,
		profile) {
	this.map_[serviceNameId] = profile;
}
 
 

helloworld.SiteProfile.prototype.getServiceProfile = function(serviceNameId) {

	return this.map_[serviceNameId];
}