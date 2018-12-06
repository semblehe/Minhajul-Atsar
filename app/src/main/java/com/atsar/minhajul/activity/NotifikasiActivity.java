package com.atsar.minhajul.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.atsar.minhajul.R;
import com.atsar.minhajul.model.ListNotif;
import com.atsar.minhajul.model.ModelNotif;
import com.atsar.minhajul.service.MyService;
import com.atsar.minhajul.util.Server;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yumna on 02/12/18.
 */

public class NotifikasiActivity  extends AppCompatActivity {

    @BindView(R.id.toolnotif)
    Toolbar toolbar;

    @BindView(R.id.my_recycleView)
    RecyclerView recyclerView;


    private ProgressDialog progressDialog;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.notifikasi);

        ButterKnife.bind(this);

        toolbar.setTitle("Pemberitahuan");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        progressDialog = new ProgressDialog(this);

        getListNotif();

    }

    private void getListNotif() {
        MyService service = Server.getClient().create(MyService.class);
        Call<ListNotif> call = service.getNotif();
        call.enqueue(new Callback<ListNotif>(){
            @Override
            public void onResponse(Call<ListNotif> call, Response<ListNotif> response){
                ListNotif listNotifikasi = response.body();

                if (listNotifikasi.getSuccess() == true){
                    List<ModelNotif> notifikasiModels = listNotifikasi.getData();
                    NotifikasiAdapter notifikasiAdapter = new NotifikasiAdapter(notifikasiModels, getApplicationContext());
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setHasFixedSize(true);
                    layoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(notifikasiAdapter);
                }
            }
            @Override
            public void onFailure(Call<ListNotif> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class NotifikasiAdapter extends RecyclerView.Adapter<NotifikasiAdapter.ViewHolder> {
        List<ModelNotif> notifikasiModelList;
        Context context;

        public NotifikasiAdapter(List<ModelNotif> notifikasiModelList, Context context) {
            this.notifikasiModelList = notifikasiModelList;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.rv_notif, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            ModelNotif modelNotif = notifikasiModelList.get(position);
            holder.judul_tv.setText(modelNotif.getJudul());
            holder.isi_tv.setText(modelNotif.getIsi());
            holder.tgl_tv.setText(modelNotif.getTgl());
            holder.jam_tv.setText(modelNotif.getJam());
        }

        @Override
        public int getItemCount()  {
            return notifikasiModelList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            TextView isi_tv,judul_tv,tgl_tv,jam_tv;

            public ViewHolder(View itemView) {
                super(itemView);
                judul_tv = (TextView) itemView.findViewById(R.id.tv_judul);
                isi_tv = (TextView) itemView.findViewById(R.id.tv_isi);
                tgl_tv = (TextView) itemView.findViewById(R.id.tv_tgl);
                jam_tv = (TextView) itemView.findViewById(R.id.tv_jam);
                itemView.setOnClickListener(this);
            }

            public void onClick(View view) {
            }


        }
    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        System.out.println(id);


        return super.onOptionsItemSelected(item);
    }



}
