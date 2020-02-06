import java.util.Random;

public class Person implements Runnable
{
	String name;
	CreditCard card;

	public Person(String name, CreditCard card)
	{
		this.name = name;
		this.card = card;
	}

	@Override
	public void run()
	{
		while (this.card.getBalance() > 0)
		{
			Random random = new Random();
			double value = random.nextInt(5000);
			System.out.println(this.name + " is trying to pay " + value + "$");
			this.card.pay(value);
			System.out.println("Available money: " + card);
		}
	}
}
