package com.example.nelther.recycleviewapp.materia;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nelther.recycleviewapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nelther on 08/11/2015.
 */
public class MateriaRecycleAdapter  extends RecyclerView.Adapter<MateriaRecycleAdapter.materiaViewHolder> {

    private List <MateriaInfo> materiaInfo;
    Context context;

    MateriaRecycleAdapter(List<MateriaInfo> materiaInfo,Context context){
        this.materiaInfo = materiaInfo;
        this.context=context;
    }
    public void setFilter(List <MateriaInfo> FiltroTareaInfo){
        materiaInfo= new ArrayList<>();
        materiaInfo.addAll(FiltroTareaInfo);
        notifyDataSetChanged();
    }

    @Override
    public materiaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.materia_card_layout,parent,false);
        return new materiaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(materiaViewHolder holder, int position) {
        MateriaInfo mi = materiaInfo.get(position);
        holder.nomMateria.setText(mi.nombreMateria);
        holder.nomMateria.setTag(position);
        holder.nomProfesor.setText(mi.profesor);
    }

    @Override
    public int getItemCount() {
        return materiaInfo.size();
    }

    public class materiaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView nomMateria, nomProfesor;
        public materiaViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            nomMateria = (TextView) itemView.findViewById(R.id.txtNomMateria);
            nomProfesor= (TextView) itemView.findViewById(R.id.txtNomProfesorMateria);
        }

        @Override
        public void onClick(View v) {
            MateriaInfo mi=materiaInfo.get((int)(v.findViewById(R.id.txtNomMateria).getTag()));
            Intent intent = new Intent(context,VerMateria.class);
            Bundle b = new Bundle();
            b.putString("idMateria",mi.idMateria);
            b.putString("nomMateria",mi.nombreMateria);
            b.putString("nomProfesor",mi.profesor);
            intent.putExtra("MateriaBundle",b);
            context.startActivity(intent);


        }
    }



}


