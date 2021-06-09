package com.example.testl;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainChinh extends AppCompatActivity {
    ArrayList itemList;
    ListView lvItem;
    Button btnThem, btnDelete, btnSignout, btnEdit;
    EditText edID,edName;
    String url = "https://60b32e4f1bec230017bf3480.mockapi.io";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chinh);

        itemList = new ArrayList();
        lvItem= findViewById(R.id.lvItem);
        btnThem= findViewById(R.id.btnThem);
        btnDelete = findViewById(R.id.btnDelete);
        btnEdit = findViewById(R.id.btnEdit);
        btnSignout = findViewById(R.id.btnDangXuat);
        edID = findViewById(R.id.ETVid);
        edName = findViewById(R.id.ETVName);

        String getAll = url + "/Item";

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Them(url);
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Xoa(url);
            }
        });
        btnSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainChinh.this, MainActivity.class));
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sua(url);
            }
        });

        LayAPI(getAll);
    }

    private void Xoa(String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url + "/Item/" + edID.getText().toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(MainChinh.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainChinh.this, "Xóa không thành công", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void Them(String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url + "/Item", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(MainChinh.this, "Thanh cong", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainChinh.this, "Khong thanh cong", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("itemName", edName.getText().toString());
                return params;
            }
        };
        RequestQueue requestQueue =Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void LayAPI(String url) {
        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Toast.makeText(MainChinh.this, "True", Toast.LENGTH_SHORT).show();
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject obj = (JSONObject) response.get(i);
                        itemList.add(obj.get("id"));
                        itemList.add(obj.get("itemName"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                lvItem.setAdapter((new ArrayAdapter<>(getApplication(), android.R.layout.simple_list_item_1, itemList)));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainChinh.this, "Error",Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
    private void Sua(String url) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.PUT, url + "/Item/" + edID.getText().toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(MainChinh.this, "Successfully", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainChinh.this, "Error by Post data!", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("itemName", edName.getText().toString());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
