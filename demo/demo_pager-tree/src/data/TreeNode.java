package data;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {
	public int id;
	public String name;
	public TreeNode parent;
	public List<TreeNode> children = new ArrayList<TreeNode>();

	public TreeNode(int id, String name, TreeNode parent, TreeNode... childs) {
		this.id = id;
		this.name = name;
		this.parent = parent;
		for (TreeNode n : childs) {
			children.add(n);
		}
	}

	public TreeNode addChild(TreeNode child) {
		children.add(child);
		return this;
	}

	public String toJSON() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("id:").append(id).append(",");
		sb.append("text:").append("\"").append(name).append("\"").append(",");
		sb.append("leaf:").append(children.isEmpty());
		return sb.append("}").toString();
	}
}
