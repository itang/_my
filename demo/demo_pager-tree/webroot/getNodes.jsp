<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="javax.servlet.http.*"%>
<%@page import="data.*"%>
<%!//TODO 从数据库等构建
	TreeNode getData() {
		TreeNode root = new TreeNode(0, "根节点", null);
		for (int i = 1; i <= 101; i++) {
			TreeNode node = new TreeNode(i, "节点" + i, root);
			root.addChild(node);
			for (int j = 1; j <= 1001; j++) {
				int id = i * 100000 + j;
				TreeNode subnode = new TreeNode(id, "节点" + id, node);
				node.addChild(subnode);
			}
		}
		return root;
	}

	//TODO 从数据库等查找特定ID的数据
	TreeNode getTreeNode(TreeNode root, int id) {
		List<TreeNode> result = new ArrayList<TreeNode>();
		flatTree(root, result);
		for (TreeNode node : result) {
			if (node.id == id) {
				return node;
			}
		}
		return null;
	}

	private void flatTree(TreeNode root, List<TreeNode> result) {
		result.add(root);
		for (TreeNode node : root.children) {
			flatTree(node, result);
		}
	}%>

<%!int getInt(HttpServletRequest request, String name, int defaultValue) {
		try {
			return Integer.parseInt(request.getParameter(name));
		} catch (Exception e) {
			return defaultValue;
		}
	}

	String getDataJSON(int nodeid, int start, int limit, int total) {
		StringBuilder sb = new StringBuilder();
		sb.append("{total:");

		TreeNode node = getTreeNode(getData(), nodeid);
		if (node != null) {
			sb.append(node.children.size()).append(",nodes:[");
			int end = limit + start;
			if (end > node.children.size()) {
				end = node.children.size();
			}
			List<TreeNode> result = node.children.subList(start, end);
			boolean first = true;
			for (TreeNode c : result) {
				if (!first) {
					sb.append(", ");
				}
				sb.append(c.toJSON());
				first = false;
			}
		} else {
			sb.append(0).append(", nodes:[");
		}

		sb.append("]}");
		return sb.toString();
	}%>
<%
	/**
	 node : the id of the node which is expending or paging
	 start : the start index of the children,when the node first expends,the start value is 0
	 limit : equals the jscode's pageSize,means the count of the node to show ervery time
	 total : the total count of the node's children,when the node first expends,the total parameter dosen't exist
	 */
	int node = getInt(request, "node", -1);
	int start = getInt(request, "start", 0);
	int limit = getInt(request, "limit", 20);
	int total = getInt(request, "total", -1);

	response.setContentType("text/x-json; charset=UTF-8");

	final String data = getDataJSON(node, start, limit, total);

	response.getWriter().write(data);
%>

