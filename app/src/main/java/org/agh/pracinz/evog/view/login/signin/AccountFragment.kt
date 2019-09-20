package org.agh.pracinz.evog.view.login.signin

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_account.*
import kotlinx.android.synthetic.main.fragment_account.view.*
import kotlinx.android.synthetic.main.fragment_personal_info.*

import org.agh.pracinz.evog.R
import org.agh.pracinz.evog.di.manual.ViewModels
import org.agh.pracinz.evog.di.module.ViewModelFactory
import org.agh.pracinz.evog.viewmodel.login.SignInViewModel
import javax.inject.Inject

class AccountFragment : Fragment(), FragmentValidator {

    lateinit var viewModel: SignInViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_account, container, false)
        viewModel = ViewModels.singInViewModel
        view.firstPasswordInput.doAfterTextChanged {
            view.firstPasswordInput.validatePassword()
            it?.toString()?.let { pass -> viewModel.password = pass }
        }
        view.secondPasswordInput.doAfterTextChanged {
            view.secondPasswordInput.validatePassword()
        }
        view.loginInput.doAfterTextChanged {
            view.loginInput.validateLogin()
            it?.toString()?.let { login -> viewModel.login = login }

        }
        return view
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
        val errorText = viewModel.validatePassoword(this.text.toString()).joinToString("\n")
        error = errorText
    }


}
