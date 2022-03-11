package com.example.sqlproject1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class showAll extends AppCompatActivity {

    ArrayList<Student> list;
    ListView lv;
    StudentTable studentTable;
    StudentAdapter studentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all);

        ((Button)findViewById(R.id.back)).setOnClickListener(this::click);

        lv = (ListView) findViewById(R.id.lv);
        studentTable = new StudentTable(this);
        list = new ArrayList<>();
        list = studentTable.getAllStudents();
        Log.i("data", "list size is " + list.size());

        studentAdapter = new StudentAdapter(this, 0, list);
        lv.setAdapter(studentAdapter);
    }

    public void click(View view) {
        setResult(RESULT_OK);
        finish();
    }

}