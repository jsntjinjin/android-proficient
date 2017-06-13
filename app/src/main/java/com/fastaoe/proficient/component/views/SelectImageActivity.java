package com.fastaoe.proficient.component.views;

import android.Manifest;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.fastaoe.baselibrary.ioc.Bind;
import com.fastaoe.baselibrary.ioc.ContentView;
import com.fastaoe.baselibrary.permission.PermissionFailure;
import com.fastaoe.baselibrary.permission.PermissionHelper;
import com.fastaoe.baselibrary.permission.PermissionSuccess;
import com.fastaoe.framelibrary.BaseSkinActivity;
import com.fastaoe.framelibrary.DefaultNavigationBar;
import com.fastaoe.proficient.MainActivity;
import com.fastaoe.proficient.R;
import com.fastaoe.proficient.component.views.adapter.SelectImageListAdapter;

import java.util.ArrayList;

/**
 * Created by jinjin on 17/6/13.
 * description:
 */

@ContentView(R.layout.activity_view_select_image)
public class SelectImageActivity extends BaseSkinActivity {

    // 是否显示相机的EXTRA_KEY
    public static final String EXTRA_SHOW_CAMERA = "EXTRA_SHOW_CAMERA";
    // 总共可以选择多少张图片的EXTRA_KEY
    public static final String EXTRA_SELECT_COUNT = "EXTRA_SELECT_COUNT";
    // 原始的图片路径的EXTRA_KEY
    public static final String EXTRA_DEFAULT_SELECTED_LIST = "EXTRA_DEFAULT_SELECTED_LIST";
    // 选择模式的EXTRA_KEY
    public static final String EXTRA_SELECT_MODE = "EXTRA_SELECT_MODE";
    // 返回选择图片列表的EXTRA_KEY
    public static final String EXTRA_RESULT = "EXTRA_RESULT";

    // 选择图片的模式 - 多选
    public static final int MODE_MULTI = 0x0011;
    // 选择图片的模式 - 单选
    public static final int MODE_SINGLE = 0x0012;
    // 单选或者多选，int类型的type
    private int mMode = MODE_MULTI;
    // int 类型的图片张数
    private int mMaxCount = 8;
    // boolean 类型的是否显示拍照按钮
    private boolean mShowCamera = true;
    // ArraryList<String> 已经选择好的图片
    private ArrayList<String> mResultList;

    // 加载所有的数据
    private static final int LOADER_TYPE = 0x0021;

    @Bind(R.id.image_list_rv)
    RecyclerView image_list_rv;

    @Override
    protected void initTitle() {
        new DefaultNavigationBar
                .Builder(this)
                .setTitle("图片选择器")
                .builder();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        // 获取参数
        Intent intent = getIntent();
        mMode = intent.getIntExtra(EXTRA_SELECT_MODE, mMode);
        mMaxCount = intent.getIntExtra(EXTRA_SELECT_COUNT, mMaxCount);
        mShowCamera = intent.getBooleanExtra(EXTRA_SHOW_CAMERA, mShowCamera);
        mResultList = intent.getStringArrayListExtra(EXTRA_DEFAULT_SELECTED_LIST);
        if (mResultList == null) {
            mResultList = new ArrayList<>();
        }

        // 初始化本地图片数据
        PermissionHelper.with(this)
                .requestCode(2)
                .permissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                .request();
    }

    @PermissionSuccess(requestCode = 2)
    private void callSuccess() {
        initImageList();
    }

    @PermissionFailure(requestCode = 2)
    private void callFailure() {
        Toast.makeText(this, "拒绝 -> READ_EXTERNAL_STORAGE", Toast.LENGTH_SHORT).show();
    }


    private void initImageList() {
        // ContentProvider获取图片
        getLoaderManager().initLoader(LOADER_TYPE, null, mLoaderCallback);
    }

    /**
     * 加载图片的CallBack
     */
    private LoaderManager.LoaderCallbacks<Cursor> mLoaderCallback =
            new LoaderManager.LoaderCallbacks<Cursor>() {
                private final String[] IMAGE_PROJECTION = {
                        MediaStore.Images.Media.DATA,
                        MediaStore.Images.Media.DISPLAY_NAME,
                        MediaStore.Images.Media.DATE_ADDED,
                        MediaStore.Images.Media.MIME_TYPE,
                        MediaStore.Images.Media.SIZE,
                        MediaStore.Images.Media._ID};

                @Override
                public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                    // 查询数据库
                    CursorLoader cursorLoader = new CursorLoader(SelectImageActivity.this,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_PROJECTION,
                            IMAGE_PROJECTION[4] + ">0 AND " + IMAGE_PROJECTION[3] + "=? OR "
                                    + IMAGE_PROJECTION[3] + "=? ",
                            new String[]{"image/jpeg", "image/png"}, IMAGE_PROJECTION[2] + " DESC");
                    return cursorLoader;
                }

                @Override
                public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
                    // 解析，封装到集合  只保存String路径
                    if (data != null && data.getCount() > 0) {
                        ArrayList<String> images = new ArrayList<>();

                        // 如果需要显示拍照，就在第一个位置上加一个空String
                        if (mShowCamera) {
                            images.add("");
                        }


                        // 不断的遍历循环
                        while (data.moveToNext()) {
                            // 只保存路径
                            String path = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[0]));
                            images.add(path);
                        }

                        // 显示列表数据
                        showImageList(images);
                    }
                }

                @Override
                public void onLoaderReset(Loader<Cursor> loader) {

                }
            };

    /**
     * 3.展示获取到的图片显示到列表
     *
     * @param images
     */
    private void showImageList(ArrayList<String> images) {
        SelectImageListAdapter listAdapter = new SelectImageListAdapter(this, images);
        image_list_rv.setLayoutManager(new GridLayoutManager(this, 4));
        image_list_rv.setAdapter(listAdapter);
    }
}
