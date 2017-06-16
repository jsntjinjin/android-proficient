package com.fastaoe.proficient.component.other.superupdate;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import com.fastaoe.framelibrary.BaseSkinActivity;

import java.io.File;

/**
 * Created by jinjin on 2017/6/16.
 * description:
 */

public class UpdateActivity extends BaseSkinActivity {

    private String patch_path = Environment.getExternalStorageDirectory().getAbsolutePath()
            + File.separator + "version_1.0_2.0.patch";

    private String new_apk_path = Environment.getExternalStorageDirectory().getAbsolutePath()
            + File.separator + "version_2.0.apk";

    @Override
    protected void initTitle() {
        // 访问后台，查看需不需要下载

        // 下载完差分包之后,调用我们的方法去合并生成新的apk
        // 耗时操作
        // 本地的apk,已经安装,getPackageResourcePath()
        // 生成的apk
        PatchUtils.combine(getPackageResourcePath(), new_apk_path, patch_path);

        try {
            // 校验签名
            if (PatchUtils.getSignature(this).equals(PatchUtils.getSignature(new_apk_path))) {
                // 安装最新版本
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(new File(new_apk_path)),
                        "application/vnd.android.package-archive");
                startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
}
