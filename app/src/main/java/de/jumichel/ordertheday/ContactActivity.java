package de.jumichel.ordertheday;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
//Klasse zur Kontakt-Verwaltung
public class ContactActivity extends AppCompatActivity implements View.OnClickListener {

    //alle wichtigen Variablen und Views
    int clickedID;

    Button button_add;
    Button button_search;
    Button button_searchPopup;
    Button button_addPopup;
    Button button_deleteContact;

    EditText text_forname;
    EditText text_surname;
    EditText text_phonenumber;
    EditText text_street;
    EditText text_number;
    EditText text_postcode;
    EditText text_city;
    EditText text_searchForname;
    EditText text_searchSurname;
    TextView text_contacts;

    TableLayout table;
    TableRow tableRow;
    TextView textFornameTable, textSurnameTable, textPhonenumberTable, textAddressTable;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        button_search = (Button) findViewById(R.id.button_search);
        button_add = findViewById(R.id.button_add);
        button_deleteContact = findViewById(R.id.button_deleteContact);

        table = findViewById(R.id.tableLayoutContact);

        button_add.setOnClickListener(this);
        button_search.setOnClickListener(this);
        button_deleteContact.setOnClickListener(this);

        //alle Kontakte sollen in der delarierten Tabelle aufgerufen werden
        listAllContacts(table);

    }
    //alle Kontakte werden in der Tabelle ausgegeben
    public void listAllContacts(TableLayout table) {
        table.removeViews(1, Math.max(0, table.getChildCount() - 1));

        SQLiteDatabase database = getBaseContext().openOrCreateDatabase(MainActivity.databaseName, MODE_PRIVATE, null);
        Cursor cursor = database.rawQuery("SELECT * FROM " + MainActivity.tableName + " ORDER BY " + MainActivity.COLUMN_SURNAME + " ASC, " +
                MainActivity.COLUMN_FORNAME + " ASC", null);
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

                textFornameTable = new TextView(this);
                textFornameTable.setWidth(85);
                textFornameTable.setText(cursor.getString(1));
                tableRow.addView(textFornameTable);

                textSurnameTable = new TextView(this);
                textSurnameTable.setWidth(85);
                textSurnameTable.setText(cursor.getString(2));
                tableRow.addView(textSurnameTable);

                textPhonenumberTable = new TextView(this);
                textPhonenumberTable.setWidth(105);
                textPhonenumberTable.setText(cursor.getString(3));
                tableRow.addView(textPhonenumberTable);

                textAddressTable = new TextView(this);
                textAddressTable.setWidth(115);
                textAddressTable.setText(cursor.getString(4));
                tableRow.addView(textAddressTable);

                table.addView(tableRow);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
    }


    //Der Kontakt wird in die Datenbank eingef??gt
    public boolean addContact(Contact contact) {
        boolean check = false;
        try {
            SQLiteDatabase database = getBaseContext().openOrCreateDatabase(MainActivity.databaseName, MODE_PRIVATE, null);
            database.execSQL("INSERT INTO " + MainActivity.tableName + " (" + MainActivity.COLUMN_FORNAME + ", "
                    + MainActivity.COLUMN_SURNAME + ", " + MainActivity.COLUMN_PHONENUMBER + ", " + MainActivity.COLUMN_ADDRESS +
                    ")VALUES('" + contact.getForname() + "','" + contact.getSurname() + "','"
                    + contact.getPhonenumber() + "','" + contact.getAddress() + "')");
            database.close();
            check = true;
        } catch(Exception e) {
            check=false;
            e.printStackTrace();
        }
        return check;
    }

    //Ausgew??hlter Kontakte wird gel??scht
    public boolean deleteContact() {
        boolean check = false;
            if(clickedID != 0) {
                SQLiteDatabase database = getBaseContext().openOrCreateDatabase(MainActivity.databaseName, MODE_PRIVATE, null);
                database.execSQL("DELETE FROM " + MainActivity.tableName + " WHERE " + MainActivity.COLUMN_ID + " = " + clickedID);
                database.close();
                check = true;
            }
        return check;
    }

    //der eingegebene Kontakt wird gesucht und Ausgegeben
    public boolean getContact(String forname, String surname) {
        boolean check = false;
        try {
            SQLiteDatabase database = getBaseContext().openOrCreateDatabase(MainActivity.databaseName, MODE_PRIVATE, null);
            Cursor cursor = database.rawQuery("SELECT * FROM " + MainActivity.tableName + " WHERE " + MainActivity.COLUMN_FORNAME
                    + " = '" + forname
                    + "' AND " + MainActivity.COLUMN_SURNAME + " = '" + surname + "'", null);
            cursor.moveToFirst();
            String ausgabe = "";
            if(cursor.getCount() == 0) {
                ausgabe = "Keinen Kontakt gefunden.";
            } else {
                ausgabe = "";
                for (int i = 1; i < cursor.getColumnCount(); i++) {
                    ausgabe = ausgabe + cursor.getString(i) + " ";
                }
            }
            text_contacts.setText(ausgabe);
            cursor.close();
            database.close();
            check = true;
        } catch (Exception e) {
            e.printStackTrace();

            check = false;
        }
        return check;
    }

    //Kontakt wird erstellt und richtig formatiert
    public void createContact() {
        String forname, surname;
        forname = text_forname.getText().toString().trim();
        surname = text_surname.getText().toString().trim();
        if(!(forname.isEmpty() || surname.isEmpty())) {
            String phonenumber, street, number, postcode, city;
            phonenumber = text_phonenumber.getText().toString().trim();
            street = text_street.getText().toString().trim();
            number = text_number.getText().toString().trim();
            postcode = text_postcode.getText().toString().trim();
            city = text_city.getText().toString().trim();

            if (phonenumber.isEmpty()) { phonenumber = "---";}
            if (street.isEmpty()) { street = "---";}
            if (number.isEmpty()) { number = "---";}
            if (postcode.isEmpty()) { postcode = "---";}
            if (city.isEmpty()) { city = "---";}

            if (checkContact(text_forname.getText().toString().trim(), text_surname.getText().toString().trim())) {

                Contact contact = new Contact(text_forname.getText().toString().trim(), text_surname.getText().toString().trim(),
                        phonenumber, street, number, postcode, city);

                if (addContact(contact)) {
                    Toast.makeText(getApplicationContext(), "Kontakt wurde hinzugef??gt.",
                            Toast.LENGTH_SHORT).show();
                    text_forname.setText("");
                    text_surname.setText("");
                    text_phonenumber.setText("");
                    text_street.setText("");
                    text_number.setText("");
                    text_postcode.setText("");
                    text_city.setText("");
                } else {
                    Toast.makeText(getApplicationContext(), "Kontakt konnte nicht hinzugef??gt werden.",
                            Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Kontakt bereits vorhanden.",
                        Toast.LENGTH_SHORT).show();
                text_forname.setText("");
                text_surname.setText("");
                text_phonenumber.setText("");
                text_street.setText("");
                text_number.setText("");
                text_postcode.setText("");
                text_city.setText("");
            }
        } else {
            Toast.makeText(getApplicationContext(), "Ein Kontakt muss einen Vor- und Nachnamen besitzen",
                    Toast.LENGTH_SHORT).show();
        }
    }

    //es wird ??berpr??ft, ob der Kontakt bereits vorhanden ist
    public boolean checkContact(String forname, String surname) {
        boolean okay = false;
        SQLiteDatabase databaseUser = getBaseContext().openOrCreateDatabase(MainActivity.databaseName, MODE_PRIVATE, null);
        Cursor cursorUser = databaseUser.rawQuery("SELECT COUNT(*) FROM "+MainActivity.tableName+" " +
                "WHERE "+MainActivity.COLUMN_FORNAME+" = '" + forname + "' AND "+MainActivity.COLUMN_SURNAME+" = '" + surname + "'", null);
        cursorUser.moveToFirst();
        if(cursorUser.getInt(0) == 0) {
            okay = true;
        }
        cursorUser.close();
        databaseUser.close();
        return okay;
    }

    //Popup wird ge??ffnet und die einzelnen Komponenten dessen geladen und verarbeitet
    //Alle n??tigen Dinge f??r das Suchen des Kontaktes zu suchen
    public void showPopup(View view) {
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popupsearchcontact, null);

        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        text_searchForname = (EditText) popupView.findViewById(R.id.textSearchForname);
        text_searchSurname = (EditText) popupView.findViewById(R.id.textSearchSurname);
        text_contacts = (TextView) popupView.findViewById(R.id.text_contact);

        button_searchPopup = popupView.findViewById(R.id.button_searchPopup);
        button_searchPopup.setOnClickListener(this);
    }

    //Popup wird ge??ffnet und einzelne Komponenten geladen und benutzt damit der Kontakt hinzugef??gt werden kann
    //Die EditText f??r die Erstellung des Kontaktes werden hier erstellt
    public void contactAddPopup(View view) {
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popupaddcontact, null);

        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        text_forname = (EditText) popupView.findViewById(R.id.textForname);
        text_surname = (EditText) popupView.findViewById(R.id.textSurname);
        text_phonenumber = (EditText) popupView.findViewById(R.id.textPhonenumber);
        text_street = (EditText) popupView.findViewById(R.id.textStreet);
        text_number = (EditText) popupView.findViewById(R.id.textNumber);
        text_postcode = (EditText) popupView.findViewById(R.id.textPostcode);
        text_city = (EditText) popupView.findViewById(R.id.textCity);

        button_addPopup = (Button) popupView.findViewById(R.id.button_addPopup);

        button_addPopup.setOnClickListener(this);
    }

    //auf Klick der Zeile der Tabelle wird diese ausgew??hlt
    //Kontakt wird ausgew??hlt
    public void onClickTableRow(View view, TableRow table) {

        if(clickedID != 0) {
            table = findViewById(clickedID);
            table.setBackgroundColor(getResources().getColor(R.color.white));

            }

        clickedID = view.getId();
        table = findViewById(clickedID);
        table.setBackgroundColor(getResources().getColor(R.color.teal_200));

    }

    //fast alle im Code beschriebene Kn??pfe werden hier wenn sie geklickt werden ausgef??hrt
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.button_addPopup:
                createContact();
                listAllContacts(table);
                break;
            case R.id.button_add:
                contactAddPopup(view);
                break;
            case R.id.button_search:
                showPopup(view);
                break;
            case R.id.button_searchPopup:
                getContact(text_searchForname.getText().toString().trim(), text_searchSurname.getText().toString().trim());
                break;
            case R.id.button_deleteContact:
                if(deleteContact()) {
                    Toast.makeText(getApplicationContext(), "Der Kontakt wurde gel??scht.", Toast.LENGTH_SHORT).show();
                    clickedID = 0;
                } else {
                    Toast.makeText(getApplicationContext(), "Ein Kontakt muss ausgew??hlt werden.", Toast.LENGTH_SHORT).show();
                }
                listAllContacts(table);
        }
    }
}