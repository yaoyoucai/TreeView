package shbd.treeview.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yh on 2016/5/7.
 * 结点的实体类
 */
public class Node {
    private int id;
    private int pId;
    private String name;
    private int level;
    private boolean isExpand;
    private int icon;
    private Node parent;
    private List<Node> children=new ArrayList<Node>();

    public Node(int id, int pId, String name) {
        this.id = id;
        this.pId = pId;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {

        return parent == null ? 0 : parent.getLevel() + 1;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isExpand() {
        return isExpand;
    }

    /**
     * 设置当前结点与子结点可扩展状态
     * @param expand
     */
    public void setExpand(boolean expand) {
        if (!expand) {
            for (Node node : children) {
                node.setExpand(false);
            }
        }
        isExpand = expand;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    /**
     * 判断是否为根结点
     *
     * @return
     */
    public boolean isRoot() {
        return parent == null;
    }

    /**
     * 判断是否为叶子结点
     *
     * @return
     */
    public boolean isLeaf() {
        return children.size() == 0;
    }


    public boolean isParentExpand() {
        if (parent == null) {
            return false;
        }
        else {
            return getParent().isExpand();
        }
    }
}
