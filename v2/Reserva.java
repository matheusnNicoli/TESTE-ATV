public class Reserva {
    private int codigo;
    private String nomeCliente;
    private String data;
    private String horario;
    private int convidados;
    private String status;
    private double valorTotal;
    private Salao salao;

    public Reserva(int codigo, String nomeCliente, String data, String horario, int convidados, double valorTotal) {
        this.codigo = codigo;
        this.nomeCliente = nomeCliente;
        this.data = data;
        this.horario = horario;
        this.convidados = convidados;
        this.valorTotal = valorTotal;
        this.status = "solicitada";
    }

    public int getCodigo() { return codigo; }
    public String getData() { return data; }
    public String getHorario() { return horario; }
    public int getConvidados() { return convidados; }
    public String getStatus() { return status; }
    public Salao getSalao() { return salao; }

    public boolean confirmar(Salao s) {
        if (!status.equals("solicitada") || s == null) return false;
        if (!s.adicionarReserva(this)) return false;
        salao = s;
        status = "confirmada";
        return true;
    }

    public boolean finalizar() {
        if (!status.equals("confirmada")) return false;
        status = "finalizada";
        salao.removerReserva(this);
        return true;
    }

    public void exibirDados() {
        System.out.printf("Código: %d | Cliente: %s | Data: %s | Horário: %s%n", codigo, nomeCliente, data, horario);
        System.out.printf("Convidados: %d | Status: %s | Valor: R$ %.2f%n", convidados, status, valorTotal);
        if (salao == null) System.out.println("Salão: não atribuído | Organizador: não atribuído");
        else System.out.printf("Salão: %d (%s) | Organizador: %s%n", salao.getNumero(), salao.getLocalizacao(), salao.getOrganizador().getNome());
    }
}
