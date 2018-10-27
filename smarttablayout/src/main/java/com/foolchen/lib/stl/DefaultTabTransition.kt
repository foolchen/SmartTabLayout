package com.foolchen.lib.stl

import android.support.constraint.ConstraintLayout
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


/**
 * 默认的转场动画管理工具
 *
 * @author chenchong
 * 2018/10/27
 * 6:14 PM
 */
class DefaultTabTransition(private val parent: SmartTabLayout) : ITabTransition {

    override fun process(percentage: Float, from: Int, to: Int) {
        if (from == to) {
            // 此时表明选中的tab未发生变化，仅需要重新确认各个child的参数即可
            onProcessFinished(from)
        }
    }

    private fun onProcessFinished(position: Int) {
        val container = parent.getContainer()
        val childCount = container.childCount
        if (position >= childCount)
            throw IllegalArgumentException("错误的 position = $position")
        for (index in 0 until childCount - 1) {
            val textView = obtainTextView(container.getChildAt(index))
            if (index == position) {
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, parent.getSelectedTextSize().toFloat())
                textView.setTextColor(parent.getSelectedTextColor())
            } else {
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, parent.getUnSelectedTextSize().toFloat())
                textView.setTextColor(parent.getUnSelectedTextColor())
            }
            textView.invalidate()
        }
        parent.post {
            val currentTab = container.getChildAt(position)
            val indicator = container.getChildAt(childCount - 1)
            val lp = indicator.layoutParams as ConstraintLayout.LayoutParams
            val left = currentTab.left
            val right = currentTab.right
            val tabWidth = right - left
            if (lp.width < 0) { // 如果indicator为wrap_content或者match_parent，则将其宽度与当前tab的宽度等同
                lp.width = right - left
            }
            // 计算indicator在ConstraintLayout中的位置
            lp.leftMargin = (left + tabWidth / 2F - lp.width / 2F).toInt()
            indicator.layoutParams = lp
        }
    }

    private fun obtainTextView(view: View): TextView {
        return if (view is ViewGroup && parent.getTextViewId() != -1) {
            view.findViewById(parent.getTextViewId())
        } else {
            (view as? TextView) ?: throw IllegalArgumentException("在未指定textViewId时，需要tab为TextView")
        }
    }
}