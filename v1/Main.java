import java.util.ArrayList;
import java.util.Scanner;
import model.*;

public class Main {
    static Scanner scan = new Scanner(System.in);
    static ArrayList<Salao> saloes = new ArrayList<>();
    static ArrayList<Organizador> organizadores = new ArrayList<>();
    static ArrayList<Reserva> reservas = new ArrayList<>();

    public static void inicializarDados(){
        organizadores.add(new Organizador("Ana", "11111111111", "31999990001", "Decoração"));
        organizadores.add(new Organizador("Bruno", "22222222222", "31999990002", "Buffet"));
        organizadores.add(new Organizador("Carla", "33333333333", "31999990003", "Cerimonial"));
        saloes.add(new Salao(1, 100, "Centro", "Festas", organizadores.get(0)));
        saloes.add(new Salao(2, 60, "Pampulha", "Reuniões", organizadores.get(1)));
        saloes.add(new Salao(3, 200, "Barreiro", "Eventos", organizadores.get(2)));
    }
    public static int lerNumero(String mensagem){
        while(true){
            System.out.print(mensagem);
            try { return Integer.parseInt(scan.nextLine().trim()); }
            catch(NumberFormatException e){ System.out.println("Digite um número inteiro válido."); }
        }
    }
    public static double lerValor(String mensagem){
        while(true){
            System.out.print(mensagem);
            try { return Double.parseDouble(scan.nextLine().trim().replace(',','.')); }
            catch(NumberFormatException e){ System.out.println("Digite um valor válido."); }
        }
    }
    public static String lerTexto(String mensagem){
        while(true){
            System.out.print(mensagem);
            String texto=scan.nextLine().trim();
            if(!texto.isEmpty()) return texto;
            System.out.println("O campo não pode ficar vazio.");
        }
    }
    public static String lerHorario(){
        while(true){
            String horario=lerTexto("Horário (manhã/tarde/noite): ").toLowerCase();
            if(horario.equals("manhã") || horario.equals("manha")) return "manhã";
            if(horario.equals("tarde") || horario.equals("noite")) return horario;
            System.out.println("Escolha manhã, tarde ou noite.");
        }
    }
    public static Salao buscarSalao(int numero){
        for(Salao i : saloes){ if(i.getNumero()==numero) return i; }
        return null;
    }
    public static Reserva buscarReserva(int codigo){
        for(Reserva i : reservas){ if(i.getCodigo()==codigo) return i; }
        return null;
    }
    public static void exibirMenu(){
        System.out.println("\n===========================================");
        System.out.println("       GERENCIAMENTO DE EVENTOS");
        System.out.println("-------------------------------------------");
        System.out.println("1) Cadastrar reserva");
        System.out.println("2) Associar organizador a salão");
        System.out.println("3) Atribuir reserva a salão (confirmar)");
        System.out.println("4) Reservas confirmadas por salão");
        System.out.println("5) Quantidade de finalizadas por salão");
        System.out.println("6) Buscar reservas por status");
        System.out.println("7) Detalhes de uma reserva");
        System.out.println("8) Finalizar reserva");
        System.out.println("0) Sair");
        System.out.println("===========================================");
    }
    public static void main(String[] args){
        inicializarDados();
        boolean menu=true;
        while(menu){
            exibirMenu();
            int opc=lerNumero("Escolha uma opção: ");
            switch(opc){
                case 1:
                    int cod=lerNumero("Código: ");
                    if(cod<=0 || buscarReserva(cod)!=null){ System.out.println("Código inválido ou já cadastrado."); break; }
                    String nome=lerTexto("Nome do cliente: ");
                    String data=lerTexto("Data (dd/mm/aaaa): ");
                    String horario=lerHorario();
                    int qnt=lerNumero("Quantidade de convidados: ");
                    double valor=lerValor("Valor total: ");
                    if(qnt<=0 || valor<0){System.out.println("Quantidade deve ser positiva e valor não pode ser negativo.");break;}
                    reservas.add(new Reserva(cod,nome,data,horario,qnt,valor));
                    System.out.println("Reserva solicitada cadastrada!");
                    break;
                case 2:
                    Salao salao=buscarSalao(lerNumero("Número do salão: "));
                    if(salao==null){System.out.println("Salão não encontrado.");break;}
                    for(int i=0;i<organizadores.size();i++) System.out.printf("%d) %s%n",i+1,organizadores.get(i).getNome());
                    int indice=lerNumero("Organizador: ")-1;
                    if(indice<0 || indice>=organizadores.size()){System.out.println("Organizador inválido.");break;}
                    Organizador escolhido=organizadores.get(indice);
                    Salao outro=null;
                    for(Salao i : saloes){if(i!=salao && i.getOrganizador()==escolhido) outro=i;}
                    if(outro!=null) outro.setOrganizador(salao.getOrganizador());
                    salao.setOrganizador(escolhido);
                    System.out.println("Organizador associado. Responsáveis trocados quando necessário.");
                    break;
                case 3:
                    Reserva reserva=buscarReserva(lerNumero("Código da reserva: "));
                    if(reserva==null || !reserva.getStatus().equals("solicitada")){System.out.println("Reserva inexistente ou não está solicitada.");break;}
                    Salao escolhidoSalao=buscarSalao(lerNumero("Número do salão: "));
                    if(escolhidoSalao==null){System.out.println("Salão não encontrado.");break;}
                    if(reserva.getQuantidadeConvidados()>escolhidoSalao.getCapacidade()){System.out.println("Capacidade do salão insuficiente.");break;}
                    if(!escolhidoSalao.horarioDisponivel(reserva)){System.out.println("Salão já reservado nesta data e horário.");break;}
                    reserva.confirmar(escolhidoSalao);
                    escolhidoSalao.getReservas().add(reserva);
                    System.out.println("Reserva confirmada e atribuída!");
                    break;
                case 4:
                    Salao consulta=buscarSalao(lerNumero("Número do salão: "));
                    if(consulta==null){System.out.println("Salão não encontrado.");break;}
                    int total=0;
                    for(Reserva i : consulta.getReservas()){
                        if(i.getStatus().equals("confirmada")){ i.exibirDetalhes(); total++; }
                    }
                    System.out.printf("Total de reservas confirmadas: %d%n",total);
                    break;
                case 5:
                    for(Salao i : saloes){
                        int quantidade=0;
                        for(Reserva r : reservas){if(r.getStatus().equals("finalizada") && r.getSalao()==i) quantidade++;}
                        System.out.printf("Salão %d: %d finalizadas%n",i.getNumero(),quantidade);
                    }
                    break;
                case 6:
                    String status=lerTexto("Status (solicitada/confirmada/finalizada): ").toLowerCase();
                    if(!status.equals("solicitada") && !status.equals("confirmada") && !status.equals("finalizada")){System.out.println("Status inválido.");break;}
                    int encontrados=0;
                    for(Reserva i : reservas){if(i.getStatus().equals(status)){i.exibirDetalhes();encontrados++;}}
                    System.out.printf("Total encontrado: %d%n",encontrados);
                    break;
                case 7:
                    Reserva detalhe=buscarReserva(lerNumero("Código da reserva: "));
                    if(detalhe==null) System.out.println("Reserva não encontrada.");
                    else detalhe.exibirDetalhes();
                    break;
                case 8:
                    Reserva finalizar=buscarReserva(lerNumero("Código da reserva: "));
                    if(finalizar==null || !finalizar.getStatus().equals("confirmada")){System.out.println("Somente reservas confirmadas podem ser finalizadas.");break;}
                    finalizar.getSalao().getReservas().remove(finalizar);
                    finalizar.finalizar();
                    System.out.println("Reserva finalizada. Histórico do salão preservado na reserva.");
                    break;
                case 0: menu=false; System.out.println("Sistema finalizado."); break;
                default: System.out.println("Opção inválida.");
            }
        }
    }
}
