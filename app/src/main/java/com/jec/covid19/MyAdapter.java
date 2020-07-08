package com.jec.covid19;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.AdapterViewHolder>{
    private  ArrayList<DistrictData> distlist;
    public static class AdapterViewHolder extends RecyclerView.ViewHolder{
        TextView con,act,rec,det,dis;
        public AdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            dis =itemView.findViewById(R.id.dist);
            con =itemView.findViewById(R.id.con);
            act=itemView.findViewById(R.id.act);
            rec =itemView.findViewById(R.id.rec);
            det =itemView.findViewById(R.id.death);
        }
    }
    public MyAdapter(ArrayList<DistrictData> distlist){
        this.distlist=distlist;
    }
    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.d_list,parent,false);
        AdapterViewHolder adapterViewHolder = new AdapterViewHolder(view);
        return  adapterViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder holder, int position) {
      DistrictData current = distlist.get(position);
      holder.dis.setText(current.getDtname());
        holder.con.setText(current.getConfirmed());
        holder.act.setText(current.getActive());
        holder.rec.setText(current.getRecovered());
        holder.det.setText(current.getDeceased());
    }

    @Override
    public int getItemCount() {
        return distlist.size();
    }



}
