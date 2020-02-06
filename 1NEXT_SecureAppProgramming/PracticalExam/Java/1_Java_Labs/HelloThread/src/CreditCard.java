public class CreditCard
{
	private double balance;

	public CreditCard(double balance)
	{
		this.balance = balance;
	}

	boolean checkAvailableMoney(double value)
	{

		if (this.balance >= value)
		{
			System.out.println("We have the money!");
			return true;
		} else
		{
			return false;
		}
	}

	synchronized void pay(double value)
	{
		if(checkAvailableMoney(value))
		{
			this.balance -= value;
		}
	}

	public double getBalance()
	{
		return balance;
	}

	@Override
	public String toString()
	{
		return "Current balance: " + this.balance;
	}
}
