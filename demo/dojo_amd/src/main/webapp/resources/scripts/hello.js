/**
 * custom module: hello
 */
dojo.provide("app.hello");

app.hello.name = "tomas";
app.hello.sayHello = function() {
	var msg = ("Hello, " + this.name + " , from hello module");
	return msg;
};