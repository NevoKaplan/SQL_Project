package com.example.sqlproject1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class showAll extends AppCompatActivity {

    ArrayList<Student> list;
    ListView lv;
    StudentTable studentTable;
    StudentAdapter studentAdapter;
    Dialog d;
    Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all);

        ((Button)findViewById(R.id.back)).setOnClickListener(this::click);

        lv = (ListView) findViewById(R.id.lv);
        studentTable = new StudentTable(this);
        list = new ArrayList<>();
        onBegin();
    }

    public void click(View view) {
        setResult(RESULT_OK);
        finish();
    }

    public void onItemClick(AdapterView<?> l, View v, int position, long id) {
        Log.i("HelloListView", "You clicked Item: " + id + " at position:" + position);
        updateCaller(Long.parseLong((((TextView)v.findViewById(R.id.sId)).getText().toString())));
        onBegin();
    }

    public void onBegin() {
        list = studentTable.getAllStudents();
        Log.i("data", "list size is " + list.size());

        studentAdapter = new StudentAdapter(this, 0, list);
        lv.setAdapter(studentAdapter);
        lv.setOnItemClickListener(this::onItemClick);
    }

    public Student clickDialog(View view) {
        if (view.getId() == R.id.update) {
            if (!((TextView) d.findViewById(R.id.FirstNameTextBox)).getText().toString().matches("") && !((TextView) d.findViewById(R.id.SurnameTextBox)).getText().toString().matches("") && !((TextView) d.findViewById(R.id.AddressTextBox)).getText().toString().matches("") && !((TextView) d.findViewById(R.id.avgTextBox)).getText().toString().matches("")) {
                if (isInteger(((TextView) d.findViewById(R.id.avgTextBox)).getText().toString())) {
                    if ((Integer.parseInt(((TextView) d.findViewById(R.id.avgTextBox)).getText().toString())) >= 0)
                        {
                            student.setName(((TextView) d.findViewById(R.id.FirstNameTextBox)).getText().toString());
                            student.setSurname(((TextView) d.findViewById(R.id.SurnameTextBox)).getText().toString());
                            student.setAddress(((TextView) d.findViewById(R.id.AddressTextBox)).getText().toString());
                            student.setAvgGrades(Integer.parseInt(((TextView) d.findViewById(R.id.avgTextBox)).getText().toString()));
                            d.dismiss();
                            studentTable.updateByRow(student);
                            studentTable.close();
                            return student;
                        }
                    else {
                        Toast.makeText(this, "Average grade must be 0 or higher", Toast.LENGTH_SHORT).show();
                        return null;
                    }
                } else {
                    Toast.makeText(this, "Average grade must be an Integer", Toast.LENGTH_SHORT).show();
                    return null;
                }
            } else {
                Toast.makeText(this, "Must fill all fields", Toast.LENGTH_SHORT).show();
            }
        }
        else if (view.getId() == R.id.delete_button) {
            createSureDialog(student.getName(), student.getSurname(), student.getId());
        }
        return null;
    }


    public void createSureDialog(String name, String fname, long id) {
        Context context = this;
        new AlertDialog.Builder(context)
                .setTitle("Delete Student")
                .setMessage("Are you sure you want to delete "  + name + " " + fname + "?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        studentTable.deleteStudentByID(id);
                        Toast.makeText(context, "Student deleted", Toast.LENGTH_SHORT).show();
                        d.dismiss();
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void createDialog() {
        d = new Dialog(this);

        d.setContentView(R.layout.custom_dialog);
        d.findViewById(R.id.update).setOnClickListener(this::clickDialog);
        d.findViewById(R.id.delete_button).setOnClickListener(this::clickDialog);
        d.setTitle("Update Student");
        ((TextView)d.findViewById(R.id.textView)).setText("Update Student");
        ((TextView)d.findViewById(R.id.buttonText)).setText("Update");
        ((TextView)d.findViewById(R.id.givenID)).setText("Given Student ID: " + student.getId());
        ((TextView)d.findViewById(R.id.FirstNameTextBox)).setText(student.getName());
        ((TextView)d.findViewById(R.id.SurnameTextBox)).setText(student.getSurname());
        ((TextView)d.findViewById(R.id.AddressTextBox)).setText(student.getAddress());
        ((TextView)d.findViewById(R.id.avgTextBox)).setText((long)student.getAvgGrades() + "");
        d.setCancelable(true);
        d.show();
    }

    public void updateCaller(long id) {
            student = studentTable.getStudentByID(id);
            if (student != null) {
                createDialog();
            } else {
                Toast.makeText(this, "ID number not found", Toast.LENGTH_SHORT).show();
            }
    }

    public static boolean isInteger(String s) {
        if(s.isEmpty()) return false;
        for(int i = 0; i < s.length(); i++) {
            if (i == 0 && s.charAt(i) == '-') {
                if (s.length() == 1) return false;
            }
            else if (!Character.isDigit(s.charAt(i)))
                return false;
        }
        return true;
    }

}