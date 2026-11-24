package dominio;

import java.util.ArrayList;
import java.util.List;

public class Espaco
{
	private final int number;
	private final int limit;
	private final String address;
	private final String category;
	private Responsavel manager;
	private final List<Agendamento> activeBookings = new ArrayList<>();

	public Espaco(int number, int limit, String address, String category)
	{
		this.number = number;
		this.limit = limit;
		this.address = address;
		this.category = category;
	}

	public int number() { return number; }
	public int limit() { return limit; }
	public String address() { return address; }
	public String category() { return category; }
	public Responsavel manager() { return manager; }
	public List<Agendamento> bookings() { return activeBookings; }

	public boolean appoint(Responsavel person)
	{
		if (person.venue() != null && person.venue() != this) return false;
		if (manager != null && manager != person) manager.assign(null);
		manager = person;
		person.assign(this);
		return true;
	}

	public boolean canHost(Agendamento booking)
	{
		if (manager == null || booking.guests() > limit) return false;
		for (int index = 0; index < activeBookings.size(); index++)
		{
			Agendamento existing = activeBookings.get(index);
			if (existing.date().equals(booking.date()) && existing.period().equals(booking.period()))
				return false;
		}
		return true;
	}

	public void register(Agendamento booking)
	{
		if (booking.status().equals("confirmada")) activeBookings.add(booking);
	}
}
