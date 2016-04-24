package com.example.theon.ecckeygen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by theon on 4/24/2016.
 */
public class PickKey extends Activity {
    private Button keyGenerate;
    ArrayList<Pair<String,String>> listobj;
    SaveLoadKey svl;
    ArrayAdapter<View> adapter;
    ListView lview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_key);
        keyGenerate = (Button) findViewById(R.id.generate_key);
        svl = new SaveLoadKey();
        listobj = svl.loadAllKey();
        adapter =new CustomAdapter(this,listobj);
        lview = (ListView) findViewById(R.id.list_key);
        lview.setAdapter(adapter);
        keyGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                svl.saveKey("1","2");
                listobj = svl.loadAllKey();
                adapter.notifyDataSetChanged();
            }
        });

        lview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent =new Intent(PickKey.this,MainActivity.class);
                TextView private_key = (TextView)view.findViewById(R.id.private_key);
                TextView public_key = (TextView) view.findViewById(R.id.public_key);
                intent.putExtra("private_key",private_key.getText());
                intent.putExtra("public_key",public_key.getText());
            }
        });
    }
}
