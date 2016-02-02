package kz.kaznitu.android.examples.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import kz.kaznitu.android.examples.R;
import kz.kaznitu.android.examples.models.Movie;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.MyViewHolder>{

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        CardView cardView ;
        ImageView thumbnail ;
        TextView title, genre, rating, releaseYear ;

        public MyViewHolder(View view){
            super(view) ;
            cardView = (CardView)view.findViewById(R.id.card) ;
            thumbnail = (ImageView)view.findViewById(R.id.thumbnail) ;
            title = (TextView)view.findViewById(R.id.title) ;
            genre = (TextView)view.findViewById(R.id.genre) ;
            rating = (TextView)view.findViewById(R.id.rating) ;
            releaseYear = (TextView)view.findViewById(R.id.releaseYear) ;
        }
    }

    List<Movie> moviesList ;
    Context mContext ;

    public RVAdapter(Context context){
        mContext = context ;
    }

    public void setData(List<Movie> movies){
        moviesList = movies ;
    }

    @Override
    public int getItemCount() {
        return moviesList.size() ;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Movie movie = moviesList.get(position) ;

        holder.title.setText(movie.getTitle());
        Picasso.with(mContext).load(movie.getThumbnailUrl()).into(holder.thumbnail);
        holder.rating.setText(Double.toString(movie.getRating()));
        holder.releaseYear.setText(Integer.toString(movie.getYear()));
        String s = "" ;
        for(String str : movie.getGenre())
            s += str + ", " ;
        holder.genre.setText(s);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row,
                parent, false) ;
        return new MyViewHolder(view);
    }
}
