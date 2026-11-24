package dominio;

public class Responsavel
{
	private final String name;
	private final String document;
	private final String phone;
	private final String specialty;
	private Espaco assignedVenue;

	public Responsavel(String name, String document, String phone, String specialty)
	{
		this.name = name;
		this.document = document;
		this.phone = phone;
		this.specialty = specialty;
	}

	public String name() { return name; }
	public String document() { return document; }
	public String phone() { return phone; }
	public String specialty() { return specialty; }
	public Espaco venue() { return assignedVenue; }
	public void assign(Espaco venue) { assignedVenue = venue; }
}
