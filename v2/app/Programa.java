package app;

import dominio.*;
import servico.Central;
import java.util.List;
import java.util.Scanner;

public class Programa
{
	private static final Scanner reader = new Scanner(System.in);
	private static final Central registry = new Central();

	public static void inicializar()
	{
		registry.addVenue(new Espaco(1, 80, "Centro", "Festas"));
		registry.addVenue(new Espaco(2, 120, "Pampulha", "Casamentos"));
		registry.addVenue(new Espaco(3, 50, "Barreiro", "Reunioes"));
		registry.addManager(new Responsavel("Carlos", "11111111111", "31999990001", "Eventos"));
		registry.addManager(new Responsavel("Matheus", "22222222222", "31999990002", "Cerimonial"));
		registry.addManager(new Responsavel("Caio", "33333333333", "31999990003", "Producao"));
		for (int index = 0; index < 3; index++)
			registry.venues().get(index).appoint(registry.managers().get(index));
	}

	private static String ask(String question)
	{
		System.out.print(question);
		return reader.nextLine().trim();
	}

	private static int number(String question)
	{
		while (true)
		{
			try { return Integer.parseInt(ask(question)); }
			catch (NumberFormatException ignored) { System.out.println("Informe um numero valido."); }
		}
	}

	private static double money(String question)
	{
		while (true)
		{
			try { return Double.parseDouble(ask(question).replace(',', '.')); }
			catch (NumberFormatException ignored) { System.out.println("Valor invalido."); }
		}
	}

	private static void createBooking()
	{
		int code = number("Codigo: ");
		if (registry.bookingById(code) != null) { System.out.println("Codigo duplicado."); return; }
		String customer = ask("Cliente: ");
		String date = ask("Data (dd/mm/aaaa): ");
		String period = ask("Periodo (manha/tarde/noite): ").toLowerCase();
		if (period.equals("manhã")) period = "manha";
		int guests = number("Numero de convidados: ");
		String state = ask("Situacao (solicitada/confirmada/finalizada): ").toLowerCase();
		double amount = money("Valor total: ");

		if (!List.of("manha", "tarde", "noite").contains(period)
			|| !List.of("solicitada", "confirmada", "finalizada").contains(state))
		{
			System.out.println("Periodo ou situacao incorretos.");
			return;
		}
		boolean saved = registry.save(new Agendamento(code, customer, date, period, guests, state, amount));
		System.out.println(saved ? "Cadastro realizado." : "Dados invalidos.");
	}

	private static void appointManager()
	{
		List<Responsavel> staff = registry.managers();
		for (int index = 0; index < staff.size(); index++)
		{
			Responsavel person = staff.get(index);
			String place = person.venue() == null ? "sem salao" : "salao " + person.venue().number();
			System.out.printf("%d - %s (%s)%n", index + 1, person.name(), place);
		}
		int selected = number("Escolha o organizador: ") - 1;
		Espaco venue = registry.venueByNumber(number("Numero do salao: "));
		if (selected < 0 || selected >= staff.size() || venue == null)
		{
			System.out.println("Selecao inexistente.");
			return;
		}
		System.out.println(venue.appoint(staff.get(selected)) ? "Responsavel vinculado." : "Organizador ja vinculado a outro salao.");
	}

	private static void allocateBooking()
	{
		int bookingId = number("Codigo da reserva: ");
		int venueId = number("Numero do salao: ");
		boolean accepted = registry.allocate(bookingId, venueId);
		System.out.println(accepted ? "Reserva associada." : "Associacao recusada. Confira status, capacidade e disponibilidade.");
	}

	private static void printBooking(Agendamento booking)
	{
		System.out.printf("Codigo: %d | Cliente: %s | Data: %s | Periodo: %s%n", booking.id(), booking.customer(), booking.date(), booking.period());
		System.out.printf("Convidados: %d | Status: %s | Total: R$%.2f%n", booking.guests(), booking.status(), booking.amount());
		Espaco place = booking.venue();
		if (place == null)
		{
			System.out.println("Salao: nao atribuido | Organizador: nao atribuido");
			return;
		}
		Responsavel manager = place.manager();
		System.out.printf("Salao: %d | Capacidade: %d | Localizacao: %s | Tipo: %s%n", place.number(), place.limit(), place.address(), place.category());
		System.out.printf("Organizador: %s | CPF: %s | Telefone: %s | Area: %s%n", manager.name(), manager.document(), manager.phone(), manager.specialty());
	}

	private static void confirmedAtVenue()
	{
		Espaco selected = registry.venueByNumber(number("Numero do salao: "));
		if (selected == null) { System.out.println("Salao inexistente."); return; }
		int count = 0;
		for (Agendamento booking : selected.bookings())
		{
			if (!booking.status().equals("confirmada")) continue;
			printBooking(booking);
			count++;
		}
		System.out.println("Reservas confirmadas: " + count);
	}

	private static void completedCounts()
	{
		for (Espaco place : registry.venues())
			System.out.printf("Salao %d: %d reservas finalizadas%n", place.number(), registry.completedAt(place));
	}

	private static void searchStatus()
	{
		String requested = ask("Status desejado: ").toLowerCase();
		if (!List.of("solicitada", "confirmada", "finalizada").contains(requested))
		{
			System.out.println("Status invalido.");
			return;
		}
		List<Agendamento> matches = registry.withStatus(requested);
		for (Agendamento entry : matches) printBooking(entry);
		System.out.println("Quantidade encontrada: " + matches.size());
	}

	private static void details()
	{
		Agendamento selected = registry.bookingById(number("Codigo da reserva: "));
		if (selected == null) System.out.println("Reserva inexistente.");
		else printBooking(selected);
	}

	private static void showOptions()
	{
		System.out.println("\nGERENCIAMENTO DE EVENTOS");
		System.out.println("1 - Nova reserva");
		System.out.println("2 - Vincular organizador");
		System.out.println("3 - Associar reserva a salao");
		System.out.println("4 - Reservas confirmadas de um salao");
		System.out.println("5 - Total de reservas finalizadas por salao");
		System.out.println("6 - Consultar por status");
		System.out.println("7 - Consultar por codigo");
		System.out.println("0 - Encerrar");
	}

	public static void main(String[] args)
	{
		inicializar();
		boolean running = true;
		while (running)
		{
			showOptions();
			int choice = number("Opcao: ");
			switch (choice)
			{
				case 1 -> createBooking();
				case 2 -> appointManager();
				case 3 -> allocateBooking();
				case 4 -> confirmedAtVenue();
				case 5 -> completedCounts();
				case 6 -> searchStatus();
				case 7 -> details();
				case 0 -> running = false;
				default -> System.out.println("Opcao desconhecida.");
			}
		}
		System.out.println("Programa encerrado.");
	}
}
