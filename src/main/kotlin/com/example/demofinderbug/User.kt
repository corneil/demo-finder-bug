package com.example.demofinderbug

import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship
import org.neo4j.ogm.annotation.Required
import org.springframework.data.neo4j.repository.Neo4jRepository

@NodeEntity("User")
class User {
    @Id
    var userId: String? = null

    @Required
    var email: String? = null

    @Required
    var fullName: String? = null

    @Relationship("USER_HAS_ADDRESS")
    var address: Address? = null

    constructor()
    constructor(userId: String, email: String, fullName: String, address: Address) {
        this.userId = userId
        this.email = email
        this.fullName = fullName
        this.address = address
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (userId != other.userId) return false

        return true
    }

    override fun hashCode(): Int {
        return userId?.hashCode() ?: 0
    }

    override fun toString(): String {
        return "User(userId=$userId, email=$email, fullName=$fullName)"
    }

}

interface UserRepository : Neo4jRepository<User, String> {
    fun findByUserId(userId: String): User?
}