package org.agh.pracinz.evog.view.login.signin


import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.media.ExifInterface
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.core.graphics.drawable.toBitmap
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_personal_info.*
import kotlinx.android.synthetic.main.fragment_personal_info.view.*
import org.agh.pracinz.evog.R
import org.agh.pracinz.evog.di.manual.ViewModels
import org.agh.pracinz.evog.model.data.Sex
import org.agh.pracinz.evog.viewmodel.login.SignInViewModel
import java.io.ByteArrayOutputStream
import java.time.LocalDate


private const val PICK_IMAGE_REQUEST = 1

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
            viewModel.sex =
                Sex.valueOf(group.findViewById<RadioButton>(checkedId).text.toString().toUpperCase())
        }
        view.birthDate.setOnDateChangeListener { _, year, month, dayOfMonth ->
            viewModel.birthDate = LocalDate.of(year, month, dayOfMonth)
        }
        view.createUserPhotoIV.setOnClickListener { chooseImage() }
        defaultImage()?.let { view.createUserPhotoIV.setImageResource(it) }

        return view
    }

    private fun defaultImage(): Int? {
        if (viewModel.file == null) {
            return when (viewModel.sex) {
                Sex.MALE -> R.drawable.male_user
                Sex.FEMALE -> R.drawable.famele_user
            }
        }
        return null
    }

    private fun chooseImage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    fun getCameraPhotoOrientation(imageFilePath: String): Int {
        val exif: ExifInterface = ExifInterface(imageFilePath)

        val exifOrientation = exif
            .getAttribute(ExifInterface.TAG_ORIENTATION)
        Log.d("exifOrientation", exifOrientation)
        val orientation = exif.getAttributeInt(
            ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_NORMAL
        )
        return when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_270 -> 270
            ExifInterface.ORIENTATION_ROTATE_180 -> 180
            ExifInterface.ORIENTATION_ROTATE_90 -> 90
            else -> 0
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            data?.data?.let {

                val loaded = Picasso.get().load(it)
                loaded.placeholder(defaultImage() ?: R.drawable.male_user)
                    .into(createUserPhotoIV, object : Callback {

                        override fun onSuccess() {
                            val photo = createUserPhotoIV.drawable.toBitmap()
                            val stream = ByteArrayOutputStream()
                            photo.compress(Bitmap.CompressFormat.PNG, 100, stream)
                            viewModel.file = stream.toByteArray()
                            stream.close()

                        }

                        override fun onError(e: Exception?) {
                        }
                    })
            }

        }
    }
}
