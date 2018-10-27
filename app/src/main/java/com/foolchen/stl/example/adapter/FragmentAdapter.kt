package com.foolchen.stl.example.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.foolchen.stl.example.fragments.UIFragment
import org.jetbrains.anko.bundleOf

class FragmentAdapter(
    fm: FragmentManager,
    private val titles: Array<CharSequence>
) : FragmentStatePagerAdapter(fm) {


  override fun getItem(position: Int): Fragment {
    val f = UIFragment()
    f.arguments = bundleOf("title" to getPageTitle(position))
    return f
  }

  override fun getCount(): Int = titles.size

  override fun getPageTitle(position: Int): CharSequence = titles[position]
}