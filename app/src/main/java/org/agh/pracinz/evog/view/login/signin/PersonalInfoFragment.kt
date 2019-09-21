package org.agh.pracinz.evog.view.login.signin


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_personal_info.view.*
import org.agh.pracinz.evog.R
import org.agh.pracinz.evog.di.manual.ViewModels
import org.agh.pracinz.evog.model.data.Sex
import org.agh.pracinz.evog.viewmodel.login.SignInViewModel
import java.time.LocalDate

class PersonalInfoFragment : Fragment() {

    lateinit var viewModel: SignInViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_personal_info, container, false)
        viewModel = ViewModels.singInViewModel
        view.firstNameInput.doAfterTextChanged {
            it?.toString()?.let { input -> viewModel.firstName = input }
        }
        view.secondNameInput.doAfterTextChanged {
            it?.toString()?.let { input -> viewModel.lastName = input }
        }
        view.descriptionInput.doAfterTextChanged {
            it?.toString()?.let { input -> viewModel.description = input }
        }
        view.sexRG.setOnCheckedChangeListener { group, checkedId ->
            viewModel.sex = Sex.valueOf(group.findViewById<RadioButton>(checkedId).text.toString().toUpperCase())
        }
        view.birthDate.setOnDateChangeListener { _, year, month, dayOfMonth ->
            viewModel.birthDate = LocalDate.of(year, month, dayOfMonth)
        }

        return view
    }


}
