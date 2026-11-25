package pm_projeto2;

import java.util.Scanner;

public class PM_Projeto2 {
    private static Academia academia = new Academia();
    private static Scanner sc = new Scanner(System.in);

    public static void inicializarDados() {
        academia.adicionarAtividade(new Musculacao("Musculação manhã", "08:00", 60, 15, 100, StatusAtividade.ATIVA, 20));
        academia.adicionarAtividade(new Natacao("Natação manhã", "09:00", 50, 10, 150, StatusAtividade.ATIVA, 1.5));
        academia.adicionarAtividade(new Spinning("Spinning tarde", "15:00", 45, 12, 120, StatusAtividade.ATIVA, 12));
        academia.adicionarPlano(new PlanoBasico("Básico", 100, 2, StatusPlano.ATIVO));
        academia.adicionarPlano(new PlanoPremium("Premium", 200, 5, StatusPlano.ATIVO));
    }

    private static int lerInteiro() {
        while (!sc.hasNextInt()) {
            System.out.print("Digite um número válido: ");
            sc.nextLine();
        }
        int numero = sc.nextInt();
        sc.nextLine();
        return numero;
    }

    private static Atividade selecionarAtividade() {
        academia.listarAtividades();
        System.out.print("Número da atividade: ");
        Atividade enc = academia.buscarAtividade(lerInteiro());
        if (enc == null) System.out.println("Atividade não encontrada.");
        return enc;
    }

    private static Plano selecionarPlano() {
        academia.listarPlanos(false);
        System.out.print("Número do plano: ");
        Plano enc = academia.buscarPlano(lerInteiro());
        if (enc == null) System.out.println("Plano não encontrado.");
        return enc;
    }

    private static void mostrarMenu() {
        System.out.println("\n--- Gerenciamento da Academia ---");
        System.out.println("1. Associar atividade a plano");
        System.out.println("2. Listar atividades");
        System.out.println("3. Listar planos");
        System.out.println("4. Listar planos ativos");
        System.out.println("5. Emitir certificado");
        System.out.println("6. Registrar presença em atividade");
        System.out.println("7. Remover atividades inativas");
        System.out.println("8. Registrar acesso diário");
        System.out.println("9. Alterar estado da atividade");
        System.out.println("10. Alterar estado do plano");
        System.out.println("0. Encerrar");
        System.out.print("Opção: ");
    }

    public static void main(String[] args) {
        inicializarDados();
        int opcao;
        do {
            mostrarMenu();
            opcao = lerInteiro();
            switch (opcao) {
                case 1:
                    Plano p = selecionarPlano();
                    Atividade a = selecionarAtividade();
                    System.out.println(academia.associar(p, a) ? "Atividade associada!" : "Associação não permitida (estado, limite, duplicidade ou modalidade).");
                    break;
                case 2:
                    academia.listarAtividades();
                    break;
                case 3:
                    academia.listarPlanos(false);
                    break;
                case 4:
                    academia.listarPlanos(true);
                    break;
                case 5:
                    Atividade c = selecionarAtividade();
                    if (c == null) break;
                    if (!(c instanceof Certificavel)) { System.out.println("Esta atividade não emite certificados."); break; }
                    System.out.print("Participante: ");
                    String participante = sc.nextLine().trim();
                    if (participante.isEmpty()) { System.out.println("Nome obrigatório."); break; }
                    ((Certificavel) c).emitirCertificado(participante);
                    break;
                case 6:
                    Atividade presenca = selecionarAtividade();
                    if (presenca == null) break;
                    if (!(presenca instanceof ControladorPresenca)) { System.out.println("Esta atividade não registra presença."); break; }
                    System.out.print("Participante: ");
                    String nome = sc.nextLine().trim();
                    if (nome.isEmpty()) { System.out.println("Nome obrigatório."); break; }
                    ((ControladorPresenca) presenca).registrarPresenca(nome);
                    break;
                case 7:
                    System.out.printf("Atividades removidas: %d%n", academia.removerInativas());
                    break;
                case 8:
                    Plano plano = selecionarPlano();
                    if (plano == null) break;
                    System.out.print("Cliente: ");
                    String cliente = sc.nextLine().trim();
                    System.out.println(academia.registrarAcesso(plano, cliente) ? "Acesso autorizado." : "Acesso negado (plano inativo ou limite diário).");
                    break;
                case 9:
                    Atividade alterar = selecionarAtividade();
                    if (alterar == null) break;
                    System.out.print("1-ATIVA, 2-LOTADA, 3-INATIVA: ");
                    int estado = lerInteiro();
                    if (estado >= 1 && estado <= 3) alterar.setStatus(StatusAtividade.values()[estado - 1]);
                    else System.out.println("Estado inválido.");
                    break;
                case 10:
                    Plano alterarPlano = selecionarPlano();
                    if (alterarPlano == null) break;
                    System.out.print("1-ATIVO, 2-SUSPENSO, 3-CANCELADO: ");
                    int situacao = lerInteiro();
                    if (situacao >= 1 && situacao <= 3) alterarPlano.setStatus(StatusPlano.values()[situacao - 1]);
                    else System.out.println("Estado inválido.");
                    break;
                case 0:
                    System.out.println("Sistema encerrado.");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
        sc.close();
    }
}
