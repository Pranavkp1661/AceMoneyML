package com.pranav.acemoneyml;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RvItemAdapter extends RecyclerView.Adapter<RvItemAdapter.ViewHolder> {
    Context context;
    List<ActorsModel> actorsModels = new ArrayList<>();

    public RvItemAdapter(Context context, List<ActorsModel> actorsModels) {
        this.context = context;
        this.actorsModels = actorsModels;
    }

    public void updateAdapter(List<ActorsModel> actorsModels) {
        this.actorsModels = actorsModels;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvActorName.setText(actorsModels.get(position).getActorName());
        Glide.with(context).load(actorsModels.get(position).getImgUrl()).into(holder.imvImage);
    }

    @Override
    public int getItemCount() {
        return actorsModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imvImage;
        TextView tvActorName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imvImage = itemView.findViewById(R.id.imvImage);
            tvActorName = itemView.findViewById(R.id.tvActorName);
        }
    }
}
