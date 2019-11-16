package org.agh.pracinz.evog.view.login.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_account.*
import kotlinx.android.synthetic.main.fragment_account.view.*
import org.agh.pracinz.evog.R
import org.agh.pracinz.evog.di.manual.ViewModels
import org.agh.pracinz.evog.viewmodel.login.SignInViewModel

class AccountFragment : Fragment(), FragmentValidator {

    lateinit var viewModel: SignInViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModels.singInViewModel
        return inflater.inflate(R.layout.fragment_account, container, false).apply {

            firstPasswordInput.doAfterTextChanged {
                firstPasswordInput.validatePassword()
                it?.toString()?.let { pass -> viewModel.password = pass }
            }
            secondPasswordInput.doAfterTextChanged {
                secondPasswordInput.validatePassword()
            }
            loginInput.doAfterTextChanged {
                loginInput.validateLogin()
                it?.toString()?.let { login -> viewModel.login = login }
            }
        }
    }

    override fun isValid(): Boolean {
        return firstPasswordInput.text.toString() == secondPasswordInput.text.toString()

    }

    private fun EditText.validateLogin() {
        viewModel.validateLogin(this.text.toString())?.let {
            error = it
        }
    }

    private fun EditText.validatePassword() {
        val errorText = viewModel.validatePassoword(text.toString())
        error = errorText
    }


}
