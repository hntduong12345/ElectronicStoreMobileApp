package com.example.electronicstoremobileapp;

import android.Manifest;

public class AppConstant {
    public static String DATE_FORMATER = "yyyy/MM/dd HH:mm:ss";
    public static final int SELECT_PICTURE_CODE = 200;
    public static final String IMAGE_MEDIA_TYPE = "image/*";
    public static final String TOBE_UPDATE_OBJECT_KEY = "UPDATE_OBJECT";
    public static final String ADMIN_ROLE = "ADMIN";
    public static final String STAFF_ROLE = "Staff";
    public static final String CUSTOMER_ROLE = "CUSTOMER";
    public static final String[] STORAGE_PERMISSIONs = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_MEDIA_IMAGES
    };
    public static final int STORAGE_PERMISSIONs_CODE = 1212;
    public static final int MAX_STORAGE_CAPACITY = 100000;
}
