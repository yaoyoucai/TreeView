package shbd.treeview;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import shbd.treeview.adpter1.SimpleTreeListViewAdapter;
import shbd.treeview.bean.FileBean;
import shbd.treeview.utils.Node;
import shbd.treeview.utils.TreeListViewAdapter;

public class MainActivity extends AppCompatActivity {
    private ListView mTree;
    private List<FileBean> mDatas;
    private SimpleTreeListViewAdapter<FileBean> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTree = (ListView) findViewById(R.id.lv_node);
        initDatas();
        mAdapter = new SimpleTreeListViewAdapter<FileBean>(getApplicationContext(), mDatas, 1, mTree);
        mAdapter.setOnTreeNodeClickListener(new TreeListViewAdapter.OnTreeNodeClickListener() {
            @Override
            public void onClick(Node node, int position) {
                if (node.isLeaf()) {
                    Toast.makeText(MainActivity.this,node.getName(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        /**
         * 长按添加子结点
         */
        mTree.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final EditText editText=new EditText(MainActivity.this);
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setView(editText);
                builder.setTitle("add node");
                builder.setNegativeButton("cancel",null);
                builder.setPositiveButton("sure", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mAdapter.addExtasNode(position,editText.getText().toString().trim());
                    }
                });
                builder.show();
                return true;
            }
        });
        mTree.setAdapter(mAdapter);
    }

    private void initDatas() {
        mDatas = new ArrayList<>();
        FileBean fileBean = new FileBean(1, -1, "根目录1");
        mDatas.add(fileBean);
        fileBean = new FileBean(2, -1, "根目录2");
        mDatas.add(fileBean);
        fileBean = new FileBean(3, -1, "根目录3");
        mDatas.add(fileBean);
        fileBean = new FileBean(4, 1, "子目录1-1");
        mDatas.add(fileBean);
        fileBean = new FileBean(5, 4, "1-1-1");
        mDatas.add(fileBean);
    }
}
