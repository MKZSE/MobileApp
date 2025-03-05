package com.example.myapplication.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.R;
import com.example.myapplication.Models.GetLogsModel;
import java.util.List;

public class LogsAdapter extends RecyclerView.Adapter<LogsAdapter.LogViewHolder> {

    private List<GetLogsModel> logs;

    public LogsAdapter(List<GetLogsModel> logs) {
        this.logs = logs;
    }

    @Override
    public LogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_log, parent, false);
        return new LogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LogViewHolder holder, int position) {
        GetLogsModel log = logs.get(position);


        holder.logId.setText(String.valueOf(log.getId()));
        holder.logDate.setText(log.getDate());
        holder.logMessage.setText(log.getMessage());
    }


    @Override
    public int getItemCount() {
        return logs.size();
    }


    public void setLogs(List<GetLogsModel> logs) {
        this.logs = logs;
        notifyDataSetChanged();
    }

    public static class LogViewHolder extends RecyclerView.ViewHolder {

        public TextView logId;
        public TextView logDate;
        public TextView logMessage;

        public LogViewHolder(View itemView) {
            super(itemView);
            logId = itemView.findViewById(R.id.tvId);
            logDate = itemView.findViewById(R.id.tvDate);
            logMessage = itemView.findViewById(R.id.tvMessage);
        }
    }
}
