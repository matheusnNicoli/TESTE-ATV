package pm_projeto2;

import java.util.ArrayList;
import java.util.Scanner;

public class PM_Projeto2 {
    static ArrayList<Atividade> atividades = new ArrayList<>();
    static ArrayList<Plano> planos = new ArrayList<>();
    static Scanner scan = new Scanner(System.in);

    public static void inicializarDados(){
        atividades.add(new Musculacao("Musculação manhã","08:00",60,15,100,StatusAtividade.ATIVA,20));
        atividades.add(new Natacao("Natação manhã","09:00",50,10,150,StatusAtividade.ATIVA,1.5));
        atividades.add(new Spinning("Spinning tarde","15:00",45,12,120,StatusAtividade.ATIVA,12));
        planos.add(new PlanoBasico("Básico",100,2,StatusPlano.ATIVO));
        planos.add(new PlanoPremium("Premium",200,5,StatusPlano.ATIVO));
    }

    public static int lerNumero(){
        while(!scan.hasNextInt()) {System.out.println("Digite um número válido:");scan.nextLine();}
        int numero = scan.nextInt();scan.nextLine();return numero;
    }

    public static void menu(){
        System.out.println("\n========== ACADEMIA ==========");
        System.out.println("1) Associar atividade ao plano");
        System.out.println("2) Exibir atividades");
        System.out.println("3) Exibir planos");
        System.out.println("4) Exibir planos ativos");
        System.out.println("5) Emitir certificado");
        System.out.println("6) Registrar presença em atividade");
        System.out.println("7) Remover atividades inativas");
        System.out.println("8) Registrar acesso à academia");
        System.out.println("9) Alterar estado da atividade");
        System.out.println("10) Alterar estado do plano");
        System.out.println("0) Sair");
        System.out.print("Opção: ");
    }

    public static Atividade escolherAtividade(){
        for(int i=0;i<atividades.size();i++) System.out.println((i+1)+") "+atividades.get(i).getNome()+" - "+atividades.get(i).getStatus());
        System.out.print("Atividade: ");int opc = lerNumero();
        if(opc<1 || opc>atividades.size()){System.out.println("Atividade inválida.");return null;}
        return atividades.get(opc-1);
    }
    public static Plano escolherPlano(){
        for(int i=0;i<planos.size();i++) System.out.println((i+1)+") "+planos.get(i).getNome()+" - "+planos.get(i).getStatus());
        System.out.print("Plano: ");int opc = lerNumero();
        if(opc<1 || opc>planos.size()){System.out.println("Plano inválido.");return null;}
        return planos.get(opc-1);
    }

    public static void main(String[] args){
        inicializarDados();
        int opc = -1;
        while(opc!=0){
            menu();opc = lerNumero();
            switch(opc){
                case 1:
                    Plano plano = escolherPlano();Atividade atividade = escolherAtividade();
                    if(plano!=null && atividade!=null){
                        boolean resultado = plano instanceof PlanoBasico ? ((PlanoBasico)plano).adicionarAtividade(atividade) : ((PlanoPremium)plano).adicionarAtividade(atividade);
                        System.out.println(resultado ? "Atividade associada!" : "Associação não permitida (estado, limite, duplicidade ou modalidade).");
                    }
                    break;
                case 2:
                    for(Atividade i : atividades) i.exibirDetalhes();
                    break;
                case 3:
                    for(Plano i : planos) i.exibirDetalhes();
                    break;
                case 4:
                    for(Plano i : planos) if(i.getStatus()==StatusPlano.ATIVO) i.exibirDetalhes();
                    break;
                case 5:
                    Atividade a = escolherAtividade();
                    if(a!=null){
                        if(a instanceof Certificavel){System.out.print("Participante: ");((Certificavel)a).emitirCertificado(scan.nextLine().trim());}
                        else System.out.println("Esta atividade não emite certificados.");
                    }
                    break;
                case 6:
                    Atividade b = escolherAtividade();
                    if(b!=null){
                        if(b instanceof ControladorPresenca){System.out.print("Participante: ");String nome = scan.nextLine().trim();if(nome.isEmpty()) System.out.println("Nome obrigatório.");else ((ControladorPresenca)b).registrarPresenca(nome);}
                        else System.out.println("Esta atividade não registra presença.");
                    }
                    break;
                case 7:
                    for(int i=atividades.size()-1;i>=0;i--){
                        if(atividades.get(i).getStatus()==StatusAtividade.INATIVA){
                            Atividade removida = atividades.remove(i);
                            for(Plano p : planos){if(p instanceof PlanoBasico)((PlanoBasico)p).removerAtividade(removida);else ((PlanoPremium)p).removerAtividade(removida);}
                            System.out.println("Removida: "+removida.getNome());
                        }
                    }
                    break;
                case 8:
                    Plano p = escolherPlano();
                    if(p!=null){System.out.print("Cliente: ");String cliente = scan.nextLine().trim();if(cliente.isEmpty()){System.out.println("Nome obrigatório.");break;}
                        boolean acesso = p instanceof PlanoBasico ? ((PlanoBasico)p).registrarAcesso(cliente) : ((PlanoPremium)p).registrarAcesso(cliente);
                        System.out.println(acesso ? "Acesso autorizado." : "Acesso negado (plano inativo ou limite diário).");}
                    break;
                case 9:
                    Atividade alterar = escolherAtividade();
                    if(alterar!=null){System.out.println("1-ATIVA 2-LOTADA 3-INATIVA");int estado = lerNumero();if(estado>=1 && estado<=3) alterar.setStatus(StatusAtividade.values()[estado-1]);else System.out.println("Estado inválido.");}
                    break;
                case 10:
                    Plano alterarPlano = escolherPlano();
                    if(alterarPlano!=null){System.out.println("1-ATIVO 2-SUSPENSO 3-CANCELADO");int estado = lerNumero();if(estado>=1 && estado<=3) alterarPlano.setStatus(StatusPlano.values()[estado-1]);else System.out.println("Estado inválido.");}
                    break;
                case 0:System.out.println("Sistema encerrado.");break;
                default:System.out.println("Opção inválida.");
            }
        }
        scan.close();
    }
}
