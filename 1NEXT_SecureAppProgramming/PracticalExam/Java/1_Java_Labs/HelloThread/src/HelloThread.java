public class HelloThread extends Thread
{
	private int id;

	public HelloThread(int id)
	{
		this.id = id;
	}

	@Override
	public void run()
	{
		try
		{
			sleep(2000);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		for(int i=0;i<3;i++)
		{
			System.out.println("Hello thread " + id);
		}
	}
}
