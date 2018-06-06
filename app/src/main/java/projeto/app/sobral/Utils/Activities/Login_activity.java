package projeto.app.sobral.Utils.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import projeto.app.sobral.R;

public class Login_activity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    private SignInButton googleSignInButton;
    private static final int RC_SIGN_IN = 1;
    private GoogleApiClient mGoogleSignClient;
    private static final String TAG = "Login_activity";
    private FirebaseAuth.AuthStateListener mAuthListener;

    private ProgressDialog loadingBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        loadingBar = new ProgressDialog(this);

        googleSignInButton = (SignInButton) findViewById(R.id.signGoogleInButton);
        TextView txtBotaoGoogleSignIn= (TextView) googleSignInButton.getChildAt(0);
        txtBotaoGoogleSignIn.setText("Login com Google");

        //Configurar o Google SingIn
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult)
                    {
                        Toast.makeText(Login_activity.this, "Conexão com Google Falhou", Toast.LENGTH_SHORT).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        googleSignInButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                signIn();
            }

        });

        final LinearLayout btn_google_for_education = (LinearLayout) findViewById(R.id.btn_google_for_education);
        btn_google_for_education.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "http://www.inteceleri.com.br/produtos-m1/#principais-atividades";
                Intent google_for_education = new Intent(Intent.ACTION_VIEW);
                google_for_education.setData(Uri.parse(url));
                startActivity(google_for_education);
            }
        });

    }

    private  void signIn(){

        Intent signIntent  = Auth.GoogleSignInApi.getSignInIntent(mGoogleSignClient);
        startActivityForResult(signIntent,RC_SIGN_IN);
    }


    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        //Resultado retornado da intenet
        if (requestCode==RC_SIGN_IN)
        {
            loadingBar.setTitle("Login Google");
            loadingBar.setMessage("Autenticando o acesso com sua conta...");
            loadingBar.setCanceledOnTouchOutside(true);
            loadingBar.show();

            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess())
            {
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
                //Toast.makeText(Login_activity.this, "Aguarde o credenciamento da sua conta...", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(Login_activity.this, "Sem resposta na autenticação", Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }

    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        Log.d(TAG, "firebaseAuthWithGoogle: " + account.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG,"LogarComCredencial:onComplete:" + task.isSuccessful());

                        if (task.isSuccessful()){
                            Log.w(TAG,"LogarComCredencial: Sucesso");
                            //Toast.makeText(Login_activity.this,"Sucesso na autenticação!",Toast.LENGTH_LONG).show();
                            SendUserToMainActivity();
                            loadingBar.dismiss();
                        }
                        else
                            {
                                Log.w(TAG, "LogarComCredencial:", task.getException());
                                String mensagem = task.getException().toString();
                                SendUserToLoginActivity();
                                Toast.makeText(Login_activity.this,"Falha na autenticação: " + mensagem,Toast.LENGTH_LONG).show();
                                loadingBar.dismiss();
                            }
                    }
                });
    }

    private void SendUserToLoginActivity()
    {
        Intent mainIntent = new Intent(Login_activity.this, Login_activity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }

    private void SendUserToMainActivity()
    {
        Intent mainIntent = new Intent(Login_activity.this, Main_activity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }

    @Override
    protected void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null)
        {
            SendUserToMainActivity();
        }
    }

    @Override
    protected  void onStop(){
        super.onStop();

        if(mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
