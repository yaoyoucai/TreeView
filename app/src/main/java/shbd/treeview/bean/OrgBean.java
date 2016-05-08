package shbd.treeview.bean;

import shbd.treeview.annotation.TreeNodeId;
import shbd.treeview.annotation.TreeNodeLabel;
import shbd.treeview.annotation.TreeNodePid;

/**
 * Created by yh on 2016/5/7.
 * 组织树的实体类
 */
public class OrgBean {
    @TreeNodeId
    private int id;
    @TreeNodePid
    private int pId;
    @TreeNodeLabel
    private String label;
    private String desc;

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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
