package com.example.mokytojas.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

public class AdapterPokemon extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    List<Pokemon> data = Collections.emptyList();
    Pokemon current;
    int currentPos = 0;

    // create constructor to initialize context and data sent from MainActivity
    public AdapterPokemon(Context context, List<Pokemon> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
        for (Pokemon pokemon : data) {
            Log.e("pokemon", pokemon.getName() + " " + pokemon.getWeight());
        }
    }

    // Inflate the layout when ViewHolder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.container_pokemon, parent, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        // Get current position of item in RecyclerView to bind data and assign values from list
        MyHolder myHolder = (MyHolder) holder;
        Pokemon current = data.get(position);
        myHolder.textName.setText(current.getName());
        myHolder.textWeight.setText("Weight: " + current.getWeight());
        myHolder.textCp.setText("Cp: " + current.getCp());
        myHolder.textAbilities.setText("Abilities: " + current.getAbilities());
        Log.e("pokemon", current.getName() + " " + current.getWeight());

    }

    // return total item from List
    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textName;
        TextView textWeight;
        TextView textCp;
        TextView textAbilities;

        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            textName = (TextView) itemView.findViewById(R.id.textName);
            textWeight = (TextView) itemView.findViewById(R.id.textWeight);
            textCp = (TextView) itemView.findViewById(R.id.textCp);
            textAbilities = (TextView) itemView.findViewById(R.id.textAbilities);
            itemView.setOnClickListener(this);
        }

        // Click event for all items
        @Override
        public void onClick(View v) {

            Toast.makeText(context, "You clicked an item", Toast.LENGTH_SHORT).show();

        }

    }

}