package com.example.toplist;

import android.app.Activity;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Items extends AppCompatActivity {

    private static final String PREF = "counter";
    private int itemsCounter;
    private ImageButton svItem;
    private EditText etItem;
    List<String> items = new ArrayList<>();
    private ListView itemsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        etItem = findViewById(R.id.etItem);

        svItem = findViewById(R.id.svItem);
        svItem.setOnClickListener(new svItemC());

        itemsList =(ListView)findViewById(R.id.itemsList);
        update();
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefGet = getSharedPreferences(PREF, Activity.MODE_PRIVATE);
        itemsCounter = prefGet.getInt("items", 0);
        //Log.d( "onResume","Resume items");
    }

    @Override
    protected void  onPause() {
        super.onPause();

        SharedPreferences prefPut = getSharedPreferences(PREF,Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = prefPut.edit();
        prefEditor.putInt("items", (itemsCounter));
        prefEditor.commit();
    }

    private void save() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,
                "toplist", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        itemsCounter++;
        String cod = Integer.toString(itemsCounter);
        String descri = etItem.getText().toString();
        ContentValues item = new ContentValues();
        item.put("id", cod);
        item.put("description", descri);
        bd.insert("items", null, item);
        bd.close();
        etItem.setText("");
        Toast.makeText(this, "Item Saved",
                Toast.LENGTH_SHORT).show();
        update();
    }

    private void update() {
        items.removeAll(items);
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,
                "toplist", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery(
                "select description from items", null);
        while(fila.moveToNext()) {
            items.add(fila.getString(0));
        }
        bd.close();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, items);
        itemsList.setAdapter(adapter);
    }

    private class svItemC implements View.OnClickListener {
        @Override
        public void onClick(View view ) {
            save();
        }
    }
}
