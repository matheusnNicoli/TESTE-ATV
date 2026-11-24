package dominio;

import java.util.ArrayList;
import java.util.List;

public class Salao {
    private int numero, capacidade;
    private String localizacao, tipo;
    private Organizador organizador;
    private List<Reserva> reservas = new ArrayList<>();

    public Salao(int numero, int capacidade, String localizacao, String tipo, Organizador organizador) {
        this.numero = numero;
        this.capacidade = capacidade;
        this.localizacao = localizacao;
        this.tipo = tipo;
        this.organizador = organizador;
    }
    public int getNumero() { return numero; }
    public int getCapacidade() { return capacidade; }
    public String getLocalizacao() { return localizacao; }
    public String getTipo() { return tipo; }
    public Organizador getOrganizador() { return organizador; }
    public void setOrganizador(Organizador organizador) { this.organizador = organizador; }
    public List<Reserva> getReservas() { return reservas; }
    public boolean podeReservar(Reserva r) {
        if(r.getQuantidadeConvidados() > capacidade) return false;
        for (Reserva c : reservas) {
            if(c.getData().equals(r.getData()) && c.getHorario().equals(r.getHorario())) return false;
        }
        return true;
    }
}
