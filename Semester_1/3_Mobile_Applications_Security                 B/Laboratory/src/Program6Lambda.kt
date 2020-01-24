fun main(args: Array<String>)
{
    val MyL:(String) -> Unit
    MyL = {s:String-> println(s)}

    val vString: String = "Tutorial"

    MyL(vString)
    println(MyFunction("Kotlin"))

    MyFun("Kotlin", MyL)
}

fun MyFun(a: String, action:(String)->Unit)
{
    println("HI!")
    action(a)
}

fun MyFunction(x:String): String
{
    var r: String = "Welcome 2"
    return (r + x)
}