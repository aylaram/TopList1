package com.example.toplist;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ImageButton itemsB, listsB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        itemsB = findViewById(R.id.goItems);
        itemsB.setOnClickListener(new itemsBC());

        listsB = findViewById(R.id.goLists);
        listsB.setOnClickListener(new listsBC());
    }

    private void startItems(View view) {
        Intent intent = new Intent(this, Items.class);
        startActivity(intent);
    }

    private void startLists(View view) {
        Intent intent = new Intent(this, Lists.class);
        startActivity(intent);
    }

    private class itemsBC implements View.OnClickListener {
        @Override
        public void onClick(View view ) {
            startItems(view);
        }
    }

    private class listsBC implements View.OnClickListener {
        @Override
        public void onClick(View view ) {
            startLists(view);
        }
    }
}
