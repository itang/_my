/**
 * custom module: hello
 */
define(["dojo"], function(dojo) {
  return {
    sayHello: function(domId, msg) {
     dojo.byId(domId).innerHTML += "<hr/>" + msg;
   }
  };
});