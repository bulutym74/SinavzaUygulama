package com.example.fethi.sinavzauygulama.ogrenci.ogrenciFragments.dahaFazlaTAB.profil;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.fethi.sinavzauygulama.R;
import com.example.fethi.sinavzauygulama.activities.UserInfoItem;
import com.example.fethi.sinavzauygulama.diger.Islevsel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;

import static io.realm.internal.SyncObjectServerFacade.getApplicationContext;

public class BolumDegistirFragment extends Fragment {

    int index = 0;
    FloatingActionButton btn_devam;

    JSONObject res;
    String token;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bolum_degistir, container, false);

        btn_devam = view.findViewById(R.id.btn_devam);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_left);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        onBackPressed();
                    }
                }, 100);
            }
        });

        final Spinner spinner = view.findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.gruplar_alan, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                index = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btn_devam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog dialog = ProgressDialog.show(getActivity(), "Yükleniyor", "Lütfen bekleyin...", true);
                dialog.show();

                int bolum;
                if (index == 3)
                    bolum = 6;
                else
                    bolum = index + 1;

                final JSONObject parameters = new JSONObject();

                try {
                    parameters.accumulate("bolum", bolum);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                try {
                    Realm realm = Realm.getDefaultInstance();
                    token = realm.where(UserInfoItem.class).findAll().get(0).getToken();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                JsonObjectRequest objectRequest = new JsonObjectRequest(
                        Request.Method.POST,
                        Islevsel.bolumDegistirURL,
                        parameters,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(final JSONObject response) {
                                dialog.dismiss();
                                try {
                                    if (response.getBoolean("isFailed")) {
                                        Log.e("FAILED : ", response.getString("message"));

                                        Toast toast = Toast.makeText(getApplicationContext(), "Bağlantı hatası!", Toast.LENGTH_SHORT);
                                        toast.setGravity(Gravity.BOTTOM, 0, 300);
                                        toast.show();
                                    } else {
                                        res = response;

                                        Toast toast = Toast.makeText(getActivity(), "Bölümünüz "+ spinner.getSelectedItem().toString()+" olarak değiştirildi", Toast.LENGTH_LONG);
                                        toast.setGravity(Gravity.BOTTOM, 0, 500);
                                        toast.show();

                                    }

                                } catch (JSONException e) {

                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("ERROR ", error.toString());

                                dialog.dismiss();
                                Toast toast = Toast.makeText(getApplicationContext(), "Bağlantı hatası!", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.BOTTOM, 0, 300);
                                toast.show();
                            }
                        }
                ) {
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

        return view;
    }

    public void onBackPressed()
    {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.popBackStack();
    }
}
