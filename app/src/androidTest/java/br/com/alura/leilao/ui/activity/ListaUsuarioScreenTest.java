package br.com.alura.leilao.ui.activity;


import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.alura.leilao.BaseTesteIntegracao;
import br.com.alura.leilao.BuildConfig;
import br.com.alura.leilao.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

//@LargeTest //anotacao para identificar o tipo de teste, neste caso e um teste qualificado como um teste grande
//@RunWith(AndroidJUnit4.class) // sugestão para caso utilize features do junit 3 e junit 4 em paralelo, por meio dessa biblioteca temos a capacidade de dar o suporte para isso
public class ListaUsuarioScreenTest extends BaseTesteIntegracao {

    @Rule
    public ActivityTestRule<ListaLeilaoActivity> mainActivity = new ActivityTestRule<>(ListaLeilaoActivity.class);

    @Before
    public void setup(){
        limpaDBLocal();
    }

    @After
    public void tearDown(){
        limpaDBLocal();
    }



    @Test
    public void deve_AparecerUsuarioNaListaDeUsuario_QuandoCadastrarUsuario() {
        onView(
                allOf(withId(R.id.lista_leilao_menu_usuarios),
                        withContentDescription("Usuários"),
                        //verifica se e o descendente de actionbar
                       // isDescendantOfA(withId(R.id.action_bar)), //comentado devido nao ser necessario verificar se e descendente da action_Bar
                        isDisplayed()))
                .perform(click());

         onView(allOf(withId(R.id.lista_usuario_fab_adiciona),
                 isDisplayed()))
                 .perform(click());

        onView(allOf(withId(R.id.form_usuario_input_text),
                isDisplayed()))
                .perform(replaceText("Joao"), closeSoftKeyboard());

        onView(allOf(
                withId(android.R.id.button1), withText("Adicionar"),isDisplayed()
        )).perform(scrollTo(), click());

        onView(
                allOf(
                        withId(R.id.item_usuario_id_com_nome),
                        isDisplayed()
                )
        ).check(matches(withText("(1) Joao")));
    }

}
