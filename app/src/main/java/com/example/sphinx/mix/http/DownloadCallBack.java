package com.example.sphinx.mix.http;

/**
 * Created by Sphinx on 2017/3/16.
 */

import android.os.Handler;
import android.os.Message;

import com.example.sphinx.mix.utils.Constants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class DownloadCallBack implements Callback {

    private Handler mHandler;

    public DownloadCallBack(Handler handler) {
        mHandler = handler;
    }

    @Override
    public void onFailure(Call call, IOException e) {
        mHandler.sendEmptyMessage(404);
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {

        if (response.code() == 200) {
            File file = new File(Constants.LOCAL_FILE_PATH);
            FileOutputStream fileOutputStream;
            InputStream inputStream;
            inputStream = response.body().byteStream();
            fileOutputStream = new FileOutputStream(file);
            byte[] buffer = new byte[2048];

            long fileSize = response.body().contentLength();
            long temp = 0;

            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, len);
                temp = temp + len;
                int percent = (int) (temp * 100.0f / fileSize);
                Message msg = new Message();
                msg.what = 300;
                msg.arg1 = percent;
                mHandler.sendMessage(msg);
            }
            fileOutputStream.flush();
        } else {
            mHandler.sendEmptyMessage(404);
        }

    }

}
