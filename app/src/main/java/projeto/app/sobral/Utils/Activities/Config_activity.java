package projeto.app.sobral.Utils.Activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.content.*;
import android.widget.*;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;

import projeto.app.sobral.Introducao.IntroActivity;
import projeto.app.sobral.R;

public class Config_activity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    private FirebaseAuth mAuth;
    private static final int RC_SIGN_IN = 1;
    private GoogleApiClient mGoogleApiClient;
    private GoogleSignInAccount GoogleSignInApi;
    private static final String TAG = "Config_activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        mAuth = FirebaseAuth.getInstance();

        botoes();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    public void onBackPressed() {

        Intent it = new Intent(this, Main_activity.class);
        startActivity(it);

        return;
    }

    private void signOut() {

        Toast.makeText(Config_activity.this, "Saindo da conta Google...", Toast.LENGTH_SHORT).show();

        Auth.GoogleSignInApi.signOut(mGoogleApiClient);
        mAuth.signOut();
        mGoogleApiClient.disconnect();
        mGoogleApiClient.connect();

        Intent loginIntent = new Intent(Config_activity.this, Login_activity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
        finish();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                startActivity(new Intent(this, Main_activity.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;
            default:break;
        }
        return true;
    }

    @Override
    protected void onPause(){
        super .onPause();
        finish();
    }

    public void botoes()
    {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Configurações"); //titulo a mostrar na barra

        final LinearLayout btn_config_bimestre = (LinearLayout) findViewById(R.id.btn_config_bimestre);
        btn_config_bimestre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Config_activity.this, Config_bimestre_activity.class);
                startActivity(intent);
                finish();
            }
        });

        final LinearLayout btn_apresentacao = (LinearLayout) findViewById(R.id.btn_apresentacao);
        btn_apresentacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Resetando contador de inicialização da IntroActivity
                Boolean status = false;
                SharedPreferences sharedPref_intro = getSharedPreferences("PREF",Context.MODE_PRIVATE);
                SharedPreferences.Editor prefEditor = sharedPref_intro.edit();
                prefEditor.putBoolean("status",status).apply();

                //Chamando a IntroActivity
                Intent IntroIntent = new Intent(Config_activity.this, IntroActivity.class);
                startActivity(IntroIntent);
                finish();
            }
        });

        final LinearLayout btn_termos = (LinearLayout) findViewById(R.id.btn_termos);
        btn_termos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Chamando a TermosActivity
                Intent TermosIntent = new Intent(Config_activity.this, Termos_activity.class);
                startActivity(TermosIntent);
                finish();
            }
        });

        final LinearLayout btn_licencas = (LinearLayout) findViewById(R.id.btn_licencas);
        btn_licencas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Chamando a LicençasActivity
                Intent LicencaIntent = new Intent(Config_activity.this, Licencas_activity.class);
                startActivity(LicencaIntent);
                finish();
            }
        });

        final LinearLayout btn_logout = (LinearLayout) findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String titulo = "Atenção!";
                String mensagem = "Deseja realmente sair da sua conta?";
                AlertDialog.Builder builder = new AlertDialog.Builder(Config_activity.this);
                builder.setTitle(titulo);
                builder.setMessage(mensagem);

                builder.setNeutralButton("NÃO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        signOut();
                    }
                });
                builder.show();

            }
        });

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        Toast.makeText(Config_activity.this, "Conexão falhou", Toast.LENGTH_SHORT).show();

    }
}
