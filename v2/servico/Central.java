package servico;

import java.util.ArrayList;
import java.util.List;
import dominio.*;

public class Central {
    private List<Organizador> organizadores = new ArrayList<>();
    private List<Salao> saloes = new ArrayList<>();
    private List<Reserva> reservas = new ArrayList<>();

    public void cadastrarOrganizador(Organizador o) { organizadores.add(o); }
    public void cadastrarSalao(Salao s) { saloes.add(s); }
    public List<Organizador> getOrganizadores() { return organizadores; }
    public List<Salao> getSaloes() { return saloes; }

    public Salao buscarSalao(int numero) {
        for (Salao s : saloes) if(s.getNumero() == numero) return s;
        return null;
    }
    public Reserva buscarReserva(int codigo) {
        for (Reserva r : reservas) if(r.getCodigo() == codigo) return r;
        return null;
    }
    public boolean adicionarReserva(Reserva r) {
        if(r.getCodigo() <= 0 || buscarReserva(r.getCodigo()) != null) return false;
        reservas.add(r);
        return true;
    }
    public void associarOrganizador(Salao s, Organizador o) {
        Salao anterior = null;
        for (Salao c : saloes) if(c != s && c.getOrganizador() == o) anterior = c;
        if(anterior != null) anterior.setOrganizador(s.getOrganizador());
        s.setOrganizador(o);
    }
    public boolean confirmar(Reserva r, Salao s) {
        if(r == null || s == null || !r.getStatus().equals("solicitada") || !s.podeReservar(r)) return false;
        r.confirmar(s);
        s.getReservas().add(r);
        return true;
    }
    public boolean finalizar(Reserva r) {
        if(r == null || !r.getStatus().equals("confirmada")) return false;
        r.getSalao().getReservas().remove(r);
        r.finalizar();
        return true;
    }
    public void mostrarConfirmadas(Salao s) {
        int total = 0;
        for (Reserva r : s.getReservas()) {
            if(r.getStatus().equals("confirmada")) { r.exibirDados(); total++; }
        }
        System.out.printf("Total de reservas confirmadas: %d%n", total);
    }
    public void mostrarFinalizadas() {
        for (Salao s : saloes) {
            int total = 0;
            for (Reserva r : reservas) if(r.getSalao() == s && r.getStatus().equals("finalizada")) total++;
            System.out.printf("Salão %d: %d finalizadas%n", s.getNumero(), total);
        }
    }
    public void buscarPorStatus(String status) {
        int total = 0;
        for (Reserva r : reservas) {
            if(r.getStatus().equals(status)) { r.exibirDados(); total++; }
        }
        System.out.printf("Total encontrado: %d%n", total);
    }
}
