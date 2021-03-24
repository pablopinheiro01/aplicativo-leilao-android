package br.com.alura.leilao.ui.activity;

import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import br.com.alura.leilao.BaseTesteIntegracao;
import br.com.alura.leilao.R;
import br.com.alura.leilao.formatter.FormatadorDeMoeda;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

public class LancesLeilaoScreenTest extends BaseTesteIntegracao {

    public ActivityTestRule activity =
            new ActivityTestRule<>(ListaLeilaoActivity.class, true, false);

    @Before
    public void setup() throws IOException {
        limpaBancoDeDadosDaApi();
        limpaDBLocal();
    }

    @After
    public void tearDown() throws IOException{
        limpaBancoDeDadosDaApi();
        limpaDBLocal();
    }

    @Test
    public void deve_AtualizarLancesDoLeilao_QuandoReceberUmLance() throws IOException {
        //mapeamento de comportamentos que serao implementados
        //salvar leilao na api
        //inicializar a main activity
        //clica no leilao cadastrado
        //clica no floatactionbutton para adicionar novo lance
        //verifica se aparece dialog com aviso que nao tem usuario e titulo para cadastrar usuario
        //clica em cadastrar usuario dentro do dialog
        //clica no FAB para cadastrar novo usuario - tela de lista de usuarios
        //clica no edittext e preenche com o nome do usuario
        //clica em adicionar
        //clica no back do android e volta para a tela onde tem os lances do leilao
        //clica no FAB lances do leilao
        //verifica visibilidade do dialog com o titulo esperado
        //clica no campo edittext do valor e preenche
        //seleciona o usuario
        //clica no botao propor para adicionar novo lance
        //fazer assertion para as views de maior e menor lance
        //fazer assertion para os maiores lances

        //salva leilao na api
        tentaSalvarLeilaoNaApi(new Leilao("Carro"));
        //inicializa activity
        activity.launchActivity(new Intent());
        //clica no leilao
        onView(withId(R.id.lista_leilao_recyclerview))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        //clica no fab da tela de lances do leilao
        onView(withId(R.id.lances_leilao_fab_adiciona))
                .perform(click());
        //verifica se aparece o dialog com o aviso por nao ter usuario com titulo e mensagem esperada
        onView(withText("Usuários não encontrados"))
                .check(matches(isDisplayed()));
        onView(withText("Não existe usuários cadastrados! Cadastre um usuário para propor o lance."))
                .check(matches(isDisplayed()));
        //clica em cadastrar usuario dentro do dialog
        onView(withText("Cadastrar usuário"))
                .perform(click());
        //clica no FAB para cadastrar novo usuario - tela de lista de usuarios
        onView(allOf(withId(R.id.lista_usuario_fab_adiciona),
                isDisplayed()))
                .perform(click());
        //clica no edittext e preenche com o nome do usuario
        onView(allOf(withId(R.id.form_usuario_input_text),
                isDisplayed()))
                .perform(replaceText("Joao"), closeSoftKeyboard());
        //clica em adicionar
        onView(allOf(
                withId(android.R.id.button1), withText("Adicionar"),isDisplayed()
        )).perform(scrollTo(), click());
        

        //clica no back do android e volta para a tela onde tem os lances do leilao
        Espresso.pressBack();

        //clica no FAB
        onView(withId(R.id.lances_leilao_fab_adiciona))
                .perform(click());
        //verifica visibilidade do dialog com o titulo esperado
        onView(withText("Novo lance"))
                .check(matches(isDisplayed()));
        //clica no edittext de valor e preenche
        onView(withId(R.id.form_lance_valor_edittext))
                .perform(click(),replaceText("200"), closeSoftKeyboard());
        //seleciona o usuario
        onView(withId(R.id.form_lance_usuario))
                .perform(click());
        //indica que vamos pegar um usuario com um id e com o nome
        // o ondata faz a interação com a view e ele seleciona o objeto esperado
        onData(is(new Usuario(1,"Joao")))
                //vamos indicar o root para pegar o foco com o isPlatformPopup()
                .inRoot(isPlatformPopup())
                .perform(click());

        //clica no botao propor para adicionar novo lance
        onView(withText("Propor")).perform(click());
        //fazer assertion para as views de maior e menor lance
        FormatadorDeMoeda formatador = new FormatadorDeMoeda();
        onView(withId(R.id.lances_leilao_maior_lance))
                .check(
                        matches(
                                allOf(
                                        withText(formatador.formata(200)),isDisplayed()
                                )
                        )
                );
        onView(withId(R.id.lances_leilao_menor_lance))
                .check(
                        matches(
                                allOf(withText(formatador.formata(200)),isDisplayed()
                                )
                        )
                );
        //fazer assertion para os maiores lances
        onView(withId(R.id.lances_leilao_maiores_lances))
                .check(
                        matches(
                            allOf(withText("200.0 - (1) Joao\n"), isDisplayed()
                        )
                )
        );




    }
}
