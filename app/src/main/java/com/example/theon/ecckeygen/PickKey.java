package com.example.theon.ecckeygen;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by theon on 4/24/2016.
 */
public class PickKey extends Activity {
    private Button keyGenerate;
    ArrayList<Pair<String,String>> listobj;
    ArrayAdapter<View> adapter;
    ListView lview;
    private int PICK_KEY_REQUEST=1;
    /*
      SharedPreference
     */
    private static final String KEYFILE = "MyKeyfile";
    private int NOKEY;
    private SharedPreferences spKey;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_key);
        keyGenerate = (Button) findViewById(R.id.generate_key);
        listobj = new ArrayList<>();

        //Load object
        spKey = getSharedPreferences(KEYFILE,MODE_PRIVATE);
        NOKEY = spKey.getInt("NOKEY",0);
        editor = spKey.edit();
        Set<String> stringSet;
        for(int i=1;;i++){
            stringSet = spKey.getStringSet("Key-"+i,null);
            if(stringSet==null) break;
            String[] publicPrivate = stringSet.toArray(new String[stringSet.size()]);
            listobj.add(new Pair<String, String>(publicPrivate[0],publicPrivate[1]));
        }
        listobj.add(new Pair<String, String>("gaje","gaje"));
        adapter =new CustomAdapter(this,listobj);
        lview = (ListView) findViewById(R.id.listkey);
        lview.setAdapter(adapter);


        keyGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Save Key*/
                Set<String> keyset = new HashSet<String>();
                keyset.add("1"); keyset.add("2");
                NOKEY++;
                editor.putStringSet("Key-"+NOKEY,keyset);
                editor.putInt("NOKEY",NOKEY);
                editor.commit();
                listobj.add(new Pair<String, String>("1","2"));
                adapter.notifyDataSetChanged();
            }
        });

        lview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = getIntent();
                TextView private_key = (TextView)view.findViewById(R.id.private_key);
                TextView public_key = (TextView) view.findViewById(R.id.public_key);
                intent.putExtra("private_key",private_key.getText());
                intent.putExtra("public_key",public_key.getText());
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}
