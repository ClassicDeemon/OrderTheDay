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
//Klasse für Dauer und Datum definieren
public class Appointments extends AppCompatActivity {

    //globale Klassen Variable
    private Date date;
    private double duration;
    private String myDate;

    CalendarView calendar;
    EditText duration_text;

    //default Kontruktor
    public Appointments(){}

    /*
    Wenn die ACtivity startet wird das ausgeführt

     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments);

        calendar = findViewById(R.id.calendar);

        duration_text = findViewById(R.id.text_duration);

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){

            /*
            wenn ein Datum ausgewählt wird, wird falls die Dauer richtig eingetragen wurde
            Durch die OnSelectedDayChange wird der Tag, Monat und Jahr übergeben
            diese Variablen werden in die Klasse Datum als Konstruktor geschrieben
            diese und die Dauer werden in die Intents geschrieben für die nächste Klasse
             */

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int day) {
                String durationCheck = duration_text.getText().toString().trim();
                if (!durationCheck.isEmpty()) {
                    duration = Double.parseDouble(duration_text.getText().toString().replace(",", ".").trim());
                    if (duration <= 0) {
                        Toast.makeText(getApplicationContext(), "Es muss eine Dauer festgelegt werden, welche im positiven Bereich liegt.", Toast.LENGTH_SHORT).show();
                    } else {
                        date = new Date(day, month + 1, year);


                        String fm = String.format("%02d", month + 1);
                        String fd = String.format("%02d", day);
                        myDate = Integer.toString(year) + "-" + fm + "-" + fd;


                        Intent intent = new Intent(Appointments.this, AppointmentsActivity.class);
                        intent.putExtra("Date", myDate);
                        intent.putExtra("Duration", Double.toString(duration));
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Es muss eine Dauer festgelegt werden.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    //das datum setzen
    public void setDate(int year, int month, int day) {
        date.setDay(day);
        date.setMonth(month);
        date.setYear(year);
    }

}
