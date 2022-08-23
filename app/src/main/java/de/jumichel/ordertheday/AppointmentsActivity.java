package de.jumichel.ordertheday;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class AppointmentsActivity extends AppCompatActivity {

    public static final String TABLEAPPOINTMENT = "appointment";
    public static final String COLUMN_CONTACTID = "app_conid";
    public static final String COLUMN_DATE = "app_date";
    public static final String COLUMN_DURATION = "app_duration";
    public static final String COLUMN_TIME = "app_time";
    public static final String COLUMN_APPID = "app_id";

    TableLayout tableContact;
    TableRow tableRowContact;
    TextView textFornameTable, textSurnameTable, textPhonenumberTable, textAddressTable;

    TableLayout tableTime;
    TableRow tableRowTime;
    TextView textFullHour, textHalfHour;

    Button button_book_appointment;

    int clickedIdContact;
    int clickedIdTime;

    String date;
    String duration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments2);

        button_book_appointment = findViewById(R.id.button_book_appointment);
        Intent intent = getIntent();
        duration = intent.getStringExtra("Duration");
        date = intent.getStringExtra("Date");

        button_book_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), clickedIdContact + " - " + date + " " + duration, Toast.LENGTH_SHORT).show();
                if(addAppointment()) {
                    Toast.makeText(getApplicationContext(), "Termin wurde hinzugefügt.",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Fehler",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        tableContact = findViewById(R.id.tableLayoutContact);
        listAllContacts(tableContact);

        tableTime = findViewById(R.id.tableLayoutTime);
        listAllTimes(tableTime);

    }

    private void listAllTimes(TableLayout table) {
        int idFull = 1;
        int idHalf = 2;
        for (int time = 0; time <= 24; time++) {

            tableRowTime = new TableRow(this);

            textFullHour = new TextView(this);
            textFullHour.setWidth(195);
            textFullHour.setText((time + ":00"));
            textFullHour.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            textFullHour.setId(idFull);
            idFull = idFull + 2;
            textFullHour.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickText(view, textFullHour);
                    Toast.makeText(getApplicationContext(), "ID: " + clickedIdTime, Toast.LENGTH_SHORT).show();
                }
            });
            tableRowTime.addView(textFullHour);

            textHalfHour = new TextView(this);
            textHalfHour.setWidth(195);
            textHalfHour.setText((time + ":30"));
            textHalfHour.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            textHalfHour.setId(idHalf);
            idHalf = idHalf + 2;
            textHalfHour.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickText(view, textHalfHour);
                }
            });
            tableRowTime.addView(textHalfHour);

            table.addView(tableRowTime);

        }
    }

    private void onClickText(View view, TextView text) {
        if (clickedIdTime != 0) {
            text = findViewById(clickedIdTime);
            text.setBackgroundColor(getResources().getColor(R.color.white));

        }

        clickedIdTime = view.getId();
        text = findViewById(clickedIdTime);
        text.setBackgroundColor(getResources().getColor(R.color.teal_200));

    }


    public void listAllContacts(TableLayout table) {
        table.removeViews(1, Math.max(0, table.getChildCount() - 1));

        SQLiteDatabase database = getBaseContext().openOrCreateDatabase(MainActivity.databaseName, MODE_PRIVATE, null);
        Cursor cursor = database.rawQuery("SELECT * FROM " + MainActivity.tableName + " ORDER BY " + MainActivity.COLUMN_SURNAME + " ASC, " +
                MainActivity.COLUMN_FORNAME + " ASC, " + MainActivity.COLUMN_ID + " ASC", null);
        cursor.moveToFirst();

        if (cursor.getCount() > 0) {
            StringBuilder stringBuilder = new StringBuilder();

            do {
                stringBuilder.append(cursor.getString(0));
                tableRowContact = new TableRow(this);
                tableRowContact.setId(cursor.getInt(0));
                tableRowContact.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onClickTableRow(view, tableRowContact);
                    }
                });

                textFornameTable = new TextView(this);
                textFornameTable.setWidth(85);
                textFornameTable.setText(cursor.getString(1));
                tableRowContact.addView(textFornameTable);

                textSurnameTable = new TextView(this);
                textSurnameTable.setWidth(85);
                textSurnameTable.setText(cursor.getString(2));
                tableRowContact.addView(textSurnameTable);

                textPhonenumberTable = new TextView(this);
                textPhonenumberTable.setWidth(105);
                textPhonenumberTable.setText(cursor.getString(3));
                tableRowContact.addView(textPhonenumberTable);

                textAddressTable = new TextView(this);
                textAddressTable.setWidth(115);
                textAddressTable.setText(cursor.getString(4));
                tableRowContact.addView(textAddressTable);

                table.addView(tableRowContact);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
    }

    public void onClickTableRow(View view, TableRow table) {

        if (clickedIdContact != 0) {
            table = findViewById(clickedIdContact);
            table.setBackgroundColor(getResources().getColor(R.color.white));

        }

        clickedIdContact = view.getId();
        table = findViewById(clickedIdContact);
        table.setBackgroundColor(getResources().getColor(R.color.teal_200));
        Toast.makeText(getApplicationContext(), "ID: " + clickedIdContact, Toast.LENGTH_SHORT).show();

    }

    public String convertToTime() {
        TextView text = findViewById(clickedIdTime);
        String txt = text.getText().toString().trim();
        String format = String.format("%5s", txt);
        format = format.replace(' ','0');
        return  format;
    }

    public boolean addAppointment() {
        boolean check = false;
        Appointments appointments = new Appointments();
        try {
            String myTime = convertToTime();
            Toast.makeText(getApplicationContext(), "TIME: " + myTime, Toast.LENGTH_SHORT).show();
            SQLiteDatabase database = getBaseContext().openOrCreateDatabase(MainActivity.databaseName, MODE_PRIVATE, null);
            database.execSQL("INSERT INTO " + TABLEAPPOINTMENT + " (" + COLUMN_CONTACTID + ", " + COLUMN_DATE + ", "
                    + COLUMN_TIME + ", " + COLUMN_DURATION + ")VALUES('" + clickedIdContact + "','" + date + "','"
                    + myTime + "','" + duration + "')");
            database.close();
            check = true;
        } catch (Exception e) {
            check = false;
            e.printStackTrace();
        }
        return check;
    }
}
