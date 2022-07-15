package com.example.uas_pbm.model;

public class modelMatkul {

    private String namamk, jammk, harimk,kelas, id;

    public modelMatkul(String namamk, String jammk, String harimk, String kelas) {
        this.namamk = namamk;
        this.jammk = jammk;
        this.harimk = harimk;
        this.kelas = kelas;
    }

    public String getNamamk() {
        return namamk;
    }

    public void setNamamk(String namamk) {
        this.namamk = namamk;
    }

    public String getJammk() {
        return jammk;
    }

    public void setJammk(String jammk) {
        this.jammk = jammk;
    }

    public String getHarimk() {
        return harimk;
    }

    public void setHarimk(String harimk) {
        this.harimk = harimk;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
