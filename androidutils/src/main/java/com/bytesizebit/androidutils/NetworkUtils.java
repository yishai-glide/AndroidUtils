package com.bytesizebit.androidutils;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

/***********
 * Android Utils
 * Created by Shahar Barsheshet on 18/05/2015.
 * bytesizebit@gmail.com
 * www.bytesizebit.com
 ***********/
public class NetworkUtils {

    private NetworkUtils() {
    }

    /**
     * Check if there is an active network
     *
     * @param context  the context
     * @return {@code true} for active network
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
     * Get the network type
     *
     * @param context  the context
     * @return String value network type
     */
    public static String getNetworkType(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo eventInfo = connectivityManager.getActiveNetworkInfo();
        if (eventInfo == null) {
            return "NONE";
        }
        return eventInfo.getTypeName();
    }

    /**
     * Get the network sub type
     *
     * @param context  the context
     * @return String value network sub type
     */
    public static String getNetworkSubType(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo eventInfo = connectivityManager.getActiveNetworkInfo();
        if (eventInfo == null) {
            return "NONE";
        }

        String typeName = eventInfo.getTypeName();
        if (typeName.equals("WIFI")) {
            return eventInfo.getExtraInfo();
        }

        return eventInfo.getSubtypeName();
    }

    /**
     * Check if connected to cellular provider
     *
     * @param networkType  the network type
     * @return {@code true} if connected to cellular provider
     */
    public static boolean isNetworkTypeCellular(String networkType) {
        return networkType.equals("MOBILE");
    }

    /**
     * Check if network connection has changed
     *
     * @param context                 the context
     * @param prevConnectivityType    previous network type
     * @param mPrevConnectionSubType  previous network sub type
     * @return {@code true} if network changed
     */
    public static boolean isNetworkChanged(Context context, String prevConnectivityType, String mPrevConnectionSubType) {
        String connectivityType = getNetworkType(context);
        String connectivitySubType = getNetworkSubType(context);
        if (null == prevConnectivityType) {
            return false;
        } else if (!prevConnectivityType.equals(connectivityType)) {
            return true;
        } else if (!mPrevConnectionSubType.equals(connectivitySubType))
        {
            return true;
        }

        return false;
    }

    /**
     * open the wireless network settings
     *
     * @param context  the context
     */
    public static void openWirelessNetworkSettings(Context context) {
        if (android.os.Build.VERSION.SDK_INT > 10) {
            context.startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
        } else {
            context.startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
        }
    }

    /**
     * Check if we have a WiFi connection
     *
     * @param context  the context
     * @return {@code true} if connected to WiFi
     */
    public static boolean isWifiConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm != null && cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;
    }

    /**
     * Get the operator name
     *
     * @param context  the context
     * @return String with operator name
     */
    public static String getNetworkOperatorName(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null ? tm.getNetworkOperatorName() : null;
    }

    /**
     * Get the phone type
     * <pre>
     * PHONE_TYPE_NONE  : 0
     * PHONE_TYPE_GSM   : 1
     * PHONE_TYPE_CDMA  : 2
     * PHONE_TYPE_SIP   : 3
     * <pre/>
     *
     * @param context  the context
     * @return int phone type
     */
    public static int getPhoneType(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null ? tm.getPhoneType() : -1;
    }
}
