package com.example.fethi.sinavzauygulama.activities;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fethi.sinavzauygulama.R;
import com.example.fethi.sinavzauygulama.diger.BottomNavigationViewHelper;
import com.example.fethi.sinavzauygulama.veli.veliFragments.VeliCozulenlerFragment;
import com.example.fethi.sinavzauygulama.veli.veliFragments.VeliOzetFragment;
import com.example.fethi.sinavzauygulama.veli.veliFragments.VeliYapilmamisOdevlerFragment;

public class VeliAnasayfaActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.v_activity_veli_anasayfa);

        BottomNavigationView navigation = findViewById(R.id.navigation_veli);
        navigation.setOnNavigationItemSelectedListener(this);
        BottomNavigationViewHelper.disableShiftMode(navigation);

        BottomNavigationMenuView menuView = (BottomNavigationMenuView) navigation.getChildAt(0);
        for (int i = 0; i < menuView.getChildCount(); i++) {
            final View iconView = menuView.getChildAt(i).findViewById(android.support.design.R.id.icon);
            final ViewGroup.LayoutParams layoutParams = iconView.getLayoutParams();
            final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();

            layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 32, displayMetrics);
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 32, displayMetrics);
            iconView.setLayoutParams(layoutParams);
        }

        loadFragment(new VeliOzetFragment());
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment = null;

        switch (item.getItemId()) {

            case R.id.nav_ozet:
                fragment = new VeliOzetFragment();
                break;

            case R.id.nav_yapilmamis_odevler:
                fragment = new VeliYapilmamisOdevlerFragment();
                break;

            case R.id.nav_cozulen_sorular:
                fragment = new VeliCozulenlerFragment();
                break;

        }

        return loadFragment(fragment);
    }


    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            moveTaskToBack(true);
            return;
        }

        this.doubleBackToExitPressedOnce = true;

        Toast toast = Toast.makeText(getApplicationContext(), "Çıkmak için tekrar basınız", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 300);
        toast.show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }


}
