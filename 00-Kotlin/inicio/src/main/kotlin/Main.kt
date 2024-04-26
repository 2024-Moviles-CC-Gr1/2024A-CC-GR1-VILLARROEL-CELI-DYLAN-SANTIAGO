import java.util.*

fun main(){
    println("Hola mundo")
    // INMUTABLES (No se RE ASIGNA "=")
    val inmutable: String = "Dylan";
    // inmutable = "Vicente" // Error!
    //MUTABLES
    var mutable: String = "Messi"
    mutable = "Adrian" // OK
    //VAL > VAR
    //Duck Tuping
    val ejemploVariable = " Adrian Eguez "
    val edadEjemplo: Int = 12
    ejemploVariable.trim()
    //ejemploVariable = edadEjemplo // ERROR!
    // Variables Primitivas
    val nombreProfesor: String = "Adrian Eguez"
    val sueldo: Double = 1.2
    val estadoCivil: Char = 'C'
    val MayorEdad: Boolean = true
    //Clases en Java
    val fechaNacimiento: Date = Date()

    //When (Switch)
    val estadoCivilWhen = "C"
    when (estadoCivilWhen){
        ("C") ->{
            println("Casado")
        }
        "S" -> {
            println("Soltero")
        }
        else ->{
            println("No Sabemos")
        }
    }

    val esSoltero = (estadoCivilWhen == "S")
    val coqueteo = if (esSoltero) "Si" else "No" // if else chiquito

    calcularSueldo(10.00)
    calcularSueldo(10.00,15.00,20.00)
    //Named parameters
    //calcularSueldo(sueldo, bonoEspecial, tasa)
    calcularSueldo(10.00, bonoEspecial = 20.00)
    calcularSueldo(bonoEspecial = 20.00, sueldo = 10.00, tasa = 14.00)

    val sumaUno = Suma(1,1) //new Suma(1,1 en Kotlin no hay "new"
    val sumaDos = Suma(null,1)
    val sumaTres = Suma(1,null)
    val sumaCuatro = Suma(null,null)
    sumaUno.sumar()
    sumaDos.sumar()
    sumaTres.sumar()
    sumaCuatro.sumar()
    println(Suma.pi)
    println(Suma.elevarAlCuadrado(2))
    println(Suma.historialSumas)

    //Arreglos
    //Estaticos
    val arregloEstatico: Array<Int> = arrayOf<Int>(1,2,3)
    println(arregloEstatico)
    //Dinamicos
    val arregloDinamico: ArrayList<Int> = arrayListOf<Int>(
        1,2,3,4,5,6,7,8,9,10
    )

    println(arregloDinamico)
    arregloDinamico.add(11)
    arregloDinamico.add(12)
    println(arregloDinamico)

    //FOR EACH = > Unit
    val respuestaForEach: Unit = arregloDinamico
        .forEach { valorActual: Int ->
            println("Valor Actual: ${valorActual}")
        }


    // "it" (en ingles "eso") significa el elemento iterado
    arregloDinamico.forEach{ println("Valor Actual (it): ${it}")}

    //MAP -> MURA(Modifica cambia) el arreglo
    //1) Enviamos el nuevo valor de la iteracion
    //2) Nos devuelve un nuevo ARREGLO con valores
    // de las iteraciones
    val respuestaMap : List<Double> = arregloDinamico
        .map { valorActual: Int ->
            return@map valorActual.toDouble() + 100.00
        }
    println(respuestaMap)
    val respuestaMapDos = arregloDinamico.map { it + 15 }
    println(respuestaMapDos)

    //Filter -> Filtrat el ARREGLO
    // 1) Devolver una expresion (True or false)
    // 2) Nuevo arreglo FILTRADO
    val respuestaFilter: List<Int> = arregloDinamico
        .filter{valorActual:Int ->
            //Expresion o Condicion
            val mayoresACinco: Boolean = valorActual > 5
            return@filter mayoresACinco
        }

    val respuestaFilterDos = arregloDinamico.filter { it <= 5 }
    println(respuestaFilter)
    println(respuestaFilterDos)

    // OR AND
    // OR -> ANY (Alguno cumple?)
    // And -> ALL (Todos cumplen?)
    val respuestaAny = arregloDinamico.any{ it > 5}
    println(respuestaAny)
    val respuestaAll = arregloDinamico.all { it > 5 }
    println(respuestaAll)


    //Reduce -> Valor acumulado
    //Valor acumulado = 0 (Se empieza en 0)
    val respuestaReduc:Int = arregloDinamico
        .reduce{ acumulado:Int, valorActual:Int ->
            return@reduce (acumulado + valorActual)
        }
    println(respuestaReduc)

    val respuestaReduce = arregloDinamico.reduce { acc, it -> it + acc }

    println(respuestaReduce)
}
//void -> Unit
fun imprimirNombre(nombre:String): Unit{
    println("Nombre: ${nombre}")
}
fun calcularSueldo(
    sueldo:Double, //requerido
    tasa: Double= 12.00, //opcional pero por defecto
    bonoEspecial: Double? = null, //Opcional (nullable)
    // Variable? -> "?" Es Nullable
):Double {
    //Int -> Int?
    //String -> String?
    //Data -> Date?
    if(bonoEspecial == null){
        return sueldo * (100/tasa)
    }else{
        return sueldo * (100/tasa) * bonoEspecial
    }
}

abstract class NumerosJava{
    protected val numeroUno:Int
    private val numeroDos:Int
    constructor(
        uno:Int,
        dos:Int
    ){
        this.numeroUno = uno
        this.numeroDos = dos
        println("Inicializando")
    }


}

abstract class Numeros(

    protected val numeroUno: Int,
    protected val numeroDos: Int,
){
    init{
        println("Inicializando")
    }

}

class Suma( //Constructor Primario
    unoParametro: Int,
    dosParametro: Int,
): Numeros( // Clase padre, Numeros (extendiendo)
    unoParametro,
    dosParametro
){
    public var soyPublicoExplicito:String = "Explicito" //publica
    val soyPublicoImplicito:String = "Implicito" //publica
    init { //BLoque codigo constructor primario
        this.numeroUno
        this.numeroDos
        numeroUno
        numeroDos
        this.soyPublicoExplicito
        soyPublicoImplicito
    }

    constructor(
        uno:Int?,
        dos:Int
    ):this(
        if(uno==null) 0 else uno,
        dos
    )
    constructor(
        uno:Int,
        dos:Int?
    ):this(
        uno,
        if(dos==null) 0 else dos,
    )

    constructor(
        uno:Int?,
        dos:Int?
    ):this(
        if(uno==null) 0 else uno,
        if(dos==null) 0 else dos,
    )

    public fun sumar():Int{
        val total = numeroUno + numeroDos
        agregarHistorial(total)
        return total
    }
    companion object{

        val pi = 3.14
        fun elevarAlCuadrado(num:Int):Int{
            return num * num
        }
        val historialSumas = arrayListOf<Int>()
        fun agregarHistorial(valorTotalSuma:Int){
            historialSumas.add(valorTotalSuma)
        }
    }
}