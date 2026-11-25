package pm_projeto2;

import java.util.ArrayList;
import java.util.List;

public class Natacao extends Atividade implements Certificavel, ControladorPresenca {
    private String horario;
    private double profundidade;
    private List<String> presencas = new ArrayList<>();

    public Natacao(String nome, String horario, int duracao, int limite, double valor, StatusAtividade status, double profundidade) {
        super(nome, duracao, limite, valor, status);
        this.horario = horario;
        this.profundidade = profundidade;
    }

    @Override
    public boolean atividadeColetiva() { return true; }

    @Override
    public void registrarPresenca(String nomeParticipante) {
        if (getStatus() == StatusAtividade.INATIVA) { System.out.println("Atividade inativa."); return; }
        if (getStatus() == StatusAtividade.LOTADA) { System.out.println("Turma lotada."); return; }
        if (presencas.contains(nomeParticipante)) { System.out.println("Presença já registrada nesta turma."); return; }
        if (presencas.size() >= getQuantidadeMaximaParticipantes()) { setStatus(StatusAtividade.LOTADA); System.out.println("Turma lotada."); return; }
        presencas.add(nomeParticipante);
        if (presencas.size() == getQuantidadeMaximaParticipantes()) setStatus(StatusAtividade.LOTADA);
        System.out.println("Presença registrada!");
    }

    @Override
    public void emitirCertificado(String nomeParticipante) {
        if (!presencas.contains(nomeParticipante)) { System.out.println("Participante sem presença registrada nesta turma."); return; }
        System.out.printf("Certificado de Natação emitido para: %s%n", nomeParticipante);
    }

    @Override
    public void exibirDetalhes() {
        System.out.printf("Natação: %s | %s | %d min | Limite: %d | Piscina: %.2f m | Presenças: %d | R$%.2f | %s%n",
                getNome(), horario, getDuracaoAula(), getQuantidadeMaximaParticipantes(), profundidade, presencas.size(), getValorMensal(), getStatus());
    }
}
