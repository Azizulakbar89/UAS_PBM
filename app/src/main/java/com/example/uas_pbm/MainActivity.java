package com.example.uas_pbm;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.uas_pbm.databinding.ActivityMainBinding;
import com.example.uas_pbm.fragment.FragmentAbout;
import com.example.uas_pbm.fragment.FragmentJadwal;
import com.example.uas_pbm.fragment.FragmentMatkul;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        replaceFragment(new FragmentJadwal());
        binding.bottomNavigation.setOnItemSelectedListener(item ->{
            switch (item.getItemId()){
                case R.id.menu_jadwal:
                    replaceFragment(new FragmentJadwal());
                    break;
                case R.id.menu_matkul:
                    replaceFragment(new FragmentMatkul());
                    break;
                case R.id.about:
                    replaceFragment(new FragmentAbout());
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.containerLayout, fragment);
        fragmentTransaction.commit();
    }
}