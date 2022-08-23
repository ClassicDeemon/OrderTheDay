package de.jumichel.ordertheday;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/*
    MainActivity zeigt die Startseite der App
    Diese zeigt das Logo und drei Knöpfe
    Mit der Aufschrift "Termine verwalten" kann man Termine verwalten
    Mit der Aufschrift "Kontakte verwalten" kann man Kontakte verwalten
    Mit der Aufschrift "Alle Termine auflisten" sieht man eine Übersicht aller Termine
*/

public class MainActivity extends AppCompatActivity {

    //Hier werden für die Tabelle der Kontakte die Spalten definiert
    public static final String COLUMN_ID = "con_id";
    public static final String COLUMN_FORNAME = "con_forname";
    public static final String COLUMN_SURNAME = "con_surname";
    public static final String COLUMN_PHONENUMBER = "con_phonenumber";
    public static final String COLUMN_ADDRESS = "con_address";
    //Deklaration der Tabellennamen der Kontakte
    public static final String tableName = "contactbook";
    //Der Datenbankname wird deklariert
    public static final String databaseName = "contactbook.db";

    //die erwähnten Knöpfe werden definiert
    Button button_contact;
    Button button_appointment;
    Button button_list_appointments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Die zuvor definierten Knöpfe werden zu einer ID zugewiesen
        button_contact = findViewById(R.id.btnContact);
        button_appointment = findViewById(R.id.btnAppointment);
        button_list_appointments = findViewById(R.id.btnListAppointsments);

        //wenn die App zum ersten Mal startet, wird die Database erstellt
        if(firstAppStart()) {
            createDatabase();
        }

        //Wenn der Knopf "Kontakte verwalten" gedrückt wird, wird in die Klasse "ContactActivity" weitergeleitet
        button_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ContactActivity.class);
                startActivity(intent);
            }
        });
        //Wenn der Knopf "Termine verwalten" gedrückt wird, wird in die Klasse Appointments weitergeleitet
        button_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Appointments.class);
                startActivity(intent);
            }
        });
        //Wenn der Knopf "Alle Termine auflisten" gedrückt wird, wird in die Klasse ListOfAppointmentsActivity weitergeleitet
        button_list_appointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListOfAppointmentsActivity.class);
                startActivity(intent);
            }
        });
    }

    //Abfrage, ob die App zum ersten Mal startet
    //in die sharedPreferences wird unter dem Namen "firstAppStart" ein boolean-Wert hinterlegt
    //dies passiert beim ersten Start
    public boolean firstAppStart() {
        boolean first = false;
        SharedPreferences sharedPreferences = getSharedPreferences("firstAppStart", MODE_PRIVATE);
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        //falls der Wert false ist,...
        if (sharedPreferences.getBoolean("firstStartApp", false) == false){
            first = true;
            //wird er auf true gesetzt
            sharedPreferencesEditor.putBoolean("firstStartApp", true);
            sharedPreferencesEditor.commit();
        }

        return first;
    }

    //Methode zur Erstellung beider notwendigen Tabellen (Kontakte, Termine)
    //Mit Hilfe von SQLite
    public void createDatabase() {
        //Die Datenbank wird geöffnet oder erstellt
        SQLiteDatabase database = getBaseContext().openOrCreateDatabase(MainActivity.databaseName, MODE_PRIVATE, null);
        //Hier wird ein SQLite Befehl ausgeführt
        //Es wird eine Tabelle erstellt mit den Spalten, die oben deklariert wurden sind
        database.execSQL("CREATE TABLE " + MainActivity.tableName + "(" +
                MainActivity.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MainActivity.COLUMN_FORNAME + " TEXT NOT NULL, " +
                MainActivity.COLUMN_SURNAME + " TEXT NOT NULL, " +
                MainActivity.COLUMN_PHONENUMBER + " TEXT NOT NULL, " +
                MainActivity.COLUMN_ADDRESS + " TEXT NOT NULL);");
        //Hier wird ebenfalls eine Tabelle erstellt, mit den Spalten die in der AppointmentsActivity definiert werden
        database.execSQL("CREATE TABLE " + AppointmentsActivity.TABLEAPPOINTMENT + "(" +
                AppointmentsActivity.COLUMN_APPID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                AppointmentsActivity.COLUMN_CONTACTID + " TEXT NOT NULL, " +
                AppointmentsActivity.COLUMN_DATE + " TEXT NOT NULL, " +
                AppointmentsActivity.COLUMN_TIME + " TEXT NOT NULL, " +
                AppointmentsActivity.COLUMN_DURATION + " TEXT NOT NULL);");
        //Am Ende muss die Datenbank wieder geschlossen werden
        database.close();
    }


}