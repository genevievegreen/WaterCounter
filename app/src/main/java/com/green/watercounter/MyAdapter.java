package com.green.watercounter;

import android.content.Context;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<Daily> mDataset;
    private Context mContext;

    public MyAdapter(Context mContext, ArrayList<Daily> mDataset) {
        this.mDataset = mDataset;
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder called");

        holder.textDate.setText(mDataset.get(position).getDateString());
        holder.textNumCups.setText("Number of cups: " + mDataset.get(position).getNumCups());

        //for the progress bar
        holder.pBar.setMax(8);
        holder.pBar.setProgress(mDataset.get(position).getNumCups());

        //when each thingy is clicked
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "boom baby", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mDataset == null) {
            return 0;
        }
        return mDataset.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        //declare objects
        public ProgressBar pBar;
        public TextView textDate;
        public TextView textNumCups;
        public RelativeLayout parentLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            //connect variables to objects in layout
            pBar = itemView.findViewById(R.id.pBarCircle);
            textDate = itemView.findViewById(R.id.textDate);
            textNumCups = itemView.findViewById(R.id.textNumCups);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }


}
