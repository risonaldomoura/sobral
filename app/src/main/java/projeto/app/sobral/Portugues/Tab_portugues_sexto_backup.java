package projeto.app.sobral.Portugues;

import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
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

//import android.app.Fragment;

/**
 * Created by Daniel on 09/01/2018.
 */

public class Tab_portugues_sexto_backup extends Fragment{

    RecyclerView rv_I_Bimestre;
    TextView tv_Titulo_I_Bimestre;

    Adaptador_Portugues_sexto adp_portugues_sexto;
    List<MyDataGetSet> listData;
    FirebaseDatabase FDB;
    //
    DatabaseReference DBR;
    DatabaseReference DBR_Titulo_I_Bimestre;

    DatabaseReference DiscRefFirebase;

    String titulo;

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
    public String disciplina = "portugues", ano, bimestre, posicao_item_lista;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rView = inflater.inflate(R.layout.layout_model_conteudo,container,false);

        fbAuth = FirebaseAuth.getInstance();
        DiscRefFirebase = FirebaseDatabase.getInstance().getReference().child("disciplinas");


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

        //=========== INÍCIO DO TRATAMENTO DOS ADPATADORES PARA CARREGAR A LISTA DE CONTEÚDOS DOS BIMESTRES===============

        rv_I_Bimestre = (RecyclerView) rView.findViewById(R.id.recyclerView_I_Bimestre);
        tv_Titulo_I_Bimestre = (TextView) rView.findViewById(R.id.Titulo_I_Bimestre);

        rv_I_Bimestre.setHasFixedSize(true);
        RecyclerView.LayoutManager LM =  new LinearLayoutManager(getActivity().getApplicationContext());
        //RecyclerView.LayoutManager LM =  new LinecmxndanielarLayoutManager(getApplicationContext());
        rv_I_Bimestre.setLayoutManager(LM);
        rv_I_Bimestre.setItemAnimator(new DefaultItemAnimator());
        rv_I_Bimestre.addItemDecoration(new DividerItemDecoration(getActivity().getApplicationContext(),LinearLayoutManager.VERTICAL));

        listData=  new ArrayList<>();
        adp_portugues_sexto = new Adaptador_Portugues_sexto(listData);
        FDB = FirebaseDatabase.getInstance();

        GetDataFirebase();
        //=================FIM DO TRATAMENTO DOS ADAPTADORES PARA CARREGAR A LISTA DE CONTEÚDOS DOS BIMESTRES==================

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

    public void GetDataFirebase(){

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
        DBR = FDB.getReference("disciplinas").child("portugues").child("6_ano").child("1_bimestre").child("conteudo");
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


    public class Adaptador_Portugues_sexto extends RecyclerView.Adapter<Adaptador_Portugues_sexto.ViewholderPortugues_sexto> {

        List<MyDataGetSet> listArray;

        public  Adaptador_Portugues_sexto(List<MyDataGetSet> List){
            this.listArray = List;
        }

        @Override
        public ViewholderPortugues_sexto onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_itemview_lista_conteudo,parent,false);

            return new ViewholderPortugues_sexto(view);
        }

        //Aqui instancia a textview e a checkbox
        public class ViewholderPortugues_sexto extends RecyclerView.ViewHolder {
            TextView myText;
            CheckBox checkbox;
            LinearLayout layout_item_view;
            View mView;

            public ViewholderPortugues_sexto(View itemView) {
                super(itemView);
                mView = itemView;

                myText = (TextView) itemView.findViewById(R.id.text_itemview);
                checkbox = (CheckBox) itemView.findViewById(R.id.cb_itemView_);
                layout_item_view = (LinearLayout) itemView.findViewById(R.id.layout_item_view);

            }
        }

        @Override
        public void onBindViewHolder(final Adaptador_Portugues_sexto.ViewholderPortugues_sexto holder, final int position) {
            MyDataGetSet x = listArray.get(position);
            holder.myText.setText(x.getX());

            ano = "6_ano";
            bimestre = "1_bimestre";

            posicao_item_lista = Integer.toString (holder.getAdapterPosition());



           //holder.checkbox.setChecked(true); //Esse seta todas as checkboxs

            //holder.getAdapterPosition().setChecked(true);

            //MÉTODO A SER USADO EM TODAS AS RECYCLERVIEWS
            //Método que faz a captura do LongClick na Lista de Conteúdos.
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener(){
                @Override
                public boolean onLongClick(View v) {
                    //Toast.makeText(getContext(), "Long Click: "+holder.getAdapterPosition() , Toast.LENGTH_SHORT).show();

                    //String conteudo_clicado = Adaptador_Portugues_sexto_I_Bimestre.listArray_I.get(position).getX();
                    //Toast.makeText(getContext(), conteudo_clicado, Toast.LENGTH_SHORT).show();
                    posicao_item_lista = Integer.toString (holder.getAdapterPosition());
                    ExibeDialogOpcoes();
                    verifica_fire();
                    //VerificaUidFirebase();
                    return true;
                }
            });

        }

        @Override
        public int getItemCount() {
            if (listArray != null)
                return listArray.size();
            else
                return 0;
        }
    }

    //MÉTODO A SER USADO EM TODAS AS RECYCLERVIEWS
    //Dialog que mostra as opções a serem escolhidas após o Long Click no itemView.
    // É chamado dentro de onLongClick(View v) que está dentro de onBindViewHolder
    private void ExibeDialogOpcoes(){
        final Dialog dialog = new Dialog(getContext());

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
                Toast.makeText(getContext(), "Abrir anotações deste conteúdo", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        //exibe na tela o dialog
        dialog.show();

    }

    //Método para Marcar o conteúdo, escrevendo a ID do usuário que estará gravada dentro do
    //child do item do conteúdo no Firebase. Ele é chamado dentro do btn_marcar_conteudo que
    //está dentro do ExibeDialogOpcoes.
    private void MarcaConteudoFirebase(){
        final String user_id = fbAuth.getCurrentUser().getUid();

        HashMap userMap_inicio_1 = new HashMap();

        userMap_inicio_1.put(user_id, "uid");

        DiscRefFirebase.child(disciplina).child(ano).child(bimestre).
                child("conteudo").child(posicao_item_lista).
                updateChildren(userMap_inicio_1).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task)
            {
                if(task.isSuccessful())
                {
                    Toast.makeText(getContext(), "Salvo", Toast.LENGTH_LONG).show();
                }
                else
                {
                    String message =  task.getException().getMessage();
                    Toast.makeText(getContext(), "Não salvo. Erro: " + message, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Método para desmarcar o conteúdo, apagando a ID do usuário que estará gravada dentro do
    //child do item do conteúdo no Firebase. Ele é chamado dentro do btn_desmarcar_conteudo que
    //está dentro do ExibeDialogOpcoes.
    private void DesmarcaConteudoFirebase(){
        final String user_id = fbAuth.getCurrentUser().getUid();

        DiscRefFirebase.child(disciplina).child(ano).child(bimestre).
                child("conteudo").child(posicao_item_lista).child(user_id).removeValue();

    }

    public void verifica_fire(){

        final String user_id = fbAuth.getCurrentUser().getUid();

        DiscRefFirebase.child(disciplina).child(ano).child(bimestre).
                child("conteudo").child(posicao_item_lista).child(user_id);


        ValueEventListener post_inicio_1_Listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){



                        //Toast.makeText(getContext(), "Posição: 0" +position_firebase+" Status: SIM", Toast.LENGTH_SHORT).show();
                        Toast.makeText(getContext(), "Status: SIM", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        //Toast.makeText(getContext(), "Posição: 0" +position_firebase+" Status: NÃO", Toast.LENGTH_SHORT).show();
                        Toast.makeText(getContext(), " Status: NÃO", Toast.LENGTH_SHORT).show();
                    }





            }
            @Override
            public void onCancelled(DatabaseError databaseError){}
        };
        DiscRefFirebase.addValueEventListener(post_inicio_1_Listener);



        /*

        DiscRefFirebase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.hasChild(user_id)) {
                    Toast.makeText(getContext(), "Status: SIM", Toast.LENGTH_SHORT).show();
                }
                else {
                    //Toast.makeText(getContext(), "Posição: 0" +position_firebase+" Status: NÃO", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getContext(), " Status: NÃO", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
    }

    public void VerificaUidFirebase(){

        final String user_id = fbAuth.getCurrentUser().getUid();

        DiscRefFirebase.child(disciplina).child(ano).child(bimestre).
                child("conteudo").child(posicao_item_lista).child(user_id).child(user_id);

        ValueEventListener post_Listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){

                    //Toast.makeText(getContext(), "Posição: 0" +position_firebase+" Status: SIM", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getContext(), "Status: SIM", Toast.LENGTH_SHORT).show();
                }
                else {
                    //Toast.makeText(getContext(), "Posição: 0" +position_firebase+" Status: NÃO", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getContext(), " Status: NÃO", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError){}
        };
        DiscRefFirebase.addValueEventListener(post_Listener);

    }

    //===========================MÉTODO QUE RECEBE AS SOMBRAS DOS BIMESTRES===========================
    @Override
    public void onViewCreated (View view, Bundle savedInstanceState) {
        ((Main_activity) getActivity()).SombraBimestre(view);
        ((Main_activity) getActivity()).tab_obj_port_sexto_(view);
    }
    //==============================================================================================

}
