import kotlinx.coroutines.*
import kotlin.system.*
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

var ADT = mutableListOf<Int>()
var rezz = mutableListOf<Int>()
val mutex = Mutex(false)

suspend fun Suma(n: Int):Int
{
    return n*(n-1)/2
}

fun main() = runBlocking {
    for(i in 1..4)
        ADT.add(i)

    var jobs = mutableListOf<Job>()

    var time = measureTimeMillis {
        for (i in 1..4) {
            jobs.add(
                async{
                    mutex.withLock {
                        rezz.add(Suma(ADT[0]))
                        ADT.removeAt(0)
                        }
                    })
        }
    }
    jobs.forEach(){it.join()}
    rezz.forEach(){println("valoare rezultat: "+it.toString())}
    println("Timp scurs: $time")
}