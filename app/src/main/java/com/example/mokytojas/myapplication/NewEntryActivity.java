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
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.HashMap;

public class NewEntryActivity extends AppCompatActivity {

    private static final String INSERT_URL = "https://deadmantest.000webhostapp.com/mobile/db.php";

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
        final Spinner entryType = findViewById(R.id.entryType);
        ArrayAdapter<String> entryTypeAdapter = new ArrayAdapter(
                NewEntryActivity.this,
                android.R.layout.simple_dropdown_item_1line,
                entryTypes
        );
//        entryTypeAdapter.setDropDownViewResource(
//                android.R.layout.simple_dropdown_item_1line
//        );
        entryType.setAdapter(entryTypeAdapter);


        final EditText nameInput = findViewById(R.id.entryName);
        final EditText weightInput = findViewById(R.id.entryWeight);

        final RadioButton cpStrong = findViewById(R.id.entryCpStrong);
        final RadioButton cpMedium = findViewById(R.id.entryCpMedium);
        final RadioButton cpWeak = findViewById(R.id.entryCpWeak);

        final CheckBox abilityUltimate = findViewById(R.id.entryAbilitiesUltimate);
        final CheckBox abilityFighting = findViewById(R.id.entryAbilitiesFighting);
        final CheckBox abilityRange = findViewById(R.id.entryAbilitiesRange);

        Button addButton = findViewById(R.id.entrySubmit);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                    Pokemon pokemon = new Pokemon(
                            nameText,
                            Double.parseDouble(weightInput.getText().toString()),
                            cpChecked,
                            abilities,
                            entryTypeTemp
                    );
                    insertToDB(pokemon);
                    //Toast.makeText(NewEntryActivity.this, pokemon.toString(), Toast.LENGTH_LONG).show();
                }

            }
        });
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
                //pokemonData.put("action", "insert");

                String result = db.sendPostRequest(INSERT_URL, pokemonData);

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
}
