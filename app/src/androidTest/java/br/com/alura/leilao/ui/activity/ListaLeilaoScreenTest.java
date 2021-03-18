package br.com.alura.leilao.ui.activity;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class ListaLeilaoScreenTest {

    @Rule
    public ActivityTestRule<ListaLeilaoActivity> activity = new ActivityTestRule<>(ListaLeilaoActivity.class, true,true);

    @Test
    public void deve_AparecerUmLeiLao_QuandoCarregarUmLeiLaoNaAPI(){
        //Pega uma view e verifica se possui o texto informado
        onView(withText("Console"))
                //verifica se a view esta sendo exibida
                .check(matches(isDisplayed()));
    }

}