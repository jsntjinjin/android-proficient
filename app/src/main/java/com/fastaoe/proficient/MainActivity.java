package com.fastaoe.proficient;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.fastaoe.baselibrary.db.DaoSupportFactory;
import com.fastaoe.baselibrary.db.IDaoSupport;
import com.fastaoe.baselibrary.dialog.AlertDialog;
import com.fastaoe.baselibrary.ioc.Bind;
import com.fastaoe.baselibrary.ioc.CheckNet;
import com.fastaoe.baselibrary.ioc.ContentView;
import com.fastaoe.baselibrary.ioc.OnClick;
import com.fastaoe.baselibrary.ioc.OnItemClick;
import com.fastaoe.baselibrary.utils.LogUtil;
import com.fastaoe.framelibrary.BaseSkinActivity;
import com.fastaoe.framelibrary.DefaultNavigationBar;
import com.fastaoe.proficient.Model.Person;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseSkinActivity {

    @Bind(R.id.main_tv1)
    private TextView main_tv1;
    @Bind(R.id.main_listview)
    ListView main_listview;

    @Override
    protected void initTitle() {
        new DefaultNavigationBar
                .Builder(this)
                .setTitle("标题")
                .setRightText("右边")
                .setRightClickListener(v -> finish())
                .builder();

        LogUtil.d("tag", main_tv1.toString());
    }

    @Override
    protected void initView() {
        main_tv1.setText("hahahaha");
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        TempFragment tempFragment = new TempFragment();
        fragmentTransaction.add(R.id.main_fragment, tempFragment);
        fragmentTransaction.commit();
    }

    @OnItemClick(R.id.main_listview)
    @CheckNet
    private void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        Toast.makeText(this, id + "", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void initData() {
        //        HttpUtils.with(this).get("url").addParam("a", "b").execute(new HttpCallback<AbcModel>() {
        //            @Override
        //            public void onSuccess(AbcModel result) {
        //
        //            }
        //
        //            @Override
        //            public void onError(Exception e) {
        //
        //            }
        //        });
        main_listview.setAdapter(new SimpleAdapter(this, getData(), R.layout.main_fragment, new String[]{"title"}, new int[]{R.id.main_tv1}));

//        IDaoSupport<Person> daoSupport = DaoSupportFactory.getFactory().getDao(Person.class);
//        daoSupport.insert(new Person("张三", 22));
    }

    @OnClick({R.id.main_tv1, R.id.main_tv2})
    private void onClick(TextView v) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setContentView(R.layout.main_fragment)
                .setText(R.id.main_tv2, "赵六")
                .fullWidth()
                .fromBottom(true)
                .setOnCancelListener(dialog1 -> {
                    Toast.makeText(this, "xixixixi", Toast.LENGTH_LONG).show();
                })
                .show();
        dialog.setOnClickListener(R.id.main_tv2,
                v1 -> Toast.makeText(this, "哈哈哈哈", Toast.LENGTH_LONG).show());
    }

    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("title", "小宗");
        map.put("info", "电台DJ");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "貂蝉");
        map.put("info", "四大美女");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "奶茶");
        map.put("info", "清纯妹妹");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "大黄");
        map.put("info", "是小狗");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "hello");
        map.put("info", "every thing");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "world");
        map.put("info", "hello world");
        list.add(map);

        return list;
    }
}
