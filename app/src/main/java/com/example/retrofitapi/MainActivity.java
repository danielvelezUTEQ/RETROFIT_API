package com.example.retrofitapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.example.retrofitapi.busqueda.BuscarRevista;
import com.example.retrofitapi.constructorjson.Revista;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    EditText txtCodigo;
    EditText multitxtResultado;
    Button btnBuscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtCodigo= findViewById(R.id.txtCodigo);
        multitxtResultado = findViewById(R.id.mltxt_resultado);
        btnBuscar = findViewById(R.id.btnBuscar);

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multitxtResultado.setText("");
                Busqueda(txtCodigo.getText().toString());
            }
        });


    }

    private void Busqueda (String codigo)
    {
        Retrofit retrofit = new Retrofit.Builder().
                baseUrl("https://revistas.uteq.edu.ec/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        BuscarRevista buscarRevista = retrofit.create(BuscarRevista.class);
        Call<List<Revista>> call = buscarRevista.find(codigo);
        call.enqueue(new Callback<List<Revista>>() {
            @Override
            public void onResponse(Call<List<Revista>> call, Response<List<Revista>> response) {
                List<Revista> revista = response.body();
                String issue = getString(R.string.issue_id);
                String volume = getString(R.string.volume);
                String number = getString(R.string.number);
                String year = getString(R.string.year);
                String date_published = getString(R.string.date_published);
                String title = getString(R.string.title);
                String doi = getString(R.string.doi);
                String cover = getString(R.string.cover);
                for(Revista r: revista){
                    String json = "";
                    json+= issue + " " + r.getIssue_id() + "\n" +
                           volume + " " + r.getVolume() + "\n" +
                           number + " " + r.getNumber() + "\n" +
                           year + " " + r.getYear() + "\n" +
                           date_published + " " + r.getDate_published() + "\n" +
                           title + " " + r.getTitle() + "\n" +
                           doi + " " + r.getDoi() + "\n" +
                           cover + " " + r.getCover() + "\n";
                    multitxtResultado.append(json);
                }
            }

            @Override
            public void onFailure(Call<List<Revista>> call, Throwable t) {
                multitxtResultado.setText(t.getMessage());
            }
        });
    }
}