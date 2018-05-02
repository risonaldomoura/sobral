package projeto.app.sobral.Utils;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;

import projeto.app.sobral.R;

public class Config_bimestre_activity extends AppCompatActivity {

    public TextView
            dia_inicio_I, mes_inicio_I,
            dia_termino_I, mes_termino_I,
            dia_inicio_II, mes_inicio_II,
            dia_termino_II, mes_termino_II,
            dia_inicio_III, mes_inicio_III,
            dia_termino_III, mes_termino_III,
            dia_inicio_IV, mes_inicio_IV,
            dia_termino_IV, mes_termino_IV;


    //Para verificar qual botão foi clicado
    public int
            ver_inicio_I = 0 , ver_termino_I = 0,
            ver_inicio_II = 0 , ver_termino_II = 0,
            ver_inicio_III = 0 , ver_termino_III = 0,
            ver_inicio_IV = 0 , ver_termino_IV = 0;


    private Button btn_cancelar, btn_check;

    public int ano, mes, dia;

    static final int DATE_DIALOG_ID = 0;

    public String mes_final, dia_final;

    private FirebaseAuth fbAuth;
    private DatabaseReference UserRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.config_bimestre);


        fbAuth = FirebaseAuth.getInstance();
        UserRef = FirebaseDatabase.getInstance().getReference().child("users");

        final LinearLayout btn_inicio_I = (LinearLayout) findViewById(R.id.btn_inicio_I);
        final LinearLayout btn_inicio_II = (LinearLayout) findViewById(R.id.btn_inicio_II);
        final LinearLayout btn_inicio_III = (LinearLayout) findViewById(R.id.btn_inicio_III);
        final LinearLayout btn_inicio_IV = (LinearLayout) findViewById(R.id.btn_inicio_IV);
        final LinearLayout btn_termino_I = (LinearLayout) findViewById(R.id.btn_termino_I);
        final LinearLayout btn_termino_II = (LinearLayout) findViewById(R.id.btn_termino_II);
        final LinearLayout btn_termino_III = (LinearLayout) findViewById(R.id.btn_termino_III);
        final LinearLayout btn_termino_IV = (LinearLayout) findViewById(R.id.btn_termino_IV);

        dia_inicio_I = (TextView) findViewById(R.id.dia_inicio_I);
        mes_inicio_I = (TextView) findViewById(R.id.mes_inicio_I);

        dia_termino_I = (TextView) findViewById(R.id.dia_termino_I);
        mes_termino_I = (TextView) findViewById(R.id.mes_termino_I);

        dia_inicio_II = (TextView) findViewById(R.id.dia_inicio_II);
        mes_inicio_II = (TextView) findViewById(R.id.mes_inicio_II);

        dia_termino_II = (TextView) findViewById(R.id.dia_termino_II);
        mes_termino_II = (TextView) findViewById(R.id.mes_termino_II);

        dia_inicio_III = (TextView) findViewById(R.id.dia_inicio_III);
        mes_inicio_III = (TextView) findViewById(R.id.mes_inicio_III);

        dia_termino_III = (TextView) findViewById(R.id.dia_termino_III);
        mes_termino_III = (TextView) findViewById(R.id.mes_termino_III);

        dia_inicio_IV = (TextView) findViewById(R.id.dia_inicio_IV);
        mes_inicio_IV = (TextView) findViewById(R.id.mes_inicio_IV);

        dia_termino_IV = (TextView) findViewById(R.id.dia_termino_IV);
        mes_termino_IV = (TextView) findViewById(R.id.mes_termino_IV);

        btn_inicio_I.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ver_inicio_I = 1;

                showDialog(DATE_DIALOG_ID);

            }
        });

        btn_termino_I.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ver_termino_I = 1;

                showDialog(DATE_DIALOG_ID);

            }
        });

        btn_inicio_II.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ver_inicio_II = 1;

                showDialog(DATE_DIALOG_ID);

            }
        });

        btn_termino_II.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ver_termino_II = 1;

                showDialog(DATE_DIALOG_ID);

            }
        });

        btn_inicio_III.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ver_inicio_III = 1;

                showDialog(DATE_DIALOG_ID);

            }
        });

        btn_termino_III.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ver_termino_III = 1;

                showDialog(DATE_DIALOG_ID);

            }
        });

        btn_inicio_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ver_inicio_IV = 1;

                showDialog(DATE_DIALOG_ID);

            }
        });

        btn_termino_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ver_termino_IV = 1;

                showDialog(DATE_DIALOG_ID);

            }
        });


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
                builder.setMessage("Confirmar alteração das datas?")
                        .setCancelable(false)
                        .setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                        .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                SaveAccountSetupInformation();

                                Intent intent = new Intent(getApplicationContext(), Config_activity.class);
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
            //ano = year;
            mes = monthOfYear+1;
            dia = dayOfMonth;

            String data = String.valueOf(dayOfMonth) + " /"
                    + String.valueOf(monthOfYear+1) + " /" + String.valueOf(year);

            dia_final = String.valueOf(dayOfMonth);

            //Toast.makeText(Config_bimestre_activity.this,"DATA = " + data, Toast.LENGTH_SHORT).show();
            //Toast.makeText(Config_bimestre_activity.this,"Dia = " + dia, Toast.LENGTH_SHORT).show();
            //Toast.makeText(Config_bimestre_activity.this,"Mes = " + mes, Toast.LENGTH_SHORT).show();

            ver_botoes();

        }
    };

    public void conversor_mes()
    {
        if (mes == 1)
        {
            mes_final = "JAN";
        }
        else if (mes == 2)
        {
            mes_final = "FEV";
        }
        else if (mes == 3)
        {
            mes_final = "MAR";
        }
        else if (mes == 4)
        {
            mes_final = "ABR";
        }
        else if (mes == 5)
        {
            mes_final = "MAI";
        }
        else if (mes == 6)
        {
            mes_final = "JUN";
        }
        else if (mes == 7)
        {
            mes_final = "JUL";
        }
        else if (mes == 8)
        {
            mes_final = "AGO";
        }
        else if (mes == 9)
        {
            mes_final = "SET";
        }
        else if (mes == 10)
        {
            mes_final = "OUT";
        }
        else if (mes == 11)
        {
            mes_final = "NOV";
        }
        else if (mes == 12)
        {
            mes_final = "DEZ";
        }



    }


    public void ver_botoes(){

        if (ver_inicio_I == 1){

            setar_inicio_I();

            ver_inicio_I = 0;

        }

        if (ver_termino_I == 1){

            setar_termino_I();

            ver_termino_I = 0;

        }

        if (ver_inicio_II == 1){

            setar_inicio_II();

            ver_inicio_II = 0;

        }

        if (ver_termino_II == 1){

            setar_termino_II();

            ver_termino_II = 0;

        }

        if (ver_inicio_III == 1){

            setar_inicio_III();

            ver_inicio_III = 0;

        }

        if (ver_termino_III == 1){

            setar_termino_III();

            ver_termino_III = 0;

        }

        if (ver_inicio_IV == 1){

            setar_inicio_IV();

            ver_inicio_IV = 0;

        }

        if (ver_termino_IV == 1){

            setar_termino_IV();

            ver_termino_IV = 0;

        }



    }


    public void setar_inicio_I(){

        conversor_mes();

        dia_inicio_I.setText(dia_final);
        mes_inicio_I.setText(mes_final);
    }

    public void setar_termino_I() {

        conversor_mes();

        dia_termino_I.setText(dia_final);
        mes_termino_I.setText(mes_final);
    }

    public void setar_inicio_II(){

        conversor_mes();

        dia_inicio_II.setText(dia_final);
        mes_inicio_II.setText(mes_final);
    }

    public void setar_termino_II() {

        conversor_mes();

        dia_termino_II.setText(dia_final);
        mes_termino_II.setText(mes_final);
    }

    public void setar_inicio_III(){

        conversor_mes();

        dia_inicio_III.setText(dia_final);
        mes_inicio_III.setText(mes_final);
    }

    public void setar_termino_III() {

        conversor_mes();

        dia_termino_III.setText(dia_final);
        mes_termino_III.setText(mes_final);
    }

    public void setar_inicio_IV(){

        conversor_mes();

        dia_inicio_IV.setText(dia_final);
        mes_inicio_IV.setText(mes_final);
    }

    public void setar_termino_IV() {

        conversor_mes();

        dia_termino_IV.setText(dia_final);
        mes_termino_IV.setText(mes_final);
    }

    //Salvando dados do usuário no Firebase
    private void SaveAccountSetupInformation()
    {
        //Captura dos dados do usuário
        final String user_id = fbAuth.getCurrentUser().getUid();

        final String dia_inicio_1 = dia_inicio_I.getText().toString();
        final String dia_termino_1 = dia_termino_I.getText().toString();

        final String mes_inicio_1 = mes_inicio_I.getText().toString();
        final String mes_termino_1 = mes_termino_I.getText().toString();

        final String dia_inicio_2 = dia_inicio_II.getText().toString();
        final String dia_termino_2 = dia_termino_II.getText().toString();

        final String mes_inicio_2 = mes_inicio_II.getText().toString();
        final String mes_termino_2 = mes_termino_II.getText().toString();

        final String dia_inicio_3 = dia_inicio_III.getText().toString();
        final String dia_termino_3 = dia_termino_III.getText().toString();

        final String mes_inicio_3 = mes_inicio_III.getText().toString();
        final String mes_termino_3 = mes_termino_III.getText().toString();

        final String dia_inicio_4 = dia_inicio_IV.getText().toString();
        final String dia_termino_4 = dia_termino_IV.getText().toString();

        final String mes_inicio_4 = mes_inicio_IV.getText().toString();
        final String mes_termino_4 = mes_termino_IV.getText().toString();

        HashMap userMap = new HashMap();
        userMap.put("dia_inicio_I", dia_inicio_1);
        userMap.put("dia_termino_I", dia_termino_1);

        userMap.put("mes_inicio_I", mes_inicio_1);
        userMap.put("mes_termino_I", mes_termino_1);

        userMap.put("dia_inicio_II", dia_inicio_2);
        userMap.put("dia_termino_II", dia_termino_2);

        userMap.put("mes_inicio_II", mes_inicio_2);
        userMap.put("mes_termino_II", mes_termino_2);

        userMap.put("dia_inicio_III", dia_inicio_3);
        userMap.put("dia_termino_III", dia_termino_3);

        userMap.put("mes_inicio_III", mes_inicio_3);
        userMap.put("mes_termino_III", mes_termino_3);

        userMap.put("dia_inicio_IV", dia_inicio_4);
        userMap.put("dia_termino_IV", dia_termino_4);

        userMap.put("mes_inicio_IV", mes_inicio_4);
        userMap.put("mes_termino_IV", mes_termino_4);

        UserRef.child(user_id).child("datas").updateChildren(userMap).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task)
            {
                if(task.isSuccessful())
                {
                    Toast.makeText(Config_bimestre_activity.this, "Datas salvas", Toast.LENGTH_LONG).show();
                }
                else
                {
                    //String message =  task.getException().getMessage();
                    //Toast.makeText(Main_activity.this, "Dados não salvos " + message, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
