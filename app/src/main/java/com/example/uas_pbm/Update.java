package com.example.uas_pbm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Update extends AppCompatActivity {

    private EditText nama;
    private Button submit, jam1;
    private Spinner hari, kelas;
    private String jammk;
    private int hour,minute;
    private String id = "";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        nama = findViewById(R.id.text_NamaMK);
        jam1 = findViewById(R.id.text_JamMK);
        hari = findViewById(R.id.text_HariMK);
        kelas = findViewById(R.id.text_KelasMK);
        submit = findViewById(R.id.btnSubmit);

        //spinner hari
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.HariMK, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hari.setAdapter(adapter);

        //spinner kelas
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getApplicationContext(), R.array.KelasMK, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        kelas.setAdapter(adapter1);


        submit.setOnClickListener(view-> {
            if (nama.getText().length()>0){
                saveData(nama.getText().toString(),jammk,hari.getSelectedItem().toString(),kelas.getSelectedItem().toString());
            } else {
                Toast.makeText(getApplicationContext(), "Silahkan isi semua data!", Toast.LENGTH_SHORT).show();
            }
        });

        //menampilkan data ketika klik edit
        Intent intent = getIntent();
        if (intent!=null){
            id = intent.getStringExtra("id");
            nama.setText(intent.getStringExtra("nama"));
            jam1.setText(intent.getStringExtra("jammk"));
            jammk = intent.getStringExtra("jammk");
            hari.setSelection(getHari(hari, intent.getStringExtra("hari")));
            kelas.setSelection(getKelas(kelas, intent.getStringExtra("kelas")));
        }
    }

    private int getKelas(Spinner kelas, String kelas1){
        for(int i=0;i<kelas.getCount();i++){
            if (kelas.getItemAtPosition(i).toString().equalsIgnoreCase(kelas1)){
                return i;
            }
        }
        return 0;
    }

    private int getHari(Spinner hari, String hari1){
        for(int i=0;i<hari.getCount();i++){
            if (hari.getItemAtPosition(i).toString().equalsIgnoreCase(hari1)){
                return i;
            }
        }
        return 0;
    }

    private void saveData(String nama, String jam, String hari, String kelas) {
        Map<String, Object> matkul = new HashMap<>();
        matkul.put("mata kuliah", nama);
        matkul.put("Jam", jam);
        matkul.put("hari", hari);
        matkul.put("kelas", kelas);

        db.collection("mata kuliah").document(id)
                .set(matkul)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Berhasil", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Gagal", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void popTimePicker(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                hour = selectedHour;
                minute = selectedMinute;
                jammk= String.format(Locale.getDefault(), "%02d:%02d",hour,minute);
                jam1.setText(String.format(Locale.getDefault(), "%02d:%02d",hour,minute));
            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener, hour, minute, true);
        timePickerDialog.setTitle("Pilih Jam Mata Kuliah");
        timePickerDialog.show();
    }
}