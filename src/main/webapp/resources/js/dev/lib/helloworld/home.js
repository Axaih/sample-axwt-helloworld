goog.provide('helloworld.Home')
goog.require('helloworld.HelloService');
goog.require('goog.dom');
goog.require('ax.dom');
goog.require('goog.ui.Component');
goog.require('ax.log');
goog.require('goog.net.Cookies');

/**
 * @constructor
 * @extends {goog.ui.Component}
 */
helloworld.Home = function(decorateEl, opt_domHelper, window) {
	goog.ui.Component.call(this, opt_domHelper);

	this.siteProfile_ = helloworld.SiteProfile.getInstance();

	this.helloService_ = new helloworld.HelloService.getInstance();

	if (!this.siteProfile_.isInitialized()) {

		this.siteProfile_.init(helloworld.Home.boot);

	}

	var sendBtnEl = goog.dom.getElement("pokeBtn");

	this.usernameEl_ = null;
	this.passwordEl_ = null;

	if (goog.isDefAndNotNull(decorateEl)) {
		this.decorate(decorateEl);
	}
	this.document_ = goog.dom.getOwnerDocument(window);

	var cookieManager = this.cookieManager_ = new goog.net.Cookies(
			this.document_);

}
goog.inherits(helloworld.Home, goog.ui.Component);

helloworld.Home.boot = {
	l0Path : {
		root : "http://helloworld-l0-tst.axaih.com:8080/"
	}
}

/**
 * Decorates the given element with this component. Overrides {@link
 * goog.ui.Component#decorateInternal} by delegating DOM manipulation to the
 * control's renderer.
 * 
 * @param {Element}
 *            element Element to decorate.
 * @protected
 * @override
 */
helloworld.Home.prototype.decorateInternal = function(decorateEl) {
	this.setElementInternal(decorateEl);

	var el = decorateEl;

	this.sendBtnEl_ = ax.dom.findNodeByRole(goog.getCssName("send-btn"), el);

}

/**
 * @override
 */
helloworld.Home.prototype.enterDocument = function() {
	helloworld.Home.superClass_.enterDocument.call(this);

	goog.events.listen(this.sendBtnEl_, goog.events.EventType.CLICK, function(e) {
		ax.log("uhu");
 
		this.helloService_.say("joao", function(resp) {
			if (resp) {

				ax.log("created");
				
 
			}
			ax.log(resp);
		}, null, this);

	}, false, this);

}
