package org.agh.pracinz.evog.view.login.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_contact_info.view.*
import org.agh.pracinz.evog.R
import org.agh.pracinz.evog.di.manual.ViewModels
import org.agh.pracinz.evog.view.onTextChange
import org.agh.pracinz.evog.viewmodel.login.SignInViewModel

class ContactInfoFragment : Fragment() {

    lateinit var viewModel: SignInViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        viewModel = ViewModels.singInViewModel
        return inflater.inflate(R.layout.fragment_contact_info, container, false).apply {


            phoneNumberInput.onTextChange {
                viewModel.phoneNumber = it
            }
            emailInput.onTextChange {
                viewModel.email = it
            }

            createAcccountButton.setOnClickListener {
                viewModel.create().subscribe { account -> signInActvity().finish(account) }
            }

        }
    }

    fun signInActvity() = (activity as SignInActivity)


}
