package model;

public class Reserva {
    private int codigo;
    private String nomeCliente;
    private String data;
    private String horario;
    private int convidados;
    private String status;
    private double valor;
    private Salao salao;

    public Reserva(int codigo, String nomeCliente, String data, String horario, int convidados, String status, double valor){
        this.codigo = codigo;
        this.nomeCliente = nomeCliente;
        this.data = data;
        this.horario = horario;
        this.convidados = convidados;
        this.status = status;
        this.valor = valor;
    }
    public int getCodigo(){return codigo;}
    public String getNomeCliente(){return nomeCliente;}
    public String getData(){return data;}
    public String getHorario(){return horario;}
    public int getConvidados(){return convidados;}
    public String getStatus(){return status;}
    public double getValor(){return valor;}
    public Salao getSalao(){return salao;}
    public void setSalao(Salao salao){this.salao = salao;}
    public void exibir(){
        System.out.printf("Codigo: %d | Cliente: %s | Data: %s | Horario: %s | Convidados: %d | Status: %s | Valor: R$%.2f%n",codigo,nomeCliente,data,horario,convidados,status,valor);
        if(salao == null){System.out.println("Salao: nao atribuido | Organizador: nao atribuido");}
        else{System.out.printf("Salao: %d (%s - %s) | Organizador: %s (%s, %s, %s)%n",salao.getNumero(),salao.getLocalizacao(),salao.getTipo(),salao.getOrganizador().getNome(),salao.getOrganizador().getCpf(),salao.getOrganizador().getTelefone(),salao.getOrganizador().getAreaAtuacao());}
    }
}
