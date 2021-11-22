package com.example.myjson;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView dataList;
    String url = "https://api.jsonbin.io/b/615513129548541c29bb053d/1";
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataList = (ListView) findViewById(R.id.dataList);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading....");
        dialog.show();
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String string) {
                parseJsonData(string);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), "Some error occurred!!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        RequestQueue rQ = Volley.newRequestQueue(MainActivity.this);
        rQ.add(request);
    }
    void parseJsonData(String jsonString) {
        try {
            JSONObject object = new JSONObject(jsonString);
            JSONArray dataArray = object.getJSONArray("employees");
            ArrayList al = new ArrayList();
            for (int i = 0; i < dataArray.length(); ++i) {
                al.add(dataArray.getString(i));
            }
            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, al);
            dataList.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        dialog.dismiss();
    }
}