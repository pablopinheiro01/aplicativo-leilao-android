package br.com.alura.leilao.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.Serializable;
import java.text.Normalizer;

import br.com.alura.leilao.R;
import br.com.alura.leilao.formatter.FormatadorDeMoeda;
import br.com.alura.leilao.model.Lance;
import br.com.alura.leilao.model.Leilao;

public class LancesLeilaoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lances_leilao);
        Intent dadosRecebidos = getIntent();
        if(dadosRecebidos.hasExtra("leilao")){
            Leilao leilao = (Leilao) dadosRecebidos.getSerializableExtra("leilao");
            FormatadorDeMoeda formatadorDeMoeda = new FormatadorDeMoeda();
            TextView descricao = findViewById(R.id.lances_leilao_descricao);
            descricao.setText(leilao.getDescricao());
            TextView maiorLance = findViewById(R.id.lances_leilao_maior_lance);
            maiorLance.setText(formatadorDeMoeda.formata(leilao.getMaiorLance()));
            TextView menorLance = findViewById(R.id.lances_leilao_menor_lance);
            menorLance.setText(formatadorDeMoeda.formata(leilao.getMenorLance()));
            TextView maioresLances = findViewById(R.id.lances_leilao_maiores_lances);
            StringBuilder sb = new StringBuilder();
            for(Lance lance: leilao.tresMaioresLances()){
                sb.append(lance.getValor() + "\n");
            }
            String maioresLancesEmTexto = sb.toString();
            maioresLances.setText(maioresLancesEmTexto);
        }
    }
}
