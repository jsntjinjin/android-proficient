package com.fastaoe.proficient.component.opengles.render;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fastaoe.baselibrary.dialog.AlertDialog;
import com.fastaoe.baselibrary.ioc.Bind;
import com.fastaoe.baselibrary.ioc.ContentView;
import com.fastaoe.baselibrary.ioc.OnClick;
import com.fastaoe.framelibrary.BaseSkinActivity;
import com.fastaoe.framelibrary.DefaultNavigationBar;
import com.fastaoe.proficient.R;

import java.util.ArrayList;

/**
 * Created by jinjin on 17/7/7.
 * description:
 */

@ContentView(R.layout.activity_opengl_fglview)
public class FGLViewActivity extends BaseSkinActivity {

    @Bind(R.id.fglview)
    FGLView fglview;

    private ArrayList<Data> mData;

    @Override
    protected void initTitle() {
        new DefaultNavigationBar
                .Builder(this)
                .setTitle("基本图形")
                .builder();
    }

    @Override
    protected void initView() {
        fglview.setShape(Triangle.class);
    }

    @Override
    protected void initData() {
        mData=new ArrayList<>();
        add("三角形", Triangle.class);
//        add("正三角形", TriangleWithCamera.class);
//        add("彩色三角形", TriangleColorFull.class);
//        add("正方形", Square.class);
//        add("圆形", Oval.class);
//        add("正方体", Cube.class);
//        add("圆锥", Cone.class);
//        add("圆柱", Cylinder.class);
//        add("球体", Ball.class);
//        add("带光源的球体",BallWithLight.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        fglview.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        fglview.onPause();
    }

    @OnClick(R.id.btn_change)
    void verticalDrag(Button button) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setContentView(R.layout.dialog_common_list)
                .show();
        ListView listView = (ListView) dialog.findViewById(R.id.listview);
        listView.setAdapter(new MyAdapter());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                fglview.setShape((Class<? extends Shape>) mData.get(position).clazz);
                dialog.dismiss();
            }
        });
    }

    private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(FGLViewActivity.this).inflate(R.layout.item_dialog_list, parent, false);
            TextView textView = (TextView) convertView.findViewById(R.id.mName);
            textView.setText(mData.get(position).showName);
            return convertView;
        }
    }

    private void add(String showName,Class<?> clazz){
        Data data=new Data();
        data.clazz=clazz;
        data.showName=showName;
        mData.add(data);
    }


    private class Data{
        String showName;
        Class<?> clazz;
    }
}
