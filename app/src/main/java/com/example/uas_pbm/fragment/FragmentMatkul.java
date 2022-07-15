package com.example.uas_pbm.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.uas_pbm.InputActivity;
import com.example.uas_pbm.R;
import com.example.uas_pbm.Update;
import com.example.uas_pbm.adapter.adapterMatkul;
import com.example.uas_pbm.model.modelMatkul;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FragmentMatkul extends Fragment {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RecyclerView recyclerView;
    private List<modelMatkul> list = new ArrayList<>();
    private adapterMatkul matkulAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_matkul, container, false);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.floating_action_button);

        recyclerView = view.findViewById(R.id.rv);
        matkulAdapter = new adapterMatkul(getActivity(), list);
        matkulAdapter.setDialog(new adapterMatkul.Dialog() {

            @Override
            public void onClick(int pos) {
                final CharSequence[] dialogItem = {"Update","Delete"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0:
                                Intent intent = new Intent(getActivity(), Update.class);
                                intent.putExtra("id", list.get(pos).getId());
                                intent.putExtra("nama", list.get(pos).getNamamk());
                                intent.putExtra("jammk", list.get(pos).getJammk());
                                intent.putExtra("hari", list.get(pos).getHarimk());
                                intent.putExtra("kelas", list.get(pos).getKelas());
                                startActivity(intent);
                                break;
                            case 1:
                                deleteData(list.get(pos).getId());
                                break;

                        }
                    }
                });
                dialog.show();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(matkulAdapter);

        fab.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), InputActivity.class));
        });
        getData();
        return view;

    }


    @Override
    public void onStart(){
        super.onStart();
        getData();
    }

//menampilkan recycleview pada navbar matakuliah
    private void getData(){
        db.collection("mata kuliah")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        list.clear();
                        if (task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                modelMatkul modal = new modelMatkul(document.getString("mata kuliah"),
                                        document.getString("Jam"),document.getString("hari"),
                                        document.getString("kelas"));
                                modal.setId(document.getId());
                                list.add(modal);
                            }
                            matkulAdapter.notifyDataSetChanged();
                        }else{
                            Toast.makeText(getActivity(), "data gagal di ambil", Toast.LENGTH_SHORT).show();
                        }
                    }

                });
    }

    private void deleteData(String id){
        db.collection("mata kuliah").document(id)
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (!task.isSuccessful()){
                            Toast.makeText(getActivity(), "data gagal dihapus", Toast.LENGTH_SHORT).show();
                        }
                        getData();
                    }
                });
    }



}