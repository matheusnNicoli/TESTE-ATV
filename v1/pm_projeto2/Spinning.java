package pm_projeto2;

import java.util.ArrayList;

public class Spinning extends Atividade implements ControladorPresenca {
    private String horario;
    private int quantidadeBicicletas;
    private ArrayList<String> participantes = new ArrayList<>();

    public Spinning(String nome, String horario, int duracao, int maximo, double valor, StatusAtividade status, int quantidadeBicicletas) {
        super(nome, duracao, maximo, valor, status);
        this.horario = horario;
        this.quantidadeBicicletas = quantidadeBicicletas;
    }

    @Override
    public boolean atividadeColetiva(){ return true; }
    @Override
    public void registrarPresenca(String nomeParticipante){
        if(getStatus()==StatusAtividade.INATIVA){System.out.println("Atividade inativa.");return;}
        if(getStatus()==StatusAtividade.LOTADA){System.out.println("Turma lotada.");return;}
        if(participantes.contains(nomeParticipante)){System.out.println("Presença já registrada nesta turma.");return;}
        int limite = Math.min(getQuantidadeMaximaParticipantes(),quantidadeBicicletas);
        if(participantes.size()>=limite){setStatus(StatusAtividade.LOTADA);System.out.println("Turma lotada.");return;}
        participantes.add(nomeParticipante);
        if(participantes.size()==limite) setStatus(StatusAtividade.LOTADA);
        System.out.println("Presença registrada!");
    }
    @Override
    public void exibirDetalhes(){
        System.out.printf("Spinning: %s | Horário: %s | Duração: %d minutos | Máximo: %d | Bicicletas: %d | Presenças: %d | Mensal: R$%.2f | %s%n",getNome(),horario,getDuracaoAula(),getQuantidadeMaximaParticipantes(),quantidadeBicicletas,participantes.size(),getValorMensal(),getStatus());
    }
}
