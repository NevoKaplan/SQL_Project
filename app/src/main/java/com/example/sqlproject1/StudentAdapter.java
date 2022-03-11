package com.example.sqlproject1;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class StudentAdapter extends ArrayAdapter<Student> {

    Context context;
    List<Student> objects;

    public StudentAdapter(Context context, int resource, List<Student> objects) {
        super(context, resource, objects);
        this.objects = objects;
        this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.custom_row, parent, false);

        Student temp = objects.get(position);
        String fullName = temp.getName() + " " + temp.getSurname();
        ((TextView)view.findViewById(R.id.tvFirstName)).setText(fullName);
        ((TextView)view.findViewById(R.id.tvWaste)).setText(temp.getAvgGrades() + "");

        if (temp.getAvgGrades() > 54) {
            ((ImageView)view.findViewById(R.id.iv)).setImageResource(R.drawable.check_mark);
        }
        else {
            ((ImageView)view.findViewById(R.id.iv)).setImageResource(R.drawable.failed);
        }

        return view;
    }



}
