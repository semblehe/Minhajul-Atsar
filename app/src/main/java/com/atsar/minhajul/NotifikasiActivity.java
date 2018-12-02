package com.atsar.minhajul;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by yumna on 02/12/18.
 */

public class NotifikasiActivity  extends AppCompatActivity {

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("test " + "create");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.notifikasi);

        ButterKnife.bind(this);

        progressDialog = new ProgressDialog(this);

    }


}
