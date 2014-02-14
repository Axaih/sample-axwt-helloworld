goog.provide('ax.dom');

goog.require('goog.dom'); 
goog.require('goog.dom.dataset');

/**
* @param {String} id string
* @param {Node} root The root of the tree to search.
* @return {Node|undefined} The found node or undefined if none is found
*/
ax.dom.findNodeByRole=function(roleStr,rootNode){
	return goog.dom.findNode(rootNode,function(node){
		return (goog.dom.isElement(node) && goog.dom.dataset.get(node, "role")==roleStr)
	});
	 
};