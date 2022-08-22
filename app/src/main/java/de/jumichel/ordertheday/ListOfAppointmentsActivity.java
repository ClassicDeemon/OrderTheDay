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

public class ListOfAppointmentsActivity extends AppCompatActivity {

    int clickedID;

    TableLayout table;
    TableRow tableRow;
    TextView textName, textDate, textTime, textDuration;

    Button button_delete_appointment;

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

    public void listAllAppointments() {
        table.removeViews(1, Math.max(0, table.getChildCount() - 1));

        SQLiteDatabase database = getBaseContext().openOrCreateDatabase(MainActivity.databaseName, MODE_PRIVATE, null);
        Cursor cursor = database.rawQuery("SELECT a.id, a.forname, a.surname, b.date, b.time, b.duration FROM " + MainActivity.tableName +
                " AS a LEFT JOIN " + AppointmentsActivity.TABLEAPPOINTMENT + " AS b ON b." + AppointmentsActivity.COLUMN_CONTACTID
                + " = a." + MainActivity.COLUMN_ID, null);
        cursor.moveToFirst();

        if (cursor.getCount() > 0) {
            StringBuilder stringBuilder = new StringBuilder();

            while (cursor.moveToNext()) {
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

                textDate = new TextView(this);
                textDate.setWidth(98);
                textDate.setText(cursor.getString(3));
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
            }
            cursor.close();
            database.close();
        }
    }
    public void onClickTableRow(View view, TableRow row) {

        if(clickedID != 0) {
            row = findViewById(clickedID);
            row.setBackgroundColor(getResources().getColor(R.color.white));

        }

        clickedID = view.getId();
        row = findViewById(clickedID);
        row.setBackgroundColor(getResources().getColor(R.color.teal_200));

    }

    public boolean deleteAppointment() {
        boolean check = false;
        if(clickedID != 0) {
            SQLiteDatabase database = getBaseContext().openOrCreateDatabase(MainActivity.databaseName, MODE_PRIVATE, null);
            database.execSQL("DELETE FROM " + AppointmentsActivity.TABLEAPPOINTMENT + " WHERE " + AppointmentsActivity.COLUMN_CONTACTID + " = " + clickedID);
            database.close();
            check = true;
        }
        return check;
    }

}