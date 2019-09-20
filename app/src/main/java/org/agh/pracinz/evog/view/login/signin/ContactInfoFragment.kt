package org.agh.pracinz.evog.view.login.signin

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProviders
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_contact_info.view.*

import org.agh.pracinz.evog.R
import org.agh.pracinz.evog.di.manual.ViewModels
import org.agh.pracinz.evog.di.module.ViewModelFactory
import org.agh.pracinz.evog.model.data.Account
import org.agh.pracinz.evog.viewmodel.login.SignInViewModel
import javax.inject.Inject

class ContactInfoFragment : Fragment() {

    lateinit var viewModel: SignInViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_contact_info, container, false)
        viewModel = ViewModels.singInViewModel

        view.phoneNumberInput.doAfterTextChanged {
            it?.toString()?.let { number -> viewModel.phoneNumber = number }
        }
        view.emailInput.doAfterTextChanged {
            it?.toString()?.let { input -> viewModel.email = input }
        }

        view.createAcccountButton.setOnClickListener {
            viewModel.create().subscribe { account -> signInActvity().finish(account) }
        }

        return view
    }


    fun signInActvity() = (getActivity() as SignInActivity)


}
