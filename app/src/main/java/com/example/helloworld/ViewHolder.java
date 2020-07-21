package com.example.helloworld;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ViewHolder extends RecyclerView.ViewHolder{

    View view;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        view=itemView;

    }
    public void setdeatils(Context context,String title,String image){
        ImageView mImagetv=view.findViewById(R.id.image_view_upload);
        TextView mtitletv=view.findViewById(R.id.text_view_name);

        mtitletv.setText(title);
        Picasso.get().load(image).into(mImagetv);

        Animation animation= AnimationUtils.loadAnimation(context,android.R.anim.slide_in_left);
        itemView.startAnimation(animation);


    }
}
