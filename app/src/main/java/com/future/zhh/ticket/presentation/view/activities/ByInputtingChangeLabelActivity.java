package com.future.zhh.ticket.presentation.view.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.future.zhh.ticket.R;
import com.future.zhh.ticket.domain.model.GasLabeReplaceInfo;
import com.future.zhh.ticket.presentation.ApplicationDatas;
import com.future.zhh.ticket.presentation.internal.dl.components.ApplicationComponent;
import com.future.zhh.ticket.presentation.internal.dl.components.ByInputtingChangeLabelActivityComponent;
import com.future.zhh.ticket.presentation.internal.dl.components.DaggerByInputtingChangeLabelActivityComponent;
import com.future.zhh.ticket.presentation.presenters.GasLabeReplacePersent;
import com.future.zhh.ticket.presentation.view.GasLabeReplaceView;
import com.future.zhh.ticket.presentation.view.widgets.circledialog.CircleDialog;
import com.future.zhh.ticket.presentation.view.widgets.keyboard.VirtualKeyboardView;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/12/2.
 * 气瓶建档修改气瓶标签
 */

public class ByInputtingChangeLabelActivity extends BaseActivity implements GasLabeReplaceView {
    @BindView(R.id.et_label)
    EditText etLabel;
    @BindView(R.id.et_scan)
    ImageView etScan;
    @BindView(R.id.img_ibtnBack)
    ImageView imgIbtnBack;
    @BindView(R.id.ibtnBack)
    LinearLayout ibtnBack;
    @BindView(R.id.tvToolbarTitle)
    TextView tvToolbarTitle;
    @BindView(R.id.ibtnSetting)
    ImageButton ibtnSetting;
    @BindView(R.id.mToolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_save)
    TextView tvSave;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    private String label;

    private Context mContext;
    private ByInputtingChangeLabelActivityComponent component;
    @Inject
    GasLabeReplacePersent gasLabeReplacePersent;


    @Override
    protected void setupActivityComponent(ApplicationComponent applicationComponent) {
        component = DaggerByInputtingChangeLabelActivityComponent
                .builder()
                .applicationComponent(applicationComponent)
                .activityModule(getActivityModule())
                .build();
        component.inject(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_by_in_putting_change_label);
        ButterKnife.bind(this);
        mContext = this;
        init();
        initView();
    }


    private void initView() {
        etLabel.setText(label);

    }

    private void init() {
        etLabel.setFocusable(false);
        etLabel.setFocusableInTouchMode(false);
        if (getIntent() != null) {
            label = getIntent().getStringExtra("label");
        }
        gasLabeReplacePersent.setView(this);
    }

    @OnClick({R.id.ibtnBack, R.id.et_scan,R.id.tv_save,R.id.tv_cancel})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibtnBack:
                this.onBackPressed();
                break;
            case R.id.et_scan:
                Intent intent = new Intent(this, ScanInfoActivity.class);
                intent.putExtra(ApplicationDatas.TAP_FROM, ApplicationDatas.TAP_FROM_CHANGE_LABEL);
                startActivityForResult(intent, ScanInfoActivity.REQUEST_CHANGE_LABEL);
                break;
            case R.id.tv_save:
                gasLabeReplacePersent.gasLabeReplace(etLabel.getText().toString());
                break;
            case R.id.tv_cancel:
                finish();
                break;
            default:
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == ScanInfoActivity.REQUEST_CHANGE_LABEL) {
            label = data.getStringExtra("label");
            etLabel.setText(label);
            etLabel.setFocusable(true);
            etLabel.setFocusable(true);
            tvCancel.setVisibility(View.VISIBLE);
            tvSave.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void retGasLabeReplaceView(boolean isSuccess, final GasLabeReplaceInfo result, String msg) {
            if (isSuccess){
                if (result!=null){
                    if (result.getGasState().equals("0")) {

                        new CircleDialog.Builder(this)
                                .setTitle("提示")
                                .setWidth((float) 0.8)
                                .setText("气瓶标签更换成功")
                                .setPositive("确定", null)
                                .show();
                        tvCancel.setVisibility(View.GONE);
                        tvSave.setVisibility(View.GONE);
                        etLabel.setFocusable(false);
                        etLabel.setFocusableInTouchMode(false);
                    } else if (result.getGasState().equals("1")) {

                        new CircleDialog.Builder(this)
                                .setCanceledOnTouchOutside(false)
                                .setCancelable(false)
                                .setTitle("提示")
                                .setWidth((float) 0.8)
                                .setText("该气瓶标签已建档!")
                                .setNegative("确定", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                    }
                                })
                                .setPositive("查看气瓶档案", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent=new Intent(ByInputtingChangeLabelActivity.this,ByInputtingEditActivity.class);
                                        intent.putExtra("label",label);
                                        mContext.startActivity(intent);
                                    }
                                })
                                .show();

                    } else if (result.getGasState() .equals("2")) {

                        new CircleDialog.Builder(this)
                                .setTitle("提示")
                                .setWidth((float) 0.8)
                                .setText("未导入该气瓶标签，无法更换!")
                                .setPositive("确定", null)
                                .show();

                    } else if (result.getGasState().equals("3")) {

                        new CircleDialog.Builder(this)
                                .setTitle("提示")
                                .setWidth((float) 0.8)
                                .setText("该气瓶标签已作废!")
                                .setPositive("确定", null)
                                .show();

                    }
                }
                }
            else {
                showToast(msg);
            }
    }
}
