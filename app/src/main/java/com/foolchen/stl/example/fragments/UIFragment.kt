package com.foolchen.stl.example.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class UIFragment : Fragment() {

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    if (container != null) {
      val text = TextView(container.context)
      val lp = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
          ViewGroup.LayoutParams.MATCH_PARENT)
      text.layoutParams = lp
      text.text = arguments!!.getString("title")
      text.gravity = Gravity.CENTER
      return text
    }
    return super.onCreateView(inflater, container, savedInstanceState)
  }
}