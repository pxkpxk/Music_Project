package com.dlighttech.music.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by zhujiang on 16-7-2.
 */
public class PreferencesUtils {

    private static SharedPreferences mShared;
    private static PreferencesUtils mPreferencesUtils;

    public static final String SONG_LIST_ID_KEY = "song_list_id_key";
    public static final String SONG_LIST = "song_list";


    private PreferencesUtils() {
    }

    public static PreferencesUtils getInstance(Context ctx, String name) {
        if (mShared == null) {
            mShared = ctx.getSharedPreferences(name, Context.MODE_PRIVATE);
        }
        if (mPreferencesUtils == null) {
            mPreferencesUtils = new PreferencesUtils();
        }
        return mPreferencesUtils;
    }


    public boolean putData(String key, Object data) {
        try {
            SharedPreferences.Editor editor = mShared.edit();
            if (data == null) {
                return false;
            }
            if (data instanceof Integer) {
                editor.putInt(key, (int) data);
            } else if (data instanceof Long) {
                editor.putLong(key, (long) data);
            } else if (data instanceof Float) {
                editor.putFloat(key, (float) data);
            } else if (data instanceof String) {
                editor.putString(key, (String) data);
            } else if (data instanceof Boolean) {
                editor.putBoolean(key, (boolean) data);
            }
            editor.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean getBoolean(String key) {
        return mShared.getBoolean(key, false);
    }

    public int getInteger(String key) {
        return mShared.getInt(key, 0);
    }

    public long getLong(String key) {
        return mShared.getLong(key, 0L);
    }

    public float getFloat(String key) {
        return mShared.getFloat(key, 0F);
    }

    public String getStr(String key) {
        return mShared.getString(key, null);
    }

}