package com.example.fethi.sinavzauygulama.veli.veliFragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.example.fethi.sinavzauygulama.R;
import com.example.fethi.sinavzauygulama.veli.veliAdapters.VeliExpLVAdapterYapilmamisOdevler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VeliYapilmamisOdevlerFragment extends Fragment {

    public List<String> list_parent;
    public VeliExpLVAdapterYapilmamisOdevler expand_adapter;
    public HashMap<String, List<String>> list_child;
    public ExpandableListView expandlist_view_odevler;
    public List<String> turkce_list;
    public List<String> matematik_list;
    public List<String> fizik_list;
    public List<String> kimya_list;
    public List<String> biyoloji_list;
    public int last_position = -1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.v_fragment_yapilmamis_odevler,container,false);

        expandlist_view_odevler = view.findViewById(R.id.expand_lv_yapilmamis_odevler);

        Hazırla(); // expandablelistview içeriğini hazırlamak için

        // ListItemFiltreAdapter sınıfımızı oluşturmak için başlıklardan oluşan listimizi ve onlara bağlı olan elemanlarımızı oluşturmak için HaspMap türünü yolluyoruz
        expand_adapter = new VeliExpLVAdapterYapilmamisOdevler(getActivity(), list_parent, list_child);
        expandlist_view_odevler.setAdapter(expand_adapter);  // oluşturduğumuz adapter sınıfını set ediyoruz
        expandlist_view_odevler.setClickable(true);

        expandlist_view_odevler.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                VeliYapilmamisKonularFragment nextFrag= new VeliYapilmamisKonularFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_left)
                        .replace(R.id.fragment_container, nextFrag,"findThisFragment")
                        .addToBackStack(null)
                        .commit();
                return false;
            }

        });
        /*expandlist_view.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                String child_name = (String) expand_adapter.getChild(groupPosition, childPosition);
                //Toast.makeText(getApplicationContext(),"hey" + child_name, Toast.LENGTH_LONG).show();

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage(child_name)
                        .setTitle("Mobilhanem Expandablelistview")
                        .setCancelable(false)
                        .setPositiveButton("TAMAM", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();


                return false;
            }
        });*/

        expandlist_view_odevler.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {//bir child açıldığında diğerlerini kapat
            @Override
            public void onGroupExpand(int groupPosition) {  //en fazla bir parentı aç
                if (last_position != -1 && last_position != groupPosition) {
                    expandlist_view_odevler.collapseGroup(last_position);
                }
                last_position = groupPosition;

            }
        });

        return view;
    }

    public void Hazırla() {
        list_parent = new ArrayList<>();  // başlıklarımızı listemelek için oluşturduk
        list_child = new HashMap<>(); // başlıklara bağlı elemenları tutmak için oluşturduk

        list_parent.add("Türkçe");  // ilk başlığı giriyoruz
        list_parent.add("Matematik");   // ikinci başlığı giriyoruz
        list_parent.add("Fizik");   // ikinci başlığı giriyoruz
        list_parent.add("Kimya");   // ikinci başlığı giriyoruz
        list_parent.add("Biyoloji");   // ikinci başlığı giriyoruz

        turkce_list = new ArrayList<>();  // ilk başlık için info2 elemanları tanımlıyoruz
        turkce_list.add("Türkçe Kitap 1");
        turkce_list.add("Türkçe Kitap 2");
        turkce_list.add("Türkçe Kitap 3");
        turkce_list.add("Türkçe Kitap 4");

        matematik_list = new ArrayList<>();  // ilk başlık için info2 elemanları tanımlıyoruz
        matematik_list.add("Matematik Kitap 1");
        matematik_list.add("Matematik Kitap 2");
        matematik_list.add("Matematik Kitap 3");

        fizik_list = new ArrayList<>();  // ilk başlık için info2 elemanları tanımlıyoruz
        fizik_list.add("Fizik Kitap 1");
        fizik_list.add("Fizik Kitap 2");

        kimya_list = new ArrayList<>();  // ilk başlık için info2 elemanları tanımlıyoruz
        kimya_list.add("Kimya Kitap 1");
        kimya_list.add("Kimya Kitap 2");

        biyoloji_list = new ArrayList<>();  // ilk başlık için info2 elemanları tanımlıyoruz
        biyoloji_list.add("Biyoloji Kitap 1");
        biyoloji_list.add("Biyoloji Kitap 2");


        list_child.put(list_parent.get(0), turkce_list); // ilk başlığımızı ve onların elemanlarını HashMap sınıfında tutuyoruz
        list_child.put(list_parent.get(1), matematik_list); // ilk başlığımızı ve onların elemanlarını HashMap sınıfında tutuyoruz
        list_child.put(list_parent.get(2), fizik_list); // ilk başlığımızı ve onların elemanlarını HashMap sınıfında tutuyoruz
        list_child.put(list_parent.get(3), kimya_list); // ilk başlığımızı ve onların elemanlarını HashMap sınıfında tutuyoruz
        list_child.put(list_parent.get(4), biyoloji_list); // ilk başlığımızı ve onların elemanlarını HashMap sınıfında tutuyoruz


    }


}


