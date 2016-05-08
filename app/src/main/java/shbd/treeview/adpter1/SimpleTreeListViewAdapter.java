package shbd.treeview.adpter1;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import shbd.treeview.R;
import shbd.treeview.utils.Node;
import shbd.treeview.utils.TreeHelper;
import shbd.treeview.utils.TreeListViewAdapter;

/**
 * Created by yh on 2016/5/8.
 */
public class SimpleTreeListViewAdapter<T> extends TreeListViewAdapter {
    public SimpleTreeListViewAdapter(Context context, List<T> datas, int defaultExpandLevel, ListView listView) {
        super(context, datas, defaultExpandLevel, listView);
    }

    @Override
    public View getConvertView(Node node, int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.ivIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
            viewHolder.tvLabel = (TextView) convertView.findViewById(R.id.tv_label);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (viewHolder != null) {
            viewHolder.tvLabel.setText(node.getName());
            if (node.getIcon() == -1) {
                viewHolder.ivIcon.setVisibility(View.INVISIBLE);
            } else {
                viewHolder.ivIcon.setVisibility(View.VISIBLE);
                viewHolder.ivIcon.setImageResource(node.getIcon());
            }
        }
        return convertView;
    }

    private static class ViewHolder {
        private TextView tvLabel;
        private ImageView ivIcon;
    }


    public void addExtasNode(int position, String name) {
        Node node = (Node) mVisibleNodes.get(position);
        int index = mAllNodes.indexOf(node);
        Node extralNode = new Node(-1, node.getId(), name);
        extralNode.setParent(node);
        node.getChildren().add(extralNode);
        mAllNodes.add(index + 1, extralNode);

        mVisibleNodes = TreeHelper.filterVisibleNodes(mAllNodes);
        notifyDataSetChanged();
    }
}
