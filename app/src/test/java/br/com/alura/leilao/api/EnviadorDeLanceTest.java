package br.com.alura.leilao.api;

import android.content.Context;

import net.bytebuddy.asm.Advice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import br.com.alura.leilao.api.retrofit.client.LeilaoWebClient;
import br.com.alura.leilao.api.retrofit.client.RespostaListener;
import br.com.alura.leilao.exception.LanceMenorQueUltimoLanceException;
import br.com.alura.leilao.exception.LanceSeguidoDoMesmoUsuarioException;
import br.com.alura.leilao.exception.UsuarioJaDeuCincoLancesException;
import br.com.alura.leilao.model.Lance;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.ui.dialog.AvisoDialogManager;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class EnviadorDeLanceTest {

    @Mock
    private LeilaoWebClient client;
    @Mock
    private EnviadorDeLance.LanceProcessadoListener listener;
    @Mock
    private Context context;
    @Mock
    private AvisoDialogManager manager;
    @Mock
    private Leilao leilao;

    @Test
    public void deve_MostrarMensagemDeFalha_QuandoLanceForMenorQueOUltimoLance(){
        EnviadorDeLance enviadorDeLance = new EnviadorDeLance(
                client,
                listener,
                manager
        );

        Mockito.doThrow(LanceMenorQueUltimoLanceException.class).when(leilao).propoe(ArgumentMatchers.any(Lance.class));
        enviadorDeLance.envia(leilao, new Lance(new Usuario("Fran"),200.0));
        Mockito.verify(manager).mostraAvisoLanceMenorQueUltimoLance();
    }


    @Test
    public void deve_MostrarMensagemDeFalha_QuandoUsuarioComCincoLancesDerNovoLance(){
        EnviadorDeLance enviadorDeLance = new EnviadorDeLance(
                client,
                listener,
                manager
        );

        Mockito.doThrow(UsuarioJaDeuCincoLancesException.class).when(leilao).propoe(ArgumentMatchers.any(Lance.class));

        enviadorDeLance.envia(leilao, new Lance(new Usuario("Alex"),200.0));

        Mockito.verify(manager).mostraAvisoUsuarioJaDeuCincoLances();

    }

    @Test
    public void deve_MostrarMensagemDeFalha_QuandoOUsuarioDoUltimoLanceDerNovoLance(){
        EnviadorDeLance enviadorDeLance = new EnviadorDeLance(
                client,
                listener,
                manager
        );

        Mockito.doThrow(LanceSeguidoDoMesmoUsuarioException.class).when(leilao).propoe(ArgumentMatchers.any(Lance.class));
        enviadorDeLance.envia(leilao, new Lance(new Usuario("Joao"),1000.0));
        Mockito.verify(manager).mostraAvisoLanceSeguidoDoMesmoUsuario();

    }

    @Test
    public void deve_MostraMensagemDeFalha_QuandoFalharEnvioDeLanceParaAPI(){
        EnviadorDeLance enviadorDeLance = new EnviadorDeLance(
                client,
                listener,
                manager
        );

        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                RespostaListener<Void> argument = invocation.getArgument(2);
                argument.falha("");
                return null;
            }
        }).when(client).propoe(
                Mockito.any(Lance.class), Mockito.anyLong(), Mockito.any(RespostaListener.class)
        );

        enviadorDeLance.envia(leilao, new Lance(new Usuario("joao"),100.0));

        Mockito.verify(manager).mostraToastFalhaNoEnvio();
        Mockito.verify(listener, Mockito.never()).processado(leilao);

    }

    @Test
    public void deve_NotificarLanceProcessado_QuandoEnviarLanceParaAPIComSucesso(){

        EnviadorDeLance enviadorDeLance = new EnviadorDeLance(
                client,
                listener,
                manager
        );

        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                RespostaListener<Void> argument = invocation.getArgument(2);
                argument.sucesso(Mockito.any(Void.class));
                return null;
            }
        }).when(client).propoe(
                Mockito.any(Lance.class), Mockito.anyLong(), Mockito.any(RespostaListener.class)
        );

        enviadorDeLance.envia(leilao, new Lance(new Usuario("joao"),100.0));

        Mockito.verify(listener).processado(Mockito.any(Leilao.class));
        Mockito.verify(manager, Mockito.never()).mostraToastFalhaNoEnvio();

    }


}