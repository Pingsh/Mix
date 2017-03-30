package com.example.sphinx.mix.downmvp.imodel;

import android.os.Handler;
import android.os.Message;

import com.example.sphinx.mix.http.DownloadCallBack;
import com.example.sphinx.mix.downmvp.ipresenter.IDownloadPresenter;
import com.example.sphinx.mix.utils.Constants;
import com.example.sphinx.mix.utils.HttpUtils;

/**
 * Created by Sphinx on 2017/3/16.
 */

//这种写法并不算MVP，因为M和P之间没有进行解耦，彼此持有对方的对象。真实情况是，M只提供方法，不发起请求；P中持有M的对象，完成真正的数据连接
public class DownloadModel implements IDownloadModel {
    private IDownloadPresenter mIDownloadPresenter;
    private MyHandler mMyHandler = new MyHandler();

    public DownloadModel(IDownloadPresenter IDownloadPresenter) {
        mIDownloadPresenter = IDownloadPresenter;
    }

    @Override
    public void down(String url) {
        HttpUtils.HttpGet(url, new DownloadCallBack(mMyHandler));
    }

    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 300:
                    int percent = msg.arg1;
                    if (percent < 100) {
                        mIDownloadPresenter.downloadProgress(percent);
                    } else {
                        mIDownloadPresenter.downloadSuccess(Constants.LOCAL_FILE_PATH);
                    }
                    break;
                case 404:
                    mIDownloadPresenter.downloadFail();
                    break;
                default:
                    break;
            }
        }
    }
}
