package br.com.alura.leilao.ui.activity;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class ListaLeilaoActivityTest {

    @Test
    public void deve_Atualizar_ListaDeLeiloes_QuandoBuscarLeiLoesDaAPI() throws InterruptedException {
        ListaLeilaoActivity activity = new ListaLeilaoActivity();

        activity.configuraAdapter();
        activity.buscaLeiloes();
        Thread.sleep(3000);
        int quantidadeDeLeiloesDevolvida = activity.getAdapter().getItemCount();
        assertThat(quantidadeDeLeiloesDevolvida, is(3));
    }

}