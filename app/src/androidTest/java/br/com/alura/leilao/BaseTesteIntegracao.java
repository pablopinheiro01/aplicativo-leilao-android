package br.com.alura.leilao;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import java.io.IOException;

import br.com.alura.leilao.api.retrofit.client.TesteWebClient;
import br.com.alura.leilao.model.Leilao;

public abstract class BaseTesteIntegracao {

    public static final String LEILAO_NAO_SALVO = "Leilão não foi salvo: ";
    public static final String ERRO_LIMPEZA_BANCO_DE_DADOS = "Banco de dados não foi limpo";

    private final TesteWebClient webClient = new TesteWebClient();

    protected void tentaSalvarLeilaoNaApi(Leilao... leilaoList) throws IOException {
        for(Leilao l: leilaoList){
            Leilao carroSalvo = webClient.salva(l);
            if(l == null) Assert.fail(LEILAO_NAO_SALVO + l.getDescricao());
        }
    }

    protected void limpaBancoDeDadosDaApi() throws IOException {
        if(!webClient.limpaBancoDeDados()) Assert.fail(ERRO_LIMPEZA_BANCO_DE_DADOS);
    }

    protected void limpaDBLocal() {
        Context context = InstrumentationRegistry.getTargetContext();
        context.deleteDatabase(BuildConfig.DB);
    }

}
