package model;

import java.util.ArrayList;

public class Salao {
    private int numero;
    private int capacidade;
    private String localizacao;
    private String tipo;
    private Organizador organizador;
    private ArrayList<Reserva> reservas = new ArrayList<>();

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
    public ArrayList<Reserva> getReservas() { return reservas; }

    public boolean estaDisponivel(String data, String horario) {
        for (Reserva i : reservas) {
            if (i.getData().equals(data) && i.getHorario().equals(horario)) return false;
        }
        return true;
    }

    public void addReserva(Reserva reserva) { reservas.add(reserva); }
    public void removerReserva(Reserva reserva) { reservas.remove(reserva); }
}
