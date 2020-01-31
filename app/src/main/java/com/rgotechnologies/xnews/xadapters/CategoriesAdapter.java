package com.rgotechnologies.xnews.xadapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.card.MaterialCardView;
import com.rgotechnologies.xnews.R;
import com.rgotechnologies.xnews.xactivities.ViewPostsByCategoriesActivity;
import com.rgotechnologies.xnews.xmodels.categories.CategoriesResponse;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {
    List<CategoriesResponse> responseList;
    Context mContext;

    public CategoriesAdapter(List<CategoriesResponse> responseList, Context mContext) {
        this.responseList = responseList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public CategoriesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=layoutInflater.inflate(R.layout.row_categories_card,parent,false);
        return new CategoriesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesAdapter.ViewHolder holder, int position) {

        try {
            holder.textViewName.setText(responseList.get(position).getName());
            Glide.with(mContext).load(responseList.get(position).getCategoryiconurl()).into(holder.imageViewCatIcon);
            Glide.with(mContext)
                    .load(responseList.get(position).getCategoryiconurl())
                    .override(75, 75)
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.imageViewCatIcon);

            holder.parentCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(mContext, ViewPostsByCategoriesActivity.class);
                    intent.putExtra("catId",String.valueOf(responseList.get(position).getId()));
                    intent.putExtra("catName",responseList.get(position).getName());
                    mContext.startActivity(intent);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public int getItemCount() {
        return responseList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        ImageView imageViewCatIcon;
        MaterialCardView parentCardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName=(TextView)itemView.findViewById(R.id.textViewCatName);
            imageViewCatIcon=(ImageView)itemView.findViewById(R.id.imageViewCategoryIcon);
            parentCardView=(MaterialCardView) itemView.findViewById(R.id.parentCardView);
        }
    }
}
