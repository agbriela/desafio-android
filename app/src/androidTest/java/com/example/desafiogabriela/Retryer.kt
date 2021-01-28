package com.example.desafiogabriela

fun retryer(times: Int = 3, action: () -> Unit) {
    Thread.sleep(300)
    for (i in 0 until times) {
        try {
            action.invoke()
            return
        } catch (ex: Throwable) {
            if (i == times) {
                throw ex
            }
            Thread.sleep(300)
        }
    }
}