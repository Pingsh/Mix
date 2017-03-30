package com.example.sphinx.mix.utils;

import android.os.Environment;

import java.io.File;

/**
 * Created by Sphinx on 2017/3/16.
 */

public class Constants {
    public static final String DOWNLOAD_URL = "http://dl.bizhi.sogou.com/images/2015/06/26/1214911.jpg";
    public static final String DOWNLOAD_ERROR_URL = "http://dl.bizhi.sogou.com/images/06/26/1214911.jpg";

    public static final String LOCAL_FILE_PATH = Environment.getExternalStorageDirectory().getPath() + File.separator + "test.jpg";

    //万年历
    public static final String BASE_URL = "http://v.juhe.cn/";

}
