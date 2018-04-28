package projeto.app.sobral.Portugues;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
//import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import projeto.app.sobral.R;
import projeto.app.sobral.Utils.Main_activity;
import projeto.app.sobral.Utils.MyDataGetSet;

/**
 * Created by Daniel on 09/01/2018.
 */

public class Tab_portugues_sexto_ extends Fragment{
    public int ID_salvo_dia_inicio_I;
    public int ID_salvo_dia_termino_I;
    public int ID_salvo_mes_inicio_I;
    public int ID_salvo_mes_termino_I;

    public int ID_salvo_dia_inicio_II;
    public int ID_salvo_dia_termino_II;
    public int ID_salvo_mes_inicio_II;
    public int ID_salvo_mes_termino_II;

    public int ID_salvo_dia_inicio_III;
    public int ID_salvo_dia_termino_III;
    public int ID_salvo_mes_inicio_III;
    public int ID_salvo_mes_termino_III;

    public int ID_salvo_dia_inicio_IV;
    public int ID_salvo_dia_termino_IV;
    public int ID_salvo_mes_inicio_IV;
    public int ID_salvo_mes_termino_IV;

    public String mes_1 = "JAN";
    public String mes_2 = "FEV";
    public String mes_3 = "MAR";
    public String mes_4 = "ABR";
    public String mes_5 = "MAI";
    public String mes_6 = "JUN";
    public String mes_7 = "JUL";
    public String mes_8 = "AGO";
    public String mes_9 = "SET";
    public String mes_10 = "OUT";
    public String mes_11 = "NOV";
    public String mes_12 = "DEZ";

    public int Dia_sistema;
    public int Mes_sistema;

    public int estadocb1;
    CheckBox cb1;

    RecyclerView rv_I_Bimestre;
    TextView tv_Titulo_I_Bimestre;
    //

    Adaptador_Portugues_sexto adp_portugues_sexto;
    List<MyDataGetSet> listData;
    FirebaseDatabase FDB;
    //
    DatabaseReference DBR;
    DatabaseReference DBR_Titulo_I_Bimestre;

    //

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {




        //RecyclerView rView = (RecyclerView) inflater.inflate(R.layout.cont_portugues_sexto_,container,false);
        View rView = inflater.inflate(R.layout.layout_model_conteudo,container,false);



        //=============== CARREGAMENTO DAS DATAS DOS BIMESTRES =====================================

        //=================PRIMEIRO BIMESTRE========================================================

        //DIA INÍCIO
        SharedPreferences sharedPref_dia_inicio_I = this.getActivity().getSharedPreferences("pref_bimestre", Context.MODE_PRIVATE);
        ID_salvo_dia_inicio_I = sharedPref_dia_inicio_I.getInt("dia_inicio_I", 15);

        //DIA TÉRMINO
        SharedPreferences sharedPref_dia_termino_I = this.getActivity().getSharedPreferences("pref_bimestre", Context.MODE_PRIVATE);
        ID_salvo_dia_termino_I = sharedPref_dia_termino_I.getInt("dia_termino_I", 15);

        //MÊS INÍCIO
        SharedPreferences sharedPref_mes_inicio_I = this.getActivity().getSharedPreferences("pref_bimestre", Context.MODE_PRIVATE);
        ID_salvo_mes_inicio_I = sharedPref_mes_inicio_I.getInt("mes_inicio_I", 1);

        //MÊS TÉRMINO
        SharedPreferences sharedPref_mes_termino_I = this.getActivity().getSharedPreferences("pref_bimestre", Context.MODE_PRIVATE);
        ID_salvo_mes_termino_I = sharedPref_mes_termino_I.getInt("mes_termino_I", 3);

        //==========================================================================================

        //=================SEGUNDO BIMESTRE=========================================================

        //DIA INICIO
        SharedPreferences sharedPref_dia_inicio_II = this.getActivity().getSharedPreferences("pref_bimestre", Context.MODE_PRIVATE);
        ID_salvo_dia_inicio_II = sharedPref_dia_inicio_II.getInt("dia_inicio_II", 16);

        //DIA TÉRMINO
        SharedPreferences sharedPref_dia_termino_II = this.getActivity().getSharedPreferences("pref_bimestre", Context.MODE_PRIVATE);
        ID_salvo_dia_termino_II = sharedPref_dia_termino_II.getInt("dia_termino_II", 16);

        //MES INÍCIO
        SharedPreferences sharedPref_mes_inicio_II = this.getActivity().getSharedPreferences("pref_bimestre", Context.MODE_PRIVATE);
        ID_salvo_mes_inicio_II = sharedPref_mes_inicio_II.getInt("mes_inicio_II", 3);

        //MÊS TÉRMINO
        SharedPreferences sharedPref_mes_termino_II = this.getActivity().getSharedPreferences("pref_bimestre", Context.MODE_PRIVATE);
        ID_salvo_mes_termino_II = sharedPref_mes_termino_II.getInt("mes_termino_II", 5);

        //==========================================================================================

        //=================TERCEIRO BIMESTRE========================================================

        //DIA INÍCIO
        SharedPreferences sharedPref_dia_inicio_III = this.getActivity().getSharedPreferences("pref_bimestre", Context.MODE_PRIVATE);
        ID_salvo_dia_inicio_III = sharedPref_dia_inicio_III.getInt("dia_inicio_III", 5);

        //DIA TÉRMINO
        SharedPreferences sharedPref_dia_termino_III = this.getActivity().getSharedPreferences("pref_bimestre", Context.MODE_PRIVATE);
        ID_salvo_dia_termino_III = sharedPref_dia_termino_III.getInt("dia_termino_III", 5);

        //MES INÍCIO
        SharedPreferences sharedPref_mes_inicio_III = this.getActivity().getSharedPreferences("pref_bimestre", Context.MODE_PRIVATE);
        ID_salvo_mes_inicio_III = sharedPref_mes_inicio_III.getInt("mes_inicio_III", 7);

        //MÊS TÉRMINO
        SharedPreferences sharedPref_mes_termino_III = this.getActivity().getSharedPreferences("pref_bimestre", Context.MODE_PRIVATE);
        ID_salvo_mes_termino_III = sharedPref_mes_termino_III.getInt("mes_termino_III", 9);

        //==========================================================================================

        //=================QUARTO BIMESTRE==========================================================

        //DIA INÍCIO
        SharedPreferences sharedPref_dia_inicio_IV = this.getActivity().getSharedPreferences("pref_bimestre", Context.MODE_PRIVATE);
        ID_salvo_dia_inicio_IV = sharedPref_dia_inicio_IV.getInt("dia_inicio_IV", 6);

        //DIA TÉRMINO
        SharedPreferences sharedPref_dia_termino_IV = this.getActivity().getSharedPreferences("pref_bimestre", Context.MODE_PRIVATE);
        ID_salvo_dia_termino_IV = sharedPref_dia_termino_IV.getInt("dia_termino_IV", 6);

        //MES INÍCIO
        SharedPreferences sharedPref_mes_inicio_IV = this.getActivity().getSharedPreferences("pref_bimestre", Context.MODE_PRIVATE);
        ID_salvo_mes_inicio_IV = sharedPref_mes_inicio_IV.getInt("mes_inicio_IV", 9);

        //MÊS TÉRMINO
        SharedPreferences sharedPref_mes_termino_IV = this.getActivity().getSharedPreferences("pref_bimestre", Context.MODE_PRIVATE);
        ID_salvo_mes_termino_IV = sharedPref_mes_termino_IV.getInt("mes_termino_IV", 11);

        //==========================================================================================


        //===================MÉTODO QUE SETA AS DATAS NOS BLOCOS DE BIMESTRES===========================
        // Iniciar os campos buscando no layout do Fragment

        //=========================I BIMESTRE=======================================================

        //DIA INICIO
        TextView dia_inicio_I = (TextView) rView.findViewById(R.id.dia_inicio_I);
        dia_inicio_I.setText("" + ID_salvo_dia_inicio_I);

        //DIA TÉRMINO
        TextView dia_termino_I = (TextView) rView.findViewById(R.id.dia_termino_I);
        dia_termino_I.setText("" + ID_salvo_dia_termino_I);

        //MES INICIO
        TextView mes_inicio_I = (TextView) rView.findViewById(R.id.mes_inicio_I);

        if (ID_salvo_mes_inicio_I == 1)
            mes_inicio_I.setText(mes_1);
        else if (ID_salvo_mes_inicio_I == 2)
            mes_inicio_I.setText(mes_2);
        else if (ID_salvo_mes_inicio_I == 3)
            mes_inicio_I.setText(mes_3);
        else if (ID_salvo_mes_inicio_I == 4)
            mes_inicio_I.setText(mes_4);
        else if (ID_salvo_mes_inicio_I == 5)
            mes_inicio_I.setText(mes_5);
        else if (ID_salvo_mes_inicio_I == 6)
            mes_inicio_I.setText(mes_6);
        else if (ID_salvo_mes_inicio_I == 7)
            mes_inicio_I.setText(mes_7);
        else if (ID_salvo_mes_inicio_I == 8)
            mes_inicio_I.setText(mes_8);
        else if (ID_salvo_mes_inicio_I == 9)
            mes_inicio_I.setText(mes_9);
        else if (ID_salvo_mes_inicio_I == 10)
            mes_inicio_I.setText(mes_10);
        else if (ID_salvo_mes_inicio_I == 11)
            mes_inicio_I.setText(mes_11);
        else if (ID_salvo_mes_inicio_I == 12)
            mes_inicio_I.setText(mes_12);

        //MES TERMINO
        TextView mes_termino_I = (TextView) rView.findViewById(R.id.mes_termino_I);

        if (ID_salvo_mes_termino_I == 1)
            mes_termino_I.setText(mes_1);
        else if (ID_salvo_mes_termino_I == 2)
            mes_termino_I.setText(mes_2);
        else if (ID_salvo_mes_termino_I == 3)
            mes_termino_I.setText(mes_3);
        else if (ID_salvo_mes_termino_I == 4)
            mes_termino_I.setText(mes_4);
        else if (ID_salvo_mes_termino_I == 5)
            mes_termino_I.setText(mes_5);
        else if (ID_salvo_mes_termino_I == 6)
            mes_termino_I.setText(mes_6);
        else if (ID_salvo_mes_termino_I == 7)
            mes_termino_I.setText(mes_7);
        else if (ID_salvo_mes_termino_I == 8)
            mes_termino_I.setText(mes_8);
        else if (ID_salvo_mes_termino_I == 9)
            mes_termino_I.setText(mes_9);
        else if (ID_salvo_mes_termino_I == 10)
            mes_termino_I.setText(mes_10);
        else if (ID_salvo_mes_termino_I == 11)
            mes_termino_I.setText(mes_11);
        else if (ID_salvo_mes_termino_I == 12)
            mes_termino_I.setText(mes_12);


        //==========================================================================================

        //=========== INÍCIO DO TRATAMENTO DOS ADPATADORES PARA CARREGAR A LISTA DE CONTEÚDOS DOS BIMESTRES===============
        rv_I_Bimestre = (RecyclerView) rView.findViewById(R.id.recyclerView_I_Bimestre);
        tv_Titulo_I_Bimestre = (TextView) rView.findViewById(R.id.Titulo_I_Bimestre);
        //

        //
        rv_I_Bimestre.setHasFixedSize(true);
        RecyclerView.LayoutManager LM =  new LinearLayoutManager(getActivity().getApplicationContext());
        //RecyclerView.LayoutManager LM =  new LinecmxndanielarLayoutManager(getApplicationContext());
        rv_I_Bimestre.setLayoutManager(LM);
        rv_I_Bimestre.setItemAnimator(new DefaultItemAnimator());
        rv_I_Bimestre.addItemDecoration(new DividerItemDecoration(getActivity().getApplicationContext(),LinearLayoutManager.VERTICAL));

        listData=  new ArrayList<>();
        adp_portugues_sexto = new Adaptador_Portugues_sexto(listData);
        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        FDB = FirebaseDatabase.getInstance();
        //FirebaseDatabase.setPersistenceEnabled();

        GetDataFirebase();
        //=================FIM DO TRATAMENTO DOS ADAPTADORES PARA CARREGAR A LISTA DE CONTEÚDOS DOS BIMESTRES==================


        return rView;
    }




    public class SendRequest extends AsyncTask<String,Void,String> {
        protected void onPreExecute(){}

        protected String doInBackground(String... arg0){
            try{

                URL url = new URL("https://script.google.com/a/gedu.demo.inteceleri.com.br/macros/s/AKfycbx-QDKkP0Ux7jD0lqbdyHDl2iVXTWkXgVIdLEGXSWCIqmVJamEe/exec");
                //https://script.google.com/a/gedu.demo.inteceleri.com.br/macros/s/AKfycbx-QDKkP0Ux7jD0lqbdyHDl2iVXTWkXgVIdLEGXSWCIqmVJamEe/exec
                JSONObject postDataParams = new JSONObject();

                postDataParams.put("titulo",titulo);
                postDataParams.put("text1",adp_portugues_sexto.listArray.get(0).getX());
                postDataParams.put("text2",adp_portugues_sexto.listArray.get(1).getX());
                postDataParams.put("text3",adp_portugues_sexto.listArray.get(2).getX());
                postDataParams.put("text4",adp_portugues_sexto.listArray.get(3).getX());
                postDataParams.put("text5",adp_portugues_sexto.listArray.get(4).getX());


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
            Toast.makeText(getContext(), "I Bimestre - Portugues 6 ano exportado! ",
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


        void GetDataFirebase(){

        //--------I_Bimestre-----------//

        //------Titulo_I_Bimestre------//
        DBR_Titulo_I_Bimestre = FDB.getReferenceFromUrl("https://matriz-sobral-194718.firebaseio.com/6_ano/portugues/I_bimestre/titulo/x");
        DBR_Titulo_I_Bimestre.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String str_Titulo_I_Bimestre = dataSnapshot.getValue(String.class);
                tv_Titulo_I_Bimestre.setText(str_Titulo_I_Bimestre);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //------Conteudo_I_Bimestre------//
        DBR = FDB.getReference("6_ano").child("portugues").child("I_bimestre").child("conteudo");
        DBR.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                MyDataGetSet x =  dataSnapshot.getValue(MyDataGetSet.class) ;


                listData.add(x);
                rv_I_Bimestre.setAdapter(adp_portugues_sexto);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



    public class Adaptador_Portugues_sexto extends RecyclerView.Adapter<Adaptador_Portugues_sexto.ViewholderPortugues_sexto>{

        List<MyDataGetSet> listArray;

        public  Adaptador_Portugues_sexto(List<MyDataGetSet> List){
            this.listArray = List;

        }

        @Override
        public ViewholderPortugues_sexto onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview_,parent,false);

            return new ViewholderPortugues_sexto(view);
        }

        @Override
        public void onBindViewHolder(Adaptador_Portugues_sexto.ViewholderPortugues_sexto holder, int position) {
            MyDataGetSet x = listArray.get(position);

            holder.myText.setText(x.getX());

        }


        public class ViewholderPortugues_sexto extends RecyclerView.ViewHolder{
            TextView myText;
            CheckBox mCB;

            public int estadocb1;
            CheckBox cb1;

            public ViewholderPortugues_sexto(View itemView) {
                super(itemView);

                mCB = (CheckBox) itemView.findViewById(R.id.cb_itemView_);
                myText = (TextView) itemView.findViewById(R.id.textView_);
            }


        }


        @Override
        public int getItemCount() {
            return listArray.size();
        }


    }

    //===========================MÉTODO QUE RECEBE AS SOMBRAS DOS BIMESTRES===========================
    @Override
    public void onViewCreated (View view, Bundle savedInstanceState) {
        //((Main_activity) getActivity()).SombraBimestre(view);
        ((Main_activity) getActivity()).tab_obj_port_sexto(view);



    }
    //==============================================================================================

}
