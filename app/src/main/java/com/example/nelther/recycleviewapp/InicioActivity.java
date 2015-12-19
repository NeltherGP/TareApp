package com.example.nelther.recycleviewapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.example.nelther.recycleviewapp.materia.FragmentoMateria;
import com.example.nelther.recycleviewapp.materia.addMateria;
import com.example.nelther.recycleviewapp.profesor.FragmentoProfesor;
import com.example.nelther.recycleviewapp.profesor.addProfesor;
import com.example.nelther.recycleviewapp.tareas.AddTarea;
import com.example.nelther.recycleviewapp.tareas.FragmentoTarea;


/**
 * Created by nelther on 04/11/2015.
 */
public class InicioActivity extends AppCompatActivity {
    Toolbar toolbar;
    ViewPager viewPager;
    TabLayout tabLayout;
    ImageButton imgbtn;
    Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio_activity);
        context=this;

        imgbtn = (ImageButton) findViewById(R.id.fab);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
        imgbtn.setOnClickListener(new ExternalOnClickListener(context, tabLayout.getSelectedTabPosition()));

      tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                switch (tab.getPosition()) {
                    case 0:
                        imgbtn.setOnClickListener(null);
                        imgbtn.setOnClickListener(new ExternalOnClickListener(context,tabLayout.getSelectedTabPosition()));
                        break;

                    case 1:

                        imgbtn.setOnClickListener(null);
                        imgbtn.setOnClickListener(new ExternalOnClickListener(context,tabLayout.getSelectedTabPosition()));
                        break;
                    case 2:
                        imgbtn.setOnClickListener(null);
                        imgbtn.setOnClickListener(new ExternalOnClickListener(context,tabLayout.getSelectedTabPosition()));

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });




    }



    public void setupViewPager (ViewPager viewPager){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new FragmentoTarea(getResources().getColor(android.R.color.background_light),this),"TAREAS");
       adapter.addFrag(new FragmentoMateria(getResources().getColor(android.R.color.background_light),this),"MATERIAS");
        adapter.addFrag(new FragmentoProfesor(getResources().getColor(android.R.color.background_light),this),"PROFESORES");

        viewPager.setAdapter(adapter);
    }



}

class ExternalOnClickListener implements View.OnClickListener {
    Context c;
    int tabPos;

    public ExternalOnClickListener(Context c, int tabPos) {
        this.c=c;
        this.tabPos=tabPos;
        Log.i("Moviles","Set Listener");
        // keep references for your onClick logic
    }

    @Override
    public void onClick(View v) {
        Log.i("Moviles","Pestaña: "+tabPos);
        Intent intent;
        switch (tabPos){
            case 0:
                 intent = new Intent(c,AddTarea.class);
                c.startActivity(intent);
                break;
            case 1:
                 intent = new Intent(c,addMateria.class);
                c.startActivity(intent);
                break;
            case 2:
               intent = new Intent(c,addProfesor.class);
                c.startActivity(intent);
        }
    }

}
