package com.heady.rekha.headyassessment.data.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.heady.rekha.headyassessment.BuildConfig;

/**
 * A custom Content Provider to do the database operations
 */
public class AppContentProvider extends ContentProvider {

    public static final String PROVIDER_NAME = BuildConfig.APP_MODULE_PACKAGE_NAME+".db.AppContentProvider";

    public static final Uri CONTENT_URI_CATEGORIES = Uri.parse("content://" + PROVIDER_NAME + "/" + DatabaseContract.Categories.TABLE_NAME);
    public static final Uri CONTENT_URI_PRODUCTS = Uri.parse("content://" + PROVIDER_NAME + "/" + DatabaseContract.Products.TABLE_NAME);

    /**
     * Constants to identify the requested operation
     */
    private static final int TABLE_CATEGORIES = 1;
    private static final int TABLE_PRODUCTS = 2;
    private static final UriMatcher uriMatcher;
    private static final String TAG = AppContentProvider.class.getName();

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, DatabaseContract.Categories.TABLE_NAME, TABLE_CATEGORIES);
        uriMatcher.addURI(PROVIDER_NAME, DatabaseContract.Products.TABLE_NAME, TABLE_PRODUCTS);
    }

    /**
     * This content provider does the database operations by this object
     */
    DatabaseHelper dbHelper;

    private SQLiteDatabase sqlDB;

    /**
     * A callback method which is invoked when the content provider is starting
     * up
     */
    @Override
    public boolean onCreate() {
        dbHelper = DatabaseHelper.getInstance(getContext());
        return true;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }


    /**
     * A callback method which is by the default content uri
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        sqlDB = dbHelper.getWritableDatabase();
        Cursor cursor;
        cursor = sqlDB.query(getTableName(uri), projection, selection, selectionArgs, null, null, sortOrder);

        if (cursor != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
            return cursor;
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        sqlDB = dbHelper.getWritableDatabase();
        int deleteCount = sqlDB.delete(getTableName(uri), selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return deleteCount;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        sqlDB = dbHelper.getWritableDatabase();
        long rowId = sqlDB.insertWithOnConflict(getTableName(uri), null, values, SQLiteDatabase.CONFLICT_REPLACE);
        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, rowId);
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        sqlDB = dbHelper.getWritableDatabase();
        int numOfRowsInserted = 0;
        sqlDB.beginTransaction();
        try {
            for (ContentValues contentValues : values) {
                long rowId = sqlDB.insertWithOnConflict(getTableName(uri), null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
                if (rowId < 0) {
                    throw new SQLException("Failed to insert row into " + uri);
                }
            }
            sqlDB.setTransactionSuccessful();
            getContext().getContentResolver().notifyChange(uri, null);
            numOfRowsInserted = values.length;
        } finally {
            sqlDB.endTransaction();
        }

        return numOfRowsInserted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        sqlDB = dbHelper.getWritableDatabase();
        int rowAffected = sqlDB.update(getTableName(uri), values, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return rowAffected;
    }

    private String getTableName(Uri uri) {
        return uriMatcher.match(uri) != -1 ? uri.getPath().replace('/', ' ').trim() : null;
    }
}
