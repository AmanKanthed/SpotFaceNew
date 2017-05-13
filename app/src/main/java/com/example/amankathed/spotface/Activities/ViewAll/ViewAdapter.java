package com.example.amankathed.spotface.Activities.ViewAll;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.amankathed.spotface.R;

import java.util.ArrayList;





public class ViewAdapter extends RecyclerView.Adapter<ViewAdapter.ViewAdapterViewHolder> {
    Context context;
    ArrayList<View_Model> nameslist;
    public ViewAdapter(ArrayList<View_Model> names,Context context){
        this.context=context;
        this.nameslist=names;
    }


    @Override
    public ViewAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.names_model,parent,false);
        return new ViewAdapterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewAdapterViewHolder holder, int position) {
            View_Model model = nameslist.get(position);
            holder.name.setText(String.valueOf(model.getNames()));
    }

    @Override
    public int getItemCount() {
        return nameslist.size();
    }


    public class ViewAdapterViewHolder extends RecyclerView.ViewHolder{
        TextView  name;
        public ViewAdapterViewHolder(View itemView) {
            super(itemView);
            name= (TextView) itemView.findViewById(R.id.view_names);
        }
    }
}
