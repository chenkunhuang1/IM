package com.example.asus.im.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.asus.im.R
import kotlinx.android.synthetic.main.fragment_contacts.view.*
import kotlinx.android.synthetic.main.view_contact_item.view.*
import org.jetbrains.anko.sp
import kotlin.math.sign

/**
 * Created by ckh.
 * date : 2019/1/7 22:18
 * desc :
 */
class SlideBar(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    var sectionHeight = 0f
    var textBaseLine = 0f

    var onSectionChangeListener : OnSectionChangeListener? = null

    var paint = Paint()
    companion object {
        private val SECTIONS = arrayOf("A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
            "K", "L","M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z")
    }

    init {
        paint.apply {
            color = resources.getColor(R.color.qq_section_text_color)
            textSize = sp(12).toFloat()
            textAlign = Paint.Align.CENTER
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        //计算每个字符的高度
        sectionHeight = h * 1.0f / SECTIONS.size
        val fontMetrics = paint.fontMetrics
        //计算文本高度
        val textHeight = fontMetrics.descent - fontMetrics.ascent
        //计算基准线
        textBaseLine = sectionHeight / 2 + (textHeight / 2 - fontMetrics.descent)

    }

    override fun onDraw(canvas: Canvas?) {
        //绘制所有字母
        val x = width * 1.0f / 2
        var baseLine = textBaseLine
        SECTIONS.forEach {
            canvas?.drawText(it,x,baseLine,paint)
            //更新y 绘制下一个
            baseLine += sectionHeight
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when(event.action){
            MotionEvent.ACTION_DOWN -> {
                setBackgroundResource(R.drawable.bg_slide_bar)
                //找到点击的字母
                val index = getTouchIndex(event)
                val firstLetter = SECTIONS[index]
                onSectionChangeListener?.onSectionChange(firstLetter)
            }
            MotionEvent.ACTION_MOVE ->{
                //找到点击的字母
                val index = getTouchIndex(event)
                val firstLetter = SECTIONS[index]
                onSectionChangeListener?.onSectionChange(firstLetter)
            }
            MotionEvent.ACTION_UP -> {
                setBackgroundColor(Color.TRANSPARENT)
                onSectionChangeListener?.onSlideFinish()
            }
        }
        return true//消费事件
    }

    /**
     * 找到点击位置字母的下标
     */
    private fun getTouchIndex(event: MotionEvent): Int {
        var index = (event.y / sectionHeight).toInt()
        //越界检查
        if (index < 0){
            index = 0
        }else if(index >= SECTIONS.size){
            index = SECTIONS.size - 1
        }
        return index
    }

    interface OnSectionChangeListener{
        fun onSectionChange(firstLetter:String)
        fun onSlideFinish()//滑动结束的回调
    }

}