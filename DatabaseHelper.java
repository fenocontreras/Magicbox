package com.kumiho.magicbox;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "magicbox.db";

    private static final String USER_TABLE_NAME = "Users";
    private static final String USER_COL_0 = "ID";
    private static final String USER_COL_1 = "Username";
    private static final String USER_COL_2 = "Password";
    private static final String USER_COL_3 = "IsCompany";
    private static final String USER_COL_4 = "Name";
    private static final String USER_COL_5 = "Surname";
    private static final String USER_COL_6 = "Email";

    private static final String BOX_TABLE_NAME = "Boxes";
    private static final String BOX_COL_0 = "ID";
    private static final String BOX_COL_1 = "Name";
    private static final String BOX_COL_2 = "Description";
    private static final String BOX_COL_3 = "Price";
    private static final String BOX_COL_4 = "Quantity";
    private static final String BOX_COL_5 = "UploadedTime";
    private static final String BOX_COL_6 = "WithdrawalTime";

    private static final String ORDER_TABLE_NAME = "Orders";
    private static final String ORDER_COL_0 = "ID";
    private static final String ORDER_COL_1 = "BoxID";
    private static final String ORDER_COL_2 = "UserID";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + USER_TABLE_NAME + " ("                // Users
                + USER_COL_0 + " INTEGER PRIMARY KEY AUTOINCREMENT, "   // ID
                + USER_COL_1 + " TEXT, "                                // Username
                + USER_COL_2 + " TEXT, "                                // Password
                + USER_COL_3 + " INTEGER, "                             // IsCompany
                + USER_COL_4 + " TEXT, "                                // Name
                + USER_COL_5 + " TEXT, "                                // Surname
                + USER_COL_6 + " TEXT)"                                 // Email
        );
        db.execSQL(
                "CREATE TABLE " + BOX_TABLE_NAME + " ("
                + BOX_COL_0 + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + BOX_COL_1 + " TEXT, "
                + BOX_COL_2 + " TEXT, "
                + BOX_COL_3 + " FLOAT, "
                + BOX_COL_4 + " INTEGER)"
        );
        db.execSQL(
                "CREATE TABLE " + ORDER_TABLE_NAME + " ("
                + ORDER_COL_0 + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ORDER_COL_1 + " INTEGER, "
                + ORDER_COL_2 + " INTEGER, "
                + "FOREIGN KEY (" + ORDER_COL_1 + ") REFERENCES " + BOX_TABLE_NAME + " (" + BOX_COL_0 + "), "
                + "FOREIGN KEY (" + ORDER_COL_2 + ") REFERENCES " + USER_TABLE_NAME + " (" + USER_COL_0 + "))"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + BOX_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ORDER_TABLE_NAME);
        onCreate(db);
    }



    public boolean checkUser(String username, String password) {
        String[] columns = { USER_COL_0 };
        SQLiteDatabase db = getReadableDatabase();
        String selection = USER_COL_1 + "=?" + " and " + USER_COL_2 + "=?";
        String[] selectionArgs = { username, password };
        Cursor cursor = db.query(USER_TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        if(count>0)
            return true;
        else
            return false;
    }

    public boolean addUser(String username, String password, int isCompany, String name, String surname, String email) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_COL_1, username);
        contentValues.put(USER_COL_2, password);
        contentValues.put(USER_COL_3, isCompany);
        contentValues.put(USER_COL_4, name);
        if (isCompany == 1)
            contentValues.put(USER_COL_5, "Company");
        else
            contentValues.put(USER_COL_5, surname);
        contentValues.put(USER_COL_6, email);
        long res = db.insert(USER_TABLE_NAME, null, contentValues);
        db.close();
        if (res == -1)
            return false;
        else
            return true;
    }

    public boolean insertData(String name, String description, float price, int quantity) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BOX_COL_1, name);
        contentValues.put(BOX_COL_2, description);
        contentValues.put(BOX_COL_3, price);
        contentValues.put(BOX_COL_4, quantity);
        long result = db.insert(BOX_TABLE_NAME, null, contentValues);
        db.close();
        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + BOX_TABLE_NAME, null);
        return cursor;
    }

    public String getUserID(String username) {
        /*
        int id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + USER_COL_0 + " FROM " + USER_TABLE_NAME +
                " WHERE " + USER_COL_1 + " = '" + username + " AND " + USER_COL_2 + " = '" + password + "'",  null);
        db.close();
        if (cursor != null && cursor.moveToFirst()) {
            if (cursor.getCount() > 0) {
                return id = cursor.getInt(0);
            }
        }
        return null;*/

        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = new String[]{ USER_COL_0, USER_COL_1, USER_COL_2, USER_COL_3, USER_COL_4, USER_COL_5, USER_COL_6 };
        Cursor cursor = db.query(USER_TABLE_NAME, columns, null, null, null, null, null);
        String resID = "";
        String resUN = "";
        db.close();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            resID = cursor.getString(0);
            resUN = cursor.getString(1);
            if (resUN.equals(username)){
                return resID;
            }
        }
        return resID;
    }
}
