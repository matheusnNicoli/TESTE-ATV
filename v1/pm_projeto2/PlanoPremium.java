package pm_projeto2;

import java.util.ArrayList;

public class PlanoPremium extends Plano {
    private ArrayList<Atividade> atividades = new ArrayList<>();

    public PlanoPremium(String nome, double valor, int maximo, StatusPlano status){ super(nome,valor,maximo,status); }
    @Override
    public boolean permiteAtividadeColetiva(){return true;}
    public boolean adicionarAtividade(Atividade atividade){
        if(getStatus()!=StatusPlano.ATIVO || atividade.getStatus()==StatusAtividade.INATIVA || atividades.contains(atividade) || atividades.size()>=getQuantidadeMaximaAtividades()) return false;
        atividades.add(atividade);
        return true;
    }
    public void removerAtividade(Atividade atividade){ atividades.remove(atividade); }
    public boolean registrarAcesso(String cliente){return getStatus()==StatusPlano.ATIVO;}
    @Override
    public void exibirDetalhes(){
        System.out.printf("Plano premium: %s | R$%.2f | %s | Atividades: %d/%d | Acesso ilimitado%n",getNome(),getValorMensal(),getStatus(),atividades.size(),getQuantidadeMaximaAtividades());
        for(Atividade i : atividades) System.out.println("  - " + i.getNome());
    }
}
