import java.util.ArrayList;
import java.util.List;

public class Salao {
    private int numero;
    private int capacidade;
    private String localizacao;
    private String tipo;
    private Organizador organizador;
    private List<Reserva> confirmadas = new ArrayList<>();

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
    public void setOrganizador(Organizador o) { organizador = o; }
    public List<Reserva> getConfirmadas() { return confirmadas; }

    public boolean adicionarReserva(Reserva r) {
        if (r.getConvidados() > capacidade) return false;
        for (Reserva c : confirmadas) {
            if (c.getData().equals(r.getData()) && c.getHorario().equals(r.getHorario())) return false;
        }
        confirmadas.add(r);
        return true;
    }

    public void removerReserva(Reserva r) { confirmadas.remove(r); }
}
