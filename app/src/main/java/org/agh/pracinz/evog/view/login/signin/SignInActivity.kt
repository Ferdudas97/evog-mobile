package org.agh.pracinz.evog.view.login.signin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_sign_in.*
import org.agh.pracinz.evog.R
import org.agh.pracinz.evog.di.manual.ViewModels
import org.agh.pracinz.evog.model.data.Account
import org.agh.pracinz.evog.view.login.ACCOUNT_CREATED_REQUEST_CODE
import org.agh.pracinz.evog.view.login.ACCOUNT_REQUEST_CODE
import org.agh.pracinz.evog.viewmodel.login.SignInViewModel

class SignInActivity : AppCompatActivity() {

    lateinit var viewModel: SignInViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        viewModel = ViewModels.singInViewModel


        signInViewPager.adapter = SignInViewPageAdapter(supportFragmentManager)
    }


    fun finish(account: Account) {
        val result = Intent()
        result.putExtra("LOGIN",account.credentials.login)
        result.putExtra("PASSWORD",account.credentials.password)
        setResult(ACCOUNT_CREATED_REQUEST_CODE, result)
        finishActivity(ACCOUNT_REQUEST_CODE)
    }
}
