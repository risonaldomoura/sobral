package projeto.app.sobral.Utils;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import projeto.app.sobral.R;

public class Config_bimestre_activity_Antiga extends AppCompatActivity{

    public static int ID1_I;//dia_inicio_I_bimestre
    public static int ID2_I;//dia_termino_I_bimestre
    public static int ID3_I;//mes_inicio_I_bimestre
    public static int ID4_I;//mes_termino_I_bimestre

    public static int ID1_II;
    public static int ID2_II;
    public static int ID3_II;
    public static int ID4_II;

    public static int ID1_III;
    public static int ID2_III;
    public static int ID3_III;
    public static int ID4_III;

    public static int ID1_IV;
    public static int ID2_IV;
    public static int ID3_IV;
    public static int ID4_IV;

    private Spinner spn_dia_inicio_I;
    private Spinner spn_dia_termino_I;
    private Spinner spn_mes_inicio_I;
    private Spinner spn_mes_termino_I;

    private Spinner spn_dia_inicio_II;
    private Spinner spn_dia_termino_II;
    private Spinner spn_mes_inicio_II;
    private Spinner spn_mes_termino_II;

    private Spinner spn_dia_inicio_III;
    private Spinner spn_dia_termino_III;
    private Spinner spn_mes_inicio_III;
    private Spinner spn_mes_termino_III;

    private Spinner spn_dia_inicio_IV;
    private Spinner spn_dia_termino_IV;
    private Spinner spn_mes_inicio_IV;
    private Spinner spn_mes_termino_IV;



    public int ID_salvo_dia_inicio_I;
    public int ID_salvo_dia_termino_I;
    public int ID_salvo_mes_inicio_I;
    public int ID_salvo_mes_termino_I;

    public int ID_salvo_dia_inicio_II;
    public int ID_salvo_dia_termino_II;
    public int ID_salvo_mes_inicio_II;
    public int ID_salvo_mes_termino_II;

    public int ID_salvo_dia_inicio_III;
    public int ID_salvo_dia_termino_III;
    public int ID_salvo_mes_inicio_III;
    public int ID_salvo_mes_termino_III;

    public int ID_salvo_dia_inicio_IV;
    public int ID_salvo_dia_termino_IV;
    public int ID_salvo_mes_inicio_IV;
    public int ID_salvo_mes_termino_IV;

    public int dia_inicio_I;
    public int dia_termino_I;
    public int mes_inicio_I;
    public int mes_termino_I;

    public int dia_inicio_II;
    public int dia_termino_II;
    public int mes_inicio_II;
    public int mes_termino_II;

    public int dia_inicio_III;
    public int dia_termino_III;
    public int mes_inicio_III;
    public int mes_termino_III;

    public int dia_inicio_IV;
    public int dia_termino_IV;
    public int mes_inicio_IV;
    public int mes_termino_IV;

    private AlertDialog ErroDataI;//atributo da classe BuscaErroData
    private AlertDialog ErroDataII;
    private AlertDialog ErroDataIII;
    private AlertDialog ErroDataIV;

    private List<String> adp_dia = new ArrayList<String>();
    private List<String> adp_mes = new ArrayList<String>();
    //private String[] id1_I;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.config_bimestre_antiga);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Configuração de bimestre"); //titulo a mostrar na barra

        adp_dia.add("  Dia  ");
        adp_dia.add("   01   ");
        adp_dia.add("   02   ");
        adp_dia.add("   03   ");
        adp_dia.add("   04   ");
        adp_dia.add("   05   ");
        adp_dia.add("   06   ");
        adp_dia.add("   07   ");
        adp_dia.add("   08   ");
        adp_dia.add("   09   ");
        adp_dia.add("   10   ");
        adp_dia.add("   11   ");
        adp_dia.add("   12   ");
        adp_dia.add("   13   ");
        adp_dia.add("   14   ");
        adp_dia.add("   15   ");
        adp_dia.add("   16   ");
        adp_dia.add("   17   ");
        adp_dia.add("   18   ");
        adp_dia.add("   19   ");
        adp_dia.add("   20   ");
        adp_dia.add("   21   ");
        adp_dia.add("   22   ");
        adp_dia.add("   23   ");
        adp_dia.add("   24   ");
        adp_dia.add("   25   ");
        adp_dia.add("   26   ");
        adp_dia.add("   27   ");
        adp_dia.add("   28   ");
        adp_dia.add("   29   ");
        adp_dia.add("   30   ");
        adp_dia.add("   31   ");

        adp_mes.add("Mês");
        adp_mes.add("Janeiro");
        adp_mes.add("Fevereiro");
        adp_mes.add("Março");
        adp_mes.add("Abril");
        adp_mes.add("Maio");
        adp_mes.add("Junho");
        adp_mes.add("Julho");
        adp_mes.add("Agosto");
        adp_mes.add("Setembro");
        adp_mes.add("Outubro");
        adp_mes.add("Novembro");
        adp_mes.add("Dezembro");


        //======================================SPINNERS DO I BIMESTRE==============================
        //spn_dia_inicio_I
        spn_dia_inicio_I = (Spinner) findViewById(R.id.spn_dia_inicio_I);

        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(this, R.layout.layout_spn_config_bimestre, adp_dia);
        ArrayAdapter<String> spnAdapter1 = adp1;
        spnAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_dia_inicio_I.setAdapter(spnAdapter1);

        //spn_dia_termino_I
        spn_dia_termino_I = (Spinner) findViewById(R.id.spn_dia_termino_I);

        ArrayAdapter<String> adp2 = new ArrayAdapter<String>(this, R.layout.layout_spn_config_bimestre, adp_dia);
        ArrayAdapter<String> spnAdapter2 = adp2;
        spnAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_dia_termino_I.setAdapter(spnAdapter2);

        //spn_mes_inicio_I
        spn_mes_inicio_I = (Spinner) findViewById(R.id.spn_mes_inicio_I);

        ArrayAdapter<String> adp3 = new ArrayAdapter<String>(this, R.layout.layout_spn_config_bimestre, adp_mes);
        ArrayAdapter<String> spnAdapter3 = adp3;
        spnAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_mes_inicio_I.setAdapter(spnAdapter3);

        //spn_mes_termino_I
        spn_mes_termino_I = (Spinner) findViewById(R.id.spn_mes_termino_I);

        ArrayAdapter<String> adp4 = new ArrayAdapter<String>(this, R.layout.layout_spn_config_bimestre, adp_mes);
        ArrayAdapter<String> spnAdapter4 = adp4;
        spnAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_mes_termino_I.setAdapter(spnAdapter4);


        //load do ID da spinner do arquivo sharedPreferences
        SharedPreferences sharedPref_dia_inicio_I = getSharedPreferences("pref_bimestre",MODE_PRIVATE);
        ID_salvo_dia_inicio_I = sharedPref_dia_inicio_I.getInt("dia_inicio_balao_1",15);
        //if(ID_salvo_dia_inicio_I != -1)
            // set the value of the spinner
            spn_dia_inicio_I.setSelection(ID_salvo_dia_inicio_I);

        //Leitura spn_dia_inicio_I
        spn_dia_inicio_I.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id1) {


                //Save do ID da spinner no sharedPreferences
                dia_inicio_I = spn_dia_inicio_I.getSelectedItemPosition();
                SharedPreferences sharedPref_dia_inicio_I = getSharedPreferences("pref_bimestre",0);
                SharedPreferences.Editor prefEditor = sharedPref_dia_inicio_I.edit();
                prefEditor.putInt("dia_inicio_balao_1",dia_inicio_I);
                prefEditor.commit();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });//end leitura

        //==========================================================================================

        //load do ID da spinner do arquivo sharedPreferences
        SharedPreferences sharedPref_dia_termino_I = getSharedPreferences("pref_bimestre",MODE_PRIVATE);
        ID_salvo_dia_termino_I = sharedPref_dia_termino_I.getInt("dia_termino_balao_1",15);
        //if(ID_salvo_dia_termino_I != -1)
            // set the value of the spinner
            spn_dia_termino_I.setSelection(ID_salvo_dia_termino_I);


        //Leitura spn_dia_termino_I
        spn_dia_termino_I.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id1) {

                //Save do ID da spinner no sharedPreferences
                dia_termino_I = spn_dia_termino_I.getSelectedItemPosition();
                SharedPreferences sharedPref_dia_termino_I = getSharedPreferences("pref_bimestre",0);
                SharedPreferences.Editor prefEditor = sharedPref_dia_termino_I.edit();
                prefEditor.putInt("dia_termino_balao_1",dia_termino_I);
                prefEditor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //==========================================================================================

        //load do ID da spinner do arquivo sharedPreferences
        SharedPreferences sharedPref_mes_inicio_I = getSharedPreferences("pref_bimestre",MODE_PRIVATE);
        ID_salvo_mes_inicio_I = sharedPref_mes_inicio_I.getInt("mes_inicio_balao_1",1);
        //if(ID_salvo_mes_inicio_I != -1)
            // set the value of the spinner
            spn_mes_inicio_I.setSelection(ID_salvo_mes_inicio_I);

        //Leitura spn_mes_inicio_I
        spn_mes_inicio_I.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id1) {

                //Save do ID da spinner no sharedPreferences
                mes_inicio_I = spn_mes_inicio_I.getSelectedItemPosition();
                SharedPreferences sharedPref_mes_inicio_I = getSharedPreferences("pref_bimestre",0);
                SharedPreferences.Editor prefEditor = sharedPref_mes_inicio_I.edit();
                prefEditor.putInt("mes_inicio_balao_1",mes_inicio_I);
                prefEditor.commit();

                //BuscaErroData();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        //==========================================================================================

        //load do ID da spinner do arquivo sharedPreferences
        SharedPreferences sharedPref_mes_termino_I = getSharedPreferences("pref_bimestre",MODE_PRIVATE);
        ID_salvo_mes_termino_I = sharedPref_mes_termino_I.getInt("mes_termino_balao_1",3);
        //if(ID_salvo_mes_termino_I != -1)
            // set the value of the spinner
            spn_mes_termino_I.setSelection(ID_salvo_mes_termino_I);

        //Leitura spn_mes_termino_I
        spn_mes_termino_I.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id1) {

                //Save do ID da spinner no sharedPreferences
                mes_termino_I = spn_mes_termino_I.getSelectedItemPosition();
                SharedPreferences sharedPref_mes_termino_I = getSharedPreferences("pref_bimestre",0);
                SharedPreferences.Editor prefEditor = sharedPref_mes_termino_I.edit();
                prefEditor.putInt("mes_termino_balao_1",mes_termino_I);
                prefEditor.commit();

                //BuscaErroData();

                if (mes_termino_I < mes_inicio_I) {

                    String mensagem = "O mês de término do 1º bimestre está errado. Configure-o novamente.";
                    AlertDialog.Builder builder = new AlertDialog.Builder(Config_bimestre_activity_Antiga.this);
                    builder.setTitle("Erro");
                    builder.setMessage(mensagem);
                    builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                        }
                    });
                    builder.show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        //==========================================================================================

        //======================================SPINNERS DO II BIMESTRE=============================
        //spn_dia_inicio_II
        spn_dia_inicio_II = (Spinner) findViewById(R.id.spn_dia_inicio_II);

        ArrayAdapter<String> adp5 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, adp_dia);
        ArrayAdapter<String> spnAdapter5 = adp5;
        spnAdapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_dia_inicio_II.setAdapter(spnAdapter5);

        //spn_dia_termino_II
        spn_dia_termino_II = (Spinner) findViewById(R.id.spn_dia_termino_II);

        ArrayAdapter<String> adp6 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, adp_dia);
        ArrayAdapter<String> spnAdapter6 = adp6;
        spnAdapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_dia_termino_II.setAdapter(spnAdapter6);

        //spn_mes_inicio_II
        spn_mes_inicio_II = (Spinner) findViewById(R.id.spn_mes_inicio_II);

        ArrayAdapter<String> adp7 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, adp_mes);
        ArrayAdapter<String> spnAdapter7 = adp7;
        spnAdapter7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_mes_inicio_II.setAdapter(spnAdapter7);

        //spn_mes_termino_II
        spn_mes_termino_II = (Spinner) findViewById(R.id.spn_mes_termino_II);

        ArrayAdapter<String> adp8 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, adp_mes);
        ArrayAdapter<String> spnAdapter8 = adp8;
        spnAdapter8.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_mes_termino_II.setAdapter(spnAdapter8);

        //==========================================================================================

        //load do ID da spinner do arquivo sharedPreferences
        SharedPreferences sharedPref_dia_inicio_II = getSharedPreferences("pref_bimestre",MODE_PRIVATE);
        ID_salvo_dia_inicio_II = sharedPref_dia_inicio_II.getInt("dia_inicio_balao_2",16);
        //if(ID_salvo_dia_inicio_II != -1)
            // set the value of the spinner
            spn_dia_inicio_II.setSelection(ID_salvo_dia_inicio_II);

        //Leitura spn_dia_inicio_II
        spn_dia_inicio_II.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id1) {

                //Save do ID da spinner no sharedPreferences
                dia_inicio_II = spn_dia_inicio_II.getSelectedItemPosition();
                SharedPreferences sharedPref_dia_inicio_II = getSharedPreferences("pref_bimestre",0);
                SharedPreferences.Editor prefEditor = sharedPref_dia_inicio_II.edit();
                prefEditor.putInt("dia_inicio_balao_2",dia_inicio_II);
                prefEditor.commit();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        //==========================================================================================

        //load do ID da spinner do arquivo sharedPreferences
        SharedPreferences sharedPref_dia_termino_II = getSharedPreferences("pref_bimestre",MODE_PRIVATE);
        ID_salvo_dia_termino_II = sharedPref_dia_termino_II.getInt("dia_termino_balao_2",16);
        //if(ID_salvo_dia_termino_II != -1)
            // set the value of the spinner
            spn_dia_termino_II.setSelection(ID_salvo_dia_termino_II);

        //Leitura spn_dia_termino_II
        spn_dia_termino_II.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id1) {

                //Save do ID da spinner no sharedPreferences
                dia_termino_II = spn_dia_termino_II.getSelectedItemPosition();
                SharedPreferences sharedPref_dia_termino_II = getSharedPreferences("pref_bimestre",0);
                SharedPreferences.Editor prefEditor = sharedPref_dia_termino_II.edit();
                prefEditor.putInt("dia_termino_balao_2",dia_termino_II);
                prefEditor.commit();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        //==========================================================================================

        //load do ID da spinner do arquivo sharedPreferences
        SharedPreferences sharedPref_mes_inicio_II = getSharedPreferences("pref_bimestre",MODE_PRIVATE);
        ID_salvo_mes_inicio_II = sharedPref_mes_inicio_II.getInt("mes_inicio_balao_2",3);
        //if(ID_salvo_mes_inicio_II != -1)
            // set the value of the spinner
            spn_mes_inicio_II.setSelection(ID_salvo_mes_inicio_II);

        //Leitura spn_mes_inicio_II
        spn_mes_inicio_II.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id1) {

                //Save do ID da spinner no sharedPreferences
                mes_inicio_II = spn_mes_inicio_II.getSelectedItemPosition();
                SharedPreferences sharedPref_mes_inicio_II = getSharedPreferences("pref_bimestre",0);
                SharedPreferences.Editor prefEditor = sharedPref_mes_inicio_II.edit();
                prefEditor.putInt("mes_inicio_balao_2",mes_inicio_II);
                prefEditor.commit();

                if (mes_inicio_II < mes_termino_I ){

                    String mensagem = "O mês de início do 2º bimestre está errado. Configure-o novamente.";
                    AlertDialog.Builder dlg2 = new AlertDialog.Builder(Config_bimestre_activity_Antiga.this);
                    dlg2.setTitle("Erro");
                    dlg2.setMessage(mensagem);
                    dlg2.setNeutralButton("OK", null);
                    dlg2.show();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        //==========================================================================================

        //load do ID da spinner do arquivo sharedPreferences
        SharedPreferences sharedPref_mes_termino_II = getSharedPreferences("pref_bimestre",MODE_PRIVATE);
        ID_salvo_mes_termino_II = sharedPref_mes_termino_II.getInt("mes_termino_balao_2",5);
        //if(ID_salvo_mes_termino_II != -1)
            // set the value of the spinner
            spn_mes_termino_II.setSelection(ID_salvo_mes_termino_II);

        //Leitura spn_mes_termino_II
        spn_mes_termino_II.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id1) {

                //Save do ID da spinner no sharedPreferences
                mes_termino_II = spn_mes_termino_II.getSelectedItemPosition();
                SharedPreferences sharedPref_mes_termino_II = getSharedPreferences("pref_bimestre",0);
                SharedPreferences.Editor prefEditor = sharedPref_mes_termino_II.edit();
                prefEditor.putInt("mes_termino_balao_2",mes_termino_II);
                prefEditor.commit();

                if (mes_termino_II < mes_inicio_II ){

                    String mensagem = "O mês de término do 2º bimestre está errado. Configure-o novamente.";
                    AlertDialog.Builder dlg2 = new AlertDialog.Builder(Config_bimestre_activity_Antiga.this);
                    dlg2.setTitle("Erro");
                    dlg2.setMessage(mensagem);
                    dlg2.setNeutralButton("OK", null);
                    dlg2.show();
                }

            }

            public int getId(){
                return ID4_II;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        //==========================================================================================

        //======================================SPINNERS DO III BIMESTRE============================
        //spn_dia_inicio_III
        spn_dia_inicio_III = (Spinner) findViewById(R.id.spn_dia_inicio_III);

        ArrayAdapter<String> adp9 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, adp_dia);
        ArrayAdapter<String> spnAdapter9 = adp9;
        spnAdapter9.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_dia_inicio_III.setAdapter(spnAdapter9);

        //spn_dia_termino_III
        spn_dia_termino_III = (Spinner) findViewById(R.id.spn_dia_termino_III);

        ArrayAdapter<String> adp10 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, adp_dia);
        ArrayAdapter<String> spnAdapter10 = adp10;
        spnAdapter10.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_dia_termino_III.setAdapter(spnAdapter10);

        //spn_mes_inicio_III
        spn_mes_inicio_III = (Spinner) findViewById(R.id.spn_mes_inicio_III);

        ArrayAdapter<String> adp11 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, adp_mes);
        ArrayAdapter<String> spnAdapter11 = adp11;
        spnAdapter11.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_mes_inicio_III.setAdapter(spnAdapter11);

        //spn_mes_termino_III
        spn_mes_termino_III = (Spinner) findViewById(R.id.spn_mes_termino_III);

        ArrayAdapter<String> adp12 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, adp_mes);
        ArrayAdapter<String> spnAdapter12 = adp12;
        spnAdapter12.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_mes_termino_III.setAdapter(spnAdapter12);

        //load do ID da spinner do arquivo sharedPreferences
        SharedPreferences sharedPref_dia_inicio_III = getSharedPreferences("pref_bimestre",MODE_PRIVATE);
        ID_salvo_dia_inicio_III = sharedPref_dia_inicio_III.getInt("dia_inicio_balao_3",5);
        //if(ID_salvo_dia_inicio_III != -1)
            // set the value of the spinner
            spn_dia_inicio_III.setSelection(ID_salvo_dia_inicio_III);

        //Leitura spn_dia_inicio_III
        spn_dia_inicio_III.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id1) {

                //Save do ID da spinner no sharedPreferences
                dia_inicio_III = spn_dia_inicio_III.getSelectedItemPosition();
                SharedPreferences sharedPref_dia_inicio_III = getSharedPreferences("pref_bimestre",0);
                SharedPreferences.Editor prefEditor = sharedPref_dia_inicio_III.edit();
                prefEditor.putInt("dia_inicio_balao_3",dia_inicio_III);
                prefEditor.commit();

            }


            //public int getId(){
            //  return ID1_III;
            //}

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        //==========================================================================================

        //load do ID da spinner do arquivo sharedPreferences
        SharedPreferences sharedPref_dia_termino_III = getSharedPreferences("pref_bimestre",MODE_PRIVATE);
        ID_salvo_dia_termino_III = sharedPref_dia_termino_III.getInt("dia_termino_balao_3",5);
        //if(ID_salvo_dia_termino_III != -1)
            // set the value of the spinner
            spn_dia_termino_III.setSelection(ID_salvo_dia_termino_III);

        //Leitura spn_dia_termino_III
        spn_dia_termino_III.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id1) {

                //Save do ID da spinner no sharedPreferences
                dia_termino_III = spn_dia_termino_III.getSelectedItemPosition();
                SharedPreferences sharedPref_dia_termino_III = getSharedPreferences("pref_bimestre",0);
                SharedPreferences.Editor prefEditor = sharedPref_dia_termino_III.edit();
                prefEditor.putInt("dia_termino_balao_3",dia_termino_III);
                prefEditor.commit();


            }

            public int getId(){
                return ID2_III;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        //==========================================================================================

        //load do ID da spinner do arquivo sharedPreferences
        SharedPreferences sharedPref_mes_inicio_III = getSharedPreferences("pref_bimestre",MODE_PRIVATE);
        ID_salvo_mes_inicio_III = sharedPref_mes_inicio_III.getInt("mes_inicio_balao_3",7);
        //if(ID_salvo_mes_inicio_III != -1)
            // set the value of the spinner
            spn_mes_inicio_III.setSelection(ID_salvo_mes_inicio_III);

        //Leitura spn_mes_inicio_III
        spn_mes_inicio_III.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id1) {

                //Save do ID da spinner no sharedPreferences
                mes_inicio_III = spn_mes_inicio_III.getSelectedItemPosition();
                SharedPreferences sharedPref_mes_inicio_III = getSharedPreferences("pref_bimestre",0);
                SharedPreferences.Editor prefEditor = sharedPref_mes_inicio_III.edit();
                prefEditor.putInt("mes_inicio_balao_3",mes_inicio_III);
                prefEditor.commit();

                if (mes_inicio_III < mes_termino_II ){

                    String mensagem = "O mês de início do 3º bimestre está errado. Configure-o novamente.";
                    AlertDialog.Builder dlg2 = new AlertDialog.Builder(Config_bimestre_activity_Antiga.this);
                    dlg2.setTitle("Erro");
                    dlg2.setMessage(mensagem);
                    dlg2.setNeutralButton("OK", null);
                    dlg2.show();
                }

            }

            public int getId(){
                return ID3_III;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        //==========================================================================================

        //load do ID da spinner do arquivo sharedPreferences
        SharedPreferences sharedPref_mes_termino_III = getSharedPreferences("pref_bimestre",MODE_PRIVATE);
        ID_salvo_mes_termino_III = sharedPref_mes_termino_III.getInt("mes_termino_balao_3",9);
        //if(ID_salvo_mes_termino_III != -1)
            // set the value of the spinner
            spn_mes_termino_III.setSelection(ID_salvo_mes_termino_III);

        //Leitura spn_mes_termino_III
        spn_mes_termino_III.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id1) {

                //Save do ID da spinner no sharedPreferences
                mes_termino_III = spn_mes_termino_III.getSelectedItemPosition();
                SharedPreferences sharedPref_mes_termino_III = getSharedPreferences("pref_bimestre",0);
                SharedPreferences.Editor prefEditor = sharedPref_mes_termino_III.edit();
                prefEditor.putInt("mes_termino_balao_3",mes_termino_III);
                prefEditor.commit();

                if (mes_termino_III < mes_inicio_III ){

                    String mensagem = "O mês de término do 3º bimestre está errado. Configure-o novamente.";
                    AlertDialog.Builder dlg3 = new AlertDialog.Builder(Config_bimestre_activity_Antiga.this);
                    dlg3.setTitle("Erro");
                    dlg3.setMessage(mensagem);
                    dlg3.setNeutralButton("OK", null);
                    dlg3.show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        //==========================================================================================

        //======================================SPINNERS DO IV BIMESTRE==============================
        //spn_dia_inicio_IV
        spn_dia_inicio_IV = (Spinner) findViewById(R.id.spn_dia_inicio_IV);

        ArrayAdapter<String> adp13 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, adp_dia);
        ArrayAdapter<String> spnAdapter13 = adp13;
        spnAdapter13.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_dia_inicio_IV.setAdapter(spnAdapter13);

        //spn_dia_termino_IV
        spn_dia_termino_IV = (Spinner) findViewById(R.id.spn_dia_termino_IV);

        ArrayAdapter<String> adp14 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, adp_dia);
        ArrayAdapter<String> spnAdapter14 = adp14;
        spnAdapter14.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_dia_termino_IV.setAdapter(spnAdapter14);

        //spn_mes_inicio_IV
        spn_mes_inicio_IV = (Spinner) findViewById(R.id.spn_mes_inicio_IV);

        ArrayAdapter<String> adp15 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, adp_mes);
        ArrayAdapter<String> spnAdapter15 = adp15;
        spnAdapter15.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_mes_inicio_IV.setAdapter(spnAdapter15);

        //spn_mes_termino_IV
        spn_mes_termino_IV = (Spinner) findViewById(R.id.spn_mes_termino_IV);

        ArrayAdapter<String> adp16 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, adp_mes);
        ArrayAdapter<String> spnAdapter16 = adp16;
        spnAdapter16.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_mes_termino_IV.setAdapter(spnAdapter16);

        //load do ID da spinner do arquivo sharedPreferences
        SharedPreferences sharedPref_dia_inicio_IV = getSharedPreferences("pref_bimestre",MODE_PRIVATE);
        ID_salvo_dia_inicio_IV = sharedPref_dia_inicio_IV.getInt("dia_inicio_balao_4",6);
        //if(ID_salvo_dia_inicio_IV != -1)
            // set the value of the spinner
            spn_dia_inicio_IV.setSelection(ID_salvo_dia_inicio_IV);


        //Leitura spn_dia_inicio_IV
        spn_dia_inicio_IV.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id1) {

                //Save do ID da spinner no sharedPreferences
                dia_inicio_IV = spn_dia_inicio_IV.getSelectedItemPosition();
                SharedPreferences sharedPref_dia_inicio_IV = getSharedPreferences("pref_bimestre",0);
                SharedPreferences.Editor prefEditor = sharedPref_dia_inicio_IV.edit();
                prefEditor.putInt("dia_inicio_balao_4",dia_inicio_IV);
                prefEditor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        //==========================================================================================

        //load do ID da spinner do arquivo sharedPreferences
        SharedPreferences sharedPref_dia_termino_IV = getSharedPreferences("pref_bimestre",MODE_PRIVATE);
        ID_salvo_dia_termino_IV = sharedPref_dia_termino_IV.getInt("dia_termino_balao_4",6);
        //if(ID_salvo_dia_termino_IV != -1)
            // set the value of the spinner
            spn_dia_termino_IV.setSelection(ID_salvo_dia_termino_IV);

        //Leitura spn_dia_termino_IV
        spn_dia_termino_IV.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id1) {

                //Save do ID da spinner no sharedPreferences
                dia_termino_IV = spn_dia_termino_IV.getSelectedItemPosition();
                SharedPreferences sharedPref_dia_termino_IV = getSharedPreferences("pref_bimestre",0);
                SharedPreferences.Editor prefEditor = sharedPref_dia_termino_IV.edit();
                prefEditor.putInt("dia_termino_balao_4",dia_termino_IV);
                prefEditor.commit();
            }

            public int getId(){
                return ID2_IV;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        //==========================================================================================

        //load do ID da spinner do arquivo sharedPreferences
        SharedPreferences sharedPref_mes_inicio_IV = getSharedPreferences("pref_bimestre",MODE_PRIVATE);
        ID_salvo_mes_inicio_IV = sharedPref_mes_inicio_IV.getInt("mes_inicio_balao_4",9);
        //if(ID_salvo_mes_inicio_IV != -1)
            // set the value of the spinner
            spn_mes_inicio_IV.setSelection(ID_salvo_mes_inicio_IV);

        //Leitura spn_mes_inicio_IV
        spn_mes_inicio_IV.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id1) {

                //Save do ID da spinner no sharedPreferences
                mes_inicio_IV = spn_mes_inicio_IV.getSelectedItemPosition();
                SharedPreferences sharedPref_mes_inicio_IV = getSharedPreferences("pref_bimestre",0);
                SharedPreferences.Editor prefEditor = sharedPref_mes_inicio_IV.edit();
                prefEditor.putInt("mes_inicio_balao_4",mes_inicio_IV);
                prefEditor.commit();

                if (mes_inicio_IV < mes_termino_III ){

                    String mensagem = "O mês de início do 4º bimestre está errado. Configure-o novamente.";
                    AlertDialog.Builder dlg2 = new AlertDialog.Builder(Config_bimestre_activity_Antiga.this);
                    dlg2.setTitle("Erro");
                    dlg2.setMessage(mensagem);
                    dlg2.setNeutralButton("OK", null);
                    dlg2.show();
                }

            }

            public int getId(){
                return ID3_IV;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        //==========================================================================================

        //load do ID da spinner do arquivo sharedPreferences
        SharedPreferences sharedPref_mes_termino_IV = getSharedPreferences("pref_bimestre",MODE_PRIVATE);
        ID_salvo_mes_termino_IV = sharedPref_mes_termino_IV.getInt("mes_termino_balao_4",11);
        //if(ID_salvo_mes_termino_IV != -1)
            // set the value of the spinner
            spn_mes_termino_IV.setSelection(ID_salvo_mes_termino_IV);

        //Leitura spn_mes_termino_I
        spn_mes_termino_IV.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id1) {

                //Save do ID da spinner no sharedPreferences
                mes_termino_IV = spn_mes_termino_IV.getSelectedItemPosition();
                SharedPreferences sharedPref_mes_termino_IV = getSharedPreferences("pref_bimestre",0);
                SharedPreferences.Editor prefEditor = sharedPref_mes_termino_IV.edit();
                prefEditor.putInt("mes_termino_balao_4",mes_termino_IV);
                prefEditor.commit();

                if (mes_termino_IV < mes_inicio_IV ){

                    String mensagem = "O mês de término do 4º bimestre está errado. Configure-o novamente.";
                    AlertDialog.Builder dlg4 = new AlertDialog.Builder(Config_bimestre_activity_Antiga.this);
                    dlg4.setTitle("Erro");
                    dlg4.setMessage(mensagem);
                    dlg4.setNeutralButton("OK", null);
                    dlg4.show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        //==========================================================================================

    }//End OnCreate

    public void onBackPressed() {

        Intent it = new Intent(this, Config_activity.class);
        startActivity(it);

        return;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                startActivity(new Intent(this, Config_activity.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
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

}
