package br.com.alura.leilao.ui.activity;

import android.content.Context;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.alura.leilao.api.retrofit.client.LeilaoWebClient;
import br.com.alura.leilao.api.retrofit.client.RespostaListener;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.ui.recyclerview.adapter.ListaLeilaoAdapter;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ListaLeilaoActivityTest {

    @Mock
    private Context context;
    @Spy
    private ListaLeilaoAdapter adapter = new ListaLeilaoAdapter(context);
    @Mock//por padrao objetos mockados nao fazem nada
    private LeilaoWebClient client;

    @Test
    public void deve_Atualizar_ListaDeLeiloes_QuandoBuscarLeiLoesDaAPI() throws InterruptedException {
        ListaLeilaoActivity activity = new ListaLeilaoActivity();

        Mockito.doNothing().when(adapter).atualizaLista();

        //mockito executando uma resposta
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) { //mockado a respsota da requisicao
                // o objeto e do mesmo tipo do argumetno requisitado dentro do client.
                //caso tivesse mais de um argumento eu preciso indicar a posicao, no caso do client so possui um unico argumento do tipo do objeto declarado
                RespostaListener<List<Leilao>> argument = invocation.getArgument(0);
                argument.sucesso(new ArrayList<Leilao>(Arrays.asList(
                        new Leilao("Computador"),
                        new Leilao("Carro")
                )));
                return null;
            }
        }).when(client)//quando executado no client chamando o metodo todos
                //passo atraves do argumentmatchers uma resposta qualquuer passando um listener qualquer...
                .todos(ArgumentMatchers.any(RespostaListener.class));

        activity.buscaLeiloes(adapter, client);

        int quantidadeDeLeiloesDevolvida = adapter.getItemCount();

        assertThat(quantidadeDeLeiloesDevolvida, is(2));
    }

}