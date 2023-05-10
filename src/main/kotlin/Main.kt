package com.pp.laborator
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import java.io.File
import kotlin.system.*

var ListOfNumbers  = mutableListOf<Int>()   //ADT-ul din care vom prelua datele care vor fi citite / sterse
val mutex = Mutex(false)


suspend fun CoroutineScope.WriteInFile(myfile: File)
{
    var time = measureTimeMillis {
        val jobs = List(100)
        {
            async {
                //delay(5000)
                repeat(1000)                   //se lanseaza 100 de corutine pe cele 3 threaduri
                {
                    if (!mutex.isLocked) {
                        //se pune lacatul
                        mutex.lock()
                        if (ListOfNumbers.isNotEmpty()) {
                            println(Thread.currentThread().toString() + ", Numar citit si sters din lista: " + ListOfNumbers[0].toString() + ", ")
                            ListOfNumbers.removeAt(0)
                        }
                        else {
                            //delay(1)
                        }
                        mutex.unlock();                                       //se scoate lacatul
                    }
                }
            }
        }
        jobs.forEach { it.join() }
    }
    println(" time = $time")
}



val mtContext = newFixedThreadPoolContext(3, "mtPool")
var counter = 0
fun main() = runBlocking<Unit> {
    val fileName = "myfile.txt"
    val myfile = File(fileName)
    for(i in 1..1000)
    {
        ListOfNumbers.add(i);                                        //initializare lista cu numere
    }
    println("se apeleaza corutinele din TP: ")
    CoroutineScope(mtContext).WriteInFile(myfile)                   //se lanseaza corutinele pe threadurile din ThreadPool
}