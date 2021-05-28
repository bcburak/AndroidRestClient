package com.example.burakcelik.restclientapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GetActivity extends AppCompatActivity {

    private ProgressDialog progress;
    String line = "";
    String cariler="";
    ListView listview1 = null;
    ArrayAdapter<String> veriAdaptoru = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get);

    }
    public void sendGetRequest(View View) {
        Bundle b = getIntent().getExtras();
        String token = b.getString("token");

        new GetClass(this).execute(token);
    }
    private class GetClass extends AsyncTask<String, String, String> {

        private final Context context;

        public GetClass(Context c){

            this.context = c;

        }

        protected void onPreExecute(){
            progress= new ProgressDialog(this.context);
            progress.setMessage("Loading");
            progress.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {

                final TextView txtCariKod = (TextView) findViewById(R.id.txtCariKod);
                final TextView txtCariAdi = (TextView)findViewById(R.id.txtCariAdi);
                URL url = new URL("http://172.16.20.136:7070/api/v2/ARPs?limit=10"); // Cari bilgisi
                //URL url = new URL("http://172.16.20.136:7070/api/v2/ItemSlips/docType=ftSSip"); // Müşteri siparişleri
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();

                String urlParameters = "grant_type=password&branchcode=0&password=Clk2016@&username=netsis&dbname=TEST&dbuser=sa&dbpassword=sapass&dbtype=0";
                connection.setRequestMethod("GET");

                connection.setRequestProperty ("Content-Type", "application/json");
                final String basicAuth = "Bearer " + params[0];
                connection.setRequestProperty("Authorization",basicAuth);
                int responseCode = connection.getResponseCode();
//Authorizatin,Bearer:token
                System.out.println("\nSending 'POST' request to URL : " + url);
                System.out.println("Post parameters : " + urlParameters);
                System.out.println("Response Code : " + responseCode);
                final StringBuilder output = new StringBuilder();
//                final StringBuilder output = new StringBuilder("Request URL " + url);
               // output.append(System.getProperty("line.separator")  + "Response Code " + responseCode);
               // output.append(System.getProperty("line.separator")  + "Type " + "GET");
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                StringBuilder responseOutput = new StringBuilder();
                //System.out.println("output===============" + br);
//                while((line = br.readLine()) != null ) {
//                    responseOutput.append(line);
//                }
                line = br.readLine();
                br.close();
                //Log.d("json obj : ",line);
                System.out.println("JSON RESULT : " + line);
                try
                {
//region Cari bilgileri

//
                    List<String> cariKodlar = new ArrayList<String>();
                    List<String> cariAdi = new ArrayList<String>();
                    JSONObject reader = new JSONObject(line);
                    JSONArray cariData  = reader.getJSONArray("Data");
                    for (int i=0; i<cariData.length(); i++) {
                        JSONObject cariDataEachValue = cariData.getJSONObject(i);

                        for(int j=0;j<cariDataEachValue.length(); j++)
                        {

                           JSONObject ustBilgi = cariDataEachValue.getJSONObject("CariTemelBilgi");
                           cariKodlar.add(ustBilgi.getString("CARI_KOD"));
                            cariAdi.add(ustBilgi.getString("CARI_ISIM"));
                          final  StringBuilder cariKodList = new StringBuilder();
                            for(String c : cariKodlar){
                                cariKodList.append(c);
                                cariKodList.append("\n");
                            }
                            final  StringBuilder cariAdList = new StringBuilder();
                            for(String c : cariAdi){
                                cariAdList.append(c);
                                cariAdList.append("\n");
                            }


                        }



                    }
                    //endregion

                    //region Müşteri Siparişi

                    listview1 = (ListView) findViewById(R.id.listView);
                    String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
                            "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
                            "Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux",
                            "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
                            "Android", "iPhone", "WindowsMobile" };

                    final ArrayList<String> list = new ArrayList<String>();
                    for (int i = 0; i < values.length; ++i) {
                        list.add(values[i]);

                            //(this, android.R.layout.simple_list_item_1, android.R.id.text1);
                     veriAdaptoru=new ArrayAdapter<String>
                            (GetActivity.this,android.R.layout.activity_list_item,android.R.id.text1,values);

                        GetActivity.this.runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                //txtCariKod.setText("Cari Kodu : " + cariKodList +"\n");
                                //txtCariAdi.setText("Cari Adı : " + cariAdList + "\n");
                                //outputView.setText(line);
                                listview1.setAdapter(veriAdaptoru);
                                progress.dismiss();

                            }
                        });
                    }
                    //endregion
                }
                catch (JSONException ex)
                {
                    Log.e("MYAPP", "unexpected JSON exception", ex);
                }


            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute() {

            progress.dismiss();
        }


    }


}
