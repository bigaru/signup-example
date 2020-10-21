package `in`.abaddon.devtest.signupexample

import `in`.abaddon.devtest.signupexample.confirmation.ConfirmationViewModel
import `in`.abaddon.devtest.signupexample.confirmation.LoadUser
import `in`.abaddon.devtest.signupexample.confirmation.ViewState
import `in`.abaddon.devtest.signupexample.model.User
import `in`.abaddon.devtest.signupexample.model.UserDao
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class ConfirmationVMTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock lateinit var userDao: UserDao
    @Mock lateinit var observer: Observer<ViewState>

    private val testDispatcher = TestCoroutineDispatcher()
    lateinit var vm: ConfirmationViewModel

    val user42 = User(42, "Felix Muster", "felix@muster.ch", "06.01.1950")

    @Before
    fun setup(){
        vm = ConfirmationViewModel(testDispatcher, userDao)
        `when`(userDao.findById(42)).thenReturn(user42)
    }

    @Test
    fun `Given existing user, VM loads user`() = runBlockingTest {
        vm.viewState.observeForever(observer)
        vm.dispatch(LoadUser(42))

        val captor = ArgumentCaptor.forClass(ViewState::class.java)
        verify(observer, times(3)).onChanged(captor.capture())
        assertEquals(user42, captor.value.user)

        vm.viewState.removeObserver(observer)
    }

    @Test
    fun `Given existing user, VM displays successful hero text and color`()  = runBlockingTest {
        vm.viewState.observeForever(observer)
        vm.dispatch(LoadUser(42))

        val captor = ArgumentCaptor.forClass(ViewState::class.java)
        verify(observer, times(3)).onChanged(captor.capture())

        val actual = captor.value
        assertEquals(R.color.colorAccent, actual.heroColor)
        assertEquals(R.string.hero_text_successful, actual.heroText)

        vm.viewState.removeObserver(observer)
    }

    @Test
    fun `VM sets the flag to on,off during loading`() = runBlockingTest {
        vm.viewState.observeForever(observer)
        vm.dispatch(LoadUser(42))

        val captor = ArgumentCaptor.forClass(ViewState::class.java)
        verify(observer, times(3)).onChanged(captor.capture())

        assertFalse(captor.allValues[0].isLoading)
        assert(captor.allValues[1].isLoading)
        assertFalse(captor.allValues[2].isLoading)

        vm.viewState.removeObserver(observer)
    }

    @Test
    fun `Given non-existing user, VM returns null`() = runBlockingTest {
        vm.viewState.observeForever(observer)
        vm.dispatch(LoadUser(-1))

        val captor = ArgumentCaptor.forClass(ViewState::class.java)
        verify(observer, times(3)).onChanged(captor.capture())
        assertEquals(null, captor.value.user)

        vm.viewState.removeObserver(observer)
    }

    @Test
    fun `Given non-existing user, VM displays failure hero text and color`() = runBlockingTest {
        vm.viewState.observeForever(observer)
        vm.dispatch(LoadUser(-1))

        val captor = ArgumentCaptor.forClass(ViewState::class.java)
        verify(observer, times(3)).onChanged(captor.capture())

        val actual = captor.value
        assertEquals(R.color.failure, actual.heroColor)
        assertEquals(R.string.hero_text_failure, actual.heroText)

        vm.viewState.removeObserver(observer)
    }
}
