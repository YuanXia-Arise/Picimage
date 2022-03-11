package com.yuanxia.imaging.sample

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_image_edit_sample.*
import com.yuanxia.yuanxia.imaging.IMGEditActivity
import com.yuanxia.yuanxia.imaging.IMGGalleryActivity
import com.yuanxia.yuanxia.imaging.gallery.model.IMGChooseMode
import com.yuanxia.yuanxia.imaging.gallery.model.IMGImageInfo
import java.io.File
import java.util.*


class ImageEditSampleActivity : AppCompatActivity() {

    private var mImageFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_edit_sample)

        btn_choose_image.setOnClickListener {
            chooseImages()
        }
    }

    private fun chooseImages() {
        startActivityForResult(IMGGalleryActivity.newIntent(this, IMGChooseMode.Builder().setSingleChoose(true).build()), REQ_IMAGE_CHOOSE)
    }

    private fun onChooseImages(images: List<IMGImageInfo>?) {
        val image = images?.get(0)
        if (image != null) {

            sdv_image.setImageURI(image.uri, null)
//Environment.getExternalStorageDirectory().getAbsolutePath()
            mImageFile = File(/*cacheDir*/"/storage/emulated/0/Download/", UUID.randomUUID().toString() + ".jpg")

            startActivityForResult(Intent(this, IMGEditActivity::class.java)
                    .putExtra(IMGEditActivity.EXTRA_IMAGE_URI, image.uri)
                    .putExtra(IMGEditActivity.EXTRA_IMAGE_SAVE_PATH, mImageFile?.absolutePath), REQ_IMAGE_EDIT)
        }
    }

    private fun onImageEditDone() {
        sdv_image_edit.setImageURI(Uri.fromFile(mImageFile), null)
        Log.d("1234567", "onImageEditDone: " + Uri.fromFile(mImageFile))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQ_IMAGE_CHOOSE -> {
                if (resultCode == Activity.RESULT_OK) {
                    onChooseImages(IMGGalleryActivity.getImageInfos(data))
                }
            }
            REQ_IMAGE_EDIT -> {
                if (resultCode == Activity.RESULT_OK) {
                    onImageEditDone()
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}