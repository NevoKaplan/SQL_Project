package com.example.sqlproject1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    StudentTable studentTable;
    Dialog d;
    int num;
    Student student;
    SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((Button)findViewById(R.id.StudentButton)).setOnClickListener(this);
        ((Button)findViewById(R.id.idbutton)).setOnClickListener(this);
        ((Button)findViewById(R.id.showAllButton)).setOnClickListener(this);
        studentTable = new StudentTable(this);
        sp = getSharedPreferences("details", 0);
        Student.setCounter(sp.getInt("counter", 0));
        ((EditText)findViewById(R.id.IDenter)).setOnFocusChangeListener(this::onFocusChange);
    }


    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
            num = -4;
            if (isInteger(((TextView)findViewById(R.id.IDenter)).getText().toString())) {
                student = studentTable.getStudentByID((long)(Integer.parseInt(((TextView) findViewById(R.id.IDenter)).getText().toString())));
                if (student != null) {
                    createDialog();
                } else {
                    Toast.makeText(this, "ID number not found", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(this, "ID must be an Integer", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.StudentButton) {
            num = 1;
            student = null;
            createDialog();
        }
        else if (view.getId() == R.id.idbutton) {
            updateCaller();
        }

        else if (view.getId() == R.id.showAllButton) {
            showAll();
        }
    }

    public void showAll() {
        Intent intent = new Intent(this, showAll.class);
        startActivityForResult(intent, 4);
    }

    public void updateCaller() {
        num = -4;
        if (isInteger(((TextView)findViewById(R.id.IDenter)).getText().toString())) {
            student = studentTable.getStudentByID((long)(Integer.parseInt(((TextView) findViewById(R.id.IDenter)).getText().toString())));
            if (student != null) {
                createDialog();
            } else {
                Toast.makeText(this, "ID number not found", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this, "ID must be an Integer", Toast.LENGTH_SHORT).show();
        }
    }

    public Student clickDialog(View view) {
        if (view.getId() == R.id.update) {
            if (!((TextView) d.findViewById(R.id.FirstNameTextBox)).getText().toString().matches("") && !((TextView) d.findViewById(R.id.SurnameTextBox)).getText().toString().matches("") && !((TextView) d.findViewById(R.id.AddressTextBox)).getText().toString().matches("") && !((TextView) d.findViewById(R.id.avgTextBox)).getText().toString().matches("")) {
                if (isInteger(((TextView) d.findViewById(R.id.avgTextBox)).getText().toString())) {
                    if ((Integer.parseInt(((TextView) d.findViewById(R.id.avgTextBox)).getText().toString())) >= 0) {
                        if (num == 1) {
                            Student student1 = new Student(((TextView) d.findViewById(R.id.FirstNameTextBox)).getText().toString(), ((TextView) d.findViewById(R.id.SurnameTextBox)).getText().toString(), ((TextView) d.findViewById(R.id.AddressTextBox)).getText().toString(), Integer.parseInt(((TextView) d.findViewById(R.id.avgTextBox)).getText().toString()));
                            saveToSP(Student.getCounter());
                            System.out.println("Student: " + student1.toString());
                            System.out.println("student Table: ");
                            studentTable.createStudent2(student1);
                            d.dismiss();
                            return student1;
                        } else {
                            student.setName(((TextView) d.findViewById(R.id.FirstNameTextBox)).getText().toString());
                            student.setSurname(((TextView) d.findViewById(R.id.SurnameTextBox)).getText().toString());
                            student.setAddress(((TextView) d.findViewById(R.id.AddressTextBox)).getText().toString());
                            student.setAvgGrades(Integer.parseInt(((TextView) d.findViewById(R.id.avgTextBox)).getText().toString()));
                            d.dismiss();
                            studentTable.updateByRow(student);
                            studentTable.close();
                            return student;
                        }
                    } else {
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

        if (num == 1) {
            d.setTitle("Add Student");
            ((TextView)d.findViewById(R.id.textView)).setText("Add Student");
            ((TextView)d.findViewById(R.id.buttonText)).setText("Create");
            ((TextView)d.findViewById(R.id.givenID)).setText("Given Student ID: " + (Student.getCounter() + 1));
            ((ImageView)d.findViewById(R.id.delete_button)).setVisibility(View.GONE);
        }

        else {
            d.setTitle("Update Student");
            ((TextView)d.findViewById(R.id.textView)).setText("Update Student");
            ((TextView)d.findViewById(R.id.buttonText)).setText("Update");
            ((TextView)d.findViewById(R.id.givenID)).setText("Given Student ID: " + student.getId());
            ((TextView)d.findViewById(R.id.FirstNameTextBox)).setText(student.getName());
            ((TextView)d.findViewById(R.id.SurnameTextBox)).setText(student.getSurname());
            ((TextView)d.findViewById(R.id.AddressTextBox)).setText(student.getAddress());
            ((TextView)d.findViewById(R.id.avgTextBox)).setText((long)student.getAvgGrades() + "");
        }
        d.setCancelable(true);
        d.show();
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

    public void saveToSP(int counter) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("counter", counter);
        editor.commit();

    }

    public void goToSite (View view) {
        goToUrl ("https://nevokaplan4.wixsite.com/bibis-adventure");
    }

    private void goToUrl (String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }

}