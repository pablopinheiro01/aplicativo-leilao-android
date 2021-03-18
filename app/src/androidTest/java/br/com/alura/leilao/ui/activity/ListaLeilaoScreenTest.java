package br.com.alura.leilao.ui.activity;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

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

    @Test
    public void deve_AparecerUmLeiLao_QuandoCarregarUmLeiLaoNaAPI() throws IOException {
//        LeilaoWebClient webClient = new LeilaoWebClient();
        TesteWebClient webClient = new TesteWebClient();
        if(!webClient.limpaBancoDeDados()) Assert.fail("Banco de dados não foi limpo");

        Leilao carroSalvo = webClient.salva(new Leilao("Carro"));
        if(carroSalvo == null) Assert.fail("Leilão não foi salvo");

        //inicializo a activity apos ter certeza que o salva foi executado
        activity.launchActivity(new Intent());

        //Pega uma view e verifica se possui o texto informado
        onView(withText("Carro"))
                //verifica se a view esta sendo exibida
                .check(matches(isDisplayed()));
    }

}