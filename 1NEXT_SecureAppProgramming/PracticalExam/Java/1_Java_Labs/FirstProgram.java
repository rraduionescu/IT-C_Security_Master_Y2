public class FirstProgram
{
	public static void main(String[] args)
	{
		float number = 1.45F;
		if(args!=null && args.length>0 && args[0]!=null && args[0].length()>0)
		{
			System.out.println("Hello " + args[0] + "! This is my first java training program. Float number: " + number);
		}
		else
		{
			System.out.println("Hello anonymous user! This is my first java training program. Float number: " + number);	
		}
	}
}