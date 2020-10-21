package `in`.abaddon.devtest.signupexample

import `in`.abaddon.devtest.signupexample.model.User
import `in`.abaddon.devtest.signupexample.model.UserDao
import `in`.abaddon.devtest.signupexample.signup.*
import android.database.sqlite.SQLiteConstraintException
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class SignupVMTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock lateinit var userDao: UserDao
    @Mock lateinit var observer: Observer<ViewState>

    private val testDispatcher = TestCoroutineDispatcher()
    lateinit var vm: SignupViewModel

    @Before
    fun setup(){
        vm = SignupViewModel(testDispatcher, SimpleValidator(), userDao)
        vm.viewState.observeForever(observer)
    }

    @Test
    fun `show no error when Name is valid`() = runBlockingTest {
        vm.dispatch(NameEntered("Bob"))

        val captor = ArgumentCaptor.forClass(ViewState::class.java)
        verify(observer, atLeastOnce()).onChanged(captor.capture())
        assertEquals("Bob", captor.value.name)
        assertEquals(null, captor.value.nameError)
    }

    @Test
    fun `show no error when Email is valid`() = runBlockingTest {
        vm.dispatch(EmailEntered("Bob@Muster.ch"))

        val captor = ArgumentCaptor.forClass(ViewState::class.java)
        verify(observer, atLeastOnce()).onChanged(captor.capture())
        assertEquals("Bob@Muster.ch", captor.value.email)
        assertEquals(null, captor.value.emailError)
    }

    @Test
    fun `show no error when Birthday is valid`() = runBlockingTest {
        vm.dispatch(BirthdayEntered("09.09.1909"))

        val captor = ArgumentCaptor.forClass(ViewState::class.java)
        verify(observer, atLeastOnce()).onChanged(captor.capture())
        assertEquals("09.09.1909", captor.value.birthday)
        assertEquals(null, captor.value.birthdayError)
    }

    @Test
    fun `show error when Name is empty`() = runBlockingTest {
        vm.dispatch(NameEntered("   "))

        val captor = ArgumentCaptor.forClass(ViewState::class.java)
        verify(observer, atLeastOnce()).onChanged(captor.capture())
        assertEquals(R.string.msg_is_required, captor.value.nameError)
    }

    @Test
    fun `show error when Email is invalid`() = runBlockingTest {
        vm.dispatch(EmailEntered("Bob Musterch"))

        val captor = ArgumentCaptor.forClass(ViewState::class.java)
        verify(observer, atLeastOnce()).onChanged(captor.capture())
        assertEquals(R.string.msg_email_invalid, captor.value.emailError)
    }

    @Test
    fun `show error when Birthday is 1889`() = runBlockingTest {
        vm.dispatch(BirthdayEntered("09.09.1889"))

        val captor = ArgumentCaptor.forClass(ViewState::class.java)
        verify(observer, atLeastOnce()).onChanged(captor.capture())
        assertEquals(R.string.msg_date_invalid, captor.value.birthdayError)
    }

    @Test
    fun `show error when Birthday is 2020`() = runBlockingTest {
        vm.dispatch(BirthdayEntered("09.09.2020"))

        val captor = ArgumentCaptor.forClass(ViewState::class.java)
        verify(observer, atLeastOnce()).onChanged(captor.capture())
        assertEquals(R.string.msg_date_invalid, captor.value.birthdayError)
    }

    @Test
    fun `show error when Birthday is invalid`() = runBlockingTest {
        // Note that 2003 is not a leap year
        vm.dispatch(BirthdayEntered("29.02.2003"))

        val captor = ArgumentCaptor.forClass(ViewState::class.java)
        verify(observer, atLeastOnce()).onChanged(captor.capture())
        assertEquals(R.string.msg_date_invalid, captor.value.birthdayError)
    }

    @Test
    fun `it is submitable when all entered values are valid`() = runBlockingTest {
        vm.dispatch(NameEntered("Felix Muster"))
        vm.dispatch(EmailEntered("felix@muster.ch"))

        assertFalse(vm.viewState.value!!.isSubmitable())

        vm.dispatch(BirthdayEntered("15.5.1950"))

        assert(vm.viewState.value!!.isSubmitable())
    }

    @Test
    fun `return new id when User creation is successful`() = runBlockingTest {
        `when`(userDao.insert( User(0,"Felix Muster", "felix@muster.ch", "15.5.1950")))
            .thenReturn(45)

        vm.dispatch(NameEntered("Felix Muster"))
        vm.dispatch(EmailEntered("felix@muster.ch"))
        vm.dispatch(BirthdayEntered("15.5.1950"))
        vm.dispatch(SubmitPressed)

        val captor = ArgumentCaptor.forClass(ViewState::class.java)
        verify(observer, atLeastOnce()).onChanged(captor.capture())

        assertEquals(OpenConfirmation(45), captor.value.effect)
    }

    @Test
    fun `isLoading flag is set to on,off during data insertion`() = runBlockingTest {
        `when`(userDao.insert( User(0,"Felix Muster", "felix@muster.ch", "15.5.1950")))
            .thenReturn(45)

        vm.dispatch(NameEntered("Felix Muster"))
        vm.dispatch(EmailEntered("felix@muster.ch"))
        vm.dispatch(BirthdayEntered("15.5.1950"))
        vm.dispatch(SubmitPressed)

        val captor = ArgumentCaptor.forClass(ViewState::class.java)
        verify(observer, atLeastOnce()).onChanged(captor.capture())
        val size = captor.allValues.size

        assertFalse(captor.allValues[size-3].isLoading)
        assert(captor.allValues[size-2].isLoading)
        assertFalse(captor.allValues[size-1].isLoading)
    }

    @Test
    fun `show error when a duplicated email is submitted`() = runBlockingTest {
        `when`(userDao.insert( User(0,"Felix Muster", "duplicate@muster.ch", "15.5.1950")))
            .thenThrow(SQLiteConstraintException())

        vm.dispatch(NameEntered("Felix Muster"))
        vm.dispatch(EmailEntered("duplicate@muster.ch"))
        vm.dispatch(BirthdayEntered("15.5.1950"))
        vm.dispatch(SubmitPressed)

        val captor = ArgumentCaptor.forClass(ViewState::class.java)
        verify(observer, atLeastOnce()).onChanged(captor.capture())

        assertEquals(ShowToast(R.string.msg_submission_failed), captor.value.effect)
    }

    @Test
    fun `reset effect`() = runBlockingTest {
        assertEquals(null, vm.viewState.value!!.effect)

        vm.dispatch(DataInserted(46))
        assertNotNull(vm.viewState.value!!.effect)

        vm.dispatch(EffectFired)
        assertEquals(null, vm.viewState.value!!.effect)
    }
}
