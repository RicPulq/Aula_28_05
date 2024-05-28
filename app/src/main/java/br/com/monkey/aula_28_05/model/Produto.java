package br.com.monkey.aula_28_05.model;

public class Produto {
    private String id;
    private  String nome;
    private  String catgoria;

    public Produto(){
    }

    public Produto(String id, String nome, String catgoria) {
        this.id = id;
        this.nome = nome;
        this.catgoria = catgoria;
    }


}
