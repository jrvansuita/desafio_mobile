package com.example.demobycoders

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.example.demobycoders.ui.login.LoginAction
import com.example.demobycoders.ui.login.LoginViewModel
import com.example.demobycoders.usecase.LoginUseCase
import com.example.tooldata.datasource.local.store.LocalStore
import com.example.tooldata.model.User
import com.example.toolui.extensions.isCompleted
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

	private lateinit var viewModel: LoginViewModel
	private val localStore: LocalStore<User> = mockk()
	private val loginUserUseCase: LoginUseCase = mockk()

	private val testDispatcher: TestDispatcher = StandardTestDispatcher()

	@Before
	fun starting() {
		clearMocks(localStore)
		clearMocks(loginUserUseCase)

		Dispatchers.setMain(testDispatcher)

		viewModel = LoginViewModel(
			savedStateHandle = SavedStateHandle(),
			localStore = localStore,
			loginUseCase = loginUserUseCase
		)
	}

	@After
	fun ending() = Dispatchers.resetMain()

	@Test
	fun `WHEN email changes THEN state should change`() = runTest {
		"invalid".let {
			viewModel.onEmailChange(it)
			viewModel.stateFlow.test {
				with(awaitItem()) {
					assertEquals(it, email.value)
					assertFalse(email.isCompleted())
					assertFalse(submit.enabled)
				}
			}
		}

		"valid@email.com".let {
			viewModel.onEmailChange(it)
			viewModel.stateFlow.test {
				with(awaitItem()) {
					assertEquals(it, email.value)
					assertTrue(email.isCompleted())
					assertFalse(submit.enabled)
				}
			}
		}
	}

	@Test
	fun `WHEN password changes THEN state should change`() = runTest {
		"xxx".let {
			viewModel.onPasswordChange(it)
			viewModel.stateFlow.test {
				with(awaitItem()) {
					assertEquals(it, password.value)
					assertFalse(password.isCompleted())
					assertFalse(submit.enabled)
				}
			}
		}

		"12345678".let {
			viewModel.onEmailChange("valid@email.com")
			viewModel.onPasswordChange(it)
			viewModel.stateFlow.test {
				with(awaitItem()) {
					assertEquals("valid@email.com", email.value)
					assertEquals(it, password.value)
					assertTrue(email.isCompleted())
					assertTrue(password.isCompleted())
					assertTrue(submit.enabled)
				}
			}
		}
	}

	@Test
	fun `WHEN onSubmit is called with invalid data THEN user can't be logged in`() =
		runTest {
			viewModel.onEmailChange("xxx")
			viewModel.onPasswordChange("xxx")
			viewModel.onSubmit()

			viewModel.stateFlow.test {
				val item = awaitItem()
				assertFalse(item.submit.enabled)
				assertFalse(item.submit.loading)
			}

			coVerify(exactly = 0) { loginUserUseCase(any(), any()) }
		}

	@Test
	fun `WHEN onSubmit is called with valid data THEN user can't be logged in`() = runTest {
		viewModel.onEmailChange("email@email.com")
		viewModel.onPasswordChange("12345678")
		viewModel.onSubmit()

		coEvery { loginUserUseCase(any(), any()) } returns Result.success(User())

		viewModel.stateFlow.test {
			val item = awaitItem()
			assertTrue(item.submit.enabled)

			viewModel.action.test {
				assertTrue(awaitItem() is LoginAction.Success)
			}

			assertFalse(item.submit.loading)
		}

		coVerify(exactly = 1) { loginUserUseCase(any(), any()) }
	}
}