package shbd.treeview.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import shbd.treeview.R;
import shbd.treeview.annotation.TreeNodeId;
import shbd.treeview.annotation.TreeNodeLabel;
import shbd.treeview.annotation.TreeNodePid;

/**
 * Created by yh on 2016/5/7.
 */
public class TreeHelper {
    /**
     * 将后台数据转换为Node结点集合
     *
     * @param datas
     * @return
     */
    private static <T> List<Node> convertData2Nodes(List<T> datas) {
        List<Node> nodeList = new ArrayList<>();
        Node node = null;

        for (T t : datas) {
            int id = -1;
            int pId = -1;
            String name = null;
            //获取到类对象
            Class clazz = t.getClass();
            //获取到类里面所有属性
            Field[] fields = clazz.getDeclaredFields();
            try {

                for (Field field : fields) {
                    //获得当前属性的注解
                    if (field.getAnnotation(TreeNodeId.class) != null) {
                        //由于当前属性是private的，默认不可访问，所以必须设置以下属性使其强制可以访问
                        field.setAccessible(true);
                        id = field.getInt(t);
                    }
                }

                for (Field field : fields) {
                    if (field.getAnnotation(TreeNodePid.class) != null) {
                        field.setAccessible(true);
                        pId = field.getInt(t);
                    }
                }

                for (Field field : fields) {
                    if (field.getAnnotation(TreeNodeLabel.class) != null) {
                        field.setAccessible(true);
                        name = (String) field.get(t);
                    }
                }

                node = new Node(id, pId, name);
                nodeList.add(node);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        //为所有结点设置父子类关系
        for (int i = 0; i < nodeList.size(); i++) {
            Node n = nodeList.get(i);
            for (int j = i + 1; j < nodeList.size(); j++) {
                Node m = nodeList.get(j);
                if (n.getpId() == m.getId()) {
                    m.getChildren().add(n);
                    n.setParent(m);
                } else if (n.getId() == m.getpId()) {
                    n.getChildren().add(m);
                    m.setParent(n);
                }

            }
        }

        //为所有结点设置图片
        for (Node n : nodeList) {
            setNodeIcon(n);
        }
        return nodeList;
    }

    /**
     * 获取到排序后的所有结点******************************需要理解
     * @param datas
     * @param defaultExpandLevel
     * @param <T>
     * @return
     */
    public static <T> List<Node> getSortedNodes(List<T> datas, int defaultExpandLevel) {
        List<Node> nodes = new ArrayList<>();
        List<Node> nodeList = convertData2Nodes(datas);
        //获取到所有的root结点;
        List<Node> rootNodes = getRootNodes(nodeList);
        for (Node node : rootNodes) {
            //将所有结点按顺序添加进来
            addNodes(nodes, node, defaultExpandLevel, 1);
        }
        return nodes;
    }

    private static List<Node> getRootNodes(List<Node> nodeList) {
        List<Node> nodes = new ArrayList<>();
        for (Node node : nodeList) {
            if (node.isRoot()) {
                nodes.add(node);
            }
        }
        return nodes;
    }


    public static void addNodes(List<Node> result, Node node, int defaultExpandLevel, int currentLevel) {
        //若当前层级小于默认展开层级，则设置当前结点的展开状态为true
        if (defaultExpandLevel > currentLevel) {
            node.setExpand(true);
        }
        result.add(node);
        if (node.isLeaf())
            return;
        for (int i = 0; i < node.getChildren().size(); i++) {
            addNodes(result, node.getChildren().get(i), defaultExpandLevel, currentLevel + 1);
        }
    }

    public static void setNodeIcon(Node node) {
        if (node.getChildren().size() > 0 && node.isExpand()) {
            node.setIcon(R.mipmap.tree_ex);
        } else if (node.getChildren().size() > 0 && !node.isExpand()) {
            node.setIcon(R.mipmap.tree_ec);
        } else {
            node.setIcon(-1);
        }
    }

    public static List<Node> filterVisibleNodes(List<Node> nodes) {
        List<Node> nodeList = new ArrayList<>();
        for (Node node : nodes) {
            if (node.isParentExpand() || node.isRoot()) {
                setNodeIcon(node);
                nodeList.add(node);
            }
        }
        return nodeList;
    }
}
