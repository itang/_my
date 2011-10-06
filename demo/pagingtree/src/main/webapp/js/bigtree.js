Ext.onReady(function() {
	var tree = new Ext.tree.TreePanel({
		applyTo : 'tree-ct',
		width : 600,
		height : 400,
		autoScroll : true,
		plugins : new Ext.ux.tree.TreeNodeMouseoverPlugin(), // must use the
																// plugin
		loader : new Ext.ux.tree.PagingTreeLoader({ // use the extend TreeLoader
			dataUrl : '/region/data',
			// 分页大小
			pageSize : 15, // the count of the childnode to show every time
			enableTextPaging : true,
			// 服务器端分页模式 //whether to show the pagination's text
			pagingModel : 'remote' // 'local' //local means client paging
									// ,remote means server paging
		}),
		root : new Ext.tree.AsyncTreeNode({
			id : '-1',
			text : '中国'
		})
	});
});
