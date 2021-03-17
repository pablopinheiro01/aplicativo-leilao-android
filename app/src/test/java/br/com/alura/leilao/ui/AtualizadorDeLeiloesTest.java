package br.com.alura.leilao.ui;

import android.content.Context;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
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

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AtualizadorDeLeiloesTest {

    @Mock
    private ListaLeilaoAdapter adapter;
    @Mock//por padrao objetos mockados nao fazem nada
    private LeilaoWebClient client;
    @Mock
    private Context context;

    @Test
    public void deve_AtualizarListaDeLeiloes_QuandoBuscarLeiLoesDaAPI() throws InterruptedException {
        AtualizadorDeLeiloes atualizador = new AtualizadorDeLeiloes();

        //mockito executando uma resposta
        doAnswer(new Answer() {
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

        atualizador.buscaLeiloes(adapter, client, context);

        //vamos verificar apenas se os metodos foram chamados conforme o esperado
        verify(client).todos(ArgumentMatchers.any(RespostaListener.class));
        // a lista de leilões tem que ser exatamente a mesma lista enviada na integração com o adapter
        verify(adapter).atualiza(
                new ArrayList<Leilao>(Arrays.asList(
                        new Leilao("Computador"),
                        new Leilao("Carro")
                ))
        );

    }

    @Test
    public void deve_ApresentarMensagemDeFalha_QuandoFalharABuscaDeLeiLoes(){
        //transformo em um objeto espiao
        AtualizadorDeLeiloes atualizador = Mockito.spy(new AtualizadorDeLeiloes());
        //nao e para fazer nada quando executar esse metodo
        doNothing().when(atualizador).mostraMensagemDeFalha(context);

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                RespostaListener<List<Leilao>> argument = invocation.getArgument(0);
                argument.falha(anyString());
                return null;
            }
        }).when(client).todos(ArgumentMatchers.any(RespostaListener.class));

        atualizador.buscaLeiloes(adapter, client, context);

        verify(atualizador).mostraMensagemDeFalha(context);


    }

}