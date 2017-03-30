package com.example.sphinx.mix.downmvp.ipresenter;

/**
 * Created by Sphinx on 2017/3/16.
 */
//床架View和Model,根据不同状态,调用Model和View的不用方法.此处方法与View的方法一一对应
public interface IDownloadPresenter {
    /**
     * 下载
     *
     * @param url
     */
    void download(String url);

    /**
     * 下载成功
     *
     * @param result
     */
    void downloadSuccess(String result);

    /**
     * 当前下载进度
     *
     * @param progress
     */
    void downloadProgress(int progress);

    /**
     * 下载失败
     */
    void downloadFail();

    /**
     * 下载提示
     */
    void downNotice();
}
