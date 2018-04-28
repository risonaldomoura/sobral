package projeto.app.sobral.Portugues;

/**
 * Created by Risonald0 on 04/05/2017.
 */

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import projeto.app.sobral.Utils.Main_activity;
import projeto.app.sobral.R;

public class Tab_portugues_oitavo extends Fragment {

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

    public String mes_1 = "JAN";
    public String mes_2 = "FEV";
    public String mes_3 = "MAR";
    public String mes_4 = "ABR";
    public String mes_5 = "MAI";
    public String mes_6 = "JUN";
    public String mes_7 = "JUL";
    public String mes_8 = "AGO";
    public String mes_9 = "SET";
    public String mes_10 = "OUT";
    public String mes_11 = "NOV";
    public String mes_12 = "DEZ";

    public int Dia_sistema;
    public int Mes_sistema;

    public int estadocb1, estadocb2, estadocb3, estadocb4, estadocb5, estadocb6, estadocb7, estadocb8
            ,estadocb9, estadocb10, estadocb11, estadocb12, estadocb13, estadocb14, estadocb15
            , estadocb16, estadocb17, estadocb18, estadocb19, estadocb20, estadocb21, estadocb22
            , estadocb23, estadocb24, estadocb25, estadocb26, estadocb27, estadocb28, estadocb29
            , estadocb30;

    CheckBox cb1, cb2, cb3, cb4, cb5, cb6, cb7, cb8, cb9, cb10, cb11, cb12, cb13, cb14, cb15, cb16
            , cb17, cb18, cb19, cb20, cb21, cb22, cb23, cb24, cb25, cb26, cb27, cb28, cb29, cb30;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cont_portugues_oitavo, container, false);

        //=============== CARREGAMENTO DAS DATAS DOS BIMESTRES =====================================

        //=================PRIMEIRO BIMESTRE========================================================

        //DIA INÍCIO
        SharedPreferences sharedPref_dia_inicio_I = this.getActivity().getSharedPreferences("pref_bimestre", Context.MODE_PRIVATE);
        ID_salvo_dia_inicio_I = sharedPref_dia_inicio_I.getInt("dia_inicio_I", 15);

        //DIA TÉRMINO
        SharedPreferences sharedPref_dia_termino_I = this.getActivity().getSharedPreferences("pref_bimestre", Context.MODE_PRIVATE);
        ID_salvo_dia_termino_I = sharedPref_dia_termino_I.getInt("dia_termino_I", 15);

        //MÊS INÍCIO
        SharedPreferences sharedPref_mes_inicio_I = this.getActivity().getSharedPreferences("pref_bimestre", Context.MODE_PRIVATE);
        ID_salvo_mes_inicio_I = sharedPref_mes_inicio_I.getInt("mes_inicio_I", 1);

        //MÊS TÉRMINO
        SharedPreferences sharedPref_mes_termino_I = this.getActivity().getSharedPreferences("pref_bimestre", Context.MODE_PRIVATE);
        ID_salvo_mes_termino_I = sharedPref_mes_termino_I.getInt("mes_termino_I", 3);

        //==========================================================================================

        //=================SEGUNDO BIMESTRE=========================================================

        //DIA INICIO
        SharedPreferences sharedPref_dia_inicio_II = this.getActivity().getSharedPreferences("pref_bimestre", Context.MODE_PRIVATE);
        ID_salvo_dia_inicio_II = sharedPref_dia_inicio_II.getInt("dia_inicio_II", 16);

        //DIA TÉRMINO
        SharedPreferences sharedPref_dia_termino_II = this.getActivity().getSharedPreferences("pref_bimestre", Context.MODE_PRIVATE);
        ID_salvo_dia_termino_II = sharedPref_dia_termino_II.getInt("dia_termino_II", 16);

        //MES INÍCIO
        SharedPreferences sharedPref_mes_inicio_II = this.getActivity().getSharedPreferences("pref_bimestre", Context.MODE_PRIVATE);
        ID_salvo_mes_inicio_II = sharedPref_mes_inicio_II.getInt("mes_inicio_II", 3);

        //MÊS TÉRMINO
        SharedPreferences sharedPref_mes_termino_II = this.getActivity().getSharedPreferences("pref_bimestre", Context.MODE_PRIVATE);
        ID_salvo_mes_termino_II = sharedPref_mes_termino_II.getInt("mes_termino_II", 5);

        //==========================================================================================

        //=================TERCEIRO BIMESTRE========================================================

        //DIA INÍCIO
        SharedPreferences sharedPref_dia_inicio_III = this.getActivity().getSharedPreferences("pref_bimestre", Context.MODE_PRIVATE);
        ID_salvo_dia_inicio_III = sharedPref_dia_inicio_III.getInt("dia_inicio_III", 5);

        //DIA TÉRMINO
        SharedPreferences sharedPref_dia_termino_III = this.getActivity().getSharedPreferences("pref_bimestre", Context.MODE_PRIVATE);
        ID_salvo_dia_termino_III = sharedPref_dia_termino_III.getInt("dia_termino_III", 5);

        //MES INÍCIO
        SharedPreferences sharedPref_mes_inicio_III = this.getActivity().getSharedPreferences("pref_bimestre", Context.MODE_PRIVATE);
        ID_salvo_mes_inicio_III = sharedPref_mes_inicio_III.getInt("mes_inicio_III", 7);

        //MÊS TÉRMINO
        SharedPreferences sharedPref_mes_termino_III = this.getActivity().getSharedPreferences("pref_bimestre", Context.MODE_PRIVATE);
        ID_salvo_mes_termino_III = sharedPref_mes_termino_III.getInt("mes_termino_III", 9);

        //==========================================================================================

        //=================QUARTO BIMESTRE==========================================================

        //DIA INÍCIO
        SharedPreferences sharedPref_dia_inicio_IV = this.getActivity().getSharedPreferences("pref_bimestre", Context.MODE_PRIVATE);
        ID_salvo_dia_inicio_IV = sharedPref_dia_inicio_IV.getInt("dia_inicio_IV", 6);

        //DIA TÉRMINO
        SharedPreferences sharedPref_dia_termino_IV = this.getActivity().getSharedPreferences("pref_bimestre", Context.MODE_PRIVATE);
        ID_salvo_dia_termino_IV = sharedPref_dia_termino_IV.getInt("dia_termino_IV", 6);

        //MES INÍCIO
        SharedPreferences sharedPref_mes_inicio_IV = this.getActivity().getSharedPreferences("pref_bimestre", Context.MODE_PRIVATE);
        ID_salvo_mes_inicio_IV = sharedPref_mes_inicio_IV.getInt("mes_inicio_IV", 9);

        //MÊS TÉRMINO
        SharedPreferences sharedPref_mes_termino_IV = this.getActivity().getSharedPreferences("pref_bimestre", Context.MODE_PRIVATE);
        ID_salvo_mes_termino_IV = sharedPref_mes_termino_IV.getInt("mes_termino_IV", 11);

        //==========================================================================================


        //===================MÉTODO QUE SETA AS DATAS NOS BLOCOS DE BIMESTRES===========================
        // Iniciar os campos buscando no layout do Fragment

        //=========================I BIMESTRE=======================================================

        //DIA INICIO
        TextView dia_inicio_I = (TextView) view.findViewById(R.id.dia_inicio_I);
        dia_inicio_I.setText("" + ID_salvo_dia_inicio_I);

        //DIA TÉRMINO
        TextView dia_termino_I = (TextView) view.findViewById(R.id.dia_termino_I);
        dia_termino_I.setText("" + ID_salvo_dia_termino_I);

        //MES INICIO
        TextView mes_inicio_I = (TextView) view.findViewById(R.id.mes_inicio_I);

        if (ID_salvo_mes_inicio_I == 1)
            mes_inicio_I.setText(mes_1);
        else if (ID_salvo_mes_inicio_I == 2)
            mes_inicio_I.setText(mes_2);
        else if (ID_salvo_mes_inicio_I == 3)
            mes_inicio_I.setText(mes_3);
        else if (ID_salvo_mes_inicio_I == 4)
            mes_inicio_I.setText(mes_4);
        else if (ID_salvo_mes_inicio_I == 5)
            mes_inicio_I.setText(mes_5);
        else if (ID_salvo_mes_inicio_I == 6)
            mes_inicio_I.setText(mes_6);
        else if (ID_salvo_mes_inicio_I == 7)
            mes_inicio_I.setText(mes_7);
        else if (ID_salvo_mes_inicio_I == 8)
            mes_inicio_I.setText(mes_8);
        else if (ID_salvo_mes_inicio_I == 9)
            mes_inicio_I.setText(mes_9);
        else if (ID_salvo_mes_inicio_I == 10)
            mes_inicio_I.setText(mes_10);
        else if (ID_salvo_mes_inicio_I == 11)
            mes_inicio_I.setText(mes_11);
        else if (ID_salvo_mes_inicio_I == 12)
            mes_inicio_I.setText(mes_12);

        //MES TERMINO
        TextView mes_termino_I = (TextView) view.findViewById(R.id.mes_termino_I);

        if (ID_salvo_mes_termino_I == 1)
            mes_termino_I.setText(mes_1);
        else if (ID_salvo_mes_termino_I == 2)
            mes_termino_I.setText(mes_2);
        else if (ID_salvo_mes_termino_I == 3)
            mes_termino_I.setText(mes_3);
        else if (ID_salvo_mes_termino_I == 4)
            mes_termino_I.setText(mes_4);
        else if (ID_salvo_mes_termino_I == 5)
            mes_termino_I.setText(mes_5);
        else if (ID_salvo_mes_termino_I == 6)
            mes_termino_I.setText(mes_6);
        else if (ID_salvo_mes_termino_I == 7)
            mes_termino_I.setText(mes_7);
        else if (ID_salvo_mes_termino_I == 8)
            mes_termino_I.setText(mes_8);
        else if (ID_salvo_mes_termino_I == 9)
            mes_termino_I.setText(mes_9);
        else if (ID_salvo_mes_termino_I == 10)
            mes_termino_I.setText(mes_10);
        else if (ID_salvo_mes_termino_I == 11)
            mes_termino_I.setText(mes_11);
        else if (ID_salvo_mes_termino_I == 12)
            mes_termino_I.setText(mes_12);

        //==========================================================================================

        //=========================II BIMESTRE======================================================

        //DIA INICIO
        TextView dia_inicio_II = (TextView) view.findViewById(R.id.dia_inicio_II);
        dia_inicio_II.setText("" + ID_salvo_dia_inicio_II);

        //DIA TÉRMINO
        TextView dia_termino_II = (TextView) view.findViewById(R.id.dia_termino_II);
        dia_termino_II.setText("" + ID_salvo_dia_termino_II);

        //MES INICIO
        TextView mes_inicio_II = (TextView) view.findViewById(R.id.mes_inicio_II);

        if (ID_salvo_mes_inicio_II == 1)
            mes_inicio_II.setText(mes_1);
        else if (ID_salvo_mes_inicio_II == 2)
            mes_inicio_II.setText(mes_2);
        else if (ID_salvo_mes_inicio_II == 3)
            mes_inicio_II.setText(mes_3);
        else if (ID_salvo_mes_inicio_II == 4)
            mes_inicio_II.setText(mes_4);
        else if (ID_salvo_mes_inicio_II == 5)
            mes_inicio_II.setText(mes_5);
        else if (ID_salvo_mes_inicio_II == 6)
            mes_inicio_II.setText(mes_6);
        else if (ID_salvo_mes_inicio_II == 7)
            mes_inicio_II.setText(mes_7);
        else if (ID_salvo_mes_inicio_II == 8)
            mes_inicio_II.setText(mes_8);
        else if (ID_salvo_mes_inicio_II == 9)
            mes_inicio_II.setText(mes_9);
        else if (ID_salvo_mes_inicio_II == 10)
            mes_inicio_II.setText(mes_10);
        else if (ID_salvo_mes_inicio_II == 11)
            mes_inicio_II.setText(mes_11);
        else if (ID_salvo_mes_inicio_II == 12)
            mes_inicio_II.setText(mes_12);

        //MES TERMINO
        TextView mes_termino_II = (TextView) view.findViewById(R.id.mes_termino_II);

        if (ID_salvo_mes_termino_II == 1)
            mes_termino_II.setText(mes_1);
        else if (ID_salvo_mes_termino_II == 2)
            mes_termino_II.setText(mes_2);
        else if (ID_salvo_mes_termino_II == 3)
            mes_termino_II.setText(mes_3);
        else if (ID_salvo_mes_termino_II == 4)
            mes_termino_II.setText(mes_4);
        else if (ID_salvo_mes_termino_II == 5)
            mes_termino_II.setText(mes_5);
        else if (ID_salvo_mes_termino_II == 6)
            mes_termino_II.setText(mes_6);
        else if (ID_salvo_mes_termino_II == 7)
            mes_termino_II.setText(mes_7);
        else if (ID_salvo_mes_termino_II == 8)
            mes_termino_II.setText(mes_8);
        else if (ID_salvo_mes_termino_II == 9)
            mes_termino_II.setText(mes_9);
        else if (ID_salvo_mes_termino_II == 10)
            mes_termino_II.setText(mes_10);
        else if (ID_salvo_mes_termino_II == 11)
            mes_termino_II.setText(mes_11);
        else if (ID_salvo_mes_termino_II == 12)
            mes_termino_II.setText(mes_12);

        //==========================================================================================

        //=========================III BIMESTRE=====================================================

        //DIA INICIO
        TextView dia_inicio_III = (TextView) view.findViewById(R.id.dia_inicio_III);
        dia_inicio_III.setText("" + ID_salvo_dia_inicio_III);

        //DIA TÉRMINO
        TextView dia_termino_III = (TextView) view.findViewById(R.id.dia_termino_III);
        dia_termino_III.setText("" + ID_salvo_dia_termino_III);

        //MES INICIO
        TextView mes_inicio_III = (TextView) view.findViewById(R.id.mes_inicio_III);

        if (ID_salvo_mes_inicio_III == 1)
            mes_inicio_III.setText(mes_1);
        else if (ID_salvo_mes_inicio_III == 2)
            mes_inicio_III.setText(mes_2);
        else if (ID_salvo_mes_inicio_III == 3)
            mes_inicio_III.setText(mes_3);
        else if (ID_salvo_mes_inicio_III == 4)
            mes_inicio_III.setText(mes_4);
        else if (ID_salvo_mes_inicio_III == 5)
            mes_inicio_III.setText(mes_5);
        else if (ID_salvo_mes_inicio_III == 6)
            mes_inicio_III.setText(mes_6);
        else if (ID_salvo_mes_inicio_III == 7)
            mes_inicio_III.setText(mes_7);
        else if (ID_salvo_mes_inicio_III == 8)
            mes_inicio_III.setText(mes_8);
        else if (ID_salvo_mes_inicio_III == 9)
            mes_inicio_III.setText(mes_9);
        else if (ID_salvo_mes_inicio_III == 10)
            mes_inicio_III.setText(mes_10);
        else if (ID_salvo_mes_inicio_III == 11)
            mes_inicio_III.setText(mes_11);
        else if (ID_salvo_mes_inicio_III == 12)
            mes_inicio_III.setText(mes_12);

        //MES TERMINO
        TextView mes_termino_III = (TextView) view.findViewById(R.id.mes_termino_III);

        if (ID_salvo_mes_termino_III == 1)
            mes_termino_III.setText(mes_1);
        else if (ID_salvo_mes_termino_III == 2)
            mes_termino_III.setText(mes_2);
        else if (ID_salvo_mes_termino_III == 3)
            mes_termino_III.setText(mes_3);
        else if (ID_salvo_mes_termino_III == 4)
            mes_termino_III.setText(mes_4);
        else if (ID_salvo_mes_termino_III == 5)
            mes_termino_III.setText(mes_5);
        else if (ID_salvo_mes_termino_III == 6)
            mes_termino_III.setText(mes_6);
        else if (ID_salvo_mes_termino_III == 7)
            mes_termino_III.setText(mes_7);
        else if (ID_salvo_mes_termino_III == 8)
            mes_termino_III.setText(mes_8);
        else if (ID_salvo_mes_termino_III == 9)
            mes_termino_III.setText(mes_9);
        else if (ID_salvo_mes_termino_III == 10)
            mes_termino_III.setText(mes_10);
        else if (ID_salvo_mes_termino_III == 11)
            mes_termino_III.setText(mes_11);
        else if (ID_salvo_mes_termino_III == 12)
            mes_termino_III.setText(mes_12);

        //==========================================================================================

        //=========================IV BIMESTRE======================================================

        //DIA INICIO
        TextView dia_inicio_IV = (TextView) view.findViewById(R.id.dia_inicio_IV);
        dia_inicio_IV.setText("" + ID_salvo_dia_inicio_IV);

        //DIA TÉRMINO
        TextView dia_termino_IV = (TextView) view.findViewById(R.id.dia_termino_IV);
        dia_termino_IV.setText("" + ID_salvo_dia_termino_IV);

        //MES INICIO
        TextView mes_inicio_IV = (TextView) view.findViewById(R.id.mes_inicio_IV);

        if (ID_salvo_mes_inicio_IV == 1)
            mes_inicio_IV.setText(mes_1);
        else if (ID_salvo_mes_inicio_IV == 2)
            mes_inicio_IV.setText(mes_2);
        else if (ID_salvo_mes_inicio_IV == 3)
            mes_inicio_IV.setText(mes_3);
        else if (ID_salvo_mes_inicio_IV == 4)
            mes_inicio_IV.setText(mes_4);
        else if (ID_salvo_mes_inicio_IV == 5)
            mes_inicio_IV.setText(mes_5);
        else if (ID_salvo_mes_inicio_IV == 6)
            mes_inicio_IV.setText(mes_6);
        else if (ID_salvo_mes_inicio_IV == 7)
            mes_inicio_IV.setText(mes_7);
        else if (ID_salvo_mes_inicio_IV == 8)
            mes_inicio_IV.setText(mes_8);
        else if (ID_salvo_mes_inicio_IV == 9)
            mes_inicio_IV.setText(mes_9);
        else if (ID_salvo_mes_inicio_IV == 10)
            mes_inicio_IV.setText(mes_10);
        else if (ID_salvo_mes_inicio_IV == 11)
            mes_inicio_IV.setText(mes_11);
        else if (ID_salvo_mes_inicio_IV == 12)
            mes_inicio_IV.setText(mes_12);

        //MES TERMINO
        TextView mes_termino_IV = (TextView) view.findViewById(R.id.mes_termino_IV);

        if (ID_salvo_mes_termino_IV == 1)
            mes_termino_IV.setText(mes_1);
        else if (ID_salvo_mes_termino_IV == 2)
            mes_termino_IV.setText(mes_2);
        else if (ID_salvo_mes_termino_IV == 3)
            mes_termino_IV.setText(mes_3);
        else if (ID_salvo_mes_termino_IV == 4)
            mes_termino_IV.setText(mes_4);
        else if (ID_salvo_mes_termino_IV == 5)
            mes_termino_IV.setText(mes_5);
        else if (ID_salvo_mes_termino_IV == 6)
            mes_termino_IV.setText(mes_6);
        else if (ID_salvo_mes_termino_IV == 7)
            mes_termino_IV.setText(mes_7);
        else if (ID_salvo_mes_termino_IV == 8)
            mes_termino_IV.setText(mes_8);
        else if (ID_salvo_mes_termino_IV == 9)
            mes_termino_IV.setText(mes_9);
        else if (ID_salvo_mes_termino_IV == 10)
            mes_termino_IV.setText(mes_10);
        else if (ID_salvo_mes_termino_IV == 11)
            mes_termino_IV.setText(mes_11);
        else if (ID_salvo_mes_termino_IV == 12)
            mes_termino_IV.setText(mes_12);

        //==========================================================================================
        //FIM DO MÉTODO DE DATAS DOS BIMESTRES


        //======================TRATAMENTO DO CHECKBOX 1============================================

        cb1 = (CheckBox) view.findViewById(R.id.cb1);

        SharedPreferences sharedPref_estadocb1 = this.getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo", Context.MODE_PRIVATE);
        estadocb1 = sharedPref_estadocb1.getInt("estadocb1", 0);

        if (estadocb1 == 0)
            cb1.setChecked(false);
        else
            cb1.setChecked(true);

        cb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {

                if (cb1.isChecked()){
                    String mensagem_marcar = getString(R.string.confirmacao_marcar);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(mensagem_marcar);

                    builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(getActivity(), Main_activity.class);
                            startActivity(intent);

                        }
                    });
                    builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            cb1.setChecked(true);

                            //estadocb1 = 1;
                            SharedPreferences sharedPref_estadocb1 = getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo",0);
                            SharedPreferences.Editor prefEditor = sharedPref_estadocb1.edit();
                            prefEditor.putInt("estadocb1",1);
                            prefEditor.commit();

                            Toast.makeText(getActivity().getApplicationContext(), "Salvo", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.show();
                }
                else {
                    String mensagem_desmarcar = getString(R.string.confirmacao_desmarcar);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(mensagem_desmarcar);

                    builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(getActivity(), Main_activity.class);
                            startActivity(intent);
                        }
                    });
                    builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            cb1.setChecked(false);

                            SharedPreferences sharedPref_estadocb1 = getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo", 0);
                            SharedPreferences.Editor prefEditor = sharedPref_estadocb1.edit();
                            prefEditor.putInt("estadocb1", 0);
                            prefEditor.commit();

                            Toast.makeText(getActivity().getApplicationContext(), "Salvo", Toast.LENGTH_SHORT).show();

                        }
                    });
                    builder.show();
                }
            }
        });


        //===================================FIM CHECKBOX 1=========================================

        //======================TRATAMENTO DO CHECKBOX 2============================================

        cb2 = (CheckBox) view.findViewById(R.id.cb2);

        SharedPreferences sharedPref_estadocb2 = this.getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo", Context.MODE_PRIVATE);
        estadocb2 = sharedPref_estadocb2.getInt("estadocb2", 0);

        if (estadocb2 == 0)
            cb2.setChecked(false);
        else
            cb2.setChecked(true);

        cb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {

                if (cb2.isChecked()){
                    String mensagem_marcar = getString(R.string.confirmacao_marcar);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(mensagem_marcar);
                    builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(getActivity(), Main_activity.class);
                            startActivity(intent);
                        }
                    });
                    builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            cb2.setChecked(true);

                            //estadocb2 = 1;
                            SharedPreferences sharedPref_estadocb2 = getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo",0);
                            SharedPreferences.Editor prefEditor = sharedPref_estadocb2.edit();
                            prefEditor.putInt("estadocb2",1);
                            prefEditor.commit();

                            Toast.makeText(getActivity().getApplicationContext(), "Salvo", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.show();
                }
                else {
                    String mensagem_desmarcar = getString(R.string.confirmacao_desmarcar);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(mensagem_desmarcar);
                    builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(getActivity(), Main_activity.class);
                            startActivity(intent);
                        }
                    });
                    builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            cb2.setChecked(false);

                            SharedPreferences sharedPref_estadocb2 = getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo", 0);
                            SharedPreferences.Editor prefEditor = sharedPref_estadocb2.edit();
                            prefEditor.putInt("estadocb2", 0);
                            prefEditor.commit();

                            Toast.makeText(getActivity().getApplicationContext(), "Salvo", Toast.LENGTH_SHORT).show();

                        }
                    });
                    builder.show();
                }
            }
        });
        //===================================FIM CHECKBOX 2=========================================


        //======================TRATAMENTO DO CHECKBOX 3============================================

        cb3 = (CheckBox) view.findViewById(R.id.cb3);

        SharedPreferences sharedPref_estadocb3 = this.getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo", Context.MODE_PRIVATE);
        estadocb3 = sharedPref_estadocb3.getInt("estadocb3", 0);

        if (estadocb3 == 0)
            cb3.setChecked(false);
        else
            cb3.setChecked(true);

        cb3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {

                if (cb3.isChecked()){
                    String mensagem_marcar = getString(R.string.confirmacao_marcar);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(mensagem_marcar);
                    builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(getActivity(), Main_activity.class);
                            startActivity(intent);
                        }
                    });
                    builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            cb3.setChecked(true);

                            //estadocb3 = 1;
                            SharedPreferences sharedPref_estadocb3 = getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo",0);
                            SharedPreferences.Editor prefEditor = sharedPref_estadocb3.edit();
                            prefEditor.putInt("estadocb3",1);
                            prefEditor.commit();

                            Toast.makeText(getActivity().getApplicationContext(), "Salvo", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.show();
                }
                else {
                    String mensagem_desmarcar = getString(R.string.confirmacao_desmarcar);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(mensagem_desmarcar);
                    builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(getActivity(), Main_activity.class);
                            startActivity(intent);
                        }
                    });
                    builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            cb3.setChecked(false);

                            SharedPreferences sharedPref_estadocb3 = getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo", 0);
                            SharedPreferences.Editor prefEditor = sharedPref_estadocb3.edit();
                            prefEditor.putInt("estadocb3", 0);
                            prefEditor.commit();

                            Toast.makeText(getActivity().getApplicationContext(), "Salvo", Toast.LENGTH_SHORT).show();

                        }
                    });
                    builder.show();
                }
            }
        });
        //===================================FIM CHECKBOX 3=========================================

        //======================TRATAMENTO DO CHECKBOX 4============================================

        cb4 = (CheckBox) view.findViewById(R.id.cb4);

        SharedPreferences sharedPref_estadocb4 = this.getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo", Context.MODE_PRIVATE);
        estadocb4 = sharedPref_estadocb4.getInt("estadocb4", 0);

        if (estadocb4 == 0)
            cb4.setChecked(false);
        else
            cb4.setChecked(true);

        cb4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {

                if (cb4.isChecked()){
                    String mensagem_marcar = getString(R.string.confirmacao_marcar);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(mensagem_marcar);
                    builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(getActivity(), Main_activity.class);
                            startActivity(intent);
                        }
                    });
                    builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            cb4.setChecked(true);

                            //estadocb4 = 1;
                            SharedPreferences sharedPref_estadocb4 = getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo",0);
                            SharedPreferences.Editor prefEditor = sharedPref_estadocb4.edit();
                            prefEditor.putInt("estadocb4",1);
                            prefEditor.commit();

                            Toast.makeText(getActivity().getApplicationContext(), "Salvo", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.show();
                }
                else {
                    String mensagem_desmarcar = getString(R.string.confirmacao_desmarcar);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(mensagem_desmarcar);
                    builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(getActivity(), Main_activity.class);
                            startActivity(intent);
                        }
                    });
                    builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            cb4.setChecked(false);

                            SharedPreferences sharedPref_estadocb4 = getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo", 0);
                            SharedPreferences.Editor prefEditor = sharedPref_estadocb4.edit();
                            prefEditor.putInt("estadocb4", 0);
                            prefEditor.commit();

                            Toast.makeText(getActivity().getApplicationContext(), "Salvo", Toast.LENGTH_SHORT).show();

                        }
                    });
                    builder.show();
                }
            }
        });
        //===================================FIM CHECKBOX 4=========================================


        //======================TRATAMENTO DO CHECKBOX 5============================================

        cb5 = (CheckBox) view.findViewById(R.id.cb5);

        SharedPreferences sharedPref_estadocb5 = this.getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo", Context.MODE_PRIVATE);
        estadocb5 = sharedPref_estadocb5.getInt("estadocb5", 0);

        if (estadocb5 == 0)
            cb5.setChecked(false);
        else
            cb5.setChecked(true);

        cb5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {

                if (cb5.isChecked()){
                    String mensagem_marcar = getString(R.string.confirmacao_marcar);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(mensagem_marcar);
                    builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(getActivity(), Main_activity.class);
                            startActivity(intent);
                        }
                    });
                    builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            cb5.setChecked(true);

                            //estadocb5 = 1;
                            SharedPreferences sharedPref_estadocb5 = getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo",0);
                            SharedPreferences.Editor prefEditor = sharedPref_estadocb5.edit();
                            prefEditor.putInt("estadocb5",1);
                            prefEditor.commit();

                            Toast.makeText(getActivity().getApplicationContext(), "Salvo", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.show();
                }
                else {
                    String mensagem_desmarcar = getString(R.string.confirmacao_desmarcar);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(mensagem_desmarcar);
                    builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(getActivity(), Main_activity.class);
                            startActivity(intent);
                        }
                    });
                    builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            cb5.setChecked(false);

                            SharedPreferences sharedPref_estadocb5 = getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo", 0);
                            SharedPreferences.Editor prefEditor = sharedPref_estadocb5.edit();
                            prefEditor.putInt("estadocb5", 0);
                            prefEditor.commit();

                            Toast.makeText(getActivity().getApplicationContext(), "Salvo", Toast.LENGTH_SHORT).show();

                        }
                    });
                    builder.show();
                }
            }
        });
        //===================================FIM CHECKBOX 5=========================================

        //======================TRATAMENTO DO CHECKBOX 6============================================

        cb6 = (CheckBox) view.findViewById(R.id.cb6);

        SharedPreferences sharedPref_estadocb6 = this.getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo", Context.MODE_PRIVATE);
        estadocb6 = sharedPref_estadocb6.getInt("estadocb6", 0);

        if (estadocb6 == 0)
            cb6.setChecked(false);
        else
            cb6.setChecked(true);

        cb6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {

                if (cb6.isChecked()){
                    String mensagem_marcar = getString(R.string.confirmacao_marcar);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(mensagem_marcar);
                    builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(getActivity(), Main_activity.class);
                            startActivity(intent);
                        }
                    });
                    builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            cb6.setChecked(true);

                            //estadocb6 = 1;
                            SharedPreferences sharedPref_estadocb6 = getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo",0);
                            SharedPreferences.Editor prefEditor = sharedPref_estadocb6.edit();
                            prefEditor.putInt("estadocb6",1);
                            prefEditor.commit();

                            Toast.makeText(getActivity().getApplicationContext(), "Salvo", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.show();
                }
                else {
                    String mensagem_desmarcar = getString(R.string.confirmacao_desmarcar);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(mensagem_desmarcar);
                    builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(getActivity(), Main_activity.class);
                            startActivity(intent);
                        }
                    });
                    builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            cb6.setChecked(false);

                            SharedPreferences sharedPref_estadocb6 = getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo", 0);
                            SharedPreferences.Editor prefEditor = sharedPref_estadocb6.edit();
                            prefEditor.putInt("estadocb6", 0);
                            prefEditor.commit();

                            Toast.makeText(getActivity().getApplicationContext(), "Salvo", Toast.LENGTH_SHORT).show();

                        }
                    });
                    builder.show();
                }
            }
        });
        //===================================FIM CHECKBOX 6=========================================


        //======================TRATAMENTO DO CHECKBOX 7============================================

        cb7 = (CheckBox) view.findViewById(R.id.cb7);

        SharedPreferences sharedPref_estadocb7 = this.getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo", Context.MODE_PRIVATE);
        estadocb7 = sharedPref_estadocb7.getInt("estadocb7", 0);

        if (estadocb7 == 0)
            cb7.setChecked(false);
        else
            cb7.setChecked(true);

        cb7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {

                if (cb7.isChecked()){
                    String mensagem_marcar = getString(R.string.confirmacao_marcar);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(mensagem_marcar);
                    builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(getActivity(), Main_activity.class);
                            startActivity(intent);
                        }
                    });
                    builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            cb7.setChecked(true);

                            //estadocb7 = 1;
                            SharedPreferences sharedPref_estadocb7 = getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo",0);
                            SharedPreferences.Editor prefEditor = sharedPref_estadocb7.edit();
                            prefEditor.putInt("estadocb7",1);
                            prefEditor.commit();

                            Toast.makeText(getActivity().getApplicationContext(), "Salvo", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.show();
                }
                else {
                    String mensagem_desmarcar = getString(R.string.confirmacao_desmarcar);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(mensagem_desmarcar);
                    builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(getActivity(), Main_activity.class);
                            startActivity(intent);
                        }
                    });
                    builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            cb7.setChecked(false);

                            SharedPreferences sharedPref_estadocb7 = getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo", 0);
                            SharedPreferences.Editor prefEditor = sharedPref_estadocb7.edit();
                            prefEditor.putInt("estadocb7", 0);
                            prefEditor.commit();

                            Toast.makeText(getActivity().getApplicationContext(), "Salvo", Toast.LENGTH_SHORT).show();

                        }
                    });
                    builder.show();
                }
            }
        });
        //===================================FIM CHECKBOX 7=========================================


        //======================TRATAMENTO DO CHECKBOX 8============================================

        cb8 = (CheckBox) view.findViewById(R.id.cb8);

        SharedPreferences sharedPref_estadocb8 = this.getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo", Context.MODE_PRIVATE);
        estadocb8 = sharedPref_estadocb8.getInt("estadocb8", 0);

        if (estadocb8 == 0)
            cb8.setChecked(false);
        else
            cb8.setChecked(true);

        cb8.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {

                if (cb8.isChecked()){
                    String mensagem_marcar = getString(R.string.confirmacao_marcar);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(mensagem_marcar);
                    builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(getActivity(), Main_activity.class);
                            startActivity(intent);
                        }
                    });
                    builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            cb8.setChecked(true);

                            //estadocb8 = 1;
                            SharedPreferences sharedPref_estadocb8 = getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo",0);
                            SharedPreferences.Editor prefEditor = sharedPref_estadocb8.edit();
                            prefEditor.putInt("estadocb8",1);
                            prefEditor.commit();

                            Toast.makeText(getActivity().getApplicationContext(), "Salvo", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.show();
                }
                else {
                    String mensagem_desmarcar = getString(R.string.confirmacao_desmarcar);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(mensagem_desmarcar);
                    builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(getActivity(), Main_activity.class);
                            startActivity(intent);
                        }
                    });
                    builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            cb8.setChecked(false);

                            SharedPreferences sharedPref_estadocb8 = getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo", 0);
                            SharedPreferences.Editor prefEditor = sharedPref_estadocb8.edit();
                            prefEditor.putInt("estadocb8", 0);
                            prefEditor.commit();

                            Toast.makeText(getActivity().getApplicationContext(), "Salvo", Toast.LENGTH_SHORT).show();

                        }
                    });
                    builder.show();
                }
            }
        });
        //===================================FIM CHECKBOX 8=========================================

        //======================TRATAMENTO DO CHECKBOX 9============================================

        cb9 = (CheckBox) view.findViewById(R.id.cb9);

        SharedPreferences sharedPref_estadocb9 = this.getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo", Context.MODE_PRIVATE);
        estadocb9 = sharedPref_estadocb9.getInt("estadocb9", 0);

        if (estadocb9 == 0)
            cb9.setChecked(false);
        else
            cb9.setChecked(true);

        cb9.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {

                if (cb9.isChecked()){
                    String mensagem_marcar = getString(R.string.confirmacao_marcar);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(mensagem_marcar);
                    builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(getActivity(), Main_activity.class);
                            startActivity(intent);
                        }
                    });
                    builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            cb9.setChecked(true);

                            //estadocb9 = 1;
                            SharedPreferences sharedPref_estadocb9 = getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo",0);
                            SharedPreferences.Editor prefEditor = sharedPref_estadocb9.edit();
                            prefEditor.putInt("estadocb9",1);
                            prefEditor.commit();

                            Toast.makeText(getActivity().getApplicationContext(), "Salvo", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.show();
                }
                else {
                    String mensagem_desmarcar = getString(R.string.confirmacao_desmarcar);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(mensagem_desmarcar);
                    builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(getActivity(), Main_activity.class);
                            startActivity(intent);
                        }
                    });
                    builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            cb9.setChecked(false);

                            SharedPreferences sharedPref_estadocb9 = getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo", 0);
                            SharedPreferences.Editor prefEditor = sharedPref_estadocb9.edit();
                            prefEditor.putInt("estadocb9", 0);
                            prefEditor.commit();

                            Toast.makeText(getActivity().getApplicationContext(), "Salvo", Toast.LENGTH_SHORT).show();

                        }
                    });
                    builder.show();
                }
            }
        });
        //===================================FIM CHECKBOX 9=========================================

        //======================TRATAMENTO DO CHECKBOX 10===========================================

        cb10 = (CheckBox) view.findViewById(R.id.cb10);

        SharedPreferences sharedPref_estadocb10 = this.getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo", Context.MODE_PRIVATE);
        estadocb10 = sharedPref_estadocb10.getInt("estadocb10", 0);

        if (estadocb10 == 0)
            cb10.setChecked(false);
        else
            cb10.setChecked(true);

        cb10.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {

                if (cb10.isChecked()){
                    String mensagem_marcar = getString(R.string.confirmacao_marcar);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(mensagem_marcar);
                    builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(getActivity(), Main_activity.class);
                            startActivity(intent);
                        }
                    });
                    builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            cb10.setChecked(true);

                            //estadocb10 = 1;
                            SharedPreferences sharedPref_estadocb10 = getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo",0);
                            SharedPreferences.Editor prefEditor = sharedPref_estadocb10.edit();
                            prefEditor.putInt("estadocb10",1);
                            prefEditor.commit();

                            Toast.makeText(getActivity().getApplicationContext(), "Salvo", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.show();
                }
                else {
                    String mensagem_desmarcar = getString(R.string.confirmacao_desmarcar);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(mensagem_desmarcar);
                    builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(getActivity(), Main_activity.class);
                            startActivity(intent);
                        }
                    });
                    builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            cb10.setChecked(false);

                            SharedPreferences sharedPref_estadocb10 = getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo", 0);
                            SharedPreferences.Editor prefEditor = sharedPref_estadocb10.edit();
                            prefEditor.putInt("estadocb10", 0);
                            prefEditor.commit();

                            Toast.makeText(getActivity().getApplicationContext(), "Salvo", Toast.LENGTH_SHORT).show();

                        }
                    });
                    builder.show();
                }
            }
        });
        //===================================FIM CHECKBOX 10========================================

        //======================TRATAMENTO DO CHECKBOX 11============================================

        cb11 = (CheckBox) view.findViewById(R.id.cb11);

        SharedPreferences sharedPref_estadocb11 = this.getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo", Context.MODE_PRIVATE);
        estadocb11 = sharedPref_estadocb11.getInt("estadocb11", 0);

        if (estadocb11 == 0)
            cb11.setChecked(false);
        else
            cb11.setChecked(true);

        cb11.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {

                if (cb11.isChecked()){
                    String mensagem_marcar = getString(R.string.confirmacao_marcar);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(mensagem_marcar);

                    builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(getActivity(), Main_activity.class);
                            startActivity(intent);

                        }
                    });
                    builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            cb11.setChecked(true);

                            //estadocb11 = 1;
                            SharedPreferences sharedPref_estadocb11 = getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo",0);
                            SharedPreferences.Editor prefEditor = sharedPref_estadocb11.edit();
                            prefEditor.putInt("estadocb11",1);
                            prefEditor.commit();

                            Toast.makeText(getActivity().getApplicationContext(), "Salvo", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.show();
                }
                else {
                    String mensagem_desmarcar = getString(R.string.confirmacao_desmarcar);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(mensagem_desmarcar);

                    builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(getActivity(), Main_activity.class);
                            startActivity(intent);
                        }
                    });
                    builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            cb11.setChecked(false);

                            SharedPreferences sharedPref_estadocb11 = getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo", 0);
                            SharedPreferences.Editor prefEditor = sharedPref_estadocb11.edit();
                            prefEditor.putInt("estadocb11", 0);
                            prefEditor.commit();

                            Toast.makeText(getActivity().getApplicationContext(), "Salvo", Toast.LENGTH_SHORT).show();

                        }
                    });
                    builder.show();
                }
            }
        });


        //===================================FIM CHECKBOX 11========================================

        //======================TRATAMENTO DO CHECKBOX 12===========================================

        cb12 = (CheckBox) view.findViewById(R.id.cb12);

        SharedPreferences sharedPref_estadocb12 = this.getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo", Context.MODE_PRIVATE);
        estadocb12 = sharedPref_estadocb12.getInt("estadocb12", 0);

        if (estadocb12 == 0)
            cb12.setChecked(false);
        else
            cb12.setChecked(true);

        cb12.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {

                if (cb12.isChecked()){
                    String mensagem_marcar = getString(R.string.confirmacao_marcar);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(mensagem_marcar);

                    builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(getActivity(), Main_activity.class);
                            startActivity(intent);

                        }
                    });
                    builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            cb12.setChecked(true);

                            //estadocb12 = 1;
                            SharedPreferences sharedPref_estadocb12 = getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo",0);
                            SharedPreferences.Editor prefEditor = sharedPref_estadocb12.edit();
                            prefEditor.putInt("estadocb12",1);
                            prefEditor.commit();

                            Toast.makeText(getActivity().getApplicationContext(), "Salvo", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.show();
                }
                else {
                    String mensagem_desmarcar = getString(R.string.confirmacao_desmarcar);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(mensagem_desmarcar);

                    builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(getActivity(), Main_activity.class);
                            startActivity(intent);
                        }
                    });
                    builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            cb12.setChecked(false);

                            SharedPreferences sharedPref_estadocb12 = getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo", 0);
                            SharedPreferences.Editor prefEditor = sharedPref_estadocb12.edit();
                            prefEditor.putInt("estadocb12", 0);
                            prefEditor.commit();

                            Toast.makeText(getActivity().getApplicationContext(), "Salvo", Toast.LENGTH_SHORT).show();

                        }
                    });
                    builder.show();
                }
            }
        });


        //===================================FIM CHECKBOX 12========================================

        //======================TRATAMENTO DO CHECKBOX 13============================================

        cb13 = (CheckBox) view.findViewById(R.id.cb13);

        SharedPreferences sharedPref_estadocb13 = this.getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo", Context.MODE_PRIVATE);
        estadocb13 = sharedPref_estadocb13.getInt("estadocb13", 0);

        if (estadocb13 == 0)
            cb13.setChecked(false);
        else
            cb13.setChecked(true);

        cb13.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {

                if (cb13.isChecked()){
                    String mensagem_marcar = getString(R.string.confirmacao_marcar);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(mensagem_marcar);

                    builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(getActivity(), Main_activity.class);
                            startActivity(intent);

                        }
                    });
                    builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            cb13.setChecked(true);

                            //estadocb13 = 1;
                            SharedPreferences sharedPref_estadocb13 = getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo",0);
                            SharedPreferences.Editor prefEditor = sharedPref_estadocb13.edit();
                            prefEditor.putInt("estadocb13",1);
                            prefEditor.commit();

                            Toast.makeText(getActivity().getApplicationContext(), "Salvo", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.show();
                }
                else {
                    String mensagem_desmarcar = getString(R.string.confirmacao_desmarcar);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(mensagem_desmarcar);

                    builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(getActivity(), Main_activity.class);
                            startActivity(intent);
                        }
                    });
                    builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            cb13.setChecked(false);

                            SharedPreferences sharedPref_estadocb13 = getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo", 0);
                            SharedPreferences.Editor prefEditor = sharedPref_estadocb13.edit();
                            prefEditor.putInt("estadocb13", 0);
                            prefEditor.commit();

                            Toast.makeText(getActivity().getApplicationContext(), "Salvo", Toast.LENGTH_SHORT).show();

                        }
                    });
                    builder.show();
                }
            }
        });


        //===================================FIM CHECKBOX 13========================================

        //======================TRATAMENTO DO CHECKBOX 14===========================================

        cb14 = (CheckBox) view.findViewById(R.id.cb14);

        SharedPreferences sharedPref_estadocb14 = this.getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo", Context.MODE_PRIVATE);
        estadocb14 = sharedPref_estadocb14.getInt("estadocb14", 0);

        if (estadocb14 == 0)
            cb14.setChecked(false);
        else
            cb14.setChecked(true);

        cb14.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {

                if (cb14.isChecked()){
                    String mensagem_marcar = getString(R.string.confirmacao_marcar);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(mensagem_marcar);

                    builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(getActivity(), Main_activity.class);
                            startActivity(intent);

                        }
                    });
                    builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            cb14.setChecked(true);

                            //estadocb14 = 1;
                            SharedPreferences sharedPref_estadocb14 = getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo",0);
                            SharedPreferences.Editor prefEditor = sharedPref_estadocb14.edit();
                            prefEditor.putInt("estadocb14",1);
                            prefEditor.commit();

                            Toast.makeText(getActivity().getApplicationContext(), "Salvo", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.show();
                }
                else {
                    String mensagem_desmarcar = getString(R.string.confirmacao_desmarcar);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(mensagem_desmarcar);

                    builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(getActivity(), Main_activity.class);
                            startActivity(intent);
                        }
                    });
                    builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            cb14.setChecked(false);

                            SharedPreferences sharedPref_estadocb14 = getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo", 0);
                            SharedPreferences.Editor prefEditor = sharedPref_estadocb14.edit();
                            prefEditor.putInt("estadocb14", 0);
                            prefEditor.commit();

                            Toast.makeText(getActivity().getApplicationContext(), "Salvo", Toast.LENGTH_SHORT).show();

                        }
                    });
                    builder.show();
                }
            }
        });


        //===================================FIM CHECKBOX 14========================================

        //======================TRATAMENTO DO CHECKBOX 15============================================

        cb15 = (CheckBox) view.findViewById(R.id.cb15);

        SharedPreferences sharedPref_estadocb15 = this.getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo", Context.MODE_PRIVATE);
        estadocb15 = sharedPref_estadocb15.getInt("estadocb15", 0);

        if (estadocb15 == 0)
            cb15.setChecked(false);
        else
            cb15.setChecked(true);

        cb15.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {

                if (cb15.isChecked()){
                    String mensagem_marcar = getString(R.string.confirmacao_marcar);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(mensagem_marcar);

                    builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(getActivity(), Main_activity.class);
                            startActivity(intent);

                        }
                    });
                    builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            cb15.setChecked(true);

                            //estadocb15 = 1;
                            SharedPreferences sharedPref_estadocb15 = getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo",0);
                            SharedPreferences.Editor prefEditor = sharedPref_estadocb15.edit();
                            prefEditor.putInt("estadocb15",1);
                            prefEditor.commit();

                            Toast.makeText(getActivity().getApplicationContext(), "Salvo", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.show();
                }
                else {
                    String mensagem_desmarcar = getString(R.string.confirmacao_desmarcar);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(mensagem_desmarcar);

                    builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(getActivity(), Main_activity.class);
                            startActivity(intent);
                        }
                    });
                    builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            cb15.setChecked(false);

                            SharedPreferences sharedPref_estadocb15 = getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo", 0);
                            SharedPreferences.Editor prefEditor = sharedPref_estadocb15.edit();
                            prefEditor.putInt("estadocb15", 0);
                            prefEditor.commit();

                            Toast.makeText(getActivity().getApplicationContext(), "Salvo", Toast.LENGTH_SHORT).show();

                        }
                    });
                    builder.show();
                }
            }
        });


        //===================================FIM CHECKBOX 15========================================

        //======================TRATAMENTO DO CHECKBOX 16============================================

        cb16 = (CheckBox) view.findViewById(R.id.cb16);

        SharedPreferences sharedPref_estadocb16 = this.getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo", Context.MODE_PRIVATE);
        estadocb16 = sharedPref_estadocb16.getInt("estadocb16", 0);

        if (estadocb16 == 0)
            cb16.setChecked(false);
        else
            cb16.setChecked(true);

        cb16.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {

                if (cb16.isChecked()){
                    String mensagem_marcar = getString(R.string.confirmacao_marcar);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(mensagem_marcar);

                    builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(getActivity(), Main_activity.class);
                            startActivity(intent);

                        }
                    });
                    builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            cb16.setChecked(true);

                            //estadocb16 = 1;
                            SharedPreferences sharedPref_estadocb16 = getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo",0);
                            SharedPreferences.Editor prefEditor = sharedPref_estadocb16.edit();
                            prefEditor.putInt("estadocb16",1);
                            prefEditor.commit();

                            Toast.makeText(getActivity().getApplicationContext(), "Salvo", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.show();
                }
                else {
                    String mensagem_desmarcar = getString(R.string.confirmacao_desmarcar);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(mensagem_desmarcar);

                    builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(getActivity(), Main_activity.class);
                            startActivity(intent);
                        }
                    });
                    builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            cb16.setChecked(false);

                            SharedPreferences sharedPref_estadocb16 = getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo", 0);
                            SharedPreferences.Editor prefEditor = sharedPref_estadocb16.edit();
                            prefEditor.putInt("estadocb16", 0);
                            prefEditor.commit();

                            Toast.makeText(getActivity().getApplicationContext(), "Salvo", Toast.LENGTH_SHORT).show();

                        }
                    });
                    builder.show();
                }
            }
        });


        //===================================FIM CHECKBOX 16========================================

        //======================TRATAMENTO DO CHECKBOX 17===========================================

        cb17 = (CheckBox) view.findViewById(R.id.cb17);

        SharedPreferences sharedPref_estadocb17 = this.getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo", Context.MODE_PRIVATE);
        estadocb17 = sharedPref_estadocb17.getInt("estadocb17", 0);

        if (estadocb17 == 0)
            cb17.setChecked(false);
        else
            cb17.setChecked(true);

        cb17.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {

                if (cb17.isChecked()){
                    String mensagem_marcar = getString(R.string.confirmacao_marcar);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(mensagem_marcar);

                    builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(getActivity(), Main_activity.class);
                            startActivity(intent);

                        }
                    });
                    builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            cb17.setChecked(true);

                            //estadocb17 = 1;
                            SharedPreferences sharedPref_estadocb17 = getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo",0);
                            SharedPreferences.Editor prefEditor = sharedPref_estadocb17.edit();
                            prefEditor.putInt("estadocb17",1);
                            prefEditor.commit();

                            Toast.makeText(getActivity().getApplicationContext(), "Salvo", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.show();
                }
                else {
                    String mensagem_desmarcar = getString(R.string.confirmacao_desmarcar);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(mensagem_desmarcar);

                    builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(getActivity(), Main_activity.class);
                            startActivity(intent);
                        }
                    });
                    builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            cb17.setChecked(false);

                            SharedPreferences sharedPref_estadocb17 = getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo", 0);
                            SharedPreferences.Editor prefEditor = sharedPref_estadocb17.edit();
                            prefEditor.putInt("estadocb17", 0);
                            prefEditor.commit();

                            Toast.makeText(getActivity().getApplicationContext(), "Salvo", Toast.LENGTH_SHORT).show();

                        }
                    });
                    builder.show();
                }
            }
        });


        //===================================FIM CHECKBOX 17========================================

        //======================TRATAMENTO DO CHECKBOX 18============================================

        cb18 = (CheckBox) view.findViewById(R.id.cb18);

        SharedPreferences sharedPref_estadocb18 = this.getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo", Context.MODE_PRIVATE);
        estadocb18 = sharedPref_estadocb18.getInt("estadocb18", 0);

        if (estadocb18 == 0)
            cb18.setChecked(false);
        else
            cb18.setChecked(true);

        cb18.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {

                if (cb18.isChecked()){
                    String mensagem_marcar = getString(R.string.confirmacao_marcar);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(mensagem_marcar);

                    builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(getActivity(), Main_activity.class);
                            startActivity(intent);

                        }
                    });
                    builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            cb18.setChecked(true);

                            //estadocb18 = 1;
                            SharedPreferences sharedPref_estadocb18 = getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo",0);
                            SharedPreferences.Editor prefEditor = sharedPref_estadocb18.edit();
                            prefEditor.putInt("estadocb18",1);
                            prefEditor.commit();

                            Toast.makeText(getActivity().getApplicationContext(), "Salvo", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.show();
                }
                else {
                    String mensagem_desmarcar = getString(R.string.confirmacao_desmarcar);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(mensagem_desmarcar);

                    builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(getActivity(), Main_activity.class);
                            startActivity(intent);
                        }
                    });
                    builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            cb18.setChecked(false);

                            SharedPreferences sharedPref_estadocb18 = getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo", 0);
                            SharedPreferences.Editor prefEditor = sharedPref_estadocb18.edit();
                            prefEditor.putInt("estadocb18", 0);
                            prefEditor.commit();

                            Toast.makeText(getActivity().getApplicationContext(), "Salvo", Toast.LENGTH_SHORT).show();

                        }
                    });
                    builder.show();
                }
            }
        });


        //===================================FIM CHECKBOX 18========================================

        //======================TRATAMENTO DO CHECKBOX 19============================================

        cb19 = (CheckBox) view.findViewById(R.id.cb19);

        SharedPreferences sharedPref_estadocb19 = this.getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo", Context.MODE_PRIVATE);
        estadocb19 = sharedPref_estadocb19.getInt("estadocb19", 0);

        if (estadocb19 == 0)
            cb19.setChecked(false);
        else
            cb19.setChecked(true);

        cb19.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {

                if (cb19.isChecked()){
                    String mensagem_marcar = getString(R.string.confirmacao_marcar);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(mensagem_marcar);

                    builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(getActivity(), Main_activity.class);
                            startActivity(intent);

                        }
                    });
                    builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            cb19.setChecked(true);

                            //estadocb19 = 1;
                            SharedPreferences sharedPref_estadocb19 = getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo",0);
                            SharedPreferences.Editor prefEditor = sharedPref_estadocb19.edit();
                            prefEditor.putInt("estadocb19",1);
                            prefEditor.commit();

                            Toast.makeText(getActivity().getApplicationContext(), "Salvo", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.show();
                }
                else {
                    String mensagem_desmarcar = getString(R.string.confirmacao_desmarcar);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(mensagem_desmarcar);

                    builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(getActivity(), Main_activity.class);
                            startActivity(intent);
                        }
                    });
                    builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            cb19.setChecked(false);

                            SharedPreferences sharedPref_estadocb19 = getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo", 0);
                            SharedPreferences.Editor prefEditor = sharedPref_estadocb19.edit();
                            prefEditor.putInt("estadocb19", 0);
                            prefEditor.commit();

                            Toast.makeText(getActivity().getApplicationContext(), "Salvo", Toast.LENGTH_SHORT).show();

                        }
                    });
                    builder.show();
                }
            }
        });


        //===================================FIM CHECKBOX 19========================================

        //======================TRATAMENTO DO CHECKBOX 20============================================

        cb20 = (CheckBox) view.findViewById(R.id.cb20);

        SharedPreferences sharedPref_estadocb20 = this.getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo", Context.MODE_PRIVATE);
        estadocb20 = sharedPref_estadocb20.getInt("estadocb20", 0);

        if (estadocb20 == 0)
            cb20.setChecked(false);
        else
            cb20.setChecked(true);

        cb20.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {

                if (cb20.isChecked()){
                    String mensagem_marcar = getString(R.string.confirmacao_marcar);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(mensagem_marcar);
                    builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(getActivity(), Main_activity.class);
                            startActivity(intent);
                        }
                    });
                    builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            cb20.setChecked(true);

                            //estadocb20 = 1;
                            SharedPreferences sharedPref_estadocb20 = getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo",0);
                            SharedPreferences.Editor prefEditor = sharedPref_estadocb20.edit();
                            prefEditor.putInt("estadocb20",1);
                            prefEditor.commit();

                            Toast.makeText(getActivity().getApplicationContext(), "Salvo", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.show();
                }
                else {
                    String mensagem_desmarcar = getString(R.string.confirmacao_desmarcar);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(mensagem_desmarcar);
                    builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(getActivity(), Main_activity.class);
                            startActivity(intent);
                        }
                    });
                    builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            cb20.setChecked(false);

                            SharedPreferences sharedPref_estadocb20 = getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo", 0);
                            SharedPreferences.Editor prefEditor = sharedPref_estadocb20.edit();
                            prefEditor.putInt("estadocb20", 0);
                            prefEditor.commit();

                            Toast.makeText(getActivity().getApplicationContext(), "Salvo", Toast.LENGTH_SHORT).show();

                        }
                    });
                    builder.show();
                }
            }
        });
        //===================================FIM CHECKBOX 20========================================

        //======================TRATAMENTO DO CHECKBOX 21============================================

        cb21 = (CheckBox) view.findViewById(R.id.cb21);

        SharedPreferences sharedPref_estadocb21 = this.getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo", Context.MODE_PRIVATE);
        estadocb21 = sharedPref_estadocb21.getInt("estadocb21", 0);

        if (estadocb21 == 0)
            cb21.setChecked(false);
        else
            cb21.setChecked(true);

        cb21.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {

                if (cb21.isChecked()){
                    String mensagem_marcar = getString(R.string.confirmacao_marcar);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(mensagem_marcar);
                    builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(getActivity(), Main_activity.class);
                            startActivity(intent);
                        }
                    });
                    builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            cb21.setChecked(true);

                            //estadocb21 = 1;
                            SharedPreferences sharedPref_estadocb21 = getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo",0);
                            SharedPreferences.Editor prefEditor = sharedPref_estadocb21.edit();
                            prefEditor.putInt("estadocb21",1);
                            prefEditor.commit();

                            Toast.makeText(getActivity().getApplicationContext(), "Salvo", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.show();
                }
                else {
                    String mensagem_desmarcar = getString(R.string.confirmacao_desmarcar);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(mensagem_desmarcar);
                    builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(getActivity(), Main_activity.class);
                            startActivity(intent);
                        }
                    });
                    builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            cb21.setChecked(false);

                            SharedPreferences sharedPref_estadocb21 = getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo", 0);
                            SharedPreferences.Editor prefEditor = sharedPref_estadocb21.edit();
                            prefEditor.putInt("estadocb21", 0);
                            prefEditor.commit();

                            Toast.makeText(getActivity().getApplicationContext(), "Salvo", Toast.LENGTH_SHORT).show();

                        }
                    });
                    builder.show();
                }
            }
        });
        //===================================FIM CHECKBOX 21========================================

        //======================TRATAMENTO DO CHECKBOX 22============================================

        cb22 = (CheckBox) view.findViewById(R.id.cb22);

        SharedPreferences sharedPref_estadocb22 = this.getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo", Context.MODE_PRIVATE);
        estadocb22 = sharedPref_estadocb22.getInt("estadocb22", 0);

        if (estadocb22 == 0)
            cb22.setChecked(false);
        else
            cb22.setChecked(true);

        cb22.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {

                if (cb22.isChecked()){
                    String mensagem_marcar = getString(R.string.confirmacao_marcar);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(mensagem_marcar);
                    builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(getActivity(), Main_activity.class);
                            startActivity(intent);
                        }
                    });
                    builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            cb22.setChecked(true);

                            //estadocb22 = 1;
                            SharedPreferences sharedPref_estadocb22 = getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo",0);
                            SharedPreferences.Editor prefEditor = sharedPref_estadocb22.edit();
                            prefEditor.putInt("estadocb22",1);
                            prefEditor.commit();

                            Toast.makeText(getActivity().getApplicationContext(), "Salvo", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.show();
                }
                else {
                    String mensagem_desmarcar = getString(R.string.confirmacao_desmarcar);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(mensagem_desmarcar);
                    builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(getActivity(), Main_activity.class);
                            startActivity(intent);
                        }
                    });
                    builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            cb22.setChecked(false);

                            SharedPreferences sharedPref_estadocb22 = getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo", 0);
                            SharedPreferences.Editor prefEditor = sharedPref_estadocb22.edit();
                            prefEditor.putInt("estadocb22", 0);
                            prefEditor.commit();

                            Toast.makeText(getActivity().getApplicationContext(), "Salvo", Toast.LENGTH_SHORT).show();

                        }
                    });
                    builder.show();
                }
            }
        });
        //===================================FIM CHECKBOX 22========================================

        //======================TRATAMENTO DO CHECKBOX 23===========================================

        cb23 = (CheckBox) view.findViewById(R.id.cb23);

        SharedPreferences sharedPref_estadocb23 = this.getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo", Context.MODE_PRIVATE);
        estadocb23 = sharedPref_estadocb23.getInt("estadocb23", 0);

        if (estadocb23 == 0)
            cb23.setChecked(false);
        else
            cb23.setChecked(true);

        cb23.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {

                if (cb23.isChecked()){
                    String mensagem_marcar = getString(R.string.confirmacao_marcar);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(mensagem_marcar);
                    builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(getActivity(), Main_activity.class);
                            startActivity(intent);
                        }
                    });
                    builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            cb23.setChecked(true);

                            //estadocb23 = 1;
                            SharedPreferences sharedPref_estadocb23 = getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo",0);
                            SharedPreferences.Editor prefEditor = sharedPref_estadocb23.edit();
                            prefEditor.putInt("estadocb23",1);
                            prefEditor.commit();

                            Toast.makeText(getActivity().getApplicationContext(), "Salvo", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.show();
                }
                else {
                    String mensagem_desmarcar = getString(R.string.confirmacao_desmarcar);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(mensagem_desmarcar);
                    builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(getActivity(), Main_activity.class);
                            startActivity(intent);
                        }
                    });
                    builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            cb23.setChecked(false);

                            SharedPreferences sharedPref_estadocb23 = getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo", 0);
                            SharedPreferences.Editor prefEditor = sharedPref_estadocb23.edit();
                            prefEditor.putInt("estadocb23", 0);
                            prefEditor.commit();

                            Toast.makeText(getActivity().getApplicationContext(), "Salvo", Toast.LENGTH_SHORT).show();

                        }
                    });
                    builder.show();
                }
            }
        });
        //===================================FIM CHECKBOX 23========================================

        //======================TRATAMENTO DO CHECKBOX 24============================================

        cb24 = (CheckBox) view.findViewById(R.id.cb24);

        SharedPreferences sharedPref_estadocb24 = this.getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo", Context.MODE_PRIVATE);
        estadocb24 = sharedPref_estadocb24.getInt("estadocb24", 0);

        if (estadocb24 == 0)
            cb24.setChecked(false);
        else
            cb24.setChecked(true);

        cb24.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {

                if (cb24.isChecked()){
                    String mensagem_marcar = getString(R.string.confirmacao_marcar);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(mensagem_marcar);
                    builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(getActivity(), Main_activity.class);
                            startActivity(intent);
                        }
                    });
                    builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            cb24.setChecked(true);

                            //estadocb24 = 1;
                            SharedPreferences sharedPref_estadocb24 = getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo",0);
                            SharedPreferences.Editor prefEditor = sharedPref_estadocb24.edit();
                            prefEditor.putInt("estadocb24",1);
                            prefEditor.commit();

                            Toast.makeText(getActivity().getApplicationContext(), "Salvo", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.show();
                }
                else {
                    String mensagem_desmarcar = getString(R.string.confirmacao_desmarcar);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(mensagem_desmarcar);
                    builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(getActivity(), Main_activity.class);
                            startActivity(intent);
                        }
                    });
                    builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            cb24.setChecked(false);

                            SharedPreferences sharedPref_estadocb24 = getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo", 0);
                            SharedPreferences.Editor prefEditor = sharedPref_estadocb24.edit();
                            prefEditor.putInt("estadocb24", 0);
                            prefEditor.commit();

                            Toast.makeText(getActivity().getApplicationContext(), "Salvo", Toast.LENGTH_SHORT).show();

                        }
                    });
                    builder.show();
                }
            }
        });
        //===================================FIM CHECKBOX 24========================================

        //======================TRATAMENTO DO CHECKBOX 25============================================

        cb25 = (CheckBox) view.findViewById(R.id.cb25);

        SharedPreferences sharedPref_estadocb25 = this.getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo", Context.MODE_PRIVATE);
        estadocb25 = sharedPref_estadocb25.getInt("estadocb25", 0);

        if (estadocb25 == 0)
            cb25.setChecked(false);
        else
            cb25.setChecked(true);

        cb25.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {

                if (cb25.isChecked()){
                    String mensagem_marcar = getString(R.string.confirmacao_marcar);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(mensagem_marcar);
                    builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(getActivity(), Main_activity.class);
                            startActivity(intent);
                        }
                    });
                    builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            cb25.setChecked(true);

                            //estadocb2 = 1;
                            SharedPreferences sharedPref_estadocb25 = getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo",0);
                            SharedPreferences.Editor prefEditor = sharedPref_estadocb25.edit();
                            prefEditor.putInt("estadocb25",1);
                            prefEditor.commit();

                            Toast.makeText(getActivity().getApplicationContext(), "Salvo", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.show();
                }
                else {
                    String mensagem_desmarcar = getString(R.string.confirmacao_desmarcar);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(mensagem_desmarcar);
                    builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(getActivity(), Main_activity.class);
                            startActivity(intent);
                        }
                    });
                    builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            cb25.setChecked(false);

                            SharedPreferences sharedPref_estadocb25 = getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo", 0);
                            SharedPreferences.Editor prefEditor = sharedPref_estadocb25.edit();
                            prefEditor.putInt("estadocb25", 0);
                            prefEditor.commit();

                            Toast.makeText(getActivity().getApplicationContext(), "Salvo", Toast.LENGTH_SHORT).show();

                        }
                    });
                    builder.show();
                }
            }
        });
        //===================================FIM CHECKBOX 25========================================


        //======================TRATAMENTO DO CHECKBOX 26============================================

        cb26 = (CheckBox) view.findViewById(R.id.cb26);

        SharedPreferences sharedPref_estadocb26 = this.getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo", Context.MODE_PRIVATE);
        estadocb26 = sharedPref_estadocb26.getInt("estadocb26", 0);

        if (estadocb26 == 0)
            cb26.setChecked(false);
        else
            cb26.setChecked(true);

        cb26.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {

                if (cb26.isChecked()){
                    String mensagem_marcar = getString(R.string.confirmacao_marcar);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(mensagem_marcar);
                    builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(getActivity(), Main_activity.class);
                            startActivity(intent);
                        }
                    });
                    builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            cb26.setChecked(true);

                            //estadocb26 = 1;
                            SharedPreferences sharedPref_estadocb26 = getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo",0);
                            SharedPreferences.Editor prefEditor = sharedPref_estadocb26.edit();
                            prefEditor.putInt("estadocb26",1);
                            prefEditor.commit();

                            Toast.makeText(getActivity().getApplicationContext(), "Salvo", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.show();
                }
                else {
                    String mensagem_desmarcar = getString(R.string.confirmacao_desmarcar);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(mensagem_desmarcar);
                    builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(getActivity(), Main_activity.class);
                            startActivity(intent);
                        }
                    });
                    builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            cb26.setChecked(false);

                            SharedPreferences sharedPref_estadocb26 = getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo", 0);
                            SharedPreferences.Editor prefEditor = sharedPref_estadocb26.edit();
                            prefEditor.putInt("estadocb26", 0);
                            prefEditor.commit();

                            Toast.makeText(getActivity().getApplicationContext(), "Salvo", Toast.LENGTH_SHORT).show();

                        }
                    });
                    builder.show();
                }
            }
        });
        //===================================FIM CHECKBOX 26========================================

        //======================TRATAMENTO DO CHECKBOX 27============================================

        cb27 = (CheckBox) view.findViewById(R.id.cb27);

        SharedPreferences sharedPref_estadocb27 = this.getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo", Context.MODE_PRIVATE);
        estadocb27 = sharedPref_estadocb27.getInt("estadocb27", 0);

        if (estadocb27 == 0)
            cb27.setChecked(false);
        else
            cb27.setChecked(true);

        cb27.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {

                if (cb27.isChecked()){
                    String mensagem_marcar = getString(R.string.confirmacao_marcar);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(mensagem_marcar);
                    builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(getActivity(), Main_activity.class);
                            startActivity(intent);
                        }
                    });
                    builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            cb27.setChecked(true);

                            //estadocb27 = 1;
                            SharedPreferences sharedPref_estadocb27 = getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo",0);
                            SharedPreferences.Editor prefEditor = sharedPref_estadocb27.edit();
                            prefEditor.putInt("estadocb27",1);
                            prefEditor.commit();

                            Toast.makeText(getActivity().getApplicationContext(), "Salvo", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.show();
                }
                else {
                    String mensagem_desmarcar = getString(R.string.confirmacao_desmarcar);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(mensagem_desmarcar);
                    builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(getActivity(), Main_activity.class);
                            startActivity(intent);
                        }
                    });
                    builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            cb27.setChecked(false);

                            SharedPreferences sharedPref_estadocb27 = getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo", 0);
                            SharedPreferences.Editor prefEditor = sharedPref_estadocb27.edit();
                            prefEditor.putInt("estadocb27", 0);
                            prefEditor.commit();

                            Toast.makeText(getActivity().getApplicationContext(), "Salvo", Toast.LENGTH_SHORT).show();

                        }
                    });
                    builder.show();
                }
            }
        });
        //===================================FIM CHECKBOX 27========================================

        //======================TRATAMENTO DO CHECKBOX 28============================================

        cb28 = (CheckBox) view.findViewById(R.id.cb28);

        SharedPreferences sharedPref_estadocb28 = this.getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo", Context.MODE_PRIVATE);
        estadocb28 = sharedPref_estadocb28.getInt("estadocb28", 0);

        if (estadocb28 == 0)
            cb28.setChecked(false);
        else
            cb28.setChecked(true);

        cb28.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {

                if (cb28.isChecked()){
                    String mensagem_marcar = getString(R.string.confirmacao_marcar);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(mensagem_marcar);
                    builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(getActivity(), Main_activity.class);
                            startActivity(intent);
                        }
                    });
                    builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            cb28.setChecked(true);

                            //estadocb28 = 1;
                            SharedPreferences sharedPref_estadocb28 = getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo",0);
                            SharedPreferences.Editor prefEditor = sharedPref_estadocb28.edit();
                            prefEditor.putInt("estadocb28",1);
                            prefEditor.commit();

                            Toast.makeText(getActivity().getApplicationContext(), "Salvo", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.show();
                }
                else {
                    String mensagem_desmarcar = getString(R.string.confirmacao_desmarcar);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(mensagem_desmarcar);
                    builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(getActivity(), Main_activity.class);
                            startActivity(intent);
                        }
                    });
                    builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            cb28.setChecked(false);

                            SharedPreferences sharedPref_estadocb28 = getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo", 0);
                            SharedPreferences.Editor prefEditor = sharedPref_estadocb28.edit();
                            prefEditor.putInt("estadocb28", 0);
                            prefEditor.commit();

                            Toast.makeText(getActivity().getApplicationContext(), "Salvo", Toast.LENGTH_SHORT).show();

                        }
                    });
                    builder.show();
                }
            }
        });
        //===================================FIM CHECKBOX 28========================================

        //======================TRATAMENTO DO CHECKBOX 29============================================

        cb29 = (CheckBox) view.findViewById(R.id.cb29);

        SharedPreferences sharedPref_estadocb29 = this.getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo", Context.MODE_PRIVATE);
        estadocb29 = sharedPref_estadocb29.getInt("estadocb29", 0);

        if (estadocb29 == 0)
            cb29.setChecked(false);
        else
            cb29.setChecked(true);

        cb29.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {

                if (cb29.isChecked()){
                    String mensagem_marcar = getString(R.string.confirmacao_marcar);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(mensagem_marcar);
                    builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(getActivity(), Main_activity.class);
                            startActivity(intent);
                        }
                    });
                    builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            cb29.setChecked(true);

                            //estadocb29 = 1;
                            SharedPreferences sharedPref_estadocb29 = getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo",0);
                            SharedPreferences.Editor prefEditor = sharedPref_estadocb29.edit();
                            prefEditor.putInt("estadocb29",1);
                            prefEditor.commit();

                            Toast.makeText(getActivity().getApplicationContext(), "Salvo", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.show();
                }
                else {
                    String mensagem_desmarcar = getString(R.string.confirmacao_desmarcar);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(mensagem_desmarcar);
                    builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(getActivity(), Main_activity.class);
                            startActivity(intent);
                        }
                    });
                    builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            cb29.setChecked(false);

                            SharedPreferences sharedPref_estadocb29 = getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo", 0);
                            SharedPreferences.Editor prefEditor = sharedPref_estadocb29.edit();
                            prefEditor.putInt("estadocb29", 0);
                            prefEditor.commit();

                            Toast.makeText(getActivity().getApplicationContext(), "Salvo", Toast.LENGTH_SHORT).show();

                        }
                    });
                    builder.show();
                }
            }
        });
        //===================================FIM CHECKBOX 29========================================

        //======================TRATAMENTO DO CHECKBOX 30============================================

        cb30 = (CheckBox) view.findViewById(R.id.cb30);

        SharedPreferences sharedPref_estadocb30 = this.getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo", Context.MODE_PRIVATE);
        estadocb30 = sharedPref_estadocb30.getInt("estadocb30", 0);

        if (estadocb30 == 0)
            cb30.setChecked(false);
        else
            cb30.setChecked(true);

        cb30.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {

                if (cb30.isChecked()){
                    String mensagem_marcar = getString(R.string.confirmacao_marcar);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(mensagem_marcar);
                    builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(getActivity(), Main_activity.class);
                            startActivity(intent);
                        }
                    });
                    builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            cb30.setChecked(true);

                            //estadocb30 = 1;
                            SharedPreferences sharedPref_estadocb30 = getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo",0);
                            SharedPreferences.Editor prefEditor = sharedPref_estadocb30.edit();
                            prefEditor.putInt("estadocb30",1);
                            prefEditor.commit();

                            Toast.makeText(getActivity().getApplicationContext(), "Salvo", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.show();
                }
                else {
                    String mensagem_desmarcar = getString(R.string.confirmacao_desmarcar);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(mensagem_desmarcar);
                    builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(getActivity(), Main_activity.class);
                            startActivity(intent);
                        }
                    });
                    builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            cb30.setChecked(false);

                            SharedPreferences sharedPref_estadocb30 = getActivity().getSharedPreferences("pref_checkbox_portugues_oitavo", 0);
                            SharedPreferences.Editor prefEditor = sharedPref_estadocb30.edit();
                            prefEditor.putInt("estadocb30", 0);
                            prefEditor.commit();

                            Toast.makeText(getActivity().getApplicationContext(), "Salvo", Toast.LENGTH_SHORT).show();

                        }
                    });
                    builder.show();
                }
            }
        });
        //===================================FIM CHECKBOX 30========================================


        DataSistema();

        return view;

    }//end OnCreate

    //=============================FUNÇÃO DE LEITURA DA DATA DO SISTEMA============================
    public void DataSistema(){

        //Leitor de data do sistema
        Calendar now = Calendar.getInstance();
        Dia_sistema = now.get(Calendar.DAY_OF_MONTH);
        Mes_sistema = now.get(Calendar.MONTH); // Note: zero based!
        Mes_sistema++;

        //Toast.makeText(getActivity().getApplicationContext()," "+ Dia_sistema + " / "+ Mes_sistema, Toast.LENGTH_LONG);

    }
    //==============================================================================================


    //===========================MÉTODO QUE RECEBE AS SOMBRAS DOS BIMESTRES===========================
    @Override
    public void onViewCreated (View view, Bundle savedInstanceState) {
        ((Main_activity) getActivity()).SombraBimestre(view);

    }
    //==============================================================================================
}