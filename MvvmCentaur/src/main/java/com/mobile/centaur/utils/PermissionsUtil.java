package com.mobile.centaur.utils;

import android.Manifest;

import androidx.fragment.app.FragmentActivity;

import com.tbruyelle.rxpermissions3.RxPermissions;

import io.reactivex.rxjava3.functions.Consumer;

public class PermissionsUtil {


    public static boolean checkLocPerssion(FragmentActivity mContext) {
        RxPermissions rxPermissions = new RxPermissions(mContext);
        if (rxPermissions.isGranted(Manifest.permission.ACCESS_COARSE_LOCATION) && rxPermissions.isGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {
            return true;
        } else {
            rxPermissions.request(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
                    .subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean granted) {
                        }
                    });
            return false;

        }
    }

    public static boolean checkCamPerssion(FragmentActivity mContext) {
        RxPermissions rxPermissions = new RxPermissions(mContext);
        if (rxPermissions.isGranted(Manifest.permission.CAMERA) && rxPermissions.isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE) && rxPermissions.isGranted(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            return true;
        } else {
            rxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                    .subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean granted) {
                        }
                    });
            return false;

        }
    }

    public static boolean checkSdcardPerssion(FragmentActivity mContext) {
        RxPermissions rxPermissions = new RxPermissions(mContext);
        if (rxPermissions.isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE) && rxPermissions.isGranted(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            return true;
        } else {
            rxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                    .subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean granted) {
                        }
                    });
            return false;

        }
    }

    public static boolean checkPhonePerssion(FragmentActivity mContext) {
        RxPermissions rxPermissions = new RxPermissions(mContext);
        if (rxPermissions.isGranted(Manifest.permission.READ_PHONE_STATE)) {
            return true;
        } else {
            rxPermissions.request(Manifest.permission.READ_PHONE_STATE)
                    .subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean granted) {
                        }
                    });
            return false;

        }
    }
}
