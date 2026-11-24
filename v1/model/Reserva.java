package model;

public class Reserva {
    private int codigo, quantidadeConvidados;
    private String nomeCliente, data, horario, status;
    private double valorTotal;
    private Salao salao;

    public Reserva(int codigo, String nomeCliente, String data, String horario, int quantidadeConvidados, double valorTotal){
        this.codigo = codigo;
        this.nomeCliente = nomeCliente;
        this.data = data;
        this.horario = horario;
        this.quantidadeConvidados = quantidadeConvidados;
        this.valorTotal = valorTotal;
        this.status = "solicitada";
    }
    public int getCodigo(){ return codigo; }
    public String getNomeCliente(){ return nomeCliente; }
    public String getData(){ return data; }
    public String getHorario(){ return horario; }
    public int getQuantidadeConvidados(){ return quantidadeConvidados; }
    public double getValorTotal(){ return valorTotal; }
    public String getStatus(){ return status; }
    public Salao getSalao(){ return salao; }
    public void confirmar(Salao salao){ this.salao=salao; this.status="confirmada"; }
    public void finalizar(){ this.status="finalizada"; }
    public void exibirDetalhes(){
        System.out.printf("Código: %d | Cliente: %s | Data: %s | Horário: %s%n",codigo,nomeCliente,data,horario);
        System.out.printf("Convidados: %d | Status: %s | Valor: R$ %.2f%n",quantidadeConvidados,status,valorTotal);
        if(salao == null) System.out.println("Salão: não atribuído | Organizador: não atribuído");
        else System.out.printf("Salão: %d - %s | Organizador: %s%n",salao.getNumero(),salao.getLocalizacao(),salao.getOrganizador().getNome());
    }
}
