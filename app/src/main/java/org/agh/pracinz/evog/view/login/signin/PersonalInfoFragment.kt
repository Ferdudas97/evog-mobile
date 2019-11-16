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
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_personal_info.*
import kotlinx.android.synthetic.main.fragment_personal_info.view.*
import org.agh.pracinz.evog.R
import org.agh.pracinz.evog.di.manual.ViewModels
import org.agh.pracinz.evog.model.data.Sex
import org.agh.pracinz.evog.view.common.MyDatePicker
import org.agh.pracinz.evog.view.onSelectedChange
import org.agh.pracinz.evog.view.onTextChange
import org.agh.pracinz.evog.viewmodel.login.SignInViewModel
import java.io.ByteArrayOutputStream


private const val PICK_IMAGE_REQUEST = 1

class PersonalInfoFragment : Fragment() {

    lateinit var viewModel: SignInViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModels.singInViewModel
        return inflater.inflate(R.layout.fragment_personal_info, container, false).apply {
            firstNameInput.onTextChange {
                viewModel.firstName = it
            }
            secondNameInput.onTextChange {
                viewModel.lastName = it
            }
            descriptionInput.onTextChange {
                viewModel.description = it
            }
            sexRG.onSelectedChange {
                viewModel.sex = Sex.valueOf(it)
            }
            birthDateButton.setOnClickListener {
                MyDatePicker(activity!!).createPicker {
                    viewModel.birthDate = it
                }
            }
            createUserPhotoIV.setOnClickListener { chooseImage() }
            defaultImage()?.let { createUserPhotoIV.setImageResource(it) }
        }
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
