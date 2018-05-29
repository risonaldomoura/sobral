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


public class Main_activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

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

        DataSistema();
        carrega_datas_firebase();

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

}