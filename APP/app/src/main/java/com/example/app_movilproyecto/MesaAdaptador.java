package com.example.app_movilproyecto;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MesaAdaptador extends RecyclerView.Adapter<MesaAdaptador.ViewHolder> {

    private List<Mesa> mesas;

    public MesaAdaptador(List<Mesa> mesas) {
        this.mesas = mesas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mesa, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Mesa mesa = mesas.get(position);
        holder.numeroTextView.setText(String.valueOf(mesa.getNumero()));
        holder.estadoTextView.setText(mesa.getEstado());
    }

    @Override
    public int getItemCount() {
        return mesas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView numeroTextView;
        public TextView estadoTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            numeroTextView = itemView.findViewById(R.id.numeroTextView);
            estadoTextView = itemView.findViewById(R.id.estadoTextView);
        }
    }
}

