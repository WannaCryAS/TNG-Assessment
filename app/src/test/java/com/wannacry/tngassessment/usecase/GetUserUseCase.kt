package com.wannacry.tngassessment.usecase

import com.wannacry.tngassessment.domain.data.Address
import com.wannacry.tngassessment.domain.data.Company
import com.wannacry.tngassessment.domain.data.Geo
import com.wannacry.tngassessment.domain.data.User
import com.wannacry.tngassessment.domain.repo.UserRepository
import com.wannacry.tngassessment.domain.usecase.GetUserUseCase
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

class GetUserUseCaseTest {

    private lateinit var repo: UserRepository
    private lateinit var useCase: GetUserUseCase

    @Before
    fun setup() {
        repo = mock(UserRepository::class.java)
        useCase = GetUserUseCase(repo)
    }

    @Test
    fun `execute should return list of users`() = runTest {
        val users = listOf(
            User(
                id = 1,
                name = "Alice",
                username = "alice",
                email = "a@a.com",
                address = Address(
                    street = "street 1",
                    suite = "suite 1",
                    city = "city 1",
                    zipcode = "zipcode 1",
                    geo = Geo(
                        lat = "-1.23456",
                        lng = "5.432112"
                    ),
                ),

                phone = "0987654321",
                website = "a.net",
                company = Company(
                    name = "company 1",
                    catchPhrase = "abcd efg",
                    bs = "bs bs"
                )
            ),
            User(
                id = 2,
                name = "Ali",
                username = "ali",
                email = "b@b.com",
                address = Address(
                    street = "street 2",
                    suite = "suite 2",
                    city = "city 2",
                    zipcode = "zipcode 2",
                    geo = Geo(
                        lat = "-1.23098",
                        lng = "5.123112"
                    ),
                ),

                phone = "1234567890",
                website = "b.net",
                company = Company(
                    name = "company 2",
                    catchPhrase = "hrjk lmno",
                    bs = "sb sb"
                ),
            )
        )

        `when`(repo.getUsers()).thenReturn(users)

        val result = useCase.execute()

        assertEquals(2, result.size)
        assertEquals("Alice", result[0].name)
        verify(repo, times(1)).getUsers()
    }

    @Test
    fun `execute should return empty list`() = runTest {
        `when`(repo.getUsers()).thenReturn(emptyList())

        val result = useCase.execute()

        assertTrue(result.isEmpty())
        verify(repo, times(1)).getUsers()
    }
}