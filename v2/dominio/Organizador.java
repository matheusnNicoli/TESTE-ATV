package dominio;

public class Organizador {
    private String nome, cpf, telefone, areaAtuacao;
    public Organizador(String nome, String cpf, String telefone, String areaAtuacao) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.areaAtuacao = areaAtuacao;
    }
    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
    public String getTelefone() { return telefone; }
    public String getAreaAtuacao() { return areaAtuacao; }
}
