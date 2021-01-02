package com.example.appsindividual.cliente;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appsindividual.Local;
import com.example.appsindividual.R;

import java.util.List;

public class AdapterCliente extends RecyclerView.Adapter<AdapterCliente.ViewHolder> {
    private List<Local> mData;
    private LayoutInflater mInflater;
    private Context context;

    Activity activity;

    public AdapterCliente(List<Local> itemList, Context context, Activity activity) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
        this.activity = activity;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @NonNull
    @Override
    public AdapterCliente.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.activity_restaurantes, null);
        AdapterCliente.ViewHolder viewHolder = new AdapterCliente.ViewHolder(view);


        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(mData.get(position));

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView cardImage;
        TextView nombre, tipo, ubicacion, detalles;

        ViewHolder(View itemView) {
            super(itemView);

            nombre = itemView.findViewById(R.id.nombre);
            //cardNombre = itemView.findViewById(R.id.cardNombre);
            tipo = itemView.findViewById(R.id.tipo);
            ubicacion = itemView.findViewById(R.id.ubicacion);
            detalles = itemView.findViewById(R.id.detalles);


        }


        void bindData(final Local item) {
          //  Glide.with(activity).load(item.getImagenEquipo()).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(cardImage);
            tipo.setText(item.getTipo());
            detalles.setText(item.getDetalle());
            nombre.setText(item.getNombre());
            ubicacion.setText(item.getUbicacion());


        }

    }




}
