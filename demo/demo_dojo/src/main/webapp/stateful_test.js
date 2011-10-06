var obj = new dojo.Stateful({
    a: 1,b : "hello,world"
});
var watchHandle = obj.watch("a", function(target, oldValue, newValue){
    console.log(target + ": " + oldValue + " -> " + newValue);
    //for(var i=0; i< arguments.length;i++){
      //console.log(arguments[i]);
    //}
});
obj.set("a", 100);

//取消watch
watchHandle.unwatch();
obj.set("a", 10);