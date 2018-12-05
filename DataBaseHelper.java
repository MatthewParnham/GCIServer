
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper implements Serializable {
    public static final String TAG = "DataBaseHelper";
    public static final String DATABASE_NAME = "GCI_Project.dp";
    public static final String TABLE_Name = "GCI_Project_Table";
    public static final String COL_0_ID = "ID";
    public static final String COL_1_USERNAME = "Username";
    public static final String COL_2_PASSWORD = "Password";
    public static final String COL_3_Messages = "Messages";

    public DataBaseHelper(Context context){
        super(context, DATABASE_NAME, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "create table " + TABLE_Name + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,Username TEXT UNIQUE,Password TEXT,Messages BLOB)";
        db.execSQL(createTable);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_Name);
        onCreate(db);
    }

    /*
    AddUser method
    adds a user to the application!
     */
    public boolean addData(String[] s){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1_USERNAME, s[0]);
        contentValues.put(COL_2_PASSWORD, s[1]);
        Log.d(TAG, "add Data: " + s + " to Table " + TABLE_Name + " at Column " + COL_1_USERNAME);
        long result = db.insert(TABLE_Name, null,contentValues);
        if(result == -1){
            return false;
        }
        else{
            return true;
        }
    }


    /*
    https://stackoverflow.com/questions/7932420/android-sqlite-cursor-contentvalues
     */
    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_Name;
        Cursor data = db.rawQuery(query, null);
        ContentValues map;
        //ArrayList<ContentValues> retVal = new ArrayList<ContentValues>();
        if(data.moveToFirst()) {
            do {
                map = new ContentValues();
                DatabaseUtils.cursorRowToContentValues(data,map);
                //retV.add(map);
            } while(data.moveToNext());
        }
        data.moveToFirst();
        return data;
    }

    /*getItemMessage method
    Gets the arraylist stored in the column with the specified username name.
     */
    public Cursor getItemMessage(String s){
        SQLiteDatabase db = this.getWritableDatabase();
        MessageHistory mh = null;
        Cursor data = db.rawQuery("SELECT " + COL_3_Messages + " FROM " + TABLE_Name
                + " WHERE " + COL_1_USERNAME + " = '" + s +  "'",null);
        return data;
    }

    /*getItemPassword method
    Gets the data located at a certain Password in password column in table
     */
    public Cursor getItemPassword(String s){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL_2_PASSWORD + " FROM " + TABLE_Name
                + " WHERE " + COL_1_USERNAME + " = '" + s +  "'";
        Cursor data = db.rawQuery(query,null);
        return data;
    }


    /*getItemId method
    Gets the data located at a certain ID in id column in table
    */
    public Cursor getItemId(String s){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL_0_ID + " FROM " + TABLE_Name
                + " WHERE " + COL_1_USERNAME + " = '" + s +  "'";
        Cursor data = db.rawQuery(query,null);
        return data;
    }

    /*
    Deletes a row/user on database!
    https://stackoverflow.com/questions/7510219/deleting-row-in-sqlite-in-android
    */
    public void deleteUser(String n){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_Name + " WHERE " +
                COL_1_USERNAME + "='" + n + "'");
        db.close();
    }

    /*
    deletes everything from the database
     */
    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_Name, null, null);

    }

    /*

    */

    /*
    addData method
    paramters: Message m
    send the message to the people with the given usernames
     */
    public boolean addData(Message m) throws IOException, ClassNotFoundException {
        if(containsUsername(m.getSender()) && containsUsername(m.getReciever())){
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor dataReciever = getItemMessage(m.getReciever());
            //TURN CURSOR TO MESSAGEHISTORY THEN SERIALIZE IT
            Cursor dataSender = getItemMessage(m.getSender());
            db.execSQL("UPDATE " + TABLE_Name + "SET " + COL_3_Messages " = " + /*NAMEOFOBJECT*/
            + "WHERE " + COL_1_USERNAME + "=" + m.getSender());
            db.execSQL("UPDATE " + TABLE_Name + "SET " + COL_3_Messages " = " + /*NAMEOFOBJECT*/
                    + "WHERE " + COL_1_USERNAME + "=" + m.getReciever());

            //db.update(TABLE_Name,oldMessagesReciever,("WHERE " + COL_1_USERNAME))
        }

    }

    public boolean containsUsername(String s){
        SQLiteDatabase db =  this.getWritableDatabase();
        boolean exists = false;
        String sql = "select * from" + TABLE_Name;
        if(s!= null){
            sql = sql + " where Username = '" + s + "'";
        }
        Cursor c = db.rawQuery(sql, null);
        if(c != null){
            if(c.getCount() > 0){
                exists = true;
            }
        }
        c.close();
        db.close();
        return exists
    }
}
