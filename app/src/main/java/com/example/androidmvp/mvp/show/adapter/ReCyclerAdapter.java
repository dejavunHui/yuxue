package com.example.androidmvp.mvp.show.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.androidmvp.R;
import com.example.androidmvp.common.Constant;
import com.example.androidmvp.data.httpdata.HttpData;
import com.example.androidmvp.mvp.entity.UserResult;
import com.example.androidmvp.mvp.entity.db.Remark;
import com.example.androidmvp.mvp.entity.db.ShowPage;
import com.example.androidmvp.util.PreviewImageLoader;
import com.example.androidmvp.widget.CircleImageView;
import com.example.androidmvp.widget.MyRemarkView;
import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;
import com.previewlibrary.GPreviewBuilder;
import com.previewlibrary.ZoomMediaLoader;
import com.example.androidmvp.mvp.entity.ThumbViewInfo;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class ReCyclerAdapter extends RecyclerView.Adapter<ReCyclerAdapter.MyViewHolder> {

    private static final String TAG = "ReCyclerAdapter";
    private Context context;
    List<ShowPage> showPages;

    private OnItemClickListener onItemClickListener = null;

    private MyRemarkView.ClickListener onRemarkItemListener = null;
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
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        loadUserIcon(holder.circleImageView, showPages.get(position).getUser());
        holder.userName.setText(showPages.get(position).getUser());
        holder.showInfo.setText(showPages.get(position).getContent());
        holder.nineGridImageView.setImagesData(showPages.get(position).getImages());
        if(showPages.get(position).getImages().size() == 0){
            holder.nineGridImageView.setVisibility(View.GONE);
        }else {
            holder.nineGridImageView.setVisibility(View.VISIBLE);
        }

        String time_ = showPages.get(position).getTimestamp();
        holder.timestamp.setText(time_.substring(0, 10) + " " + time_.substring(11, 16));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, showPages, position);
                }
            }
        });
        holder.dianzan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, showPages, position);
                }
            }
        });
        holder.pinglun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, showPages, position);
                }
            }
        });

        holder.show_remark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, showPages, position);
                }

            }
        });

        holder.pinglun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, showPages, position);
                }
            }
        });

        List<Remark> remarks = showPages.get(position).getRemarks();
        holder.layout.removeAllViews();
        for (Remark remark : remarks) {
//            View view = holder.inflater.inflate(R.layout.remark_item,null);
            MyRemarkView view = new MyRemarkView(context, remark);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(layoutParams);
            view.setListener(onRemarkItemListener);
            holder.layout.addView(view);
        }
    }

    public void loadUserIcon(final ImageView view, String user) {
        HttpData.getInstance().getLoginInfo(new Observer<UserResult>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(UserResult value) {
                Glide.with(context).load(Constant.Urls.IMAGEURLROOT + value.getIcon()).into(view);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        }, user);
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

    public void setOnRemarkItemListener(MyRemarkView.ClickListener onRemarkItemListener) {
        this.onRemarkItemListener = onRemarkItemListener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView userName;
        TextView timestamp;
        TextView showInfo;
        CircleImageView circleImageView;
        ImageButton dianzan;
        ImageButton show_remark;
        EditText pinglun;

        NineGridImageView<String> nineGridImageView;
        NineGridImageViewAdapter viewAdapter;

        LayoutInflater inflater;
        LinearLayout layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.show_user_image);
            userName = itemView.findViewById(R.id.show_user_name);
            timestamp = itemView.findViewById(R.id.timestamp);
            showInfo = itemView.findViewById(R.id.showinfo);
            dianzan = itemView.findViewById(R.id.dianzan);
            show_remark = itemView.findViewById(R.id.show_remark);
            nineGridImageView = itemView.findViewById(R.id.showimages);
            inflater = LayoutInflater.from(context);
            layout = itemView.findViewById(R.id.remarkLayout);
            pinglun = itemView.findViewById(R.id.edit_remark);
            final ArrayList<ThumbViewInfo> mThumbViewInfoList = new ArrayList<>();
            viewAdapter = new NineGridImageViewAdapter<String>() {
                @Override
                protected void onDisplayImage(Context context, ImageView imageView, String image) {
                    Glide.with(context).load(Constant.Urls.IMAGEURLROOT + image).into(imageView);
                }

                @Override
                protected void onItemImageClick(Context context, int index, List<String> list) {
                    super.onItemImageClick(context, index, list);
                    Log.d(TAG, "onItemImageClick: "+"点击了"+index + " "+list.get(index));

                    ThumbViewInfo item;
                    mThumbViewInfoList.clear();
                    for (int i = 0;i < list.size(); i++) {
                        Rect bounds = new Rect();
                        //new ThumbViewInfo(图片地址);
                        item=new ThumbViewInfo(Constant.Urls.IMAGEURLROOT+list.get(i));
                        item.setBounds(bounds);
                        mThumbViewInfoList.add(item);
                    }
                    GPreviewBuilder.from((Activity)context)
//                            .to(xxActivity.class)//使用自定义界面
                            .setData(mThumbViewInfoList)
                            .setCurrentIndex(index)
                            .setSingleFling(true)
                            .setType(GPreviewBuilder.IndicatorType.Dot)
                            .start();
                }

                @Override
                protected ImageView generateImageView(Context context) {
                    return super.generateImageView(context);
                }
            };
            nineGridImageView.setAdapter(viewAdapter);
        }

        public void setOnFoucsEdit(){
            pinglun.setFocusable(true);
            pinglun.setFocusableInTouchMode(true);
            pinglun.requestFocus();

        }

    }

    public interface OnItemClickListener {
        void onItemClick(View view, List<ShowPage> showPageResults, int position);
    }



}
