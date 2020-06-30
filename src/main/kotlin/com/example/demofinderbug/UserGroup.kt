package com.example.demofinderbug

import org.neo4j.ogm.annotation.GeneratedValue
import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Required
import org.springframework.data.neo4j.repository.Neo4jRepository

@NodeEntity("UserGroup")
class UserGroup {
    @Id
    @GeneratedValue
    var id: Long? = null

    @Required
    var name: String? = null

    @Required
    var description: String? = null

    constructor()
    constructor(name: String, description: String) {
        this.name = name
        this.description = description
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserGroup

        if (id != other.id) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (name?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "UserGroup(id=$id, name=$name, description=$description)"
    }

}

interface UserGroupRepository : Neo4jRepository<UserGroup, Long> {
    fun findByName(name: String): UserGroup?
}