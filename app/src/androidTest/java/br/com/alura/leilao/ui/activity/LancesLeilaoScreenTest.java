package br.com.alura.leilao.ui.activity;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;

import org.hamcrest.core.AllOf;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import br.com.alura.leilao.BaseTesteIntegracao;
import br.com.alura.leilao.R;
import br.com.alura.leilao.database.DatabaseHelper;
import br.com.alura.leilao.database.dao.UsuarioDAO;
import br.com.alura.leilao.formatter.FormatadorDeMoeda;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
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
        onView(allOf(
                withId(R.id.lances_leilao_fab_adiciona), isDisplayed()
        )).perform(click());

        //verifica se aparece o dialog com o aviso por nao ter usuario com titulo e mensagem esperada
        onView(allOf(withText("Usuários não encontrados"),
                //encontrei o alertTitle alterando o texto acima provocando um erro, apos a exibicao da stack de erro eu busquei na hierarquia
                //o res-name que seria o nome do recurso assim consigo realizar o match se o texto acima esta sendo apresentado no recurso esperado
                withId(R.id.alertTitle)))
                .check(matches(isDisplayed()));

        onView(allOf(
                withText("Não existe usuários cadastrados! Cadastre um usuário para propor o lance."),
                withId(android.R.id.message)))//tecnica utilizada descrita acima para buscar o nome do recurso neste caso referenciei o id do android e nao do meu pacote
                .check(matches(isDisplayed()));

        //clica em cadastrar usuario dentro do dialog
        onView(allOf(
                withText("Cadastrar usuário"),
                isDisplayed()))
                .perform(click());

        //clica no FAB para cadastrar novo usuario - tela de lista de usuarios
        onView(allOf(withId(R.id.lista_usuario_fab_adiciona),
                isDisplayed()))
                .perform(click());

        //clica no edittext e preenche com o nome do usuario
        onView(allOf(withId(R.id.form_usuario_input_text),
                isDisplayed()))
                .perform(
                        click(),
                        typeText("Joao"), //alterado para digitar o texto na view como o usuario
                        closeSoftKeyboard());

        //clica em adicionar
        onView(allOf(
                withId(android.R.id.button1), withText("Adicionar"),isDisplayed()
        )).perform(scrollTo(), click());

        //clica no back do android e volta para a tela onde tem os lances do leilao
        Espresso.pressBack();

        //clica no FAB
        onView(allOf(withId(R.id.lances_leilao_fab_adiciona),isDisplayed()))
                .perform(click());

        //verifica visibilidade do dialog com o titulo esperado
        propoemNovoLance("200", 1, "Joao");

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

    @Test
    public void deve_AtualizarLancesDoLeilao_QuandoReceberTresLances() throws IOException {
        //salva leilao na api
        tentaSalvarLeilaoNaApi(new Leilao("Carro"));
        tentaSalvarUsuariosNoBancoDeDados(new Usuario("Joao"),new Usuario("Fran"));

        //inicializa activity
        activity.launchActivity(new Intent());

        //clica no leilao
        onView(withId(R.id.lista_leilao_recyclerview))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));

        propoemNovoLance("200", 1, "Joao");
        propoemNovoLance("300", 2, "Fran");
        propoemNovoLance("400", 1, "Joao");


        //fazer assertion para as views de maior e menor lance
        FormatadorDeMoeda formatador = new FormatadorDeMoeda();
        onView(withId(R.id.lances_leilao_maior_lance))
                .check(
                        matches(
                                allOf(
                                        withText(formatador.formata(400)),isDisplayed()
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
                                allOf(withText("400.0 - (1) Joao\n"
                                        +"300.0 - (2) Fran\n"
                                        +"200.0 - (1) Joao\n"),
                                        isDisplayed()
                                )
                        )
                );
    }

    private void propoemNovoLance(String valorDoLance, int idDoUsuario, String nomeDoUsuario) {

        //clica no FAB
        onView(allOf(withId(R.id.lances_leilao_fab_adiciona),isDisplayed()))
                .perform(click());

        //verifica visibilidade do dialog com o titulo esperado
        onView(allOf(
                withText("Novo lance"),
                withId(R.id.alertTitle)))
                .check(matches(isDisplayed()));

        //clica no edittext de valor e preenche
        onView(allOf(
                withId(R.id.form_lance_valor_edittext),
                isDisplayed())
        ).perform(
                click(),
                typeText(valorDoLance),
                closeSoftKeyboard());

        //seleciona o usuario
        onView(allOf(
                withId(R.id.form_lance_usuario),
                isDisplayed())
        ).perform(click());

        //indica que vamos pegar um usuario com um id e com o nome
        // o ondata faz a interação com a view e ele seleciona o objeto esperado
        onData(is(new Usuario(idDoUsuario, nomeDoUsuario)))
                //vamos indicar o root para pegar o foco com o isPlatformPopup()
                .inRoot(isPlatformPopup())
                .perform(click());

        //clica no botao propor para adicionar novo lance
        onView(allOf(
                withText("Propor"),
                isDisplayed())
        ).perform(click());
    }

    private void tentaSalvarUsuariosNoBancoDeDados(Usuario... usuarios) {
        UsuarioDAO dao = new UsuarioDAO(InstrumentationRegistry.getTargetContext());

        for(Usuario usuario: usuarios){
            Usuario usuarioSalvo = dao.salva(usuario);
            if(usuarioSalvo == null){
                Assert.fail("Usuario: "+usuarioSalvo.getNome()+" nao foi salvo");
            }
        }

    }


}
