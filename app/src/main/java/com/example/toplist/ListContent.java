package com.example.toplist;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListContent extends AppCompatActivity {

    List<String> items = new ArrayList<>();
    private ListView itemsList;
    private TextView listName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_content);

        listName = (TextView) findViewById(R.id.listName);
        itemsList =(ListView)findViewById(R.id.itemsList);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Bundle bundle = getIntent().getExtras();
        String listID=bundle.getString("listID");

        update(listID);
    }

    private void update(String listID) {
        items.removeAll(items);
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,
                "toplist", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila2 = bd.rawQuery(
                "select description from lists where id = "+listID, null);
        if(fila2.moveToFirst()) {
            listName.setText(fila2.getString(0));
        }
        Cursor fila = bd.rawQuery(
                "select description from items where id in (select iditem from relation where idlist = "+listID+")", null);
        while(fila.moveToNext()) {
            items.add(fila.getString(0));
            //Log.d("bringIt", listID);
        }
        /*
        Cursor fila2 = bd.rawQuery(
                "select * from relation", null);
        while(fila2.moveToNext()) {
            Log.d("basededator", "idlist: "+fila2.getString(0)+" iditem"+fila2.getString(1));
        }
        */
        bd.close();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_multiple_choice, items);
        itemsList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        itemsList.setAdapter(adapter);
    }
}
