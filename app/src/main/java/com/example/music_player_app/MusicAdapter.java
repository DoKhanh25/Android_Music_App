package com.example.music_player_app;

import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.music_player_app.model.MusicFileModel;

import java.util.ArrayList;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.ViewHolder> {
    private Context context;
    private ArrayList<MusicFileModel> musicFileModels;

    public MusicAdapter(Context context, ArrayList<MusicFileModel> musicFileModels){
        this.context = context;
        this.musicFileModels = musicFileModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.music_item,  parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try{
            holder.fileName.setText(musicFileModels.get(position).getTitle());
            byte[] image = getAlbumArt(musicFileModels.get(position).getPath());

            if(image != null){
                Glide.with(context).asBitmap().load(image).into(holder.albumArt);
            } else {
                Glide.with(context).load(R.drawable.music_player_song).into(holder.albumArt);
            }
        } catch (Exception e){
            Log.e("err", e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return musicFileModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView fileName;
        ImageView albumArt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fileName = itemView.findViewById(R.id.music_filename);
            albumArt = itemView.findViewById(R.id.music_img);
        }
    }

    private byte[] getAlbumArt(String uri) throws Exception{
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(uri);
        byte[] art = retriever.getEmbeddedPicture();
        retriever.release();
        return art;
    }
}
