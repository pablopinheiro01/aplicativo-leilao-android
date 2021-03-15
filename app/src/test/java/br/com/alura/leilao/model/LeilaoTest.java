package br.com.alura.leilao.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

import br.com.alura.leilao.exception.LanceMenorQueUltimoLanceException;
import br.com.alura.leilao.exception.LanceSeguidoDoMesmoUsuarioException;
import br.com.alura.leilao.exception.UsuarioJaDeuCincoLancesException;

import static org.junit.Assert.*;

public class LeilaoTest {
    public static final double DELTA = 0.0001;

    //nomenclatura de metodos de teste
    //[nome do metodo][estado de teste][resultado esperado]
    //[deve][resultado esperado][estado de teste]   '

    private final Leilao CONSOLE = new Leilao("Console");
    private final Usuario ALEX = new Usuario("Alex");

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void getDescricao() {
        //cria cenario de testes
        //executa acao esperada
        String descricaoDevolvida = CONSOLE.getDescricao();
        //testa resultado esperado
        assertEquals("Console", descricaoDevolvida);
    }

    @Test
    public void getMaiorLanceQuandoRecebeApenasUmLanceDevolveMaiorLance() {
        CONSOLE.propoe(new Lance(new Usuario("Joao"), 200.0));

        System.out.println((5.1 + 4.3));
        System.out.println((4.0 + 5.4));
        //Usando o Delta no calculo de double
        //a diferença entre os valores com ponto flutuante e se ele for maior,
        // significa que os valores sao equivalentes
        System.out.println("O Valor do delta é: " + ((9.4) - (9.399999999999999)));
        System.out.println(((9.4) - (9.399999999999999)) < 0.00000000000001);
        System.out.println((5.1 + 4.3) == (4.0 + 5.4));

        double maiorLance = CONSOLE.getMaiorLance();

        //verifica se devolve o maior lance com apenas um lance
        //o numero padrao usado para calculos comuns no delta adotado pelos desenvolvedores é normalmente 0.0001
        assertEquals(200.0, maiorLance, DELTA);
    }

//    @Test
//    public void getMaiorLanceEmOrdemCrescente() {
//        //verificar se devolve maior lance com mais de um lance em ordem crescente
//        CONSOLE.propoe(new Lance(ALEX, 100.0));
//        CONSOLE.propoe(new Lance(new Usuario("Fran"), 200.0));
//
//        double maiorLance = CONSOLE.getMaiorLance();
//        assertEquals(200.00, maiorLance, DELTA);
//    }
//
//    @Test
//    public void getMaiorLanceEmOrdemDecrescente() {
//        //verifica se devolve maior lance com mais de um lance em ordem decrescente
//        CONSOLE.propoe(new Lance(ALEX, 20000.0));
//        CONSOLE.propoe(new Lance(new Usuario("Fran"), 10000.0));
//
//        double maiorLance = CONSOLE.getMaiorLance();
//        assertEquals(20000.0, maiorLance, DELTA);
//    }

    @Test
    public void deve_DevolverMenorLance_QuandoRecebeApenasUmLance() {
        CONSOLE.propoe(new Lance(ALEX, 200.0));

        double menorLance = CONSOLE.getMenorLance();

        assertEquals(200.0, menorLance, DELTA);
    }

//    @Test
//    public void deve_DevolverOMenorLance_QuandoRecebeMaisDeUmLanceEmOrdemCrescente() {
//        //verifica se devolve maior lance com mais de um lance em ordem decrescente
//        CONSOLE.propoe(new Lance(ALEX, 10000.0));
//        CONSOLE.propoe(new Lance(new Usuario("Fran"), 20000.0));
//
//        double menorLance = CONSOLE.getMenorLance();
//        assertEquals(10000.0, menorLance, DELTA);
//    }



    @Test
    public void deve_DevolverOsTresMaioresLances_QuandoRecebeExatosTresLances() {
        CONSOLE.propoe(new Lance(ALEX, 200.0));
        CONSOLE.propoe(new Lance(new Usuario("Fran"), 300.0));
        CONSOLE.propoe(new Lance(new Usuario("Joao"), 400.0));

        List<Lance> tresMaioresLancesDevolvidos = CONSOLE.tresMaioresLances();

        assertEquals(3, tresMaioresLancesDevolvidos.size());
        assertEquals(400.0, tresMaioresLancesDevolvidos.get(0).getValor(), DELTA);
        assertEquals(300.0, tresMaioresLancesDevolvidos.get(1).getValor(), DELTA);
        assertEquals(200.0, tresMaioresLancesDevolvidos.get(2).getValor(), DELTA);
    }

    @Test
    public void deve_DevolverTresMaioresLances_QuandoNaoRecebeLances() {
        List<Lance> tresMaioresLancesDevolvidos = CONSOLE.tresMaioresLances();
        assertEquals(0, tresMaioresLancesDevolvidos.size());
    }

    @Test
    public void deve_DevolverTresMaioresLances_QuandoRecebeApenasUmLance() {
        CONSOLE.propoe(new Lance(ALEX, 200.0));
        List<Lance> tresMaioresLancesDevolvidos = CONSOLE.tresMaioresLances();

        assertEquals(1, tresMaioresLancesDevolvidos.size());
        assertEquals(200.0, tresMaioresLancesDevolvidos.get(0).getValor(), DELTA);
    }

    @Test
    public void deve_DevolverTresMaioresLances_QuandoRecebeApenasDoisLances() {
        CONSOLE.propoe(new Lance(ALEX, 300.0));
        CONSOLE.propoe(new Lance(new Usuario("Fran"), 400.0));

        List<Lance> tresMaioresLances = CONSOLE.tresMaioresLances();

        assertEquals(2, tresMaioresLances.size());
        assertEquals(400.0, tresMaioresLances.get(0).getValor(), DELTA);
        assertEquals(300.0, tresMaioresLances.get(1).getValor(), DELTA);
    }


    @Test
    public void deve_DevolverTresMaioresLances_QuandoRecebeApenasQuatroLances(){
        CONSOLE.propoe(new Lance(ALEX, 300.0));
        CONSOLE.propoe(new Lance(new Usuario("Jao"), 400.0));
        CONSOLE.propoe(new Lance(new Usuario("Carla"), 500.0));
        CONSOLE.propoe(new Lance(new Usuario("vic"), 600.0));

        List<Lance> tresMaioresLances = CONSOLE.tresMaioresLances();

        assertEquals(3, tresMaioresLances.size());
        assertEquals(600.0, tresMaioresLances.get(0).getValor(),DELTA);
        assertEquals(500.0, tresMaioresLances.get(1).getValor(),DELTA);
        assertEquals(400.0, tresMaioresLances.get(2).getValor(),DELTA);
    }

    @Test
    public void deve_DevolverTresMaioresLances_QuandoRecebeMaisDeTresLances(){
        CONSOLE.propoe(new Lance(ALEX, 300.0));
        CONSOLE.propoe(new Lance(new Usuario("Jao"), 400.0));
        CONSOLE.propoe(new Lance(new Usuario("CARLA"), 500.0));
        CONSOLE.propoe(new Lance(new Usuario("felo"), 600.0));

        List<Lance> tresMaioresLances = CONSOLE.tresMaioresLances();

        assertEquals(3, tresMaioresLances.size());
        assertEquals(600.0, tresMaioresLances.get(0).getValor(),DELTA);
        assertEquals(500.0, tresMaioresLances.get(1).getValor(),DELTA);
        assertEquals(400.0, tresMaioresLances.get(2).getValor(),DELTA);

        CONSOLE.propoe(new Lance(ALEX, 700.0));

        List<Lance> tresMaioresLancesParaCincoLancesDevolvido = CONSOLE.tresMaioresLances();

        assertEquals(3, tresMaioresLancesParaCincoLancesDevolvido.size());

        assertEquals(700.0, tresMaioresLancesParaCincoLancesDevolvido.get(0).getValor(), DELTA);
        assertEquals(600.0, tresMaioresLancesParaCincoLancesDevolvido.get(1).getValor(), DELTA);
        assertEquals(500.0, tresMaioresLancesParaCincoLancesDevolvido.get(2).getValor(), DELTA);

    }

    @Test
    public void deve_DevolverValorZeroPAraMaiorLance_QuandoNaoTiverLances(){
        double maiorLanceDevolvido = CONSOLE.getMaiorLance();

        assertEquals(0.0, maiorLanceDevolvido, DELTA);
    }

    @Test
    public void deve_DevolverValorZeroPAraMenorLance_QuandoNaoTiverLances(){
        double menorLance = CONSOLE.getMenorLance();
        assertEquals(0.0, menorLance, DELTA);
    }

    @Test(expected = LanceMenorQueUltimoLanceException.class)
    public void naoDeve_AdicionarLance_QuandoForMenorQueOMaiorLance(){

        CONSOLE.propoe(new Lance(ALEX,500.0));
        CONSOLE.propoe(new Lance(new Usuario("Fran"),10.0));

    }

    @Test(expected = LanceSeguidoDoMesmoUsuarioException.class)
    public void naoDeve_AdicionarLance_QuandoForOMesmoUsuarioDoUltimoLance(){
        CONSOLE.propoe(new Lance(ALEX,500.0));
        CONSOLE.propoe(new Lance(new Usuario("Alex"),600.0));

//        try{
//            CONSOLE.propoe(new Lance(new Usuario("Alex"),600.0));
//            fail("Era esperada uma RuntimeException");
//        }catch (RuntimeException e){
//            //teste passou
//            assertEquals("Mesmo usuario do ultimo lance",e.getMessage());
//        }

    }

    @Test
    public void naoDeve_AdicionarLance_QuandoUsuarioDerCincoLances() {
        exception.expect(UsuarioJaDeuCincoLancesException.class);

        Usuario jao = new Usuario("jao");
        Usuario ferka = new Usuario("Ferka");
        CONSOLE.propoe(new Lance(jao, 100.0));
        CONSOLE.propoe(new Lance(ALEX, 200.0));
        CONSOLE.propoe(new Lance(ferka, 300.0));
        CONSOLE.propoe(new Lance(jao, 400.0));
        CONSOLE.propoe(new Lance(ALEX, 500.0));
        CONSOLE.propoe(new Lance(ferka, 600.0));
        CONSOLE.propoe(new Lance(jao, 700.0));
        CONSOLE.propoe(new Lance(ALEX, 800.0));
        CONSOLE.propoe(new Lance(ferka, 900.0));
        CONSOLE.propoe(new Lance(jao, 1000.0));
        CONSOLE.propoe(new Lance(ALEX, 1100.0));
        CONSOLE.propoe(new Lance(ferka, 1200.0));
        CONSOLE.propoe(new Lance(jao, 2200.0));
        CONSOLE.propoe(new Lance(ALEX, 3300.0));
        CONSOLE.propoe(new Lance(ferka, 3500.0));

        CONSOLE.propoe(new Lance(jao, 22000.0));
        CONSOLE.propoe(new Lance(ALEX, 33000.0));
        CONSOLE.propoe(new Lance(ferka, 35000.0));

    }

}