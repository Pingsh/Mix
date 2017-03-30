package com.example.sphinx.mix.downmvp.ipresenter;

import com.example.sphinx.mix.downmvp.imodel.DownloadModel;
import com.example.sphinx.mix.downmvp.imodel.IDownloadModel;
import com.example.sphinx.mix.downmvp.iview.IDownloadView;

/**
 * Created by Sphinx on 2017/3/16.
 */

public class DownloadPresenter implements IDownloadPresenter {
    private IDownloadView mIDownloadView;
    private IDownloadModel mIDownloadModel;


    public DownloadPresenter(IDownloadView IDownloadView) {
        mIDownloadView = IDownloadView;
        mIDownloadModel = new DownloadModel(this);
    }

    @Override
    public void download(String url) {
        mIDownloadView.showProgress(true);
        //这是个错误的示范，详见DownloadModel
        mIDownloadModel.down(url);
    }

    @Override
    public void downloadSuccess(String result) {
        mIDownloadView.showProgress(false);
        mIDownloadView.setView(result);
    }

    @Override
    public void downloadProgress(int progress) {
        mIDownloadView.showProcessProgress(progress);
    }

    @Override
    public void downloadFail() {
        mIDownloadView.showProgress(false);
        mIDownloadView.showError();
    }

    @Override
    public void downNotice() {
        mIDownloadView.showProgress(false);
        mIDownloadView.showNotice(true);
    }
}
