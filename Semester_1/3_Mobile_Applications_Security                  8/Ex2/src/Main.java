import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.locks.ReentrantLock;

public class Main
{
	static int currentFloor;

	public static void main(String[] args)
	{
		Random random = new Random();
		Scanner scanner = new Scanner(System.in);

		ReentrantLock lock = new ReentrantLock();

		while(true)
		{
			currentFloor = random.nextInt(6);

			System.out.println("Press one button: 0-5");
			String floor = scanner.nextLine();
			if(!floor.equals("0") && !floor.equals("1") && !floor.equals("2") &&
					!floor.equals("3") && !floor.equals("4") && !floor.equals("5"))
			{
				System.out.println("Invalid input!");
			}
			else
			{
				int command = Integer.parseInt(floor);
				Elevator elevator = new Elevator();
				elevator.setSelectedFloor(command);
				elevator.setAreDoorsOpen(false);
				Runnable worker = () -> {
					lock.lock();
					if (currentFloor > command)
					{
						while(currentFloor > command)
						{
							currentFloor--;
							try
							{
								Thread.sleep(random.nextInt(3000));
							}
							catch (InterruptedException e)
							{
								e.printStackTrace();
							}
							System.out.println("The elevator is on its way. Current floor " + currentFloor);
						}
					}
					else if (currentFloor < command)
					{
						while (currentFloor < command)
						{
							currentFloor++;
							try
							{
								Thread.sleep(random.nextInt(3000));
							}
							catch (InterruptedException e)
							{
								e.printStackTrace();
							}
							System.out.println("The elevator is on its way. Current floor " + currentFloor);
						}
					}
					elevator.setAreDoorsOpen(true);
					System.out.println("The elevator has arrived. Please step in!");
					try
					{
						Thread.sleep(5000);
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
					elevator.setAreDoorsOpen(false);
					System.out.println("The doors are closing!");
					lock.unlock();
				};

				Thread t = new Thread(worker);
				t.start();
			}
		}
	}
}