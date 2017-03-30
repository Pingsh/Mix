package com.example.sphinx.mix.dagger.other;

import com.example.sphinx.mix.dagger.other.ApiCalendar;
import com.example.sphinx.mix.utils.Constants;

/**
 * Created by Sphinx on 2017/3/27.
 */

public enum EQueryFrom {
    WAN_NIAN_LI(0,"万年历", Constants.BASE_URL, ApiCalendar.class);

    private int index;
    private String name;
    private String url;
    private Class apiClass;

    EQueryFrom(int index, String name, String url, Class apiClass) {
        this.index = index;
        this.name = name;
        this.url = url;
        this.apiClass = apiClass;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public Class getApiClass() {
        return apiClass;
    }
}
