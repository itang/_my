<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <meta content="text/html;charset=UTF-8" http-equiv="Content-Type"/>
    <title>页面生命周期-dojo quick start</title>
    <link rel="stylesheet" type="text/css" href="http://ajax.googleapis.com/ajax/libs/dojo/1.5/dijit/themes/claro/claro.css"/>
    <script src="http://ajax.googleapis.com/ajax/libs/dojo/1.5/dojo/dojo.xd.js" djConfig="parseOnLoad: true"></script>
    <script type="text/javascript">
      dojo.addOnLoad(function() {
        alert("load1");
      });
      function load2(){alert("load2");}
      dojo.addOnLoad(load2);
      function load3(){
          dojo.connect(dojo.byId('closeBtn'), 'onclick', null, function(){
              window.close();
          });
      }
      dojo.addOnLoad(load3);

      dojo.addOnUnload(function(){
        alert('你想退出?');
      });
      window.onbeforeunload = function(){ 
         return ("do you want leave?");
      }
      window.onunload = function(){
         alert("要退出随你!");
      }
    </script>
</head>
<body class="claro">
  <div><h1>页面生命周期</h1></div>
  <div>windows关闭（刷新、跳转到另一个链接）时事件
    <ul>
      <li>onbeforeunload 关闭之前的事件，可调出浏览器关闭确认窗口，从而可以阻止关闭</li>
      <li>onunload 正在关闭的事件，在onbeforeunload事件之后，此时无法阻止窗口的关闭</li>
    </ui>
  </div>
  <div>
    <input type="button" value="close" id="closeBtn"/>
  </div>
</body>
</html>