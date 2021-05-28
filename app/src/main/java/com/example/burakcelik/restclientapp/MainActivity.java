package com.example.burakcelik.restclientapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

public class MainActivity extends AppCompatActivity {



    private ProgressDialog progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    protected void onPostExecute() {
        progress.dismiss();
    }

    public void sendPostRequest(View View) {

        TextView txthost = (TextView)findViewById(R.id.etxtIP);// params [0]
        TextView txtCompany = (TextView)findViewById(R.id.etxtCompany);// params [2]
        TextView txtUser = (TextView)findViewById(R.id.etxtUser);// params [3]
        TextView txtPassword = (TextView)findViewById(R.id.etxtPassword);// params [1]


        TextView txtDbPassword = (TextView)findViewById(R.id.etxtDbPassword);// params [4]
        TextView txtDbUser = (TextView)findViewById(R.id.etxtDbUser);// params [5]
        TextView txtBranchCode = (TextView)findViewById(R.id.etxtBranchCode);// params [6]
        TextView txtToken = (TextView)findViewById(R.id.txtViewToken);
        new PostClass(this).execute(txthost.getText().toString(), txtPassword.getText().toString(), txtCompany.getText().toString(), txtUser.getText().toString()
                , txtDbPassword.getText().toString(), txtDbUser.getText().toString(), txtBranchCode.getText().toString(), txtToken.getText().toString());


    }

    private class PostClass extends AsyncTask<String, String, String>
    {

        private final Context context;

        public PostClass(Context c) {

            this.context = c;
//            this.error = status;
//            this.type = t;
        }



        protected void onPreExecute(){
        progress= new ProgressDialog(this.context);
        progress.setMessage("Loading");
        progress.show();
    }

        protected String doInBackground(String... params) {

            try {

                URL url = new URL("http://"+params[0]+"/api/v2/token");
                String urlParam ="grant_type=password&branchcode="+params[6]+"&password="+params[1]+"&username="+params[3]+"&dbname="+params[2]+"&dbuser="+params[5]+"&dbpassword="+params[4]+"&dbtype=0";
                // String urlParam ="grant_type=password&branchcode="+params[1]+"&password=Clk2016@&username=netsis&dbname=TEST&dbuser=sa&dbpassword=sapass&dbtype=0";
                byte[] urlParameters = urlParam.getBytes(Charset.forName("UTF-8"));
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty ("Content-Type", "application/x-www-form-urlencoded");

                connection.connect();
                DataOutputStream dStream = new DataOutputStream(connection.getOutputStream());
                dStream.writeBytes(urlParam);
                dStream.flush();
                dStream.close();
                int responseCode = connection.getResponseCode();
                System.out.println("\nSending 'POST' request to URL : " + url);
                System.out.println("Post parameters : " + urlParameters);
                System.out.println("Response Code : " + responseCode);

                final StringBuilder output = new StringBuilder();

                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder responseOutput = new StringBuilder();

                String[]  tokenReplace = br.readLine().toString().replace('"',';').split(";");
                br.close();
                output.append(tokenReplace[3]);

               // final TextView txtToken = (TextView)findViewById(R.id.txtViewToken);
                MainActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                      //  txtToken.setText(output);
                        progress.dismiss();
                        Intent i =new Intent(MainActivity.this,GetActivity.class) ;
                        i.putExtra("token",output.toString());
                        startActivity(i);
                    }
                });


            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return params[7];
        }

        protected void onPostExecute() {
            progress.dismiss();
        }

}
}

