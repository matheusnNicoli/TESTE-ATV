package pm_projeto2;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class PlanoBasico extends Plano {
    private ArrayList<Atividade> atividades = new ArrayList<>();
    private HashMap<String,LocalDate> ultimosAcessos = new HashMap<>();

    public PlanoBasico(String nome, double valor, int maximo, StatusPlano status){ super(nome,valor,maximo,status); }
    @Override
    public boolean permiteAtividadeColetiva(){return false;}
    public boolean adicionarAtividade(Atividade atividade){
        if(getStatus()!=StatusPlano.ATIVO || atividade.getStatus()==StatusAtividade.INATIVA || atividade.atividadeColetiva() || atividades.contains(atividade) || atividades.size()>=getQuantidadeMaximaAtividades()) return false;
        atividades.add(atividade);
        return true;
    }
    public void removerAtividade(Atividade atividade){ atividades.remove(atividade); }
    public boolean registrarAcesso(String cliente){
        if(getStatus()!=StatusPlano.ATIVO || LocalDate.now().equals(ultimosAcessos.get(cliente))) return false;
        ultimosAcessos.put(cliente, LocalDate.now());
        return true;
    }
    @Override
    public void exibirDetalhes(){
        System.out.printf("Plano básico: %s | R$%.2f | %s | Atividades: %d/%d | 1 acesso diário%n",getNome(),getValorMensal(),getStatus(),atividades.size(),getQuantidadeMaximaAtividades());
        for(Atividade i : atividades) System.out.println("  - " + i.getNome());
    }
}
