package pm_projeto2;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlanoBasico extends Plano {
    private List<Atividade> atividades = new ArrayList<>();
    private Map<String, LocalDate> acessos = new HashMap<>();

    public PlanoBasico(String nome, double valor, int limite, StatusPlano status) {
        super(nome, valor, limite, status);
    }

    @Override
    public boolean permiteAtividadeColetiva() { return false; }

    public boolean adicionarAtividade(Atividade a) {
        if (getStatus() != StatusPlano.ATIVO || a.getStatus() == StatusAtividade.INATIVA) return false;
        if (a.atividadeColetiva() || atividades.contains(a) || atividades.size() >= getQuantidadeMaximaAtividades()) return false;
        return atividades.add(a);
    }

    public void removerAtividade(Atividade a) { atividades.remove(a); }

    public boolean registrarAcesso(String cliente) {
        if (getStatus() != StatusPlano.ATIVO) return false;
        LocalDate hoje = LocalDate.now();
        if (hoje.equals(acessos.get(cliente))) return false;
        acessos.put(cliente, hoje);
        return true;
    }

    @Override
    public void exibirDetalhes() {
        System.out.printf("Básico: %s | R$%.2f | %s | Atividades: %d/%d | 1 acesso por dia%n",
                getNome(), getValorMensal(), getStatus(), atividades.size(), getQuantidadeMaximaAtividades());
        for (Atividade a : atividades) System.out.println("  - " + a.getNome());
    }
}
