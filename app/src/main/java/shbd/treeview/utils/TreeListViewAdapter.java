package shbd.treeview.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.List;

/**
 * Created by yh on 2016/5/7.
 */
public abstract class TreeListViewAdapter<T> extends BaseAdapter {
    protected Context mContext;
    protected List<Node> mAllNodes;
    protected List<Node> mVisibleNodes;

    protected LayoutInflater mInflater;
    protected ListView mListView;

    private OnTreeNodeClickListener mListener;
    public TreeListViewAdapter(Context context, List<T> datas, int defaultExpandLevel, ListView listView) {
        this.mContext = context;
        this.mAllNodes = TreeHelper.getSortedNodes(datas, defaultExpandLevel);
        this.mVisibleNodes = TreeHelper.filterVisibleNodes(mAllNodes);

        this.mInflater = LayoutInflater.from(context);
        this.mListView = listView;

        if (mListView != null) {
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    expandOrCollapse(position);
                    if (mListener!=null){
                        mListener.onClick(getItem(position),position);
                    }
                }
            });
        }
    }

    /**
     * 展开或收缩结点
     *
     * @param position
     */
    private void expandOrCollapse(int position) {
        Node node = mVisibleNodes.get(position);
        if (node != null) {
            /**
             * 若当前结点是叶子结点，则不做任何操作
             */
            if (node.isLeaf())
                return;
            node.setExpand(!node.isExpand());
            mVisibleNodes = TreeHelper.filterVisibleNodes(mAllNodes);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mVisibleNodes.size();
    }

    @Override
    public Node getItem(int position) {
        return mVisibleNodes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=getConvertView(getItem(position),position,convertView,parent);
        view.setPadding(getItem(position).getLevel()*30,3,3,3);
        return view;
    }

    public void setOnTreeNodeClickListener(OnTreeNodeClickListener listener){
        this.mListener=listener;
    }
    public interface OnTreeNodeClickListener{
        void onClick(Node node, int position);
    }

    public abstract View getConvertView(Node node,int position, View convertView, ViewGroup parent);

}
