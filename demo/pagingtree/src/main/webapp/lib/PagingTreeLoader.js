Ext.ns("Ext.ux.tree");

Ext.ux.tree.PagingTreeLoader = function(config) {
	
	this.pagingModel = config.pagingModel || "local";
	this.pageSize = config.pageSize || 20;
	this.ptb = false;
	this.ptbConfig = {enableTextPaging:config.enableTextPaging,hideMode:'visibility'};
	
	Ext.ux.tree.PagingTreeLoader.superclass.constructor.apply(this, arguments);
};

Ext.extend(Ext.ux.tree.PagingTreeLoader, Ext.tree.TreeLoader, {

    doPreload : function(node){
		var pi = node.attributes.pagingInfo;
		if(pi == undefined){
			node.attributes.pagingInfo = pi = {limit: this.pageSize,start: 0};
		}
		if(this.pagingModel == "local"){
			var children = node.attributes.children;
			if(children){
				var limit = pi.limit;
				var start = pi.start;
				var total = pi.total = children.length;
				
				node.beginUpdate();
				for(var len = (start + limit); start < len && start < total; start++){
					var cn = node.appendChild(this.createNode(children[start]));
					if(this.preloadChildren){
                        this.doPreload(cn);
                    }
				}
				node.endUpdate();
				
				if(limit < total){
					this.createToolbar(node);
				}
				
				return true;
			}
		}
		
		Ext.apply(this.baseParams,pi);

		return false;
    },
		
    processResponse : function(response, node, callback){
        var json = response.responseText;
        try {
            var o = eval("("+json+")");
			var pi = node.attributes.pagingInfo;
			if(this.isArray(o)){
				pi.total = o.length;
			}else{
				pi.total = o.total || o.nodes.length;
				o = o.nodes;
			}
			if(this.pagingModel == 'local'){
				node.attributes.children = o;
			}
			node.beginUpdate();
            for(var i = 0, len = o.length; i < len && i < pi.limit; i++){
				var cn = this.createNode(o[i]);
                if(cn){
					cn = node.appendChild(cn);
                }
            }
            node.endUpdate();

			if(pi.limit < pi.total){
				this.createToolbar(node);
			}
			
            if(typeof callback == "function"){
                callback(this, node);
            }
        }catch(e){
            this.handleFailure(response);
        }
    },

	isArray : function(v){
		return v && typeof v.length == 'number' && typeof v.splice == 'function';
	},
	
    handleResponse : function(response){
        this.transId = false;
        var arg = response.argument;
        this.processResponse(response, arg.node, arg.callback);
		this.fireEvent("load", this, arg.node, response);

		this.addMouseOverEvent(arg.node);			
    },

	addMouseOverEvent : function(node){
		var tree = node.ownerTree;
		if(!tree.hasListener('mouseover')){
			tree.on('mouseover',this.onMouseOver,this);
		}
	},
	
	onMouseOver : function(node){
		if(node.isLeaf() || !node.isLoaded()){
			return;
		}
		var ptb = node.attributes.ptb;
		if(ptb){
			if(this.ptb !== ptb){
				if(this.ptb)
					this.ptb.hide();
				this.ptb = ptb;
			}
			ptb.show();
		}
	},
	
	
	createToolbar : function(node){
		var ptb = node.attributes.ptb;
		var tree = node.ownerTree;
		
		if(this.ptb !== ptb){
			if(this.ptb){
				this.ptb.hide();
			}
			var showTop = (!tree.rootVisible && node.isRoot);
			if(ptb == undefined){
				node.attributes.ptb = ptb = new Ext.ux.tree.PagingTreeToolbar(this.ptbConfig);
				
				var el = node.getUI().getEl();
				if(!showTop){
					el = Ext.get(el.firstChild);
					if(Ext.isIE){
						el.addClass('x-tree-paging-nowrap');
						el = Ext.DomHelper.append(el,{tag:'div',cls:'x-tree-paging-inline'},true);
					}else{
						el.addClass('x-tree-paging-float');
						el = Ext.DomHelper.insertAfter(el,{tag:'div',cls:'x-tree-paging-inline'},true);
					}
				}
				ptb.render(el);
			}
			this.ptb = showTop ? this.ptb : ptb;
		}
		ptb.setTreeNode(node);
	}

});

Ext.ux.tree.TreeNodeMouseoverPlugin = Ext.extend(Object, {
	init: function(tree) {
		if (!tree.rendered) {
			tree.on('render', function() {this.init(tree)}, this);
		}else{
			this.tree = tree;
			tree.body.on('mouseover', this.onTreeMouseover, this, {delegate: 'div.x-tree-node-el'});
		}
	},
	onTreeMouseover: function(e, t) {
		var nodeEl = Ext.fly(t);
		if (nodeEl) {
			var nodeId = nodeEl.getAttributeNS('ext', 'tree-node-id');
			if (nodeId) {
				this.tree.fireEvent('mouseover', this.tree.getNodeById(nodeId), e);
			}
		}
	}
});


Ext.ux.tree.PagingTreeToolbar = Ext.extend(Ext.Toolbar, {

    firstText : Ext.PagingToolbar.prototype.firstText,
    prevText : Ext.PagingToolbar.prototype.prevText,
    nextText : Ext.PagingToolbar.prototype.nextText,
    lastText : Ext.PagingToolbar.prototype.lastText,
	afterPageText : "<font size=2>/{0}</font>",
    
	autoCreate: {
	    cls:'x-tree-paging-toolbar x-small-editor x-tree-paging-inline',
		style:'vertical-align:middle',
	    html:'<table cellspacing="0"><tr></tr></table>'
	},
	
	// private
    onRender : function(ct, position){
		this.height = Ext.isIE ? 10 : 18;
		this.width = 50;
        Ext.PagingToolbar.superclass.onRender.call(this, ct, position);
		
        this.first = this.addButton({
            tooltip: this.firstText,
            cls:"x-tree-paging-btn-icon",
            iconCls: "x-tbar-page-first",
            disabled: true,
            handler: this.onClick.createDelegate(this, ["first"])
        });
        this.prev = this.addButton({
            tooltip: this.prevText,
			cls:"x-tree-paging-btn-icon",
            iconCls: "x-tbar-page-prev",
            disabled: true,
            handler: this.onClick.createDelegate(this, ["prev"])
        });
        this.next = this.addButton({
            tooltip: this.nextText,
            cls:"x-tree-paging-btn-icon",
            iconCls: "x-tbar-page-next",
            disabled: true,
            handler: this.onClick.createDelegate(this, ["next"])
        });
        this.last = this.addButton({
            tooltip: this.lastText,
            cls:"x-tree-paging-btn-icon",
            iconCls: "x-tbar-page-last",
            disabled: true,
            handler: this.onClick.createDelegate(this, ["last"])
        });

		if(this.enableTextPaging == true){
			//this.addText('&nbsp;');
			this.field = Ext.get(this.addDom({
				tag: "input",
				type: "text",
				size: "3",
				value: "1",
				cls: "x-tbar-page-number"
			 }).el);
			 this.field.on("keydown", this.onPagingKeydown, this);
			 this.field.on("focus", function(){this.dom.select();});
			 this.afterTextEl = this.addText(String.format(this.afterPageText, 1));
			 this.field.setHeight(16);
		}
    },
	
    // private
    onClick : function(which){
		switch(which){
            case "first":
				this.pi.start = 0;
            break;
            case "prev":
				this.pi.start = Math.max(0, this.pi.start-this.pi.limit);
            break;
            case "next":
				this.pi.start = this.pi.start+this.pi.limit;
            break;
            case "last":
                var total = this.pi.total;
                var extra = total % this.pi.limit;
                var lastStart = extra ? (total - extra) : (total-this.pi.limit);
				this.pi.start = lastStart;
            break;
        }

		this.updateField();
		this.treeNode.reload();
    },

	// private
	updateField : function(){
		if(this.enableTextPaging === true){
			var d = this.getPageData(), ap = d.activePage, ps = d.pages;
			this.afterTextEl.el.innerHTML = String.format(this.afterPageText, d.pages);
			this.field.dom.value = ap;
		}
	},

	// private
    onPagingKeydown : function(e){
		var k = e.getKey(), d = this.getPageData(), pageNum;
		if (k == e.RETURN) {
			e.stopEvent();
			pageNum = this.readPage(d);
			if(pageNum !== false){
				pageNum = Math.min(Math.max(1, pageNum), d.pages) - 1;
				this.pi.start = pageNum * this.pi.limit;
                this.treeNode.reload();
            }
        }else if (k == e.HOME || k == e.END){
            e.stopEvent();
            pageNum = k == e.HOME ? 1 : d.pages;
            this.field.dom.value = pageNum;
        }else if (k == e.UP || k == e.PAGEUP || k == e.DOWN || k == e.PAGEDOWN){
            e.stopEvent();
            if(pageNum = this.readPage(d)){
                var increment = e.shiftKey ? 10 : 1;
                if(k == e.DOWN || k == e.PAGEDOWN){
                    increment *= -1;
                }
                pageNum += increment;
                if(pageNum >= 1 & pageNum <= d.pages){
                    this.field.dom.value = pageNum;
                }
            }
        }
    },

	// private
    readPage : function(d){
        var v = this.field.dom.value, pageNum;
        if (!v || isNaN(pageNum = parseInt(v, 10))) {
            this.field.dom.value = d.activePage;
            return false;
        }
        return pageNum;
    },

    // private
    getPageData : function(){
		var pi = this.pi;
        var total = pi.total;
        return {
            total : total,
            activePage : Math.ceil((pi.start+pi.limit)/pi.limit),
            pages :  total < pi.limit ? 1 : Math.ceil(total/pi.limit)
        };
    },

	// private
	resetToolBar : function(){
		var fp = this.pi.start == 0;
		var nl = (this.pi.start + this.pi.limit) >= this.pi.total;

		this.first.setDisabled(fp);
        this.prev.setDisabled(fp);
        this.next.setDisabled(nl);
        this.last.setDisabled(nl);
		
		this.updateField();
	},

	setTreeNode : function(node){
		this.treeNode = node;
		this.pi = this.treeNode.attributes.pagingInfo;

		this.resetToolBar();
	}
});