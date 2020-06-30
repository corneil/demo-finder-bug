package com.example.demofinderbug

import org.neo4j.driver.AuthTokens
import org.neo4j.driver.Driver
import org.neo4j.driver.GraphDatabase
import org.neo4j.ogm.session.SessionFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager

@Configuration
class TestContainerConfig {
    @Bean
    fun transactionManager(sessionFactory: SessionFactory): Neo4jTransactionManager {
        return Neo4jTransactionManager(sessionFactory)
    }

    @Bean
    fun graphDriver(): Driver {
        return GraphDatabase.driver(Neo4JTestBase.neo4jContainer.getBoltUrl(), AuthTokens.basic("neo4j", Neo4JTestBase.PASSWORD))
    }

    @Bean
    fun sessionFactory(): SessionFactory {
        val configuration =
                org.neo4j.ogm.config.Configuration.Builder().uri(Neo4JTestBase.neo4jContainer.getBoltUrl())
                        .credentials("neo4j", Neo4JTestBase.PASSWORD).build()
        return SessionFactory(configuration, "com.example.demofinderbug")
    }
}