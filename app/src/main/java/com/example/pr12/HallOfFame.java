package com.example.pr12;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;
public class HallOfFame extends AppCompatActivity {
    // Model: Record (intents = puntuació, nom)
    public static class Record {
        public int intents;
        public String nom;
        public Record(int _intents, String _nom) {
            intents = _intents;
            nom = _nom;
        }
    }
    // Model = Taula de records: utilitzem ArrayList
    ArrayList<Record> records;
    // ArrayAdapter serà l'intermediari amb la ListView
    ArrayAdapter<Record> adapter;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hall_of_fame);

        // Obtenim les dades de la partida
        String receivedNom = getIntent().getStringExtra("nom");
        int receivedIntents = getIntent().getIntExtra("intents", 0);

        // Inicialitzem el model
        records = new ArrayList<Record>();
        // Afegim un nou rècord fent servir les dades anteriors
        records.add(new Record(receivedIntents, receivedNom));

        // Inicialitzem l'ArrayAdapter amb el layout pertinent
        adapter = new ArrayAdapter<Record>(this, R.layout.list_item, records) {
            @SuppressLint("SetTextI18n")
            @NonNull
            @Override
            public View getView(int pos, View convertView, @NonNull ViewGroup container) {

                // GetView ens construeix el layout i hi "pinta" els valors de l'element en la posició pos
                if (convertView == null) {
                    // Inicialitzem l'element la View amb el seu layout
                    convertView = getLayoutInflater().inflate(R.layout.list_item, container, false);
                }
                // "Pintem" els valors (també quan es refresca)
                if (receivedIntents > 0) {
                    ((TextView) convertView.findViewById(R.id.nom)).setText(Objects.requireNonNull(getItem(pos)).nom);
                    ((TextView) convertView.findViewById(R.id.intents)).setText(Integer.toString(Objects.requireNonNull(getItem(pos)).intents));
                }
                return convertView;
            }
        };

        // Busquem la ListView i li endollem l'ArrayAdapter
        ListView lv =  findViewById(R.id.recordsView);
        lv.setAdapter(adapter);
    }
}
