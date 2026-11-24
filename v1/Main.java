import java.util.ArrayList;
import java.util.Scanner;
import model.*;

public class Main {
    static Scanner scan = new Scanner(System.in);
    static ArrayList<Salao> saloes = new ArrayList<>();
    static ArrayList<Organizador> organizadores = new ArrayList<>();
    static ArrayList<Reserva> reservas = new ArrayList<>();

    public static void inicializarDados() {
        organizadores.add(new Organizador("Ana", "11111111111", "31999990001", "Decoração"));
        organizadores.add(new Organizador("Bruno", "22222222222", "31999990002", "Cerimonial"));
        organizadores.add(new Organizador("Carla", "33333333333", "31999990003", "Produção"));
        saloes.add(new Salao(1, 80, "Centro", "Social", organizadores.get(0)));
        saloes.add(new Salao(2, 150, "Pampulha", "Festas", organizadores.get(1)));
        saloes.add(new Salao(3, 50, "Barreiro", "Reuniões", organizadores.get(2)));
    }

    static int lerInteiro(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            try { return Integer.parseInt(scan.nextLine().trim()); }
            catch (NumberFormatException e) { System.out.println("Digite um número inteiro válido."); }
        }
    }

    static double lerValor(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            try { return Double.parseDouble(scan.nextLine().trim().replace(',', '.')); }
            catch (NumberFormatException e) { System.out.println("Digite um valor numérico válido."); }
        }
    }

    static String lerTexto(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String texto = scan.nextLine().trim();
            if (!texto.isEmpty()) return texto;
            System.out.println("O campo não pode ficar vazio.");
        }
    }

    static String lerHorario() {
        while (true) {
            String horario = lerTexto("Horário (manhã/tarde/noite): ").toLowerCase();
            if (horario.equals("manhã") || horario.equals("tarde") || horario.equals("noite")) return horario;
            System.out.println("Horário inválido.");
        }
    }

    static Reserva buscarReserva(int codigo) {
        for (Reserva i : reservas) {
            if (i.getCodigo() == codigo) return i;
        }
        return null;
    }

    static Salao buscarSalao(int numero) {
        for (Salao i : saloes) {
            if (i.getNumero() == numero) return i;
        }
        return null;
    }

    static void listarSaloes() {
        for (Salao i : saloes) {
            System.out.printf("%d - %s (%s), capacidade %d, organizador %s%n", i.getNumero(), i.getLocalizacao(), i.getTipo(), i.getCapacidade(), i.getOrganizador().getNome());
        }
    }

    static void cadastrarReserva() {
        int codigo = lerInteiro("Código: ");
        if (codigo <= 0 || buscarReserva(codigo) != null) { System.out.println("Código inválido ou já cadastrado."); return; }
        String nome = lerTexto("Nome do cliente: ");
        String data = lerTexto("Data (AAAA-MM-DD): ");
        if (!data.matches("\\d{4}-\\d{2}-\\d{2}")) { System.out.println("Use a data no formato AAAA-MM-DD."); return; }
        try { java.time.LocalDate.parse(data); }
        catch (java.time.format.DateTimeParseException e) { System.out.println("Data inválida."); return; }
        String horario = lerHorario();
        int qnt = lerInteiro("Quantidade de convidados: ");
        double valor = lerValor("Valor total: ");
        if (qnt <= 0 || !Double.isFinite(valor) || valor < 0) { System.out.println("Quantidade ou valor inválido."); return; }
        reservas.add(new Reserva(codigo, nome, data, horario, qnt, valor));
        System.out.println("Reserva solicitada cadastrada, sem salão atribuído.");
    }

    static void confirmarReserva() {
        Reserva reserva = buscarReserva(lerInteiro("Código da reserva: "));
        if (reserva == null) { System.out.println("Reserva não encontrada."); return; }
        listarSaloes();
        Salao salao = buscarSalao(lerInteiro("Número do salão: "));
        if (reserva.confirmar(salao)) System.out.println("Reserva confirmada e atribuída ao salão.");
        else System.out.println("Não foi possível confirmar: confira status, capacidade e disponibilidade.");
    }

    static void finalizarReserva() {
        Reserva reserva = buscarReserva(lerInteiro("Código da reserva: "));
        if (reserva != null && reserva.finalizar()) System.out.println("Reserva finalizada.");
        else System.out.println("Reserva inexistente ou não confirmada.");
    }

    static void trocarOrganizador() {
        listarSaloes();
        Salao destino = buscarSalao(lerInteiro("Salão que receberá outro organizador: "));
        if (destino == null) { System.out.println("Salão não encontrado."); return; }
        for (int i = 0; i < organizadores.size(); i++) System.out.printf("%d - %s%n", i + 1, organizadores.get(i).getNome());
        int opc = lerInteiro("Escolha o organizador: ");
        if (opc < 1 || opc > organizadores.size()) { System.out.println("Organizador inválido."); return; }
        Organizador escolhido = organizadores.get(opc - 1);
        for (Salao i : saloes) {
            if (i.getOrganizador() == escolhido && i != destino) {
                i.setOrganizador(destino.getOrganizador());
                break;
            }
        }
        destino.setOrganizador(escolhido);
        System.out.println("Associação atualizada; todos os salões continuam com um único organizador.");
    }

    static void listarConfirmadas() {
        listarSaloes();
        Salao salao = buscarSalao(lerInteiro("Número do salão: "));
        if (salao == null) { System.out.println("Salão não encontrado."); return; }
        int qnt = 0;
        for (Reserva i : salao.getReservas()) {
            i.exibirDetalhes();
            qnt++;
        }
        System.out.println("Total de reservas confirmadas: " + qnt);
    }

    static void listarFinalizadas() {
        for (Salao salao : saloes) {
            int qnt = 0;
            for (Reserva i : reservas) {
                if (i.getStatus().equals("finalizada") && i.getSalao() == salao) qnt++;
            }
            System.out.printf("Salão %d: %d reservas finalizadas%n", salao.getNumero(), qnt);
        }
    }

    static void buscarPorStatus() {
        String status = lerTexto("Status (solicitada/confirmada/finalizada): ").toLowerCase();
        if (!status.equals("solicitada") && !status.equals("confirmada") && !status.equals("finalizada")) { System.out.println("Status inválido."); return; }
        int qnt = 0;
        for (Reserva i : reservas) {
            if (i.getStatus().equals(status)) { i.exibirDetalhes(); qnt++; }
        }
        System.out.println("Total encontrado: " + qnt);
    }

    public static void main(String[] args) {
        inicializarDados();
        while (true) {
            System.out.println("\n==============================");
            System.out.println(" GERENCIAMENTO DE SALÕES");
            System.out.println("1) Cadastrar reserva");
            System.out.println("2) Confirmar e atribuir salão");
            System.out.println("3) Finalizar reserva");
            System.out.println("4) Associar organizador");
            System.out.println("5) Reservas confirmadas de um salão");
            System.out.println("6) Finalizadas por salão");
            System.out.println("7) Buscar por status");
            System.out.println("8) Detalhes de uma reserva");
            System.out.println("9) Listar salões");
            System.out.println("0) Sair");
            int opc = lerInteiro("Opção: ");
            switch (opc) {
                case 1: cadastrarReserva(); break;
                case 2: confirmarReserva(); break;
                case 3: finalizarReserva(); break;
                case 4: trocarOrganizador(); break;
                case 5: listarConfirmadas(); break;
                case 6: listarFinalizadas(); break;
                case 7: buscarPorStatus(); break;
                case 8:
                    Reserva reserva = buscarReserva(lerInteiro("Código da reserva: "));
                    if (reserva != null) reserva.exibirDetalhes();
                    else System.out.println("Reserva não encontrada.");
                    break;
                case 9: listarSaloes(); break;
                case 0: System.out.println("Sistema encerrado."); return;
                default: System.out.println("Opção inválida.");
            }
        }
    }
}
