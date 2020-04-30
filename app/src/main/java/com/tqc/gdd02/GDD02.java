package com.tqc.gdd02;

import android.app.Activity;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class GDD02 extends Activity {
    private static final String DBNAME = "MY_DB";
    private static final String TABLENAME = "MY_TABLE";
    private static final String FIELD01_NAME = "_id";
    private static final String FIELD02_NAME = "_text1";
    private SQLiteDatabase dataBase;
    private Spinner Spinner01;
    private CharSequence[] strNames;
    private Cursor cursor;
    private String orderNames[];
    //資料筆數
    private int recordCount;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Spinner01 = (Spinner) findViewById(R.id.Spinner01);
        Resources resources = getResources();
        String CREATE_SQL = "create table if not exists " + TABLENAME + " (" + FIELD01_NAME + " integer primary key autoincrement, " + FIELD02_NAME + " varchar not null);";

        //TO DO
        dataBase = openOrCreateDatabase(DBNAME, MODE_PRIVATE, null);
        dataBase.execSQL(CREATE_SQL);
        dataBase.execSQL("delete from " + TABLENAME);
        strNames = resources.getStringArray(R.array.strNames);
        for (int i = 0; i < strNames.length; i++) {

            String data = String.format("insert into %s(%s) values ('%s') ",TABLENAME,FIELD02_NAME,strNames[i]);
            dataBase.execSQL(data);
        }
        String data = String.format("select *  From %s ORDER BY %s ",TABLENAME,FIELD02_NAME);
        cursor = dataBase.rawQuery(data, null);
        recordCount = cursor.getCount();
        orderNames = new String[recordCount];
        cursor.moveToFirst();
        for (int i = 0; i < recordCount; i++) {
            orderNames[i] = cursor.getString(1);
            cursor.moveToNext();
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, orderNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //TO DO Spinner
        Spinner01.setAdapter(adapter);
    }
}