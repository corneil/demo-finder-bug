package com.example.demofinderbug

import org.neo4j.ogm.annotation.EndNode
import org.neo4j.ogm.annotation.GeneratedValue
import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.RelationshipEntity
import org.neo4j.ogm.annotation.StartNode
import org.springframework.data.neo4j.repository.Neo4jRepository

@RelationshipEntity("GROUP_MEMBER")
class GroupMember {
    @StartNode
    var user: User? = null

    @EndNode
    var group: UserGroup? = null

    var enabled: Boolean? = false

    @Id
    @GeneratedValue
    var id: Long? = null

    constructor()
    constructor(user: User?, group: UserGroup?, enabled: Boolean) {
        this.user = user
        this.group = group
        this.enabled = enabled
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GroupMember

        if (user != other.user) return false
        if (group != other.group) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = user?.hashCode() ?: 0
        result = 31 * result + (group?.hashCode() ?: 0)
        result = 31 * result + (id?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "UserInGroup(user=$user, group=$group, enabled=$enabled, id=$id)"
    }
}

interface GroupMemberRepository : Neo4jRepository<GroupMember, Long> {
    fun countAllByGroup_Name(name: String): Long
    fun findAllByGroup_Name(name: String): Iterable<GroupMember>

    fun countAllByEnabledAndUser_UserId(enabled: Boolean, userId: String): Long
    fun findAllByEnabledAndUser_UserId(enabled: Boolean, userId: String): Iterable<GroupMember>

    fun countAllByUser_Address_Code(code: String): Long
    fun findAllByUser_Address_Code(code: String): Iterable<GroupMember>

}