package com.mobile.centaur.h5container;

import android.os.Environment;

public interface Bridge_Constants {

    String BASE_DIR = Environment.getExternalStorageDirectory() + "/XiongAnX";

    String TITLE_NAME = "title_name";
    String HTML_URL = "html_url";
    String TITLE_LOADING = "title_loading";
    String GLOBLE_LOADING = "globle_loading";
    String TITLE_BACKGROUND = "title_background";
    String TITLE_TEXT_COLOR = "title_text_color";
    String TITLE_ICON_BACK = "title_icon_back";
    String PIC_DIR = BASE_DIR + "/picture/";
    String HASHMAP_KEY_DIDI = "key_didi";
    String HASHMAP_KEY_CALLBACK = "key_callback";
    String isCanSupportZoom = "isCanSupportZoom";


}
