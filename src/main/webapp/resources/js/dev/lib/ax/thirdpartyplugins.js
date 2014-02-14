// Copyright 2008 The Closure Library Authors. All Rights Reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS-IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

/**
 * @fileoverview Class for making an element detach and float to remain visible
 *               when otherwise it would have scrolled up out of view.
 *               <p>
 *               The element remains at its normal position in the layout until
 *               scrolling would cause its top edge to scroll off the top of the
 *               viewport; at that point, the element is replaced with an
 *               invisible placeholder (to keep the layout stable), reattached
 *               in the dom tree to a new parent (the body element by default),
 *               and set to "fixed" positioning (emulated for IE < 7) so that it
 *               remains at its original X position while staying fixed to the
 *               top of the viewport in the Y dimension.
 *               <p>
 *               When the window is scrolled up past the point where the
 *               original element would be fully visible again, the element
 *               snaps back into place, replacing the placeholder.
 * 
 * @see ../demos/scrollfloater.html
 * 
 * Adapted from http://go/elementfloater.js
 */

goog.provide('ax.ThirdPartyPlugins');
goog.require('goog.async.Deferred');
goog.require('goog.array');
goog.require('goog.dom');

goog.require('ax.net.jsloader');
goog.require('goog.async.Delay');
goog.require('goog.events');
goog.require('goog.events.EventType');

/**
 * Creates a ScrollFloater; see file overview for details.
 * 
 * @constructor
 */
ax.ThirdPartyPlugins = function(window) {
	this.window_ = window;

	
	
	// google plus
	var plusOneDeferred = this.plusOneDeferred_ = ax.net.jsloader
			.load("https://apis.google.com/js/plusone.js");

	window["___gcfg"] = {
		lang : 'en-US',
		parsetags : 'explicit'
	};
	

//	this.plusOneDeferred_ = this.whenPlusOneSdkIsLoaded();  
	// analytics
	/*this.googleAnalytics(window, document, 'script',
			'//www.google-analytics.com/analytics.js',
			'googleanaliticsvariable');

	window["googleanaliticsvariable"]('create', 'UA-40553032-2', 'axaih.com');
	window["googleanaliticsvariable"]('send', 'pageview');*/

	// load facebook sdk
	 
	//this.fckSdkDeferred_ = this.facebookSdk(window.document, 'script',
		//	'facebook-jssdk'); 

	this.fckSdkDeferred_ = this.whenFacebookSdkIsLoaded();  
	// async adsense 
	
	var adsDeferred = ax.net.jsloader.load(
			"//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js", null, {
				'async' : true
			}); 
	
	
	


}

ax.ThirdPartyPlugins.prototype.whenPlusOneSdkIsLoaded = function() {
	 

	var deferred = new goog.async.Deferred(function() {
		ax.log("plusOneSdk deferred cancel");
	}, null);
 
	this.plusOneDelay_ = new goog.async.Delay(
			function() {
				if ((typeof (this.window_["gapi"]) != 'undefined' && this.window_["gapi"] != null)
						&& (typeof (this.window_["gapi"]["plusone"]) != 'undefined' && this.window_["gapi"]["plusone"] != null)) {

					this.plusOneDelay_.stop();
					deferred.callback(null);

				} else {

					this.plusOneDelay_.start();
				}

			}, 500, this);

	this.plusOneDelay_.start();

	return deferred;
}

ax.ThirdPartyPlugins.prototype.facebookLogin=function(){
	
	this.window_["FB"].login(function(response) {
		   if (response.authResponse) {
		     console.log('Welcome!  Fetching your information.... authResponse:');
		     console.log(response);
		     
		     FB.api('/me', function(response) {
		       console.log('Good to see you, ' + response.name + '.');
		       console.log( response );
		       
		     });
		     
		     FB.api('/platform/posts', { limit: 3 }, function(response) {
		    	  for (var i=0, l=response.length; i<l; i++) {
		    	    var post = response[i];
		    	    if (post.message) {
		    	      window.alert('Message: ' + post.message);
		    	    } else if (post.attachment && post.attachment.name) {
		    	      window.alert('Attachment: ' + post.attachment.name);
		    	    }
		    	  }
		    	});
		     
		   } else {
		     console.log('User cancelled login or did not fully authorize.');
		   }
		 });
	
	

}

ax.ThirdPartyPlugins.prototype.whenFacebookSdkIsLoaded = function() {

	var deferred = new goog.async.Deferred(function() {
		ax.log("facebookSdk deferred cancel");
	}, null);
 
	this.faceDelay_ = new goog.async.Delay(
			function() {
				if ((typeof (this.window_["FB"]) != 'undefined' && this.window_["FB"] != null)
						&& (typeof (this.window_["FB"]["XFBML"]) != 'undefined' && this.window_["FB"]["XFBML"] != null)) {

					this.faceDelay_.stop();
					deferred.callback(null);

				} else {

					this.faceDelay_.start();
				}

			}, 500, this);

	this.faceDelay_.start();

	return deferred;
}

ax.ThirdPartyPlugins.prototype.facebookSdk = function(d, s, id) {

	var deferred = new goog.async.Deferred(function() {
		ax.log("facebookSdk deferred cancel");
	}, null);

	var js, fjs = d.getElementsByTagName(s)[0];
	if (d.getElementById(id))
		return;
	js = d.createElement(s);
	js.id = id;
	js.async = true;
	js.src = "//connect.facebook.net/pt_BR/all.js#xfbml=1";

	fjs.parentNode.insertBefore(js, fjs);

	this.faceDelay_ = new goog.async.Delay(
			function() {
				if ((typeof (this.window_["FB"]) != 'undefined' && this.window_["FB"] != null)
						&& (typeof (this.window_["FB"]["XFBML"]) != 'undefined' && this.window_["FB"]["XFBML"] != null)) {

					this.faceDelay_.stop();
					deferred.callback(null);

				} else {

					this.faceDelay_.start();
				}

			}, 500, this);

	this.faceDelay_.start();

	return deferred;
}

ax.ThirdPartyPlugins.prototype.googleAnalytics = function(i, s, o, g, r, a, m) {

	i['GoogleAnalyticsObject'] = r;
	i[r] = i[r] || function() {
		(i[r].q = i[r].q || []).push(arguments)
	}, i[r].l = 1 * new Date();
	a = s.createElement(o), m = s.getElementsByTagName(o)[0];
	a.async = 1;
	a.src = g;
	m.parentNode.insertBefore(a, m)

}

ax.ThirdPartyPlugins.prototype.googlePlusOneGo = function(el) {
	var plusOneDeferred = this.plusOneDeferred_;

	if (plusOneDeferred.hasFired()) {

		this.window_["gapi"]["plusone"].go(el);
	} else {
		
		goog.async.Deferred.when(plusOneDeferred,function() {

			this.window_["gapi"]["plusone"].go(this.el_);

		}, {
			window_ : this.window_,
			el_ : el
		});
	}
}
ax.ThirdPartyPlugins.prototype.fbkParse = function(el) {

	var fckSdkDeferred = this.fckSdkDeferred_;

	if (fckSdkDeferred.hasFired()) {

		this.window_["FB"]["XFBML"].parse(el);
	} else {

		goog.async.Deferred.when(fckSdkDeferred, function() {

			this.window_["FB"]["XFBML"].parse(this.el_);

		}, {
			window_ : this.window_,
			el_ : el
		});
	}

}
