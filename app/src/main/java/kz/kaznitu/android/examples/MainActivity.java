package kz.kaznitu.android.examples;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import kz.kaznitu.android.examples.fragments.MainFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getFragmentManager() ;
        FragmentTransaction ft = fm.beginTransaction() ;
        ft.add(R.id.main_fragment_container,new MainFragment(), "main fragment") ;
        ft.commit() ;
    }
}