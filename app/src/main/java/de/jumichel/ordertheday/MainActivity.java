package de.jumichel.ordertheday;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_FORNAME = "forname";
    public static final String COLUMN_SURNAME = "surname";
    public static final String COLUMN_PHONENUMBER = "phonenumber";
    public static final String COLUMN_ADDRESS = "address";

    public static final String databaseName = "contactbook.db";
    public static final String tableName = "contactbook";

    Button button_contact;
    Button button_appointment;
    Button button_list_appointments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_contact = (Button) findViewById(R.id.btnContact);
        button_appointment = (Button) findViewById(R.id.btnAppointment);
        button_list_appointments = findViewById(R.id.btnListAppointsments);

        if(firstAppStart()) {
            createDatabase();
        }

        button_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ContactActivity.class);
                startActivity(intent);
            }
        });
        button_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Appointments.class);
                startActivity(intent);
            }
        });
        button_list_appointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListOfAppointmentsActivity.class);
                startActivity(intent);
            }
        });
    }

    public boolean firstAppStart() {
        boolean first = false;
        SharedPreferences sharedPreferences = getSharedPreferences("firstStartApp", MODE_PRIVATE);
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        if (sharedPreferences.getBoolean("firstStartApp", false) == false){
            first = true;
            sharedPreferencesEditor.putBoolean("firstStartApp", true);
            sharedPreferencesEditor.commit();
        }

        return first;
    }

    public void createDatabase() {
        SQLiteDatabase database = getBaseContext().openOrCreateDatabase(MainActivity.databaseName, MODE_PRIVATE, null);
        database.execSQL("CREATE TABLE " + AppointmentsActivity.TABLEAPPOINTMENT + "(" +
                AppointmentsActivity.COLUMN_CONTACTID + " TEXT NOT NULL, " +
                AppointmentsActivity.COLUMN_DATE + " TEXT NOT NULL, " +
                AppointmentsActivity.COLUMN_TIME + " TEXT NOT NULL, " +
                AppointmentsActivity.COLUMN_DURATION + " TEXT NOT NULL);");
        database.close();
    }


}