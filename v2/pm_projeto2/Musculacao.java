package pm_projeto2;

public class Musculacao extends Atividade {
    private String horario;
    private int aparelhos;

    public Musculacao(String nome, String horario, int duracao, int limite, double valor, StatusAtividade status, int aparelhos) {
        super(nome, duracao, limite, valor, status);
        this.horario = horario;
        this.aparelhos = aparelhos;
    }

    @Override
    public boolean atividadeColetiva() { return false; }

    @Override
    public void exibirDetalhes() {
        System.out.printf("Musculação: %s | %s | %d min | Limite: %d | Aparelhos: %d | R$%.2f | %s%n",
                getNome(), horario, getDuracaoAula(), getQuantidadeMaximaParticipantes(), aparelhos, getValorMensal(), getStatus());
    }
}
