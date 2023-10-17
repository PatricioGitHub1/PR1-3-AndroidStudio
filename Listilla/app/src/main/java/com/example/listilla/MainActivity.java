package com.example.listilla;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    // Model: Record (intents=puntuació, nom)
    class Record implements Comparable<Record>{
        public int intents;
        public String nom;

        public int profile_pic;
        public Record(int _intents, String _nom, int _photo ) {
            intents = _intents;
            nom = _nom;
            profile_pic = _photo;
        }

        // Metodo para ordenar
        @Override
        public int compareTo(Record o) {
            return o.intents - this.intents;
        }
    }
    // Model = Taula de records: utilitzem ArrayList
    ArrayList<Record> records;

    //ArrayLists de nombres y apellidos randomizados
    ArrayList<String> names = new ArrayList<>(Arrays.asList("Jhon", "Victor", "Patricio", "Lucia", "Xavi", "Ian", "Hakim", "Asa", "Tetsuo", "Guillermo", "Sarah", "Lee", "Glenn", "Linus", "Makima", "Melissa"));
    ArrayList<String> surnames = new ArrayList<>(Arrays.asList("Parraga", "Perez", "Herrero", "Alonso", "Ruiz", "Carrasco", "Hassoun", "Mitaka", "Del Toro", "Salazar", "Torvalds", "Higashikata", "Doe", "McCaffe", "Gates", "Musk"));

    ArrayList<Integer> images = new ArrayList<>(Arrays.asList(R.drawable.defaultpfp, R.drawable.iconoesteve, R.drawable.ireliacat));
    // ArrayAdapter serà l'intermediari amb la ListView
    ArrayAdapter<Record> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicialitzem model
        records = new ArrayList<Record>();
        // Afegim alguns exemples
        records.add( new Record(33,"Manolo", images.get(0)) );
        records.add( new Record(12,"Pepe",images.get(1)) ) ;
        records.add( new Record(42,"Laura", images.get(2)) );

        // Inicialitzem l'ArrayAdapter amb el layout pertinent
        adapter = new ArrayAdapter<Record>( this, R.layout.list_item, records )
        {
            @Override
            public View getView(int pos, View convertView, ViewGroup container)
            {
                // getView ens construeix el layout i hi "pinta" els valors de l'element en la posició pos
                if( convertView==null ) {
                    // inicialitzem l'element la View amb el seu layout
                    convertView = getLayoutInflater().inflate(R.layout.list_item, container, false);
                }
                // "Pintem" valors (també quan es refresca)
                ((TextView) convertView.findViewById(R.id.nom)).setText(getItem(pos).nom);
                ((TextView) convertView.findViewById(R.id.intents)).setText(Integer.toString(getItem(pos).intents));

                // Randomizar profile picture
                ((ImageView) convertView.findViewById(R.id.imageView)).setImageResource(getItem(pos).profile_pic);
                return convertView;
            }

        };

        // busquem la ListView i li endollem el ArrayAdapter
        ListView lv = (ListView) findViewById(R.id.recordsView);
        lv.setAdapter(adapter);

        // botó per afegir entrades a la ListView
        Button b = (Button) findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0;i<5;i++) {
                    boolean isName = true;
                    String name = "";
                    String surname = "";
                    for (int j = 0; j != 2; j++) {
                        int random_index  = (int) (Math.random() * names.size());
                        if (isName) {
                            name = names.get(random_index);
                            isName = false;
                        } else {
                            surname = surnames.get(random_index);
                        }
                    }
                    int score = (int) (Math.random() * 100);
                    int photo_index = (int) (Math.random() * images.size());
                    records.add(new Record(score, name + " " + surname, images.get(photo_index)));
                }
                // notificar l'adapter dels canvis al model
                adapter.notifyDataSetChanged();
            }
        });

        Button b2 = (Button) findViewById(R.id.button2);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(records);
                // notificar l'adapter dels canvis al model
                adapter.notifyDataSetChanged();
            }
        });
    }
}