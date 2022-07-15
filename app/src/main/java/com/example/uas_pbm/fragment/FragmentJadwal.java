package com.example.uas_pbm.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.uas_pbm.R;
import com.example.uas_pbm.adapter.adapterMatkul;
import com.example.uas_pbm.model.modelMatkul;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FragmentJadwal extends Fragment {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RecyclerView recyclerView;
    private List<modelMatkul> list = new ArrayList<>();
    private adapterMatkul matkulAdapter;

    public FragmentJadwal() {
        // Required empty public constructor
    }

    public static String getCurrentDate(){
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE", new Locale("id","ID"));
        String tanggal = simpleDateFormat.format(c);
        return tanggal;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_jadwal, container, false);

        recyclerView = view.findViewById(R.id.rv);
        matkulAdapter = new adapterMatkul(getActivity(), list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(matkulAdapter);

        getData();
        return view;

    }

    private void getData(){
        db.collection("mata kuliah")
                .whereEqualTo("hari", getCurrentDate())
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

    @Override
    public void onStart(){
        super.onStart();
        getData();
    }
}