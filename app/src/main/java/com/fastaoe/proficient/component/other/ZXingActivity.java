package com.fastaoe.proficient.component.other;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.TextView;
import android.widget.Toast;

import com.fastaoe.baselibrary.ioc.ContentView;
import com.fastaoe.baselibrary.ioc.OnClick;
import com.fastaoe.baselibrary.permission.PermissionFailure;
import com.fastaoe.baselibrary.permission.PermissionHelper;
import com.fastaoe.baselibrary.permission.PermissionSuccess;
import com.fastaoe.framelibrary.BaseSkinActivity;
import com.fastaoe.framelibrary.DefaultNavigationBar;
import com.fastaoe.proficient.Constants;
import com.fastaoe.proficient.R;
import com.fastaoe.proficient.component.other.zxing.activity.CaptureActivity;

/**
 * Created by jinjin on 17/6/14.
 * description:
 */

@ContentView(R.layout.activity_other_zxing)
public class ZXingActivity extends BaseSkinActivity {

    @Override
    protected void initTitle() {
        new DefaultNavigationBar
                .Builder(this)
                .setTitle("二维码")
                .builder();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.zxing)
    void spanZXing(TextView textView) {
        PermissionHelper.with(this)
                .requestCode(Constants.PERMISSION_CAMERA)
                .permissions(Manifest.permission.CAMERA)
                .request();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionHelper.onRequestPermissionsResult(this, requestCode, permissions);
    }

    @PermissionSuccess(requestCode = Constants.PERMISSION_CAMERA)
    private void callSuccess() {
        startActivity(new Intent(this, CaptureActivity.class));
    }

    @PermissionFailure(requestCode = Constants.PERMISSION_CAMERA)
    private void callFailure() {
        Toast.makeText(this, "拒绝了相机权限，请到设置中打开！", Toast.LENGTH_SHORT).show();
    }

}
