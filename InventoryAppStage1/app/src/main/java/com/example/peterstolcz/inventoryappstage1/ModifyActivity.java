package com.example.peterstolcz.inventoryappstage1;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.peterstolcz.inventoryappstage1.data.BookstoreContract.BookstoreEntry;
import com.example.peterstolcz.inventoryappstage1.data.BookstoreDbHelper;

public class ModifyActivity extends AppCompatActivity {


    private EditText mNameEditText;

    private EditText mPriceEditText;

    private EditText mQuantityEditText;

    public Spinner mSupplierNameSpinner;

    public TextView mSupplierPhoneTextView;

    private int mSupplierName = BookstoreEntry.SUPPLIER_UNKNOWN;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        mNameEditText = findViewById(R.id.modify_book_name);
        mPriceEditText = findViewById(R.id.modify_book_price);
        mQuantityEditText = findViewById(R.id.modify_book_quantity);
        mSupplierNameSpinner = findViewById(R.id.spinner_supplier_name);
        mSupplierPhoneTextView = findViewById(R.id.supplier_phone_number);

        setupSpinner();
    }

    private void setupSpinner() {

        ArrayAdapter supplierSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_supplier_options, android.R.layout.simple_spinner_item);

        supplierSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        mSupplierNameSpinner.setAdapter(supplierSpinnerAdapter);

        mSupplierNameSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.supplier_readwell))) {
                        mSupplierName = BookstoreEntry.SUPPLIER_READWELL;
                        mSupplierPhoneTextView.setText(R.string.phonenumber_readwell);
                    } else if (selection.equals(getString(R.string.supplier_goodbooks))) {
                        mSupplierName = BookstoreEntry.SUPPLIER_GOODBOOKS;
                        mSupplierPhoneTextView.setText(R.string.phonenumber_goodbooks);
                    } else if (selection.equals(getString(R.string.supplier_fictionfans))) {
                        mSupplierName = BookstoreEntry.SUPPLIER_FICTIONFANS;
                        mSupplierPhoneTextView.setText(R.string.phonenumber_fictionfans);
                    } else if (selection.equals(getString(R.string.supplier_bookhouse))) {
                        mSupplierName = BookstoreEntry.SUPPLIER_BOOKHOUSE;
                        mSupplierPhoneTextView.setText(R.string.phonenumber_bookhouse);
                    } else {
                        mSupplierName = BookstoreEntry.SUPPLIER_UNKNOWN;
                        mSupplierPhoneTextView.setText(R.string.phonenumber_none);
                    }

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mSupplierName = BookstoreEntry.SUPPLIER_UNKNOWN;
            }
        });
    }

    private void insertBook() {
        String nameString = mNameEditText.getText().toString().trim();

        String priceString = mPriceEditText.getText().toString().trim();
        int price = Integer.parseInt(priceString);

        String quantityString = mQuantityEditText.getText().toString().trim();
        int quantity = Integer.parseInt(quantityString);

        String supplierphoneString = mSupplierPhoneTextView.getText().toString().trim();


        BookstoreDbHelper mDbHelper = new BookstoreDbHelper(this);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(BookstoreEntry.COLUMN_BOOK_NAME, nameString);
        values.put(BookstoreEntry.COLUMN_BOOK_PRICE, price);
        values.put(BookstoreEntry.COLUMN_BOOK_QUANTITY, quantity);
        values.put(BookstoreEntry.COLUMN_SUPPLIER_NAME, mSupplierName);
        values.put(BookstoreEntry.COLUMN_SUPPLIER_PHONE_NUMBER, supplierphoneString);

        long newRowId = db.insert(BookstoreEntry.TABLE_NAME, null, values);

        if (newRowId == -1) {
            Toast.makeText(this, "Error saving book", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Book saved with following row id: " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_modify, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.save:
                String nameCheck = mNameEditText.getText().toString().trim();
                String priceCheck = mPriceEditText.getText().toString().trim();
                String quantityCheck = mQuantityEditText.getText().toString().trim();

                if(quantityCheck.isEmpty()&& priceCheck.isEmpty()||quantityCheck.isEmpty()&&nameCheck.isEmpty()||priceCheck.isEmpty()&&nameCheck.isEmpty()) {
                    Toast.makeText(this, "Necessary information missing.", Toast.LENGTH_SHORT).show();
                }else if(nameCheck.isEmpty()){
                    Toast.makeText(this, "No name has been entered.", Toast.LENGTH_SHORT).show();
                }else if(priceCheck.isEmpty()){
                    Toast.makeText(this, "No price has been entered.", Toast.LENGTH_SHORT).show();
                }else if(quantityCheck.isEmpty()){
                    Toast.makeText(this, "No quantity has been entered.", Toast.LENGTH_SHORT).show();

                } else {
                    insertBook();
                    NavUtils.navigateUpFromSameTask(this);
                    return true;
                }

        }
        return super.onOptionsItemSelected(item);
    }

}
