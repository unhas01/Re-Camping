package com.example.woowa_recamping.db;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.woowa_recamping.data.User;

public class UserDAO extends SQLiteOpenHelper {
    private Context context;

    public UserDAO(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE if not exists user ("
                + "_id integer primary key autoincrement,"  //0
                + "name text,"          //1
                + "uid text,"           //2
                + "pwd text,"           //3
                + "age integer,"        //4
                + "email text,"         //5
                + "phone text,"         //6
                + "addr text,"          //7
                + "mile integer);";     //8
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String sql = "DROP TABLE if exists user";

        db.execSQL(sql);
        onCreate(db);
    }

    @NonNull
    public boolean hasUserData(SQLiteDatabase db, String id) {
        String sql = "SELECT * FROM user WHERE uid = '" + id + "';";

        try {
            Cursor cur = db.rawQuery(sql, null);
            if (cur != null && cur.moveToFirst()) {
                cur.close();
                return true;
            }
            cur.close();
        } catch(SQLException e) {
            Log.e("signUp", "데이터 검색 오류");
        }
        return false;
    }

    public String getUserData(SQLiteDatabase db, String id) {
        String sql = "SELECT * FROM user WHERE uid = '" + id + "';";

        try {
            Cursor cur = db.rawQuery(sql, null);
            if(cur != null && cur.moveToNext()) {

                return cur.getString(3);
            }
            cur.close();
        } catch(SQLException e) {
            Log.e("signUp", "데이터 검색 오류");

            return null;
        }

        return null;
    }

    public boolean insertUserDate(SQLiteDatabase db, User user) {
        String sql = "INSERT INTO user (name, uid, pwd, age, email, phone, addr, mile) VALUES(?, ?, ?, ?, ?, ?, ?, 0);";

        try {
            db.execSQL(sql, new Object[]{user.getName(), user.getId(), user.getPwd(), user.getAge(), user.getEmail(), user.getPhone(), user.getAddr()});
            return true;
        } catch(SQLException e) {
            Log.e("signUp", "데이터 추가 오류");
            e.printStackTrace();
        }
        return false;
    }
}
