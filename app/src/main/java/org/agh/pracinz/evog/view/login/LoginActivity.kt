package org.agh.pracinz.evog.view.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.agh.pracinz.evog.R
import org.agh.pracinz.evog.di.manual.ViewModels
import org.agh.pracinz.evog.model.data.UserCredentials
import org.agh.pracinz.evog.view.events.list.EventListActivity
import org.agh.pracinz.evog.view.login.signin.SignInActivity
import org.agh.pracinz.evog.viewmodel.login.LogInViewModel

class LoginActivity : AppCompatActivity() {

    lateinit var logInViewModel: LogInViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        logInViewModel = ViewModels.loginViewModel
        signInButton.setOnClickListener(this::onSignInClick)
        logInButton.setOnClickListener(this::onLogInClick)
    }

    fun onSignInClick(view: View) {
        val intent = Intent(this, SignInActivity::class.java)

        startActivityForResult(intent, ACCOUNT_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == ACCOUNT_REQUEST_CODE && resultCode == ACCOUNT_CREATED_REQUEST_CODE) {
            data?.let {
                val credentials = UserCredentials(it.extras["LOGIN"] as String, it.extras["PASSWORD"] as String)
                logInViewModel.logIn(credentials.login, credentials.password)
            }
        }
    }

    fun onLogInClick(view: View) {
        val login = loginInput.text.toString()
        val password = passwordInput.text.toString()
//        logInViewModel.logIn(login, password)
        goToEventList()
    }

    private inline fun goToEventList() = startActivity(Intent(this, EventListActivity::class.java))
}
