package projeto.app.sobral.Utils.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;

import projeto.app.sobral.R;


public class Splash_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

       Thread timerThread = new Thread() {
           public void run(){
               try{
                   sleep (500);
               } catch (InterruptedException e){
                   e.printStackTrace();
               } finally
               {
                   Intent intent = new Intent(Splash_activity.this, Main_activity.class);
                   startActivity(intent);
               }
           }
       };
       timerThread.start();

    }

    @Override
    protected void onPause(){
        super .onPause();
        finish();
    }
}
