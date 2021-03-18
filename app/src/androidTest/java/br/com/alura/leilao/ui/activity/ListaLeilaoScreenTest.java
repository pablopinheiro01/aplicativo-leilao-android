package br.com.alura.leilao.ui.activity;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import br.com.alura.leilao.api.retrofit.client.LeilaoWebClient;
import br.com.alura.leilao.api.retrofit.client.TesteWebClient;
import br.com.alura.leilao.model.Leilao;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class ListaLeilaoScreenTest {

    @Rule
    public ActivityTestRule<ListaLeilaoActivity> activity = new ActivityTestRule<>(ListaLeilaoActivity.class, true,false);

    private final TesteWebClient webClient = new TesteWebClient();

    @Test
    public void deve_AparecerUmLeiLao_QuandoCarregarUmLeiLaoNaAPI() throws IOException {

        limpaBaseDeDadosDaApi();

        tentaSalvarLeilaoNaApi(new Leilao("Carro"));

        //inicializo a activity apos ter certeza que o salva foi executado
        activity.launchActivity(new Intent());

        //Pega uma view e verifica se possui o texto informado
        onView(withText("Carro"))
                //verifica se a view esta sendo exibida
                .check(matches(isDisplayed()));
    }


    @Test
    public void deve_AparecerDoisLeiloes_QuandoCarregarDoisLeiloesDaApi() throws IOException {
        limpaBaseDeDadosDaApi();

        tentaSalvarLeilaoNaApi(new Leilao("Carro"), new Leilao("Computador"));

        //inicializo a activity apos ter certeza que o salva foi executado
        activity.launchActivity(new Intent());

        //Pega uma view e verifica se possui o texto informado
        onView(withText("Carro"))
                //verifica se a view esta sendo exibida
                .check(matches(isDisplayed()));//Pega uma view e verifica se possui o texto informado
        onView(withText("Computador"))
                //verifica se a view esta sendo exibida
                .check(matches(isDisplayed()));
    }

    private void limpaBaseDeDadosDaApi() throws IOException {
        if(!webClient.limpaBancoDeDados()) Assert.fail("Banco de dados não foi limpo");
    }

    private void tentaSalvarLeilaoNaApi(Leilao... leilaoList) throws IOException {
        for(Leilao l: leilaoList){
            Leilao carroSalvo = webClient.salva(l);
            if(l == null) Assert.fail("Leilão não foi salvo: "+ l.getDescricao());
        }
    }

}