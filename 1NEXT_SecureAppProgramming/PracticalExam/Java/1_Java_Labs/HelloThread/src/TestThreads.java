public class TestThreads
{
	public static void main(String[] args)
	{
		System.out.println("Program START");

		HelloThread t1 = new HelloThread(1);
		HelloThread t2 = new HelloThread(2);

		// sequential execution
		//t1.run();
		//t2.run();

		t1.start();
		t2.start();

		try
		{
			t1.join();
			t2.join();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}

		CreditCard card = new CreditCard(500);

		Thread wife = new Thread(new Person("Alice",card));
		Thread husband = new Thread(new Person("Bob",card));

		wife.start();
		husband.start();

		try
		{
			husband.join();
			wife.join();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}

		System.out.println("Program END");
	}
}
