class myClass {
    private var name: String = "Data field";
    fun printMe() {
        println("Myclass = " + this.name + " || this = " + this)
    }
}

class myClassPrimaryConstructor(nameStr: String? = null) {
    private var name: String? = nameStr

    init {
        println("init myClassPrimaryConstructor")
    }

    fun printMe() {
        println("Myclass = " + this.name + " || this = " + this)
    }
}

class myTime {
    val hours: Int;
    val min: Int;
    val sec: Int;

    constructor() {
        this.hours = 0
        this.min = 0
        this.sec = 0
    }

    constructor(h: Int, m: Int, s: Int) {
        this.hours = h
        this.min = m
        this.sec = s
    }

    constructor(obj: myTime) {
        this.hours = obj.hours
        this.min = obj.min
        this.sec = obj.sec
    }

    constructor(str: String) {
        val subs = str.split(":")
        this.hours = subs[0].toInt()
        this.min = subs[1].toInt()
        this.sec = subs[2].toInt()
    }

    fun serialize(): String {
        return "{%02d}{%02d}{%02d} || this = %s".format(this.hours, this.min, this.sec, this)
    }
}


fun main(args: Array<String>)
{
    val obj = myClass()
    obj.printMe()

    val o1 = myClassPrimaryConstructor()
    o1.printMe()

    val o2 = myClassPrimaryConstructor("...valoare tati...")
    o2.printMe()

    var t1 = myTime("11:57:26")
    println("t1 = " + t1.serialize())

    var t2 = myTime(13, 15, 52)
    println("t2 = " + t1.serialize())

    var t3: myTime?

    if (t2.hours == 14)
    {
        t3 = null
    }
    else
    {
        t3 = myTime(t2);
    }

    println("t3 = " + t3?.serialize())
}