package servico;

import dominio.*;
import java.util.ArrayList;
import java.util.List;

public class Central
{
	private final List<Espaco> venues = new ArrayList<>();
	private final List<Responsavel> managers = new ArrayList<>();
	private final List<Agendamento> bookings = new ArrayList<>();

	public void addVenue(Espaco venue) { venues.add(venue); }
	public void addManager(Responsavel person) { managers.add(person); }
	public List<Espaco> venues() { return venues; }
	public List<Responsavel> managers() { return managers; }
	public List<Agendamento> bookings() { return bookings; }

	public Espaco venueByNumber(int number)
	{
		for (Espaco venue : venues)
			if (venue.number() == number) return venue;
		return null;
	}

	public Agendamento bookingById(int code)
	{
		for (Agendamento booking : bookings)
			if (booking.id() == code) return booking;
		return null;
	}

	public boolean save(Agendamento booking)
	{
		if (booking.id() <= 0 || booking.customer().isBlank() || booking.date().isBlank()
			|| booking.guests() <= 0 || booking.amount() < 0 || bookingById(booking.id()) != null)
			return false;
		bookings.add(booking);
		return true;
	}

	public boolean allocate(int code, int venueNumber)
	{
		Agendamento booking = bookingById(code);
		Espaco venue = venueByNumber(venueNumber);
		if (booking == null || venue == null || booking.venue() != null) return false;
		if (booking.status().equals("solicitada") || !venue.canHost(booking)) return false;
		booking.placeAt(venue);
		venue.register(booking);
		return true;
	}

	public int completedAt(Espaco venue)
	{
		int count = 0;
		for (Agendamento item : bookings)
			if (item.venue() == venue && item.status().equals("finalizada")) count++;
		return count;
	}

	public List<Agendamento> withStatus(String state)
	{
		List<Agendamento> result = new ArrayList<>();
		for (Agendamento booking : bookings)
			if (booking.status().equals(state)) result.add(booking);
		return result;
	}
}
