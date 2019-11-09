package org.agh.pracinz.evog.view.user

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_user_details.*
import org.agh.pracinz.evog.R
import org.agh.pracinz.evog.di.manual.ViewModels
import org.agh.pracinz.evog.model.data.User
import org.agh.pracinz.evog.viewmodel.login.UserDetailsViewModel
import java.time.LocalDateTime

class UserDetailsActivity : AppCompatActivity() {

    private lateinit var viewModel: UserDetailsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModels.userDetailsViewModel
        setContentView(R.layout.activity_user_details)

        intent?.extras?.get("USER_ID")?.let {
            viewModel.getUser(it as String).observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setUi)

        }
    }

    private fun setUi(user: User) {
        user.apply {
            userDetailsMail.text = email
            userDetailsAge.text = "${LocalDateTime.now().year - birthDate.year} lat"
            userDetailsName.text = "$firstName $lastName"
            userDetailsDescription.text = description
            userDetailsPhoneNumberTv.text = phoneNumber
            viewModel.loadImage(userDetailsPhotoIV, user.photosId.first())

        }
    }
}
