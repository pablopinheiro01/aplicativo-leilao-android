package br.com.alura.leilao.ui.recyclerview.adapter;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Display;

import org.junit.Test;
import org.mockito.Mockito;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

import br.com.alura.leilao.model.Leilao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class ListaLeilaoAdapterTest {

    @Test
    public void deve_AtualizarListaDeLeiloes_QuandoReceberListaDeLeiloes(){

        //criado um objeto mock
        //nao executa nada do fonte que estamos simulando
        Context context = Mockito.mock(Context.class);

        //cria um objeto espiao do mockito
        // o spy executa o objeto da classe esperada no caso eu criei um context anteriormente.
        ListaLeilaoAdapter listaLeilaoAdapter = Mockito.spy(new ListaLeilaoAdapter(context));
        //no momento que estiver espiando o objeto nao vamos fazer nada quando chamar o metodo atualizaLista que encapsula notifydatasetchanged.
        Mockito.doNothing().when(listaLeilaoAdapter).atualizaLista();

        listaLeilaoAdapter.atualiza(new ArrayList<Leilao>(Arrays.asList(
                new Leilao("Console"),
                new Leilao("Computador")
        )));

        int quantidadeDeLeiloes = listaLeilaoAdapter.getItemCount();
        //verifica o objeto mockado, e analisa se o metodo em questao foi chamado ou nao no caso o atualizalista
        Mockito.verify(listaLeilaoAdapter).atualizaLista();
        assertThat(quantidadeDeLeiloes, is(2));

    }

}