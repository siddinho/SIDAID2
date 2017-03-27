package com.example.siddharth.sidaid2;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class DiseasePrediction extends AppCompatActivity {
    SQLiteDatabase mydatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease_prediction);
        Toast.makeText(getApplicationContext(),"Coming Soon",Toast.LENGTH_LONG).show();
        mydatabase = openOrCreateDatabase("siddharth",MODE_PRIVATE,null);
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS Diseases(Diseasename VARCHAR,Symptom1 VARCHAR,Symptom2 VARCHAR,Symptom3 VARCHAR);");
int a= (int) fetchPlacesCount();
        if(a==0){
            mydatabase.execSQL("INSERT INTO Diseases VALUES('CHICKENPOX','FEVER','WHITE SPOTS','RASHES');");
            mydatabase.execSQL("INSERT INTO Diseases VALUES('ALLERGY','FEVER','WHITE SPOTS','MOUTH INF');");
            mydatabase.execSQL("INSERT INTO Diseases VALUES('MEASLES','FEVER','RUNNY NOSE',' RED-RASHES');");
            mydatabase.execSQL("INSERT INTO Diseases VALUES('HEPATITIS A','NAUSEA','STOMACH PAIN','SICKNESS');");
            mydatabase.execSQL("INSERT INTO Diseases VALUES('CONJUCTIVITIS','EYE IRRITATION','FEVER','INSOMNIA');");
            mydatabase.execSQL("INSERT INTO Diseases VALUES('MUMPS','JAW PAIN','SWELLLING','FEVER');");

        }
        Cursor resultSet = mydatabase.rawQuery("Select Symptom1 from Diseases",null);
        resultSet.moveToFirst();
        String trial = null;
        String []arr = new String[10];
        for(int i=0;i<10;i++){
            arr[i]=resultSet.getString(0);
            trial=trial+arr[i];

        }
       // Toast.makeText(getApplicationContext(),trial,Toast.LENGTH_SHORT).show();

    }


    private long fetchPlacesCount() {
        String sql = "SELECT COUNT(*) FROM Diseases";
        SQLiteStatement statement = mydatabase.compileStatement(sql);
        long count = statement.simpleQueryForLong();
        return count;
    }
}
