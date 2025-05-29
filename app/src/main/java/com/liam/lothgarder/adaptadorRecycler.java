package com.liam.lothgarder;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class adaptadorRecycler extends RecyclerView.Adapter<adaptadorRecycler.ViewHolder> {
    private List<planta> listaPlantas;
    private OnItemClickListener listener;
    private int posicionSeleccionada = RecyclerView.NO_POSITION;

    public interface OnItemClickListener {
        void onItemClick(planta plantaSeleccionada);
    }

    public adaptadorRecycler(List<planta> listaPlantas, OnItemClickListener listener) {
        this.listaPlantas = listaPlantas;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView eNombrePU;

        public ViewHolder(View itemView) {
            super(itemView);
            eNombrePU = itemView.findViewById(R.id.eNombrePlanta);
        }

        public void bind(planta plantaItem, boolean estaSeleccionada) {
            eNombrePU.setText(plantaItem.getNombre());

            // Cambiar color de fondo si está seleccionado
            itemView.setBackgroundColor(
                    estaSeleccionada ? Color.parseColor("#C8E6C9") : Color.TRANSPARENT
            );
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.planta, parent, false);
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        planta plantaItem = listaPlantas.get(position);
        boolean estaSeleccionada = position == posicionSeleccionada;
        holder.bind(plantaItem, estaSeleccionada);

        holder.itemView.setOnClickListener(v -> {
            int posicionAnterior = posicionSeleccionada;
            posicionSeleccionada = holder.getAdapterPosition();

            notifyItemChanged(posicionAnterior);
            notifyItemChanged(posicionSeleccionada);

            if (listener != null) {
                listener.onItemClick(plantaItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaPlantas.size();
    }
}