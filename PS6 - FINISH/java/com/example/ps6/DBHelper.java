package com.example.ps6;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.ps6.Model.Category;
import com.example.ps6.Model.Student;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private final String TAG = getClass().getSimpleName();
    private final boolean D = true;

    private static final String DB_NAME = "TRY4.db";
    private static final int DB_VER = 1;
    private static final String TABLE_STUDENT = "Students";
    private static final String TABLE_CATEGORY = "Categories";

    private static final String CREATE_TABLE_STUDENT =
            "CREATE TABLE IF NOT EXISTS " + TABLE_STUDENT +
                    "(" +
                    "num TEXT NOT NULL," +
                    "name TEXT NOT NULL," +
                    "phone TEXT NOT NULL," +
                    "imageName TEXT NULL," +
                    "dept TEXT NOT NULL," +
                    "PRIMARY KEY (num))";

    private static final String CREATE_TABLE_CATEGORY =
            "CREATE TABLE IF NOT EXISTS " + TABLE_CATEGORY +
                    "(" +
                    "grp TEXT NOT NULL," +
                    "code TEXT NOT NULL," +
                    "codeKor TEXT NOT NULL," +
                    "caption TEXT NOT NULL," +
                    "creation_Date TEXT NULL," +
                    "processing_Date TEXT NULL," +
                    "PRIMARY KEY (grp,code))";

    String DROP_TABLE_STUDENT = "DROP TABLE IF EXISTS " + TABLE_STUDENT;
    String DROP_TABLE_CATEGORY = "DROP TABLE IF EXISTS " + TABLE_CATEGORY;

    private static final String INSERT_DATA_DEPT1 =
            "INSERT INTO " + TABLE_CATEGORY +
                    "( grp, code, codeKor, caption )" +
                    "VALUES" +
                    "( 'dept', '1001', '컴퓨터소프트웨어과', 'caption' )";

    private static final String INSERT_DATA_DEPT2 =
            "INSERT INTO " + TABLE_CATEGORY +
                    "( grp, code, codeKor, caption )" +
                    "VALUES" +
                    "( 'dept', '2001', '사이버보안과', 'caption' )";

    public void INPUT_DEPT1(SQLiteDatabase db){
        db.execSQL(INSERT_DATA_DEPT1);
        db.execSQL(INSERT_DATA_DEPT2);
    }


    public DBHelper(Context c) {
        super(c, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CATEGORY);
        db.execSQL(CREATE_TABLE_STUDENT);

        INPUT_DEPT1(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int old_v, int new_v) {
        if(old_v != new_v){
            db.execSQL(DROP_TABLE_STUDENT);
            db.execSQL(DROP_TABLE_CATEGORY);

            onCreate(db);
        }
    }

    public List<Student> selectStudent() {

        List<Student> students = new ArrayList<Student>();

        String orderBy = "num";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TABLE_STUDENT, null, null, null, null, null, orderBy);
        c.moveToFirst();
        if (c.getCount() > 0) {
            while (!c.isAfterLast()) {
                Student s = new Student();
                s.setNum(c.getString(c.getColumnIndex("num")));
                s.setName(c.getString(c.getColumnIndex("name")));
                s.setPhone(c.getString(c.getColumnIndex("phone")));
                s.setImageName(c.getString(c.getColumnIndex("imageName")));
                s.setDept(c.getString(c.getColumnIndex("dept")));

                students.add(s);
                c.moveToNext();
                if (D) Log.i(TAG, s.toString());
            }
        }
        c.close();

        //if (D) Log.i(TAG, students.toString());
        return students;
    }

    public Student selectStudent(String num){

        Student s = null;
        if(num == null) return s;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.query(TABLE_STUDENT, null, "num = ?",
                new String[]{num}, null, null, null);

        c.moveToFirst();

        if(c.getCount() > 0){
            s = new Student();
            s.setNum(c.getString(c.getColumnIndex("num")));
            s.setName(c.getString(c.getColumnIndex("name")));
            s.setPhone(c.getString(c.getColumnIndex("phone")));
            s.setImageName(c.getString(c.getColumnIndex("imageName")));
            s.setDept(c.getString(c.getColumnIndex("dept")));
        }
        c.close();
        return s;

    }

    public Student insertUpdate(String num, String name, String phone, String dept){

        Student s = selectStudent(num);

        if(num == null || name == null) return s;

        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();

        values.put("num", num);
        values.put("name", name);
        values.put("phone", phone);
        values.putNull("imageName");
        values.put("dept", dept);

        if(s != null){
            db.update(TABLE_STUDENT, values, "num = ?", new String[]{num});
        } else {
            db.insert(TABLE_STUDENT,null,values);
        }
        s = selectStudent(num);
        return s;
    }

    public Student insertUpdate(String num, String name, String phone,
                                String imageName,String dept){

        Student s = selectStudent(num);

        if(num == null || name == null) return s;

        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();

        values.put("num", num);
        values.put("name", name);
        values.put("phone", phone);
        values.put("imageName", imageName);
        values.put("dept", dept);

        if(s != null){
            db.update(TABLE_STUDENT, values, "num = ?", new String[]{num});
        } else {
            db.insert(TABLE_STUDENT,null,values);
        }

        s = selectStudent(num);

        return s;
    }

    public boolean deleteStudent(String num){
        SQLiteDatabase db = this.getWritableDatabase();
        int n = db.delete(TABLE_STUDENT,"num = ?", new String[]{num});

        return (n > 0 ? true : false);
    }

    public List<Category> selectCategory(){

        List<Category> cgs = new ArrayList<Category>();

        SQLiteDatabase db = this.getReadableDatabase();

        String[] tablecol =
                new String[]
                        {"grp", "code", "codeKor", "caption"};

        Cursor c = db.query(TABLE_CATEGORY, tablecol, null,null,
                null,null,"grp, code");

        if(c.getCount() > 0){
            c.moveToFirst();
            while(!c.isAfterLast()){
                Category cg = new Category();
                cg.setGrp(c.getString(c.getColumnIndex("grp")));
                cg.setCode(c.getString(c.getColumnIndex("code")));
                cg.setCodeKor(c.getString(c.getColumnIndex("codeKor")));
                cg.setCaption(c.getString(c.getColumnIndex("caption")));
                cgs.add(cg);
            }
        }
        c.close();
        return cgs;
    }

    public List<Category> selectCategory(String grp){

        List<Category> cgs = new ArrayList<Category>();

        if(grp == null){
            cgs = selectCategory();
            return cgs;
        }

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.query(TABLE_CATEGORY, null,"grp = ?",
                new String[]{grp}, null,null,null);

        if(c.getCount() > 0){
            if(c.moveToFirst()){
                do{
                    Category cg = new Category();
                    cg.setGrp(c.getString(c.getColumnIndex("grp")));
                    cg.setCode(c.getString(c.getColumnIndex("code")));
                    cg.setCodeKor(c.getString(c.getColumnIndex("codeKor")));
                    cg.setCaption(c.getString(c.getColumnIndex("caption")));
                    cgs.add(cg);
                }while (c.moveToNext());
            }
            c.close();
        } else {
            cgs = selectCategory();
        }
        return cgs;
    }

    public Category selectCategory(String grp, String code){

        Category cg = new Category();

        if(grp == null || code == null) return cg;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.query(TABLE_CATEGORY, null,"grp = ? and code = ?",
                new String[]{grp, code}, null,null,null);

        if(c.getCount() > 0){
            cg.setGrp(c.getString(c.getColumnIndex("grp")));
            cg.setCode(c.getString(c.getColumnIndex("code")));
            cg.setCodeKor(c.getString(c.getColumnIndex("codeKor")));
            cg.setCaption(c.getString(c.getColumnIndex("caption")));
        }
        c.close();
        return cg;
    }

    public Student saveImageName(String num, String imageName) {

        Student s = selectStudent(num);

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        if (s.getNum() == null) {//not exist
            values.put("num", num);
            values.put("imageName", imageName);
            long n = db.insert(TABLE_STUDENT, null, values);
        } else {//already inserted
            values.put("imageName", imageName);
            int n = db.update(TABLE_STUDENT, values, "num = ?", new String[]{num});
        }
        db.close();

        s = selectStudent(num);

        return s;
    }

    public void CloseDB(){
        SQLiteDatabase db = this.getReadableDatabase();
        if(db.isOpen() && db != null){
            db.close();
        }
    }
}
