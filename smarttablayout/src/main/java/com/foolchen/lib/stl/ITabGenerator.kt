package com.foolchen.lib.stl

import android.view.View

/**
 * 用于创建tab的接口
 *
 * @author chenchong
 * 2018/10/27
 * 5:00 PM
 */
interface ITabGenerator {
  /**
   * 创建一个tab
   */
  fun createTab(index: Int): View

  fun createIndicator(): View
  /**
   * 向tab绑定数据
   */
  fun onBindTab(tab: View, title: String, index: Int)

  fun getTabId(index: Int): Int
}