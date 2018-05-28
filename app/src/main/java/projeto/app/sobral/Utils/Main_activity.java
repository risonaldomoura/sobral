package projeto.app.sobral.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import java.util.Calendar;
import java.util.HashMap;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import projeto.app.sobral.R;
import projeto.app.sobral.Matematica.Tab_matematica_nono;
import projeto.app.sobral.Matematica.Tab_matematica_oitavo;
import projeto.app.sobral.Matematica.Tab_matematica_setimo;
import projeto.app.sobral.Matematica.Tab_matematica_sexto;
import projeto.app.sobral.Portugues.Tab_portugues_nono;
import projeto.app.sobral.Portugues.Tab_portugues_oitavo;
import projeto.app.sobral.Portugues.Tab_portugues_setimo;

import projeto.app.sobral.Portugues.Tab_portugues_sexto_;
import projeto.app.sobral.Portugues.Tab_portugues_setimo_;
import projeto.app.sobral.Portugues.Tab_portugues_oitavo_;
import projeto.app.sobral.Portugues.Tab_portugues_nono_;

import projeto.app.sobral.Matematica.Tab_matematica_sexto_;
import projeto.app.sobral.Matematica.Tab_matematica_setimo_;
import projeto.app.sobral.Matematica.Tab_matematica_oitavo_;
import projeto.app.sobral.Matematica.Tab_matematica_nono_;




public class Main_activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    //Autentição com o Google
    private GoogleApiClient googleApiClient;
    private ImageView photoID;
    private TextView nomeID;
    private RoundedBitmapDrawable roundedBitmapDrawable;
    private Intent data;

    private GoogleSignInAccount GoogleSignInAccount;

    //Firebase
    private FirebaseAuth fbAuth;
    private FirebaseAuth.AuthStateListener fbAuthListener;

    private DatabaseReference UsersRef;

    private FirebaseDatabase FirebaseDatabase;
    private DatabaseReference UserData;

    private Spinner spn_disciplina;
    public static int IDAtual;
    public static int ID;
    public static int IDAnterior;

    public int execucao = 1;
    public int configurar;

    public int Dia_sistema;
    public int Mes_sistema;

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

    private ArrayAdapter<String> adp_disciplina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        //Persistência dos dados do Firebase no dispositivo
        DatabasePersistence.getDatabase();

        fbAuth = FirebaseAuth.getInstance();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("users");
        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        //load do ID do estado de configurar bimestre
        SharedPreferences sharedPref_configurar = getSharedPreferences("pref_bimestre",MODE_PRIVATE);
        configurar = sharedPref_configurar.getInt("configurar",-1);

        //Salva booleana para saber se o app já executou anteriormente
        SharedPreferences sharedPref_execucao = getSharedPreferences("pref_bimestre",0);
        SharedPreferences.Editor prefEditor = sharedPref_execucao.edit();
        prefEditor.putInt("execucao",execucao);
        prefEditor.commit();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        spn_disciplina = (Spinner) findViewById(R.id.spn_disciplina);
        adp_disciplina = new ArrayAdapter<String>(this, R.layout.layout_spinner);
        adp_disciplina.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_disciplina.setAdapter(adp_disciplina);

        adp_disciplina.add("Língua portuguesa");
        adp_disciplina.add("Matemática");

        //FrameLayout da spinner
        final FrameLayout fl_spinner = (FrameLayout) findViewById(R.id.fl_spinner);

        //Botao OK dos objetivos
        Button btn_ok = (Button) findViewById(R.id.btn_ok_obj);
        final FrameLayout fl_obj = (FrameLayout) findViewById(R.id.fl_obj);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fl_obj.setVisibility(View.GONE);
                fl_spinner.setVisibility(View.VISIBLE);
            }
        });

        //FrameLayout contato
        final FrameLayout fl_contato = (FrameLayout) findViewById(R.id.fl_contato);
        Button btn_ok_contato;
        btn_ok_contato = (Button) findViewById(R.id.btn_ok_contato);
        btn_ok_contato.setOnClickListener(new View.OnClickListener() {
                                                 @Override
                                                 public void onClick(View v) {
                                                     fl_contato.setVisibility(View.GONE);
                                                     fl_spinner.setVisibility(View.VISIBLE);
                                                 }
                                             }
        );

        //FrameLayout matematica
        final FrameLayout fl_matematica = (FrameLayout) findViewById(R.id.fl_matematica);
        Button btn_ok_matematica;
        btn_ok_matematica = (Button) findViewById(R.id.btn_ok_matematica);
        btn_ok_matematica.setOnClickListener(new View.OnClickListener() {
                                                 @Override
                                                 public void onClick(View v) {
                                                     fl_matematica.setVisibility(View.GONE);
                                                     fl_spinner.setVisibility(View.VISIBLE);
                                                 }
                                             }
        );

        //========================FrameLayout PORTUGUEs=====================================================================================
        // -------------------- TEXTO DE APRESENTAÇÂO------------------------------------------
        DatabaseReference DBR_FL_Portugues;

        FirebaseDatabase FDB1 = FirebaseDatabase.getInstance();

        DBR_FL_Portugues = FDB1.getReferenceFromUrl("https://matriz-sobral-194718.firebaseio.com/disciplinas/portugues/apresentacao/1/x");
        DBR_FL_Portugues.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                //FrameLayout portugues ------ Conteudo
                final String str_dbr_fl_portugues = dataSnapshot.getValue(String.class);
                final TextView apresentacao_fl_portugues = (TextView) findViewById(R.id.txt_apresentacao_portugues);
                apresentacao_fl_portugues.setText(str_dbr_fl_portugues);
                final FrameLayout fl_portugues = (FrameLayout) findViewById(R.id.fl_portugues);
                //TextView tv_fl_portugues = (TextView) findViewById(R.id.txt_apresentacao_portugues);
                //tv_fl_portugues.setText();
                Button btn_ok_portugues;
                btn_ok_portugues = (Button) findViewById(R.id.btn_ok_portugues);
                btn_ok_portugues.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            fl_portugues.setVisibility(View.GONE);
                                                            fl_spinner.setVisibility(View.VISIBLE);



                                                            String apr_port = str_dbr_fl_portugues;
                                                            TextView txt_apr_port = (TextView) findViewById(R.id.txt_apresentacao_portugues);
                                                            txt_apr_port.setText(apr_port);
                                                        }
                                                    }
                );
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // ------------------------------------- TÍTULO ----------------------------------------
        DatabaseReference DBR_titulo_FL_Portugues;

        FirebaseDatabase FDB2 = FirebaseDatabase.getInstance();

        DBR_titulo_FL_Portugues = FDB2.getReferenceFromUrl("https://matriz-sobral-194718.firebaseio.com/disciplinas/portugues/apresentacao/0/x");
        DBR_titulo_FL_Portugues.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                //FrameLayout portugues ------ Titulo
                final String str_dbr_fl_titulo_portugues = dataSnapshot.getValue(String.class);
                final TextView apresentacao_fl_titulo_portugues = (TextView) findViewById(R.id.titulo_apresentacao_portugues);
                apresentacao_fl_titulo_portugues.setText(str_dbr_fl_titulo_portugues);

                final FrameLayout fl_titulo_portugues = (FrameLayout) findViewById(R.id.fl_portugues);
                Button btn_ok_portugues;
                btn_ok_portugues = (Button) findViewById(R.id.btn_ok_portugues);
                btn_ok_portugues.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            fl_titulo_portugues.setVisibility(View.GONE);
                                                            fl_spinner.setVisibility(View.VISIBLE);



                                                            String tit_port = str_dbr_fl_titulo_portugues;
                                                            TextView txt_apr_tit_port = (TextView) findViewById(R.id.titulo_apresentacao_portugues);
                                                            txt_apr_tit_port.setText(tit_port);
                                                        }
                                                    }
                );


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //======================== FIM FrameLayout PORTUGUES=====================================================================================






        //========================FrameLayout MATEMATICA=====================================================================================
        // -------------------- TEXTO DE APRESENTAÇÂO------------------------------------------
        DatabaseReference DBR_FL_Matematica;

        FirebaseDatabase FDB3 = FirebaseDatabase.getInstance();

        DBR_FL_Matematica = FDB3.getReferenceFromUrl("https://matriz-sobral-194718.firebaseio.com/disciplinas/matematica/apresentacao/1/x");
        DBR_FL_Matematica.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                //FrameLayout matematica ------ Conteudo
                final String str_dbr_fl_matematica = dataSnapshot.getValue(String.class);
                final TextView apresentacao_fl_matematica = (TextView) findViewById(R.id.txt_apresentacao_matematica);
                apresentacao_fl_matematica.setText(str_dbr_fl_matematica);
                final FrameLayout fl_matematica = (FrameLayout) findViewById(R.id.fl_matematica);

                Button btn_ok_matematica;
                btn_ok_matematica = (Button) findViewById(R.id.btn_ok_matematica);
                btn_ok_matematica.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            fl_matematica.setVisibility(View.GONE);
                                                            fl_spinner.setVisibility(View.VISIBLE);



                                                            String apr_mat = str_dbr_fl_matematica;
                                                            TextView txt_apr_mat = (TextView) findViewById(R.id.txt_apresentacao_portugues);
                                                            txt_apr_mat.setText(apr_mat);
                                                        }
                                                    }
                );
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // ------------------------------------- TÍTULO ----------------------------------------
        DatabaseReference DBR_titulo_FL_Matematica;

        FirebaseDatabase FDB4 = FirebaseDatabase.getInstance();

        DBR_titulo_FL_Matematica = FDB4.getReferenceFromUrl("https://matriz-sobral-194718.firebaseio.com/disciplinas/matematica/apresentacao/0/x");
        DBR_titulo_FL_Matematica.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                //FrameLayout matematica ------ Titulo
                final String str_dbr_fl_titulo_matematica = dataSnapshot.getValue(String.class);
                final TextView apresentacao_fl_titulo_matematica = (TextView) findViewById(R.id.titulo_apresentacao_matematica);
                apresentacao_fl_titulo_matematica.setText(str_dbr_fl_titulo_matematica);

                final FrameLayout fl_titulo_matematica = (FrameLayout) findViewById(R.id.fl_matematica);
                Button btn_ok_matematica;
                btn_ok_matematica = (Button) findViewById(R.id.btn_ok_matematica);
                btn_ok_matematica.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            fl_titulo_matematica.setVisibility(View.GONE);
                                                            fl_spinner.setVisibility(View.VISIBLE);



                                                            String tit_mat = str_dbr_fl_titulo_matematica;
                                                            TextView txt_apr_tit_mat = (TextView) findViewById(R.id.titulo_apresentacao_matematica);
                                                            txt_apr_tit_mat.setText(tit_mat);
                                                        }
                                                    }
                );


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //========================FIM FrameLayout MATEMATICA=====================================================================================


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header  = navigationView.getHeaderView(0);
        photoID = (ImageView) header.findViewById(R.id.imageViewPhoto);
        nomeID = (TextView) header.findViewById(R.id.textViewEmailUsuario);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient= new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();


        fbAuth = FirebaseAuth.getInstance();
        fbAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){
                FirebaseUser user = fbAuth.getCurrentUser();
                if(user != null){
                    setUserData(user);
                }
            }
        };

        data_bimestre_shared_pref();
        //==========================SPINNER DISCIPLINA==============================================
        //load do ID da spinner do arquivo sharedPreferences
        SharedPreferences sharedPref = getSharedPreferences("disciplina",MODE_PRIVATE);
        int ID_salvo_disciplina = sharedPref.getInt("selecaodisciplina",0);

        // set the value of the spinner
        spn_disciplina.setSelection(ID_salvo_disciplina); //Seta a posição da spinner

        ID = ID_salvo_disciplina; //Seta o valor salvo para a ID da spinner

        //Método do spinner para capturar o item selecionado
        spn_disciplina.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id1) {

                //Save do ID da spinner no sharedPreferences
                int IDescolhadisciplina = spn_disciplina.getSelectedItemPosition();
                SharedPreferences sharedPref = getSharedPreferences("disciplina",0);
                SharedPreferences.Editor prefEditor = sharedPref.edit();
                prefEditor.putInt("selecaodisciplina",IDescolhadisciplina);
                prefEditor.commit();


                ID = posicao;

                IDAtual = ID;

                if (IDAtual != IDAnterior) {

                    startActivity(this);
                    IDAnterior = IDAtual;
                    //onPause();

                } else {

                }

            }

            public int getId(){
                return ID;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });
        //==========================================================================================

        DataSistema();




    }//end OnCreate




    //Verifica se tem conexão com a internet
    private void verificaconexao() {
        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        final DrawerLayout DrawerLayout = (DrawerLayout) findViewById(R.id
                .drawer_layout);

        @SuppressLint("WifiManagerLeak") final WifiManager wifi = (WifiManager)this.getSystemService(Context.WIFI_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean connected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if (connected) {
            //System.out.println("connected");
        } else if (!connected){
            Snackbar snackbar = Snackbar
                    .make(DrawerLayout, "Sem Conexão à internet", Snackbar.LENGTH_LONG)
                    .setAction("ATIVAR WIFI", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            wifi.setWifiEnabled(true);
                        }
                    });
            snackbar.setActionTextColor(Color.RED);
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackbar.show();

            //System.out.println("not connected");
        }
    }

    public void data_bimestre_shared_pref()
    {
        //=================PRIMEIRO BIMESTRE========================================================

        //DIA INÍCIO
        SharedPreferences sharedPref_dia_inicio_I = getSharedPreferences("pref_bimestre",MODE_PRIVATE);
        ID_salvo_dia_inicio_I = sharedPref_dia_inicio_I.getInt("dia_inicio_balao_1", 15);


        //DIA TÉRMINO
        SharedPreferences sharedPref_dia_termino_I = getSharedPreferences("pref_bimestre",MODE_PRIVATE);
        ID_salvo_dia_termino_I = sharedPref_dia_termino_I.getInt("dia_termino_balao_1",15);

        //MÊS INÍCIO
        SharedPreferences sharedPref_mes_inicio_I = getSharedPreferences("pref_bimestre",MODE_PRIVATE);
        ID_salvo_mes_inicio_I = sharedPref_mes_inicio_I.getInt("mes_inicio_balao_1",1);

        //MÊS TÉRMINO
        SharedPreferences sharedPref_mes_termino_I = getSharedPreferences("pref_bimestre",MODE_PRIVATE);
        ID_salvo_mes_termino_I = sharedPref_mes_termino_I.getInt("mes_termino_balao_1",3);

        //==========================================================================================

        //=================SEGUNDO BIMESTRE=========================================================

        //DIA INICIO
        SharedPreferences sharedPref_dia_inicio_II = getSharedPreferences("pref_bimestre",MODE_PRIVATE);
        ID_salvo_dia_inicio_II = sharedPref_dia_inicio_II.getInt("dia_inicio_balao_2",16);

        //DIA TÉRMINO
        SharedPreferences sharedPref_dia_termino_II = getSharedPreferences("pref_bimestre",MODE_PRIVATE);
        ID_salvo_dia_termino_II = sharedPref_dia_termino_II.getInt("dia_termino_balao_2",16);

        //MES INÍCIO
        SharedPreferences sharedPref_mes_inicio_II = getSharedPreferences("pref_bimestre",MODE_PRIVATE);
        ID_salvo_mes_inicio_II = sharedPref_mes_inicio_II.getInt("mes_inicio_balao_2",3);

        //MÊS TÉRMINO
        SharedPreferences sharedPref_mes_termino_II = getSharedPreferences("pref_bimestre",MODE_PRIVATE);
        ID_salvo_mes_termino_II = sharedPref_mes_termino_II.getInt("mes_termino_balao_2",5);

        //==========================================================================================

        //=================TERCEIRO BIMESTRE========================================================

        //DIA INÍCIO
        SharedPreferences sharedPref_dia_inicio_III = getSharedPreferences("pref_bimestre",MODE_PRIVATE);
        ID_salvo_dia_inicio_III = sharedPref_dia_inicio_III.getInt("dia_inicio_balao_3",5);

        //DIA TÉRMINO
        SharedPreferences sharedPref_dia_termino_III = getSharedPreferences("pref_bimestre",MODE_PRIVATE);
        ID_salvo_dia_termino_III = sharedPref_dia_termino_III.getInt("dia_termino_balao_3",5);

        //MES INÍCIO
        SharedPreferences sharedPref_mes_inicio_III = getSharedPreferences("pref_bimestre",MODE_PRIVATE);
        ID_salvo_mes_inicio_III = sharedPref_mes_inicio_III.getInt("mes_inicio_balao_3",7);

        //MÊS TÉRMINO
        SharedPreferences sharedPref_mes_termino_III = getSharedPreferences("pref_bimestre",MODE_PRIVATE);
        ID_salvo_mes_termino_III = sharedPref_mes_termino_III.getInt("mes_termino_balao_3",9);

        //==========================================================================================

        //=================QUARTO BIMESTRE==========================================================

        //DIA INÍCIO
        SharedPreferences sharedPref_dia_inicio_IV = getSharedPreferences("pref_bimestre",MODE_PRIVATE);
        ID_salvo_dia_inicio_IV = sharedPref_dia_inicio_IV.getInt("dia_inicio_balao_4",6);

        //DIA TÉRMINO
        SharedPreferences sharedPref_dia_termino_IV = getSharedPreferences("pref_bimestre",MODE_PRIVATE);
        ID_salvo_dia_termino_IV = sharedPref_dia_termino_IV.getInt("dia_termino_balao_4",6);

        //MES INÍCIO
        SharedPreferences sharedPref_mes_inicio_IV = getSharedPreferences("pref_bimestre",MODE_PRIVATE);
        ID_salvo_mes_inicio_IV = sharedPref_mes_inicio_IV.getInt("mes_inicio_balao_4",9);

        //MÊS TÉRMINO
        SharedPreferences sharedPref_mes_termino_IV = getSharedPreferences("pref_bimestre",MODE_PRIVATE);
        ID_salvo_mes_termino_IV = sharedPref_mes_termino_IV.getInt("mes_termino_balao_4",11);

        //==========================================================================================

    }

    //===================Tab OBJETIVO PORTUGUES=================================================
    public void tab_obj_port_sexto_(View view) {
        final FrameLayout fl_obj = (FrameLayout) findViewById(R.id.fl_obj);
        final FrameLayout fl_spinner = (FrameLayout) findViewById(R.id.fl_spinner);

        //=========================================BOTÃO OBJ PORT SEXTO 1-Bimestre===========================================
        final Button btn1 = (Button) view.findViewById(R.id.btn_obj_I);

        //========== Texto Botão Objetivo Portugues Sexto ========================================
        DatabaseReference DBR_Obj_I_Bimestre;
        FirebaseDatabase FDB3 = FirebaseDatabase.getInstance();

        DBR_Obj_I_Bimestre = FDB3.getReferenceFromUrl("https://matriz-sobral-194718.firebaseio.com/disciplinas/portugues/6_ano/1_bimestre/objetivo/0/x");
        DBR_Obj_I_Bimestre.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String str_dbr_obj_i_bimestre = dataSnapshot.getValue(String.class);

                //TextView obj_conteudo_i_bimestre = (TextView) findViewById(R.id.txt_obj);

                //obj_conteudo_i_bimestre.setText(str_dbr_obj_i_bimestre);

                btn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        fl_obj.setVisibility(View.VISIBLE);
                        fl_spinner.setVisibility(View.GONE);




                        String obj = str_dbr_obj_i_bimestre;
                        TextView txt_obj = (TextView) findViewById(R.id.txt_objetivo);
                        txt_obj.setText(obj);

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //=========================================BOTÃO OBJ PORT SEXTO 1-Bimestre===========================================





        //=========================================BOTÃO OBJ PORT SEXTO 2-Bimestre===========================================
        final Button btn2 = (Button) view.findViewById(R.id.btn_obj_II);

        //========== Texto Botão Objetivo Portugues Sexto ========================================
        DatabaseReference DBR_Obj_II_Bimestre;
        FirebaseDatabase FDB4 = FirebaseDatabase.getInstance();

        DBR_Obj_II_Bimestre = FDB4.getReferenceFromUrl("https://matriz-sobral-194718.firebaseio.com/disciplinas/portugues/6_ano/2_bimestre/objetivo/0/x");
        DBR_Obj_II_Bimestre.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String str_dbr_obj_ii_bimestre = dataSnapshot.getValue(String.class);

                //TextView obj_conteudo_i_bimestre = (TextView) findViewById(R.id.txt_obj);

                //obj_conteudo_i_bimestre.setText(str_dbr_obj_i_bimestre);

                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        fl_obj.setVisibility(View.VISIBLE);
                        fl_spinner.setVisibility(View.GONE);




                        String obj = str_dbr_obj_ii_bimestre;
                        TextView txt_obj = (TextView) findViewById(R.id.txt_objetivo);
                        txt_obj.setText(obj);

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //=========================================BOTÃO OBJ PORT SEXTO 2-Bimestre===========================================




        //=========================================BOTÃO OBJ PORT SEXTO 3-Bimestre===========================================
        final Button btn3 = (Button) view.findViewById(R.id.btn_obj_III);

        //========== Texto Botão Objetivo Portugues Sexto ========================================
        DatabaseReference DBR_Obj_III_Bimestre;
        FirebaseDatabase FDB5 = FirebaseDatabase.getInstance();

        DBR_Obj_III_Bimestre = FDB5.getReferenceFromUrl("https://matriz-sobral-194718.firebaseio.com/disciplinas/portugues/6_ano/3_bimestre/objetivo/0/x");
        DBR_Obj_III_Bimestre.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String str_dbr_obj_iii_bimestre = dataSnapshot.getValue(String.class);

                //TextView obj_conteudo_i_bimestre = (TextView) findViewById(R.id.txt_obj);

                //obj_conteudo_i_bimestre.setText(str_dbr_obj_i_bimestre);

                btn3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        fl_obj.setVisibility(View.VISIBLE);
                        fl_spinner.setVisibility(View.GONE);




                        String obj = str_dbr_obj_iii_bimestre;
                        TextView txt_obj = (TextView) findViewById(R.id.txt_objetivo);
                        txt_obj.setText(obj);

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //=========================================BOTÃO OBJ PORT SEXTO 3-Bimestre===========================================



        //=========================================BOTÃO OBJ PORT SEXTO 4-Bimestre===========================================
        final Button btn4 = (Button) view.findViewById(R.id.btn_obj_IV);

        //========== Texto Botão Objetivo Portugues Sexto ========================================
        DatabaseReference DBR_Obj_IV_Bimestre;
        FirebaseDatabase FDB6 = FirebaseDatabase.getInstance();

        DBR_Obj_IV_Bimestre = FDB5.getReferenceFromUrl("https://matriz-sobral-194718.firebaseio.com/disciplinas/portugues/6_ano/4_bimestre/objetivo/0/x");
        DBR_Obj_IV_Bimestre.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String str_dbr_obj_iv_bimestre = dataSnapshot.getValue(String.class);

                //TextView obj_conteudo_i_bimestre = (TextView) findViewById(R.id.txt_obj);

                //obj_conteudo_i_bimestre.setText(str_dbr_obj_i_bimestre);

                btn4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        fl_obj.setVisibility(View.VISIBLE);
                        fl_spinner.setVisibility(View.GONE);




                        String obj = str_dbr_obj_iv_bimestre;
                        TextView txt_obj = (TextView) findViewById(R.id.txt_objetivo);
                        txt_obj.setText(obj);

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //=========================================BOTÃO OBJ PORT SEXTO 3-Bimestre===========================================

    }


    public void tab_obj_port_setimo_(View view) {
        final FrameLayout fl_obj = (FrameLayout) findViewById(R.id.fl_obj);
        final FrameLayout fl_spinner = (FrameLayout) findViewById(R.id.fl_spinner);

        //=========================================BOTÃO OBJ PORT SETIMO 1-Bimestre===========================================
        final Button btn1 = (Button) view.findViewById(R.id.btn_obj_I);

        //========== Texto Botão Objetivo Portugues Setimo ========================================
        DatabaseReference DBR_Obj_I_Bimestre;
        FirebaseDatabase FDB3 = FirebaseDatabase.getInstance();

        DBR_Obj_I_Bimestre = FDB3.getReferenceFromUrl("https://matriz-sobral-194718.firebaseio.com/disciplinas/portugues/7_ano/1_bimestre/objetivo/0/x");
        DBR_Obj_I_Bimestre.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String str_dbr_obj_i_bimestre = dataSnapshot.getValue(String.class);

                //TextView obj_conteudo_i_bimestre = (TextView) findViewById(R.id.txt_obj);

                //obj_conteudo_i_bimestre.setText(str_dbr_obj_i_bimestre);

                btn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        fl_obj.setVisibility(View.VISIBLE);
                        fl_spinner.setVisibility(View.GONE);




                        String obj = str_dbr_obj_i_bimestre;
                        TextView txt_obj = (TextView) findViewById(R.id.txt_objetivo);
                        txt_obj.setText(obj);

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //=========================================BOTÃO OBJ PORT SETIMO 1-Bimestre===========================================





        //=========================================BOTÃO OBJ PORT SETIMO 2-Bimestre===========================================
        final Button btn2 = (Button) view.findViewById(R.id.btn_obj_II);

        //========== Texto Botão Objetivo Portugues Setimo ========================================
        DatabaseReference DBR_Obj_II_Bimestre;
        FirebaseDatabase FDB4 = FirebaseDatabase.getInstance();

        DBR_Obj_II_Bimestre = FDB4.getReferenceFromUrl("https://matriz-sobral-194718.firebaseio.com/disciplinas/portugues/7_ano/2_bimestre/objetivo/0/x");
        DBR_Obj_II_Bimestre.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String str_dbr_obj_ii_bimestre = dataSnapshot.getValue(String.class);

                //TextView obj_conteudo_i_bimestre = (TextView) findViewById(R.id.txt_obj);

                //obj_conteudo_i_bimestre.setText(str_dbr_obj_i_bimestre);

                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        fl_obj.setVisibility(View.VISIBLE);
                        fl_spinner.setVisibility(View.GONE);




                        String obj = str_dbr_obj_ii_bimestre;
                        TextView txt_obj = (TextView) findViewById(R.id.txt_objetivo);
                        txt_obj.setText(obj);

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //=========================================BOTÃO OBJ PORT SETIMO 2-Bimestre===========================================




        //=========================================BOTÃO OBJ PORT SETIMO 3-Bimestre===========================================
        final Button btn3 = (Button) view.findViewById(R.id.btn_obj_III);

        //========== Texto Botão Objetivo Portugues Setimo ========================================
        DatabaseReference DBR_Obj_III_Bimestre;
        FirebaseDatabase FDB5 = FirebaseDatabase.getInstance();

        DBR_Obj_III_Bimestre = FDB5.getReferenceFromUrl("https://matriz-sobral-194718.firebaseio.com/disciplinas/portugues/7_ano/3_bimestre/objetivo/0/x");
        DBR_Obj_III_Bimestre.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String str_dbr_obj_iii_bimestre = dataSnapshot.getValue(String.class);

                //TextView obj_conteudo_i_bimestre = (TextView) findViewById(R.id.txt_obj);

                //obj_conteudo_i_bimestre.setText(str_dbr_obj_i_bimestre);

                btn3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        fl_obj.setVisibility(View.VISIBLE);
                        fl_spinner.setVisibility(View.GONE);




                        String obj = str_dbr_obj_iii_bimestre;
                        TextView txt_obj = (TextView) findViewById(R.id.txt_objetivo);
                        txt_obj.setText(obj);

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //=========================================BOTÃO OBJ PORT SETIMO 3-Bimestre===========================================



        //=========================================BOTÃO OBJ PORT SETIMO 4-Bimestre===========================================
        final Button btn4 = (Button) view.findViewById(R.id.btn_obj_IV);

        //========== Texto Botão Objetivo Portugues Setimo ========================================
        DatabaseReference DBR_Obj_IV_Bimestre;
        FirebaseDatabase FDB6 = FirebaseDatabase.getInstance();

        DBR_Obj_IV_Bimestre = FDB5.getReferenceFromUrl("https://matriz-sobral-194718.firebaseio.com/disciplinas/portugues/7_ano/4_bimestre/objetivo/0/x");
        DBR_Obj_IV_Bimestre.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String str_dbr_obj_iv_bimestre = dataSnapshot.getValue(String.class);

                //TextView obj_conteudo_i_bimestre = (TextView) findViewById(R.id.txt_obj);

                //obj_conteudo_i_bimestre.setText(str_dbr_obj_i_bimestre);

                btn4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        fl_obj.setVisibility(View.VISIBLE);
                        fl_spinner.setVisibility(View.GONE);




                        String obj = str_dbr_obj_iv_bimestre;
                        TextView txt_obj = (TextView) findViewById(R.id.txt_objetivo);
                        txt_obj.setText(obj);

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //=========================================BOTÃO OBJ PORT SETIMO 3-Bimestre===========================================

    }



    public void tab_obj_port_oitavo_(View view) {
        final FrameLayout fl_obj = (FrameLayout) findViewById(R.id.fl_obj);
        final FrameLayout fl_spinner = (FrameLayout) findViewById(R.id.fl_spinner);

        //=========================================BOTÃO OBJ PORT OITAVO 1-Bimestre===========================================
        final Button btn1 = (Button) view.findViewById(R.id.btn_obj_I);

        //========== Texto Botão Objetivo Portugues Oitavo ========================================
        DatabaseReference DBR_Obj_I_Bimestre;
        FirebaseDatabase FDB3 = FirebaseDatabase.getInstance();

        DBR_Obj_I_Bimestre = FDB3.getReferenceFromUrl("https://matriz-sobral-194718.firebaseio.com/disciplinas/portugues/8_ano/1_bimestre/objetivo/0/x");
        DBR_Obj_I_Bimestre.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String str_dbr_obj_i_bimestre = dataSnapshot.getValue(String.class);

                //TextView obj_conteudo_i_bimestre = (TextView) findViewById(R.id.txt_obj);

                //obj_conteudo_i_bimestre.setText(str_dbr_obj_i_bimestre);

                btn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        fl_obj.setVisibility(View.VISIBLE);
                        fl_spinner.setVisibility(View.GONE);




                        String obj = str_dbr_obj_i_bimestre;
                        TextView txt_obj = (TextView) findViewById(R.id.txt_objetivo);
                        txt_obj.setText(obj);

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //=========================================BOTÃO OBJ PORT OITAVO 1-Bimestre===========================================





        //=========================================BOTÃO OBJ PORT OITAVO 2-Bimestre===========================================
        final Button btn2 = (Button) view.findViewById(R.id.btn_obj_II);

        //========== Texto Botão Objetivo Portugues Oitavo ========================================
        DatabaseReference DBR_Obj_II_Bimestre;
        FirebaseDatabase FDB4 = FirebaseDatabase.getInstance();

        DBR_Obj_II_Bimestre = FDB4.getReferenceFromUrl("https://matriz-sobral-194718.firebaseio.com/disciplinas/portugues/8_ano/2_bimestre/objetivo/0/x");
        DBR_Obj_II_Bimestre.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String str_dbr_obj_ii_bimestre = dataSnapshot.getValue(String.class);

                //TextView obj_conteudo_i_bimestre = (TextView) findViewById(R.id.txt_obj);

                //obj_conteudo_i_bimestre.setText(str_dbr_obj_i_bimestre);

                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        fl_obj.setVisibility(View.VISIBLE);
                        fl_spinner.setVisibility(View.GONE);




                        String obj = str_dbr_obj_ii_bimestre;
                        TextView txt_obj = (TextView) findViewById(R.id.txt_objetivo);
                        txt_obj.setText(obj);

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //=========================================BOTÃO OBJ PORT OITAVO 2-Bimestre===========================================




        //=========================================BOTÃO OBJ PORT OITAVO 3-Bimestre===========================================
        final Button btn3 = (Button) view.findViewById(R.id.btn_obj_III);

        //========== Texto Botão Objetivo Portugues Oitavo ========================================
        DatabaseReference DBR_Obj_III_Bimestre;
        FirebaseDatabase FDB5 = FirebaseDatabase.getInstance();

        DBR_Obj_III_Bimestre = FDB5.getReferenceFromUrl("https://matriz-sobral-194718.firebaseio.com/disciplinas/portugues/8_ano/3_bimestre/objetivo/0/x");
        DBR_Obj_III_Bimestre.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String str_dbr_obj_iii_bimestre = dataSnapshot.getValue(String.class);

                //TextView obj_conteudo_i_bimestre = (TextView) findViewById(R.id.txt_obj);

                //obj_conteudo_i_bimestre.setText(str_dbr_obj_i_bimestre);

                btn3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        fl_obj.setVisibility(View.VISIBLE);
                        fl_spinner.setVisibility(View.GONE);




                        String obj = str_dbr_obj_iii_bimestre;
                        TextView txt_obj = (TextView) findViewById(R.id.txt_objetivo);
                        txt_obj.setText(obj);

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //=========================================BOTÃO OBJ PORT OITAVO 3-Bimestre===========================================



        //=========================================BOTÃO OBJ PORT OITAVO 4-Bimestre===========================================
        final Button btn4 = (Button) view.findViewById(R.id.btn_obj_IV);

        //========== Texto Botão Objetivo Portugues Oitavo ========================================
        DatabaseReference DBR_Obj_IV_Bimestre;
        FirebaseDatabase FDB6 = FirebaseDatabase.getInstance();

        DBR_Obj_IV_Bimestre = FDB5.getReferenceFromUrl("https://matriz-sobral-194718.firebaseio.com/disciplinas/portugues/8_ano/4_bimestre/objetivo/0/x");
        DBR_Obj_IV_Bimestre.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String str_dbr_obj_iv_bimestre = dataSnapshot.getValue(String.class);

                //TextView obj_conteudo_i_bimestre = (TextView) findViewById(R.id.txt_obj);

                //obj_conteudo_i_bimestre.setText(str_dbr_obj_i_bimestre);

                btn4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        fl_obj.setVisibility(View.VISIBLE);
                        fl_spinner.setVisibility(View.GONE);




                        String obj = str_dbr_obj_iv_bimestre;
                        TextView txt_obj = (TextView) findViewById(R.id.txt_objetivo);
                        txt_obj.setText(obj);

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //=========================================BOTÃO OBJ PORT OITAVO 3-Bimestre===========================================

    }


    public void tab_obj_port_nono_(View view) {
        final FrameLayout fl_obj = (FrameLayout) findViewById(R.id.fl_obj);
        final FrameLayout fl_spinner = (FrameLayout) findViewById(R.id.fl_spinner);

        //=========================================BOTÃO OBJ PORT NONO 1-Bimestre===========================================
        final Button btn1 = (Button) view.findViewById(R.id.btn_obj_I);

        //========== Texto Botão Objetivo Portugues Nono ========================================
        DatabaseReference DBR_Obj_I_Bimestre;
        FirebaseDatabase FDB3 = FirebaseDatabase.getInstance();

        DBR_Obj_I_Bimestre = FDB3.getReferenceFromUrl("https://matriz-sobral-194718.firebaseio.com/disciplinas/portugues/9_ano/1_bimestre/objetivo/0/x");
        DBR_Obj_I_Bimestre.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String str_dbr_obj_i_bimestre = dataSnapshot.getValue(String.class);

                //TextView obj_conteudo_i_bimestre = (TextView) findViewById(R.id.txt_obj);

                //obj_conteudo_i_bimestre.setText(str_dbr_obj_i_bimestre);

                btn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        fl_obj.setVisibility(View.VISIBLE);
                        fl_spinner.setVisibility(View.GONE);




                        String obj = str_dbr_obj_i_bimestre;
                        TextView txt_obj = (TextView) findViewById(R.id.txt_objetivo);
                        txt_obj.setText(obj);

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //=========================================BOTÃO OBJ PORT NONO 1-Bimestre===========================================





        //=========================================BOTÃO OBJ PORT NONO 2-Bimestre===========================================
        final Button btn2 = (Button) view.findViewById(R.id.btn_obj_II);

        //========== Texto Botão Objetivo Portugues Nono ========================================
        DatabaseReference DBR_Obj_II_Bimestre;
        FirebaseDatabase FDB4 = FirebaseDatabase.getInstance();

        DBR_Obj_II_Bimestre = FDB4.getReferenceFromUrl("https://matriz-sobral-194718.firebaseio.com/disciplinas/portugues/9_ano/2_bimestre/objetivo/0/x");
        DBR_Obj_II_Bimestre.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String str_dbr_obj_ii_bimestre = dataSnapshot.getValue(String.class);

                //TextView obj_conteudo_i_bimestre = (TextView) findViewById(R.id.txt_obj);

                //obj_conteudo_i_bimestre.setText(str_dbr_obj_i_bimestre);

                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        fl_obj.setVisibility(View.VISIBLE);
                        fl_spinner.setVisibility(View.GONE);




                        String obj = str_dbr_obj_ii_bimestre;
                        TextView txt_obj = (TextView) findViewById(R.id.txt_objetivo);
                        txt_obj.setText(obj);

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //=========================================BOTÃO OBJ PORT NONO 2-Bimestre===========================================




        //=========================================BOTÃO OBJ PORT NONO 3-Bimestre===========================================
        final Button btn3 = (Button) view.findViewById(R.id.btn_obj_III);

        //========== Texto Botão Objetivo Portugues Nono ========================================
        DatabaseReference DBR_Obj_III_Bimestre;
        FirebaseDatabase FDB5 = FirebaseDatabase.getInstance();

        DBR_Obj_III_Bimestre = FDB5.getReferenceFromUrl("https://matriz-sobral-194718.firebaseio.com/disciplinas/portugues/9_ano/3_bimestre/objetivo/0/x");
        DBR_Obj_III_Bimestre.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String str_dbr_obj_iii_bimestre = dataSnapshot.getValue(String.class);

                //TextView obj_conteudo_i_bimestre = (TextView) findViewById(R.id.txt_obj);

                //obj_conteudo_i_bimestre.setText(str_dbr_obj_i_bimestre);

                btn3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        fl_obj.setVisibility(View.VISIBLE);
                        fl_spinner.setVisibility(View.GONE);




                        String obj = str_dbr_obj_iii_bimestre;
                        TextView txt_obj = (TextView) findViewById(R.id.txt_objetivo);
                        txt_obj.setText(obj);

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //=========================================BOTÃO OBJ PORT NONO 3-Bimestre===========================================



        //=========================================BOTÃO OBJ PORT NONO 4-Bimestre===========================================
        final Button btn4 = (Button) view.findViewById(R.id.btn_obj_IV);

        //========== Texto Botão Objetivo Portugues Nono ========================================
        DatabaseReference DBR_Obj_IV_Bimestre;
        FirebaseDatabase FDB6 = FirebaseDatabase.getInstance();

        DBR_Obj_IV_Bimestre = FDB5.getReferenceFromUrl("https://matriz-sobral-194718.firebaseio.com/disciplinas/portugues/9_ano/4_bimestre/objetivo/0/x");
        DBR_Obj_IV_Bimestre.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String str_dbr_obj_iv_bimestre = dataSnapshot.getValue(String.class);

                //TextView obj_conteudo_i_bimestre = (TextView) findViewById(R.id.txt_obj);

                //obj_conteudo_i_bimestre.setText(str_dbr_obj_i_bimestre);

                btn4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        fl_obj.setVisibility(View.VISIBLE);
                        fl_spinner.setVisibility(View.GONE);




                        String obj = str_dbr_obj_iv_bimestre;
                        TextView txt_obj = (TextView) findViewById(R.id.txt_objetivo);
                        txt_obj.setText(obj);

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //=========================================BOTÃO OBJ PORT NONO 4-Bimestre===========================================

    }

    //===================FIM Tab OBJETIVO PORTUGUES=================================================




    //===================Tab OBJETIVO MATEMATICA=================================================
    public void tab_obj_mat_sexto_(View view) {
        final FrameLayout fl_obj = (FrameLayout) findViewById(R.id.fl_obj);
        final FrameLayout fl_spinner = (FrameLayout) findViewById(R.id.fl_spinner);

        //=========================================BOTÃO OBJ MAT SEXTO 1-Bimestre===========================================
        final Button btn1 = (Button) view.findViewById(R.id.btn_obj_I);

        //========== Texto Botão Objetivo Matematica Sexto ========================================
        DatabaseReference DBR_Obj_I_Bimestre;
        FirebaseDatabase FDB3 = FirebaseDatabase.getInstance();

        DBR_Obj_I_Bimestre = FDB3.getReferenceFromUrl("https://matriz-sobral-194718.firebaseio.com/disciplinas/matematica/6_ano/1_bimestre/objetivo/0/x");
        DBR_Obj_I_Bimestre.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String str_dbr_obj_i_bimestre = dataSnapshot.getValue(String.class);

                //TextView obj_conteudo_i_bimestre = (TextView) findViewById(R.id.txt_obj);

                //obj_conteudo_i_bimestre.setText(str_dbr_obj_i_bimestre);

                btn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        fl_obj.setVisibility(View.VISIBLE);
                        fl_spinner.setVisibility(View.GONE);




                        String obj = str_dbr_obj_i_bimestre;
                        TextView txt_obj = (TextView) findViewById(R.id.txt_objetivo);
                        txt_obj.setText(obj);

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //=========================================BOTÃO OBJ MAT SEXTO 1-Bimestre===========================================





        //=========================================BOTÃO OBJ MAT SEXTO 2-Bimestre===========================================
        final Button btn2 = (Button) view.findViewById(R.id.btn_obj_II);

        //========== Texto Botão Objetivo Matematica Sexto ========================================
        DatabaseReference DBR_Obj_II_Bimestre;
        FirebaseDatabase FDB4 = FirebaseDatabase.getInstance();

        DBR_Obj_II_Bimestre = FDB4.getReferenceFromUrl("https://matriz-sobral-194718.firebaseio.com/disciplinas/matematica/6_ano/2_bimestre/objetivo/0/x");
        DBR_Obj_II_Bimestre.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String str_dbr_obj_ii_bimestre = dataSnapshot.getValue(String.class);

                //TextView obj_conteudo_i_bimestre = (TextView) findViewById(R.id.txt_obj);

                //obj_conteudo_i_bimestre.setText(str_dbr_obj_i_bimestre);

                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        fl_obj.setVisibility(View.VISIBLE);
                        fl_spinner.setVisibility(View.GONE);




                        String obj = str_dbr_obj_ii_bimestre;
                        TextView txt_obj = (TextView) findViewById(R.id.txt_objetivo);
                        txt_obj.setText(obj);

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //=========================================BOTÃO OBJ MAT SEXTO 2-Bimestre===========================================




        //=========================================BOTÃO OBJ MAT SEXTO 3-Bimestre===========================================
        final Button btn3 = (Button) view.findViewById(R.id.btn_obj_III);

        //========== Texto Botão Objetivo Matematica Sexto ========================================
        DatabaseReference DBR_Obj_III_Bimestre;
        FirebaseDatabase FDB5 = FirebaseDatabase.getInstance();

        DBR_Obj_III_Bimestre = FDB5.getReferenceFromUrl("https://matriz-sobral-194718.firebaseio.com/disciplinas/matematica/6_ano/3_bimestre/objetivo/0/x");
        DBR_Obj_III_Bimestre.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String str_dbr_obj_iii_bimestre = dataSnapshot.getValue(String.class);

                //TextView obj_conteudo_i_bimestre = (TextView) findViewById(R.id.txt_obj);

                //obj_conteudo_i_bimestre.setText(str_dbr_obj_i_bimestre);

                btn3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        fl_obj.setVisibility(View.VISIBLE);
                        fl_spinner.setVisibility(View.GONE);




                        String obj = str_dbr_obj_iii_bimestre;
                        TextView txt_obj = (TextView) findViewById(R.id.txt_objetivo);
                        txt_obj.setText(obj);

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //=========================================BOTÃO OBJ MAT SEXTO 3-Bimestre===========================================



        //=========================================BOTÃO OBJ MAT SEXTO 4-Bimestre===========================================
        final Button btn4 = (Button) view.findViewById(R.id.btn_obj_IV);

        //========== Texto Botão Objetivo Matematica Sexto ========================================
        DatabaseReference DBR_Obj_IV_Bimestre;
        FirebaseDatabase FDB6 = FirebaseDatabase.getInstance();

        DBR_Obj_IV_Bimestre = FDB5.getReferenceFromUrl("https://matriz-sobral-194718.firebaseio.com/disciplinas/matematica/6_ano/4_bimestre/objetivo/0/x");
        DBR_Obj_IV_Bimestre.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String str_dbr_obj_iv_bimestre = dataSnapshot.getValue(String.class);

                //TextView obj_conteudo_i_bimestre = (TextView) findViewById(R.id.txt_obj);

                //obj_conteudo_i_bimestre.setText(str_dbr_obj_i_bimestre);

                btn4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        fl_obj.setVisibility(View.VISIBLE);
                        fl_spinner.setVisibility(View.GONE);




                        String obj = str_dbr_obj_iv_bimestre;
                        TextView txt_obj = (TextView) findViewById(R.id.txt_objetivo);
                        txt_obj.setText(obj);

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //=========================================BOTÃO OBJ MAT SEXTO 3-Bimestre===========================================

    }


    public void tab_obj_mat_setimo_(View view) {
        final FrameLayout fl_obj = (FrameLayout) findViewById(R.id.fl_obj);
        final FrameLayout fl_spinner = (FrameLayout) findViewById(R.id.fl_spinner);

        //=========================================BOTÃO OBJ MAT SETIMO 1-Bimestre===========================================
        final Button btn1 = (Button) view.findViewById(R.id.btn_obj_I);

        //========== Texto Botão Objetivo Matematica Setimo ========================================
        DatabaseReference DBR_Obj_I_Bimestre;
        FirebaseDatabase FDB3 = FirebaseDatabase.getInstance();

        DBR_Obj_I_Bimestre = FDB3.getReferenceFromUrl("https://matriz-sobral-194718.firebaseio.com/disciplinas/matematica/7_ano/1_bimestre/objetivo/0/x");
        DBR_Obj_I_Bimestre.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String str_dbr_obj_i_bimestre = dataSnapshot.getValue(String.class);

                //TextView obj_conteudo_i_bimestre = (TextView) findViewById(R.id.txt_obj);

                //obj_conteudo_i_bimestre.setText(str_dbr_obj_i_bimestre);

                btn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        fl_obj.setVisibility(View.VISIBLE);
                        fl_spinner.setVisibility(View.GONE);




                        String obj = str_dbr_obj_i_bimestre;
                        TextView txt_obj = (TextView) findViewById(R.id.txt_objetivo);
                        txt_obj.setText(obj);

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //=========================================BOTÃO OBJ MAT SETIMO 1-Bimestre===========================================





        //=========================================BOTÃO OBJ MAT SETIMO 2-Bimestre===========================================
        final Button btn2 = (Button) view.findViewById(R.id.btn_obj_II);

        //========== Texto Botão Objetivo Matematica Setimo ========================================
        DatabaseReference DBR_Obj_II_Bimestre;
        FirebaseDatabase FDB4 = FirebaseDatabase.getInstance();

        DBR_Obj_II_Bimestre = FDB4.getReferenceFromUrl("https://matriz-sobral-194718.firebaseio.com/disciplinas/matematica/7_ano/2_bimestre/objetivo/0/x");
        DBR_Obj_II_Bimestre.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String str_dbr_obj_ii_bimestre = dataSnapshot.getValue(String.class);

                //TextView obj_conteudo_i_bimestre = (TextView) findViewById(R.id.txt_obj);

                //obj_conteudo_i_bimestre.setText(str_dbr_obj_i_bimestre);

                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        fl_obj.setVisibility(View.VISIBLE);
                        fl_spinner.setVisibility(View.GONE);




                        String obj = str_dbr_obj_ii_bimestre;
                        TextView txt_obj = (TextView) findViewById(R.id.txt_objetivo);
                        txt_obj.setText(obj);

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //=========================================BOTÃO OBJ MAT SETIMO 2-Bimestre===========================================




        //=========================================BOTÃO OBJ MAT SETIMO 3-Bimestre===========================================
        final Button btn3 = (Button) view.findViewById(R.id.btn_obj_III);

        //========== Texto Botão Objetivo Matematica Setimo ========================================
        DatabaseReference DBR_Obj_III_Bimestre;
        FirebaseDatabase FDB5 = FirebaseDatabase.getInstance();

        DBR_Obj_III_Bimestre = FDB5.getReferenceFromUrl("https://matriz-sobral-194718.firebaseio.com/disciplinas/matematica/7_ano/3_bimestre/objetivo/0/x");
        DBR_Obj_III_Bimestre.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String str_dbr_obj_iii_bimestre = dataSnapshot.getValue(String.class);

                //TextView obj_conteudo_i_bimestre = (TextView) findViewById(R.id.txt_obj);

                //obj_conteudo_i_bimestre.setText(str_dbr_obj_i_bimestre);

                btn3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        fl_obj.setVisibility(View.VISIBLE);
                        fl_spinner.setVisibility(View.GONE);




                        String obj = str_dbr_obj_iii_bimestre;
                        TextView txt_obj = (TextView) findViewById(R.id.txt_objetivo);
                        txt_obj.setText(obj);

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //=========================================BOTÃO OBJ MAT SETIMO 3-Bimestre===========================================



        //=========================================BOTÃO OBJ MAT SETIMO 4-Bimestre===========================================
        final Button btn4 = (Button) view.findViewById(R.id.btn_obj_IV);

        //========== Texto Botão Objetivo Matematica Setimo ========================================
        DatabaseReference DBR_Obj_IV_Bimestre;
        FirebaseDatabase FDB6 = FirebaseDatabase.getInstance();

        DBR_Obj_IV_Bimestre = FDB5.getReferenceFromUrl("https://matriz-sobral-194718.firebaseio.com/disciplinas/matematica/7_ano/4_bimestre/objetivo/0/x");
        DBR_Obj_IV_Bimestre.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String str_dbr_obj_iv_bimestre = dataSnapshot.getValue(String.class);

                //TextView obj_conteudo_i_bimestre = (TextView) findViewById(R.id.txt_obj);

                //obj_conteudo_i_bimestre.setText(str_dbr_obj_i_bimestre);

                btn4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        fl_obj.setVisibility(View.VISIBLE);
                        fl_spinner.setVisibility(View.GONE);




                        String obj = str_dbr_obj_iv_bimestre;
                        TextView txt_obj = (TextView) findViewById(R.id.txt_objetivo);
                        txt_obj.setText(obj);

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //=========================================BOTÃO OBJ MAT SETIMO 4-Bimestre===========================================

    }


    public void tab_obj_mat_oitavo_(View view) {
        final FrameLayout fl_obj = (FrameLayout) findViewById(R.id.fl_obj);
        final FrameLayout fl_spinner = (FrameLayout) findViewById(R.id.fl_spinner);

        //=========================================BOTÃO OBJ MAT OITAVO 1-Bimestre===========================================
        final Button btn1 = (Button) view.findViewById(R.id.btn_obj_I);

        //========== Texto Botão Objetivo Matematica Oitavo ========================================
        DatabaseReference DBR_Obj_I_Bimestre;
        FirebaseDatabase FDB3 = FirebaseDatabase.getInstance();

        DBR_Obj_I_Bimestre = FDB3.getReferenceFromUrl("https://matriz-sobral-194718.firebaseio.com/disciplinas/matematica/8_ano/1_bimestre/objetivo/0/x");
        DBR_Obj_I_Bimestre.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String str_dbr_obj_i_bimestre = dataSnapshot.getValue(String.class);

                //TextView obj_conteudo_i_bimestre = (TextView) findViewById(R.id.txt_obj);

                //obj_conteudo_i_bimestre.setText(str_dbr_obj_i_bimestre);

                btn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        fl_obj.setVisibility(View.VISIBLE);
                        fl_spinner.setVisibility(View.GONE);




                        String obj = str_dbr_obj_i_bimestre;
                        TextView txt_obj = (TextView) findViewById(R.id.txt_objetivo);
                        txt_obj.setText(obj);

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //=========================================BOTÃO OBJ MAT OITAVO 1-Bimestre===========================================





        //=========================================BOTÃO OBJ MAT OITAVO 2-Bimestre===========================================
        final Button btn2 = (Button) view.findViewById(R.id.btn_obj_II);

        //========== Texto Botão Objetivo Matematica Oitavo ========================================
        DatabaseReference DBR_Obj_II_Bimestre;
        FirebaseDatabase FDB4 = FirebaseDatabase.getInstance();

        DBR_Obj_II_Bimestre = FDB4.getReferenceFromUrl("https://matriz-sobral-194718.firebaseio.com/disciplinas/matematica/8_ano/2_bimestre/objetivo/0/x");
        DBR_Obj_II_Bimestre.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String str_dbr_obj_ii_bimestre = dataSnapshot.getValue(String.class);

                //TextView obj_conteudo_i_bimestre = (TextView) findViewById(R.id.txt_obj);

                //obj_conteudo_i_bimestre.setText(str_dbr_obj_i_bimestre);

                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        fl_obj.setVisibility(View.VISIBLE);
                        fl_spinner.setVisibility(View.GONE);




                        String obj = str_dbr_obj_ii_bimestre;
                        TextView txt_obj = (TextView) findViewById(R.id.txt_objetivo);
                        txt_obj.setText(obj);

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //=========================================BOTÃO OBJ MAT OITAVO 2-Bimestre===========================================




        //=========================================BOTÃO OBJ MAT OITAVO 3-Bimestre===========================================
        final Button btn3 = (Button) view.findViewById(R.id.btn_obj_III);

        //========== Texto Botão Objetivo Matematica Oitavo ========================================
        DatabaseReference DBR_Obj_III_Bimestre;
        FirebaseDatabase FDB5 = FirebaseDatabase.getInstance();

        DBR_Obj_III_Bimestre = FDB5.getReferenceFromUrl("https://matriz-sobral-194718.firebaseio.com/disciplinas/matematica/8_ano/3_bimestre/objetivo/0/x");
        DBR_Obj_III_Bimestre.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String str_dbr_obj_iii_bimestre = dataSnapshot.getValue(String.class);

                //TextView obj_conteudo_i_bimestre = (TextView) findViewById(R.id.txt_obj);

                //obj_conteudo_i_bimestre.setText(str_dbr_obj_i_bimestre);

                btn3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        fl_obj.setVisibility(View.VISIBLE);
                        fl_spinner.setVisibility(View.GONE);




                        String obj = str_dbr_obj_iii_bimestre;
                        TextView txt_obj = (TextView) findViewById(R.id.txt_objetivo);
                        txt_obj.setText(obj);

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //=========================================BOTÃO OBJ MAT OITAVO 3-Bimestre===========================================



        //=========================================BOTÃO OBJ MAT OITAVO 4-Bimestre===========================================
        final Button btn4 = (Button) view.findViewById(R.id.btn_obj_IV);

        //========== Texto Botão Objetivo Matematica Oitavo ========================================
        DatabaseReference DBR_Obj_IV_Bimestre;
        FirebaseDatabase FDB6 = FirebaseDatabase.getInstance();

        DBR_Obj_IV_Bimestre = FDB5.getReferenceFromUrl("https://matriz-sobral-194718.firebaseio.com/disciplinas/matematica/8_ano/4_bimestre/objetivo/0/x");
        DBR_Obj_IV_Bimestre.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String str_dbr_obj_iv_bimestre = dataSnapshot.getValue(String.class);

                //TextView obj_conteudo_i_bimestre = (TextView) findViewById(R.id.txt_obj);

                //obj_conteudo_i_bimestre.setText(str_dbr_obj_i_bimestre);

                btn4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        fl_obj.setVisibility(View.VISIBLE);
                        fl_spinner.setVisibility(View.GONE);




                        String obj = str_dbr_obj_iv_bimestre;
                        TextView txt_obj = (TextView) findViewById(R.id.txt_objetivo);
                        txt_obj.setText(obj);

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //=========================================BOTÃO OBJ MAT OITAVO 3-Bimestre===========================================

    }


    public void tab_obj_mat_nono_(View view) {
        final FrameLayout fl_obj = (FrameLayout) findViewById(R.id.fl_obj);
        final FrameLayout fl_spinner = (FrameLayout) findViewById(R.id.fl_spinner);

        //=========================================BOTÃO OBJ MAT NONO 1-Bimestre===========================================
        final Button btn1 = (Button) view.findViewById(R.id.btn_obj_I);

        //========== Texto Botão Objetivo Matematica Nono ========================================
        DatabaseReference DBR_Obj_I_Bimestre;
        FirebaseDatabase FDB3 = FirebaseDatabase.getInstance();

        DBR_Obj_I_Bimestre = FDB3.getReferenceFromUrl("https://matriz-sobral-194718.firebaseio.com/disciplinas/matematica/9_ano/1_bimestre/objetivo/0/x");
        DBR_Obj_I_Bimestre.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String str_dbr_obj_i_bimestre = dataSnapshot.getValue(String.class);

                //TextView obj_conteudo_i_bimestre = (TextView) findViewById(R.id.txt_obj);

                //obj_conteudo_i_bimestre.setText(str_dbr_obj_i_bimestre);

                btn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        fl_obj.setVisibility(View.VISIBLE);
                        fl_spinner.setVisibility(View.GONE);




                        String obj = str_dbr_obj_i_bimestre;
                        TextView txt_obj = (TextView) findViewById(R.id.txt_objetivo);
                        txt_obj.setText(obj);

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //=========================================BOTÃO OBJ MAT NONO 1-Bimestre===========================================





        //=========================================BOTÃO OBJ MAT NONO 2-Bimestre===========================================
        final Button btn2 = (Button) view.findViewById(R.id.btn_obj_II);

        //========== Texto Botão Objetivo Matematica Nono ========================================
        DatabaseReference DBR_Obj_II_Bimestre;
        FirebaseDatabase FDB4 = FirebaseDatabase.getInstance();

        DBR_Obj_II_Bimestre = FDB4.getReferenceFromUrl("https://matriz-sobral-194718.firebaseio.com/disciplinas/matematica/9_ano/2_bimestre/objetivo/0/x");
        DBR_Obj_II_Bimestre.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String str_dbr_obj_ii_bimestre = dataSnapshot.getValue(String.class);

                //TextView obj_conteudo_i_bimestre = (TextView) findViewById(R.id.txt_obj);

                //obj_conteudo_i_bimestre.setText(str_dbr_obj_i_bimestre);

                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        fl_obj.setVisibility(View.VISIBLE);
                        fl_spinner.setVisibility(View.GONE);




                        String obj = str_dbr_obj_ii_bimestre;
                        TextView txt_obj = (TextView) findViewById(R.id.txt_objetivo);
                        txt_obj.setText(obj);

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //=========================================BOTÃO OBJ MAT NONO 2-Bimestre===========================================




        //=========================================BOTÃO OBJ MAT NONO 3-Bimestre===========================================
        final Button btn3 = (Button) view.findViewById(R.id.btn_obj_III);

        //========== Texto Botão Objetivo Matematica Nono ========================================
        DatabaseReference DBR_Obj_III_Bimestre;
        FirebaseDatabase FDB5 = FirebaseDatabase.getInstance();

        DBR_Obj_III_Bimestre = FDB5.getReferenceFromUrl("https://matriz-sobral-194718.firebaseio.com/disciplinas/matematica/9_ano/3_bimestre/objetivo/0/x");
        DBR_Obj_III_Bimestre.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String str_dbr_obj_iii_bimestre = dataSnapshot.getValue(String.class);

                //TextView obj_conteudo_i_bimestre = (TextView) findViewById(R.id.txt_obj);

                //obj_conteudo_i_bimestre.setText(str_dbr_obj_i_bimestre);

                btn3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        fl_obj.setVisibility(View.VISIBLE);
                        fl_spinner.setVisibility(View.GONE);




                        String obj = str_dbr_obj_iii_bimestre;
                        TextView txt_obj = (TextView) findViewById(R.id.txt_objetivo);
                        txt_obj.setText(obj);

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //=========================================BOTÃO OBJ MAT NONO 3-Bimestre===========================================



        //=========================================BOTÃO OBJ MAT NONO 4-Bimestre===========================================
        final Button btn4 = (Button) view.findViewById(R.id.btn_obj_IV);

        //========== Texto Botão Objetivo Matematica Nono ========================================
        DatabaseReference DBR_Obj_IV_Bimestre;
        FirebaseDatabase FDB6 = FirebaseDatabase.getInstance();

        DBR_Obj_IV_Bimestre = FDB5.getReferenceFromUrl("https://matriz-sobral-194718.firebaseio.com/disciplinas/matematica/9_ano/4_bimestre/objetivo/0/x");
        DBR_Obj_IV_Bimestre.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String str_dbr_obj_iv_bimestre = dataSnapshot.getValue(String.class);

                //TextView obj_conteudo_i_bimestre = (TextView) findViewById(R.id.txt_obj);

                //obj_conteudo_i_bimestre.setText(str_dbr_obj_i_bimestre);

                btn4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        fl_obj.setVisibility(View.VISIBLE);
                        fl_spinner.setVisibility(View.GONE);




                        String obj = str_dbr_obj_iv_bimestre;
                        TextView txt_obj = (TextView) findViewById(R.id.txt_objetivo);
                        txt_obj.setText(obj);

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //=========================================BOTÃO OBJ MAT NONO 4-Bimestre===========================================

    }

    //===================FIM Tab OBJETIVO MATEMATICA=================================================


    //=============================FUNÇÃO DE LEITURA DA DATA DO SISTEMA============================
    public void DataSistema(){

        //Leitor de data do sistema
        Calendar now = Calendar.getInstance();
        Dia_sistema = now.get(Calendar.DAY_OF_MONTH);
        Mes_sistema = now.get(Calendar.MONTH); // Note: zero based!
        Mes_sistema++;

       //Toast.makeText(Main_activity.this, "Dia: "+ Dia_sistema + " Mês: "+ Mes_sistema,Toast.LENGTH_SHORT).show();
    }
    //==============================================================================================

    private void setUserData(FirebaseUser user){

        nomeID.setText(user.getDisplayName());

        Glide.with(this).load(user.getPhotoUrl()).asBitmap().centerCrop().into(new BitmapImageViewTarget(photoID) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                photoID.setImageDrawable(circularBitmapDrawable);
            }
        });

    }

    protected void onStart(){
        super.onStart();

        FirebaseUser currentUser = fbAuth.getCurrentUser();
        fbAuth.addAuthStateListener(fbAuthListener);
        
        if (currentUser == null)
        {
            SendUserToLoginActivity();
        }
        else
        {
            CheckUserExistence();
        }
        verificaconexao();
    }

    private void CheckUserExistence()
    {

        UsersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                SaveAccountSetupInformation();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //Salvando dados do usuário no Firebase
    private void SaveAccountSetupInformation()
    {
        //Captura dos dados do usuário
        final String user_id = fbAuth.getCurrentUser().getUid();
        final String username = fbAuth.getCurrentUser().getDisplayName();
        final String email = fbAuth.getCurrentUser().getEmail();

            HashMap userMap = new HashMap();
            userMap.put("nome", username);
            userMap.put("uid", user_id);
            userMap.put("email", email);

            UsersRef.child(user_id).child("dados").updateChildren(userMap).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task)
                {
                    if(task.isSuccessful())
                    {
                        //Toast.makeText(Main_activity.this, "Dados salvos", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        //String message =  task.getException().getMessage();
                        //Toast.makeText(Main_activity.this, "Dados não salvos " + message, Toast.LENGTH_SHORT).show();
                    }
                }
            });

    }


    private void SendUserToLoginActivity() 
    {
        //Intent loginIntent = new Intent(Main_activity.this, Login_activity.class);
        Intent IntroIntent = new Intent(Main_activity.this, IntroActivity.class);
        IntroIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(IntroIntent);
        finish();
    }

    protected  void onStop(){
        super.onStop();

        if(fbAuthListener != null){
            fbAuth.removeAuthStateListener(fbAuthListener);
        }
    }

    //=============================COLOCAR SOMBRA NO BLOCO DE BIMESTRE==============================

    public void SombraBimestre(View view) {

        FrameLayout fl_1 = (FrameLayout) view.findViewById(R.id.fl_1);
        FrameLayout fl_2 = (FrameLayout) view.findViewById(R.id.fl_2);
        FrameLayout fl_3 = (FrameLayout) view.findViewById(R.id.fl_3);
        FrameLayout fl_4 = (FrameLayout) view.findViewById(R.id.fl_4);

        //====================================I BIMESTRE========================================

        if (Mes_sistema == ID_salvo_mes_termino_I) {

            if (Dia_sistema > ID_salvo_dia_termino_I) {

                // Toast.makeText(Main_activity.this, "Sombra Bim_I ", Toast.LENGTH_SHORT).show();

                fl_1.setVisibility(View.VISIBLE);

            } else
                fl_1.setVisibility(View.GONE);
        } else if (Mes_sistema > ID_salvo_mes_termino_I) {

            fl_1.setVisibility(View.VISIBLE);

        } else if (Mes_sistema < ID_salvo_mes_termino_I) {

            fl_1.setVisibility(View.GONE);
        }
        //======================================================================================

        //===================================II BIMESTRE========================================
        if (Mes_sistema == ID_salvo_mes_termino_II) {

            if (Dia_sistema > ID_salvo_dia_termino_II) {

                //Toast.makeText(Main_activity.this, "Sombra Bim_I ", Toast.LENGTH_SHORT).show();

                fl_2.setVisibility(View.VISIBLE);

            } else
                fl_2.setVisibility(View.GONE);
        } else if (Mes_sistema > ID_salvo_mes_termino_II) {

            fl_2.setVisibility(View.VISIBLE);

        } else if (Mes_sistema < ID_salvo_mes_termino_II) {

            fl_2.setVisibility(View.GONE);
        }
        //======================================================================================

        //==================================III BIMESTRE========================================
        if (Mes_sistema == ID_salvo_mes_termino_III) {

            if (Dia_sistema > ID_salvo_dia_termino_III) {

                //Toast.makeText(Main_activity.this, "Sombra Bim_I ", Toast.LENGTH_SHORT).show();

                fl_3.setVisibility(View.VISIBLE);

            } else
                fl_3.setVisibility(View.GONE);
        } else if (Mes_sistema > ID_salvo_mes_termino_III) {

            fl_3.setVisibility(View.VISIBLE);

        } else if (Mes_sistema < ID_salvo_mes_termino_III) {

            fl_3.setVisibility(View.GONE);
        }
        //======================================================================================

        //===================================IV BIMESTRE========================================
        if (Mes_sistema == ID_salvo_mes_termino_IV) {

            if (Dia_sistema > ID_salvo_dia_termino_IV) {

                //Toast.makeText(Main_activity.this, "Sombra Bim_I ", Toast.LENGTH_SHORT).show();

                fl_4.setVisibility(View.VISIBLE);

            } else
                fl_4.setVisibility(View.GONE);
        } else if (Mes_sistema > ID_salvo_mes_termino_IV) {

            fl_4.setVisibility(View.VISIBLE);

        } else if (Mes_sistema < ID_salvo_mes_termino_IV) {

            fl_4.setVisibility(View.GONE);
        }
        //======================================================================================
    }
    //==============================================================================================

    private void startActivity(AdapterView.OnItemSelectedListener onItemSelectedListener) {

        Intent it = new Intent(this, Main_activity.class);
        startActivity(it);

    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem ( int position){

            if (ID == 0) {//Portugues

                switch (position) {

                    case 0:
                        return new Tab_portugues_sexto_();

                    case 1:

                        return new Tab_portugues_setimo_();


                    case 2:

                        return new Tab_portugues_oitavo_();

                    case 3:

                        return new Tab_portugues_nono_();
                }
            }
            else if (ID == 1) {//Matematica

                switch (position) {

                    case 0:

                        return new Tab_matematica_sexto_();

                    case 1:

                        return new Tab_matematica_setimo_();


                    case 2:

                        return new Tab_matematica_oitavo_();

                    case 3:

                        return new Tab_matematica_nono_();
                }
            }
            return null;



        }

        @Override
        public int getCount() {
            // Show 4 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "6º ANO";
                case 1:
                    return "7º ANO";
                case 2:
                    return "8º ANO";
                case 3:
                    return "9º ANO";
            }
            return null;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id == R.id.contato){
            FrameLayout fl = (FrameLayout) findViewById(R.id.fl_contato);
            FrameLayout fls = (FrameLayout) findViewById(R.id.fl_spinner);
            fl.setVisibility(View.VISIBLE);
            fls.setVisibility(View.GONE);
        }

        if (id == R.id.matrizpdf){
            Intent it = new Intent(this, Pdf_view_activity.class);
            startActivity(it);
        }

        if (id == R.id.configuracoes) {
            Intent it = new Intent(this, Config_activity.class);
            startActivity(it);
            // chamar a activity de configurações
        } else if (id == R.id.compartilhar) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Compartilhe a Matriz Sobral com seus amigos!");
            shareIntent.putExtra(Intent.EXTRA_TEXT,"Em breve, na play store, Matriz curricular de Sobral - CE!"
            );
            startActivity(Intent.createChooser(shareIntent, "Compartilhar via"));
            //Abrir opçoes de compartilhar
        }   if (id == R.id.matematica) {
            FrameLayout fl = (FrameLayout) findViewById(R.id.fl_matematica);
            FrameLayout fls = (FrameLayout) findViewById(R.id.fl_spinner);
            fl.setVisibility(View.VISIBLE);
            fls.setVisibility(View.GONE);

        } if (id == R.id.portugues) {
            FrameLayout fl = (FrameLayout) findViewById(R.id.fl_portugues);
            FrameLayout fls = (FrameLayout) findViewById(R.id.fl_spinner);
            fl.setVisibility(View.VISIBLE);
            fls.setVisibility(View.GONE);

        } if (id == R.id.docs){
            PackageManager packageManager = getPackageManager();
            String packageName = "com.google.android.apps.docs.editors.docs";
            Intent intent = packageManager.getLaunchIntentForPackage(packageName);

            String url = "https://play.google.com/store/apps/details?id=com.google.android.apps.docs.editors.docs";
            Intent googleplay = new Intent(Intent.ACTION_VIEW);
            googleplay.setData(Uri.parse(url));

            if(null != intent){
                startActivity(intent);
            }
            else if (null == intent){
                Toast.makeText(this, "Redirecionando para play store...", Toast.LENGTH_SHORT).show();
                startActivity(googleplay);
            }
        } if (id == R.id.calendario){
            PackageManager packageManager = getPackageManager();
            String packageName = "com.google.android.calendar";
            Intent intent = packageManager.getLaunchIntentForPackage(packageName);

            String url = "https://play.google.com/store/apps/details?id=com.google.android.calendar";
            Intent googleplay = new Intent(Intent.ACTION_VIEW);
            googleplay.setData(Uri.parse(url));

            if(null != intent){
                startActivity(intent);
            }
            else if (null == intent){
                Toast.makeText(this, "Redirecionando para play store...", Toast.LENGTH_SHORT).show();
                startActivity(googleplay);
            }
        } if (id == R.id.classroom){
            PackageManager packageManager = getPackageManager();
            String packageName = "com.google.android.apps.classroom";
            Intent intent = packageManager.getLaunchIntentForPackage(packageName);

            String url = "https://play.google.com/store/apps/details?id=com.google.android.apps.classroom";
            Intent googleplay = new Intent(Intent.ACTION_VIEW);
            googleplay.setData(Uri.parse(url));

            if(null != intent){
                startActivity(intent);
            }
            else if (null == intent){
                Toast.makeText(this, "Redirecionando para play store...", Toast.LENGTH_SHORT).show();
                startActivity(googleplay);
            }
        } if (id == R.id.hangouts){
            PackageManager packageManager = getPackageManager();
            String packageName = "com.google.android.apps.meetings";
            Intent intent = packageManager.getLaunchIntentForPackage(packageName);

            String url = "https://play.google.com/store/apps/details?id=com.google.android.apps.meetings";
            Intent googleplay = new Intent(Intent.ACTION_VIEW);
            googleplay.setData(Uri.parse(url));

            if(null != intent){
                startActivity(intent);
            }
            else if (null == intent){
                Toast.makeText(this, "Redirecionando para play store...", Toast.LENGTH_SHORT).show();
                startActivity(googleplay);
            }
        } if (id == R.id.youtube){
            PackageManager packageManager = getPackageManager();
            String packageName = "com.google.android.youtube";
            Intent intent = packageManager.getLaunchIntentForPackage(packageName);

            String url = "https://play.google.com/store/apps/details?id=com.google.android.youtube";
            Intent googleplay = new Intent(Intent.ACTION_VIEW);
            googleplay.setData(Uri.parse(url));

            if(null != intent){
                startActivity(intent);
            }
            else if (null == intent){
                Toast.makeText(this, "Redirecionando para play store...", Toast.LENGTH_SHORT).show();
                startActivity(googleplay);
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
