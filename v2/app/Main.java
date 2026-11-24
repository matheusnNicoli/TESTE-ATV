package app;

import java.util.Scanner;
import dominio.*;
import servico.Central;

public class Main {
    static Scanner sc = new Scanner(System.in);
    static Central central = new Central();

    public static void inicializarDados() {
        Organizador a = new Organizador("Ana", "11111111111", "31999990001", "Decoração");
        Organizador b = new Organizador("Bruno", "22222222222", "31999990002", "Buffet");
        Organizador c = new Organizador("Carla", "33333333333", "31999990003", "Cerimonial");
        central.cadastrarOrganizador(a);
        central.cadastrarOrganizador(b);
        central.cadastrarOrganizador(c);
        central.cadastrarSalao(new Salao(1, 100, "Centro", "Festas", a));
        central.cadastrarSalao(new Salao(2, 60, "Pampulha", "Reuniões", b));
        central.cadastrarSalao(new Salao(3, 200, "Barreiro", "Eventos", c));
    }
    public static String lerTexto(String mensagem) {
        while(true) {
            System.out.print(mensagem);
            String texto = sc.nextLine().trim();
            if(!texto.isEmpty()) return texto;
            System.out.println("O campo não pode ficar vazio.");
        }
    }
    public static int lerInteiro(String mensagem) {
        while(true) {
            try { return Integer.parseInt(lerTexto(mensagem)); }
            catch(NumberFormatException e) { System.out.println("Digite um número inteiro válido."); }
        }
    }
    public static double lerValor(String mensagem) {
        while(true) {
            try { return Double.parseDouble(lerTexto(mensagem).replace(',','.')); }
            catch(NumberFormatException e) { System.out.println("Digite um valor válido."); }
        }
    }
    public static String lerHorario() {
        while(true) {
            String horario = lerTexto("Horário (manhã/tarde/noite): ").toLowerCase();
            if(horario.equals("manha") || horario.equals("manhã")) return "manhã";
            if(horario.equals("tarde") || horario.equals("noite")) return horario;
            System.out.println("Horário inválido.");
        }
    }
    public static void mostrarMenu() {
        System.out.println("\n--- Sistema de Eventos ---");
        System.out.println("1. Cadastrar reserva");
        System.out.println("2. Associar organizador a salão");
        System.out.println("3. Atribuir reserva a salão (confirmar)");
        System.out.println("4. Exibir confirmadas de um salão");
        System.out.println("5. Contar finalizadas por salão");
        System.out.println("6. Buscar por status");
        System.out.println("7. Detalhes de uma reserva");
        System.out.println("8. Finalizar reserva");
        System.out.println("0. Encerrar");
    }
    public static void main(String[] args) {
        inicializarDados();
        int opcao;
        do {
            mostrarMenu();
            opcao = lerInteiro("Opção: ");
            switch(opcao) {
                case 1:
                    int codigo = lerInteiro("Código: ");
                    if(codigo <= 0 || central.buscarReserva(codigo) != null) { System.out.println("Código inválido ou já cadastrado."); break; }
                    String nome = lerTexto("Nome do cliente: ");
                    String data = lerTexto("Data (dd/mm/aaaa): ");
                    String horario = lerHorario();
                    int quantidade = lerInteiro("Quantidade de convidados: ");
                    double valor = lerValor("Valor total: ");
                    if(quantidade <= 0 || valor < 0) { System.out.println("Quantidade deve ser positiva e valor não pode ser negativo."); break; }
                    central.adicionarReserva(new Reserva(codigo,nome,data,horario,quantidade,valor));
                    System.out.println("Reserva solicitada cadastrada!");
                    break;
                case 2:
                    Salao s = central.buscarSalao(lerInteiro("Número do salão: "));
                    if(s == null) { System.out.println("Salão não encontrado."); break; }
                    for(int i=0;i<central.getOrganizadores().size();i++) System.out.printf("%d) %s%n",i+1,central.getOrganizadores().get(i).getNome());
                    int indice = lerInteiro("Organizador: ") - 1;
                    if(indice < 0 || indice >= central.getOrganizadores().size()) { System.out.println("Organizador inválido."); break; }
                    central.associarOrganizador(s, central.getOrganizadores().get(indice));
                    System.out.println("Organizador associado. Responsáveis trocados quando necessário.");
                    break;
                case 3:
                    Reserva r = central.buscarReserva(lerInteiro("Código da reserva: "));
                    Salao escolhido = central.buscarSalao(lerInteiro("Número do salão: "));
                    if(central.confirmar(r, escolhido)) System.out.println("Reserva confirmada e atribuída!");
                    else System.out.println("Não foi possível confirmar: confira status, capacidade e disponibilidade.");
                    break;
                case 4:
                    Salao consulta = central.buscarSalao(lerInteiro("Número do salão: "));
                    if(consulta == null) System.out.println("Salão não encontrado.");
                    else central.mostrarConfirmadas(consulta);
                    break;
                case 5:
                    central.mostrarFinalizadas();
                    break;
                case 6:
                    String status = lerTexto("Status (solicitada/confirmada/finalizada): ").toLowerCase();
                    if(status.equals("solicitada") || status.equals("confirmada") || status.equals("finalizada")) central.buscarPorStatus(status);
                    else System.out.println("Status inválido.");
                    break;
                case 7:
                    Reserva enc = central.buscarReserva(lerInteiro("Código da reserva: "));
                    if(enc != null) enc.exibirDados();
                    else System.out.println("Reserva não encontrada.");
                    break;
                case 8:
                    if(central.finalizar(central.buscarReserva(lerInteiro("Código da reserva: ")))) System.out.println("Reserva finalizada. Histórico do salão preservado na reserva.");
                    else System.out.println("Somente reservas confirmadas podem ser finalizadas.");
                    break;
                case 0: System.out.println("Sistema finalizado."); break;
                default: System.out.println("Opção inválida.");
            }
        } while(opcao != 0);
    }
}
