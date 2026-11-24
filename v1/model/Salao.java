package model;

import java.util.ArrayList;

public class Salao {
    private int numero, capacidade;
    private String localizacao, tipo;
    private Organizador organizador;
    private ArrayList<Reserva> reservas = new ArrayList<>();

    public Salao(int numero, int capacidade, String localizacao, String tipo, Organizador organizador){
        this.numero=numero;
        this.capacidade=capacidade;
        this.localizacao=localizacao;
        this.tipo=tipo;
        this.organizador=organizador;
    }
    public int getNumero(){ return numero; }
    public int getCapacidade(){ return capacidade; }
    public String getLocalizacao(){ return localizacao; }
    public String getTipo(){ return tipo; }
    public Organizador getOrganizador(){ return organizador; }
    public void setOrganizador(Organizador organizador){ this.organizador=organizador; }
    public ArrayList<Reserva> getReservas(){ return reservas; }
    public boolean horarioDisponivel(Reserva reserva){
        for(Reserva i : reservas){
            if(i.getData().equals(reserva.getData()) && i.getHorario().equals(reserva.getHorario())) return false;
        }
        return true;
    }
}
