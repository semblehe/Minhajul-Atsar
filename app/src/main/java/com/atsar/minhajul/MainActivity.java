package com.atsar.minhajul;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.atsar.minhajul.model.ListRadio;
import com.atsar.minhajul.model.ModelRadio;
import com.atsar.minhajul.player.PlaybackStatus;
import com.atsar.minhajul.player.RadioManager;
import com.atsar.minhajul.service.MyService;
import com.atsar.minhajul.util.Server;
import com.atsar.minhajul.util.ShoutcastListAdapter;

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

//    @BindView(R.id.nestedview)
//    NestedScrollView nestedView;
//
//    @BindView(R.id.coordinator)
//    CoordinatorLayout coordinatorLayout;
//
//    @BindView(R.id.appbar)
//    AppBarLayout appBarLayout;

    @BindView(R.id.listview)
    ListView listView;


    @BindView(R.id.name)
    TextView textView;

    @BindView(R.id.sub_player)
    View subPlayer;

    RadioManager radioManager;

    String streamURL,judul,pembicara;

    private ProgressDialog progressDialog;

//    private DrawerLayout mDrawerLayout;
//    private ActionBarDrawerToggle mToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("test "+"create");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        radioManager = RadioManager.with(this);

        progressDialog = new ProgressDialog(this);

        getRadio();
//        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
//        mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
//        NavigationView nvDrawer = (NavigationView) findViewById(R.id.nv);
//        mDrawerLayout.addDrawerListener(mToggle);
//        mToggle.syncState();
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        setupDrawerContent(nvDrawer);

//        MyService apiService = Server.getClient().create(MyService.class);
//        Call<ListRadio> call = apiService.getRadio();
//        ListRadio listradio = response.body();
//        List<ModelRadio> list = new ArrayList<>();
////
//        ShoutcastListAdapter shoutcast= new ShoutcastListAdapter(this,list);
//        listView.setAdapter(shoutcast);
//        listView.setAdapter(shoutcast);


//            nestedView.scrollTo(0, 0);
//
//            nestedView.fullScroll(View.FOCUS_DOWN);
//            nestedView.fullScroll(View.FOCUS_UP);
//
//        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
//        AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();
//        if (behavior != null) {
//            behavior.onNestedFling(coordinatorLayout, appBarLayout, null, 0, 10000, true);
//        }
    }

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
                ? R.drawable.ic_pause_black
                : R.drawable.ic_play_arrow_black);

    }

    @OnClick(R.id.playTrigger)
    public void onClicked(){
        System.out.println("test "+"click");
        System.out.println(streamURL);
        if(TextUtils.isEmpty(streamURL)) return;

        radioManager.playOrPause(streamURL,judul,pembicara);
    }

    @OnItemClick(R.id.listview)
    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
        System.out.println("test "+streamURL);
        ModelRadio shoutcast = (ModelRadio) parent.getItemAtPosition(position);
        if(shoutcast == null){

            return;

        }

        System.out.println(view);

        textView.setText(shoutcast.getPembicara());

        subPlayer.setVisibility(View.VISIBLE);

        streamURL = shoutcast.getUrl();
        judul = shoutcast.getJudul();
        pembicara = shoutcast.getPembicara();

        radioManager.playOrPause(streamURL,judul,pembicara);
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
        }

        return super.onOptionsItemSelected(item);
    }
}
