package com.foolchen.lib.stl

import android.support.constraint.ConstraintLayout
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.TextView

/**
 * 默认的tab生成器
 * @author chenchong
 * 2018/10/27
 * 5:10 PM
 */
class DefaultTabGenerator(private val parent: SmartTabLayout) : ITabGenerator {


  override fun createTab(index: Int): TextView {
    val tab = TextView(parent.context)
    tab.setTextSize(TypedValue.COMPLEX_UNIT_PX, parent.getUnSelectedTextSize().toFloat())
    tab.setTextColor(parent.getUnSelectedTextColor())
    tab.setPadding(parent.getTabPaddingStart(), 0, parent.getTabPaddingEnd(), 0)
    val lp = ConstraintLayout.LayoutParams(
        ConstraintLayout.LayoutParams.WRAP_CONTENT,
        ConstraintLayout.LayoutParams.WRAP_CONTENT
    )
    if (index == 0) {
      // 第一个tab与父布局左边缘对齐
      lp.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
    } else {
      // 从第二个tab开始，位于上一个tab之后
      lp.startToEnd = getTabId(index - 1) // 位于上一个
    }
    lp.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
    lp.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
    tab.layoutParams = lp
    tab.id = getTabId(index) // 由于parentId=0，故此处使用index当做tab的id不能从0开始
    return tab
  }

  override fun createIndicator(): ImageView {
    val view = ImageView(parent.context)
    if (parent.getIndicatorDrawable() != null) {
      view.setImageDrawable(parent.getIndicatorDrawable())
    } else {
      view.setBackgroundColor(parent.getIndicatorColor())
    }
    val container = parent.getContainer()
    val lp = ConstraintLayout.LayoutParams(parent.getIndicatorWidth(), parent.getIndicatorHeight())
    lp.startToStart = container.getChildAt(0).id
    lp.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
    view.layoutParams = lp
    return view
  }

  override fun onBindTab(tab: View, title: String, index: Int) {
    (tab as TextView).text = title
  }

  override fun getTabId(index: Int) = index + 1
}