package com.example.uas_pbm;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class InputActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_input);

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

    }

    //add data
    private void saveData(String nama, String jam, String hari, String kelas) {
        Map<String, Object> matkul = new HashMap<>();
        matkul.put("mata kuliah", nama);
        matkul.put("Jam", jam);
        matkul.put("hari", hari);
        matkul.put("kelas", kelas);

            db.collection("mata kuliah")
                    .add(matkul)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(getApplicationContext(), "Berhasil", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

    }

    //timepicker
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