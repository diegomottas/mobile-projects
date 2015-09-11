package net.unesc.diego.exerciciocomponentes.cadastro;

public class ItemListView {

    private String texto;


    public ItemListView() {
        this("");
    }

    public ItemListView(String texto) {
        this.texto = texto;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

}
