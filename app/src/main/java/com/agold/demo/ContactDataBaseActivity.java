package com.agold.demo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by root on 17-4-19.
 */

public class ContactDataBaseActivity extends AppCompatActivity implements View.OnClickListener{
    public Button b1;
    public Button b2;
    public Button b3;
    public Button b4;
    public Button b5;
    public Button b6;

    private NumberProvider mNumberprovider;
    private Cursor mCursor;
    private Context mContext;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_layout);
        b1 = (Button) findViewById(R.id.button1);
        b2 = (Button) findViewById(R.id.button2);
        b3 = (Button) findViewById(R.id.button3);
        b4 = (Button) findViewById(R.id.button4);
        b5 = (Button) findViewById(R.id.button5);
        b6 = (Button) findViewById(R.id.button6);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b2.setOnClickListener(this);
        b4.setOnClickListener(this);
        b5.setOnClickListener(this);
        b6.setOnClickListener(this);

        mContext = this;
        mNumberprovider = new NumberProvider(this);

    }

    @Override
        public void onClick(View v) {
            android.util.Log.i("ly20170419","now we receive this click event");
            android.util.Log.i("ly20170419","show this v information-->"+v.toString());
            if(v.equals(b1)){
                android.util.Log.i("ly20170419","now we think the view equals");
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                View addContact = getLayoutInflater().inflate(R.layout.add_contact,null);
                final EditText editNumber = (EditText) addContact.findViewById(R.id.et_number);
                final EditText editName = (EditText)addContact.findViewById(R.id.et_name);
                builder.setView(addContact);
                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mNumberprovider.open();
                        android.util.Log.i("ly20170419","now we click the ok button");
                        String name = null;
                        String number = null;
                        if(editName.getText() != null){
                            name = editName.getText().toString();
                        }
                        if(editNumber.getText() != null){
                            number = editNumber.getText().toString();
                        }
                        if(number != null && !TextUtils.isEmpty(number)){
                            Long insertResult = mNumberprovider.insertData(name,number,1);
                            if(insertResult > 0){
                                Toast.makeText(ContactDataBaseActivity.this,"OK",Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(ContactDataBaseActivity.this,"FAIL",Toast.LENGTH_SHORT).show();
                            }
                        }
                        mNumberprovider.close();

                    }
                });
                builder.create().show();
            }

            if(v.equals(b2)){
                android.util.Log.i("ly20170420","this is button 2");
                mNumberprovider.open();
                if(mCursor != null){
                    mCursor.close();
                }
                mCursor = mNumberprovider.query();
                if(mCursor != null){
                    mCursor.moveToFirst();
                    for(int i = 0;i < mCursor.getCount();i++){
                        android.util.Log.i("ly20170420","this is number in data base -->"+mCursor.getString(mCursor.getColumnIndexOrThrow(NumberProvider.KEY_NUM)));
                        android.util.Log.i("ly20170420","this is name in data base -->"+mCursor.getString(mCursor.getColumnIndexOrThrow(NumberProvider.KEY_NAME)));
                        mCursor.moveToNext();
                    }
                }
                mCursor.close();
                mNumberprovider.close();
            }

            if(v.equals(b4)){
                Intent startService = new Intent(this,CallService.class);
                startService(startService);
            }
            if(v.equals(b5)){
                Intent destoryService = new Intent(this,CallService.class);
                stopService(destoryService);
            }

            if(v.equals(b6)){
                android.util.Log.i("ly20170420","this is button 6");
                mNumberprovider.open();
                if(mCursor != null){
                    mCursor.close();
                }
                mCursor = mNumberprovider.query();
                if(mCursor != null){
                    mCursor.moveToFirst();
                    for(int i = 0;i < mCursor.getCount();i++){
                        String numberDelete = mCursor.getString(mCursor.getColumnIndexOrThrow(NumberProvider.KEY_NUM));
                        mNumberprovider.deleteData(numberDelete);
                        mCursor.moveToNext();
                    }
                }
                mCursor.close();
                mNumberprovider.close();

            }
        }

}
