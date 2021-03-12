package com.tom.myapplication;

import android.os.LocaleList;

import java.util.Locale;

/**
 * Created by Tomleisen on 2021/3/12.
 * Email : xy162162a@163.com
 * Operation :
 * 参考 https://blog.csdn.net/chaihuasong/article/details/7097966
 */
public class Utils {


    /**
     * 判断国家：
     *
     * 中文：getResources().getConfiguration().locale.getCountry().equals("CN")
     *
     * 繁体中文： getResources().getConfiguration().locale.getCountry().equals("TW") 
     *
     * 英文(英式)：getResources().getConfiguration().locale.getCountry().equals("UK")
     *
     * 英文(美式)：getResources().getConfiguration().locale.getCountry().equals("US")
     */

    /**
     * 下面是判断是否是中文或者繁体中文(台湾)
     */
    public boolean isLunarSetting() {
        String language = getLanguageEnv();

        if (language != null
                && (language.trim().equals("zh-CN") || language.trim().equals("zh-TW")))
            return true;
        else
            return false;
    }


    private String getLanguageEnv() {
//        Locale locale = LocaleList.getDefault().get(0);
        Locale l = Locale.getDefault();
        String language = l.getLanguage();
        String country = l.getCountry().toLowerCase();
        if ("zh".equals(language)) {
            if ("cn".equals(country)) {
                language = "zh-CN";
            } else if ("tw".equals(country)) {
                language = "zh-TW";
            }
        } else if ("pt".equals(language)) {
            if ("br".equals(country)) {
                language = "pt-BR";
            } else if ("pt".equals(country)) {
                language = "pt-PT";
            }
        }
        return language;
    }


}
