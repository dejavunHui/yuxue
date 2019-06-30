package com.example.androidmvp.mvp.show.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidmvp.R;
import com.example.androidmvp.mvp.entity.show.ImageResult;
import com.example.androidmvp.mvp.entity.db.ShowPage;
import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;

import java.util.List;

public class ReCyclerAdapter extends RecyclerView.Adapter<ReCyclerAdapter.MyViewHolder> {

    private Context context;
    List<ShowPage> showPages;

    private OnItemClickListener onItemClickListener = null;


    public ReCyclerAdapter(Context context, List<ShowPage> showPages) {
        this.context = context;
        this.showPages = showPages;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = View.inflate(context, R.layout.fragment_showitem, null);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
//        Glide.with(context).load(showPageResults.get(position).getAutor().getIcon()).into(holder.userIcon);
//        holder.userName.setText(showPageResults.get(position).getAutor().getUsername());
//        holder.showInfo.setText(showPageResults.get(position).getContent());
//        holder.nineGridImageView.setImagesData(showPageResults.get(position).getImages());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, showPages, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return showPages.size();
    }

    public void setShowPageResults(List<ShowPage> showPages) {
        this.showPages = showPages;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView userIcon;
        TextView userName;
        TextView showInfo;
        NineGridImageView<String> nineGridImageView;


        NineGridImageViewAdapter viewAdapter;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            userIcon = itemView.findViewById(R.id.show_user_image);
            userName = itemView.findViewById(R.id.show_user_name);
            showInfo = itemView.findViewById(R.id.showinfo);
            nineGridImageView = itemView.findViewById(R.id.showimages);


            viewAdapter = new NineGridImageViewAdapter<ImageResult>() {
                @Override
                protected void onDisplayImage(Context context, ImageView imageView, ImageResult imageResult) {
//                    Glide.with(context).load(imageResult.getUrl()).into(imageView);
                }

                @Override
                protected void onItemImageClick(Context context, int index, List list) {
                    super.onItemImageClick(context, index, list);
                }

                @Override
                protected ImageView generateImageView(Context context) {
                    return super.generateImageView(context);
                }
            };
            nineGridImageView.setAdapter(viewAdapter);
        }

    }

    public interface OnItemClickListener {
        void onItemClick(View view, List<ShowPage> showPageResults, int position);
    }
}
