package projeto.app.sobral.Utils;

/*
* Esta tela salva as datas configuradas nesta tela no firebase, dentro da conta do usuário
* E carrega as datas também e seta nos campos de data da tela conforme a conta do usuário.
*
* */


//============================================ Imports Google Script API ==========================================================================================
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
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



import com.google.android.gms.auth.GoogleAuthException;
import com.google.api.services.script.model.*;
import java.util.Map;

import android.Manifest;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
//===================================================== FIM: Imports Google Script API ====================================================================================


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import projeto.app.sobral.R;


public class Config_bimestre_activity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks  {

    //===================================== Parametros Google Script API =============================================================================


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

    public TextView
            dia_inicio_balao_1, mes_inicio_balao_1,
            dia_termino_balao_1, mes_termino_balao_1,
            dia_inicio_balao_2, mes_inicio_balao_2,
            dia_termino_balao_2, mes_termino_balao_2,
            dia_inicio_balao_3, mes_inicio_balao_3,
            dia_termino_balao_3, mes_termino_balao_3,
            dia_inicio_balao_4, mes_inicio_balao_4,
            dia_termino_balao_4, mes_termino_balao_4;


    //Para verificar qual botão foi clicado e atribuir as datas conforme a variável de cada um
    public int
            ver_inicio_I = 0 , ver_termino_I = 0,
            ver_inicio_II = 0 , ver_termino_II = 0,
            ver_inicio_III = 0 , ver_termino_III = 0,
            ver_inicio_IV = 0 , ver_termino_IV = 0;

    public int
            gravar_inicio_1=0, gravar_termino_1=0,
            gravar_inicio_2=0, gravar_termino_2=0,
            gravar_inicio_3=0, gravar_termino_3=0,
            gravar_inicio_4=0, gravar_termino_4=0;

    private Button btn_cancelar, btn_check;

    public String dia, mes, ano, mes_palavra, data;

    //Dia e Mês carregado do firebase
    public String dia_up_inicio_1, mes_up_inicio_1, dia_up_termino_1, mes_up_termino_1,
            dia_up_inicio_2, mes_up_inicio_2, dia_up_termino_2, mes_up_termino_2,
            dia_up_inicio_3, mes_up_inicio_3, dia_up_termino_3, mes_up_termino_3,
            dia_up_inicio_4, mes_up_inicio_4, dia_up_termino_4, mes_up_termino_4;

    static final int DATE_DIALOG_ID = 0;

    //Dia/Mes/Ano que será enviado para o firebase
    public String dia_inicio_fb_1, mes_inicio_fb_1, ano_inicio_fb_1,
                dia_termino_fb_1, mes_termino_fb_1, ano_termino_fb_1,
                dia_inicio_fb_2, mes_inicio_fb_2, ano_inicio_fb_2,
                dia_termino_fb_2, mes_termino_fb_2, ano_termino_fb_2,
                dia_inicio_fb_3, mes_inicio_fb_3, ano_inicio_fb_3,
                dia_termino_fb_3, mes_termino_fb_3, ano_termino_fb_3,
                dia_inicio_fb_4, mes_inicio_fb_4, ano_inicio_fb_4,
                dia_termino_fb_4, mes_termino_fb_4, ano_termino_fb_4;

    private FirebaseAuth fbAuth;
    private DatabaseReference UserRef;
    private DatabaseReference UserData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.config_bimestre);



        fbAuth = FirebaseAuth.getInstance();
        UserRef = FirebaseDatabase.getInstance().getReference().child("users");


        text_views();

        baloes_datas();


        botoes_toolbar();


        carregar_data_inicio_1();
        carregar_data_termino_1();
        carregar_data_inicio_2();
        carregar_data_termino_2();
        carregar_data_inicio_3();
        carregar_data_termino_3();
        carregar_data_inicio_4();
        carregar_data_termino_4();

        mOutputText = new TextView(this);
        mProgress = new ProgressDialog(this);

        // Initialize credentials and service object.
        mCredential = GoogleAccountCredential.usingOAuth2(
                this, Arrays.asList(SCOPES))
                .setBackOff(new ExponentialBackOff());



    }




    private void text_views() {
        dia_inicio_balao_1 = (TextView) findViewById(R.id.dia_inicio_I);
        mes_inicio_balao_1 = (TextView) findViewById(R.id.mes_inicio_I);

        dia_termino_balao_1 = (TextView) findViewById(R.id.dia_termino_I);
        mes_termino_balao_1 = (TextView) findViewById(R.id.mes_termino_I);

        dia_inicio_balao_2 = (TextView) findViewById(R.id.dia_inicio_II);
        mes_inicio_balao_2 = (TextView) findViewById(R.id.mes_inicio_II);

        dia_termino_balao_2 = (TextView) findViewById(R.id.dia_termino_II);
        mes_termino_balao_2 = (TextView) findViewById(R.id.mes_termino_II);

        dia_inicio_balao_3 = (TextView) findViewById(R.id.dia_inicio_III);
        mes_inicio_balao_3 = (TextView) findViewById(R.id.mes_inicio_III);

        dia_termino_balao_3 = (TextView) findViewById(R.id.dia_termino_III);
        mes_termino_balao_3 = (TextView) findViewById(R.id.mes_termino_III);

        dia_inicio_balao_4 = (TextView) findViewById(R.id.dia_inicio_IV);
        mes_inicio_balao_4 = (TextView) findViewById(R.id.mes_inicio_IV);

        dia_termino_balao_4 = (TextView) findViewById(R.id.dia_termino_IV);
        mes_termino_balao_4 = (TextView) findViewById(R.id.mes_termino_IV);

    }

    private void baloes_datas() {

        final LinearLayout btn_inicio_I = (LinearLayout) findViewById(R.id.btn_inicio_I);
        final LinearLayout btn_inicio_II = (LinearLayout) findViewById(R.id.btn_inicio_II);
        final LinearLayout btn_inicio_III = (LinearLayout) findViewById(R.id.btn_inicio_III);
        final LinearLayout btn_inicio_IV = (LinearLayout) findViewById(R.id.btn_inicio_IV);
        final LinearLayout btn_termino_I = (LinearLayout) findViewById(R.id.btn_termino_I);
        final LinearLayout btn_termino_II = (LinearLayout) findViewById(R.id.btn_termino_II);
        final LinearLayout btn_termino_III = (LinearLayout) findViewById(R.id.btn_termino_III);
        final LinearLayout btn_termino_IV = (LinearLayout) findViewById(R.id.btn_termino_IV);

        btn_inicio_I.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ver_inicio_I = 1;
                gravar_inicio_1 = 1;

                showDialog(DATE_DIALOG_ID);

            }
        });

        btn_termino_I.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ver_termino_I = 1;
                gravar_termino_1 = 1;
                showDialog(DATE_DIALOG_ID);

            }
        });

        btn_inicio_II.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ver_inicio_II = 1;
                gravar_inicio_2 = 1;
                showDialog(DATE_DIALOG_ID);

            }
        });

        btn_termino_II.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ver_termino_II = 1;
                gravar_termino_2 = 1;
                showDialog(DATE_DIALOG_ID);

            }
        });

        btn_inicio_III.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ver_inicio_III = 1;
                gravar_inicio_3 = 1;
                showDialog(DATE_DIALOG_ID);

            }
        });

        btn_termino_III.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ver_termino_III = 1;
                gravar_termino_3 = 1;
                showDialog(DATE_DIALOG_ID);

            }
        });

        btn_inicio_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ver_inicio_IV = 1;
                gravar_inicio_4 = 1;
                showDialog(DATE_DIALOG_ID);

            }
        });

        btn_termino_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ver_termino_IV = 1;
                gravar_termino_4 = 1;
                showDialog(DATE_DIALOG_ID);

            }
        });
    }

    private void botoes_toolbar() {



        btn_cancelar = (Button) findViewById(R.id.btn_cancelar);
        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(Config_bimestre_activity.this);
                builder.setTitle("Atenção!");
                builder.setMessage("Descartar alteração das datas?")
                        .setCancelable(false)
                        .setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                        .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(getApplicationContext(), Config_activity.class);
                                startActivity(intent);

                                Toast.makeText(getApplicationContext(), "Alterações descartadas", Toast.LENGTH_SHORT).show();
                                finishAffinity();
                            }
                        });
                builder.show();
            }
        });




        btn_check = (Button) findViewById(R.id.btn_check);
        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                AlertDialog.Builder builder = new AlertDialog.Builder(Config_bimestre_activity.this);
                builder.setTitle("Atenção!");
                builder.setMessage("Confirmar alteração e sincronização das datas no seu Google Agenda?")
                        .setCancelable(false)
                        .setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                        .setPositiveButton("SIM", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int id) {


                                //===================== Execucao do Script======================================

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        getResultsFromApi();
                                    }
                                },2000);

                                //===================== Fim Execucao do Script===================================

                                GravarDataFirebase();

                                mProgress.setMessage("Salvando e sincronizando datas no seu Google Agenda ...");



                                Intent intent = new Intent(Config_bimestre_activity.this, Config_activity.class);
                                startActivity(intent);

                                //Toast.makeText(getApplicationContext(), "Datas salvas", Toast.LENGTH_SHORT).show();
                                //finishAffinity();



                            }

                        });

                builder.show();


            }
        });





    }

    @Override
    protected Dialog onCreateDialog(int id) {
        Calendar calendario = Calendar.getInstance();

        int ano = calendario.get(Calendar.YEAR);
        int mes = calendario.get(Calendar.MONTH);
        int dia = calendario.get(Calendar.DAY_OF_MONTH);

        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, mDateSetListener, ano, mes,
                        dia);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            //Para tornar pública a data
            //Ano, mês e dia estão em String
            ano = String.valueOf(year);
            mes = String.valueOf(monthOfYear+1);
            dia = String.valueOf(dayOfMonth);

            adicao_zero();
            conversor_mes();
            ver_botoes();

        }
    };




    //Adição do Zero à frente do número
    public void adicao_zero(){

        if (dia.equals("1"))
            dia = "01";
        else if (dia.equals("2"))
            dia = "02";
        else if (dia.equals("3"))
            dia = "03";
        else if (dia.equals("4"))
            dia = "04";
        else if (dia.equals("5"))
            dia = "05";
        else if (dia.equals("6"))
            dia = "06";
        else if (dia.equals("7"))
            dia = "07";
        else if (dia.equals("8"))
            dia = "08";
        else if (dia.equals("9"))
            dia = "09";


        if (mes.equals("1"))
            mes = "01";
        else if (mes.equals("2"))
            mes = "02";
        else if (mes.equals("3"))
            mes = "03";
        else if (mes.equals("4"))
            mes = "04";
        else if (mes.equals("5"))
            mes = "05";
        else if (mes.equals("6"))
            mes = "06";
        else if (mes.equals("7"))
            mes = "07";
        else if (mes.equals("8"))
            mes = "08";
        else if (mes.equals("9"))
            mes = "09";

    }

    //Aqui faço a atribuição de mês para nome do mês, por exemplo, 1 == JAN
    public void conversor_mes()
    {
        if (mes.equals("01"))
        {
            mes_palavra = "JAN";
        }
        else if (mes.equals("02"))
        {
            mes_palavra = "FEV";
        }
        else if (mes.equals("03"))
        {
            mes_palavra = "MAR";
        }
        else if (mes.equals("04"))
        {
            mes_palavra = "ABR";
        }
        else if (mes.equals("05"))
        {
            mes_palavra = "MAI";
        }
        else if (mes.equals("06"))
        {
            mes_palavra = "JUN";
        }
        else if (mes.equals("07"))
        {
            mes_palavra = "JUL";
        }
        else if (mes.equals("08"))
        {
            mes_palavra = "AGO";
        }
        else if (mes.equals("09"))
        {
            mes_palavra = "SET";
        }
        else if (mes.equals("10"))
        {
            mes_palavra = "OUT";
        }
        else if (mes.equals("11"))
        {
            mes_palavra = "NOV";
        }
        else if (mes.equals("12"))
        {
            mes_palavra = "DEZ";
        }



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

    //Aqui grava qual botão foi clicado, para chamar o método de cada um e gravar no firebase cada data separadamente
    public void ver_botoes(){

        if (ver_inicio_I == 1){

            setar_inicio_1();

            ver_inicio_I =0;
        }

        if (ver_termino_I == 1){

            setar_termino_1();
            ver_termino_I =0;
        }

        if (ver_inicio_II == 1){

            setar_inicio_2();
            ver_inicio_II =0;
        }

        if (ver_termino_II == 1){

            setar_termino_2();
            ver_termino_II =0;
        }

        if (ver_inicio_III == 1){

            setar_inicio_3();
            ver_inicio_III =0;
        }

        if (ver_termino_III == 1){

            setar_termino_3();
            ver_termino_III =0;
        }

        if (ver_inicio_IV == 1){

            setar_inicio_4();
            ver_inicio_IV =0;
        }

        if (ver_termino_IV == 1){

            setar_termino_4();
            ver_termino_IV =0;
        }



    }


    public void setar_inicio_1(){

        //Dia e mês mostrados no balão do bimestre
        dia_inicio_balao_1.setText(dia);
        mes_inicio_balao_1.setText(mes_palavra);

        //Dia, mês e ano que serão enviados para o Firebase
        dia_inicio_fb_1 = dia;
        mes_inicio_fb_1 = mes;
        ano_inicio_fb_1 = ano;
    }
    public void setar_termino_1() {

        dia_termino_balao_1.setText(dia);
        mes_termino_balao_1.setText(mes_palavra);

        dia_termino_fb_1 = dia;
        mes_termino_fb_1 = mes;
        ano_termino_fb_1 = ano;
    }
    public void setar_inicio_2(){

        dia_inicio_balao_2.setText(dia);
        mes_inicio_balao_2.setText(mes_palavra);

        dia_inicio_fb_2 = dia;
        mes_inicio_fb_2 = mes;
        ano_inicio_fb_2 = ano;
    }
    public void setar_termino_2() {

        dia_termino_balao_2.setText(dia);
        mes_termino_balao_2.setText(mes_palavra);

        dia_termino_fb_2 = dia;
        mes_termino_fb_2 = mes;
        ano_termino_fb_2 = ano;
    }
    public void setar_inicio_3(){

        dia_inicio_balao_3.setText(dia);
        mes_inicio_balao_3.setText(mes_palavra);

        dia_inicio_fb_3 = dia;
        mes_inicio_fb_3 = mes;
        ano_inicio_fb_3 = ano;
    }
    public void setar_termino_3() {

        dia_termino_balao_3.setText(dia);
        mes_termino_balao_3.setText(mes_palavra);

        dia_termino_fb_3 = dia;
        mes_termino_fb_3 = mes;
        ano_termino_fb_3 = ano;
    }
    public void setar_inicio_4(){

        dia_inicio_balao_4.setText(dia);
        mes_inicio_balao_4.setText(mes_palavra);

        dia_inicio_fb_4 = dia;
        mes_inicio_fb_4 = mes;
        ano_inicio_fb_4 = ano;
    }
    public void setar_termino_4() {

        dia_termino_balao_4.setText(dia);
        mes_termino_balao_4.setText(mes_palavra);

        dia_termino_fb_4 = dia;
        mes_termino_fb_4 = mes;
        ano_termino_fb_4 = ano;
    }

    //Salvando dados do usuário no Firebase
    private void GravarDataFirebase()
    {
        //Captura dos dados do usuário

        if (gravar_inicio_1 == 1){
            GravarDataFirebase_inicio_1();
            gravar_inicio_1 =0;
        }

        if (gravar_termino_1 == 1){

            GravarDataFirebase_termino_1();
            gravar_termino_1 =0;
        }

        if (gravar_inicio_2 == 1){
            GravarDataFirebase_inicio_2();
            gravar_inicio_2 =0;
        }

        if (gravar_termino_2 == 1){

            GravarDataFirebase_termino_2();
            gravar_termino_2 =0;
        }

        if (gravar_inicio_3 == 1){

            GravarDataFirebase_inicio_3();
            gravar_inicio_3 =0;
        }

        if (gravar_termino_3 == 1){

            GravarDataFirebase_termino_3();
            gravar_termino_3 =0;
        }

        if (gravar_inicio_4 == 1){

            GravarDataFirebase_inicio_4();
            gravar_inicio_4 =0;
        }

        if (gravar_termino_4 == 1){

            GravarDataFirebase_termino_4();
            gravar_termino_4 =0;
        }

    }


    public void GravarDataFirebase_inicio_1(){
        final String user_id = fbAuth.getCurrentUser().getUid();

        final String inicio_1 = (dia_inicio_fb_1+"/"+mes_inicio_fb_1+"/"+ano_inicio_fb_1);

        HashMap userMap_inicio_1 = new HashMap();

        userMap_inicio_1.put("inicio_1", inicio_1);

        UserRef.child(user_id).child("datas").child("inicio_1").updateChildren(userMap_inicio_1).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task)
            {
                if(task.isSuccessful())
                {
                    //Toast.makeText(Config_bimestre_activity.this, "Datas salvas com sucesso e sincronizadas no seu Google Agenda", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //String message =  task.getException().getMessage();
                    //Toast.makeText(Config_bimestre_activity.this, "Dados não salvos. Erro: " + message, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public void GravarDataFirebase_termino_1(){

        final String user_id = fbAuth.getCurrentUser().getUid();
        final String termino_1 = (dia_termino_fb_1+"/"+mes_termino_fb_1+"/"+ano_termino_fb_1);

        HashMap userMap_termino_1 = new HashMap();

        userMap_termino_1.put("termino_1", termino_1);

        UserRef.child(user_id).child("datas").child("termino_1").updateChildren(userMap_termino_1).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task)
            {
                if(task.isSuccessful())
                {
                    //Toast.makeText(Config_bimestre_activity.this, "Datas salvas com sucesso e sincronizadas no seu Google Agenda", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //String message =  task.getException().getMessage();
                    //Toast.makeText(Config_bimestre_activity.this, "Dados não salvos. Erro: " + message, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public void GravarDataFirebase_inicio_2(){

        final String user_id = fbAuth.getCurrentUser().getUid();
        final String inicio_2 = (dia_inicio_fb_2+"/"+mes_inicio_fb_2+"/"+ano_inicio_fb_2);

        HashMap userMap_inicio_2 = new HashMap();

        userMap_inicio_2.put("inicio_2", inicio_2);

        UserRef.child(user_id).child("datas").child("inicio_2").updateChildren(userMap_inicio_2).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task)
            {
                if(task.isSuccessful())
                {
                    //Toast.makeText(Config_bimestre_activity.this, "Datas salvas com sucesso e sincronizadas no seu Google Agenda", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //String message =  task.getException().getMessage();
                    //Toast.makeText(Config_bimestre_activity.this, "Dados não salvos. Erro: " + message, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void GravarDataFirebase_termino_2(){

        final String user_id = fbAuth.getCurrentUser().getUid();
        final String termino_2 = (dia_termino_fb_2+"/"+mes_termino_fb_2+"/"+ano_termino_fb_2);

        HashMap userMap_termino_2 = new HashMap();

        userMap_termino_2.put("termino_2", termino_2);

        UserRef.child(user_id).child("datas").child("termino_2").updateChildren(userMap_termino_2).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task)
            {
                if(task.isSuccessful())
                {
                    //Toast.makeText(Config_bimestre_activity.this, "Datas salvas com sucesso e sincronizadas no seu Google Agenda", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //String message =  task.getException().getMessage();
                    //Toast.makeText(Config_bimestre_activity.this, "Dados não salvos. Erro: " + message, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public void GravarDataFirebase_inicio_3(){

        final String user_id = fbAuth.getCurrentUser().getUid();
        final String inicio_3 = (dia_inicio_fb_3+"/"+mes_inicio_fb_3+"/"+ano_inicio_fb_3);

        HashMap userMap_inicio_3 = new HashMap();

        userMap_inicio_3.put("inicio_3", inicio_3);

        UserRef.child(user_id).child("datas").child("inicio_3").updateChildren(userMap_inicio_3).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task)
            {
                if(task.isSuccessful())
                {
                    //Toast.makeText(Config_bimestre_activity.this, "Datas salvas com sucesso e sincronizadas no seu Google Agenda", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //String message =  task.getException().getMessage();
                    //Toast.makeText(Config_bimestre_activity.this, "Dados não salvos. Erro: " + message, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public void GravarDataFirebase_termino_3(){

        final String user_id = fbAuth.getCurrentUser().getUid();
        final String termino_3 = (dia_termino_fb_3+"/"+mes_termino_fb_3+"/"+ano_termino_fb_3);

        HashMap userMap_termino_3 = new HashMap();

        userMap_termino_3.put("termino_3", termino_3);

        UserRef.child(user_id).child("datas").child("termino_3").updateChildren(userMap_termino_3).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task)
            {
                if(task.isSuccessful())
                {
                    //Toast.makeText(Config_bimestre_activity.this, "Datas salvas com sucesso e sincronizadas no seu Google Agenda", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //String message =  task.getException().getMessage();
                    //Toast.makeText(Config_bimestre_activity.this, "Dados não salvos. Erro: " + message, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public void GravarDataFirebase_inicio_4(){

        final String user_id = fbAuth.getCurrentUser().getUid();
        final String inicio_4 = (dia_inicio_fb_4+"/"+mes_inicio_fb_4+"/"+ano_inicio_fb_4);

        HashMap userMap_inicio_4 = new HashMap();

        userMap_inicio_4.put("inicio_4", inicio_4);

        UserRef.child(user_id).child("datas").child("inicio_4").updateChildren(userMap_inicio_4).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task)
            {
                if(task.isSuccessful())
                {
                    //Toast.makeText(Config_bimestre_activity.this, "Datas salvas com sucesso e sincronizadas no seu Google Agenda", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //String message =  task.getException().getMessage();
                    //Toast.makeText(Config_bimestre_activity.this, "Dados não salvos. Erro: " + message, Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    public void GravarDataFirebase_termino_4(){

        final String user_id = fbAuth.getCurrentUser().getUid();
        final String termino_4 = (dia_termino_fb_4+"/"+mes_termino_fb_4+"/"+ano_termino_fb_4);

        HashMap userMap_termino_4 = new HashMap();

        userMap_termino_4.put("termino_4", termino_4);

        UserRef.child(user_id).child("datas").child("termino_4").updateChildren(userMap_termino_4).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task)
            {
                if(task.isSuccessful())
                {
                    //Toast.makeText(Config_bimestre_activity.this, "Datas salvas com sucesso e sincronizadas no seu Google Agenda", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //String message =  task.getException().getMessage();
                    //Toast.makeText(Config_bimestre_activity.this, "Dados não salvos. Erro: " + message, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    public void carregar_data_inicio_1(){

        final String user_id = fbAuth.getCurrentUser().getUid();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        UserData = database.getReference().child("users").child(user_id).child("datas").child("inicio_1");

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){

                    DatasFirebase post = dataSnapshot.getValue(DatasFirebase.class);

                    dia_up_inicio_1 = post.inicio_1;
                    mes_up_inicio_1 = post.inicio_1;

                    dia_up_inicio_1 = dia_up_inicio_1.substring(0, 2);
                    mes_up_inicio_1 = mes_up_inicio_1.substring(3, 5);

                    conversor_mes_up_inicio_1();

                    dia_inicio_balao_1.setText(dia_up_inicio_1);
                    mes_inicio_balao_1.setText(mes_up_inicio_1);

                }
                else {
                    //Toast.makeText(Config_bimestre_activity.this, "Datas não configuradas", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Config_bimestre_activity.this, "Falha na atualização das datas",
                        Toast.LENGTH_SHORT).show();

            }
        };
        UserData.addValueEventListener(postListener);

    }
    public void carregar_data_termino_1(){

        final String user_id = fbAuth.getCurrentUser().getUid();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        UserData = database.getReference().child("users").child(user_id).child("datas").child("termino_1");

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){

                    DatasFirebase post = dataSnapshot.getValue(DatasFirebase.class);

                    dia_up_termino_1 = post.termino_1;
                    mes_up_termino_1 = post.termino_1;

                    dia_up_termino_1 = dia_up_termino_1.substring(0, 2);
                    mes_up_termino_1 = mes_up_termino_1.substring(3, 5);

                    conversor_mes_up_termino_1();

                    dia_termino_balao_1.setText(dia_up_termino_1);
                    mes_termino_balao_1.setText(mes_up_termino_1);

                }
                else {
                    //Toast.makeText(Config_bimestre_activity.this, "Datas não configuradas", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Config_bimestre_activity.this, "Falha na atualização das datas",
                        Toast.LENGTH_SHORT).show();

            }
        };
        UserData.addValueEventListener(postListener);

    }
    public void carregar_data_inicio_2(){

        final String user_id = fbAuth.getCurrentUser().getUid();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        UserData = database.getReference().child("users").child(user_id).child("datas").child("inicio_2");

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){

                    DatasFirebase post = dataSnapshot.getValue(DatasFirebase.class);

                    dia_up_inicio_2 = post.inicio_2;
                    mes_up_inicio_2 = post.inicio_2;

                    dia_up_inicio_2 = dia_up_inicio_2.substring(0, 2);
                    mes_up_inicio_2 = mes_up_inicio_2.substring(3, 5);

                    conversor_mes_up_inicio_2();

                    dia_inicio_balao_2.setText(dia_up_inicio_2);
                    mes_inicio_balao_2.setText(mes_up_inicio_2);

                }
                else {
                    //Toast.makeText(Config_bimestre_activity.this, "Datas não configuradas", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Config_bimestre_activity.this, "Falha na atualização das datas",
                        Toast.LENGTH_SHORT).show();

            }
        };
        UserData.addValueEventListener(postListener);

    }
    public void carregar_data_termino_2(){

        final String user_id = fbAuth.getCurrentUser().getUid();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        UserData = database.getReference().child("users").child(user_id).child("datas").child("termino_2");

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){

                    DatasFirebase post = dataSnapshot.getValue(DatasFirebase.class);

                    dia_up_termino_2 = post.termino_2;
                    mes_up_termino_2 = post.termino_2;

                    dia_up_termino_2 = dia_up_termino_2.substring(0, 2);
                    mes_up_termino_2 = mes_up_termino_2.substring(3, 5);

                    conversor_mes_up_termino_2();

                    dia_termino_balao_2.setText(dia_up_termino_2);
                    mes_termino_balao_2.setText(mes_up_termino_2);

                }
                else {
                   // Toast.makeText(Config_bimestre_activity.this, "Datas não configuradas", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Config_bimestre_activity.this, "Falha na atualização das datas",
                        Toast.LENGTH_SHORT).show();

            }
        };
        UserData.addValueEventListener(postListener);

    }
    public void carregar_data_inicio_3(){

        final String user_id = fbAuth.getCurrentUser().getUid();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        UserData = database.getReference().child("users").child(user_id).child("datas").child("inicio_3");

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){

                    DatasFirebase post = dataSnapshot.getValue(DatasFirebase.class);

                    dia_up_inicio_3 = post.inicio_3;
                    mes_up_inicio_3 = post.inicio_3;

                    dia_up_inicio_3 = dia_up_inicio_3.substring(0, 2);
                    mes_up_inicio_3 = mes_up_inicio_3.substring(3, 5);

                    conversor_mes_up_inicio_3();

                    dia_inicio_balao_3.setText(dia_up_inicio_3);
                    mes_inicio_balao_3.setText(mes_up_inicio_3);

                }
                else {
                    //Toast.makeText(Config_bimestre_activity.this, "Datas não configuradas", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Config_bimestre_activity.this, "Falha na atualização das datas",
                        Toast.LENGTH_SHORT).show();

            }
        };
        UserData.addValueEventListener(postListener);

    }
    public void carregar_data_termino_3(){

        final String user_id = fbAuth.getCurrentUser().getUid();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        UserData = database.getReference().child("users").child(user_id).child("datas").child("termino_3");

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){

                    DatasFirebase post = dataSnapshot.getValue(DatasFirebase.class);

                    dia_up_termino_3 = post.termino_3;
                    mes_up_termino_3 = post.termino_3;

                    dia_up_termino_3 = dia_up_termino_3.substring(0, 2);
                    mes_up_termino_3 = mes_up_termino_3.substring(3, 5);

                    conversor_mes_up_termino_3();

                    dia_termino_balao_3.setText(dia_up_termino_3);
                    mes_termino_balao_3.setText(mes_up_termino_3);

                }
                else {
                    //Toast.makeText(Config_bimestre_activity.this, "Datas não configuradas", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Config_bimestre_activity.this, "Falha na atualização das datas",
                        Toast.LENGTH_SHORT).show();

            }
        };
        UserData.addValueEventListener(postListener);

    }
    public void carregar_data_inicio_4(){

        final String user_id = fbAuth.getCurrentUser().getUid();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        UserData = database.getReference().child("users").child(user_id).child("datas").child("inicio_4");

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){

                    DatasFirebase post = dataSnapshot.getValue(DatasFirebase.class);

                    dia_up_inicio_4 = post.inicio_4;
                    mes_up_inicio_4 = post.inicio_4;

                    dia_up_inicio_4 = dia_up_inicio_4.substring(0, 2);
                    mes_up_inicio_4 = mes_up_inicio_4.substring(3, 5);

                    conversor_mes_up_inicio_4();

                    dia_inicio_balao_4.setText(dia_up_inicio_4);
                    mes_inicio_balao_4.setText(mes_up_inicio_4);

                }
                else {
                    //Toast.makeText(Config_bimestre_activity.this, "Datas não configuradas", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Config_bimestre_activity.this, "Falha na atualização das datas",
                        Toast.LENGTH_SHORT).show();

            }
        };
        UserData.addValueEventListener(postListener);

    }
    public void carregar_data_termino_4(){

        final String user_id = fbAuth.getCurrentUser().getUid();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        UserData = database.getReference().child("users").child(user_id).child("datas").child("termino_4");

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){

                    DatasFirebase post = dataSnapshot.getValue(DatasFirebase.class);

                    dia_up_termino_4 = post.termino_4;
                    mes_up_termino_4 = post.termino_4;

                    dia_up_termino_4 = dia_up_termino_4.substring(0, 2);
                    mes_up_termino_4 = mes_up_termino_4.substring(3, 5);

                    conversor_mes_up_termino_4();

                    dia_termino_balao_4.setText(dia_up_termino_4);
                    mes_termino_balao_4.setText(mes_up_termino_4);

                }
                else {
                    //Toast.makeText(Config_bimestre_activity.this, "Datas não configuradas", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Config_bimestre_activity.this, "Falha na atualização das datas",
                        Toast.LENGTH_SHORT).show();

            }
        };
        UserData.addValueEventListener(postListener);

    }

    @Override
    public void onStart(){
        super.onStart();


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
                    throws java.io.IOException {
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
    private void getResultsFromApi() {
        if (! isGooglePlayServicesAvailable()) {
            acquireGooglePlayServices();
        } else if (mCredential.getSelectedAccountName() == null) {
            chooseAccount();
        } else if (! isDeviceOnline()) {
            mOutputText.setText("No network connection available.");
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
                    mOutputText.setText(
                            "Este APP requer o Google Play Services. Por favor instalar o " +
                                    "Google Play Services no seu dispositivo e reabra o aplicativo.");
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


            /*List<Object> paramTituloTexto = new ArrayList<>();
            paramTituloTexto.add(titulo);
            paramTituloTexto.add(text1);
            */

            List<Object> paramDiaMesAno = new ArrayList<>();
            paramDiaMesAno.add(dia_inicio_fb_1);
            paramDiaMesAno.add(mes_inicio_fb_1);
            paramDiaMesAno.add(ano_inicio_fb_1);
            paramDiaMesAno.add(dia_termino_fb_1);
            paramDiaMesAno.add(mes_termino_fb_1);
            paramDiaMesAno.add(ano_termino_fb_1);

            paramDiaMesAno.add(dia_inicio_fb_2);
            paramDiaMesAno.add(mes_inicio_fb_2);
            paramDiaMesAno.add(ano_inicio_fb_2);
            paramDiaMesAno.add(dia_termino_fb_2);
            paramDiaMesAno.add(mes_termino_fb_2);
            paramDiaMesAno.add(ano_termino_fb_2);

            paramDiaMesAno.add(dia_inicio_fb_3);
            paramDiaMesAno.add(mes_inicio_fb_3);
            paramDiaMesAno.add(ano_inicio_fb_3);
            paramDiaMesAno.add(dia_termino_fb_3);
            paramDiaMesAno.add(mes_termino_fb_3);
            paramDiaMesAno.add(ano_termino_fb_3);

            paramDiaMesAno.add(dia_inicio_fb_4);
            paramDiaMesAno.add(mes_inicio_fb_4);
            paramDiaMesAno.add(ano_inicio_fb_4);
            paramDiaMesAno.add(dia_termino_fb_4);
            paramDiaMesAno.add(mes_termino_fb_4);
            paramDiaMesAno.add(ano_termino_fb_4);

            //
            // Create an execution request object.
            //ExecutionRequest request = new ExecutionRequest()
            //        .setFunction("getFoldersUnderRoot");

            ExecutionRequest request = new ExecutionRequest()
                    .setFunction("CriarEventoCalendario")
                    .setParameters(paramDiaMesAno);

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

            java.lang.StringBuilder sb =
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
            mOutputText.setText("");
            mProgress.show();
        }

        @Override
        protected void onPostExecute(List<String> output) {
            mProgress.hide();
            if (output == null || output.size() == 0) {
                mOutputText.setText("Sem resultados retornados.");
            } else {
                output.add(0, "Dados recuperados usando o Google Apps Script API:");
                mOutputText.setText(TextUtils.join("\n", output));
            }
        }

        @Override
        protected void onCancelled() {
            mProgress.hide();
            if (mLastError != null) {
                if (mLastError instanceof GooglePlayServicesAvailabilityIOException) {
                    showGooglePlayServicesAvailabilityErrorDialog(
                            ((GooglePlayServicesAvailabilityIOException) mLastError)
                                    .getConnectionStatusCode());
                } else if (mLastError instanceof UserRecoverableAuthIOException) {
                    startActivityForResult(
                            ((UserRecoverableAuthIOException) mLastError).getIntent(),
                            Config_bimestre_activity.REQUEST_AUTHORIZATION);
                } else {
                    mOutputText.setText("O seguinte erro ocorreu:\n"
                            + mLastError.getMessage());
                }
            } else {
                mOutputText.setText("Requisição cancelada.");
            }
        }
    }

    // =========================================== FIM: Metodos Gogole Script API ==============================================================================================
}
