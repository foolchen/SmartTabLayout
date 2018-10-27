package com.foolchen.lib.stl

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.support.constraint.ConstraintLayout
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.HorizontalScrollView

/**
 * 用于ViewPager或者多个Fragment的标签导航
 * @author chenchong
 * 2018/10/27
 * 4:57 PM
 */
class SmartTabLayout : HorizontalScrollView {
  private val mContainer: ConstraintLayout by lazy {
    val constraintLayout = ConstraintLayout(context)
    val lp = ViewGroup.LayoutParams(
        ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.MATCH_PARENT
    )
    this@SmartTabLayout.addView(constraintLayout, lp)
    constraintLayout
  }

  private var mUnSelectedTextSize: Int = 40
  private var mSelectedTextSize: Int = 40
  private var mUnSelectedTextColor: Int = Color.BLACK
  private var mSelectedTextColor: Int = Color.BLACK
  private var mPaddingStart: Int = 15
  private var mPaddingEnd: Int = 15
  private var mIndicatorColor = Color.BLUE
  private var mIndicatorDrawable: Drawable? = null
  private var mIndicatorWidth = ConstraintLayout.LayoutParams.MATCH_PARENT
  private var mIndicatorHeight = 9
  private var mCurrentPosition = 0
  private var mTextViewId = -1

  constructor(context: Context) : super(context) {
    init(context, null)
  }

  constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
    init(context, attrs)
  }

  constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
      context, attrs,
      defStyleAttr
  ) {
    init(context, attrs)
  }

  private val mPageChangeListener: ViewPager.SimpleOnPageChangeListener by lazy {
    object : ViewPager.SimpleOnPageChangeListener() {
      override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        //mTabTransition.process(positionOffset, mCurrentPosition, position)

      }

      override fun onPageSelected(position: Int) {
        mTabTransition.process(1F, position, position)
        mCurrentPosition = position
      }
    }
  }
  private var mTabTransition: ITabTransition = DefaultTabTransition(this)

  private lateinit var mTabGenerator: ITabGenerator
  private var mViewPager: ViewPager? = null
  private var mAdapter: PagerAdapter? = null

  private fun init(context: Context, attrs: AttributeSet?) {
    if (attrs != null) {
      val ta = context.obtainStyledAttributes(attrs, R.styleable.SmartTabLayout)
      mUnSelectedTextSize = ta.getDimensionPixelSize(
          R.styleable.SmartTabLayout_stl_unselected_text_size, 40
      )
      mSelectedTextSize = ta.getDimensionPixelSize(
          R.styleable.SmartTabLayout_stl_selected_text_size, 40
      )
      mUnSelectedTextColor = ta.getColor(
          R.styleable.SmartTabLayout_stl_unselected_text_color,
          Color.BLACK
      )
      mSelectedTextColor = ta.getColor(
          R.styleable.SmartTabLayout_stl_selected_text_color,
          Color.BLACK
      )
      mPaddingStart = ta.getDimensionPixelSize(R.styleable.SmartTabLayout_stl_padding_start, 15)
      mPaddingEnd = ta.getDimensionPixelSize(R.styleable.SmartTabLayout_stl_padding_end, 15)
      mIndicatorColor = ta.getColor(R.styleable.SmartTabLayout_stl_indicator_color, Color.BLUE)
      mIndicatorWidth = ta.getDimensionPixelSize(
          R.styleable.SmartTabLayout_stl_indicator_width,
          ConstraintLayout.LayoutParams.MATCH_PARENT
      )
      mIndicatorHeight = ta.getDimensionPixelSize(
          R.styleable.SmartTabLayout_stl_indicator_height,
          9
      )
      mIndicatorDrawable = ta.getDrawable(R.styleable.SmartTabLayout_stl_indicator_drawable)
      mTextViewId = ta.getResourceId(R.styleable.SmartTabLayout_stl_text_view_id, -1)
      ta.recycle()
    }

    mTabGenerator = DefaultTabGenerator(this)
  }

  fun setTabGenerator(tabGenerator: ITabGenerator) {
    mTabGenerator = tabGenerator
  }

  fun getTabGenerator() = mTabGenerator

  fun getUnSelectedTextSize() = mUnSelectedTextSize

  fun getSelectedTextSize() = mSelectedTextSize

  fun getUnSelectedTextColor() = mUnSelectedTextColor

  fun getSelectedTextColor() = mSelectedTextColor

  fun getTabPaddingStart() = mPaddingStart

  fun getTabPaddingEnd() = mPaddingEnd

  fun getIndicatorColor() = mIndicatorColor

  fun getIndicatorWidth() = mIndicatorWidth

  fun getIndicatorHeight() = mIndicatorHeight

  fun getIndicatorDrawable() = mIndicatorDrawable

  fun getTextViewId() = mTextViewId

  /**
   * 使用ViewPager初始化导航
   */
  fun setViewPager(vp: ViewPager) {
    mViewPager = vp
    vp.addOnPageChangeListener(mPageChangeListener)
    val adapter = vp.adapter ?: throw NullPointerException(
        "在初始化SmartTabLayout之前请先为ViewPager设置Adapter"
    )
    mAdapter = adapter
    notifyDataSetChanged()
  }

  fun notifyDataSetChanged() {
    removeAllViews() // 首先移除所有的tab，防止重复添加
    addTabs()
    addIndicator()
    post { mTabTransition.process(1f, mCurrentPosition, mCurrentPosition) }
  }

  fun getTabCount() = mAdapter?.count ?: 0

  override fun onAttachedToWindow() {
    super.onAttachedToWindow()
    mViewPager?.removeOnPageChangeListener(mPageChangeListener) // 防止重复添加
    mViewPager?.addOnPageChangeListener(mPageChangeListener)
  }

  override fun onDetachedFromWindow() {
    super.onDetachedFromWindow()
    mViewPager?.removeOnPageChangeListener(mPageChangeListener)
  }

  internal fun getContainer() = mContainer

  // 向布局中添加tab
  private fun addTabs() {
    mAdapter?.let { adapter ->
      val count = adapter.count
      for (index in 0 until count) {
        val tab = mTabGenerator.createTab(index)
        mContainer.addView(tab)
        mTabGenerator.onBindTab(tab, adapter.getPageTitle(index).toString(), index)
      }
    }
  }

  private fun addIndicator() {
    mContainer.addView(mTabGenerator.createIndicator())
  }
}