import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);
    static Gerenciador gerenciador = new Gerenciador();

    public static void inicializarDados() {
        Organizador a = new Organizador("Ana", "11111111111", "31999990001", "Decoração");
        Organizador b = new Organizador("Bruno", "22222222222", "31999990002", "Cerimonial");
        Organizador c = new Organizador("Carla", "33333333333", "31999990003", "Produção");
        gerenciador.adicionarOrganizador(a);
        gerenciador.adicionarOrganizador(b);
        gerenciador.adicionarOrganizador(c);
        gerenciador.adicionarSalao(new Salao(1, 80, "Centro", "Social", a));
        gerenciador.adicionarSalao(new Salao(2, 150, "Pampulha", "Festas", b));
        gerenciador.adicionarSalao(new Salao(3, 50, "Barreiro", "Reuniões", c));
    }

    static String lerTexto(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String texto = sc.nextLine().trim();
            if (!texto.isEmpty()) return texto;
            System.out.println("O campo não pode ficar vazio.");
        }
    }

    static int lerInteiro(String mensagem) {
        while (true) {
            try { return Integer.parseInt(lerTexto(mensagem)); }
            catch (NumberFormatException e) { System.out.println("Digite um número inteiro válido."); }
        }
    }

    static double lerValor(String mensagem) {
        while (true) {
            try { return Double.parseDouble(lerTexto(mensagem).replace(',', '.')); }
            catch (NumberFormatException e) { System.out.println("Digite um valor numérico válido."); }
        }
    }

    static String lerHorario() {
        while (true) {
            String horario = lerTexto("Horário (manhã/tarde/noite): ").toLowerCase();
            if (horario.equals("manhã") || horario.equals("tarde") || horario.equals("noite")) return horario;
            System.out.println("Horário inválido.");
        }
    }

    static void cadastrar() {
        int codigo = lerInteiro("Código: ");
        if (codigo <= 0 || gerenciador.buscarReserva(codigo) != null) {
            System.out.println("Código inválido ou já cadastrado."); return;
        }
        String nome = lerTexto("Nome do cliente: ");
        String data = lerTexto("Data (AAAA-MM-DD): ");
        if (!data.matches("\\d{4}-\\d{2}-\\d{2}")) { System.out.println("Use a data no formato AAAA-MM-DD."); return; }
        try { java.time.LocalDate.parse(data); }
        catch (java.time.format.DateTimeParseException e) { System.out.println("Data inválida."); return; }
        String horario = lerHorario();
        int convidados = lerInteiro("Quantidade de convidados: ");
        double valor = lerValor("Valor total: ");
        if (convidados <= 0 || !Double.isFinite(valor) || valor < 0) { System.out.println("Quantidade ou valor inválido."); return; }
        gerenciador.cadastrarReserva(new Reserva(codigo, nome, data, horario, convidados, valor));
        System.out.println("Reserva solicitada cadastrada, sem salão atribuído.");
    }

    static void listarSaloes() {
        for (Salao s : gerenciador.getSaloes()) {
            System.out.printf("%d - %s (%s), capacidade %d, organizador %s%n", s.getNumero(), s.getLocalizacao(), s.getTipo(), s.getCapacidade(), s.getOrganizador().getNome());
        }
    }

    static void confirmar() {
        Reserva r = gerenciador.buscarReserva(lerInteiro("Código da reserva: "));
        if (r == null) { System.out.println("Reserva não encontrada."); return; }
        listarSaloes();
        Salao s = gerenciador.buscarSalao(lerInteiro("Número do salão: "));
        if (r.confirmar(s)) System.out.println("Reserva confirmada e atribuída ao salão.");
        else System.out.println("Não foi possível confirmar: confira status, capacidade e disponibilidade.");
    }

    static void finalizar() {
        Reserva r = gerenciador.buscarReserva(lerInteiro("Código da reserva: "));
        if (r != null && r.finalizar()) System.out.println("Reserva finalizada.");
        else System.out.println("Reserva inexistente ou não confirmada.");
    }

    static void associar() {
        listarSaloes();
        Salao s = gerenciador.buscarSalao(lerInteiro("Salão que receberá outro organizador: "));
        if (s == null) { System.out.println("Salão não encontrado."); return; }
        for (int i = 0; i < gerenciador.getOrganizadores().size(); i++) {
            System.out.printf("%d - %s%n", i + 1, gerenciador.getOrganizadores().get(i).getNome());
        }
        int opcao = lerInteiro("Escolha o organizador: ");
        if (opcao < 1 || opcao > gerenciador.getOrganizadores().size()) { System.out.println("Organizador inválido."); return; }
        gerenciador.associarOrganizador(s, gerenciador.getOrganizadores().get(opcao - 1));
        System.out.println("Associação atualizada; todos os salões continuam com um único organizador.");
    }

    static void consultarReserva() {
        Reserva enc = gerenciador.buscarReserva(lerInteiro("Código da reserva: "));
        if (enc == null) System.out.println("Reserva não encontrada.");
        else enc.exibirDados();
    }

    public static void main(String[] args) {
        inicializarDados();
        int opcao;
        do {
            System.out.println("\n--- Gerenciamento de Salões ---");
            System.out.println("1. Cadastrar reserva");
            System.out.println("2. Confirmar e atribuir salão");
            System.out.println("3. Finalizar reserva");
            System.out.println("4. Associar organizador");
            System.out.println("5. Reservas confirmadas de um salão");
            System.out.println("6. Finalizadas por salão");
            System.out.println("7. Buscar por status");
            System.out.println("8. Detalhes de uma reserva");
            System.out.println("9. Listar salões");
            System.out.println("0. Sair");
            opcao = lerInteiro("Opção: ");

            switch (opcao) {
                case 1: cadastrar(); break;
                case 2: confirmar(); break;
                case 3: finalizar(); break;
                case 4: associar(); break;
                case 5:
                    listarSaloes();
                    Salao s = gerenciador.buscarSalao(lerInteiro("Número do salão: "));
                    if (s == null) System.out.println("Salão não encontrado.");
                    else gerenciador.listarConfirmadas(s);
                    break;
                case 6: gerenciador.listarFinalizadas(); break;
                case 7:
                    String status = lerTexto("Status (solicitada/confirmada/finalizada): ").toLowerCase();
                    if (status.equals("solicitada") || status.equals("confirmada") || status.equals("finalizada")) gerenciador.buscarPorStatus(status);
                    else System.out.println("Status inválido.");
                    break;
                case 8: consultarReserva(); break;
                case 9: listarSaloes(); break;
                case 0: System.out.println("Sistema encerrado."); break;
                default: System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }
}
