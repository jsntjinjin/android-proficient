package com.fastaoe.proficient.component.views;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.fastaoe.baselibrary.ioc.Bind;
import com.fastaoe.baselibrary.ioc.ContentView;
import com.fastaoe.baselibrary.ioc.OnClick;
import com.fastaoe.baselibrary.permission.PermissionFailure;
import com.fastaoe.baselibrary.permission.PermissionHelper;
import com.fastaoe.baselibrary.permission.PermissionSuccess;
import com.fastaoe.framelibrary.BaseSkinActivity;
import com.fastaoe.framelibrary.DefaultNavigationBar;
import com.fastaoe.proficient.Constants;
import com.fastaoe.proficient.R;
import com.fastaoe.proficient.component.views.selectimage.ImageSelector;
import com.fastaoe.proficient.component.views.selectimage.SelectImageActivity;
import com.fastaoe.proficient.component.views.selectimage.SelectImageListAdapter;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;

import static com.fastaoe.proficient.R.id.select_num;

/**
 * Created by jinjin on 2017/6/13.
 * description:
 */

@ContentView(R.layout.activity_view_test_image)
public class ImageTestActivity extends BaseSkinActivity {

    private ArrayList<String> images = new ArrayList<>();

    private final int SELECT_IMAGE_REQUEST = 0x0011;

    private boolean isSingle = false;

    @Bind(R.id.image_list_rv)
    RecyclerView image_list_rv;

    @Override
    protected void initTitle() {
        new DefaultNavigationBar
                .Builder(this)
                .setTitle("图片选择结果")
                .builder();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.btn_choose_many)
    void chooseMany(Button button) {
        isSingle = false;

        PermissionHelper.with(this)
                .requestCode(Constants.PERMISSION_CAMERA)
                .permissions(Manifest.permission.CAMERA)
                .request();
    }

    @OnClick(R.id.btn_choose_one)
    void chooseOne(Button button) {
        isSingle = true;

        PermissionHelper.with(this)
                .requestCode(Constants.PERMISSION_CAMERA)
                .permissions(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .request();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionHelper.onRequestPermissionsResult(this, requestCode, permissions);
    }

    @PermissionSuccess(requestCode = Constants.PERMISSION_CAMERA)
    private void callSuccess() {
        if (isSingle) {
            ImageSelector.create().single().origin(images)
                    .showCamera(true).start(this, SELECT_IMAGE_REQUEST);
        } else {
            ImageSelector.create().count(9).multi().origin(images)
                    .showCamera(true).start(this, SELECT_IMAGE_REQUEST);
        }
    }

    @PermissionFailure(requestCode = Constants.PERMISSION_CAMERA)
    private void callFailure() {
        Toast.makeText(this, "拒绝了相机权限，请到设置中打开！", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_IMAGE_REQUEST && data != null) {
                images = data.getStringArrayListExtra(SelectImageActivity.EXTRA_RESULT);
                // 做一下显示
                Log.e("TAG", images.toString());

                SelectImageListAdapter listAdapter = new SelectImageListAdapter(this, images, images, 9);
                image_list_rv.setLayoutManager(new GridLayoutManager(this, 3));
                image_list_rv.setAdapter(listAdapter);

            }
        }
    }
}
