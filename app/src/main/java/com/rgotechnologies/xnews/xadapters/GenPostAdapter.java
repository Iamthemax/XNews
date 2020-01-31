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
import com.rgotechnologies.xnews.MainActivity;
import com.rgotechnologies.xnews.R;
import com.rgotechnologies.xnews.xactivities.PostDetailsActivity;
import com.rgotechnologies.xnews.xlibs.XDateConverter;
import com.rgotechnologies.xnews.xmodels.general.Content;
import com.rgotechnologies.xnews.xmodels.general.GenPostResponse;

import java.util.List;

public class GenPostAdapter extends RecyclerView.Adapter<GenPostAdapter.ViewHolder> {

    List<GenPostResponse> responseList;
    Context mContext;
    XDateConverter dateConverter;


    public GenPostAdapter(List<GenPostResponse> responseList, Context mContext) {
        this.responseList = responseList;
        this.mContext = mContext;
        dateConverter = new XDateConverter();
    }

    @NonNull
    @Override
    public GenPostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view;
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.row_post_list_card_1, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull GenPostAdapter.ViewHolder holder, int position) {

        holder.textViewTitle.setText(responseList.get(position).getTitle().getRendered());
        holder.textViewDate.setText(dateConverter.getFullDate(responseList.get(position).getDate()));
        Glide.with(mContext).load(responseList.get(position).getFeaturedImageUrl()).into(holder.imageViewThumb);

    }

    @Override
    public int getItemCount() {
        return responseList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        MaterialTextView textViewTitle;
        TextView textViewDate;
        ImageView imageViewThumb;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = (MaterialTextView) itemView.findViewById(R.id.tvTitle);
            textViewDate = (TextView) itemView.findViewById(R.id.tvDate);
            imageViewThumb = (ImageView) itemView.findViewById(R.id.imageViewThumb);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(mContext, PostDetailsActivity.class);
                    intent.putExtra("postObj", (Parcelable) responseList.get(getAdapterPosition()));
                    mContext.startActivity(intent);
                }
            });
        }
    }
}



