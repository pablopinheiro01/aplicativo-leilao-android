package br.com.alura.leilao.ui.activity;

import android.content.Intent;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import br.com.alura.leilao.BaseTesteIntegracao;
import br.com.alura.leilao.R;
import br.com.alura.leilao.api.retrofit.client.TesteWebClient;
import br.com.alura.leilao.formatter.FormatadorDeMoeda;
import br.com.alura.leilao.matchers.ViewMatchers;
import br.com.alura.leilao.model.Leilao;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

public class ListaLeilaoScreenTest extends BaseTesteIntegracao {

    @Rule
    public ActivityTestRule<ListaLeilaoActivity> activity = new ActivityTestRule<>(ListaLeilaoActivity.class, true,false);

    private FormatadorDeMoeda formatadorDeMoeda = new FormatadorDeMoeda();

    @Before //a anotacao roda sempre o metodo antes de qualquer teste
    //setup e o nome default que o mercado adota para essa tecnica
    public void setup() throws IOException {
        limpaBancoDeDadosDaApi();
    }

    @After //executado sempre no final de todos os testes
    //tearDown é um nome default que o mercado adotou para essa tecnica
    public void tearDown() throws IOException {
        limpaBancoDeDadosDaApi();
    }

    @Test
    public void deve_AparecerUmLeiLao_QuandoCarregarUmLeiLaoNaAPI() throws IOException {

        tentaSalvarLeilaoNaApi(new Leilao("Carro"),new Leilao("Casa"));

//        //inicializo a activity apos ter certeza que o salva foi executado
        activity.launchActivity(new Intent());

        onView(withId(R.id.lista_leilao_recyclerview)
                //o lance esta sendo enviado com o valor 0.0 deixando a responsabilidade de formatação na sua classe especifica.
        ).check(matches(ViewMatchers.apareceLeilaoNaPosicao(0, "Carro", 0.00)));

        onView(withId(R.id.lista_leilao_recyclerview)
        ).check(matches(ViewMatchers.apareceLeilaoNaPosicao(1, "Casa", 0.00)));

    }

    @Test
    public void deve_AparecerDoisLeiloes_QuandoCarregarDoisLeiloesDaApi() throws IOException {

        tentaSalvarLeilaoNaApi(new Leilao("Carro"), new Leilao("Computador"));

        //inicializo a activity apos ter certeza que o salva foi executado
        activity.launchActivity(new Intent());

        onView(withId(R.id.lista_leilao_recyclerview)
        ).check(matches(ViewMatchers.apareceLeilaoNaPosicao(0, "Carro", 0.00)));

        onView(withId(R.id.lista_leilao_recyclerview)
        ).check(matches(ViewMatchers.apareceLeilaoNaPosicao(1, "Computador", 0.00)));

    }




    @Test
    public void deve_AparecerUltimoLeilao_QuandoCarregarDezLeiloesDaApi() throws IOException {
        tentaSalvarLeilaoNaApi(
                new Leilao("Carro"),
                new Leilao("Computador"),
                new Leilao("Notebook"),
                new Leilao("TV"),
                new Leilao("Console"),
                new Leilao("Jogo"),
                new Leilao("Estante"),
                new Leilao("Quadro"),
                new Leilao("Iphone"),
                new Leilao("Casa")
        );

        activity.launchActivity(new Intent());

        onView(withId(R.id.lista_leilao_recyclerview)
        )
                //scrolla a view ate a ultima posicao
                //para utilizacao do perform eu desabilitei as opções graficas do smarthphone com a opção de desenvolvedor habilitada
                //as opcoes desabilitadas foram as animation scale das opções do desenvolvedor
                .perform(RecyclerViewActions.scrollToPosition(9))
                .check(
                matches(
                        ViewMatchers.apareceLeilaoNaPosicao(9, "Casa", 0.00)));
    }

}