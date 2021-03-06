package com.atsar.minhajul;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.atsar.minhajul.activity.JadwalActivity;
import com.atsar.minhajul.activity.NotifikasiActivity;
import com.atsar.minhajul.app.Config;
import com.atsar.minhajul.model.ListRadio;
import com.atsar.minhajul.model.ModelFcm;
import com.atsar.minhajul.model.ModelRadio;
import com.atsar.minhajul.player.PlaybackStatus;
import com.atsar.minhajul.player.RadioManager;
import com.atsar.minhajul.service.MyService;
import com.atsar.minhajul.util.Server;
import com.atsar.minhajul.util.ShoutcastListAdapter;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.playTrigger)
    ImageButton trigger;

//    @BindView(R.id.pullToRefresh)
//    SwipeRefreshLayout swipeRefreshLayout;
//
//    @BindView(R.id.naView)
//    NavigationView navigationView;
//
//    @BindView(R.id.appbar)
//    AppBarLayout appBarLayout;

    @BindView(R.id.listview)
    ListView listView;


    @BindView(R.id.name)
    TextView textView;

    @BindView(R.id.gbr)
    ImageView gbr;

    @BindView(R.id.sub_player)
    View subPlayer;

//    @BindView(R.id.txt_reg_id)
//    TextView txtRegId;
//
//    @BindView(R.id.txt_push_message)
//    TextView txtMessage;

    RadioManager radioManager;

    String streamURL,judul,pembicara,versi;

    private ProgressDialog progressDialog;

    String os = android.os.Build.VERSION.RELEASE;

    private BroadcastReceiver mRegistrationBroadcastReceiver;

    private static final String TAG = MainActivity.class.getSimpleName();

    private DrawerLayout mDrawer;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("test "+"create");
        super.onCreate(savedInstanceState);


        if(Integer.parseInt(os.substring(0,1))<=5){
            setContentView(R.layout.activity_os);
        }else{
            setContentView(R.layout.activity_os);
        }


        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        radioManager = RadioManager.with(this);

        progressDialog = new ProgressDialog(this);


        getRadio();

        mDrawer=(DrawerLayout) findViewById(R.id.drawer_layout);
        nvDrawer=(NavigationView) findViewById(R.id.naView);
        setupDrawer(nvDrawer);
        drawerToggle= setupDrawerToggle();
        mDrawer.addDrawerListener(drawerToggle);
//        nvDrawer.getMenu().getItem(0).setChecked(true);
//        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
//        mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
//        NavigationView nvDrawer = (NavigationView) findViewById(R.id.nv);
//        mDrawerLayout.addDrawerListener(mToggle);
//        mToggle.syncState();
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        setupDrawerContent(nvDrawer);

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                    displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");

                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();

//                    txtMessage.setText(message);
                }
            }
        };



        displayFirebaseRegId();

//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            int Refreshcounter = 1; //Counting how many times user have refreshed the layout
//
//            @Override
//            public void onRefresh() {
//                //Here you can update your data from internet or from local SQLite data
//
//                getRadio();
//                swipeRefreshLayout.setRefreshing(false);
//            }
//        });
    }


    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);

        Log.e(TAG, "Firebase reg id: " + regId);

        if (!TextUtils.isEmpty(regId)) {
            getPostFCM(regId);
        }
//            txtRegId.setText("Firebase Reg Id: " + regId);
//        else
//            txtRegId.setText("Firebase Reg Id is not received yet!");
    }

    private void setupDrawer(NavigationView nview){
        nview.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener(){
                    public boolean onNavigationItemSelected(MenuItem menu){
                        Terpilih(menu);
                        return true;
                    }
                });
    }
    private ActionBarDrawerToggle setupDrawerToggle(){
        return new ActionBarDrawerToggle(this, mDrawer, toolbar,
                R.string.open, R.string.close);
    }

    private void Terpilih(MenuItem menu) {
        switch(menu.getItemId()){
            case R.id.jadwal:
                startActivity(new Intent(this, JadwalActivity.class));
//                overridePendingTransition(R.anim.slide_no_move, R.anim.fade);
//                Intent injadwal = new Intent(this, JadwalActivity.class);
//                startActivity(injadwal);
                break;

        }
//        try{
//            frag=(Fragment) fragclass.newInstance();
//        }catch(Exception e){}
//        menu.setChecked(true);
//        setTitle(menu.getTitle());
        mDrawer.closeDrawers();


    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        drawerToggle.onConfigurationChanged(newConfig);
//    }


    private void getRadio() {
        progressDialog.setMessage("Memuat Data ...");
        progressDialog.show();

        MyService service = Server.getClient().create(MyService.class);
        Call<ListRadio> call = service.getRadio();
        call.enqueue(new Callback<ListRadio>() {
            @Override
            public void onResponse(Call<ListRadio> call, Response<ListRadio> response) {
                ListRadio listRadio = response.body();

                if (listRadio.getSuccess() == true) {
                    List<ModelRadio> modelRadios = listRadio.getData();
                    ShoutcastListAdapter shoutcast= new ShoutcastListAdapter(MainActivity.this, modelRadios);
                    listView.setAdapter(shoutcast);
                    progressDialog.dismiss();
                }else{
                    Toast.makeText(MainActivity.this,"Afwan, Kesalahan Server", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<ListRadio> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Internet Tidak Aktif", Toast.LENGTH_SHORT).show();
                if (progressDialog != null && progressDialog.isShowing()){
                    progressDialog.dismiss();
                }

            }
        });
    }

    @Override
    public void onStart() {
        System.out.println("test "+"start");
        super.onStart();

        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        System.out.println("test "+"stop");
        EventBus.getDefault().unregister(this);

        super.onStop();
    }

    @Override
    protected void onDestroy() {
        System.out.println("test "+"destroy");
        radioManager.unbind();

        super.onDestroy();
    }

    @Override
    protected void onResume() {
        System.out.println("test "+"resume");
        super.onResume();

        radioManager.bind();
    }

    @Override
    public void onBackPressed() {
        System.out.println("test "+"pres");
//        radioManager.playOrPause(streamURL);
//        finish();
        moveTaskToBack(true);
    }

    @Subscribe
    public void onEvent(String status){
        System.out.println("test "+"event");
        switch (status){

            case PlaybackStatus.LOADING:

                // loading

                break;

            case PlaybackStatus.ERROR:

                Toast.makeText(this, R.string.no_stream, Toast.LENGTH_SHORT).show();

                break;

        }

        trigger.setImageResource(status.equals(PlaybackStatus.PLAYING)
                ? R.drawable.ic_stop_white
                : R.drawable.ic_stop_white);

    }

    @OnClick(R.id.playTrigger)
    public void onClicked(){
        System.out.println("test "+"click");
        System.out.println(streamURL);
        if(TextUtils.isEmpty(streamURL)) return;
        subPlayer.setVisibility(View.GONE);
        radioManager.playOrPause(streamURL,judul,pembicara);
    }

    @OnItemClick(R.id.listview)
    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
        System.out.println("test "+streamURL);
        ModelRadio shoutcast = (ModelRadio) parent.getItemAtPosition(position);
        if(shoutcast == null){
            return;
        }

        if(!shoutcast.getPembicara().equals("")) {
            textView.setText(shoutcast.getPembicara());

            subPlayer.setVisibility(View.VISIBLE);

            streamURL = shoutcast.getUrl();
            judul = shoutcast.getJudul();
            pembicara = shoutcast.getPembicara();

            Picasso.with(MainActivity.this)
                    .load("http://alilmu.net/images/" + shoutcast.getGambar())
                    .into(gbr);

            radioManager.playOrPause(streamURL, judul, pembicara);
        }else{
            Toast.makeText(MainActivity.this,"Afwan, Saluran Radio sedang OFF", Toast.LENGTH_SHORT).show();
        }
//        textView.setText(getString(R.string.live_broadcast));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_refresh:
                getRadio();
                break;
            case R.id.action_notif:
                startActivity(new Intent(this, NotifikasiActivity.class));
                break;
        }


        return super.onOptionsItemSelected(item);
    }

    private void getPostFCM(String idFcm) {
        MyService service = Server.getClient().create(MyService.class);
        service.getPostFcm(idFcm);
        Call<ModelFcm> call = service.getPostFcm(idFcm);
        call.enqueue(new Callback<ModelFcm>() {
            @Override
            public void onResponse(Call<ModelFcm> call, Response<ModelFcm> response) {
                ModelFcm fcmModel = response.body();
//                Boolean sukses = eventModel.getSuccess(); // never used
                boolean success = fcmModel.getSuccess();

                if (success == true) {
//                    Toast.makeText(MainActivity.this, "Kirim FCM Token Sukses", Toast.LENGTH_SHORT).show();
                } else {
//                    Toast.makeText(MainActivity.this, "Kirim FCM Token gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelFcm> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                if (progressDialog != null && progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
            }
        });
    }
}
