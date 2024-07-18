package com.example.freemarket

import android.app.Dialog

class ProgressDialog {
    fun showDialog(context:android.content.Context) {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.dialog_progressbar)
        dialog.show()
    }
}