package com.cs3714.sojo.proj;

/**
 * Created by SOJO on 8/8/15.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

public class Ingredients extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "IngredientsDB";

    public Ingredients(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create book table
        String CREATE_INGREDIENTS_TABLE = "CREATE TABLE ingredients ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, "+
                "type TEXT )";

        // create books table
        db.execSQL(CREATE_INGREDIENTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older books table if existed
        db.execSQL("DROP TABLE IF EXISTS ingredients");

        // create fresh books table
        this.onCreate(db);
    }
    //---------------------------------------------------------------------

    /**
     * CRUD operations (create "add", read "get", update, delete) book + get all books + delete all books
     */

    // Books table name
    private static final String TABLE_INGREDIENTS = "ingredients";

    // Books Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_TYPE = "type";

    private static final String[] COLUMNS = {KEY_ID,KEY_NAME,KEY_TYPE};

    public void addIngredient(Ingredient ing){
        Log.d("addIngredient", ing.toString());
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, ing.getName()); // get title
        values.put(KEY_TYPE, ing.getType()); // get author

        // 3. insert
        db.insert(TABLE_INGREDIENTS, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    public Ingredient getIngredient(int id){

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(TABLE_INGREDIENTS, // a. table
                        COLUMNS, // b. column names
                        " id = ?", // c. selections
                        new String[] { String.valueOf(id) }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // 4. build book object
        Ingredient ing = new Ingredient();
        ing.setID(Integer.parseInt(cursor.getString(0)));
        ing.setName(cursor.getString(1));
        ing.setType(cursor.getString(2));

        Log.d("getIng (" + id + ")", ing.toString());

        // 5. return book
        return ing;
    }

    // Get All Books
    public List<Ingredient> getAllIngredients() {
        List<Ingredient> ings = new LinkedList<Ingredient>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_INGREDIENTS;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build book and add it to list
        Ingredient ing = null;
        if (cursor.moveToFirst()) {
            do {
                ing = new Ingredient();
                ing.setID(Integer.parseInt(cursor.getString(0)));
                ing.setName(cursor.getString(1));
                ing.setType(cursor.getString(2));

                // Add book to books
                ings.add(ing);
            } while (cursor.moveToNext());
        }

        Log.d("getAllIngs()", ings.toString());

        // return books
        return ings;
    }

    // Get All Books
    public List<String> getAllNames() {
        List<String> ings = new LinkedList<>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_INGREDIENTS;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build book and add it to list

        Ingredient ing = null;
        if (cursor.moveToFirst()) {
            do {
                ing = new Ingredient();
                ing.setName(cursor.getString(1));


                // Add book to books
                ings.add(ing.getName());
            } while (cursor.moveToNext());
        }

        Log.d("getAllIngs()", ings.toString());

        // return books
        return ings;
    }


    // Updating single book
    public int updateIngredient(Ingredient ing) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("name", ing.getName()); // get title
        values.put("type", ing.getType()); // get author

        // 3. updating row
        int i = db.update(TABLE_INGREDIENTS, //table
                values, // column/value
                KEY_ID+" = ?", // selections
                new String[] { String.valueOf(ing.getID()) }); //selection args

        // 4. close
        db.close();

        return i;

    }

    // Deleting single book
    public void deleteIngredient(Ingredient ing) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_INGREDIENTS,
                KEY_ID+" = ?",
                new String[] { String.valueOf(ing.getID()) });

        // 3. close
        db.close();

        Log.d("deleteIngredient", ing.toString());

    }
    public boolean hasObject(String id) {
        SQLiteDatabase db = getWritableDatabase();
        String selectString = "SELECT * FROM " + TABLE_INGREDIENTS + " WHERE " + KEY_NAME + " =?";

        // Add the String you are searching by here.
        // Put it in an array to avoid an unrecognized token error
        Cursor cursor = db.rawQuery(selectString, new String[] {id});

        boolean hasObject = false;
        if(cursor.moveToFirst()){
            hasObject = true;

            //region if you had multiple records to check for, use this region.

            int count=0;
            while(!cursor.isLast()){
                count++;
            }
            //here, count is records found
            Log.d("", String.format("%d records found", count));

            //endregion

        }

        cursor.close();          // Dont forget to close your cursor
        db.close();              //AND your Database!
        return hasObject;
    }
}
