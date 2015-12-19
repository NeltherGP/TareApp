package com.example.nelther.recycleviewapp.tareas;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nelther.recycleviewapp.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by nelther on 04/11/2015.
 */
public class TareaRecycleAdapter extends RecyclerView.Adapter<TareaRecycleAdapter.tareaViewHolder> {

    private List <TareaInfo> tareaInfo;

    TareaInfo ti;
    Context context;
    public TareaRecycleAdapter(List<TareaInfo> tareaInfo, Context context){
        Log.i("MOVILES","HOLA ");
        this.tareaInfo=tareaInfo;
        this.context=context;
    }

    public void setFilter(List <TareaInfo> FiltroTareaInfo){
        tareaInfo= new ArrayList<>();
        tareaInfo.addAll(FiltroTareaInfo);
        notifyDataSetChanged();
    }

    @Override
    public tareaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i("Moviles","HOLA onCreateViewHolder: ");
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout,parent,false);
        //v.setTag(pos);
        return new tareaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(tareaViewHolder holder, int position) {
       ;
        ti = tareaInfo.get(position);
        holder.nombre.setText(ti.nombreTarea);
        holder.nombre.setTag(position);
        holder.materia.setText(ti.materia);
        holder.descripcion.setText(ti.descripcion);
        holder.entrega.setText(ti.entrega);

    }

    @Override
    public int getItemCount() {
        return tareaInfo.size();
    }

    public class tareaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView nombre, materia, descripcion, entrega;
        public tareaViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            nombre = (TextView) itemView.findViewById(R.id.txtNomTarea);
            materia=(TextView) itemView.findViewById(R.id.txtMateriaTarea);
            descripcion = (TextView) itemView.findViewById(R.id.txtDescripcionTarea);
            entrega = (TextView) itemView.findViewById(R.id.txtFechaEntrega);
        }

        @Override
        public void onClick(View v) {

            ti=tareaInfo.get((int) (v.findViewById(R.id.txtNomTarea).getTag()));
            Intent intent = new Intent(context,VerTarea.class);
            Bundle b = new Bundle();
            b.putInt("idTarea",ti.idTarea);
            Log.i("Moviles","ID TAREA = "+ti.idTarea);
            b.putString("nomtarea", ti.nombreTarea);
            b.putString("descTarea", ti.descripcion);
            b.putString(("materia"),ti.materia);
            b.putString("fechaEntrega",ti.entrega);
            //b.putString("recordar",ti.recordar);
            intent.putExtra("TareaBundle", b);
            context.startActivity(intent);
        }


    }


}
