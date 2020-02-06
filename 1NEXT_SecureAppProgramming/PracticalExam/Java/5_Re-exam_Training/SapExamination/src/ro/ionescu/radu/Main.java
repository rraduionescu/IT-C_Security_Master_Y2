package ro.ionescu.radu;
class Base
{
	public void Test(){
		System.out.println("test 1");
	}
}
class Subclass extends Base
{
	public void Test(){
		System.out.println("test 2");
	}
}
public class Main
{
	public static void main(String[] args)
	{
		Base b = new Base();
		b.Test();
		Subclass d = new Subclass();
		d.Test();
		d=(Subclass)b;
		d.Test();
	}
}
