package com.atsar.minhajul.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.atsar.minhajul.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RekamanActivity extends AppCompatActivity {

    @BindView(R.id.tooljadwal)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.jadwal);


        ButterKnife.bind(this);

        toolbar.setTitle("Rekaman");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



    }


}
