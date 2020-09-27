package com.example.currencylistapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONObject;
import org.json.*;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TextView Date,PreviousDate,mresult;
    private RequestQueue requestQueue;
    private Button convert;
    private EditText inputSumm;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ImageView chooseValute,reload;
    public ArrayList<ModelValute> arrayList=new ArrayList<>();
    public  String[] valutes=new String[]{"AUD","AZN","GBP","AMD","BYN","BGN","BRL","HUF","HKD","DKK","USD","EUR","INR","KZT","CAD","KGS","CNY","MDL","NOK","PLN","RON","XDR","SGD","TJS","TRY","TMT","UZS","UAH","CZK","SEK","CHF","ZAR","KRW","JPY"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        convert=findViewById(R.id.ConvertationBtn);
        Date=findViewById(R.id.Date);
        PreviousDate=findViewById(R.id.PreviousDate);
        mRecyclerView=findViewById(R.id.ValuteRV);
        inputSumm=findViewById(R.id.summ);
        chooseValute=findViewById(R.id.choose);
        mresult=findViewById(R.id.result);
        reload=findViewById(R.id.reload);
        mRecyclerView.setHasFixedSize(true);

        requestQueue= Volley.newRequestQueue(this);
        mLayoutManager=new LinearLayoutManager(this);
        jsonParse();
        mRecyclerView.setLayoutManager(mLayoutManager);
        convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mresult.setText(Convertation());
            }
        });
        chooseValute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Выберите категорию:")
                        .setItems(valutes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                String selected=valutes[which];
                                choose(selected);
                            }
                        }).show();
            }
        });
        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonParse();
            }
        });
    }
    private String rub;
    private Double value=null;
    private void choose(String selected)
    {
        Double Nominal=null;
        for (int i=0;i<valutes.length;i++)
        {
            for (ModelValute modelValute:arrayList)
            {
                if(modelValute.getCharCode().equals(selected))
                {

                    value= Double.parseDouble(modelValute.getValue());
                    Nominal= Double.parseDouble(modelValute.getNominal());
                    value=value*Nominal;
                }
            }
        }
    }

    private void jsonParse()
    {
       final String jsonString = "https://www.cbr-xml-daily.ru/daily_json.js";
        final JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, jsonString, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject object=new JSONObject(response.toString());
                            String data=object.getString("Date");
                            String previous=object.getString("PreviousDate");

                            Date.setText(cutDate(data));
                            PreviousDate.setText(cutDate(previous));
                            JSONObject valute=object.getJSONObject("Valute");

                            for (int i=0;i<valutes.length;i++)
                            {
                                JSONObject example=valute.getJSONObject(valutes[i]);
                                String NumCode=example.getString("NumCode");
                                String CharCode=example.getString("CharCode");
                                String Nominal=example.getString("Nominal");
                                String Name=example.getString("Name");
                                String Value=example.getString("Value");
                                String Previous=example.getString("Previous");
                                arrayList.add(new ModelValute(NumCode,CharCode,Nominal,Name,Value,Previous));
                               mAdapter=new ValuteAdapter(arrayList);
                               mRecyclerView.setAdapter(mAdapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(request);
    }

    private String cutDate(String data)
    {
        String newdata=data.substring(data.indexOf('2'),data.indexOf('T'));
        return newdata;
    }

    private String Convertation()
    {
        String resultStr=null;
        rub=inputSumm.getText().toString().trim();
        if (rub.equals(""))
        {
             Toast.makeText(MainActivity.this,"Введите сумму ...",Toast.LENGTH_SHORT).show();
        }
        else
            {
                if (value==null)
                {
                    Toast.makeText(MainActivity.this,"Выберите валюту ...",Toast.LENGTH_SHORT).show();
                }
                else
                    {
                    double rubDouble = Double.parseDouble(rub);
                    double result = rubDouble / value;
                    resultStr = String.format("%.3f", result);
                     }
        }
        return resultStr;
    }

}
