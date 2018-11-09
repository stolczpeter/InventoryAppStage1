package com.example.peterstolcz.inventoryappstage1;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.peterstolcz.inventoryappstage1.data.BookstoreContract.BookstoreEntry;
import com.example.peterstolcz.inventoryappstage1.data.BookstoreDbHelper;

public class BookstoreActivity extends AppCompatActivity {

    private BookstoreDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookstore);

        FloatingActionButton additem = findViewById(R.id.add_item);
        additem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BookstoreActivity.this, ModifyActivity.class);
                startActivity(intent);
            }
        });

        mDbHelper = new BookstoreDbHelper(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    private void displayDatabaseInfo() {

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                BookstoreEntry._ID,
                BookstoreEntry.COLUMN_BOOK_NAME,
                BookstoreEntry.COLUMN_BOOK_QUANTITY,
                BookstoreEntry.COLUMN_BOOK_PRICE,
                BookstoreEntry.COLUMN_SUPPLIER_NAME,
                BookstoreEntry.COLUMN_SUPPLIER_PHONE_NUMBER
        };

        Cursor cursor = db.query(
                BookstoreEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);

        TextView displayView = findViewById(R.id.text_view_bookstore);

        try {

            displayView.setText("The inventory contains " + cursor.getCount() + " book(s).\n\nThe details of the books stored are as follows: \n\n");
            displayView.append(BookstoreEntry._ID + " / " +
                    BookstoreEntry.COLUMN_BOOK_NAME + " / " +
                    BookstoreEntry.COLUMN_BOOK_PRICE + " / " +
                    BookstoreEntry.COLUMN_BOOK_QUANTITY + " / " +
                    BookstoreEntry.COLUMN_SUPPLIER_NAME + " / " +
                    BookstoreEntry.COLUMN_SUPPLIER_PHONE_NUMBER + "\n");

            int idColumnIndex = cursor.getColumnIndex(BookstoreEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(BookstoreEntry.COLUMN_BOOK_NAME);
            int priceColumnIndex = cursor.getColumnIndex(BookstoreEntry.COLUMN_BOOK_PRICE);
            int quantityColumnIndex = cursor.getColumnIndex(BookstoreEntry.COLUMN_BOOK_QUANTITY);
            int suppliernameColumnIndex = cursor.getColumnIndex(BookstoreEntry.COLUMN_SUPPLIER_NAME);
            int supplierphonenumberColumnIndex = cursor.getColumnIndex(BookstoreEntry.COLUMN_SUPPLIER_PHONE_NUMBER);

            while (cursor.moveToNext()) {

                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                int currentPrice = cursor.getInt(priceColumnIndex);
                int currentQuantity = cursor.getInt(quantityColumnIndex);
                int Suppliers = cursor.getInt(suppliernameColumnIndex);

                String currentSuppliername=null;

                if(Suppliers==0){
                    currentSuppliername = "Unknown";
                }else if(Suppliers==1){
                    currentSuppliername = "Goodbooks";
                }else if(Suppliers==2){
                    currentSuppliername = "Readwell";
                }else if(Suppliers==3){
                    currentSuppliername = "Fictionfans";
                }else {
                    currentSuppliername = "Bookhouse";
                }
                String currentSupplierphonenumber = cursor.getString(supplierphonenumberColumnIndex);

                displayView.append(("\n" + currentID + " / " +
                        currentName + " / " +
                        currentPrice + " / " +
                        currentQuantity + " / " +
                        currentSuppliername + " / " +
                        currentSupplierphonenumber));
            }
        } finally {

            cursor.close();
        }
    }
}