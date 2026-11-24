import java.util.ArrayList;
import java.util.List;

public class Gerenciador {
    private List<Salao> saloes = new ArrayList<>();
    private List<Organizador> organizadores = new ArrayList<>();
    private List<Reserva> reservas = new ArrayList<>();

    public void adicionarSalao(Salao s) { saloes.add(s); }
    public void adicionarOrganizador(Organizador o) { organizadores.add(o); }
    public List<Salao> getSaloes() { return saloes; }
    public List<Organizador> getOrganizadores() { return organizadores; }

    public boolean cadastrarReserva(Reserva r) {
        if (r.getCodigo() <= 0 || buscarReserva(r.getCodigo()) != null) return false;
        reservas.add(r);
        return true;
    }

    public Reserva buscarReserva(int codigo) {
        for (Reserva r : reservas) {
            if (r.getCodigo() == codigo) return r;
        }
        return null;
    }

    public Salao buscarSalao(int numero) {
        for (Salao s : saloes) {
            if (s.getNumero() == numero) return s;
        }
        return null;
    }

    public void associarOrganizador(Salao s, Organizador o) {
        if (s.getOrganizador() == o) return;
        for (Salao atual : saloes) {
            if (atual != s && atual.getOrganizador() == o) {
                atual.setOrganizador(s.getOrganizador());
                break;
            }
        }
        s.setOrganizador(o);
    }

    public void listarConfirmadas(Salao s) {
        int total = 0;
        for (Reserva r : s.getConfirmadas()) {
            r.exibirDados();
            total++;
        }
        System.out.println("Total de reservas confirmadas: " + total);
    }

    public void listarFinalizadas() {
        for (Salao s : saloes) {
            int total = 0;
            for (Reserva r : reservas) {
                if (r.getSalao() == s && r.getStatus().equals("finalizada")) total++;
            }
            System.out.printf("Salão %d: %d reservas finalizadas%n", s.getNumero(), total);
        }
    }

    public void buscarPorStatus(String status) {
        int total = 0;
        for (Reserva r : reservas) {
            if (r.getStatus().equals(status)) {
                r.exibirDados();
                total++;
            }
        }
        System.out.println("Total encontrado: " + total);
    }
}
