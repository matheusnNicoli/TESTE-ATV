package model;

public class Reserva {
    private int codigo;
    private String nomeCliente;
    private String data;
    private String horario;
    private int qntConvidados;
    private String status;
    private double valorTotal;
    private Salao salao;

    public Reserva(int codigo, String nomeCliente, String data, String horario, int qntConvidados, double valorTotal) {
        this.codigo = codigo;
        this.nomeCliente = nomeCliente;
        this.data = data;
        this.horario = horario;
        this.qntConvidados = qntConvidados;
        this.valorTotal = valorTotal;
        this.status = "solicitada";
    }

    public int getCodigo() { return codigo; }
    public String getNomeCliente() { return nomeCliente; }
    public String getData() { return data; }
    public String getHorario() { return horario; }
    public int getQntConvidados() { return qntConvidados; }
    public String getStatus() { return status; }
    public double getValorTotal() { return valorTotal; }
    public Salao getSalao() { return salao; }

    public boolean confirmar(Salao salao) {
        if (!status.equals("solicitada") || salao == null || qntConvidados > salao.getCapacidade() || !salao.estaDisponivel(data, horario)) return false;
        this.salao = salao;
        status = "confirmada";
        salao.addReserva(this);
        return true;
    }

    public boolean finalizar() {
        if (!status.equals("confirmada")) return false;
        status = "finalizada";
        salao.removerReserva(this);
        return true;
    }

    public void exibirDetalhes() {
        System.out.printf("Código: %d | Cliente: %s | Data: %s | Horário: %s%n", codigo, nomeCliente, data, horario);
        System.out.printf("Convidados: %d | Status: %s | Valor: R$ %.2f%n", qntConvidados, status, valorTotal);
        if (salao != null) {
            System.out.printf("Salão: %d (%s) | Organizador: %s%n", salao.getNumero(), salao.getLocalizacao(), salao.getOrganizador().getNome());
        } else System.out.println("Salão: não atribuído | Organizador: não atribuído");
    }
}
