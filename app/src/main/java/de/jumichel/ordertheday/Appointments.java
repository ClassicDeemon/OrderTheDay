package de.jumichel.ordertheday;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import de.jumichel.ordertheday.ContactActivity;

public class Appointments extends AppCompatActivity {

    private Date date;
    private double duration;

    public Appointments(){}

    CalendarView calendar;
    EditText duration_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments);

        calendar = findViewById(R.id.calendar);

        duration_text = findViewById(R.id.text_duration);


        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int day) {
                duration = Integer.parseInt(duration_text.getText().toString().trim());
                date = new Date(day, month, year);

                Toast.makeText(getApplicationContext(), date.toString(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(Appointments.this, AppointmentsActivity.class);
                intent.putExtra("Date", date.toString());
                intent.putExtra("Duration", Double.toString(duration));
                startActivity(intent);

            }
        });
    }

   /* private void addToTableAppointment(Date date) {
        SQLiteDatabase database = getBaseContext().openOrCreateDatabase(MainActivity.databaseName, MODE_PRIVATE, null);
        database.execSQL("INSERT INTO " + AppointmentsActivity.TABLEAPPOINTMENT + " (" + AppointmentsActivity.COLUMN_DATE + ", "
                + AppointmentsActivity.COLUMN_DURATION + ")VALUES('" +  date.toString() + "','"
                + duration + "')");
        database.close();
    }*/
    public void setDate(int year, int month, int day) {
        date.setDay(day);
        date.setMonth(month);
        date.setYear(year);
    }

}
