package com.example.demofinderbug

import org.neo4j.ogm.annotation.GeneratedValue
import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.springframework.data.neo4j.repository.Neo4jRepository

@NodeEntity("Address")
class Address {
    @Id
    @GeneratedValue
    var id: Long? = null
    var line1: String? = null
    var line2: String? = null
    var code: String? = null

    constructor()
    constructor(line1: String, line2: String, code: String) {
        this.line1 = line1
        this.line2 = line2
        this.code = code
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Address

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

    override fun toString(): String {
        return "Address(id=$id, line1=$line1, line2=$line2, code=$code)"
    }

}

interface AddressRepository : Neo4jRepository<Address, Long>