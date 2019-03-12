package com.example.toplist;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Lists extends AppCompatActivity {

    private ImageButton nvList;
    List<String> lists = new ArrayList<>();
    private ListView listsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lists);

        nvList = findViewById(R.id.nvList);
        nvList.setOnClickListener(new nvListC());

        listsList =(ListView)findViewById(R.id.listsList);
    }

    @Override
    protected void onResume() {
        super.onResume();

        update();
    }

    private void startNList(View view) {
        Intent intent = new Intent(this, NewList.class);
        startActivity(intent);
    }

    private void update() {
        lists.removeAll(lists);
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,
                "toplist", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery(
                "select description from lists", null);
        while(fila.moveToNext()) {
            lists.add(fila.getString(0));
        }
        bd.close();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, lists);
        listsList.setAdapter(adapter);
        listsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                int listID = arg2 + 1;
                Intent intent = new Intent(arg1.getContext(), ListContent.class);
                intent.putExtra("listID", Integer.toString(listID));
                //Log.d("sendID", Integer.toString(listID));
                startActivity(intent);
                //Log.d("ListPosition","Items " +  arg2 );
            }
        });
    }

    private class nvListC implements View.OnClickListener {
        @Override
        public void onClick(View view ) {
            startNList(view);
        }
    }
}
