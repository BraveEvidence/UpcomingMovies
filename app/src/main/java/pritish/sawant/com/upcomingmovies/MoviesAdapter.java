package pritish.sawant.com.upcomingmovies;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by pritish on 29/9/17.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {

    private List<Movies> moviesList;
    private Movies movies;
    private Context context;

    public MoviesAdapter(List<Movies> moviesList,Context context) {
        this.moviesList = moviesList;
        this.context=context;
    }

    @Override
    public MoviesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MoviesAdapter.MyViewHolder holder, int position) {
        movies = moviesList.get(position);

        Glide.with(context).load(movies.getPosterpath()).into(holder.imageView);
        holder.titletextView.setText(movies.getTitle());

        holder.releasedate.setText(movies.getReleasedate());
        if(movies.isAdult()){
            holder.rating.setText("A");
        }else{
            holder.rating.setText("U/A");
        }

    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView titletextView,releasedate,rating;
        private ImageView imageView;


        public MyViewHolder(View itemView) {
            super(itemView);
            titletextView=(TextView)itemView.findViewById(R.id.moviename);
            releasedate=(TextView)itemView.findViewById(R.id.moviereleasedate);
            rating=(TextView)itemView.findViewById(R.id.movierating);
            imageView=(ImageView) itemView.findViewById(R.id.movieimage);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            int position=getAdapterPosition();
            Intent intent=new Intent(v.getContext(),FullMoviesActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Movies news=moviesList.get(position);
            intent.putExtra("MyMovies",news);

            context.startActivity(intent);

        }
    }

    public void setNewsData(List<Movies> moviesData) {

        moviesList = moviesData;

        notifyDataSetChanged();

    }
}