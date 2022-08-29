package de.jumichel.ordertheday;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
//Klasse wo alle termine aufgelistet werden und das Löschen dieser unternommen werden kann
public class ListOfAppointmentsActivity extends AppCompatActivity {

    int clickedID;

    TableLayout table;
    TableRow tableRow;
    TextView textName, textDate, textTime, textDuration;

    Button button_delete_appointment;

    //aufrufen aller wichtigen Methoden
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_appointments);

        table = findViewById(R.id.table_all_appointments);
        button_delete_appointment = findViewById(R.id.btnDeleteAppointment);

        listAllAppointments();

        button_delete_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(deleteAppointment()){
                    Toast.makeText(getBaseContext(), "Erfolgreich gelöscht.", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getBaseContext(), "Fehler", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    //alle Termine werden aufgelistet
    public void listAllAppointments() {
        table.removeViews(1, Math.max(0, table.getChildCount() - 1));

        SQLiteDatabase database = getBaseContext().openOrCreateDatabase(MainActivity.databaseName, MODE_PRIVATE, null);
        Cursor cursor = database.rawQuery("SELECT " +
                AppointmentsActivity.COLUMN_APPID+", " +
                MainActivity.COLUMN_FORNAME+", " +
                MainActivity.COLUMN_SURNAME+", " +
                AppointmentsActivity.COLUMN_DATE+", " +
                AppointmentsActivity.COLUMN_TIME+", " +
                AppointmentsActivity.COLUMN_DURATION+" " +
                "FROM "+AppointmentsActivity.TABLEAPPOINTMENT+" LEFT JOIN "+MainActivity.tableName+
                " ON "+AppointmentsActivity.COLUMN_CONTACTID+" = "+MainActivity.COLUMN_ID+ " " +
                " ORDER BY "+ AppointmentsActivity.COLUMN_DATE +" ASC, "+ AppointmentsActivity.COLUMN_TIME + " ASC ", null);
        cursor.moveToFirst();

        if (cursor.getCount() > 0) {
            StringBuilder stringBuilder = new StringBuilder();

            do {
                stringBuilder.append(cursor.getString(0));
                tableRow = new TableRow(this);
                tableRow.setId(cursor.getInt(0));
                tableRow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onClickTableRow(view, tableRow);
                    }
                });

                textName = new TextView(this);
                textName.setWidth(98);
                textName.setText(cursor.getString(1) + " " + cursor.getString(2));
                textName.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                tableRow.addView(textName);

                String[] myDate = cursor.getString(3).split("-");

                textDate = new TextView(this);
                textDate.setWidth(98);
                textDate.setText(myDate[2] + "." + myDate[1] + "." + myDate[0]);
                textDate.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                tableRow.addView(textDate);

                textTime = new TextView(this);
                textTime.setWidth(97);
                textTime.setText(cursor.getString(4));
                textTime.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                tableRow.addView(textTime);

                textDuration = new TextView(this);
                textDuration.setWidth(97);
                textDuration.setText(cursor.getString(5));
                textDuration.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                tableRow.addView(textDuration);

                table.addView(tableRow);
            } while (cursor.moveToNext());
            cursor.close();
            database.close();
        }
    }
    //wenn auf eine Zeile geklickt wird, wird diese ausgewählt
    public void onClickTableRow(View view, TableRow row) {

        if(clickedID != 0) {
            row = findViewById(clickedID);
            row.setBackgroundColor(getResources().getColor(R.color.white));

        }

        clickedID = view.getId();
        row = findViewById(clickedID);
        row.setBackgroundColor(getResources().getColor(R.color.teal_200));

    }
    //ausgewählter Termin wird gelöscht
    public boolean deleteAppointment() {
        boolean check = false;
        if(clickedID != 0) {
            SQLiteDatabase database = getBaseContext().openOrCreateDatabase(MainActivity.databaseName, MODE_PRIVATE, null);
            database.execSQL("DELETE FROM " + AppointmentsActivity.TABLEAPPOINTMENT + " WHERE " + AppointmentsActivity.COLUMN_APPID + " = " + clickedID);
            database.close();
            check = true;
            listAllAppointments();
        }
        return check;
    }

}