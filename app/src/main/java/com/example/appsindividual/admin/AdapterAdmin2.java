package com.example.appsindividual.admin;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appsindividual.Local;
import com.example.appsindividual.R;

import java.util.List;

public class AdapterAdmin2 extends  RecyclerView.Adapter<AdapterAdmin2.ViewHolder>{
    private List<Local> mData;
    private LayoutInflater mInflater;
    private Context context;
    private OnItemClickListener mListener;
    Activity activity;
    public interface OnItemClickListener {
        void onRechazarClick(int position);

        void onAprobarClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener= listener;
    }
    public AdapterAdmin2(List<Local> itemList, Context context, Activity activity) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
        this.activity = activity;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterAdmin2.ViewHolder holder, final int position) {
        //holder.bindData(mData.get(position));
        holder.bindData(mData.get(position));
    }

    @NonNull
    @Override
    public AdapterAdmin2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.gestion, null);

        return new AdapterAdmin2.ViewHolder(view, mListener);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView cardImage;
        TextView nombre, tipo, ubicacion, detalles;

        ViewHolder(View itemView, OnItemClickListener listener){
            super(itemView);
            nombre = itemView.findViewById(R.id.nombre);
            //cardNombre = itemView.findViewById(R.id.cardNombre);
            tipo = itemView.findViewById(R.id.Tipo);
            ubicacion = itemView.findViewById(R.id.ubicacion);
            detalles = itemView.findViewById(R.id.detalles);


           Button btnrechazar = itemView.findViewById(R.id.buttonRechazar);
            btnrechazar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onRechazarClick(position);

                        }
                    }
                }
            });

            Button btnAprobar = itemView.findViewById(R.id.buttonAprobar);
            btnAprobar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onAprobarClick(position);

                        }
                    }

                }
            });
        }
        void bindData(final Local item){
            tipo.setText(item.getTipo());
            detalles.setText(item.getDetalle());
            nombre.setText(item.getNombre());
            ubicacion.setText(item.getUbicacion());

        }
    }




}
