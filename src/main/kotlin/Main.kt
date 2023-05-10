import kotlinx.coroutines.*
import kotlin.system.*
import kotlinx.coroutines.channels.*

var ADT = mutableListOf<Int>()

suspend fun stage1Pipeline(lista: MutableList<Int>) {
    val alfa = 2
    for(i in 0..10) {
        lista[i] = lista[i] * alfa
    }
}

suspend fun stage2Pipeline(lista: MutableList<Int>) {
    lista.sort()
}

suspend fun stage3Pipeline(lista: MutableList<Int>) {
    lista.forEach { print(it.toString() +", ")}
}

fun main() = runBlocking {

    for(i in 0..10)
    {
        ADT.add(10 - i)
    }
    var time = measureTimeMillis {
        val PIPE1 = async {
            stage1Pipeline(ADT)
            delay(100)
        }

        val PIPE2 = async {
            PIPE1.await()
            stage2Pipeline(ADT)
            delay(1000)
        }

        val PIPE3 = async {
            PIPE2.await()
            stage3Pipeline(ADT)
            delay(500)
        }
        PIPE3.await()
    }
    println("Time: +$time")
}