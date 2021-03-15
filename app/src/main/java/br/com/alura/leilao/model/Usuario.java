package br.com.alura.leilao.model;

import java.io.Serializable;

public class Usuario implements Serializable {

    private final String nome;

    public Usuario(String nome) {
        this.nome = nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;

        Usuario usuario = (Usuario) o;

        return nome != null ? nome.equals(usuario.nome) : usuario.nome == null;
    }

    @Override
    public int hashCode() {
        return nome != null ? nome.hashCode() : 0;
    }
}
