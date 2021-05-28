package com.example.burakcelik.restclientapp;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by Burak.Celik on 09.05.2016.
 */

public class Post extends AsyncTask<String, String, String>
{

    private final Context context;

    public Post(Context c) {

        this.context = c;
//            this.error = status;
//            this.type = t;
    }

    @Override
    protected String doInBackground(String... params) {

        try {
            //Notification.Builder
            //URL url = new URL("http://"+params[0]+"/api/v2/token");
            URL url = new URL("http://172.16.2.179:8282/v1_0/NAF.Services.Sts/OAuth/token");
            //String urlParam ="grant_type=password&branchcode="+params[6]+"&password="+params[1]+"&username="+params[3]+"&dbname="+params[2]+"&dbuser="+params[5]+"&dbpassword="+params[4]+"&dbtype=0";
            String urlParam ="grant_type=password&client_id="+"ae842e46-8fae-4f23-a431-5c9744aa0fa4"+"&client_secret="+"00000000-0000-0000-0000-000000000000"+"&username="+"burak.celik@logo.com.tr"+"&password="+"Clk2016@";
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

            final StringBuilder output = new StringBuilder("Request URL " + url);
            output.append(System.getProperty("line.separator") + "Request Parameters " + urlParameters);
            output.append(System.getProperty("line.separator")  + "Response Code " + responseCode);
            output.append(System.getProperty("line.separator")  + "Type " + "POST");
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));


            StringBuilder responseOutput = new StringBuilder();

            String  tokenReplace = br.readLine().toString().replace('"',';');
            String[] baseToken =tokenReplace.split(";");
            br.close();
            tokenReplace = baseToken[3];
            params[7]=tokenReplace;


        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return params[7];
    }


}










//public class Rest {
  //  @TargetApi(Build.VERSION_CODES.KITKAT)
   // public void Customer()
    //{
      //  try {
//      //      AsyncHttpClient test = new AsyncHttpClient();
          //  URL url = new URL("http://www.example.com/customers");
        //    String data = "grant_type=password&branchcode=0&password=Clk2016@&username=netsis&dbname=TEST&dbuser=sa&dbpassword=sapass&dbtype=0";
          //  HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            //connection.setDoOutput(true);
            //connection.setInstanceFollowRedirects(false);
            //connection.setRequestMethod("PUT");
            //connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
           // List<NameValuePair> params = new ArrayList<NameValuePair>();
            //params.add(new BasicNameValuePair("firstParam", paramValue1));
            //params.add(new BasicNameValuePair("secondParam", paramValue2));
           // params.add(new BasicNameValuePair("thirdParam", paramValue3));

          //  ContentValues values=new ContentValues();
           // values.put("grant_type","password");
          //  values.put("branchcode","0");
           // values.put("password","net1");
            //values.put("username","netsis");
           // values.put("dbname","TEST");
           // values.put("dbuser","sa");
           // values.put("dbpassword","sapass");
            //values.put("dbtype","0");

           // database.insert(Table_name,null,values);

//            byte[] postbyte =data.getBytes(StandardCharsets.US_ASCII);

         //   InputStream iStream = connection.getContent();
           // BufferedReader br = new BufferedReader(new InputStreamReader(iStream, "utf8"));
         //  StringBuffer sb = new StringBuffer();
           // String line = "";

           // while ((line = br.readLine()) != null) {
               // sb.append(line);
            //}

//            data = sb.toString();
            //OutputStream os = connection.getOutputStream();
           // jaxbContext.createMarshaller().marshal(customer, os);
            //os.flush();

//            connection.getResponseCode();
//            connection.disconnect();
      //  } catch(Exception e) {
        //    throw new RuntimeException(e);
        //}
   // }
//}
