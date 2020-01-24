package com.example.registerloginapp

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast

internal class CustomToast {
    fun showtoast(context: Context, view: View, error: Int) {
        val inflater = (context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
        val layout = inflater.inflate(R.layout.custom_toast,
                view.findViewById<ViewGroup>(R.id.toast_root) )
        val text = layout.findViewById<TextView>(R.id.toast_error)
        text.setText(error)
        val toast = Toast(context) // Get Toast Context
        toast.setGravity(Gravity.TOP or Gravity.FILL_HORIZONTAL, 0, 0)
        toast.duration = Toast.LENGTH_SHORT
        toast.view = layout
        toast.show()
    }
}