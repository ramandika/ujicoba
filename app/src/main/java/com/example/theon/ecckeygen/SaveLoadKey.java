package com.example.theon.ecckeygen;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Pair;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by theon on 4/24/2016.
 */
public class SaveLoadKey extends Activity {
    private static final String KEYFILE = "MyKeyfile";
    private int NOKEY;
    private SharedPreferences spKey;
    private SharedPreferences.Editor editor;

    public SaveLoadKey(){
        spKey = getSharedPreferences(KEYFILE, Context.MODE_PRIVATE);
        editor = spKey.edit();
        NOKEY = spKey.getInt("NOKEY",0);
    }

    public void saveKey(String privateKey, String publicKey){
        Set<String> keyset = new HashSet<String>();
        keyset.add(privateKey); keyset.add(publicKey);
        NOKEY++;
        editor.putStringSet("Key-"+NOKEY,keyset);
        editor.putInt("NOKEY",NOKEY);
    }
    public ArrayList<Pair<String,String>> loadAllKey(){
        Set<String> stringSet;
        ArrayList<Pair<String,String>> temp = new ArrayList<Pair<String,String>>();
        for(int i=1;;i++){
            stringSet = spKey.getStringSet("Key-"+i,null);
            if(stringSet==null) break;
            String[] publicPrivate = stringSet.toArray(new String[stringSet.size()]);
            temp.add(new Pair<String, String>(publicPrivate[0],publicPrivate[1]));
        }
        return temp;
    }
}
