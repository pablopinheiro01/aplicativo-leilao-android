package br.com.alura.leilao.ui;

import android.support.v7.widget.RecyclerView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.alura.leilao.database.dao.UsuarioDAO;
import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.ui.recyclerview.adapter.ListaUsuarioAdapter;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class AtualizadorDeUsuarioTest {

    @Mock
    private UsuarioDAO dao;
    @Mock
    private ListaUsuarioAdapter adapter;
    @Mock
    private RecyclerView recyclerView;

    @Test
    public void deve_AtualizaListaDeUsuarios_QuandoSalvarUsuario(){
        AtualizadorDeUsuario atualizador = new AtualizadorDeUsuario(
                dao,
                adapter,
                recyclerView);

        Usuario alex = new Usuario("Alex");
        //o thenReturn analisa o tipo de retorno que a gente pode dar, simulamos o retorno do metodo do banco de dados
        Mockito.when(dao.salva(alex)).thenReturn(new Usuario(1,"Alex"));

        //eu simulo a posicao que eu quero que seja retornado no adapter no caso sera 0 devido o calculo feito na classe responsavel
        Mockito.when(adapter.getItemCount()).thenReturn(1);

        atualizador.salva(alex);

        Mockito.verify(dao).salva(new Usuario("Alex"));
        Mockito.verify(adapter).adiciona(new Usuario(1,"Alex"));
        Mockito.verify(recyclerView).smoothScrollToPosition(0);

    }

}