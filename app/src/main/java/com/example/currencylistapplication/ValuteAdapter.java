package com.example.currencylistapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class ValuteAdapter extends RecyclerView.Adapter<ValuteAdapter.ViewHolder> {
    private ArrayList<ModelValute> marrayList;

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView NumCode,CharCode,Nominal,Name,Value,Previous;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            NumCode=itemView.findViewById(R.id.NumCode);
            CharCode=itemView.findViewById(R.id.CharCode);
            Nominal=itemView.findViewById(R.id.Nominal);
            Name=itemView.findViewById(R.id.Name);
            Value=itemView.findViewById(R.id.Value);
            Previous=itemView.findViewById(R.id.Previous);
        }
    }

    public ValuteAdapter(ArrayList<ModelValute> arrayList)
    {
     marrayList=arrayList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_valute,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelValute currentValute=marrayList.get(position);
        String name=currentValute.getName();
        String numcode=currentValute.getNumCode();
        String charcode=currentValute.getCharCode();
        String nominal=currentValute.getNominal();
        String value=currentValute.getValue();
        String previous=currentValute.getPrevious();
        holder.Name.setText(name);
        holder.NumCode.setText("Numcode:"+numcode);
        holder.CharCode.setText("CharCode:"+charcode);
        holder.Nominal.setText("Номинал:"+nominal);
        holder.Value.setText(value);
        holder.Previous.setText(previous);
    }

    @Override
    public int getItemCount() {
        return marrayList.size();
    }




}
