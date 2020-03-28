package se09.cert.service

import io.micronaut.runtime.Micronaut

object Application {

    @JvmStatic
    fun main(args: Array<String>) {
        Micronaut.build()
                .packages("se09.cert.service")
                .mainClass(Application.javaClass)
                .start()
    }
}