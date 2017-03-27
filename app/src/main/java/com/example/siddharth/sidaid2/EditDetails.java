package com.example.siddharth.sidaid2;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditDetails extends AppCompatActivity {

    EditText msgtext;
    EditText mobilenumber1;
    EditText mobilenumber3;
    EditText mobilenumber2;
    TextView save;
    String msg;
    String m1;
    String m2;
    String m3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_details);
        msgtext = (EditText) findViewById(R.id.msgtext);
        mobilenumber1 = (EditText) findViewById(R.id.mobilenumber1);
        mobilenumber2 = (EditText) findViewById(R.id.mobilenumber2);
        mobilenumber3 = (EditText) findViewById(R.id.mobilenumber3);
        save = (TextView) findViewById(R.id.savebtn);
        ///Validation Added

        if(mobilenumber1.length()<10 && mobilenumber2.length()<10 && mobilenumber3.length()<10  ){
            Toast.makeText(EditDetails.this,"Invalid details",Toast.LENGTH_LONG).show();
        }
        final SQLiteDatabase mydatabase = openOrCreateDatabase("Database", MODE_PRIVATE, null);
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS data(Message VARCHAR,Number1 VARCHAR,Number2 VARCHAR,Number3 VARCHAR);");
        Cursor resultSet = mydatabase.rawQuery("Select *  from data", null);
        if (resultSet.getCount() != 0) {
            resultSet.moveToFirst();
            msg = resultSet.getString(0);
            m1 = resultSet.getString(1);
            m2 = resultSet.getString(2);
            m3 = resultSet.getString(3);
            msgtext.setText("" + msg);
            mobilenumber1.setText("" + m1);
            mobilenumber2.setText("" + m2);
            mobilenumber3.setText("" + m3);


        }


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msg = msgtext.getText().toString();
                m1 = mobilenumber1.getText().toString();
                m2 = mobilenumber2.getText().toString();
                m3 = mobilenumber3.getText().toString();
                mydatabase.execSQL("delete from data");
                mydatabase.execSQL("Insert into data values('" + msg + "','" + m1 + "','" + m2 + "','" + m3 + "')");
                //Toast.makeText(EditDetails.this,"Done",Toast.LENGTH_LONG).show();
            }
        });


    }
}

