package com.heady.rekha.headyassessment.data.db;

import android.provider.BaseColumns;

public final class DatabaseContract {

    /**
     * Database name
     */
    public static final String DATABASE_NAME = "heady-db";
    /**
     * Version number of the database
     */
    public static final int DATABASE_VERSION = 1;

    private DatabaseContract() {
    }

    //Categories table
    public static abstract class Categories implements BaseColumns {

        public static final String TABLE_NAME = "Categories";

        public static final String COLUMN_CAT_ID = "cat_id";
        public static final String COLUMN_CAT_NAME = "cat_name";
        public static final String COLUMN_PRODUCT_IDS = "product_ids";

        public static final String sql_createTable = "create table " + TABLE_NAME + " ( "
                + _ID + " integer primary key autoincrement , "
                + COLUMN_CAT_ID + " text UNIQUE, "
                + COLUMN_CAT_NAME + " text, "
                + COLUMN_PRODUCT_IDS + " text )";

        public static final String[] mProjection = {_ID, COLUMN_CAT_ID, COLUMN_CAT_NAME, COLUMN_PRODUCT_IDS};
    }

    //products table
    public static abstract class Products implements BaseColumns {

        public static final String TABLE_NAME = "Products";

        public static final String COLUMN_PRODUCTS_ID = "products_id";
        public static final String COLUMN_PRODUCTS_JSON = "products_json";
        public static final String COLUMN_VIEW_COUNT = "view_count";
        public static final String COLUMN_ORDER_COUNT = "order_count";
        public static final String COLUMN_SHARES = "shares";
        //
        public static final String sql_createTable = "create table " + TABLE_NAME + " ( "
                + _ID + " integer primary key autoincrement , "
                + COLUMN_PRODUCTS_ID + " integer UNIQUE, "
                + COLUMN_VIEW_COUNT + " integer , "
                + COLUMN_ORDER_COUNT + " integer , "
                + COLUMN_SHARES + " integer , "
                + COLUMN_PRODUCTS_JSON + " text )";

        public static final String[] mProjection = {_ID, COLUMN_PRODUCTS_ID,
                COLUMN_PRODUCTS_JSON , COLUMN_VIEW_COUNT, COLUMN_ORDER_COUNT, COLUMN_SHARES};
    }

}