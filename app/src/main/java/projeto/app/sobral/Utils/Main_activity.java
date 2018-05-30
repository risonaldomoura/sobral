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
import android.widget.LinearLayout;
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
import projeto.app.sobral.Matematica.Tab_matematica_nono_;
import projeto.app.sobral.Matematica.Tab_matematica_oitavo_;
import projeto.app.sobral.Matematica.Tab_matematica_setimo_;
import projeto.app.sobral.Matematica.Tab_matematica_sexto_;
import projeto.app.sobral.Portugues.Tab_portugues_nono_;
import projeto.app.sobral.Portugues.Tab_portugues_oitavo_;
import projeto.app.sobral.Portugues.Tab_portugues_setimo_;

import projeto.app.sobral.Portugues.Tab_portugues_sexto_;


//============================================ Imports Google Script API ==========================================================================================

import android.Manifest;
import android.accounts.AccountManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.script.model.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import projeto.app.sobral.R;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

//===================================================== FIM: Imports Google Script API ====================================================================================



public class Main_activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, EasyPermissions.PermissionCallbacks{



    //===================================== Parametros Google Script API =============================================================================
    public String titulo;
    public String text1;

    GoogleAccountCredential mCredential;
    private TextView mOutputText;
    ProgressDialog mProgress;

    static final int REQUEST_ACCOUNT_PICKER = 1000;
    static final int REQUEST_AUTHORIZATION = 1001;
    static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;
    static final int REQUEST_PERMISSION_GET_ACCOUNTS = 1003;

    private static final String PREF_ACCOUNT_NAME = "accountName";
    //private static final String[] SCOPES = { "https://www.googleapis.com/auth/script.projects" };
    //private static final String[] SCOPES = { "https://www.googleapis.com/auth/documents"};
    private static final String[] SCOPES = {"https://www.googleapis.com/auth/calendar","https://www.googleapis.com/auth/documents","https://www.googleapis.com/auth/drive"};
    //===================================== FIM: Parametros Google Script API ========================================================================


    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    //Autentição com o Google
    private GoogleApiClient googleApiClient;
    private ImageView photoID;
    private TextView nomeID;
    //Firebase
    private FirebaseAuth fbAuth;
    private FirebaseAuth.AuthStateListener fbAuthListener;

    private DatabaseReference UsersRef;

    private Spinner spn_disciplina;
    public static int IDAtual;
    public static int ID;
    public static int IDAnterior;

    public int execucao = 1;
    public int configurar;

    public int dia_atual;
    public int mes_atual;

    //Dia e Mês carregado do firebase
    public int dia_salvo_inicio_1, mes_salvo_inicio_1, dia_salvo_termino_1, mes_salvo_termino_1,
            dia_salvo_inicio_2, mes_salvo_inicio_2, dia_salvo_termino_2, mes_salvo_termino_2,
            dia_salvo_inicio_3, mes_salvo_inicio_3, dia_salvo_termino_3, mes_salvo_termino_3,
            dia_salvo_inicio_4, mes_salvo_inicio_4, dia_salvo_termino_4, mes_salvo_termino_4;

    private DatabaseReference   UserData_inicio_1, UserData_termino_1,
            UserData_inicio_2, UserData_termino_2,
            UserData_inicio_3, UserData_termino_3,
            UserData_inicio_4, UserData_termino_4;

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


        //================================Google Script API: Iniciar Credenciais=========================================
        // Initialize credentials and service object.
        mCredential = GoogleAccountCredential.usingOAuth2(
                this, Arrays.asList(SCOPES))
                .setBackOff(new ExponentialBackOff());

        //================================Fim: Google Script API: Iniciar Credenciais=========================================




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

        DBR_Obj_IV_Bimestre = FDB6.getReferenceFromUrl("https://matriz-sobral-194718.firebaseio.com/disciplinas/portugues/9_ano/4_bimestre/objetivo/0/x");
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

        DBR_Obj_IV_Bimestre = FDB6.getReferenceFromUrl("https://matriz-sobral-194718.firebaseio.com/disciplinas/matematica/6_ano/4_bimestre/objetivo/0/x");
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
        dia_atual = now.get(Calendar.DAY_OF_MONTH);
        mes_atual = now.get(Calendar.MONTH); // Note: zero based!
        mes_atual++;

        //Toast.makeText(Main_activity.this, "Dia: "+ dia_atual + " Mês: "+ mes_atual,Toast.LENGTH_SHORT).show();
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
            DataSistema();
            carrega_datas_firebase();
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

        //Carrega as datas salvas pelos métodos que carregam as datas do firebase
        SharedPreferences sharedPref = getSharedPreferences("save_datas",MODE_PRIVATE);
        dia_salvo_inicio_1 = sharedPref.getInt("dia_salvo_inicio_1",0);
        mes_salvo_inicio_1 = sharedPref.getInt("mes_salvo_inicio_1",0);
        dia_salvo_termino_1 = sharedPref.getInt("dia_salvo_termino_1",0);
        mes_salvo_termino_1 = sharedPref.getInt("mes_salvo_termino_1",0);

        dia_salvo_inicio_2 = sharedPref.getInt("dia_salvo_inicio_2",0);
        mes_salvo_inicio_2 = sharedPref.getInt("mes_salvo_inicio_2",0);
        dia_salvo_termino_2 = sharedPref.getInt("dia_salvo_termino_2",0);
        mes_salvo_termino_2 = sharedPref.getInt("mes_salvo_termino_2",0);

        dia_salvo_inicio_3 = sharedPref.getInt("dia_salvo_inicio_3",0);
        mes_salvo_inicio_3 = sharedPref.getInt("mes_salvo_inicio_3",0);
        dia_salvo_termino_3 = sharedPref.getInt("dia_salvo_termino_3",0);
        mes_salvo_termino_3 = sharedPref.getInt("mes_salvo_termino_3",0);

        dia_salvo_inicio_4 = sharedPref.getInt("dia_salvo_inicio_4",0);
        mes_salvo_inicio_4 = sharedPref.getInt("mes_salvo_inicio_4",0);
        dia_salvo_termino_4 = sharedPref.getInt("dia_salvo_termino_4",0);
        mes_salvo_termino_4 = sharedPref.getInt("mes_salvo_termino_4",0);

        LinearLayout balao_inicio_1 = (LinearLayout) view.findViewById(R.id.balao_inicio_1);
        LinearLayout balao_inicio_2 = (LinearLayout) view.findViewById(R.id.balao_inicio_2);
        LinearLayout balao_inicio_3 = (LinearLayout) view.findViewById(R.id.balao_inicio_3);
        LinearLayout balao_inicio_4 = (LinearLayout) view.findViewById(R.id.balao_inicio_4);

        LinearLayout balao_termino_1 = (LinearLayout) view.findViewById(R.id.balao_termino_1);
        LinearLayout balao_termino_2 = (LinearLayout) view.findViewById(R.id.balao_termino_2);
        LinearLayout balao_termino_3 = (LinearLayout) view.findViewById(R.id.balao_termino_3);
        LinearLayout balao_termino_4 = (LinearLayout) view.findViewById(R.id.balao_termino_4);

        LinearLayout barra_balao_1 = (LinearLayout) view.findViewById(R.id.barra_balao_1);
        LinearLayout barra_balao_2 = (LinearLayout) view.findViewById(R.id.barra_balao_2);
        LinearLayout barra_balao_3 = (LinearLayout) view.findViewById(R.id.barra_balao_3);
        LinearLayout barra_balao_4 = (LinearLayout) view.findViewById(R.id.barra_balao_4);

        //====================================I BIMESTRE========================================

        //Identifica que o mês atual é o mês início configurado
        if(mes_atual == mes_salvo_inicio_1){

            //Identifica que o dia configurado já passou
            if (dia_atual >= dia_salvo_inicio_1){
                //Aplica amarelo
                balao_inicio_1.setBackgroundResource(R.drawable.layout_gota_agora);
                balao_termino_1.setBackgroundResource(R.drawable.layout_gota_agora);
            }

        }

        //Identifica que o mês atual é o mês término configurado
        else if (mes_atual == mes_salvo_termino_1) {

            //identifica que o dia configurado já passou
            if (dia_atual > dia_salvo_termino_1) {

                //Aplica cinza
                balao_inicio_1.setBackgroundResource(R.drawable.layout_gota_depois);
                balao_termino_1.setBackgroundResource(R.drawable.layout_gota_depois);
                barra_balao_1.setBackgroundResource(R.color.elementos_conteudo);

            } else{//Identifica que o dia atual ainda não passou

                //Aplica amarelo
                balao_inicio_1.setBackgroundResource(R.drawable.layout_gota_agora);
                balao_termino_1.setBackgroundResource(R.drawable.layout_gota_agora);
            }

        }
        //Identifica que o mês atual é maior que o último mês do bimestre
        else if (mes_atual > mes_salvo_termino_1) {

            //Aplica cinza
            balao_inicio_1.setBackgroundResource(R.drawable.layout_gota_depois);
            balao_termino_1.setBackgroundResource(R.drawable.layout_gota_depois);
            barra_balao_1.setBackgroundResource(R.color.elementos_conteudo);

        }

        //Identifica que o mês atual está entre o mês início e o mês final
        else if (mes_atual > mes_salvo_inicio_1 && mes_atual < mes_salvo_termino_1) {

            //Aplica amarelo
            balao_inicio_1.setBackgroundResource(R.drawable.layout_gota_agora);
            balao_termino_1.setBackgroundResource(R.drawable.layout_gota_agora);

        }



        //======================================================================================

        //===================================II BIMESTRE========================================
        //Identifica que o mês atual é o mês início configurado
        if(mes_atual == mes_salvo_inicio_2){

            //Identifica que o dia configurado já passou
            if (dia_atual >= dia_salvo_inicio_2){
                //Aplica amarelo
                balao_inicio_2.setBackgroundResource(R.drawable.layout_gota_agora);
                balao_termino_2.setBackgroundResource(R.drawable.layout_gota_agora);
            }

        }

        //Identifica que o mês atual é o mês término configurado
        else if (mes_atual == mes_salvo_termino_2) {

            //identifica que o dia configurado já passou
            if (dia_atual > dia_salvo_termino_2) {

                //Aplica cinza
                balao_inicio_2.setBackgroundResource(R.drawable.layout_gota_depois);
                balao_termino_2.setBackgroundResource(R.drawable.layout_gota_depois);
                barra_balao_2.setBackgroundResource(R.color.elementos_conteudo);

            } else{//Identifica que o dia atual ainda não passou

                //Aplica amarelo
                balao_inicio_2.setBackgroundResource(R.drawable.layout_gota_agora);
                balao_termino_2.setBackgroundResource(R.drawable.layout_gota_agora);
            }

        }
        //Identifica que o mês atual é maior que o último mês do bimestre
        else if (mes_atual > mes_salvo_termino_2) {

            //Aplica cinza
            balao_inicio_2.setBackgroundResource(R.drawable.layout_gota_depois);
            balao_termino_2.setBackgroundResource(R.drawable.layout_gota_depois);
            barra_balao_2.setBackgroundResource(R.color.elementos_conteudo);

        }

        //Identifica que o mês atual está entre o mês início e o mês final
        else if (mes_atual < mes_salvo_termino_2 && mes_atual > mes_salvo_inicio_2) {

            //Aplica amarelo
            balao_inicio_2.setBackgroundResource(R.drawable.layout_gota_agora);
            balao_termino_2.setBackgroundResource(R.drawable.layout_gota_agora);

        }
        //======================================================================================

        //==================================III BIMESTRE========================================
        //Identifica que o mês atual é o mês início configurado
        if(mes_atual == mes_salvo_inicio_3){

            //Identifica que o dia configurado já passou
            if (dia_atual >= dia_salvo_inicio_3){
                //Aplica amarelo
                balao_inicio_3.setBackgroundResource(R.drawable.layout_gota_agora);
                balao_termino_3.setBackgroundResource(R.drawable.layout_gota_agora);
            }

        }

        //Identifica que o mês atual é o mês término configurado
        else if (mes_atual == mes_salvo_termino_3) {

            //identifica que o dia configurado já passou
            if (dia_atual > dia_salvo_termino_3) {

                //Aplica cinza
                balao_inicio_3.setBackgroundResource(R.drawable.layout_gota_depois);
                balao_termino_3.setBackgroundResource(R.drawable.layout_gota_depois);
                barra_balao_3.setBackgroundResource(R.color.elementos_conteudo);

            } else{//Identifica que o dia atual ainda não passou

                //Aplica amarelo
                balao_inicio_3.setBackgroundResource(R.drawable.layout_gota_agora);
                balao_termino_3.setBackgroundResource(R.drawable.layout_gota_agora);
            }

        }
        //Identifica que o mês atual é maior que o último mês do bimestre
        else if (mes_atual > mes_salvo_termino_3) {

            //Aplica cinza
            balao_inicio_3.setBackgroundResource(R.drawable.layout_gota_depois);
            balao_termino_3.setBackgroundResource(R.drawable.layout_gota_depois);
            barra_balao_3.setBackgroundResource(R.color.elementos_conteudo);

        }

        //Identifica que o mês atual está entre o mês início e o mês final
        else if (mes_atual < mes_salvo_termino_3 && mes_atual > mes_salvo_inicio_3) {

            //Aplica amarelo
            balao_inicio_3.setBackgroundResource(R.drawable.layout_gota_agora);
            balao_termino_3.setBackgroundResource(R.drawable.layout_gota_agora);

        }
        //======================================================================================

        //===================================IV BIMESTRE========================================
        mes_salvo_inicio_4 = 6;
        //Identifica que o mês atual é o mês início configurado
        if (mes_atual == mes_salvo_inicio_4){

            //Identifica que o dia configurado já passou
            if (dia_atual >= dia_salvo_inicio_4){
                //Aplica amarelo
                balao_inicio_4.setBackgroundResource(R.drawable.layout_gota_agora);
                balao_termino_4.setBackgroundResource(R.drawable.layout_gota_agora);
            }

        }

        //Identifica que o mês atual é o mês término configurado
        else if (mes_atual == mes_salvo_termino_4) {

            //identifica que o dia configurado já passou
            if (dia_atual > dia_salvo_termino_4) {

                //Aplica cinza
                balao_inicio_4.setBackgroundResource(R.drawable.layout_gota_depois);
                balao_termino_4.setBackgroundResource(R.drawable.layout_gota_depois);
                barra_balao_4.setBackgroundResource(R.color.elementos_conteudo);

            } else if (dia_atual <= dia_salvo_termino_1){//Identifica que o dia atual ainda não passou

                //Aplica amarelo
                balao_inicio_4.setBackgroundResource(R.drawable.layout_gota_agora);
                balao_termino_4.setBackgroundResource(R.drawable.layout_gota_agora);
            }

        }
        //Identifica que o mês atual é maior que o último mês do bimestre
        else if (mes_atual > mes_salvo_termino_4) {

            //Aplica cinza
            balao_inicio_4.setBackgroundResource(R.drawable.layout_gota_depois);
            balao_termino_4.setBackgroundResource(R.drawable.layout_gota_depois);
            barra_balao_4.setBackgroundResource(R.color.elementos_conteudo);

        }

        //Identifica que o mês atual está entre o mês início e o mês final
        else if (mes_atual < mes_salvo_termino_4 && mes_atual > mes_salvo_inicio_4) {

            //Aplica amarelo
            balao_inicio_4.setBackgroundResource(R.drawable.layout_gota_agora);
            balao_termino_4.setBackgroundResource(R.drawable.layout_gota_agora);

        }

        else if (mes_atual < mes_salvo_inicio_4) {
            balao_inicio_4.setBackgroundResource(R.drawable.layout_gota_antes);
            balao_termino_4.setBackgroundResource(R.drawable.layout_gota_antes);

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

    public void carrega_datas_firebase(){

        //=============MÉTODO CARREGA AS DATAS DO FIREBASE =============

        final String user_id = fbAuth.getCurrentUser().getUid();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        //=================PRIMEIRO BIMESTRE========================================================

        //INÍCIO

        UserData_inicio_1 = database.getReference().child("users").child(user_id).child("datas").child("inicio_1");

        ValueEventListener post_inicio_1_Listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){

                    DatasFirebase post = dataSnapshot.getValue(DatasFirebase.class);

                    dia_salvo_inicio_1 = Integer.parseInt(post.inicio_1.substring(0, 2));
                    mes_salvo_inicio_1 = Integer.parseInt(post.inicio_1.substring(3, 5));

                    SharedPreferences sharedPref = getSharedPreferences("save_datas",0);
                    SharedPreferences.Editor prefEditor = sharedPref.edit();
                    prefEditor.putInt("dia_salvo_inicio_1",dia_salvo_inicio_1);
                    prefEditor.commit();

                    SharedPreferences.Editor prefEditor2 = sharedPref.edit();
                    prefEditor2.putInt("mes_salvo_inicio_1",mes_salvo_inicio_1);
                    prefEditor2.commit();
                }
                else {}
            }
            @Override
            public void onCancelled(DatabaseError databaseError){}
        };
        UserData_inicio_1.addValueEventListener(post_inicio_1_Listener);

        //TÉRMINO

        UserData_termino_1 = database.getReference().child("users").child(user_id).child("datas").child("termino_1");

        ValueEventListener post_termino_1_Listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){

                    DatasFirebase post = dataSnapshot.getValue(DatasFirebase.class);

                    dia_salvo_termino_1 = Integer.parseInt(post.termino_1.substring(0, 2));
                    mes_salvo_termino_1 = Integer.parseInt(post.termino_1.substring(3, 5));

                    //Salva a data lida para que possa ser acessada pelo outro método
                    SharedPreferences sharedPref = getSharedPreferences("save_datas",0);
                    SharedPreferences.Editor prefEditor = sharedPref.edit();
                    prefEditor.putInt("dia_salvo_termino_1",dia_salvo_termino_1);
                    prefEditor.commit();

                    SharedPreferences.Editor prefEditor2 = sharedPref.edit();
                    prefEditor2.putInt("mes_salvo_termino_1",mes_salvo_termino_1);
                    prefEditor2.commit();

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

        UserData_inicio_2 = database.getReference().child("users").child(user_id).child("datas").child("inicio_2");

        ValueEventListener post_inicio_2_Listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){

                    DatasFirebase post = dataSnapshot.getValue(DatasFirebase.class);

                    dia_salvo_inicio_2 = Integer.parseInt(post.inicio_2.substring(0, 2));
                    mes_salvo_inicio_2 = Integer.parseInt(post.inicio_2.substring(3, 5));

                    SharedPreferences sharedPref = getSharedPreferences("save_datas",0);
                    SharedPreferences.Editor prefEditor = sharedPref.edit();
                    prefEditor.putInt("dia_salvo_inicio_2",dia_salvo_inicio_2);
                    prefEditor.commit();

                    SharedPreferences.Editor prefEditor2 = sharedPref.edit();
                    prefEditor2.putInt("mes_salvo_inicio_2",mes_salvo_inicio_2);
                    prefEditor2.commit();

                }
                else {}
            }
            @Override
            public void onCancelled(DatabaseError databaseError){}
        };
        UserData_inicio_2.addValueEventListener(post_inicio_2_Listener);

        //TÉRMINO

        UserData_termino_2 = database.getReference().child("users").child(user_id).child("datas").child("termino_2");

        ValueEventListener post_termino_2_Listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){

                    DatasFirebase post = dataSnapshot.getValue(DatasFirebase.class);

                    dia_salvo_termino_2 = Integer.parseInt(post.termino_2.substring(0, 2));
                    mes_salvo_termino_2 = Integer.parseInt(post.termino_2.substring(3, 5));

                    SharedPreferences sharedPref = getSharedPreferences("save_datas",0);
                    SharedPreferences.Editor prefEditor = sharedPref.edit();
                    prefEditor.putInt("dia_salvo_termino_2",dia_salvo_termino_2);
                    prefEditor.commit();

                    SharedPreferences.Editor prefEditor2 = sharedPref.edit();
                    prefEditor2.putInt("mes_salvo_termino_2",mes_salvo_termino_2);
                    prefEditor2.commit();

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

        UserData_inicio_3 = database.getReference().child("users").child(user_id).child("datas").child("inicio_3");

        ValueEventListener post_inicio_3_Listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){

                    DatasFirebase post = dataSnapshot.getValue(DatasFirebase.class);

                    dia_salvo_inicio_3 = Integer.parseInt(post.inicio_3.substring(0, 2));
                    mes_salvo_inicio_3 = Integer.parseInt(post.inicio_3.substring(3, 5));

                    SharedPreferences sharedPref = getSharedPreferences("save_datas",0);
                    SharedPreferences.Editor prefEditor = sharedPref.edit();
                    prefEditor.putInt("dia_salvo_inicio_3",dia_salvo_inicio_3);
                    prefEditor.commit();

                    SharedPreferences.Editor prefEditor2 = sharedPref.edit();
                    prefEditor2.putInt("mes_salvo_inicio_3",mes_salvo_inicio_3);
                    prefEditor2.commit();

                }
                else {}
            }
            @Override
            public void onCancelled(DatabaseError databaseError){}
        };
        UserData_inicio_3.addValueEventListener(post_inicio_3_Listener);

        //TÉRMINO

        UserData_termino_3 = database.getReference().child("users").child(user_id).child("datas").child("termino_3");

        ValueEventListener post_termino_3_Listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){

                    DatasFirebase post = dataSnapshot.getValue(DatasFirebase.class);

                    dia_salvo_termino_3 = Integer.parseInt(post.termino_3.substring(0, 2));
                    mes_salvo_termino_3 = Integer.parseInt(post.termino_3.substring(3, 5));

                    SharedPreferences sharedPref = getSharedPreferences("save_datas",0);
                    SharedPreferences.Editor prefEditor = sharedPref.edit();
                    prefEditor.putInt("dia_salvo_termino_3",dia_salvo_termino_3);
                    prefEditor.commit();

                    SharedPreferences.Editor prefEditor2 = sharedPref.edit();
                    prefEditor2.putInt("mes_salvo_termino_3",mes_salvo_termino_3);
                    prefEditor2.commit();
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

        UserData_inicio_4 = database.getReference().child("users").child(user_id).child("datas").child("inicio_4");

        ValueEventListener post_inicio_4_Listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){

                    DatasFirebase post = dataSnapshot.getValue(DatasFirebase.class);

                    dia_salvo_inicio_4 = Integer.parseInt(post.inicio_4.substring(0, 2));
                    mes_salvo_inicio_4 = Integer.parseInt(post.inicio_4.substring(3, 5));

                    SharedPreferences sharedPref = getSharedPreferences("save_datas",0);
                    SharedPreferences.Editor prefEditor = sharedPref.edit();
                    prefEditor.putInt("dia_salvo_inicio_4",dia_salvo_inicio_4);
                    prefEditor.commit();

                    SharedPreferences.Editor prefEditor2 = sharedPref.edit();
                    prefEditor2.putInt("mes_salvo_inicio_4",mes_salvo_inicio_4);
                    prefEditor2.commit();

                }
                else {}
            }
            @Override
            public void onCancelled(DatabaseError databaseError){}
        };
        UserData_inicio_4.addValueEventListener(post_inicio_4_Listener);

        //TÉRMINO


        UserData_termino_4 = database.getReference().child("users").child(user_id).child("datas").child("termino_4");

        ValueEventListener post_termino_4_Listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){

                    DatasFirebase post = dataSnapshot.getValue(DatasFirebase.class);

                    dia_salvo_termino_4 = Integer.parseInt(post.termino_4.substring(0, 2));
                    mes_salvo_termino_4 = Integer.parseInt(post.termino_4.substring(3, 5));

                    SharedPreferences sharedPref = getSharedPreferences("save_datas",0);
                    SharedPreferences.Editor prefEditor = sharedPref.edit();
                    prefEditor.putInt("dia_salvo_termino_4",dia_salvo_termino_4);
                    prefEditor.commit();

                    SharedPreferences.Editor prefEditor2 = sharedPref.edit();
                    prefEditor2.putInt("mes_salvo_termino_4",mes_salvo_termino_4);
                    prefEditor2.commit();

                }
                else {}
            }
            @Override
            public void onCancelled(DatabaseError databaseError){}
        };
        UserData_termino_4.addValueEventListener(post_termino_4_Listener);

        //==========================================================================================
    }

    // =========================================== Metodos Gogole Script API ==============================================================================================
    /**
     * Extend the given HttpRequestInitializer (usually a credentials object)
     * with additional initialize() instructions.
     *
     * @param requestInitializer the initializer to copy and adjust; typically
     *         a credential object.
     * @return an initializer with an extended read timeout.
     */
    private static HttpRequestInitializer setHttpTimeout(
            final HttpRequestInitializer requestInitializer) {
        return new HttpRequestInitializer() {
            @Override
            public void initialize(HttpRequest httpRequest)
                    throws IOException {
                requestInitializer.initialize(httpRequest);
                // This allows the API to call (and avoid timing out on)
                // functions that take up to 6 minutes to complete (the maximum
                // allowed script run time), plus a little overhead.
                httpRequest.setReadTimeout(380000);
            }
        };
    }

    /**
     * Attempt to call the API, after verifying that all the preconditions are
     * satisfied. The preconditions are: Google Play Services installed, an
     * account was selected and the device currently has online access. If any
     * of the preconditions are not satisfied, the app will prompt the user as
     * appropriate.
     */
    public void getResultsFromApi() {
        if (! isGooglePlayServicesAvailable()) {
            acquireGooglePlayServices();
        } else if (mCredential.getSelectedAccountName() == null) {
            chooseAccount();
        } else if (! isDeviceOnline()) {
            //mOutputText.setText("Internet não disponível.");
        } else {
            new MakeRequestTask(mCredential).execute();
        }
    }

    /**
     * Attempts to set the account used with the API credentials. If an account
     * name was previously saved it will use that one; otherwise an account
     * picker dialog will be shown to the user. Note that the setting the
     * account to use with the credentials object requires the app to have the
     * GET_ACCOUNTS permission, which is requested here if it is not already
     * present. The AfterPermissionGranted annotation indicates that this
     * function will be rerun automatically whenever the GET_ACCOUNTS permission
     * is granted.
     */
    @AfterPermissionGranted(REQUEST_PERMISSION_GET_ACCOUNTS)
    private void chooseAccount() {
        if (EasyPermissions.hasPermissions(
                this, Manifest.permission.GET_ACCOUNTS))
        {
            String accountName = getPreferences(Context.MODE_PRIVATE)
                    .getString(PREF_ACCOUNT_NAME, null);
            if (accountName != null) {
                mCredential.setSelectedAccountName(accountName);
                getResultsFromApi();
            } else {
                // Start a dialog from which the user can choose an account
                startActivityForResult(
                        mCredential.newChooseAccountIntent(),
                        REQUEST_ACCOUNT_PICKER);
            }
        } else {
            // Request the GET_ACCOUNTS permission via a user dialog
            EasyPermissions.requestPermissions(
                    this,
                    "Este APP precisa acessar sua conta Google (via Contatos).",
                    REQUEST_PERMISSION_GET_ACCOUNTS,
                    Manifest.permission.GET_ACCOUNTS);
        }
    }

    /**
     * Called when an activity launched here (specifically, AccountPicker
     * and authorization) exits, giving you the requestCode you started it with,
     * the resultCode it returned, and any additional data from it.
     * @param requestCode code indicating which activity result is incoming.
     * @param resultCode code indicating the result of the incoming
     *     activity result.
     * @param data Intent (containing result data) returned by incoming
     *     activity result.
     */
    @Override
    protected void onActivityResult(
            int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case REQUEST_GOOGLE_PLAY_SERVICES:
                if (resultCode != RESULT_OK) {
                    //mOutputText.setText("Este APP requer o Google Play Services. Por favor instalar o " +                                    "Google Play Services no seu dispositivo e reabra o aplicativo.");
                } else {
                    getResultsFromApi();
                }
                break;
            case REQUEST_ACCOUNT_PICKER:
                if (resultCode == RESULT_OK && data != null &&
                        data.getExtras() != null) {
                    String accountName =
                            data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                    if (accountName != null) {
                        SharedPreferences settings =
                                getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString(PREF_ACCOUNT_NAME, accountName);
                        editor.apply();
                        mCredential.setSelectedAccountName(accountName);
                        getResultsFromApi();
                    }
                }
                break;
            case REQUEST_AUTHORIZATION:
                if (resultCode == RESULT_OK) {
                    getResultsFromApi();
                }
                break;
        }
    }

    /**
     * Respond to requests for permissions at runtime for API 23 and above.
     * @param requestCode The request code passed in
     *     requestPermissions(android.app.Activity, String, int, String[])
     * @param permissions The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     *     which is either PERMISSION_GRANTED or PERMISSION_DENIED. Never null.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(
                requestCode, permissions, grantResults, this);
    }

    /**
     * Callback for when a permission is granted using the EasyPermissions
     * library.
     * @param requestCode The request code associated with the requested
     *         permission
     * @param list The requested permission list. Never null.
     */
    @Override
    public void onPermissionsGranted(int requestCode, List<String> list) {
        // Do nothing.
    }

    /**
     * Callback for when a permission is denied using the EasyPermissions
     * library.
     * @param requestCode The request code associated with the requested
     *         permission
     * @param list The requested permission list. Never null.
     */
    @Override
    public void onPermissionsDenied(int requestCode, List<String> list) {
        // Do nothing.
    }

    /**
     * Checks whether the device currently has a network connection.
     * @return true if the device has a network connection, false otherwise.
     */
    private boolean isDeviceOnline() {
        ConnectivityManager connMgr =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    /**
     * Check that Google Play services APK is installed and up to date.
     * @return true if Google Play Services is available and up to
     *     date on this device; false otherwise.
     */
    private boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability apiAvailability =
                GoogleApiAvailability.getInstance();
        final int connectionStatusCode =
                apiAvailability.isGooglePlayServicesAvailable(this.getApplicationContext());
        return connectionStatusCode == ConnectionResult.SUCCESS;
    }

    /**
     * Attempt to resolve a missing, out-of-date, invalid or disabled Google
     * Play Services installation via a user dialog, if possible.
     */
    private void acquireGooglePlayServices() {
        GoogleApiAvailability apiAvailability =
                GoogleApiAvailability.getInstance();
        final int connectionStatusCode =
                apiAvailability.isGooglePlayServicesAvailable(this);
        if (apiAvailability.isUserResolvableError(connectionStatusCode)) {
            showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode);
        }
    }


    /**
     * Display an error dialog showing that Google Play Services is missing
     * or out of date.
     * @param connectionStatusCode code describing the presence (or lack of)
     *     Google Play Services on this device.
     */
    void showGooglePlayServicesAvailabilityErrorDialog(
            final int connectionStatusCode) {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        Dialog dialog = apiAvailability.getErrorDialog(
                this,
                connectionStatusCode,
                REQUEST_GOOGLE_PLAY_SERVICES);
        dialog.show();
    }

    /**
     * An asynchronous task that handles the Google Apps Script API call.
     * Placing the API calls in their own task ensures the UI stays responsive.
     */
    private class MakeRequestTask extends AsyncTask<Void, Void, List<String>> {
        private com.google.api.services.script.Script mService = null;
        private Exception mLastError = null;

        MakeRequestTask(GoogleAccountCredential credential) {
            HttpTransport transport = AndroidHttp.newCompatibleTransport();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
            mService = new com.google.api.services.script.Script.Builder(
                    transport, jsonFactory, setHttpTimeout(credential))
                    .setApplicationName("title_activity_config_bimestre")
                    .build();
        }

        /**
         * Background task to call Google Apps Script API.
         * @param params no parameters needed for this task.
         */
        @Override
        protected List<String> doInBackground(Void... params) {
            try {
                return getDataFromApi();
            } catch (Exception e) {
                mLastError = e;
                cancel(true);
                return null;
            }
        }

        /**
         * Call the API to run an Apps Script function that returns a list
         * of folders within the user's root directory on Drive.
         *
         * @return list of String folder names and their IDs
         * @throws IOException
         */
        private List<String> getDataFromApi()
                throws IOException, GoogleAuthException {
            // ID of the script to call. Acquire this from the Apps Script editor,
            // under Publish > Deploy as API executable.
            String scriptId = "1k-hbqzpdY4Ynxszn7hWQCDPyPiwx_1uSE89A9WP-IkWmfAcpfXW4gT_t";
            //tring scriptId = "169ym8aJKxgVWCFdSD2mjaEnqeZebltuIiAIQXKi9jyf2djzF_B32qsSN";
            List<String> folderList = new ArrayList<String>();

            //
            //String titulo = "Lingua Portuguesa - 6 ano - Oralidade";
            //String text1 = "Relacionar as regras de cortesia e de interação";


            List<Object> paramTituloTexto = new ArrayList<>();
            paramTituloTexto.add(titulo);
            paramTituloTexto.add(text1);




            //
            // Create an execution request object.
            //ExecutionRequest request = new ExecutionRequest()
            //        .setFunction("getFoldersUnderRoot");

            ExecutionRequest request = new ExecutionRequest()
                    .setFunction("CriaDocsDrive")
                    .setParameters(paramTituloTexto);

            // Make the request.
            Operation op =
                    mService.scripts().run(scriptId, request).execute();

            // Print results of request.
            if (op.getError() != null) {
                throw new IOException(getScriptError(op));
            }
            if (op.getResponse() != null &&
                    op.getResponse().get("result") != null) {
                // The result provided by the API needs to be cast into
                // the correct type, based upon what types the Apps Script
                // function returns. Here, the function returns an Apps
                // Script Object with String keys and values, so must be
                // cast into a Java Map (folderSet).
                Map<String, String> folderSet =
                        (Map<String, String>)(op.getResponse().get("result"));

                for (String id: folderSet.keySet()) {
                    folderList.add(
                            String.format("%s (%s)", folderSet.get(id), id));
                }
            }

            return folderList;
        }

        /**
         * Interpret an error response returned by the API and return a String
         * summary.
         *
         * @param op the Operation returning an error response
         * @return summary of error response, or null if Operation returned no
         *     error
         */
        private String getScriptError(Operation op) {
            if (op.getError() == null) {
                return null;
            }

            // Extract the first (and only) set of error details and cast as a Map.
            // The values of this map are the script's 'errorMessage' and
            // 'errorType', and an array of stack trace elements (which also need to
            // be cast as Maps).
            Map<String, Object> detail = op.getError().getDetails().get(0);
            List<Map<String, Object>> stacktrace =
                    (List<Map<String, Object>>)detail.get("scriptStackTraceElements");

            StringBuilder sb =
                    new StringBuilder("\nMensagem de erro do Script: ");
            sb.append(detail.get("MensagemdeErro"));

            if (stacktrace != null) {
                // There may not be a stacktrace if the script didn't start
                // executing.
                sb.append("\n Stacktrace Rrror do Script:");
                for (Map<String, Object> elem : stacktrace) {
                    sb.append("\n  ");
                    sb.append(elem.get("function"));
                    sb.append(":");
                    sb.append(elem.get("lineNumber"));
                }
            }
            sb.append("\n");
            return sb.toString();
        }


        @Override
        protected void onPreExecute() {
            //mOutputText.setText("");
            //mProgress.show();
        }

        @Override
        protected void onPostExecute(List<String> output) {
            //mProgress.hide();
            if (output == null || output.size() == 0) {
                //mOutputText.setText("Sem resultados retornados.");
            } else {
                output.add(0, "Dados recuperados usando o Google Apps Script API:");
                //mOutputText.setText(TextUtils.join("\n", output));
            }
        }

        @Override
        protected void onCancelled() {
            //mProgress.hide();
            if (mLastError != null) {
                if (mLastError instanceof GooglePlayServicesAvailabilityIOException) {
                    showGooglePlayServicesAvailabilityErrorDialog(
                            ((GooglePlayServicesAvailabilityIOException) mLastError)
                                    .getConnectionStatusCode());
                } else if (mLastError instanceof UserRecoverableAuthIOException) {
                    startActivityForResult(
                            ((UserRecoverableAuthIOException) mLastError).getIntent(),
                            Main_activity.REQUEST_AUTHORIZATION);
                } else {
                    //mOutputText.setText("O seguinte erro ocorreu:\n"    + mLastError.getMessage());
                }
            } else {
                //mOutputText.setText("Requisição cancelada.");
            }
        }
    }

    // =========================================== FIM: Metodos Gogole Script API ==============================================================================================


}