package com.example.sqlproject1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class StudentTable extends SQLiteOpenHelper {

    public static final String DATABASENAME = "student.db";
    public static final String TABLE_PRODUCT = "Student";
    public static final int DATABASEVERSION = 1;

    public static final String COLUMN_ID = "studentID";
    public static final String COLUMN_NAME = "Name";
    public static final String COLUMN_SURNAME = "Surname";
    public static final String COLUMN_ADDRESS = "Address";
    public static final String COLUMN_AVGGRADE = "AverageGrade";

    SQLiteDatabase database;

    private final String CREATE_TABLE_PRODUCT = "CREATE TABLE IF NOT EXISTS " + TABLE_PRODUCT + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_NAME + " VARCHAR," + COLUMN_SURNAME + " VARCHAR," + COLUMN_ADDRESS + " VARCHAR," + COLUMN_AVGGRADE +
            " INTEGER" + ");";

    String[] allColumns = {StudentTable.COLUMN_ID, StudentTable.COLUMN_NAME, StudentTable.COLUMN_SURNAME, StudentTable.COLUMN_ADDRESS, StudentTable.COLUMN_AVGGRADE};

    public StudentTable(Context context) {
        super(context, DATABASENAME, null, DATABASEVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PRODUCT);
        Log.i("data1", "Table customer created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
        onCreate(db);
    }

    public void open() {
        database = this.getWritableDatabase();
        Log.i("data", "Database connection open");
    }

    public Student createStudent2(Student student) {
        ContentValues values = new ContentValues();
        values.put(StudentTable.COLUMN_NAME, student.getName());
        values.put(StudentTable.COLUMN_SURNAME, student.getSurname());
        values.put(StudentTable.COLUMN_ADDRESS, student.getAddress());
        values.put(StudentTable.COLUMN_AVGGRADE, student.getAvgGrades());
        System.out.println("database: " + database);
        this.open();
        long insertid = database.insert(StudentTable.TABLE_PRODUCT, null, values);
        Log.i("data", "Student " + insertid + "insert to database");
        this.close();
        return student;
    }

    public long updateByRow(Student s) {
        this.open();
        ContentValues values = new ContentValues();
        values.put(StudentTable.COLUMN_ID, s.getId());
        values.put(StudentTable.COLUMN_NAME, s.getName());
        values.put(StudentTable.COLUMN_SURNAME, s.getSurname());
        values.put(StudentTable.COLUMN_ADDRESS, s.getAddress());
        values.put(StudentTable.COLUMN_AVGGRADE, s.getAvgGrades());

        return database.update(StudentTable.TABLE_PRODUCT, values, StudentTable.COLUMN_ID + "=" + s.getId(), null);
    }

    public Student getStudentByID(long rowID) {
        this.open();
        Cursor cursor = database.query(StudentTable.TABLE_PRODUCT, allColumns, StudentTable.COLUMN_ID + "=" + rowID, null, null, null, null);
        cursor.moveToFirst();

        if (cursor.getCount() > 0) {
            long id = cursor.getLong(cursor.getColumnIndex(StudentTable.COLUMN_ID));
            String fname = cursor.getString(cursor.getColumnIndex(StudentTable.COLUMN_NAME));
            String lname = cursor.getString(cursor.getColumnIndex(StudentTable.COLUMN_SURNAME));
            String address = cursor.getString(cursor.getColumnIndex(StudentTable.COLUMN_ADDRESS));
            int grade = cursor.getInt(cursor.getColumnIndex(StudentTable.COLUMN_AVGGRADE));
            Student s = new Student(fname, lname, address, grade, id);
            this.close();
            return s;
        }
        return null;
    }

    public long deleteStudentByID(long rowID) {
        this.open();
        long fina = database.delete(StudentTable.TABLE_PRODUCT, StudentTable.COLUMN_ID + "=" + rowID, null);
        this.close();
        return fina;
    }

    public ArrayList<Student> getAllStudents() {
        this.open();
        ArrayList<Student> l = new ArrayList<>();
        Cursor cursor = database.query(StudentTable.TABLE_PRODUCT, allColumns, null, null, null, null, null);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                long id = cursor.getLong(cursor.getColumnIndex(StudentTable.COLUMN_ID));
                String fname = cursor.getString(cursor.getColumnIndex(StudentTable.COLUMN_NAME));
                String lname = cursor.getString(cursor.getColumnIndex(StudentTable.COLUMN_SURNAME));
                String address = cursor.getString(cursor.getColumnIndex(StudentTable.COLUMN_ADDRESS));
                int grade = cursor.getInt(cursor.getColumnIndex(StudentTable.COLUMN_AVGGRADE));
                Student s = new Student(fname, lname, address, grade, id);
                l.add(s);
            }
        }
        this.close();
        return l;
    }

}
