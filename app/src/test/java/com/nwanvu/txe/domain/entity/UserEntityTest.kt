package com.nwanvu.txe.domain.entity

import junit.framework.TestCase.assertEquals
import org.junit.Test

/**
 * Unit tests for the implementation of [UserEntity]
 */
class UserEntityTest {
    @Test
    fun mapToData() {
        val id = 1
        val name = "Vu Nguyen"
        val username = "nwanvu93"
        val avatar = "https://example.com/avatar.jpg"
        val profileUrl = "https://github.com/nwanvu93"
        val location = "HCM"
        val bio = "Software Engineer"
        val blog = "https://nwanvu.com"
        val followers = 100
        val following = 50

        // Create UserEntity instance with sample data
        val userEntity = UserEntity(
            id, name, username, avatar, profileUrl, location, bio, blog, followers, following
        )

        // Convert to data model
        val data = userEntity.toData()

        // Verify the data
        assertEquals(id, data.id)
        assertEquals(name, data.name)
        assertEquals(username, data.username)
        assertEquals(avatar, data.avatar)
        assertEquals(profileUrl, data.profileUrl)
        assertEquals(location, data.location)
        assertEquals(bio, data.bio)
        assertEquals(blog, data.blog)
        assertEquals(followers, data.followers)
        assertEquals(following, data.following)
    }
}