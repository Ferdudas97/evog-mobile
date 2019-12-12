package org.agh.pracinz.evog.view.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_login.*
import org.agh.pracinz.evog.R
import org.agh.pracinz.evog.di.manual.Db
import org.agh.pracinz.evog.di.manual.ViewModels
import org.agh.pracinz.evog.model.data.Account
import org.agh.pracinz.evog.model.data.UserCredentials
import org.agh.pracinz.evog.model.db.CredentialsDao
import org.agh.pracinz.evog.view.common.RxActivity
import org.agh.pracinz.evog.view.createToast
import org.agh.pracinz.evog.view.doAsync
import org.agh.pracinz.evog.view.login.signin.SignInActivity
import org.agh.pracinz.evog.view.main.MainActivity
import org.agh.pracinz.evog.viewmodel.login.LogInViewModel

class LoginActivity : RxActivity() {

    private lateinit var logInViewModel: LogInViewModel

    private lateinit var dao: CredentialsDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        logInViewModel = ViewModels.loginViewModel
        signInButton.setOnClickListener(this::onSignInClick)
        logInButton.setOnClickListener(this::onLogInClick)
        dao = Db.get(applicationContext).credentialsDao()
        autoLogin()
    }

    fun autoLogin() {
        doAsync {
            dao.get()?.let {
                login(it)
            }
        }
    }


    fun onSignInClick(view: View) {
        val intent = Intent(this, SignInActivity::class.java)

        startActivityForResult(intent, ACCOUNT_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == ACCOUNT_REQUEST_CODE && resultCode == ACCOUNT_CREATED_REQUEST_CODE) {
            data?.let {
                val credentials =
                    UserCredentials(it.extras["LOGIN"] as String, it.extras["PASSWORD"] as String)
                login(credentials)
            }
        }
    }

    private fun login(credentials: UserCredentials) {
        logInViewModel.logIn(credentials)
            .doOnSuccess {
                doAsync {
                    dao.add(credentials)
                }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(onError = this::onError, onSuccess = this::goToEventList).apply {
                disposables.add(this)
            }
    }

    fun onLogInClick(view: View) {
        val login = loginInput.text.toString()
        val password = passwordInput.text.toString()
        val credentials = UserCredentials(login, password)
        login(credentials)
    }

    private fun onError(throwable: Throwable) = this.createToast(throwable.message ?: "")
    private inline fun goToEventList(account: Account) =
        startActivity(Intent(this, MainActivity::class.java))
}
