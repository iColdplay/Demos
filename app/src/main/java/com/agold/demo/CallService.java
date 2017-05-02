package com.agold.demo;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import java.util.ArrayList;

/**
 * Created by root on 17-4-27.
 */

public class CallService extends Service {

    private NumberProvider mNumberprovider;
    private Cursor mCursor;
    private Context mContext;
    private TelephonyManager telephonyManager;
    private PhoneStateListener mListener;

    private static ArrayList<String> numbers = new ArrayList<String>();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        android.util.Log.i("ly20170427", "CallService onBind");
        return null;
    }

    @Override
    public void onCreate() {
        android.util.Log.i("ly20170427", "CallService onCreate");
        mContext = this;
        mNumberprovider = new NumberProvider(this);

        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        mListener = new PhoneCallListener();
        telephonyManager.listen(mListener,PhoneCallListener.LISTEN_CALL_STATE);

        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        android.util.Log.i("ly20170427", "CallService onStartCommand");
        setNumbers();
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        android.util.Log.i("ly20170427", "CallService onStartCommand numbers0-->"+numbers.get(0));
        Uri data = Uri.parse("tel:" + numbers.get(0));
        callIntent.setData(data);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            android.util.Log.i("ly20170427", " return by permission" +
                    "");
            return super.onStartCommand(intent, flags, startId);
        }
        callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(callIntent);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        android.util.Log.i("ly20170427","CallService onDestory");
        super.onDestroy();
    }

    public void setNumbers(){
        mNumberprovider.open();
        if(mCursor != null){
            mCursor.close();
        }
        mCursor = mNumberprovider.query();
        if(mCursor != null){
            mCursor.moveToFirst();
            numbers.clear();
            for(int i = 0;i < mCursor.getCount();i++){
                android.util.Log.i("ly20170427","this is number in data base -->"+mCursor.getString(mCursor.getColumnIndexOrThrow(NumberProvider.KEY_NUM)));
                android.util.Log.i("ly20170427","this is name in data base -->"+mCursor.getString(mCursor.getColumnIndexOrThrow(NumberProvider.KEY_NAME)));
                String number = mCursor.getString(mCursor.getColumnIndexOrThrow(NumberProvider.KEY_NUM));
                if(!numbers.contains(number)){

                    numbers.add(number);
                }
                mCursor.moveToNext();
            }
        }
        mCursor.close();
        mNumberprovider.close();
    }

    public class PhoneCallListener extends PhoneStateListener{
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            switch (state) {
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    android.util.Log.i("ly20170427","CALL_STATE_OFFHOOK");
                    break;
                case TelephonyManager.CALL_STATE_RINGING:
                    android.util.Log.i("ly20170427","CALL_STATE_RINGING");
                    break;
                case TelephonyManager.CALL_STATE_IDLE:
                    android.util.Log.i("ly20170427","CALL_STATE_IDLE");
                    break;
            }
        }
    }

}
