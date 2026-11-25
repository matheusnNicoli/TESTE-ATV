package pm_projeto2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Academia {
    private List<Atividade> atividades = new ArrayList<>();
    private List<Plano> planos = new ArrayList<>();

    public void adicionarAtividade(Atividade a) { atividades.add(a); }
    public void adicionarPlano(Plano p) { planos.add(p); }

    public Atividade buscarAtividade(int indice) {
        if (indice < 1 || indice > atividades.size()) return null;
        return atividades.get(indice - 1);
    }

    public Plano buscarPlano(int indice) {
        if (indice < 1 || indice > planos.size()) return null;
        return planos.get(indice - 1);
    }

    public void listarAtividades() {
        for (int i = 0; i < atividades.size(); i++) {
            System.out.print((i + 1) + ") ");
            atividades.get(i).exibirDetalhes();
        }
    }

    public void listarPlanos(boolean apenasAtivos) {
        for (int i = 0; i < planos.size(); i++) {
            Plano p = planos.get(i);
            if (apenasAtivos && p.getStatus() != StatusPlano.ATIVO) continue;
            System.out.print((i + 1) + ") ");
            p.exibirDetalhes();
        }
    }

    public boolean associar(Plano p, Atividade a) {
        if (p == null || a == null) return false;
        if (p instanceof PlanoBasico) return ((PlanoBasico) p).adicionarAtividade(a);
        return ((PlanoPremium) p).adicionarAtividade(a);
    }

    public boolean registrarAcesso(Plano p, String cliente) {
        if (p == null || cliente.isEmpty()) return false;
        if (p instanceof PlanoBasico) return ((PlanoBasico) p).registrarAcesso(cliente);
        return ((PlanoPremium) p).registrarAcesso(cliente);
    }

    public int removerInativas() {
        int quantidade = 0;
        Iterator<Atividade> it = atividades.iterator();
        while (it.hasNext()) {
            Atividade a = it.next();
            if (a.getStatus() != StatusAtividade.INATIVA) continue;
            for (Plano p : planos) {
                if (p instanceof PlanoBasico) ((PlanoBasico) p).removerAtividade(a);
                else ((PlanoPremium) p).removerAtividade(a);
            }
            it.remove();
            quantidade++;
        }
        return quantidade;
    }
}
