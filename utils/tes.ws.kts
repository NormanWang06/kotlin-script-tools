import kotlinx.coroutines.*

GlobalScope.launch {
    delay(1000L)
    println("World!")
}