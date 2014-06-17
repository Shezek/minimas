package dataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class SQLController {

 private SQLiteHelper dbhelper;
 private Context ourcontext;
 private SQLiteDatabase database;

 public SQLController(Context c) {
  ourcontext = c;
 }

 public SQLController open() throws SQLException {
  dbhelper = new SQLiteHelper(ourcontext,"TableNadadores", null, 1);
  database = dbhelper.getWritableDatabase();
  return this;

 }

 public void close() {
  dbhelper.close();
 }



 public Cursor readEntry() {

  String[] allColumns = new String[] {"nombre","edad","piscina","prueba","tiempo",};

  Cursor c = database.query("Nadadores", allColumns, null, null, null,
    null, null);

  if (c != null) {
   c.moveToFirst();
  }
  return c;

 }

} 