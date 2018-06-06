package projeto.app.sobral.Utils.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import projeto.app.sobral.R;

public class Login_activity_antiga extends AppCompatActivity{

    private SignInButton mgoogleBtn;
    private static final int RC_SIGN_IN = 1;
    private GoogleApiClient mGoogleApiClient;
    private static final String TAG = "Registro_activity";
    private FirebaseAuth.AuthStateListener mAuthListener;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){
                //FirebaseUser user = firebaseAuth.getCurrentUser();
                //if(user != null){
                if(firebaseAuth.getCurrentUser() != null){
                    startActivity(new Intent(Login_activity_antiga.this,Main_activity.class));

                }
            }

        };

        mgoogleBtn = (SignInButton) findViewById(R.id.signGoogleInButton);
        TextView txtBotaoGoogleSignIn= (TextView) mgoogleBtn.getChildAt(0);
        txtBotaoGoogleSignIn.setText("Login com Google");
        //Configurar o Google SingIn
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                /*
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener(){
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult){
                        Toast.makeText(Registro_activity.this,"Erro ao fazer login!",Toast.LENGTH_LONG).show();
                    }
                })*/
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        mgoogleBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                signIn();
            }

        });



        /*
        progressDiag = new ProgressDialog(this);
        botaoRegistrar = (Button) findViewById(R.id.btn_registrarRegistro);
        campoEditarEmail = (EditText) findViewById(R.id.edtEmailRegistro);
        campoEditarSenha = (EditText) findViewById(R.id.edtSenhaRegistro);
        campoEditarSenhaConfirm = (EditText) findViewById(R.id.edtSenhaRegistroConfirmacao);
        botaoRegistrar.setOnClickListener(this);
        */
    }

    private  void signIn(){

        Intent signIntent  = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signIntent,RC_SIGN_IN);
    }


    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        //Resultado retornado da intenet
        if (requestCode==RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()){
                //Se o Google SignIn obteve sucesso, o autentica no Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            }
        }

    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG,"LogarComCredencial:onComplete:" + task.isSuccessful());

                        if (!task.isSuccessful()){
                            Log.w(TAG,"LogarComCredencial",task.getException());
                            Toast.makeText(Login_activity_antiga.this,"Falha na autenticação!",Toast.LENGTH_LONG).show();


                        }

                    }
                });
    }

    @Override
    protected void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected  void onStop(){
        super.onStop();

        if(mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }


    }
}
