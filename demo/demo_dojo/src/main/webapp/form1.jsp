<%@page contentType="text/html;charset=UTF-8" %>
<%@page pageEncoding="UTF-8" %>
<html>
<head>
    <meta content="text/html;charset=UTF-8" http-equiv="Content-Type"/>
    <title>dojo常用表单控件及验证 - dojo quick start</title>
    <link rel="stylesheet" type="text/css" href="http://ajax.googleapis.com/ajax/libs/dojo/1.5/dijit/themes/claro/claro.css"/>
    <script src="http://ajax.googleapis.com/ajax/libs/dojo/1.5/dojo/dojo.xd.js" djConfig="parseOnLoad: true"></script> 
    <link ref="stylesheet" href="http://ajax.googleapis.com/ajax/libs/dojo/1.5/dojox/form/resources/FileUploader.css"/>
    <link ref="stylesheet" href="http://ajax.googleapis.com/ajax/libs/dojo/1.5/dojox/form/resources/FileInput.css"/>
    <script type="text/javascript">
      dojo.require("dijit.form.ValidationTextBox");
      dojo.require("dijit.form.Button");
      dojo.require("dijit.form.TextBox");
      dojo.require("dijit.form.Form");
      dojo.require("dijit.form.CheckBox");
      dojo.require("dijit.form.RadioButton");
      dojo.require("dijit.form.FilteringSelect");
      dojo.require("dijit.form.DateTextBox");
      dojo.require("dojox.form.FileUploader");
      
      dojo.require("dojox.embed.Flash");
      dojo.require("dojo.parser");
      
      dojo.addOnLoad(function(){
      var fileMask = [
	["Jpeg File", 	"*.jpg;*.jpeg"],
	["GIF File", 	"*.gif"],
	["PNG File", 	"*.png"],
	["All Images", 	"*.jpg;*.jpeg;*.gif;*.png"]
	];
      var f0 = new dojox.form.FileUploader({
		button:dijit.byId("btn0"), 
		degradable:true,
		uploadUrl: 'form1.jsp',
		uploadOnChange:false, 
		selectMultipleFiles:false,
		fileMask:fileMask,
		isDebug:true
	});
      });
    </script>
</head>
<body class="claro">
  <div><h1>dojo常用表单控件及验证</h1></div>
  <div id="treeOne"/>
  <div>
    <form dojoType="dijit.form.Form" action="index.jsp" method="get">  
      <script type="dojo/method" event="onSubmit">  
	if (this.validate()) {  
	alert("Form is valid, submitting!");
	} else {  
	alert("Form contains invalid.  Please complete all required fields.");
	return false;  
	}  
	return true;  
      </script>  
      
      <b>First Name: </b>  
      <input type="text" name="firstName" placeholder="Your Name" id="firstName" dojoType="dijit.form.TextBox" />  
      <b>Website:  </b>  
      <input type="text" name="website" placeholder="Your Website" id="website" dojoType="dijit.form.TextBox" />  
      <!-- radio buttons:  dijit.form.FilteringSelect -->  
      <b>Favorite Color: </b>  
      <select name="color" id="color" placeholder="select a color" dojoType="dijit.form.FilteringSelect">  
	<option value="" selected>Select a Color</option>  
	<option value="Red">Red</option>  
	<option value="Green">Green</option>  
	<option value="Blue">Blue</option>  
      </select>  
      <!-- radio buttons:  dijit.form.CheckBox -->  
      <b>Send Emails? </b>  
      <input type="checkbox" id="checkbox" checked="checked" dojoType="dijit.form.CheckBox" />  
      
      <!-- radio buttons:  dijit.form.RadioButton -->  
      <b>Email Format: </b>  
      <input type="radio" id="radio1" name="format" checked="checked" dojoType="dijit.form.RadioButton" />  
      <label for="radio1">HTML</label>  
      
      <input type="radio" id="radio2" name="format" dojoType="dijit.form.RadioButton" />  
      <label for="radio2">Text</label>  
      <hr size="1"/>
      带验证 Validation*
      <input dojoType="dijit.form.ValidationTextBox" required="true" placeholder="Your Name" missingMessage="Ooops!  You forgot your first name!" name="firstName1" id="firstName1" type="text" regExp="[\w]+" />
      <input dojoType="dijit.form.ValidationTextBox" required="true" regExp="(https?|ftp)://[A-Za-z0-9-_]+\.[A-Za-z0-9-_%\?\/\.=]+" name="website1" placeholder="Your Website" id="website1" type="text" />  
      <hr size="1"/>
      使用dojox.validate验证
      <script type="text/javascript">  
	dojo.require("dojox.validate");  
	dojo.require("dojox.validate.web");  
      </script>  
      <strong>Email:</strong>  
      <input type="text" required="true" name="email" id="email" dojoType="dijit.form.ValidationTextBox" validator="dojox.validate.isEmailAddress" />  
      <hr size="1"/>
      日期控件
      <input type="text" dojoType="dijit.form.DateTextBox" constraints="{datePattern:'yyyy-MM-dd'}" required="true" invalidMessage="请提供一个合法的日期" name="date" id="date"/>
      <hr size="1"/>

      <!-- submit button:  dijit.form.Button -->  
      <input type="submit" value="Submit Form" label="Submit Form" id="submitButton" dojoType="dijit.form.Button" />  
    </form> 
          文件上传控件
      <div id="btn0" class="browse" dojoType="dijit.form.Button">Select Images...</div> 
  </div>
</body>
</html>
