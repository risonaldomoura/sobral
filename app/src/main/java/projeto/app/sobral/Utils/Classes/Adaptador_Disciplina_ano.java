package projeto.app.sobral.Utils.Classes;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import projeto.app.sobral.R;

public class Adaptador_Disciplina_ano extends RecyclerView.Adapter<Adaptador_Disciplina_ano.ViewholderDisciplina_ano>{

    public List<MyDataGetSet> listArray;

    public  Adaptador_Disciplina_ano(List<MyDataGetSet> List){
        this.listArray = List;

    }

    @Override
    public ViewholderDisciplina_ano onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_itemview_lista_conteudo,parent,false);

        return new ViewholderDisciplina_ano(view);
    }

    @Override
    public void onBindViewHolder(final Adaptador_Disciplina_ano.ViewholderDisciplina_ano holder, int position) {
        MyDataGetSet x = listArray.get(position);
        holder.myText.setText(x.getX());
        holder.mCB.setChecked(x.isB());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v) {

                return true;
            }
        });

    }


    public class ViewholderDisciplina_ano extends RecyclerView.ViewHolder{
        TextView myText;
        CheckBox mCB;


        public ViewholderDisciplina_ano(View itemView) {
            super(itemView);

            mCB = (CheckBox) itemView.findViewById(R.id.cb_itemView_);
            myText = (TextView) itemView.findViewById(R.id.text_itemview);
        }

    }

    @Override
    public int getItemCount() {
        return listArray.size();
    }

}

