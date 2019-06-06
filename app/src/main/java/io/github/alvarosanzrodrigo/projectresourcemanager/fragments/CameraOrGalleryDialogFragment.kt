package io.github.alvarosanzrodrigo.projectresourcemanager.fragments

import android.content.DialogInterface
import android.R
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import io.github.alvarosanzrodrigo.projectresourcemanager.adapters.AdapterProjects


class CameraOrGalleryDialogFragment : DialogFragment() {

    interface OnClickedOptionListener{
        fun onOptionChoosed(optionChoosed: Int)
    }

    var mCallBack: OnClickedOptionListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the Builder class for convenient dialog construction
        val builder = context?.let { AlertDialog.Builder(it) }
        builder?.setMessage(getString(io.github.alvarosanzrodrigo.projectresourcemanager.R.string.choose_gallery_or_camera))
            ?.setPositiveButton(getString(io.github.alvarosanzrodrigo.projectresourcemanager.R.string.camera), DialogInterface.OnClickListener { dialog, id ->
                // FIRE ZE MISSILES!
                mCallBack?.onOptionChoosed(1)
            })?.setNegativeButton(getString(io.github.alvarosanzrodrigo.projectresourcemanager.R.string.gallery), DialogInterface.OnClickListener { dialog, id ->
            // User cancelled the dialog
                mCallBack?.onOptionChoosed(2)
        })
        // Create the AlertDialog object and return it
        return builder!!.create()
    }
}