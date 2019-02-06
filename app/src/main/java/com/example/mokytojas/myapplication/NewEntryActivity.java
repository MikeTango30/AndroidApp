package com.example.mokytojas.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.HashMap;

public class NewEntryActivity extends AppCompatActivity {

    public static final String DB_URL = "https://deadmantest.000webhostapp.com/mobile/db.php";

    Pokemon pokemon;

    EditText nameInput;
    EditText weightInput;

    RadioButton cpStrong;
    RadioButton cpMedium;
    RadioButton cpWeak;

    CheckBox abilityUltimate;
    CheckBox abilityFighting;
    CheckBox abilityRange;

    Spinner entryType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        String entryTypes[] = {
                getResources().getString(R.string.entryTypeEarth),
                getResources().getString(R.string.entryTypeWater),
                getResources().getString(R.string.entryTypeWind),
                getResources().getString(R.string.entryTypeFire),
                getResources().getString(R.string.entryTypeElectric)
        };

        entryType = findViewById(R.id.entryType);
        ArrayAdapter<String> entryTypeAdapter = new ArrayAdapter(
                NewEntryActivity.this,
                android.R.layout.simple_dropdown_item_1line,
                entryTypes
        );

        entryType.setAdapter(entryTypeAdapter);

        nameInput = findViewById(R.id.entryName);
        weightInput = findViewById(R.id.entryWeight);

        cpStrong = findViewById(R.id.entryCpStrong);
        cpMedium = findViewById(R.id.entryCpMedium);
        cpWeak = findViewById(R.id.entryCpWeak);

        abilityUltimate = findViewById(R.id.entryAbilitiesUltimate);
        abilityFighting = findViewById(R.id.entryAbilitiesFighting);
        abilityRange = findViewById(R.id.entryAbilitiesRange);

        Button insertButton = findViewById(R.id.btnInsert);
        Button updateButton = findViewById(R.id.btnUpdate);
        Button deleteButton = findViewById(R.id.btnDelete);

        // checking if it's new or existing entry
        Intent intent = getIntent();
        pokemon = (Pokemon) intent.getSerializableExtra(AdapterPokemon.ENTRY);

        if (pokemon == null) { // new entry
            //since it's new entry - we can't delete or update
            insertButton.setEnabled(true);
            updateButton.setEnabled(false);
            deleteButton.setEnabled(false);

            //public Pokemon(String name, double weight, String cp, String abilities, String type) {
            pokemon = new Pokemon(
                "",
                0,
                getResources().getString(R.string.entryCpStrong),
                getResources().getString(R.string.entryAbilitiesUltimate),
                getResources().getString(R.string.entryTypeEarth)
            );
        } else { // existing entry
            //since it's existing entry - we can delete or update, but not create new
            insertButton.setEnabled(false);
            updateButton.setEnabled(true);
            deleteButton.setEnabled(true);

            //all data exists from db - it comes from search activity
        }

        // inserting data from pokemon object to form

        nameInput.setText(pokemon.getName());
        weightInput.setText(Double.toString(pokemon.getWeight()));

        //radio buttons
        if (pokemon.getCp().equals(getResources().getString(R.string.entryCpStrong))) {
            cpStrong.setChecked(true);
        } else if (pokemon.getCp().equals(getResources().getString(R.string.entryCpMedium))) {
            cpMedium.setChecked(true);
        } else if (pokemon.getCp().equals(getResources().getString(R.string.entryCpWeak))) {
            cpWeak.setChecked(true);
        }

        //checkboxes

        boolean isChecked = false;
        if (pokemon.getAbilities().equalsIgnoreCase(getResources().getString(R.string.entryAbilitiesFighting))) {
            abilityFighting.setChecked(true);
            isChecked = true;
        }
        if (pokemon.getAbilities().equalsIgnoreCase(getResources().getString(R.string.entryAbilitiesUltimate))) {
            abilityUltimate.setChecked(true);
            isChecked = true;
        }
        if (pokemon.getAbilities().equalsIgnoreCase(getResources().getString(R.string.entryAbilitiesRange))) {
            abilityRange.setChecked(true);
            isChecked = true;
        }
        if(!isChecked) { //was new entry - nothing to check
            abilityUltimate.setChecked(true); // lets say it will be default value for new entry
        }

        //spinner aka select
        if (!pokemon.getType().equals(null)) {
            int spinnerPosition = entryTypeAdapter.getPosition(pokemon.getType());
            entryType.setSelection(spinnerPosition);
        } else {
            entryType.setSelection(0);
        }

        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pokemon pokemon = getDataFromForm();
                Toast.makeText(NewEntryActivity.this,
                        pokemon.getName() + "\n" +
                        pokemon.getWeight() + "\n" +
                        pokemon.getCp() + "\n" +
                        pokemon.getType() + "\n" +
                        pokemon.getAbilities(),
                        Toast.LENGTH_SHORT).show();
                insertToDB(pokemon);
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pokemon pokemon = getDataFromForm();
                Toast.makeText(NewEntryActivity.this,
                        pokemon.getName() + "\n" +
                                pokemon.getWeight() + "\n" +
                                pokemon.getCp() + "\n" +
                                pokemon.getType() + "\n" +
                                pokemon.getAbilities(),
                        Toast.LENGTH_SHORT).show();
                updateDB(pokemon);
            }
        });
    }

    private Pokemon getDataFromForm(){
        String nameText = nameInput.getText().toString();
        //double weightNumber = Double.parseDouble(weightInput.getText().toString());
        String weightNumber = weightInput.getText().toString();

        String cpChecked = "";
        if(cpStrong.isChecked()){
            cpChecked = cpStrong.getText().toString();
        }else if(cpMedium.isChecked()){
            cpChecked = cpMedium.getText().toString();
        }else if(cpWeak.isChecked()){
            cpChecked = cpWeak.getText().toString();
        }

        String abilities = "";
        if(abilityUltimate.isChecked()){
            abilities += abilityUltimate.getText().toString() + " ";
        }
        if(abilityFighting.isChecked()){
            abilities +=  abilityFighting.getText().toString() + " ";
        }
        if(abilityRange.isChecked()){
            abilities +=  abilityRange.getText().toString() + " ";
        }

        String entryTypeTemp = entryType.getSelectedItem().toString();

        nameInput.setError(null);
        weightInput.setError(null);

        if(!Validation.isValidEntryName(nameText)){
            nameInput.setError(getResources().getString(R.string.entryNameError));
            nameInput.requestFocus();
        }else if(!Validation.isValidEntryWeight(weightNumber)){
            weightInput.setError((getResources().getString(R.string.entryWeightError)));
            weightInput.requestFocus();
        }else if(abilities.isEmpty()){
            Toast.makeText(NewEntryActivity.this,
                    getResources().getString(R.string.entryCheckAbilities),
                    Toast.LENGTH_LONG).show();
        }else{
            Pokemon pokemonTmp = new Pokemon(
                    nameText,
                    Double.parseDouble(weightInput.getText().toString()),
                    cpChecked,
                    abilities,
                    entryTypeTemp
            );
            return pokemonTmp;
              //Toast.makeText(NewEntryActivity.this, pokemon.toString(), Toast.LENGTH_LONG).show();
        }
        return null;
    }

    private void insertToDB(Pokemon pokemon) {
        class NewEntry extends AsyncTask<String, Void, String> {

            ProgressDialog loading;
            DB db = new DB();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(NewEntryActivity.this,
                        getResources().getString(R.string.entryDatabaseInfo),
                        null, true, true);
            }

            @Override
            protected String doInBackground(String... strings) {
                // Pirmas string yra raktas, antras - reikšmė.
                HashMap<String, String> pokemonData = new HashMap<String, String>();
                pokemonData.put("name", strings[0]);
                pokemonData.put("weight", strings[1]);
                pokemonData.put("cp", strings[2]);
                pokemonData.put("abilities", strings[3]);
                pokemonData.put("type", strings[4]);
                pokemonData.put("action", "insert");

                String result = db.sendPostRequest(DB_URL, pokemonData);

                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(NewEntryActivity.this, s, Toast.LENGTH_SHORT).show();
                Intent goToSearchActivity = new Intent(NewEntryActivity.this, SearchActivity.class);
                startActivity(goToSearchActivity);
            }
        }

        NewEntry newEntry = new NewEntry();
        newEntry.execute(pokemon.getName(),
                Double.toString(pokemon.getWeight()),
                pokemon.getCp(),
                pokemon.getAbilities(),
                pokemon.getType());
    }

    private void updateDB(Pokemon pokemon) {
        class UpdateEntry extends AsyncTask<String, Void, String> {

            ProgressDialog loading;
            DB db = new DB();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(NewEntryActivity.this,
                        getResources().getString(R.string.entryDatabaseInfo),
                        null, true, true);
            }

            @Override
            protected String doInBackground(String... strings) {
                // Pirmas string yra raktas, antras - reikšmė.
                HashMap<String, String> pokemonData = new HashMap<String, String>();
                pokemonData.put("id", strings[0]);
                pokemonData.put("name", strings[1]);
                pokemonData.put("weight", strings[2]);
                pokemonData.put("cp", strings[3]);
                pokemonData.put("abilities", strings[4]);
                pokemonData.put("type", strings[5]);
                pokemonData.put("action", "update");

                String result = db.sendPostRequest(DB_URL, pokemonData);

                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(NewEntryActivity.this, s, Toast.LENGTH_SHORT).show();
                Intent goToSearchActivity = new Intent(NewEntryActivity.this, SearchActivity.class);
                startActivity(goToSearchActivity);
            }
        }

        UpdateEntry updateEntry = new UpdateEntry();
        updateEntry.execute(Integer.toString(pokemon.getId()),
                pokemon.getName(),
                Double.toString(pokemon.getWeight()),
                pokemon.getCp(),
                pokemon.getAbilities(),
                pokemon.getType());
    }
}
