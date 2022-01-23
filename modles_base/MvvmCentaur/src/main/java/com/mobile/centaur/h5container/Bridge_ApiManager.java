package com.mobile.centaur.h5container;

import android.content.Context;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.mobile.centaur.apis.Bridge_BaseComponentApis;
import com.mobile.centaur.h5container.control.Bridge_CommonListener;
import com.mobile.centaur.h5container.webview.BridgeWebView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class Bridge_ApiManager {

    private static BridgeWebView mWebView;
    private static Context mContext;

    public static void init(BridgeWebView _webView, FragmentActivity _context) {
        mWebView = _webView;
        mContext = _context;
        Bridge_BaseComponentApis cApis = new Bridge_BaseComponentApis(mWebView, _context, (Bridge_CommonListener) _context);
        Method[] cMethods = cApis.getClass().getDeclaredMethods();

        for (Method m : cMethods) {
            try {
                if (!m.getName().equals("access$super") && !m.getName().equals("access$000")) {
                    m.invoke(null, new Object[]{});
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    public static void initCustom(Class customClass) {


        try {
            Method[] methods = customClass.getDeclaredMethods();
            for (Method m : methods) {
                Log.i("khw", "method ===== " + m.getName());

                if (!m.getName().equals("access$super") && !m.getName().equals("access$000")) {
                    // Log.i("xxxx", "method ===== " + m.getName());
                    m.invoke(null, new Object[]{});

                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
