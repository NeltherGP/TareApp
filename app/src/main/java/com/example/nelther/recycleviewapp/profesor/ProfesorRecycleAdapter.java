package com.example.nelther.recycleviewapp.profesor;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nelther.recycleviewapp.R;
import com.example.nelther.recycleviewapp.verProfesor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nelther on 08/11/2015.
 */
public class ProfesorRecycleAdapter extends RecyclerView.Adapter<ProfesorRecycleAdapter.profesorViewHolder> {


    private List <ProfesorInfo> profesorInfo;

    ProfesorRecycleAdapter(List<ProfesorInfo> materiaInfo, Context context){
        this.profesorInfo = materiaInfo;
        this.context=context;
    }
    Context context;

    public void setFilter(List <ProfesorInfo> FiltroTareaInfo){
        profesorInfo= new ArrayList<>();
        profesorInfo.addAll(FiltroTareaInfo);
        notifyDataSetChanged();
    }
    @Override
    public profesorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.profesor_card_layout,parent,false);
        return new profesorViewHolder(v);
    }

    @Override
    public void onBindViewHolder(profesorViewHolder holder, int position) {
        ProfesorInfo pi = profesorInfo.get(position);
        holder.nomProfesor.setText(pi.nombreProfesor);
        holder.correoProfesor.setText(pi.correoProfesor);
        holder.nomProfesor.setTag(position);
    }

    @Override
    public int getItemCount() {
        return profesorInfo.size();
    }

    public class profesorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView nomProfesor,correoProfesor;
        public profesorViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            nomProfesor= (TextView) itemView.findViewById(R.id.txtNomProfesor_cardProfLayout);
            correoProfesor= (TextView) itemView.findViewById(R.id.correo_cardProfLayout);
        }

        @Override
        public void onClick(View v) {
            ProfesorInfo pi = profesorInfo.get((int)(v.findViewById(R.id.txtNomProfesor_cardProfLayout).getTag()));
            Intent intent = new Intent(context,verProfesor.class);
            Bundle b = new Bundle();
            b.putString("idProfesor",pi.idProfesor);
            b.putString("nombre", pi.nombreProfesor);
            b.putString("correo",pi.correoProfesor);
            intent.putExtra("ProfesorBundle", b);
            context.startActivity(intent);
        }

    }





}


