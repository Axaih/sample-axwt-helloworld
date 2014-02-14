goog.provide('helloworld.HelloService');
goog.require('helloworld.SiteProfile');
goog.require('goog.async.Deferred');
goog.require('goog.net.XhrIo');
goog.require('goog.proto2.PbLiteSerializer');
goog.require('goog.proto.Serializer');
goog.require('goog.proto2.TextFormatSerializer');
goog.require('pbdesc.com.mycompany.helloworld.HelloMsg');
goog.require('goog.Uri');
goog.require('goog.Uri.QueryData');
goog.require('ax.log');


/**
 * @constructor
 */
helloworld.HelloService = function() {
	this.siteProfile_ = helloworld.SiteProfile.getInstance();
 
	this.pbLiteSerializer_ = new goog.proto2.PbLiteSerializer();
	this.serializer_ = new goog.proto.Serializer();
	this.textFormatSerializer_= new goog.proto2.TextFormatSerializer();

}
goog.addSingletonGetter(helloworld.HelloService);

helloworld.HelloService.ID = "HelloService";
/**
 * @param {accounts.LoginInterface}
 * @param {?function(*)}
 * @param {?function(*)}
 * @param {Object=}
 * @return {?boolean}
 */
helloworld.HelloService.prototype.say = function(text, cb,
		errcb, scope) {

	var ret = new goog.async.Deferred(errcb, scope);
	ret.addCallback(cb);
	
	if (goog.isDefAndNotNull(text)) {
		var xhr = new goog.net.XhrIo();

		goog.events.listen(xhr, goog.net.EventType.COMPLETE, function(e) {
			var xhr = e.target;
			var obj = xhr.getResponseJson();

			ax.log("getPage obj");
			ax.log(obj);
			
			this.helloMsgResp_ = new pbdesc.com.mycompany.helloworld.HelloMsg();
			this.helloMsgResp_ = this.pbLiteSerializer_.deserialize(
					 this.helloMsgResp_.getDescriptor(), obj);
			
			ax.log(" this.helloMsgResp_.getText");
			
			
			ax.log( this.helloMsgResp_.getText() );
			 
			ax.log("pretty");
			ax.log(this.textFormatSerializer_.serialize(this.helloMsgResp_));
			
		 
			ret.callback(this.helloMsgResp_.getText());

		}, false, this);
 
		var helloMsg= new pbdesc.com.mycompany.helloworld.HelloMsg();
		
		 
		helloMsg.setText(  text); 
		
		 
		
		var serializedArray = this.pbLiteSerializer_.serialize(helloMsg);
		var serializedArrayStr = [];
		serializedArrayStr = this.serializer_.serialize(serializedArray);
		
		
		ax.log("serializedArray: " + serializedArrayStr);

		xhr.send(this.siteProfile_.getL0Path().root + "si/hello/say",
				"POST", serializedArrayStr.toString(), {
					"Accept" : "application/protojson",
					"Content-Type" : "application/protojson"
				});
	} else {
		ret.callback(null);
	}

	return ret;

}
