package kz.kaznitu.android.examples.apps;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


public class AppController extends Application {
    private RequestQueue mRequestQueue ;
    private static AppController mInstance ;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this ;
    }

    public static AppController getInstance(){
            return mInstance ;
    }

    public RequestQueue getRequestQueue(){
        if(mRequestQueue == null)
            mRequestQueue = Volley.newRequestQueue(getApplicationContext()) ;
        return  mRequestQueue ;
    }
}
