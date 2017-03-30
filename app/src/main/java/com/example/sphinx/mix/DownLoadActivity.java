package com.example.sphinx.mix;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sphinx.mix.downmvp.ipresenter.DownloadPresenter;
import com.example.sphinx.mix.downmvp.iview.IDownloadView;
import com.example.sphinx.mix.http.DownloadCallBack;
import com.example.sphinx.mix.utils.Constants;
import com.example.sphinx.mix.utils.HttpUtils;

import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

/**
 * 将网络图片下载到本地,下载同时显示进度条.然后加载.分别以MVC和MVP的模式实现
 */
public class DownLoadActivity extends AppCompatActivity implements IDownloadView {
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    private Context mContext;
    private ImageView mImageView;
    private MyHandler mMyHandler;
    private ProgressDialog progressDialog;
    private DownloadPresenter mDownloadPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_load);
        mContext = this;
        mMyHandler = new MyHandler();

        initToolbar();
        initPermission();

//        initMVC();
        initMVP();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initPermission() {
        /*if (ContextCompat.checkSelfPermission(DownLoadActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(DownLoadActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_CALL_PHONE);
        }*/

        PermissionGen.with(DownLoadActivity.this)
                .addRequestCode(100)
                .permissions(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .request();

        /*PermissionGen.needPermission(DownLoadActivity.this, 100,
                new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }
        );*/

    }

    @PermissionFail(requestCode = 100)
    public void doFailSomething() {
        Toast.makeText(this, "Permission is failed", Toast.LENGTH_SHORT).show();
    }

    private void initMVP() {
        mDownloadPresenter = new DownloadPresenter(this);
        //view init
        mImageView = (ImageView) findViewById(R.id.image);
        findViewById(R.id.downloadBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDownloadPresenter.download(Constants.DOWNLOAD_URL);
            }
        });

        findViewById(R.id.downloadBtn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDownloadPresenter.download(Constants.DOWNLOAD_ERROR_URL);
            }
        });

        progressDialog = new ProgressDialog(mContext);
        progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                progressDialog.dismiss();
            }
        });
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setTitle("下载文件");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
    }

    @Override
    public void showProgress(boolean flag) {
        if (flag) {
            progressDialog.show();
        } else {
            progressDialog.dismiss();
        }
    }

    @Override
    public void showProcessProgress(int x) {
        progressDialog.setProgress(x);
    }

    @Override
    public void showError() {
        Toast.makeText(mContext, "Download fail !", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setView(String result) {
        Glide.with(mContext).load(result).into(mImageView);
    }

    @Override
    public void showNotice(boolean flag) {
        //暂时不做处理
    }

    protected void initMVC() {
        //view init
        mImageView = (ImageView) findViewById(R.id.image);

        progressDialog = new ProgressDialog(mContext);
        progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                progressDialog.dismiss();
            }
        });
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setTitle("下载文件");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        findViewById(R.id.downloadBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HttpUtils.HttpGet(Constants.DOWNLOAD_URL, new DownloadCallBack(mMyHandler));
                progressDialog.show();

            }
        });

        findViewById(R.id.downloadBtn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                HttpUtils.HttpGet(Constants.DOWNLOAD_ERROR_URL, new DownloadCallBack(mMyHandler));
            }
        });

    }

    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 300:
                    int percent = msg.arg1;
                    if (percent < 100) {
                        progressDialog.setProgress(percent);
                    } else {
                        progressDialog.dismiss();
                        Glide.with(mContext).load(Constants.DOWNLOAD_URL).into(mImageView);
                    }
                    break;
                case 404:
                    progressDialog.dismiss();
                    Toast.makeText(mContext, "Download fail MVC!", Toast.LENGTH_SHORT).show();
                    break;

                default:
                    break;

            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        /*if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(DownLoadActivity.this, "申请成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(DownLoadActivity.this, "权限拒绝", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);*/
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
}
