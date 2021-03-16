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
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

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
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class) //indicando a inicialização do mockitoannotations.initmocks....
public class ListaLeilaoAdapterTest {

    @Mock //passando a responsabilidade de injetar o objeto para o mockito
    private Context context;

    @Spy // passando a responsabilidade de injetar o objeto para o mockito
    private ListaLeilaoAdapter listaLeilaoAdapter = new ListaLeilaoAdapter(context);

    @Test
    public void deve_AtualizarListaDeLeiloes_QuandoReceberListaDeLeiloes(){
//        MockitoAnnotations.initMocks(this); //metodo deprecated substituido pela abordagem da anotação de classe
        //no momento que estiver espiando o objeto nao vamos fazer nada quando chamar o metodo atualizaLista que encapsula notifydatasetchanged.
        doNothing().when(listaLeilaoAdapter).atualizaLista();

        listaLeilaoAdapter.atualiza(new ArrayList<Leilao>(Arrays.asList(
                new Leilao("Console"),
                new Leilao("Computador")
        )));

        int quantidadeDeLeiloes = listaLeilaoAdapter.getItemCount();
        //verifica o objeto mockado, e analisa se o metodo em questao foi chamado ou nao no caso o atualizalista
        verify(listaLeilaoAdapter).atualizaLista();
        assertThat(quantidadeDeLeiloes, is(2));

    }

}