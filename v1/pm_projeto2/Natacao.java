package pm_projeto2;

import java.util.ArrayList;

public class Natacao extends Atividade implements Certificavel, ControladorPresenca {
    private String horario;
    private double profundidadePiscina;
    private ArrayList<String> participantes = new ArrayList<>();

    public Natacao(String nome, String horario, int duracao, int maximo, double valor, StatusAtividade status, double profundidadePiscina) {
        super(nome, duracao, maximo, valor, status);
        this.horario = horario;
        this.profundidadePiscina = profundidadePiscina;
    }

    @Override
    public boolean atividadeColetiva(){ return true; }
    @Override
    public void registrarPresenca(String nomeParticipante){
        if(getStatus()==StatusAtividade.INATIVA){System.out.println("Atividade inativa.");return;}
        if(getStatus()==StatusAtividade.LOTADA){System.out.println("Turma lotada.");return;}
        if(participantes.contains(nomeParticipante)){System.out.println("Presença já registrada nesta turma.");return;}
        if(participantes.size()>=getQuantidadeMaximaParticipantes()){
            setStatus(StatusAtividade.LOTADA);
            System.out.println("Turma lotada.");return;
        }
        participantes.add(nomeParticipante);
        if(participantes.size()==getQuantidadeMaximaParticipantes()) setStatus(StatusAtividade.LOTADA);
        System.out.println("Presença registrada!");
    }
    @Override
    public void emitirCertificado(String nomeParticipante){
        if(participantes.contains(nomeParticipante)) System.out.println("Certificado de Natação emitido para: " + nomeParticipante);
        else System.out.println("Participante sem presença registrada nesta turma.");
    }
    @Override
    public void exibirDetalhes(){
        System.out.printf("Natação: %s | Horário: %s | Duração: %d minutos | Máximo: %d | Profundidade: %.2f m | Presenças: %d | Mensal: R$%.2f | %s%n",getNome(),horario,getDuracaoAula(),getQuantidadeMaximaParticipantes(),profundidadePiscina,participantes.size(),getValorMensal(),getStatus());
    }
}
