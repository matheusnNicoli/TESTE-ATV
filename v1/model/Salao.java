package model;
import java.util.ArrayList;

public class Salao {
    private int numero;
    private int capacidade;
    private String localizacao;
    private String tipo;
    private Organizador organizador;
    private ArrayList<Reserva> reservas = new ArrayList<>();

    public Salao(int numero, int capacidade, String localizacao, String tipo){
        this.numero = numero;
        this.capacidade = capacidade;
        this.localizacao = localizacao;
        this.tipo = tipo;
    }
    public int getNumero(){return numero;}
    public int getCapacidade(){return capacidade;}
    public String getLocalizacao(){return localizacao;}
    public String getTipo(){return tipo;}
    public Organizador getOrganizador(){return organizador;}
    public ArrayList<Reserva> getReservas(){return reservas;}

    public boolean setOrganizador(Organizador o){
        if(o == null || (o.getSalao()!=null && o.getSalao()!=this)){return false;}
        if(organizador != null && organizador != o){organizador.setSalao(null);}
        organizador = o;
        o.setSalao(this);
        return true;
    }
    public boolean addReserva(Reserva r){
        if(organizador==null || r.getStatus().equals("solicitada") || r.getSalao()!=null || r.getConvidados()>capacidade){return false;}
        for(Reserva i : reservas){
            if(i.getData().equals(r.getData()) && i.getHorario().equals(r.getHorario())){return false;}
        }
        r.setSalao(this);
        if(r.getStatus().equals("confirmada")){reservas.add(r);}
        return true;
    }
}
