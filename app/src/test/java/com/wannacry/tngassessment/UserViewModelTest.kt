package com.wannacry.tngassessment

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.wannacry.tngassessment.domain.data.Address
import com.wannacry.tngassessment.domain.data.Company
import com.wannacry.tngassessment.domain.data.Geo
import com.wannacry.tngassessment.domain.data.User
import com.wannacry.tngassessment.domain.usecase.GetUserUseCase
import com.wannacry.tngassessment.presentation.UiState
import com.wannacry.tngassessment.presentation.viewmodel.UsersViewModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UsersViewModelTest {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    private val dispatcher = StandardTestDispatcher()

    private lateinit var useCase: GetUserUseCase
    private lateinit var viewModel: UsersViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        useCase = mockk()
        viewModel = UsersViewModel(useCase)
    }

    @Test
    fun `getUsers success should update state and usersUi`() = runTest {
        val mockUsers = listOf(
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
        coEvery { useCase.execute() } returns mockUsers

        viewModel.getUsers()
        advanceTimeBy(2000) // skip delay
        advanceUntilIdle()

        assertTrue(viewModel.state.value is UiState.Success)
        assertEquals(2, viewModel.usersUi.value!!.size)
    }

    @Test
    fun `getUsers empty list should return Error`() = runTest {
        coEvery { useCase.execute() } returns emptyList()

        viewModel.getUsers()
        advanceTimeBy(2000)
        advanceUntilIdle()

        val state = viewModel.state.value
        assertTrue(state is UiState.Error)
        assertEquals("No user found", (state as UiState.Error).errorMessage)
    }

    @Test
    fun `getUsers throws exception should return Error`() = runTest {
        coEvery { useCase.execute() } throws RuntimeException("Network error")

        viewModel.getUsers()
        advanceTimeBy(2000)
        advanceUntilIdle()

        val state = viewModel.state.value
        assertTrue(state is UiState.Error)
        assertEquals("Network error", (state as UiState.Error).errorMessage)
    }

    @Test
    fun `sortByName should sort list ascending and descending`() = runTest {
        var users = listOf<User>()
        viewModel.sortByName(true)
        var asc = (viewModel.state.value as UiState.Success).users
        assertEquals(users, asc.map { it.name })

        viewModel.sortByName(false)
        var desc = (viewModel.state.value as UiState.Success).users
        assertEquals(users, desc.map { it.name })

        users = listOf(
            User(
                id = 1,
                name = "Charlie",
                username = "char",
                email = "c@c.com",
                address = Address(
                    street = "street 1",
                    suite = "suite 1",
                    city = "city 1",
                    zipcode = "zipcode 1",
                    geo = Geo(
                        lat = "-1.23098",
                        lng = "5.123112"
                    ),
                ),

                phone = "1234567890",
                website = "b.net",
                company = Company(
                    name = "company 1",
                    catchPhrase = "hrjk lmno",
                    bs = "sb sb"
                ),
            ),
            User(
                id = 2,
                name = "Ali",
                username = "al",
                email = "a@a.com",
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

                phone = "0987654321",
                website = "a.net",
                company = Company(
                    name = "company 2",
                    catchPhrase = "hrjk lmno",
                    bs = "sb sb"
                ),
            ),
            User(
                id = 3,
                name = "Abu",
                username = "ash",
                email = "b@b.com",
                address = Address(
                    street = "street 3",
                    suite = "suite 3",
                    city = "city 3",
                    zipcode = "zipcode 3",
                    geo = Geo(
                        lat = "-1.23098",
                        lng = "5.123112"
                    ),
                ),

                phone = "6789054321",
                website = "a.net",
                company = Company(
                    name = "company 3",
                    catchPhrase = "hrjk lmno",
                    bs = "sb sb"
                ),
            ),
        )
        coEvery { useCase.execute() } returns users

        viewModel.getUsers()
        advanceTimeBy(2000)
        advanceUntilIdle()

        viewModel.sortByName(true)
        asc = (viewModel.state.value as UiState.Success).users
        assertEquals(listOf("Abu", "Ali", "Charlie"), asc.map { it.name })

        viewModel.sortByName(false)
        desc = (viewModel.state.value as UiState.Success).users
        assertEquals(listOf("Charlie", "Ali", "Abu"), desc.map { it.name })
    }
}
