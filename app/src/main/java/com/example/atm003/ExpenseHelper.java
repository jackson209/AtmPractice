package com.example.atm003;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ExpenseHelper extends SQLiteOpenHelper {
    public ExpenseHelper(Context context){
        this(context,"atm",null,1);
    }
    private ExpenseHelper(Context context, String name,  SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

//    public ExpenseHelper(@androidx.annotation.Nullable Context context, @androidx.annotation.Nullable String name, @androidx.annotation.Nullable SQLiteDatabase.CursorFactory factory, int version) {
//        super(context, name, factory, version);
//    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE expense (_id INTEGER PRIMARY KEY NOT NULL," +
                "cdate VARCHAR NOT NULL," +
                "info VARCHAR NOT NULL,amount INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
