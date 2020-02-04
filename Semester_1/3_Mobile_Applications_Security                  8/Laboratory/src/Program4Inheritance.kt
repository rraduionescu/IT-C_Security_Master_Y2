open class Vehicle
{
    private var weight: Float
    var weightProperty: Float
        get() = this.weight
        set(value) {this.weight = value}

    constructor(weight: Float = 0.0f)
    {
        this.weight = weight
    }

    open fun printMe(): String
    {
        var r: String = "Vehicle::weight = " + this.weight
        return r
    }
}

class Auto : Vehicle
{
    private var doorsNo: Int
    var doorsNoProperty: Int
        get() = doorsNo
        set(value) {this.doorsNo = value}

    constructor(weight: Float = 0.0f, doorsNo: Int = 0): super(weight)
    {
     this.doorsNo = doorsNo
    }

    override fun printMe(): String
    {
        var r: String = "Auto::weight = " + this.weightProperty + " Auto::doorsNo = " + this.doorsNo
        return r
    }
}

class Plane : Vehicle
{
    private var capacity: Float
    var capacityProperty: Float
        get() = capacity
    set(value) {this.capacity = value}

    private var enginesNo: Int

    constructor(weight: Float = 0.0f, capacity: Float = 0.0f, enginesNo: Int = 0):super(weight)
    {
        this.capacity = capacity
        this.enginesNo = enginesNo
    }

    override fun printMe(): String
    {
        var r: String = "Plane::weight = " + this.weightProperty + " Plane::capacity = " + this.capacity + " Plane::enginesNo = " + this.enginesNo
        return r
    }
}

fun main(args : Array<String>)
{
    var vob1: Vehicle = Vehicle(100.2f)
    var vob2: Vehicle = Vehicle(55.4f)

    println("vobj1 = " + vob1.printMe())
    println("vobj2 = " + vob2.printMe())

    vob2 = vob1
    vob2.weightProperty = 300.0f

    println("vobj1 = " + vob1.printMe())
    println("vobj2 = " + vob2.printMe())

    var v: Vehicle
    var a: Auto = Auto(1200.0f, 3)
    var p: Plane = Plane(10750.2f, 120.0f, 2)

    v = a
    println("v = " + v.printMe())

    v = p
    println("v = " + v.printMe())

    v = a
    //p = v as Plane
}
