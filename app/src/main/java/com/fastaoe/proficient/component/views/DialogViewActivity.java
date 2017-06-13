package com.fastaoe.proficient.component.views;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fastaoe.baselibrary.dialog.AlertDialog;
import com.fastaoe.baselibrary.ioc.ContentView;
import com.fastaoe.baselibrary.ioc.OnClick;
import com.fastaoe.framelibrary.BaseSkinActivity;
import com.fastaoe.framelibrary.DefaultNavigationBar;
import com.fastaoe.proficient.R;

/**
 * Created by jinjin on 2017/6/10.
 */

@ContentView(R.layout.activity_view_dialog)
public class DialogViewActivity extends BaseSkinActivity {

    @Override
    protected void initTitle() {
        new DefaultNavigationBar
                .Builder(this)
                .setTitle("dialog")
                .builder();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.dialog_01)
    void dialog01(TextView tv) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setContentView(R.layout.dialog_common_edit)
                .setOnCancelListener(dialog1 -> {
                    Toast.makeText(this, "取消", Toast.LENGTH_SHORT).show();
                })
                .show();
        dialog.setOnClickListener(R.id.btn_cancel, v -> {
            Toast.makeText(this, "取消", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });
        dialog.setOnClickListener(R.id.btn_ok, v -> {
            EditText edit_text = (EditText) dialog.findViewById(R.id.edit_text);
            Toast.makeText(this, edit_text.getText(), Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });
    }

    @OnClick(R.id.dialog_02)
    void dialog02(TextView tv) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setContentView(R.layout.dialog_common_text)
                .setText(R.id.content, "这是代码中生成的内容！")
                .fullWidth()
                .fromBottom(true)
                .setOnCancelListener(dialog1 -> {
                    Toast.makeText(this, "取消", Toast.LENGTH_SHORT).show();
                })
                .show();
        dialog.setOnClickListener(R.id.btn_cancel, v -> {
            Toast.makeText(this, "取消", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });
        dialog.setOnClickListener(R.id.btn_ok, v -> {
            Toast.makeText(this, "确认", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });
    }
}
