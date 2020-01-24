interface InterfaceExample
{
    var myVar: Int //abstract property
    fun abstractMethod(): String
    fun hello()
    {
        println("Default Hello interface method!")
    }
}

class InterfaceImplementation: InterfaceExample
{
    override var myVar: Int = 25
    override fun abstractMethod(): String
    {
        return "Learn Kotlin!"
    }
}

class GenericsExample<T>(input: T)
{
    init
    {
        println("Generic value = "+ input)
    }
}

fun main(args: Array<String>)
{
    var obj = InterfaceImplementation()
    //var obj: InterfaceExample
    //obj = InterfaceImplementation

    println("obj.myVar = ${obj.myVar}")
    obj.hello()
    println("obj.abstractMethod = " + obj.abstractMethod())

    var obj1 = GenericsExample<String>("Java/Kotlin")
    var obj2 = GenericsExample<Int>(10)
}