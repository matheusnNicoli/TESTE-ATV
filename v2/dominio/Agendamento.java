package dominio;

public class Agendamento
{
	private final int id;
	private final String customer;
	private final String date;
	private final String period;
	private final int guests;
	private final String state;
	private final double amount;
	private Espaco venue;

	public Agendamento(int id, String customer, String date, String period, int guests, String state, double amount)
	{
		this.id = id;
		this.customer = customer;
		this.date = date;
		this.period = period;
		this.guests = guests;
		this.state = state;
		this.amount = amount;
	}

	public int id() { return id; }
	public String customer() { return customer; }
	public String date() { return date; }
	public String period() { return period; }
	public int guests() { return guests; }
	public String status() { return state; }
	public double amount() { return amount; }
	public Espaco venue() { return venue; }
	public void placeAt(Espaco chosen) { venue = chosen; }
}
