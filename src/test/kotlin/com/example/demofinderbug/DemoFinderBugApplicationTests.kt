package com.example.demofinderbug

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class DemoFinderBugApplicationTests : Neo4JTestBase() {

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var groupRepository: UserGroupRepository

    @Autowired
    lateinit var memberRepository: GroupMemberRepository

    @Autowired
    lateinit var addressRepository: AddressRepository

    @Test
    fun testUsers() {
        groupRepository.save(UserGroup("group1", "Group1"))
        groupRepository.save(UserGroup("group2", "Group2"))
        groupRepository.save(UserGroup("group3", "Group3"))

        userRepository.save(User("corneil",
                "corneil.duplessis@gmail.com",
                "Corneil du Plessis",
                addressRepository.save(Address("l1", "l2", "0001"))))
        userRepository.save(User("johndoe",
                "johndoe@email.com",
                "John Doe",
                addressRepository.save(Address("ll1", "xy2", "0001"))))

        val corneil = userRepository.findByUserId("corneil") ?: error("Expected User:corneil")
        val john = userRepository.findByUserId("johndoe") ?: error("Expected User:johndoe")

        val group1 = groupRepository.findByName("group1") ?: error("Expected UserGroup:group1")
        val group2 = groupRepository.findByName("group2") ?: error("Expected UserGroup:group2")
        val group3 = groupRepository.findByName("group3") ?: error("Expected UserGroup:group3")

        memberRepository.save(GroupMember(corneil, group1, true))
        memberRepository.save(GroupMember(corneil, group2, false))
        memberRepository.save(GroupMember(corneil, group3, true))

        memberRepository.save(GroupMember(john, group1, true))
        memberRepository.save(GroupMember(john, group2, true))
        memberRepository.save(GroupMember(john, group3, false))

        val group1Members = memberRepository.findAllByGroup_Name("group1").toList()
        assertThat(group1Members.size).isEqualTo(2)

        val corneilEnabledGroups = memberRepository.findAllByEnabledAndUser_UserId(true, "corneil").toList()
        assertThat(corneilEnabledGroups.size).isEqualTo(2)

        val corneilDisabledGroups = memberRepository.findAllByEnabledAndUser_UserId(false, "corneil").toList()
        assertThat(corneilDisabledGroups.size).isEqualTo(1)

        val livesAtCode = memberRepository.findAllByUser_Address_Code("0001").toList()
        assertThat(livesAtCode.size).isEqualTo(2)
    }
}
