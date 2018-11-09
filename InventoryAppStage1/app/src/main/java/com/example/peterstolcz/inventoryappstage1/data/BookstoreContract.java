package com.example.peterstolcz.inventoryappstage1.data;

import android.provider.BaseColumns;

public class BookstoreContract {

    public BookstoreContract() {}

    public final static class BookstoreEntry implements BaseColumns {

        public final static String TABLE_NAME = "bookstore";

        public final static String _ID = BaseColumns._ID;

        public final static String COLUMN_BOOK_NAME ="Name";

        public final static String COLUMN_BOOK_PRICE = "Price";

        public final static String COLUMN_BOOK_QUANTITY = "Quantity";

        public final static String COLUMN_SUPPLIER_NAME = "Supplier_name";

        public final static String COLUMN_SUPPLIER_PHONE_NUMBER = "Supplier_phone_number";

        public static final int SUPPLIER_UNKNOWN = 0;
        public static final int SUPPLIER_GOODBOOKS = 1;
        public static final int SUPPLIER_FICTIONFANS = 2;
        public static final int SUPPLIER_BOOKHOUSE = 3;
        public static final int SUPPLIER_READWELL = 4;


    }

}