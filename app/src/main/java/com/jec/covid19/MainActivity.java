package com.jec.covid19;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;



public class MainActivity extends AppCompatActivity {
    TextView t1,t2,t3,t4,t5,t6,t7,t8,t9;
    ProgressBar pb;
    private AdView adView;
    LoadingDialog dialog;
    RequestQueue queue;
    ArrayList<DistrictData> dislist;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         dialog =new LoadingDialog(MainActivity.this);
         queue = Volley.newRequestQueue(this);
         recyclerView = findViewById(R.id.recycler);
         dislist = new ArrayList<DistrictData>();

         t1=findViewById(R.id.newcon);
         t2=findViewById(R.id.newrec);
         t3=findViewById(R.id.newdet);
        t5 =findViewById(R.id.date);
        t6 = findViewById(R.id.tv6);
        t7 = findViewById(R.id.tv7);
        t8 = findViewById(R.id.tv8);
        t9 = findViewById(R.id.tv9);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        fetch();
        district();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_add:
                fetch();
                return true;
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void fetch(){
        dialog.startLoading();

        String url ="https://api.covid19india.org/data.json";

// Request a string response from the provided URL.
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("statewise");
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject as = jsonArray.getJSONObject(i);
                        String state = as.getString("state");
                        if(Objects.equals(state,"Assam")){
                            String con = as.getString("confirmed");
                            t6.setText(con);
                            t7.setText(as.getString("recovered"));
                            t8.setText(as.getString("deaths"));
                            t9.setText(as.getString("active"));
                            t5.setText("As on "+as.getString("lastupdatedtime"));
                            int delcon = Integer.parseInt(as.getString("deltaconfirmed"));
                            if(delcon!=0)
                            t1.setText("+"+as.getString("deltaconfirmed"));

                            int delrec = Integer.parseInt(as.getString("deltarecovered"));
                            if(delrec!=0)
                                t2.setText("+"+as.getString("deltarecovered"));

                            int deldet = Integer.parseInt(as.getString("deltadeaths"));
                            if(deldet!=0)
                                t3.setText("+"+as.getString("deltadeaths"));
                            dialog.endDialog();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        queue.add(request);
    }

    private void district(){

        String url ="https://api.covid19india.org/state_district_wise.json";

// Request a string response from the provided URL.
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    JSONObject object = jsonObject.getJSONObject("Assam");
                    JSONObject district = object.getJSONObject("districtData");
                    JSONArray nameArray = district.names();
                    JSONArray valArray = district.toJSONArray(nameArray);
                    for(int i=0;i<nameArray.length();i++){
                        JSONObject as = valArray.getJSONObject(i);
                        dislist.add(new DistrictData(nameArray.getString(i),as.getString("active"),as.getString("confirmed"),
                                as.getString("deceased"),as.getString("recovered")));

                    }
                   layoutManager = new LinearLayoutManager(MainActivity.this);
                    adapter = new MyAdapter(dislist);

                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);
                    System.out.println("hrllo");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        queue.add(request);
    }

}
