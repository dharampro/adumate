package com.adumate.config

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.config.server.EnableConfigServer

@EnableConfigServer
@SpringBootApplication
class AdumateConfigApplication

fun main(args: Array<String>) {
	runApplication<AdumateConfigApplication>(*args)
}
