package projeto.app.sobral.Utils;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import projeto.app.sobral.R;

public class AppBarMainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_header_main_activity);




        /*
        //fbAuth = FirebaseAuth.getInstance();
        textUsuarioEmail = (TextView) findViewById(R.id.textViewEmailUsuario);
        nome = account.getDisplayName();
        textUsuarioEmail.setText(nome);

        fotoPessoa = (ImageView) findViewById(R.id.imageViewPhoto);
        foto = account.getPhotoUrl();
        fotoPessoa.setImageURI(foto);

        */

        /*
        if(fbAuth.getCurrentUser() == null){
            startActivity(new Intent(this,Login_activity.class));
        }else{
            FirebaseUser usuario = fbAuth.getCurrentUser();
            textUsuarioEmail.setText(usuario.getEmail());

            startActivity(new Intent(getApplicationContext(),Main_activity.class));

        }
        */
    }
}
