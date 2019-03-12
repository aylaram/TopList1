package com.example.toplist;

import android.app.Activity;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class NewList extends AppCompatActivity {

    private static final String PREF = "counter";
    private int listsCounter, itemsCounter;
    private ImageButton svList;
    private EditText etList;
    List<String> items = new ArrayList<>();
    private ListView itemsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_list);

        etList = findViewById(R.id.etList);

        svList = findViewById(R.id.svList);
        svList.setOnClickListener(new svListC());

        itemsList =(ListView)findViewById(R.id.itemsList);
        update();
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefGet = getSharedPreferences(PREF, Activity.MODE_PRIVATE);
        listsCounter = prefGet.getInt("lists", 0);
        itemsCounter = prefGet.getInt("items", 0);
        //Log.d( "onResume","Resume items");
    }

    @Override
    protected void  onPause() {
        super.onPause();

        SharedPreferences prefPut = getSharedPreferences(PREF,Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = prefPut.edit();
        prefEditor.putInt("lists", (listsCounter));
        prefEditor.commit();
    }

    private void save() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,
                "toplist", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        listsCounter++;
        String cod = Integer.toString(listsCounter);
        String descri = etList.getText().toString();
        ContentValues list = new ContentValues();
        list.put("id", cod);
        list.put("description", descri);
        bd.insert("lists", null, list);
        SparseBooleanArray sparseBooleanArray = itemsList.getCheckedItemPositions();
        for(int i = 0; i < itemsCounter; i++){
            if(sparseBooleanArray.get(i)) {
                int idItem = i+1;
                ContentValues relation = new ContentValues();
                relation.put("idList", Integer.toString(listsCounter));
                relation.put("idItem", Integer.toString(idItem));
                bd.insert("relation", null, relation);
                //Log.d("saveLI", "list:"+Integer.toString(listsCounter)+" item: "+Integer.toString(idItem));
            }
        }
        bd.close();
        etList.setText("");
        Toast.makeText(this, "List Saved",
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
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_multiple_choice, items);
        itemsList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        itemsList.setAdapter(adapter);
    }

    private class svListC implements View.OnClickListener {
        @Override
        public void onClick(View view ) {
            save();
        }
    }
}
