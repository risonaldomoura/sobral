package projeto.app.sobral.Matematica;

import android.app.Dialog;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
//import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import projeto.app.sobral.R;
import projeto.app.sobral.Utils.Classes.DatasFirebase;
import projeto.app.sobral.Utils.Activities.Main_activity;
import projeto.app.sobral.Utils.Classes.MyDataGetSet;

/**
 * Created by Daniel on 09/01/2018.
 */

public class Tab_matematica_oitavo_ extends Fragment{

    String sTitulo_I_Bimestre;
    String sTitulo_II_Bimestre;
    String sTitulo_III_Bimestre;
    String sTitulo_IV_Bimestre;

    RecyclerView rv_I_Bimestre;
    TextView tv_Titulo_I_Bimestre;
    Adaptador_Matematica_oitavo_I_Bimestre adp_matematica_oitavo_I;
    List<MyDataGetSet> listData_I_Bimestre;
    DatabaseReference DBR_Titulo_I_Bimestre;

    RecyclerView rv_II_Bimestre;
    TextView tv_Titulo_II_Bimestre;
    Adaptador_Matematica_oitavo_II_Bimestre adp_matematica_oitavo_II;
    List<MyDataGetSet> listData_II_Bimestre;
    DatabaseReference DBR_Titulo_II_Bimestre;

    RecyclerView rv_III_Bimestre;
    TextView tv_Titulo_III_Bimestre;
    Adaptador_Matematica_oitavo_III_Bimestre adp_matematica_oitavo_III;
    List<MyDataGetSet> listData_III_Bimestre;
    DatabaseReference DBR_Titulo_III_Bimestre;

    RecyclerView rv_IV_Bimestre;
    TextView tv_Titulo_IV_Bimestre;
    Adaptador_Matematica_oitavo_IV_Bimestre adp_matematica_oitavo_IV;
    List<MyDataGetSet> listData_IV_Bimestre;
    DatabaseReference DBR_Titulo_IV_Bimestre;


    FirebaseDatabase FDB;
    DatabaseReference DBR;
    DatabaseReference DiscRefFirebase;

    //cont_act é um contador para que o carregamento de saves do firebase ocorra somente uma vez por execução do fragment (Tab)
    public int position_check;

    String titulo, uid;

    //Dia e Mês carregado do firebase
    public String dia_up_inicio_1, mes_up_inicio_1, dia_up_termino_1, mes_up_termino_1,
            dia_up_inicio_2, mes_up_inicio_2, dia_up_termino_2, mes_up_termino_2,
            dia_up_inicio_3, mes_up_inicio_3, dia_up_termino_3, mes_up_termino_3,
            dia_up_inicio_4, mes_up_inicio_4, dia_up_termino_4, mes_up_termino_4;

    private FirebaseAuth fbAuth;
    private DatabaseReference UserData_inicio_1, UserData_termino_1,
            UserData_inicio_2, UserData_termino_2,
            UserData_inicio_3, UserData_termino_3,
            UserData_inicio_4, UserData_termino_4;


    //Essas variáveis vão variar na referência do database Firebase para salvar e deletar a ID do usuário
    //utilizada para marcar e desmarcar o conteúdo
    public String disciplina = "matematica", ano = "8_ano", bimestre, position_firebase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View rView = inflater.inflate(R.layout.layout_model_conteudo,container,false);

        fbAuth = FirebaseAuth.getInstance();
        DiscRefFirebase = FirebaseDatabase.getInstance().getReference().child("disciplinas");

        //=========== INÍCIO DO TRATAMENTO DOS ADPATADORES PARA CARREGAR A LISTA DE CONTEÚDOS DOS BIMESTRES===============

        FDB = FirebaseDatabase.getInstance();


        RecyclerView.LayoutManager LM_I = new LinearLayoutManager(getActivity().getApplicationContext());
        rv_I_Bimestre = (RecyclerView) rView.findViewById(R.id.recyclerView_I_Bimestre);
        tv_Titulo_I_Bimestre = (TextView) rView.findViewById(R.id.Titulo_I_Bimestre);
        rv_I_Bimestre.setHasFixedSize(true);
        rv_I_Bimestre.setLayoutManager(LM_I);
        rv_I_Bimestre.setItemAnimator(new DefaultItemAnimator());
        rv_I_Bimestre.addItemDecoration(new DividerItemDecoration(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL));
        listData_I_Bimestre = new ArrayList<>();
        adp_matematica_oitavo_I = new Adaptador_Matematica_oitavo_I_Bimestre(listData_I_Bimestre);


        RecyclerView.LayoutManager LM_II = new LinearLayoutManager(getActivity().getApplicationContext());
        rv_II_Bimestre = (RecyclerView) rView.findViewById(R.id.recyclerView_II_Bimestre);
        tv_Titulo_II_Bimestre = (TextView) rView.findViewById(R.id.Titulo_II_Bimestre);
        rv_II_Bimestre.setHasFixedSize(true);
        rv_II_Bimestre.setLayoutManager(LM_II);
        rv_II_Bimestre.setItemAnimator(new DefaultItemAnimator());
        rv_II_Bimestre.addItemDecoration(new DividerItemDecoration(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL));
        listData_II_Bimestre = new ArrayList<>();
        adp_matematica_oitavo_II = new Adaptador_Matematica_oitavo_II_Bimestre(listData_II_Bimestre);


        RecyclerView.LayoutManager LM_III = new LinearLayoutManager(getActivity().getApplicationContext());
        rv_III_Bimestre = (RecyclerView) rView.findViewById(R.id.recyclerView_III_Bimestre);
        tv_Titulo_III_Bimestre = (TextView) rView.findViewById(R.id.Titulo_III_Bimestre);
        rv_III_Bimestre.setHasFixedSize(true);
        rv_III_Bimestre.setLayoutManager(LM_III);
        rv_III_Bimestre.setItemAnimator(new DefaultItemAnimator());
        rv_III_Bimestre.addItemDecoration(new DividerItemDecoration(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL));
        listData_III_Bimestre = new ArrayList<>();
        adp_matematica_oitavo_III = new Adaptador_Matematica_oitavo_III_Bimestre(listData_III_Bimestre);


        RecyclerView.LayoutManager LM_IV = new LinearLayoutManager(getActivity().getApplicationContext());
        rv_IV_Bimestre = (RecyclerView) rView.findViewById(R.id.recyclerView_IV_Bimestre);
        tv_Titulo_IV_Bimestre = (TextView) rView.findViewById(R.id.Titulo_IV_Bimestre);
        rv_IV_Bimestre.setHasFixedSize(true);
        rv_IV_Bimestre.setLayoutManager(LM_IV);
        rv_IV_Bimestre.setItemAnimator(new DefaultItemAnimator());
        rv_IV_Bimestre.addItemDecoration(new DividerItemDecoration(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL));
        listData_IV_Bimestre = new ArrayList<>();
        adp_matematica_oitavo_IV = new Adaptador_Matematica_oitavo_IV_Bimestre(listData_IV_Bimestre);


        rv_I_Bimestre.setNestedScrollingEnabled(true);
        rv_II_Bimestre.setNestedScrollingEnabled(true);
        rv_III_Bimestre.setNestedScrollingEnabled(true);
        rv_IV_Bimestre.setNestedScrollingEnabled(true);

        //=================FIM DO TRATAMENTO DOS ADAPTADORES PARA CARREGAR A LISTA DE CONTEÚDOS DOS BIMESTRES==================


        //=============MÉTODO CARREGA DO FIREBASE E SETA AS DATAS NOS BALÕES DE BIMESTRES===========
        final String user_id = fbAuth.getCurrentUser().getUid();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        //=================PRIMEIRO BIMESTRE========================================================

        //INÍCIO
        final TextView dia_inicio_I = (TextView) rView.findViewById(R.id.dia_inicio_I);
        final TextView mes_inicio_I = (TextView) rView.findViewById(R.id.mes_inicio_I);

        UserData_inicio_1 = database.getReference().child("users").child(user_id).child("datas").child("inicio_1");

        ValueEventListener post_inicio_1_Listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){

                    DatasFirebase post = dataSnapshot.getValue(DatasFirebase.class);

                    dia_up_inicio_1 = post.inicio_1;
                    mes_up_inicio_1 = post.inicio_1;

                    dia_up_inicio_1 = dia_up_inicio_1.substring(0, 2);
                    mes_up_inicio_1 = mes_up_inicio_1.substring(3, 5);

                    conversor_mes_up_inicio_1();

                    dia_inicio_I.setText(dia_up_inicio_1);
                    mes_inicio_I.setText(mes_up_inicio_1);
                }
                else {}
            }
            @Override
            public void onCancelled(DatabaseError databaseError){}
        };
        UserData_inicio_1.addValueEventListener(post_inicio_1_Listener);

        //TÉRMINO
        final TextView dia_termino_I = (TextView) rView.findViewById(R.id.dia_termino_I);
        final TextView mes_termino_I = (TextView) rView.findViewById(R.id.mes_termino_I);

        UserData_termino_1 = database.getReference().child("users").child(user_id).child("datas").child("termino_1");

        ValueEventListener post_termino_1_Listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){

                    DatasFirebase post = dataSnapshot.getValue(DatasFirebase.class);

                    dia_up_termino_1 = post.termino_1;
                    mes_up_termino_1 = post.termino_1;

                    dia_up_termino_1 = dia_up_termino_1.substring(0, 2);
                    mes_up_termino_1 = mes_up_termino_1.substring(3, 5);

                    conversor_mes_up_termino_1();

                    dia_termino_I.setText(dia_up_termino_1);
                    mes_termino_I.setText(mes_up_termino_1);

                }
                else {}
            }
            @Override
            public void onCancelled(DatabaseError databaseError){}
        };
        UserData_termino_1.addValueEventListener(post_termino_1_Listener);

        //==========================================================================================

        //=================SEGUNDO BIMESTRE=========================================================

        //INÍCIO
        final TextView dia_inicio_II = (TextView) rView.findViewById(R.id.dia_inicio_II);
        final TextView mes_inicio_II = (TextView) rView.findViewById(R.id.mes_inicio_II);

        UserData_inicio_2 = database.getReference().child("users").child(user_id).child("datas").child("inicio_2");

        ValueEventListener post_inicio_2_Listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){

                    DatasFirebase post = dataSnapshot.getValue(DatasFirebase.class);

                    dia_up_inicio_2 = post.inicio_2;
                    mes_up_inicio_2 = post.inicio_2;

                    dia_up_inicio_2 = dia_up_inicio_2.substring(0, 2);
                    mes_up_inicio_2 = mes_up_inicio_2.substring(3, 5);

                    conversor_mes_up_inicio_2();

                    dia_inicio_II.setText(dia_up_inicio_2);
                    mes_inicio_II.setText(mes_up_inicio_2);
                }
                else {}
            }
            @Override
            public void onCancelled(DatabaseError databaseError){}
        };
        UserData_inicio_2.addValueEventListener(post_inicio_2_Listener);

        //TÉRMINO
        final TextView dia_termino_II = (TextView) rView.findViewById(R.id.dia_termino_II);
        final TextView mes_termino_II = (TextView) rView.findViewById(R.id.mes_termino_II);

        UserData_termino_2 = database.getReference().child("users").child(user_id).child("datas").child("termino_2");

        ValueEventListener post_termino_2_Listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){

                    DatasFirebase post = dataSnapshot.getValue(DatasFirebase.class);

                    dia_up_termino_2 = post.termino_2;
                    mes_up_termino_2 = post.termino_2;

                    dia_up_termino_2 = dia_up_termino_2.substring(0, 2);
                    mes_up_termino_2 = mes_up_termino_2.substring(3, 5);

                    conversor_mes_up_termino_2();

                    dia_termino_II.setText(dia_up_termino_2);
                    mes_termino_II.setText(mes_up_termino_2);

                }
                else {}
            }
            @Override
            public void onCancelled(DatabaseError databaseError){}
        };
        UserData_termino_2.addValueEventListener(post_termino_2_Listener);

        //==========================================================================================

        //=================TERCEIRO BIMESTRE========================================================

        //INÍCIO
        final TextView dia_inicio_III = (TextView) rView.findViewById(R.id.dia_inicio_III);
        final TextView mes_inicio_III = (TextView) rView.findViewById(R.id.mes_inicio_III);

        UserData_inicio_3 = database.getReference().child("users").child(user_id).child("datas").child("inicio_3");

        ValueEventListener post_inicio_3_Listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){

                    DatasFirebase post = dataSnapshot.getValue(DatasFirebase.class);

                    dia_up_inicio_3 = post.inicio_3;
                    mes_up_inicio_3 = post.inicio_3;

                    dia_up_inicio_3 = dia_up_inicio_3.substring(0, 2);
                    mes_up_inicio_3 = mes_up_inicio_3.substring(3, 5);

                    conversor_mes_up_inicio_3();

                    dia_inicio_III.setText(dia_up_inicio_3);
                    mes_inicio_III.setText(mes_up_inicio_3);
                }
                else {}
            }
            @Override
            public void onCancelled(DatabaseError databaseError){}
        };
        UserData_inicio_3.addValueEventListener(post_inicio_3_Listener);

        //TÉRMINO
        final TextView dia_termino_III = (TextView) rView.findViewById(R.id.dia_termino_III);
        final TextView mes_termino_III = (TextView) rView.findViewById(R.id.mes_termino_III);

        UserData_termino_3 = database.getReference().child("users").child(user_id).child("datas").child("termino_3");

        ValueEventListener post_termino_3_Listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){

                    DatasFirebase post = dataSnapshot.getValue(DatasFirebase.class);

                    dia_up_termino_3 = post.termino_3;
                    mes_up_termino_3 = post.termino_3;

                    dia_up_termino_3 = dia_up_termino_3.substring(0, 2);
                    mes_up_termino_3 = mes_up_termino_3.substring(3, 5);

                    conversor_mes_up_termino_3();

                    dia_termino_III.setText(dia_up_termino_3);
                    mes_termino_III.setText(mes_up_termino_3);

                }
                else {}
            }
            @Override
            public void onCancelled(DatabaseError databaseError){}
        };
        UserData_termino_3.addValueEventListener(post_termino_3_Listener);
        //==========================================================================================

        //=================QUARTO BIMESTRE==========================================================

        //INÍCIO
        final TextView dia_inicio_IV = (TextView) rView.findViewById(R.id.dia_inicio_IV);
        final TextView mes_inicio_IV = (TextView) rView.findViewById(R.id.mes_inicio_IV);

        UserData_inicio_4 = database.getReference().child("users").child(user_id).child("datas").child("inicio_4");

        ValueEventListener post_inicio_4_Listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){

                    DatasFirebase post = dataSnapshot.getValue(DatasFirebase.class);

                    dia_up_inicio_4 = post.inicio_4;
                    mes_up_inicio_4 = post.inicio_4;

                    dia_up_inicio_4 = dia_up_inicio_4.substring(0, 2);
                    mes_up_inicio_4 = mes_up_inicio_4.substring(3, 5);

                    conversor_mes_up_inicio_4();

                    dia_inicio_IV.setText(dia_up_inicio_4);
                    mes_inicio_IV.setText(mes_up_inicio_4);
                }
                else {}
            }
            @Override
            public void onCancelled(DatabaseError databaseError){}
        };
        UserData_inicio_4.addValueEventListener(post_inicio_4_Listener);

        //TÉRMINO
        final TextView dia_termino_IV = (TextView) rView.findViewById(R.id.dia_termino_IV);
        final TextView mes_termino_IV = (TextView) rView.findViewById(R.id.mes_termino_IV);

        UserData_termino_4 = database.getReference().child("users").child(user_id).child("datas").child("termino_4");

        ValueEventListener post_termino_4_Listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){

                    DatasFirebase post = dataSnapshot.getValue(DatasFirebase.class);

                    dia_up_termino_4 = post.termino_4;
                    mes_up_termino_4 = post.termino_4;

                    dia_up_termino_4 = dia_up_termino_4.substring(0, 2);
                    mes_up_termino_4 = mes_up_termino_4.substring(3, 5);

                    conversor_mes_up_termino_4();

                    dia_termino_IV.setText(dia_up_termino_4);
                    mes_termino_IV.setText(mes_up_termino_4);

                }
                else {}
            }
            @Override
            public void onCancelled(DatabaseError databaseError){}
        };
        UserData_termino_4.addValueEventListener(post_termino_4_Listener);

        //==========================================================================================


        GetDataFirebase_I_Bimestre();
        GetDataFirebase_II_Bimestre();
        GetDataFirebase_III_Bimestre();
        GetDataFirebase_IV_Bimestre();

        return rView;
    }

    public void conversor_mes_up_inicio_1(){

        if (mes_up_inicio_1.equals("01"))
        {
            mes_up_inicio_1 = "JAN";

        }
        else if (mes_up_inicio_1.equals("02") )
        {
            mes_up_inicio_1 = "FEV";

        }
        else if (mes_up_inicio_1.equals("03"))
        {
            mes_up_inicio_1 = "MAR";

        }
        else if (mes_up_inicio_1.equals("04") )
        {
            mes_up_inicio_1 = "ABR";

        }
        else if (mes_up_inicio_1.equals("05"))
        {
            mes_up_inicio_1 = "MAI";

        }
        else if (mes_up_inicio_1.equals("06"))
        {
            mes_up_inicio_1 = "JUN";

        }
        else if (mes_up_inicio_1.equals("07"))
        {
            mes_up_inicio_1 = "JUL";

        }

        else if (mes_up_inicio_1.equals("08"))
        {
            mes_up_inicio_1 = "AGO";

        }

        else if (mes_up_inicio_1.equals("09"))
        {
            mes_up_inicio_1 = "SET";

        }

        else if (mes_up_inicio_1.equals("10"))
        {
            mes_up_inicio_1 = "OUT";

        }

        else if (mes_up_inicio_1.equals("11") )
        {
            mes_up_inicio_1 = "NOV";

        }

        else if (mes_up_inicio_1.equals("12") )
        {
            mes_up_inicio_1 = "DEZ";

        }

    }

    public void conversor_mes_up_termino_1(){

        if (mes_up_termino_1.equals("01"))
        {
            mes_up_termino_1 = "JAN";

        }
        else if (mes_up_termino_1.equals("02") )
        {
            mes_up_termino_1 = "FEV";

        }
        else if (mes_up_termino_1.equals("03"))
        {
            mes_up_termino_1 = "MAR";

        }
        else if (mes_up_termino_1.equals("04") )
        {
            mes_up_termino_1 = "ABR";

        }
        else if (mes_up_termino_1.equals("05"))
        {
            mes_up_termino_1 = "MAI";

        }
        else if (mes_up_termino_1.equals("06"))
        {
            mes_up_termino_1 = "JUN";

        }
        else if (mes_up_termino_1.equals("07"))
        {
            mes_up_termino_1 = "JUL";

        }

        else if (mes_up_termino_1.equals("08"))
        {
            mes_up_termino_1 = "AGO";

        }

        else if (mes_up_termino_1.equals("09"))
        {
            mes_up_termino_1 = "SET";

        }

        else if (mes_up_termino_1.equals("10"))
        {
            mes_up_termino_1 = "OUT";

        }

        else if (mes_up_termino_1.equals("11") )
        {
            mes_up_termino_1 = "NOV";

        }

        else if (mes_up_termino_1.equals("12") )
        {
            mes_up_termino_1 = "DEZ";

        }

    }

    public void conversor_mes_up_inicio_2(){

        if (mes_up_inicio_2.equals("01"))
        {
            mes_up_inicio_2 = "JAN";

        }
        else if (mes_up_inicio_2.equals("02") )
        {
            mes_up_inicio_2 = "FEV";

        }
        else if (mes_up_inicio_2.equals("03"))
        {
            mes_up_inicio_2 = "MAR";

        }
        else if (mes_up_inicio_2.equals("04") )
        {
            mes_up_inicio_2 = "ABR";

        }
        else if (mes_up_inicio_2.equals("05"))
        {
            mes_up_inicio_2 = "MAI";

        }
        else if (mes_up_inicio_2.equals("06"))
        {
            mes_up_inicio_2 = "JUN";

        }
        else if (mes_up_inicio_2.equals("07"))
        {
            mes_up_inicio_2 = "JUL";

        }

        else if (mes_up_inicio_2.equals("08"))
        {
            mes_up_inicio_2 = "AGO";

        }

        else if (mes_up_inicio_2.equals("09"))
        {
            mes_up_inicio_2 = "SET";

        }

        else if (mes_up_inicio_2.equals("10"))
        {
            mes_up_inicio_2 = "OUT";

        }

        else if (mes_up_inicio_2.equals("11") )
        {
            mes_up_inicio_2 = "NOV";

        }

        else if (mes_up_inicio_2.equals("12") )
        {
            mes_up_inicio_2 = "DEZ";

        }

    }

    public void conversor_mes_up_termino_2(){

        if (mes_up_termino_2.equals("01"))
        {
            mes_up_termino_2 = "JAN";

        }
        else if (mes_up_termino_2.equals("02") )
        {
            mes_up_termino_2 = "FEV";

        }
        else if (mes_up_termino_2.equals("03"))
        {
            mes_up_termino_2 = "MAR";

        }
        else if (mes_up_termino_2.equals("04") )
        {
            mes_up_termino_2 = "ABR";

        }
        else if (mes_up_termino_2.equals("05"))
        {
            mes_up_termino_2 = "MAI";

        }
        else if (mes_up_termino_2.equals("06"))
        {
            mes_up_termino_2 = "JUN";

        }
        else if (mes_up_termino_2.equals("07"))
        {
            mes_up_termino_2 = "JUL";

        }

        else if (mes_up_termino_2.equals("08"))
        {
            mes_up_termino_2 = "AGO";

        }

        else if (mes_up_termino_2.equals("09"))
        {
            mes_up_termino_2 = "SET";

        }

        else if (mes_up_termino_2.equals("10"))
        {
            mes_up_termino_2 = "OUT";

        }

        else if (mes_up_termino_2.equals("11") )
        {
            mes_up_termino_2 = "NOV";

        }

        else if (mes_up_termino_2.equals("12") )
        {
            mes_up_termino_2 = "DEZ";

        }

    }

    public void conversor_mes_up_inicio_3(){

        if (mes_up_inicio_3.equals("01"))
        {
            mes_up_inicio_3 = "JAN";

        }
        else if (mes_up_inicio_3.equals("02") )
        {
            mes_up_inicio_3 = "FEV";

        }
        else if (mes_up_inicio_3.equals("03"))
        {
            mes_up_inicio_3 = "MAR";

        }
        else if (mes_up_inicio_3.equals("04") )
        {
            mes_up_inicio_3 = "ABR";

        }
        else if (mes_up_inicio_3.equals("05"))
        {
            mes_up_inicio_3 = "MAI";

        }
        else if (mes_up_inicio_3.equals("06"))
        {
            mes_up_inicio_3 = "JUN";

        }
        else if (mes_up_inicio_3.equals("07"))
        {
            mes_up_inicio_3 = "JUL";

        }

        else if (mes_up_inicio_3.equals("08"))
        {
            mes_up_inicio_3 = "AGO";

        }

        else if (mes_up_inicio_3.equals("09"))
        {
            mes_up_inicio_3 = "SET";

        }

        else if (mes_up_inicio_3.equals("10"))
        {
            mes_up_inicio_3 = "OUT";

        }

        else if (mes_up_inicio_3.equals("11") )
        {
            mes_up_inicio_3 = "NOV";

        }

        else if (mes_up_inicio_3.equals("12") )
        {
            mes_up_inicio_3 = "DEZ";

        }

    }

    public void conversor_mes_up_termino_3(){

        if (mes_up_termino_3.equals("01"))
        {
            mes_up_termino_3 = "JAN";

        }
        else if (mes_up_termino_3.equals("02") )
        {
            mes_up_termino_3 = "FEV";

        }
        else if (mes_up_termino_3.equals("03"))
        {
            mes_up_termino_3 = "MAR";

        }
        else if (mes_up_termino_3.equals("04") )
        {
            mes_up_termino_3 = "ABR";

        }
        else if (mes_up_termino_3.equals("05"))
        {
            mes_up_termino_3 = "MAI";

        }
        else if (mes_up_termino_3.equals("06"))
        {
            mes_up_termino_3 = "JUN";

        }
        else if (mes_up_termino_3.equals("07"))
        {
            mes_up_termino_3 = "JUL";

        }

        else if (mes_up_termino_3.equals("08"))
        {
            mes_up_termino_3 = "AGO";

        }

        else if (mes_up_termino_3.equals("09"))
        {
            mes_up_termino_3 = "SET";

        }

        else if (mes_up_termino_3.equals("10"))
        {
            mes_up_termino_3 = "OUT";

        }

        else if (mes_up_termino_3.equals("11") )
        {
            mes_up_termino_3 = "NOV";

        }

        else if (mes_up_termino_3.equals("12") )
        {
            mes_up_termino_3 = "DEZ";

        }

    }

    public void conversor_mes_up_inicio_4(){

        if (mes_up_inicio_4.equals("01"))
        {
            mes_up_inicio_4 = "JAN";

        }
        else if (mes_up_inicio_4.equals("02") )
        {
            mes_up_inicio_4 = "FEV";

        }
        else if (mes_up_inicio_4.equals("03"))
        {
            mes_up_inicio_4 = "MAR";

        }
        else if (mes_up_inicio_4.equals("04") )
        {
            mes_up_inicio_4 = "ABR";

        }
        else if (mes_up_inicio_4.equals("05"))
        {
            mes_up_inicio_4 = "MAI";

        }
        else if (mes_up_inicio_4.equals("06"))
        {
            mes_up_inicio_4 = "JUN";

        }
        else if (mes_up_inicio_4.equals("07"))
        {
            mes_up_inicio_4 = "JUL";

        }

        else if (mes_up_inicio_4.equals("08"))
        {
            mes_up_inicio_4 = "AGO";

        }

        else if (mes_up_inicio_4.equals("09"))
        {
            mes_up_inicio_4 = "SET";

        }

        else if (mes_up_inicio_4.equals("10"))
        {
            mes_up_inicio_4 = "OUT";

        }

        else if (mes_up_inicio_4.equals("11") )
        {
            mes_up_inicio_4 = "NOV";

        }

        else if (mes_up_inicio_4.equals("12") )
        {
            mes_up_inicio_4 = "DEZ";

        }

    }

    public void conversor_mes_up_termino_4(){

        if (mes_up_termino_4.equals("01"))
        {
            mes_up_termino_4 = "JAN";

        }
        else if (mes_up_termino_4.equals("02") )
        {
            mes_up_termino_4 = "FEV";

        }
        else if (mes_up_termino_4.equals("03"))
        {
            mes_up_termino_4 = "MAR";

        }
        else if (mes_up_termino_4.equals("04") )
        {
            mes_up_termino_4 = "ABR";

        }
        else if (mes_up_termino_4.equals("05"))
        {
            mes_up_termino_4 = "MAI";

        }
        else if (mes_up_termino_4.equals("06"))
        {
            mes_up_termino_4 = "JUN";

        }
        else if (mes_up_termino_4.equals("07"))
        {
            mes_up_termino_4 = "JUL";

        }

        else if (mes_up_termino_4.equals("08"))
        {
            mes_up_termino_4 = "AGO";

        }

        else if (mes_up_termino_4.equals("09"))
        {
            mes_up_termino_4 = "SET";

        }

        else if (mes_up_termino_4.equals("10"))
        {
            mes_up_termino_4 = "OUT";

        }

        else if (mes_up_termino_4.equals("11") )
        {
            mes_up_termino_4 = "NOV";

        }

        else if (mes_up_termino_4.equals("12") )
        {
            mes_up_termino_4 = "DEZ";

        }

    }


    //======================Enviar Conteudo I - Bimestre Google Script API============================

    public class SendRequest extends AsyncTask<String,Void,String> {
        protected void onPreExecute(){}

        protected String doInBackground(String... arg0){
            try{

                URL url = new URL("https://script.google.com/a/gedu.demo.inteceleri.com.br/macros/s/AKfycbx-QDKkP0Ux7jD0lqbdyHDl2iVXTWkXgVIdLEGXSWCIqmVJamEe/exec");
                //https://script.google.com/a/gedu.demo.inteceleri.com.br/macros/s/AKfycbx-QDKkP0Ux7jD0lqbdyHDl2iVXTWkXgVIdLEGXSWCIqmVJamEe/exec
                JSONObject postDataParams = new JSONObject();

                postDataParams.put("titulo",titulo);
                //postDataParams.put("text1", adp_matematica_oitavo_1_bimestre.listArray.get(0).getX());
                //postDataParams.put("text2", adp_matematica_oitavo_1_bimestre.listArray.get(1).getX());
                //postDataParams.put("text3", adp_matematica_oitavo_1_bimestre.listArray.get(2).getX());
                //postDataParams.put("text4", adp_matematica_oitavo_1_bimestre.listArray.get(3).getX());
                //postDataParams.put("text5", adp_matematica_oitavo_1_bimestre.listArray.get(4).getX());


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

    //======================Fim ----Enviar Conteudo I - Bimestre Google Script API============================

    public void GetDataFirebase_I_Bimestre() {

        //--------I_Bimestre-----------//

        //------Titulo_I_Bimestre------//
        DBR_Titulo_I_Bimestre = FDB.getReferenceFromUrl("https://matriz-sobral-194718.firebaseio.com/disciplinas/matematica/8_ano/1_bimestre/titulo/0/x");
        DBR_Titulo_I_Bimestre.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String str_Titulo_I_Bimestre = dataSnapshot.getValue(String.class);
                sTitulo_I_Bimestre = str_Titulo_I_Bimestre;
                tv_Titulo_I_Bimestre.setText(str_Titulo_I_Bimestre);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //------Conteudo_I_Bimestre------//
        DBR = FDB.getReference("disciplinas").child("matematica").child("8_ano").child("1_bimestre").child("conteudo");
        DBR.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                MyDataGetSet x = dataSnapshot.getValue(MyDataGetSet.class);

                listData_I_Bimestre.add(x);
                rv_I_Bimestre.setAdapter(adp_matematica_oitavo_I);

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

    public class Adaptador_Matematica_oitavo_I_Bimestre extends RecyclerView.Adapter<Adaptador_Matematica_oitavo_I_Bimestre.ViewholderMatematica_oitavo_I_Bimestre> {

        List<MyDataGetSet> listArray_I;
        int cont_act_1 = 1;

        public Adaptador_Matematica_oitavo_I_Bimestre(List<MyDataGetSet> List) {
            this.listArray_I = List;
        }

        @Override
        public ViewholderMatematica_oitavo_I_Bimestre onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_itemview_lista_conteudo, parent, false);

            return new ViewholderMatematica_oitavo_I_Bimestre(view);
        }

        //Aqui instancia a textview e a checkbox
        public class ViewholderMatematica_oitavo_I_Bimestre extends RecyclerView.ViewHolder {
            TextView myText;
            CheckBox mCB;
            LinearLayout layout_item_view;
            View mView;

            public ViewholderMatematica_oitavo_I_Bimestre(View itemView) {
                super(itemView);
                mView = itemView;

                myText = (TextView) itemView.findViewById(R.id.text_itemview);
                mCB = (CheckBox) itemView.findViewById(R.id.cb_itemView_);
                layout_item_view = (LinearLayout) itemView.findViewById(R.id.layout_item_view);

            }
        }

        @Override
        public void onBindViewHolder(final ViewholderMatematica_oitavo_I_Bimestre holder, final int position) {
            MyDataGetSet x = listArray_I.get(position);
            holder.myText.setText(x.getX());
            holder.mCB.setChecked(x.isB());
            holder.setIsRecyclable(true);



            //Método que faz a captura do LongClick na Lista de Conteúdos.
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    bimestre = "1_bimestre";
                    position_check = position;
                    position_firebase = Integer.toString(position);
                    ExibeDialogOpcoes();
                    return true;
                }
            });

        }

        @Override
        public int getItemCount() {
            if (listArray_I != null){
                if (cont_act_1 == 1){
                    carrega_saves();
                    cont_act_1 = 0;
                }
                return listArray_I.size();
            }
            else
                return 0;
        }

        private void carrega_saves(){

            DiscRefFirebase = FirebaseDatabase.getInstance().getReference().child("disciplinas");
            final String user_id = fbAuth.getCurrentUser().getUid();
            bimestre = "1_bimestre";

            for(int cont =0; cont<= listArray_I.size(); cont ++){
                final int cont_ = cont;
                final String posicao_lista = Integer.toString(cont_);

                DiscRefFirebase.child(disciplina).child(ano).child(bimestre).
                        child("conteudo").child(posicao_lista).child(user_id).child(user_id).
                        addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()){
                                    String isSave = (String) dataSnapshot.getValue();
                                    if (isSave.equals("true")){
                                        listData_I_Bimestre.get(cont_).setB(true);
                                        notifyItemChanged(cont_);
                                    } else{
                                        listData_I_Bimestre.get(cont_).setB(false);
                                        notifyItemChanged(cont_);
                                    }

                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
            }

        }

        //MÉTODO A SER USADO EM TODAS AS RECYCLERVIEWS
        //Dialog que mostra as opções a serem escolhidas após o Long Click no itemView.
        // É chamado dentro de onLongClick(View v) que está dentro de onBindViewHolder
        public void ExibeDialogOpcoes() {
            final Dialog dialog = new Dialog(getActivity());

            dialog.setContentView(R.layout.layout_opcoes_click_itemview);

            //instancia os objetos que estão no layout customdialog.xml
            final TextView btn_marcar_conteudo = (TextView) dialog.findViewById(R.id.btn_marcar_conteudo);
            final TextView btn_desmarcar_conteudo = (TextView) dialog.findViewById(R.id.btn_desmarcar_conteúdo);
            final TextView btn_abrir_anotacoes = (TextView) dialog.findViewById(R.id.btn_abrir_anotacoes);

            //Aqui marca o conteúdo
            btn_marcar_conteudo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(getContext(), "Conteúdo marcado ", Toast.LENGTH_SHORT).show();

                    MarcaConteudoFirebase();
                    dialog.dismiss();
                }
            });

            //Aqui desmarca o conteúdo
            btn_desmarcar_conteudo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(getContext(), "Conteúdo desmarcado ", Toast.LENGTH_SHORT).show();
                    DesmarcaConteudoFirebase();
                    dialog.dismiss();
                }
            });

            //Aqui deve abrir o documento desse conteúdo no docs.
            btn_abrir_anotacoes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(getContext(), "Abrir anotações deste conteúdo", Toast.LENGTH_SHORT).show();
                    ((Main_activity) getActivity()).disciplina = "Matemática";
                    ((Main_activity) getActivity()).ano = "m_8ano";
                    String p = String.valueOf(position_check);
                    ((Main_activity) getActivity()).titulo = "m8a"+p+"_"+sTitulo_I_Bimestre;
                    ((Main_activity) getActivity()).text1 = listData_I_Bimestre.get(position_check).getX();
                    ((Main_activity) getActivity()).getResultsFromApi();
                    dialog.dismiss();
                }
            });

            //exibe na tela o dialog
            dialog.show();

        }

        //Método para Marcar o conteúdo, escrevendo a ID do usuário que estará gravada dentro do
        //child do item do conteúdo no Firebase. Ele é chamado dentro do btn_marcar_conteudo que
        //está dentro do ExibeDialogOpcoes.
        private void MarcaConteudoFirebase() {
            final String user_id = fbAuth.getCurrentUser().getUid();

            HashMap userMap_inicio_1 = new HashMap();

            userMap_inicio_1.put(user_id, "true");

            DiscRefFirebase.child(disciplina).child(ano).child(bimestre).
                    child("conteudo").child(position_firebase).child(user_id).
                    updateChildren(userMap_inicio_1).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getContext(), "Salvo", Toast.LENGTH_LONG).show();
                        listData_I_Bimestre.get(position_check).setB(true);
                        notifyItemChanged(position_check);
                    } else {
                        String message = task.getException().getMessage();
                        Toast.makeText(getContext(), "Não salvo. Erro: " + message, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        //Método para desmarcar o conteúdo, apagando a ID do usuário que estará gravada dentro do
        //child do item do conteúdo no Firebase. Ele é chamado dentro do btn_desmarcar_conteudo que
        //está dentro do ExibeDialogOpcoes.
        private void DesmarcaConteudoFirebase() {

            final String user_id = fbAuth.getCurrentUser().getUid();

            DiscRefFirebase.child(disciplina).child(ano).child(bimestre).
                    child("conteudo").child(position_firebase).child(user_id).removeValue();

            listData_I_Bimestre.get(position_check).setB(false);

            notifyItemChanged(position_check);
            Toast.makeText(getContext(), "Salvo" , Toast.LENGTH_SHORT).show();

        }

    }



    public void GetDataFirebase_II_Bimestre() {

        //--------II_Bimestre-----------//

        //------Titulo_II_Bimestre------//
        DBR_Titulo_II_Bimestre = FDB.getReferenceFromUrl("https://matriz-sobral-194718.firebaseio.com/disciplinas/matematica/8_ano/2_bimestre/titulo/0/x");
        DBR_Titulo_II_Bimestre.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String str_Titulo_II_Bimestre = dataSnapshot.getValue(String.class);
                sTitulo_II_Bimestre = str_Titulo_II_Bimestre;
                tv_Titulo_II_Bimestre.setText(str_Titulo_II_Bimestre);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //------Conteudo_II_Bimestre------//
        DBR = FDB.getReference("disciplinas").child("matematica").child("8_ano").child("2_bimestre").child("conteudo");
        DBR.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                MyDataGetSet x = dataSnapshot.getValue(MyDataGetSet.class);

                listData_II_Bimestre.add(x);
                rv_II_Bimestre.setAdapter(adp_matematica_oitavo_II);

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

    public class Adaptador_Matematica_oitavo_II_Bimestre extends RecyclerView.Adapter<Adaptador_Matematica_oitavo_II_Bimestre.ViewholderMatematica_oitavo_II_Bimestre> {

        List<MyDataGetSet> listArray_II;
        int  cont_act_2 = 1;

        public Adaptador_Matematica_oitavo_II_Bimestre(List<MyDataGetSet> List) {
            this.listArray_II = List;
        }

        @Override
        public ViewholderMatematica_oitavo_II_Bimestre onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_itemview_lista_conteudo, parent, false);

            return new ViewholderMatematica_oitavo_II_Bimestre(view);
        }

        @Override
        public void onBindViewHolder(final Adaptador_Matematica_oitavo_II_Bimestre.ViewholderMatematica_oitavo_II_Bimestre holder, final int position) {
            MyDataGetSet x = listArray_II.get(position);
            holder.myText.setText(x.getX());
            holder.mCB.setChecked(x.isB());
            holder.setIsRecyclable(true);



            //Método que faz a captura do LongClick na Lista de Conteúdos.
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    bimestre = "2_bimestre";
                    position_check = position;
                    position_firebase = Integer.toString(position);
                    ExibeDialogOpcoes();
                    return true;
                }
            });
        }

        //Aqui instancia a textview e a checkbox
        public class ViewholderMatematica_oitavo_II_Bimestre extends RecyclerView.ViewHolder {
            TextView myText;
            CheckBox mCB;
            LinearLayout layout_item_view;
            View mView;

            public ViewholderMatematica_oitavo_II_Bimestre(View itemView) {
                super(itemView);
                mView = itemView;

                myText = (TextView) itemView.findViewById(R.id.text_itemview);
                mCB = (CheckBox) itemView.findViewById(R.id.cb_itemView_);
                layout_item_view = (LinearLayout) itemView.findViewById(R.id.layout_item_view);

            }
        }

        @Override
        public int getItemCount() {
            if (listArray_II != null){
                if (cont_act_2 == 1){
                    carrega_saves();
                    cont_act_2 = 0;
                }
                return listArray_II.size();
            }
            else
                return 0;
        }

        private void carrega_saves(){

            DiscRefFirebase = FirebaseDatabase.getInstance().getReference().child("disciplinas");
            final String user_id = fbAuth.getCurrentUser().getUid();
            bimestre = "2_bimestre";

            for(int cont =0; cont<= listArray_II.size(); cont ++){
                final int cont_ = cont;
                final String posicao_lista = Integer.toString(cont_);

                DiscRefFirebase.child(disciplina).child(ano).child(bimestre).
                        child("conteudo").child(posicao_lista).child(user_id).child(user_id).
                        addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()){
                                    String isSave = (String) dataSnapshot.getValue();
                                    if (isSave.equals("true")){
                                        listData_II_Bimestre.get(cont_).setB(true);
                                        notifyItemChanged(cont_);
                                    } else{
                                        listData_II_Bimestre.get(cont_).setB(false);
                                        notifyItemChanged(cont_);
                                    }

                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
            }

        }

        //MÉTODO A SER USADO EM TODAS AS RECYCLERVIEWS
        //Dialog que mostra as opções a serem escolhidas após o Long Click no itemView.
        // É chamado dentro de onLongClick(View v) que está dentro de onBindViewHolder
        public void ExibeDialogOpcoes() {
            final Dialog dialog = new Dialog(getActivity());

            dialog.setContentView(R.layout.layout_opcoes_click_itemview);

            //instancia os objetos que estão no layout customdialog.xml
            final TextView btn_marcar_conteudo = (TextView) dialog.findViewById(R.id.btn_marcar_conteudo);
            final TextView btn_desmarcar_conteudo = (TextView) dialog.findViewById(R.id.btn_desmarcar_conteúdo);
            final TextView btn_abrir_anotacoes = (TextView) dialog.findViewById(R.id.btn_abrir_anotacoes);

            //Aqui marca o conteúdo
            btn_marcar_conteudo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(getContext(), "Conteúdo marcado ", Toast.LENGTH_SHORT).show();

                    MarcaConteudoFirebase();
                    dialog.dismiss();
                }
            });

            //Aqui desmarca o conteúdo
            btn_desmarcar_conteudo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(getContext(), "Conteúdo desmarcado ", Toast.LENGTH_SHORT).show();
                    DesmarcaConteudoFirebase();
                    dialog.dismiss();
                }
            });

            //Aqui deve abrir o documento desse conteúdo no docs.
            btn_abrir_anotacoes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(getContext(), "Abrir anotações deste conteúdo", Toast.LENGTH_SHORT).show();
                    ((Main_activity) getActivity()).disciplina = "Matemática";
                    ((Main_activity) getActivity()).ano = "m_8ano";
                    String p = String.valueOf(position_check);
                    ((Main_activity) getActivity()).titulo = "m8a"+p+"_"+sTitulo_II_Bimestre;
                    ((Main_activity) getActivity()).text1 = listData_II_Bimestre.get(position_check).getX();
                    ((Main_activity) getActivity()).getResultsFromApi();
                    dialog.dismiss();
                }
            });

            //exibe na tela o dialog
            dialog.show();

        }

        //Método para Marcar o conteúdo, escrevendo a ID do usuário que estará gravada dentro do
        //child do item do conteúdo no Firebase. Ele é chamado dentro do btn_marcar_conteudo que
        //está dentro do ExibeDialogOpcoes.
        private void MarcaConteudoFirebase() {
            final String user_id = fbAuth.getCurrentUser().getUid();

            HashMap userMap_inicio_1 = new HashMap();

            userMap_inicio_1.put(user_id, "true");

            DiscRefFirebase.child(disciplina).child(ano).child(bimestre).
                    child("conteudo").child(position_firebase).child(user_id).
                    updateChildren(userMap_inicio_1).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getContext(), "Salvo", Toast.LENGTH_LONG).show();
                        listData_II_Bimestre.get(position_check).setB(true);
                        notifyItemChanged(position_check);
                    } else {
                        String message = task.getException().getMessage();
                        Toast.makeText(getContext(), "Não salvo. Erro: " + message, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        //Método para desmarcar o conteúdo, apagando a ID do usuário que estará gravada dentro do
        //child do item do conteúdo no Firebase. Ele é chamado dentro do btn_desmarcar_conteudo que
        //está dentro do ExibeDialogOpcoes.
        private void DesmarcaConteudoFirebase() {

            final String user_id = fbAuth.getCurrentUser().getUid();

            DiscRefFirebase.child(disciplina).child(ano).child(bimestre).
                    child("conteudo").child(position_firebase).child(user_id).removeValue();

            listData_II_Bimestre.get(position_check).setB(false);

            notifyItemChanged(position_check);
            Toast.makeText(getContext(), "Salvo" , Toast.LENGTH_SHORT).show();

        }

    }



    public void GetDataFirebase_III_Bimestre() {

        //--------III_Bimestre-----------//

        //------Titulo_III_Bimestre------//
        DBR_Titulo_III_Bimestre = FDB.getReferenceFromUrl("https://matriz-sobral-194718.firebaseio.com/disciplinas/matematica/8_ano/3_bimestre/titulo/0/x");
        DBR_Titulo_III_Bimestre.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String str_Titulo_III_Bimestre = dataSnapshot.getValue(String.class);
                sTitulo_III_Bimestre = str_Titulo_III_Bimestre;
                tv_Titulo_III_Bimestre.setText(str_Titulo_III_Bimestre);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //------Conteudo_III_Bimestre------//
        DBR = FDB.getReference("disciplinas").child("matematica").child("8_ano").child("3_bimestre").child("conteudo");
        DBR.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                MyDataGetSet x = dataSnapshot.getValue(MyDataGetSet.class);

                listData_III_Bimestre.add(x);
                rv_III_Bimestre.setAdapter(adp_matematica_oitavo_III);

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

    public class Adaptador_Matematica_oitavo_III_Bimestre extends RecyclerView.Adapter<Adaptador_Matematica_oitavo_III_Bimestre.ViewholderMatematica_oitavo_III_Bimestre> {

        List<MyDataGetSet> listArray_III;
        int  cont_act_3 = 1;

        public Adaptador_Matematica_oitavo_III_Bimestre(List<MyDataGetSet> List) {
            this.listArray_III = List;
        }

        @Override
        public ViewholderMatematica_oitavo_III_Bimestre onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_itemview_lista_conteudo, parent, false);

            return new ViewholderMatematica_oitavo_III_Bimestre(view);
        }

        @Override
        public void onBindViewHolder(final Adaptador_Matematica_oitavo_III_Bimestre.ViewholderMatematica_oitavo_III_Bimestre holder, final int position) {
            MyDataGetSet x = listArray_III.get(position);
            holder.myText.setText(x.getX());
            holder.mCB.setChecked(x.isB());
            holder.setIsRecyclable(true);



            //Método que faz a captura do LongClick na Lista de Conteúdos.
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    bimestre = "3_bimestre";
                    position_check = position;
                    position_firebase = Integer.toString(position);
                    ExibeDialogOpcoes();
                    return true;
                }
            });
        }

        //Aqui instancia a textview e a checkbox
        public class ViewholderMatematica_oitavo_III_Bimestre extends RecyclerView.ViewHolder {
            TextView myText;
            CheckBox mCB;
            LinearLayout layout_item_view;
            View mView;

            public ViewholderMatematica_oitavo_III_Bimestre(View itemView) {
                super(itemView);
                mView = itemView;

                myText = (TextView) itemView.findViewById(R.id.text_itemview);
                mCB = (CheckBox) itemView.findViewById(R.id.cb_itemView_);
                layout_item_view = (LinearLayout) itemView.findViewById(R.id.layout_item_view);

            }
        }

        @Override
        public int getItemCount() {
            if (listArray_III != null){
                if (cont_act_3 == 1){
                    carrega_saves();
                    cont_act_3 = 0;
                }
                return listArray_III.size();
            }
            else
                return 0;
        }

        private void carrega_saves(){

            DiscRefFirebase = FirebaseDatabase.getInstance().getReference().child("disciplinas");
            final String user_id = fbAuth.getCurrentUser().getUid();
            bimestre = "3_bimestre";

            for(int cont =0; cont<= listArray_III.size(); cont ++){
                final int cont_ = cont;
                final String posicao_lista = Integer.toString(cont_);

                DiscRefFirebase.child(disciplina).child(ano).child(bimestre).
                        child("conteudo").child(posicao_lista).child(user_id).child(user_id).
                        addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()){
                                    String isSave = (String) dataSnapshot.getValue();
                                    if (isSave.equals("true")){
                                        listData_III_Bimestre.get(cont_).setB(true);
                                        notifyItemChanged(cont_);
                                    } else{
                                        listData_III_Bimestre.get(cont_).setB(false);
                                        notifyItemChanged(cont_);
                                    }

                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
            }

        }

        //MÉTODO A SER USADO EM TODAS AS RECYCLERVIEWS
        //Dialog que mostra as opções a serem escolhidas após o Long Click no itemView.
        // É chamado dentro de onLongClick(View v) que está dentro de onBindViewHolder
        public void ExibeDialogOpcoes() {
            final Dialog dialog = new Dialog(getActivity());

            dialog.setContentView(R.layout.layout_opcoes_click_itemview);

            //instancia os objetos que estão no layout customdialog.xml
            final TextView btn_marcar_conteudo = (TextView) dialog.findViewById(R.id.btn_marcar_conteudo);
            final TextView btn_desmarcar_conteudo = (TextView) dialog.findViewById(R.id.btn_desmarcar_conteúdo);
            final TextView btn_abrir_anotacoes = (TextView) dialog.findViewById(R.id.btn_abrir_anotacoes);

            //Aqui marca o conteúdo
            btn_marcar_conteudo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(getContext(), "Conteúdo marcado ", Toast.LENGTH_SHORT).show();

                    MarcaConteudoFirebase();
                    dialog.dismiss();
                }
            });

            //Aqui desmarca o conteúdo
            btn_desmarcar_conteudo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(getContext(), "Conteúdo desmarcado ", Toast.LENGTH_SHORT).show();
                    DesmarcaConteudoFirebase();
                    dialog.dismiss();
                }
            });

            //Aqui deve abrir o documento desse conteúdo no docs.
            btn_abrir_anotacoes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(getContext(), "Abrir anotações deste conteúdo", Toast.LENGTH_SHORT).show();
                    ((Main_activity) getActivity()).disciplina = "Matemática";
                    ((Main_activity) getActivity()).ano = "m_8ano";
                    String p = String.valueOf(position_check);
                    ((Main_activity) getActivity()).titulo = "m8a"+p+"_"+sTitulo_III_Bimestre;
                    ((Main_activity) getActivity()).text1 = listData_III_Bimestre.get(position_check).getX();
                    ((Main_activity) getActivity()).getResultsFromApi();
                    dialog.dismiss();
                }
            });

            //exibe na tela o dialog
            dialog.show();

        }

        //Método para Marcar o conteúdo, escrevendo a ID do usuário que estará gravada dentro do
        //child do item do conteúdo no Firebase. Ele é chamado dentro do btn_marcar_conteudo que
        //está dentro do ExibeDialogOpcoes.
        private void MarcaConteudoFirebase() {
            final String user_id = fbAuth.getCurrentUser().getUid();

            HashMap userMap_inicio_1 = new HashMap();

            userMap_inicio_1.put(user_id, "true");

            DiscRefFirebase.child(disciplina).child(ano).child(bimestre).
                    child("conteudo").child(position_firebase).child(user_id).
                    updateChildren(userMap_inicio_1).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getContext(), "Salvo", Toast.LENGTH_LONG).show();
                        listData_III_Bimestre.get(position_check).setB(true);
                        notifyItemChanged(position_check);
                    } else {
                        String message = task.getException().getMessage();
                        Toast.makeText(getContext(), "Não salvo. Erro: " + message, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        //Método para desmarcar o conteúdo, apagando a ID do usuário que estará gravada dentro do
        //child do item do conteúdo no Firebase. Ele é chamado dentro do btn_desmarcar_conteudo que
        //está dentro do ExibeDialogOpcoes.
        private void DesmarcaConteudoFirebase() {

            final String user_id = fbAuth.getCurrentUser().getUid();

            DiscRefFirebase.child(disciplina).child(ano).child(bimestre).
                    child("conteudo").child(position_firebase).child(user_id).removeValue();

            listData_III_Bimestre.get(position_check).setB(false);

            notifyItemChanged(position_check);
            Toast.makeText(getContext(), "Salvo" , Toast.LENGTH_SHORT).show();

        }

    }


    public void GetDataFirebase_IV_Bimestre() {

        //--------IV_Bimestre-----------//

        //------Titulo_IV_Bimestre------//
        DBR_Titulo_IV_Bimestre = FDB.getReferenceFromUrl("https://matriz-sobral-194718.firebaseio.com/disciplinas/matematica/8_ano/4_bimestre/titulo/0/x");
        DBR_Titulo_IV_Bimestre.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String str_Titulo_IV_Bimestre = dataSnapshot.getValue(String.class);
                sTitulo_IV_Bimestre = str_Titulo_IV_Bimestre;
                tv_Titulo_IV_Bimestre.setText(str_Titulo_IV_Bimestre);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //------Conteudo_I_Bimestre------//
        DBR = FDB.getReference("disciplinas").child("matematica").child("8_ano").child("4_bimestre").child("conteudo");
        DBR.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                MyDataGetSet x = dataSnapshot.getValue(MyDataGetSet.class);

                listData_IV_Bimestre.add(x);
                rv_IV_Bimestre.setAdapter(adp_matematica_oitavo_IV);

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

    public class Adaptador_Matematica_oitavo_IV_Bimestre extends RecyclerView.Adapter<Adaptador_Matematica_oitavo_IV_Bimestre.ViewholderMatematica_oitavo_IV_Bimestre> {

        List<MyDataGetSet> listArray_IV;
        int   cont_act_4 = 1;

        public Adaptador_Matematica_oitavo_IV_Bimestre(List<MyDataGetSet> List) {
            this.listArray_IV = List;
        }

        @Override
        public ViewholderMatematica_oitavo_IV_Bimestre onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_itemview_lista_conteudo, parent, false);

            return new ViewholderMatematica_oitavo_IV_Bimestre(view);
        }

        //Aqui instancia a textview e a checkbox
        public class ViewholderMatematica_oitavo_IV_Bimestre extends RecyclerView.ViewHolder {
            TextView myText;
            CheckBox mCB;
            LinearLayout layout_item_view;
            View mView;

            public ViewholderMatematica_oitavo_IV_Bimestre(View itemView) {
                super(itemView);
                mView = itemView;

                myText = (TextView) itemView.findViewById(R.id.text_itemview);
                mCB = (CheckBox) itemView.findViewById(R.id.cb_itemView_);
                layout_item_view = (LinearLayout) itemView.findViewById(R.id.layout_item_view);

            }
        }

        @Override
        public void onBindViewHolder(final ViewholderMatematica_oitavo_IV_Bimestre holder, final int position) {
            MyDataGetSet x = listArray_IV.get(position);
            holder.myText.setText(x.getX());
            holder.mCB.setChecked(x.isB());
            holder.setIsRecyclable(true);



            //Método que faz a captura do LongClick na Lista de Conteúdos.
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    bimestre = "4_bimestre";
                    position_check = position;
                    position_firebase = Integer.toString(position);
                    ExibeDialogOpcoes();
                    return true;
                }
            });

        }

        @Override
        public int getItemCount() {
            if (listArray_IV != null){
                if (cont_act_4 == 1){
                    carrega_saves();
                    cont_act_4 = 0;
                }
                return listArray_IV.size();
            }
            else
                return 0;
        }

        private void carrega_saves(){

            DiscRefFirebase = FirebaseDatabase.getInstance().getReference().child("disciplinas");
            final String user_id = fbAuth.getCurrentUser().getUid();
            bimestre = "4_bimestre";

            for(int cont =0; cont<= listArray_IV.size(); cont ++){
                final int cont_ = cont;
                final String posicao_lista = Integer.toString(cont_);

                DiscRefFirebase.child(disciplina).child(ano).child(bimestre).
                        child("conteudo").child(posicao_lista).child(user_id).child(user_id).
                        addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()){
                                    String isSave = (String) dataSnapshot.getValue();
                                    if (isSave.equals("true")){
                                        listData_IV_Bimestre.get(cont_).setB(true);
                                        notifyItemChanged(cont_);
                                    } else{
                                        listData_IV_Bimestre.get(cont_).setB(false);
                                        notifyItemChanged(cont_);
                                    }

                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
            }

        }

        //MÉTODO A SER USADO EM TODAS AS RECYCLERVIEWS
        //Dialog que mostra as opções a serem escolhidas após o Long Click no itemView.
        // É chamado dentro de onLongClick(View v) que está dentro de onBindViewHolder
        public void ExibeDialogOpcoes() {
            final Dialog dialog = new Dialog(getActivity());

            dialog.setContentView(R.layout.layout_opcoes_click_itemview);

            //instancia os objetos que estão no layout customdialog.xml
            final TextView btn_marcar_conteudo = (TextView) dialog.findViewById(R.id.btn_marcar_conteudo);
            final TextView btn_desmarcar_conteudo = (TextView) dialog.findViewById(R.id.btn_desmarcar_conteúdo);
            final TextView btn_abrir_anotacoes = (TextView) dialog.findViewById(R.id.btn_abrir_anotacoes);

            //Aqui marca o conteúdo
            btn_marcar_conteudo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(getContext(), "Conteúdo marcado ", Toast.LENGTH_SHORT).show();

                    MarcaConteudoFirebase();
                    dialog.dismiss();
                }
            });

            //Aqui desmarca o conteúdo
            btn_desmarcar_conteudo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(getContext(), "Conteúdo desmarcado ", Toast.LENGTH_SHORT).show();
                    DesmarcaConteudoFirebase();
                    dialog.dismiss();
                }
            });

            //Aqui deve abrir o documento desse conteúdo no docs.
            btn_abrir_anotacoes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(getContext(), "Abrir anotações deste conteúdo", Toast.LENGTH_SHORT).show();
                    ((Main_activity) getActivity()).disciplina = "Matemática";
                    ((Main_activity) getActivity()).ano = "m_8ano";
                    String p = String.valueOf(position_check);
                    ((Main_activity) getActivity()).titulo = "m8a"+p+"_"+sTitulo_IV_Bimestre;
                    ((Main_activity) getActivity()).text1 = listData_IV_Bimestre.get(position_check).getX();
                    ((Main_activity) getActivity()).getResultsFromApi();
                    dialog.dismiss();
                }
            });

            //exibe na tela o dialog
            dialog.show();

        }

        //Método para Marcar o conteúdo, escrevendo a ID do usuário que estará gravada dentro do
        //child do item do conteúdo no Firebase. Ele é chamado dentro do btn_marcar_conteudo que
        //está dentro do ExibeDialogOpcoes.
        private void MarcaConteudoFirebase() {
            final String user_id = fbAuth.getCurrentUser().getUid();

            HashMap userMap_inicio_1 = new HashMap();

            userMap_inicio_1.put(user_id, "true");

            DiscRefFirebase.child(disciplina).child(ano).child(bimestre).
                    child("conteudo").child(position_firebase).child(user_id).
                    updateChildren(userMap_inicio_1).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getContext(), "Salvo", Toast.LENGTH_LONG).show();
                        listData_IV_Bimestre.get(position_check).setB(true);
                        notifyItemChanged(position_check);
                    } else {
                        String message = task.getException().getMessage();
                        Toast.makeText(getContext(), "Não salvo. Erro: " + message, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        //Método para desmarcar o conteúdo, apagando a ID do usuário que estará gravada dentro do
        //child do item do conteúdo no Firebase. Ele é chamado dentro do btn_desmarcar_conteudo que
        //está dentro do ExibeDialogOpcoes.
        private void DesmarcaConteudoFirebase() {

            final String user_id = fbAuth.getCurrentUser().getUid();

            DiscRefFirebase.child(disciplina).child(ano).child(bimestre).
                    child("conteudo").child(position_firebase).child(user_id).removeValue();

            listData_IV_Bimestre.get(position_check).setB(false);

            notifyItemChanged(position_check);
            Toast.makeText(getContext(), "Salvo" , Toast.LENGTH_SHORT).show();

        }

    }


    @Override
    public void onStart() {

        super.onStart();

    }




    //===========================MÉTODO QUE RECEBE AS SOMBRAS DOS BIMESTRES===========================
    @Override
    public void onViewCreated (View view, Bundle savedInstanceState) {
        ((Main_activity) getActivity()).SombraBimestre(view);
        ((Main_activity) getActivity()).tab_obj_mat_oitavo_(view);



    }
    //==============================================================================================

}
