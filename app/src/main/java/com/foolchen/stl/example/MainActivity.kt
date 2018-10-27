package com.foolchen.stl.example

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.foolchen.stl.example.adapter.FragmentAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    vp.adapter = FragmentAdapter(supportFragmentManager,
        resources.getTextArray(R.array.fragment_titles))
    stl.setViewPager(vp)
  }
}
