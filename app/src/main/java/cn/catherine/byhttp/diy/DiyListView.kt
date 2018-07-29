package cn.catherine.byhttp.diy

import android.content.Context
import android.util.AttributeSet
import android.view.*
import android.widget.ListView
import android.widget.RelativeLayout
import cn.catherine.byhttp.R

/**
 *
 * ByHttp
 *
 * cn.catherine.byhttp.diy
 *
 * created by catherine in 七月/28/2018/上午2:54
 */

class DiyListView : ListView,
        View.OnTouchListener,
        GestureDetector.OnGestureListener {

    constructor(context: Context?, attrs: AttributeSet) : super(context, attrs) {
        gestureDetector = GestureDetector(context, this)
        setOnTouchListener(this)
    }

    interface OnDeleteListener {
        fun onDelete(index: Int)
    }

    private var gestureDetector: GestureDetector? = null//手势动作探测器
    private lateinit var onDeleteListener: OnDeleteListener

    fun setonDeleteListener(deleteListener: OnDeleteListener) {
        this.onDeleteListener = deleteListener
    }

    fun isDeleteShow(): Boolean = isDeleteShow()

    private var isShowDeleteBtn = false//当前删除按钮是否显示出来
    private var currentSelectItem: Int = 0//当前选择的列表项
    private var itemLayout: ViewGroup? = null//列表项布局
    private var deleteBtn: View? = null

    override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
        return if (Math.abs(velocityX) > Math.abs(velocityY)) {//当水平移动大于垂直移动
            if (!isShowDeleteBtn) {//如果当前没有显示delete
                showDeleteBtn()
            } else {//如果显示，就隐藏
                hideDeleteBtn()
            }
            true

        } else {
            false
        }
    }

    private fun showDeleteBtn() {
        deleteBtn = LayoutInflater.from(context).inflate(R.layout.int_delete, null)
        deleteBtn!!.setOnClickListener {
            hideDeleteBtn()
            onDeleteListener.onDelete(currentSelectItem)
        }
        itemLayout = getChildAt(currentSelectItem - firstVisiblePosition) as ViewGroup?
        var params = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
        params.addRule(RelativeLayout.CENTER_VERTICAL)
        itemLayout!!.addView(deleteBtn, params)
        isShowDeleteBtn = true
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        return gestureDetector!!.onTouchEvent(event)
//以下用于触摸以下就隐藏删除按钮
//        if (isShowDeleteBtn) {
//            hideDeleteBtn()
//            false
//        } else {
//            gestureDetector!!.onTouchEvent(event)
//        }
    }

    /**
     * 隐藏删除按钮
     * */
    fun hideDeleteBtn() {
        itemLayout!!.removeView(deleteBtn)
        deleteBtn = null
        isShowDeleteBtn = false

    }

    override fun onShowPress(e: MotionEvent?) {
    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean = false

    override fun onDown(e: MotionEvent): Boolean {
        if (!isShowDeleteBtn) {
            currentSelectItem = pointToPosition(e.x.toInt(), e.y.toInt())
        }
        return false
    }

    override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean = false

    override fun onLongPress(e: MotionEvent?) {
    }


}