package com.liam.lothgarder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class plantaAdapter extends RecyclerView.Adapter<plantaAdapter.ViewHolder> {

    //Definición de varibales globales
    private List<planta> listaPlantas;
    private OnItemClickListener listener;
    private int posicionSeleccionada = RecyclerView.NO_POSITION;

    public interface OnItemClickListener {
        void onItemClick(planta plantaSeleccionada);
    }

    public plantaAdapter(List<planta> listaPlantas, OnItemClickListener listener) {
        this.listaPlantas = listaPlantas;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        //Nombre Planta Usuario
        TextView eNombrePU, eIdPu;
        View container;

        public ViewHolder(View itemView) {
            super(itemView);
            eNombrePU = itemView.findViewById(R.id.eNombrePlanta);
            eIdPu = itemView.findViewById(R.id.eIdPlanta);
            container = itemView.findViewById(R.id.containerP);
        }

        public void bind(planta plantaItem, boolean estaSeleccionada, int numeroVeUsuario) {
            eNombrePU.setText(plantaItem.getNombre());
            eIdPu.setText("No." + numeroVeUsuario);

            int color = estaSeleccionada
                    ? ContextCompat.getColor(itemView.getContext(), R.color.verdeC)
                    : ContextCompat.getColor(itemView.getContext(), R.color.fondo1);

            container.setBackgroundColor(color);
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

        int numeroVisible = position + 1;
        boolean estaSeleccionada = position == posicionSeleccionada;

        holder.bind(plantaItem, estaSeleccionada, numeroVisible);
        //holder.bind(plantaItem, estaSeleccionada);

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