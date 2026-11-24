import model.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static Scanner scan = new Scanner(System.in);
    static ArrayList<Salao> saloes = new ArrayList<>();
    static ArrayList<Organizador> organizadores = new ArrayList<>();
    static ArrayList<Reserva> reservas = new ArrayList<>();

    public static void inicializar(){
        saloes.add(new Salao(1, 80, "Centro", "Festas"));
        saloes.add(new Salao(2, 120, "Pampulha", "Casamentos"));
        saloes.add(new Salao(3, 50, "Barreiro", "Reunioes"));
        organizadores.add(new Organizador("Carlos", "11111111111", "31999990001", "Eventos"));
        organizadores.add(new Organizador("Matheus", "22222222222", "31999990002", "Cerimonial"));
        organizadores.add(new Organizador("Caio", "33333333333", "31999990003", "Producao"));
        for(int i=0; i<3; i++){saloes.get(i).setOrganizador(organizadores.get(i));}
    }
    public static Salao buscarSalao(int numero){
        for(Salao s : saloes){if(s.getNumero()==numero){return s;}}
        return null;
    }
    public static Reserva buscarReserva(int codigo){
        for(Reserva r : reservas){if(r.getCodigo()==codigo){return r;}}
        return null;
    }
    public static void menu(){
        System.out.println("\n---------- MENU ----------");
        System.out.println("1) Cadastrar reserva");
        System.out.println("2) Associar organizador a salao");
        System.out.println("3) Atribuir reserva a salao");
        System.out.println("4) Listar reservas confirmadas");
        System.out.println("5) Quantidade de reservas finalizadas");
        System.out.println("6) Buscar reservas por status");
        System.out.println("7) Consultar reserva");
        System.out.println("0) Sair");
        System.out.print("Escolha a opc: ");
    }
    public static int lerInt(String msg){
        while(true){
            System.out.print(msg);
            try{return Integer.parseInt(scan.nextLine().trim());}
            catch(NumberFormatException e){System.out.println("Digite um numero valido");}
        }
    }
    public static double lerValor(){
        while(true){
            System.out.print("Valor total: ");
            try{return Double.parseDouble(scan.nextLine().trim().replace(',','.'));}
            catch(NumberFormatException e){System.out.println("Valor invalido");}
        }
    }
    public static void cadastrar(){
        int cod = lerInt("Codigo: ");
        if(buscarReserva(cod)!=null){System.out.println("Codigo ja cadastrado!");return;}
        System.out.print("Nome do cliente: ");
        String nome = scan.nextLine();
        System.out.print("Data (dd/mm/aaaa): ");
        String data = scan.nextLine().trim();
        System.out.print("Horario (manha/tarde/noite): ");
        String horario = scan.nextLine().trim().toLowerCase();
        if(!horario.equals("manha") && !horario.equals("manhã") && !horario.equals("tarde") && !horario.equals("noite")){System.out.println("Horario invalido");return;}
        if(horario.equals("manhã")){horario="manha";}
        int convidados = lerInt("Quantidade de convidados: ");
        System.out.print("Status (solicitada/confirmada/finalizada): ");
        String status = scan.nextLine().trim().toLowerCase();
        if(!status.equals("solicitada") && !status.equals("confirmada") && !status.equals("finalizada")){System.out.println("Status invalido");return;}
        double valor = lerValor();
        if(cod<=0 || nome.isBlank() || data.isBlank() || convidados<=0 || valor<0){System.out.println("Dados invalidos");return;}
        reservas.add(new Reserva(cod,nome,data,horario,convidados,status,valor));
        System.out.println("Reserva cadastrada! Associe um salao se necessario.");
    }
    public static void associarOrganizador(){
        System.out.println("Organizadores:");
        for(int i=0; i<organizadores.size(); i++){
            Organizador o = organizadores.get(i);
            System.out.printf("%d) %s - %s%n",i+1,o.getNome(),o.getSalao()==null?"sem salao":"salao "+o.getSalao().getNumero());
        }
        int pos = lerInt("Numero do organizador: ")-1;
        Salao s = buscarSalao(lerInt("Numero do salao: "));
        if(pos<0 || pos>=organizadores.size() || s==null){System.out.println("Dados nao encontrados");return;}
        if(s.setOrganizador(organizadores.get(pos))){System.out.println("Organizador associado!");}
        else{System.out.println("Organizador ja esta em outro salao");}
    }
    public static void atribuir(){
        Reserva r = buscarReserva(lerInt("Codigo da reserva: "));
        Salao s = buscarSalao(lerInt("Numero do salao: "));
        if(r==null || s==null){System.out.println("Reserva ou salao nao encontrado");return;}
        if(s.addReserva(r)){System.out.println("Reserva atribuida!");}
        else{System.out.println("Nao foi possivel atribuir. Verifique status, capacidade, organizador e disponibilidade.");}
    }
    public static void confirmadas(){
        Salao s = buscarSalao(lerInt("Numero do salao: "));
        if(s==null){System.out.println("Salao nao encontrado");return;}
        int total=0;
        for(Reserva r : s.getReservas()){
            if(r.getStatus().equals("confirmada")){r.exibir();total++;}
        }
        System.out.println("Total de confirmadas: "+total);
    }
    public static void finalizadas(){
        for(Salao s : saloes){
            int total=0;
            for(Reserva r : reservas){
                if(r.getStatus().equals("finalizada") && r.getSalao()==s){total++;}
            }
            System.out.println("Salao "+s.getNumero()+" - finalizadas: "+total);
        }
    }
    public static void porStatus(){
        System.out.print("Status: ");
        String status = scan.nextLine().trim().toLowerCase();
        if(!status.equals("solicitada") && !status.equals("confirmada") && !status.equals("finalizada")){System.out.println("Status invalido");return;}
        int total=0;
        for(Reserva r : reservas){
            if(r.getStatus().equals(status)){r.exibir();total++;}
        }
        System.out.println("Total: "+total);
    }
    public static void consultar(){
        Reserva r = buscarReserva(lerInt("Codigo: "));
        if(r==null){System.out.println("Reserva nao encontrada");return;}
        r.exibir();
    }
    public static void main(String[] args){
        inicializar();
        while(true){
            menu();
            int opc = lerInt("");
            switch(opc){
                case 1: cadastrar();break;
                case 2: associarOrganizador();break;
                case 3: atribuir();break;
                case 4: confirmadas();break;
                case 5: finalizadas();break;
                case 6: porStatus();break;
                case 7: consultar();break;
                case 0: System.out.println("Saindo...");return;
                default: System.out.println("Opcao invalida");
            }
        }
    }
}
