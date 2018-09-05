package com.example.fethi.sinavzauygulama.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.fethi.sinavzauygulama.R;
import com.example.fethi.sinavzauygulama.diger.App;
import com.example.fethi.sinavzauygulama.diger.Islevsel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import io.realm.Realm;

import static io.realm.internal.SyncObjectServerFacade.getApplicationContext;

public class OnayBekleniyor extends AppCompatActivity {

    boolean doubleBackToExitPressedOnce = false;
    TextView onay,ret1,ret2;
    ImageView log_out;
    LinearLayout onayBekleniyorView,kayıtReddedildiView;
    FloatingActionButton btn_devam;
    AppCompatEditText code;

    Realm realm = Realm.getDefaultInstance();
    JSONObject res;
    String token;

    int tur = 0;
    boolean ret = false;
    boolean sor = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_onay_bekleniyor);

        onayBekleniyorView = findViewById(R.id.onayBekleniyorView);
        kayıtReddedildiView = findViewById(R.id.kayıtReddedildiView);
        btn_devam = findViewById(R.id.btn_devam);
        code = findViewById(R.id.code);
        ret1 = findViewById(R.id.txt_ret1);
        ret2 = findViewById(R.id.txt_ret2);

        log_out = findViewById(R.id.log_out);
        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sor = false;
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.where(UserInfoItem.class).findAll().deleteAllFromRealm();
                    }
                });

                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        onay = findViewById(R.id.txt_onay);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            ret = bundle.getBoolean("ret");
            tur = bundle.getInt("tur");
            if (tur == 1){
                onay.setText("Hesabınızın kurumunuz tarafından onaylanması bekleniyor");
                ret1.setText("Kaydınız kurum tarafından reddedildi!");
                ret2.setText("Yeni bir kurum koduyla kayıt olmayı deneyin");
                btn_devam.setVisibility(View.VISIBLE);
            }
            if (ret){
                onayBekleniyorView.setVisibility(View.GONE);
                kayıtReddedildiView.setVisibility(View.VISIBLE);
                btn_devam.setVisibility(View.VISIBLE);
                sor = false;
            }
        }

        final Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if (sor){

                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                    Realm realm = Realm.getDefaultInstance();
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            token = realm.where(UserInfoItem.class).findAll().get(0).getToken();
                        }
                    });

                    JsonObjectRequest objectRequest = new JsonObjectRequest(
                            Request.Method.GET,
                            Islevsel.isApprovedURL,
                            new JSONObject(),
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(final JSONObject response) {
                                    try {
                                        if (response.getBoolean("isFailed")) {

                                            Toast toast = Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_SHORT);
                                            toast.setGravity(Gravity.BOTTOM, 0, 300);
                                            toast.show();

                                        } else {
                                            res = response;
                                            Log.e("ISAPPROVED",""+res.getInt("isApproved"));

                                            switch (res.getInt("isApproved")){
                                                case 0:
                                                    kayıtReddedildiView.setVisibility(View.VISIBLE);
                                                    btn_devam.setVisibility(View.VISIBLE);
                                                    sor = false;
                                                    break;
                                                case 1:
                                                    sor = false;
                                                    Islevsel.updateURL();
                                                    if (tur == 0)
                                                        startActivity(new Intent(getApplicationContext(), OgrenciAnasayfaActivity.class));
                                                    else
                                                        startActivity(new Intent(getApplicationContext(), OgretmenAnasayfaActivity.class));
                                                    break;
                                            }
                                            timer.cancel();

                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.e("ERROR : ", error.toString());

                                    Toast toast = Toast.makeText(getApplicationContext(), "Bağlantı hatası!", Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.BOTTOM, 0, 300);
                                    toast.show();
                                }
                            }
                    ){
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            final Map<String, String> headers = new HashMap<>();
                            headers.putAll(super.getHeaders());

                            headers.put("Authorization", "Bearer " + token);

                            return headers;
                        }
                    };
                    requestQueue.add(objectRequest);

                }
            }
        };
        timer.schedule(timerTask,30000);

        btn_devam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final JSONObject parameters = new JSONObject();

                try {
                    parameters.accumulate("kod", code.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                token = realm.where(UserInfoItem.class).findAll().get(0).getToken();

                JsonObjectRequest objectRequest = new JsonObjectRequest(
                        Request.Method.POST,
                        Islevsel.yeniKodYollaURL,
                        parameters,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(final JSONObject response) {

                                try {
                                    if (response.getBoolean("isFailed")) {

                                        Toast toast = Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_SHORT);
                                        toast.setGravity(Gravity.BOTTOM, 0, 300);
                                        toast.show();

                                    } else {
                                        res = response;

                                        sor = true;
                                        kayıtReddedildiView.setVisibility(View.GONE);
                                        btn_devam.setVisibility(View.GONE);
                                        onayBekleniyorView.setVisibility(View.VISIBLE);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("ERROR : ", error.toString());

                                Toast toast = Toast.makeText(getApplicationContext(), "Bağlantı hatası!", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.BOTTOM, 0, 300);
                                toast.show();
                            }
                        }
                ){
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        final Map<String, String> headers = new HashMap<>();
                        headers.putAll(super.getHeaders());

                        headers.put("Authorization", "Bearer " + token);

                        return headers;
                    }
                };
                requestQueue.add(objectRequest);
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            moveTaskToBack(true);
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Çıkmak için tekrar basınız", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
