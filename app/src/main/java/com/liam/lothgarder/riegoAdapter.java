package com.liam.lothgarder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import java.util.HashMap;
import java.util.Map;

public class riegoAdapter extends RecyclerView.Adapter<riegoAdapter.ViewHolder> {

    private List<riego> lista;

    private Map<Integer, Integer> mapaPlantas = new HashMap<>();
    private int contador = 1;

    public riegoAdapter(List<riego> lista) {
        this.lista = lista;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView etPlantaR, tvFecha, tvSegundos, tvAgua;

        public ViewHolder(View itemView) {
            super(itemView);
            etPlantaR = itemView.findViewById(R.id.etPlantaR);
            tvFecha = itemView.findViewById(R.id.etFechaR);
            tvSegundos = itemView.findViewById(R.id.etSegR);
            tvAgua = itemView.findViewById(R.id.etAguaR);
        }

        public void bind(riego item, int numeroVisible) {
            etPlantaR.setText("Planta " + numeroVisible);
            tvFecha.setText("Fecha: " + item.getFechaR());
            tvSegundos.setText("Riego: " + item.getSegR() + " seg");
            tvAgua.setText("Agua: " + item.getAguaR() + " ml");
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.riego, parent, false);
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        riego item = lista.get(position);

        int id = item.getIdPu();

        //Si no existe el ID, se asigna número nuevo
        if (!mapaPlantas.containsKey(id)) {
            mapaPlantas.put(id, contador++);
        }

        int numeroVisible = mapaPlantas.get(id);

        holder.bind(item, numeroVisible);
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public void actualizarLista(List<riego> nuevaLista) {
        lista.clear();
        lista.addAll(nuevaLista);

        mapaPlantas.clear();
        contador = 1;

        notifyDataSetChanged();
    }
}