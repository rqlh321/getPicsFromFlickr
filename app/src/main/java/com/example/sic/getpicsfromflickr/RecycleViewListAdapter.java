package com.example.sic.getpicsfromflickr;

import android.content.Context;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sic.getpicsfromflickr.model.Photo;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class RecycleViewListAdapter extends RecyclerView.Adapter<RecycleViewListAdapter.ViewHolder> {
    private ArrayList<Photo> list = new ArrayList<>();
    private Context context;

    public RecycleViewListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.folderText.setText(list.get(position).getTitle());
        Glide.with(context)
                .load(list.get(position).getUrl_s())
                .bitmapTransform(new RoundedCornersTransformation(context, 5, 5))
                .into(holder.folderCover);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                AlertDialog alertDialog = alert.create();
                alertDialog.setTitle(context.getString(R.string.share));
                LayoutInflater layoutInflater = LayoutInflater.from(context);
                View shareView = layoutInflater.inflate(R.layout.share_preview, null);
                ShareButton shareButton = (ShareButton) shareView.findViewById(R.id.fb_share_button);
                ShareLinkContent content = new ShareLinkContent.Builder()
                        .setContentUrl(Uri.parse("https://www.flickr.com"))
                        .setImageUrl(Uri.parse(list.get(position).getUrl_s()))
                        .setContentTitle("Look what i found on Flickr")
                        .setContentDescription(list.get(position).getTitle())
                        .build();
                shareButton.setShareContent(content);
                ImageView previewImage = (ImageView) shareView.findViewById(R.id.preview_image);
                Glide.with(context)
                        .load(list.get(position).getUrl_n())
                        .into(previewImage);
                alertDialog.setView(shareView);
                alertDialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addAll(ArrayList<Photo> newList) {
        list.addAll(newList);
        notifyDataSetChanged();
    }

    public void clear() {
        list.clear();
        notifyDataSetChanged();
    }

    public ArrayList<Photo> getList() {
        return list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        TextView folderText;
        ImageView folderCover;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            folderText = (TextView) view.findViewById(R.id.folder_text);
            folderCover = (ImageView) view.findViewById(R.id.folder_preview_image);
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }

}