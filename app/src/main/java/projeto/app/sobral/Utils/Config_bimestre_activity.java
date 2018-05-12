package projeto.app.sobral.Utils;

/*
* Esta tela salva as datas configuradas nesta tela no firebase, na conta do usuário
* Falta colocar a parte em que ele carrega do firebase as datas já salvas!
*
* */

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


    private Button btn_cancelar, btn_check;

    public int dia, mes, ano;
    public String mes_string, dia_string, ano_string, data;

    static final int DATE_DIALOG_ID = 0;

    //Dia/Mes/Ano que será enviado para o firebase
    public int dia_inicio_fb_1, mes_inicio_fb_1, ano_inicio_fb_1,
                dia_termino_fb_1, mes_termino_fb_1, ano_termino_fb_1,
                dia_inicio_fb_2, mes_inicio_fb_2, ano_inicio_fb_2,
                dia_termino_fb_2, mes_termino_fb_2, ano_termino_fb_2,
                dia_inicio_fb_3, mes_inicio_fb_3, ano_inicio_fb_3,
                dia_termino_fb_3, mes_termino_fb_3, ano_termino_fb_3,
                dia_inicio_fb_4, mes_inicio_fb_4, ano_inicio_fb_4,
                dia_termino_fb_4, mes_termino_fb_4, ano_termino_fb_4;

    private FirebaseAuth fbAuth;
    private DatabaseReference UserRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.config_bimestre);

        fbAuth = FirebaseAuth.getInstance();
        UserRef = FirebaseDatabase.getInstance().getReference().child("users");

        text_views();

        baloes_datas();

        botoes_toolbar();
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
            //Ano, mês e dia estão em int
            ano = year;
            mes = monthOfYear+1;
            dia = dayOfMonth;

            //Dia trasnformado em string
            dia_string = String.valueOf(dayOfMonth);
            ano_string = String.valueOf(year);

            conversor_mes();

            ver_botoes();

        }
    };

    //Aqui faço a atribuição de mês para nome do mês, por exemplo, 1 == JAN
    public void conversor_mes()
    {
        if (mes == 1)
        {
            mes_string = "JAN";
        }
        else if (mes == 2)
        {
            mes_string = "FEV";
        }
        else if (mes == 3)
        {
            mes_string = "MAR";
        }
        else if (mes == 4)
        {
            mes_string = "ABR";
        }
        else if (mes == 5)
        {
            mes_string = "MAI";
        }
        else if (mes == 6)
        {
            mes_string = "JUN";
        }
        else if (mes == 7)
        {
            mes_string = "JUL";
        }
        else if (mes == 8)
        {
            mes_string = "AGO";
        }
        else if (mes == 9)
        {
            mes_string = "SET";
        }
        else if (mes == 10)
        {
            mes_string = "OUT";
        }
        else if (mes == 11)
        {
            mes_string = "NOV";
        }
        else if (mes == 12)
        {
            mes_string = "DEZ";
        }



    }

    //Aqui grava qual botão foi clicado, para chamar o método de cada um
    public void ver_botoes(){

        if (ver_inicio_I == 1){

            setar_inicio_1();

            ver_inicio_I = 0;

        }

        if (ver_termino_I == 1){

            setar_termino_1();

            ver_termino_I = 0;

        }

        if (ver_inicio_II == 1){

            setar_inicio_2();

            ver_inicio_II = 0;

        }

        if (ver_termino_II == 1){

            setar_termino_2();

            ver_termino_II = 0;

        }

        if (ver_inicio_III == 1){

            setar_inicio_3();

            ver_inicio_III = 0;

        }

        if (ver_termino_III == 1){

            setar_termino_3();

            ver_termino_III = 0;

        }

        if (ver_inicio_IV == 1){

            setar_inicio_4();

            ver_inicio_IV = 0;

        }

        if (ver_termino_IV == 1){

            setar_termino_4();

            ver_termino_IV = 0;

        }



    }


    public void setar_inicio_1(){

        //Dia e mês mostrados no balão do bimestre
        dia_inicio_balao_1.setText(dia_string);
        mes_inicio_balao_1.setText(mes_string);

        //Dia, mês e ano que serão enviados para o Firebase
        dia_inicio_fb_1 = dia;
        mes_inicio_fb_1 = mes;
        ano_inicio_fb_1 = ano;
    }

    public void setar_termino_1() {

        dia_termino_balao_1.setText(dia_string);
        mes_termino_balao_1.setText(mes_string);

        dia_termino_fb_1 = dia;
        mes_termino_fb_1 = mes;
        ano_termino_fb_1 = ano;
    }

    public void setar_inicio_2(){

        dia_inicio_balao_2.setText(dia_string);
        mes_inicio_balao_2.setText(mes_string);

        dia_inicio_fb_2 = dia;
        mes_inicio_fb_2 = mes;
        ano_inicio_fb_2 = ano;
    }

    public void setar_termino_2() {

        dia_termino_balao_2.setText(dia_string);
        mes_termino_balao_2.setText(mes_string);

        dia_termino_fb_2 = dia;
        mes_termino_fb_2 = mes;
        ano_termino_fb_2 = ano;
    }

    public void setar_inicio_3(){

        dia_inicio_balao_3.setText(dia_string);
        mes_inicio_balao_3.setText(mes_string);

        dia_inicio_fb_3 = dia;
        mes_inicio_fb_3 = mes;
        ano_inicio_fb_3 = ano;
    }

    public void setar_termino_3() {

        dia_termino_balao_3.setText(dia_string);
        mes_termino_balao_3.setText(mes_string);

        dia_termino_fb_3 = dia;
        mes_termino_fb_3 = mes;
        ano_termino_fb_3 = ano;
    }

    public void setar_inicio_4(){

        dia_inicio_balao_4.setText(dia_string);
        mes_inicio_balao_4.setText(mes_string);

        dia_inicio_fb_4 = dia;
        mes_inicio_fb_4 = mes;
        ano_inicio_fb_4 = ano;
    }

    public void setar_termino_4() {

        dia_termino_balao_4.setText(dia_string);
        mes_termino_balao_4.setText(mes_string);

        dia_termino_fb_4 = dia;
        mes_termino_fb_4 = mes;
        ano_termino_fb_4 = ano;
    }

    //Salvando dados do usuário no Firebase
    private void SaveAccountSetupInformation()
    {
        //Captura dos dados do usuário
        final String user_id = fbAuth.getCurrentUser().getUid();

        final String inicio_1 = (dia_inicio_fb_1+"/"+mes_inicio_fb_1+"/"+ano_inicio_fb_1);
        final String termino_1 = (dia_termino_fb_1+"/"+mes_termino_fb_1+"/"+ano_termino_fb_1);

        final String inicio_2 = (dia_inicio_fb_2+"/"+mes_inicio_fb_2+"/"+ano_inicio_fb_2);
        final String termino_2 = (dia_termino_fb_2+"/"+mes_termino_fb_2+"/"+ano_termino_fb_2);;

        final String inicio_3 = (dia_inicio_fb_3+"/"+mes_inicio_fb_3+"/"+ano_inicio_fb_3);
        final String termino_3 = (dia_termino_fb_3+"/"+mes_termino_fb_3+"/"+ano_termino_fb_3);

        final String inicio_4 = (dia_inicio_fb_4+"/"+mes_inicio_fb_4+"/"+ano_inicio_fb_4);
        final String termino_4 = (dia_termino_fb_4+"/"+mes_termino_fb_4+"/"+ano_termino_fb_4);


        HashMap userMap = new HashMap();

        userMap.put("inicio_1", inicio_1);
        userMap.put("termino_1", termino_1);

        userMap.put("inicio_2", inicio_2);
        userMap.put("termino_2", termino_2);

        userMap.put("inicio_3", inicio_3);
        userMap.put("termino_3", termino_3);

        userMap.put("inicio_4", inicio_4);
        userMap.put("termino_4", termino_4);


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
                    String message =  task.getException().getMessage();
                    Toast.makeText(Config_bimestre_activity.this, "Dados não salvos. Erro: " + message, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
