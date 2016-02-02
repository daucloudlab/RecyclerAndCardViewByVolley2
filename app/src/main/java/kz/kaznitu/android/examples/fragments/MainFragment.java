package kz.kaznitu.android.examples.fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import kz.kaznitu.android.examples.R;
import kz.kaznitu.android.examples.adapters.RVAdapter;
import kz.kaznitu.android.examples.apps.AppController;
import kz.kaznitu.android.examples.models.Movie;

public class MainFragment extends Fragment{
    private List<Movie> movies = new ArrayList<Movie>();
    private String url = "http://api.androidhive.info/json/movies.json";
    RVAdapter adapter ;
    ProgressDialog dialog ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false) ;
        RecyclerView recyclerView = (RecyclerView)rootView.findViewById(R.id.rv) ;
        adapter = new RVAdapter(getActivity()) ;
        LinearLayoutManager llm = new LinearLayoutManager(getActivity()) ;
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);
        adapter.setData(movies);

        dialog = new ProgressDialog(getActivity()) ;
        dialog.setMessage("Күту тұрыңыз...");
        dialog.setCancelable(false);

        getAndInitData();

        return rootView ;
    }

    public void getAndInitData(){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try{
                            Log.d("MYAPP", response.toString()) ;
                            showDialog();
                            for(int i = 0; i < response.length(); i++){
                                JSONObject object =response.getJSONObject(i) ;
                                Movie movie = new Movie();
                                movie.setTitle(object.getString("title"));
                                movie.setThumbnailUrl(object.getString("image"));
                                movie.setRating(object.getDouble("rating")) ;
                                movie.setYear(object.getInt("releaseYear"));

                                // Genre is json array
                                JSONArray genreArry = object.getJSONArray("genre");
                                ArrayList<String> genre = new ArrayList<String>();
                                for (int j = 0; j < genreArry.length(); j++) {
                                    genre.add((String) genreArry.get(j));
                                }
                                movie.setGenre(genre);

                                movies.add(movie) ;
                            }
                            hideDialog();
                            adapter.notifyDataSetChanged();
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERROR!!!", error.toString()) ;
            }
        }) ;
        AppController.getInstance().getRequestQueue().add(jsonArrayRequest) ;
    }

    public void showDialog(){
        if(!dialog.isShowing())
            dialog.show();
    }

    public void hideDialog(){
        if(dialog.isShowing())
            dialog.hide();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        dialog.hide();
        dialog.dismiss();
    }
}
