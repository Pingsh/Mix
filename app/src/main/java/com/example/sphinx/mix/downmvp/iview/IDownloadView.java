package com.example.sphinx.mix.downmvp.iview;

/**
 * Created by Sphinx on 2017/3/16.
 */
//Activity需要实现的类,根据结果做相应的响应
public interface IDownloadView {
    void showProgress(boolean flag);

    void showProcessProgress(int x);

    void showError();

    void setView(String result);

    void showNotice(boolean flag);
}
