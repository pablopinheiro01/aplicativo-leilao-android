package br.com.alura.leilao.ui.activity;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import br.com.alura.leilao.R;
import br.com.alura.leilao.api.retrofit.client.LeilaoWebClient;
import br.com.alura.leilao.api.retrofit.client.TesteWebClient;
import br.com.alura.leilao.formatter.FormatadorDeMoeda;
import br.com.alura.leilao.model.Leilao;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

public class ListaLeilaoScreenTest {

    public static final String ERRO_LIMPEZA_BANCO_DE_DADOS = "Banco de dados não foi limpo";
    public static final String LEILAO_NAO_SALVO = "Leilão não foi salvo: ";

    @Rule
    public ActivityTestRule<ListaLeilaoActivity> activity = new ActivityTestRule<>(ListaLeilaoActivity.class, true,false);

    private final TesteWebClient webClient = new TesteWebClient();
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

        tentaSalvarLeilaoNaApi(new Leilao("Carro"));

        //inicializo a activity apos ter certeza que o salva foi executado
        activity.launchActivity(new Intent());

        //Pega uma view e verifica se possui o texto informado
        onView(//allOf recebe um varargs de validações que podem ser combinadas para validação de uma view
                allOf(
                        withText("Carro"),withId(R.id.item_leilao_descricao)
                )
                //verifica se a view esta sendo exibida
        ).check(matches(isDisplayed()));

        String formatoEsperado = formatadorDeMoeda.formata(0.0);

        onView(
                allOf(
                        withText(formatoEsperado),
                        withId(R.id.item_leilao_maior_lance)
                )
        ).check(matches(isDisplayed()));
    }


    @Test
    public void deve_AparecerDoisLeiloes_QuandoCarregarDoisLeiloesDaApi() throws IOException {

        tentaSalvarLeilaoNaApi(new Leilao("Carro"), new Leilao("Computador"));

        //inicializo a activity apos ter certeza que o salva foi executado
        activity.launchActivity(new Intent());

        //Pega uma view e verifica se possui o texto informado
        onView(
                allOf(
                    withText("Carro"),withId(R.id.item_leilao_descricao)
                )
                //verifica se a view esta sendo exibida
        ).check(matches(isDisplayed()));//Pega uma view e verifica se possui o texto informado
        onView(
                allOf(
                        withText("Computador"),
                        withId(R.id.item_leilao_descricao)
                )
                //verifica se a view esta sendo exibida
        ).check(matches(isDisplayed()));
    }

    private void tentaSalvarLeilaoNaApi(Leilao... leilaoList) throws IOException {
        for(Leilao l: leilaoList){
            Leilao carroSalvo = webClient.salva(l);
            if(l == null) Assert.fail(LEILAO_NAO_SALVO + l.getDescricao());
        }
    }


    private void limpaBancoDeDadosDaApi() throws IOException {
        if(!webClient.limpaBancoDeDados()) Assert.fail(ERRO_LIMPEZA_BANCO_DE_DADOS);
    }

}