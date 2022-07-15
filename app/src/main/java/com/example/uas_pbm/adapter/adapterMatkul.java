package com.example.uas_pbm.adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uas_pbm.R;
import com.example.uas_pbm.model.modelMatkul;

import java.util.List;

public class adapterMatkul extends RecyclerView.Adapter<adapterMatkul.MyViewHolder>{
    private Context context;
    private List<modelMatkul> list;
    private Dialog dialog;

    public interface Dialog{
        void onClick(int pos);
    }

    public void setDialog(Dialog dialog){
        this.dialog = dialog;
    }

    public adapterMatkul(Context context, List<modelMatkul> list){
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.nama.setText(list.get(position).getNamamk());
        holder.jam.setText(list.get(position).getJammk());
        holder.hari.setText(list.get(position).getHarimk());
        holder.kelas.setText(list.get(position).getKelas());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nama,jam,kelas,hari;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            nama = itemView.findViewById(R.id.namamk);
            jam = itemView.findViewById(R.id.jammk);
            hari = itemView.findViewById(R.id.harimk);
            kelas = itemView.findViewById(R.id.kelas);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dialog!=null){
                        dialog.onClick(getLayoutPosition());
                    }
                }
            });
        }
    }
}