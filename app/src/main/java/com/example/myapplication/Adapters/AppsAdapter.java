package com.example.myapplication.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Models.GetAppModel;
import com.example.myapplication.R;

import java.util.List;

public class AppsAdapter extends RecyclerView.Adapter<AppsAdapter.AppViewHolder> {

    private List<GetAppModel> appList;

    public AppsAdapter(List<GetAppModel> appList) {
        this.appList = appList;
    }

    @NonNull
    @Override
    public AppViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_app, parent, false);
        return new AppViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppViewHolder holder, int position) {
        GetAppModel app = appList.get(position);
        holder.tvAppId.setText(String.valueOf(app.getId()));
        holder.tvAppName.setText(app.getApp_Name());
    }

    @Override
    public int getItemCount() {
        return appList.size();
    }

    public void setApps(List<GetAppModel> newApps) {
        this.appList = newApps;
        notifyDataSetChanged();
    }

    public static class AppViewHolder extends RecyclerView.ViewHolder {
        TextView tvAppId, tvAppName;

        public AppViewHolder(View itemView) {
            super(itemView);
            tvAppId = itemView.findViewById(R.id.tvAppId);
            tvAppName = itemView.findViewById(R.id.tvAppName);
        }
    }
}
