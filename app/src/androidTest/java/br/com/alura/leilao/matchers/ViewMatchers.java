package br.com.alura.leilao.matchers;

import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

import br.com.alura.leilao.R;
import br.com.alura.leilao.formatter.FormatadorDeMoeda;

import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;

public class ViewMatchers {

    public static Matcher<? super View> apareceLeilaoNaPosicao(final int posicaoItem, final String descricaoEsperada, final double maiorLanceEsperado) {

        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class){

            private Matcher<View> displayed = isDisplayed();
            private final FormatadorDeMoeda formatador = new FormatadorDeMoeda();

            private final String maiorLanceEsperadoFormatado = formatador.formata(maiorLanceEsperado);

            @Override
            public void describeTo(Description description) {
                description.appendText(" View com descrição: ")
                        .appendValue(descricaoEsperada)
                        .appendText(", maior lance: ")
                        .appendValue(maiorLanceEsperadoFormatado)
                        .appendText(" na posicao: ")
                        .appendValue(posicaoItem)
                        .appendText(" nao foi encontrada.");
                description.appendDescriptionOf(displayed);
            }

            @Override
            protected boolean matchesSafely(RecyclerView item) {
                RecyclerView.ViewHolder viewHolderDevolvido = item.findViewHolderForAdapterPosition(posicaoItem);
                if(viewHolderDevolvido == null) throw new IndexOutOfBoundsException("View Holder na posição esperada: "+posicaoItem+" nao foi encontrado");

                View viewDoViewHolder = viewHolderDevolvido.itemView;

                boolean temDescricaoEsperada = verificaDescricaoEsperada(viewDoViewHolder);

                boolean temMaiorLanceEsperado = verificaMaiorLanceEsperado(viewDoViewHolder);

                return temDescricaoEsperada
                        && temMaiorLanceEsperado
                        && displayed.matches(viewDoViewHolder);
            }

            private boolean verificaMaiorLanceEsperado(View viewDoViewHolder) {
                TextView textViewMaiorLance = viewDoViewHolder.findViewById(R.id.item_leilao_maior_lance);
                return textViewMaiorLance.getText().toString().equals(maiorLanceEsperadoFormatado);
            }

            private boolean verificaDescricaoEsperada(View viewDoViewHolder) {
                TextView textViewDescricao = viewDoViewHolder.findViewById(R.id.item_leilao_descricao);
                return textViewDescricao.getText().toString().equals(descricaoEsperada);
            }
        };
    }
}
