package com.rgotechnologies.xnews.xadapters;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.textview.MaterialTextView;
import com.rgotechnologies.xnews.R;
import com.rgotechnologies.xnews.xactivities.PostDetailsActivity;
import com.rgotechnologies.xnews.xlibs.XSharedPreferences;
import com.rgotechnologies.xnews.xmodels.general.GenPostResponse;
import com.sackcentury.shinebuttonlib.ShineButton;

import java.util.List;

public class FavouritesListAdapter extends RecyclerView.Adapter<FavouritesListAdapter.ViewHolder> {
    List<GenPostResponse> responseList;
    Context mContext;
    XSharedPreferences sharedPreferences;

    public FavouritesListAdapter(List<GenPostResponse> list, Context mContext) {
        this.responseList = list;
        this.mContext = mContext;
        sharedPreferences=new XSharedPreferences();
    }

    @NonNull
    @Override
    public FavouritesListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=layoutInflater.inflate(R.layout.row_post_favourites_list,parent,false);
        return new FavouritesListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouritesListAdapter.ViewHolder holder, int position) {
        holder.textViewTitle.setText(responseList.get(position).getTitle().getRendered());
        holder.textViewDate.setText(responseList.get(position).getDateGmt());
        Glide.with(mContext).load(responseList.get(position).getFeaturedImageUrl()).into(holder.imageViewThumb);
        holder.shineButtonFavList.setChecked(true);
        holder.shineButtonFavList.setOnCheckStateChangeListener(new ShineButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(View view, boolean checked) {
                if(checked){
                    sharedPreferences.addFavorite(mContext,responseList.get(position));
                }else{
                    sharedPreferences.removeFavorite(mContext,responseList.get(position));
                    responseList.remove(position);
                    notifyItemRemoved(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return responseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        MaterialTextView textViewTitle;
        TextView textViewDate;
        ImageView imageViewThumb;
        ShineButton shineButtonFavList;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle=itemView.findViewById(R.id.tvTitle);
            textViewDate= itemView.findViewById(R.id.tvDate);
            imageViewThumb=itemView.findViewById(R.id.imageViewThumb);
            shineButtonFavList=itemView.findViewById(R.id.shineButtonFavList);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent =new Intent(mContext, PostDetailsActivity.class);
                    intent.putExtra("postObj", (Parcelable) responseList.get(getAdapterPosition()));
                    mContext.startActivity(intent);
                }
            });
        }
    }

}
