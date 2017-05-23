package robin.com.wifisensor.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import robin.com.wifisensor.model.QuizModel;

import java.util.ArrayList;

/**
 * Created by Bhadresh Chavada on 13-02-2017.
 */

public class IntuitveDatabase extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "INITUTIVE_DB";
    public static final int version = 1;

    public static final String TABLE_NAME = "INITUTIVE_IMAGE_TABLE";
    public static final String ID = "ID";
    public static final String IMAGE1 = "IMAGE1";
    public static final String IMAGE2 = "IMAGE2";
    public static final String IMAGE3 = "IMAGE3";
    public static final String IMAGE4 = "IMAGE4";
    public static final String QUIZTYPE = "QUIZTYPE";

    Context context;

    public static final String SQL_CREATE_TABLE_INITUTIVE = "CREATE TABLE " + TABLE_NAME + " (" +
            ID + " INTEGER PRIMARY KEY," +
            IMAGE1 + " TEXT," +
            IMAGE2 + " TEXT," + IMAGE3 + " TEXT," + IMAGE4 + " TEXT," + QUIZTYPE + " TEXT);";


    public IntuitveDatabase(Context context) {
        super(context, DATABASE_NAME, null, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_INITUTIVE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void InsertImage(QuizModel quizModel) {
        SQLiteDatabase db = new IntuitveDatabase(context).getWritableDatabase();


// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(IMAGE1, quizModel.getIMAGE1());
        values.put(IMAGE2, quizModel.getIMAGE2());
        values.put(IMAGE3, quizModel.getIMAGE3());
        values.put(IMAGE4, quizModel.getIMAGE4());
        values.put(QUIZTYPE, quizModel.getQUIZID());


// Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(TABLE_NAME, null, values);
    }

    public ArrayList<QuizModel> GetImage(String QuizID) {

        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<QuizModel> Location_array = new ArrayList<QuizModel>();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE " + QUIZTYPE + "=" + QuizID;

        String[] params = new String[]{ QuizID };
        Cursor c = db.query(TABLE_NAME, null,
                QUIZTYPE +" = ?", params,
                null, null, null);

        Log.e("LOG", selectQuery);


//        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                QuizModel quiz_model = new QuizModel();
                quiz_model.setID(c.getString((c.getColumnIndex(ID))));
                quiz_model.setIMAGE1((c.getString(c.getColumnIndex(IMAGE1))));
                quiz_model.setIMAGE2(c.getString(c.getColumnIndex(IMAGE2)));
                quiz_model.setIMAGE3(c.getString(c.getColumnIndex(IMAGE3)));
                quiz_model.setIMAGE4(c.getString(c.getColumnIndex(IMAGE4)));
                quiz_model.setQUIZID(c.getString(c.getColumnIndex(QUIZTYPE)));


                // adding to todo list
                Location_array.add(quiz_model);
                deleteField(c.getInt((c.getColumnIndex(ID))));
            } while (c.moveToNext());
        }
        return Location_array;
    }

    public void deleteField(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    public void DropTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS TABLE_NAME;");
    }
}
