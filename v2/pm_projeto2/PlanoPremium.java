package pm_projeto2;

import java.util.ArrayList;
import java.util.List;

public class PlanoPremium extends Plano {
    private List<Atividade> atividades = new ArrayList<>();

    public PlanoPremium(String nome, double valor, int limite, StatusPlano status) {
        super(nome, valor, limite, status);
    }

    @Override
    public boolean permiteAtividadeColetiva() { return true; }

    public boolean adicionarAtividade(Atividade a) {
        if (getStatus() != StatusPlano.ATIVO || a.getStatus() == StatusAtividade.INATIVA) return false;
        if (atividades.contains(a) || atividades.size() >= getQuantidadeMaximaAtividades()) return false;
        return atividades.add(a);
    }

    public void removerAtividade(Atividade a) { atividades.remove(a); }

    public boolean registrarAcesso(String cliente) { return getStatus() == StatusPlano.ATIVO; }

    @Override
    public void exibirDetalhes() {
        System.out.printf("Premium: %s | R$%.2f | %s | Atividades: %d/%d | Acesso ilimitado%n",
                getNome(), getValorMensal(), getStatus(), atividades.size(), getQuantidadeMaximaAtividades());
        for (Atividade a : atividades) System.out.println("  - " + a.getNome());
    }
}
