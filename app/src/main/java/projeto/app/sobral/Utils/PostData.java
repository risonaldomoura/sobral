package projeto.app.sobral.Utils;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Daniel on 04/02/2018.
 */

public class PostData extends AppCompatActivity {
    private ProgressDialog progress;

    TextView tv_titulo;

    TextView tv_cont1;
    TextView tv_cont2;
    TextView tv_cont3;
    TextView tv_cont4;
    TextView tv_cont5;
    TextView tv_cont6;
    TextView tv_cont7;
    TextView tv_cont8;
    TextView tv_cont9;
    TextView tv_cont10;

    String titulo;
    String text1;
    String text2;
    String text3;
    String text4;
    String text5;
    String text6;
    String text7;
    String text8;
    String text9;
    String text10;

    Button botao_docs;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        //Pegar esses textos do Firebase
        titulo = "Matriz_Sobral_Portugues_I_Bimestre";

        text1 = "Relacionar fonemas e grafemas";
        text2 = "Respeitar as regras de cortesia e de interação";
        text3 = "Realizar apresentações orais";
        text4 = "Expressar-se de maneira efetiva nas diferentes interações";
        text5 = "Compreender texto oral";

    }


    public class SendRequest extends AsyncTask<String,Void,String>{
        protected void onPreExecute(){}

        protected String doInBackground(String... arg0){
            try{
                URL url = new URL("https://script.google.com/a/gedu.demo.inteceleri.com.br/macros/s/AKfycbx-QDKkP0Ux7jD0lqbdyHDl2iVXTWkXgVIdLEGXSWCIqmVJamEe/exec");
                JSONObject postDataParams = new JSONObject();

                postDataParams.put("titulo",titulo);
                postDataParams.put("text1",text1);
                postDataParams.put("text2",text2);
                postDataParams.put("text3",text3);
                postDataParams.put("text4",text4);
                postDataParams.put("text5",text5);
                postDataParams.put("text6",text6);
                postDataParams.put("text7",text7);
                postDataParams.put("text8",text8);
                postDataParams.put("text9",text9);
                postDataParams.put("text10",text10);

                Log.e("params",postDataParams.toString());
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode=conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuffer sb = new StringBuffer("");
                    String line="";

                    while((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();

                }
                else {
                    return new String("false : "+responseCode);
                }

            }catch (Exception e){
                return new String("Exception"+e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getApplicationContext(), result,
                    Toast.LENGTH_LONG).show();

        }
    }

    public String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while(itr.hasNext()){

            String key= itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }
}
