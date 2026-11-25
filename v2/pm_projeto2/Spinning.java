package pm_projeto2;

import java.util.ArrayList;
import java.util.List;

public class Spinning extends Atividade implements ControladorPresenca {
    private String horario;
    private int bicicletas;
    private List<String> presencas = new ArrayList<>();

    public Spinning(String nome, String horario, int duracao, int limite, double valor, StatusAtividade status, int bicicletas) {
        super(nome, duracao, limite, valor, status);
        this.horario = horario;
        this.bicicletas = bicicletas;
    }

    @Override
    public boolean atividadeColetiva() { return true; }

    @Override
    public void registrarPresenca(String nomeParticipante) {
        if (getStatus() == StatusAtividade.INATIVA) { System.out.println("Atividade inativa."); return; }
        if (getStatus() == StatusAtividade.LOTADA) { System.out.println("Turma lotada."); return; }
        if (presencas.contains(nomeParticipante)) { System.out.println("Presença já registrada nesta turma."); return; }
        int limite = Math.min(getQuantidadeMaximaParticipantes(), bicicletas);
        if (presencas.size() >= limite) { setStatus(StatusAtividade.LOTADA); System.out.println("Turma lotada."); return; }
        presencas.add(nomeParticipante);
        if (presencas.size() == limite) setStatus(StatusAtividade.LOTADA);
        System.out.println("Presença registrada!");
    }

    @Override
    public void exibirDetalhes() {
        System.out.printf("Spinning: %s | %s | %d min | Limite: %d | Bicicletas: %d | Presenças: %d | R$%.2f | %s%n",
                getNome(), horario, getDuracaoAula(), getQuantidadeMaximaParticipantes(), bicicletas, presencas.size(), getValorMensal(), getStatus());
    }
}
