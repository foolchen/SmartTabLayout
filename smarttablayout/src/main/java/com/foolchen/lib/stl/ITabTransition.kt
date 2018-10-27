package com.foolchen.lib.stl

/**
 * 用于定义tab切换时transition的行为的接口
 *
 * @author chenchong
 * 2018/10/27
 * 5:03 PM
 */
interface ITabTransition {
  fun process(percentage: Float, from: Int, to: Int)
}