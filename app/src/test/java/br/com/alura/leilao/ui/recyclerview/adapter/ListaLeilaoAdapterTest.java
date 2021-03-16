package br.com.alura.leilao.ui.recyclerview.adapter;

import android.content.Context;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import br.com.alura.leilao.model.Leilao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class ListaLeilaoAdapterTest {

    @Test
    public void deve_AtualizarListaDeLeiloes_QuandoReceberListaDeLeiloes(){
        ListaLeilaoAdapter listaLeilaoAdapter = new ListaLeilaoAdapter(null);
        listaLeilaoAdapter.atualiza(new ArrayList<Leilao>(Arrays.asList(
                new Leilao("Console"),
                new Leilao("Computador")
        )));

        int quantidadeDeLeiloes = listaLeilaoAdapter.getItemCount();
        assertThat(quantidadeDeLeiloes, is(2));

    }

}