package com.example.sergeykuchin.spacexrockets.ui.rocket.adapter

import android.graphics.Canvas
import android.util.LruCache
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


class LaunchHeaderItemDecoration(private var listener: LaunchHeaderInterface) : RecyclerView.ItemDecoration() {

    private var stickyHeaderHeight: Int = 0

    private val lruViewsCache: LruCache<Int, View> = LruCache(DEFAULT_BUFFER_SIZE)

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)

        val topChild = parent.getChildAt(0) ?: return

        val topChildPosition = parent.getChildAdapterPosition(topChild)

        if (topChildPosition == RecyclerView.NO_POSITION) {
            return
        }

        val currentHeader = getHeaderViewForItem(topChildPosition, parent)
        fixLayoutSize(parent, currentHeader)
        val contactPoint = currentHeader.bottom
        val childInContact = getChildInContact(parent, contactPoint) ?: return

        if (listener.isHeader(parent.getChildAdapterPosition(childInContact))) {
            moveHeader(c, currentHeader, childInContact)
            return
        }

        drawHeader(c, currentHeader)
    }

    private fun getHeaderViewForItem(itemPosition: Int, parent: RecyclerView): View {
        val headerPosition = listener.getHeaderPositionForItem(itemPosition)
        val layoutResId = listener.getHeaderLayout(headerPosition)

        val cachedView = lruViewsCache.get(layoutResId)
        val header = if (cachedView == null) {
            val newView = LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)
            lruViewsCache.put(layoutResId, newView)
            newView
        } else cachedView

        listener.bindHeaderData(header, headerPosition)
        return header
    }

    private fun drawHeader(c: Canvas, header: View) {
        c.save()
        c.translate(0f, 0f)
        header.draw(c)
        c.restore()
    }

    private fun moveHeader(c: Canvas, currentHeader: View, nextHeader: View) {
        c.save()
        c.translate(0f, (nextHeader.top - currentHeader.height).toFloat())
        currentHeader.draw(c)
        c.restore()
    }

    private fun getChildInContact(parent: RecyclerView, contactPoint: Int): View? {
        var childInContact: View? = null
        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)
            if (child.bottom > contactPoint) {
                if (child.top <= contactPoint) {
                    // This child overlaps the contactPoint
                    childInContact = child
                    break
                }
            }
        }
        return childInContact
    }

    /**
     * Properly measures and layouts the top sticky header.
     * @param parent ViewGroup: RecyclerView in this case.
     */
    private fun fixLayoutSize(parent: ViewGroup, view: View) {

        // Specs for parent (RecyclerView)
        val widthSpec = View.MeasureSpec.makeMeasureSpec(parent.width, View.MeasureSpec.EXACTLY)
        val heightSpec = View.MeasureSpec.makeMeasureSpec(parent.height, View.MeasureSpec.UNSPECIFIED)

        // Specs for children (headers)
        val childWidthSpec =
            ViewGroup.getChildMeasureSpec(widthSpec, parent.paddingLeft + parent.paddingRight, view.layoutParams.width)
        val childHeightSpec = ViewGroup.getChildMeasureSpec(
            heightSpec,
            parent.paddingTop + parent.paddingBottom,
            view.layoutParams.height
        )

        view.measure(childWidthSpec, childHeightSpec)

        stickyHeaderHeight = view.measuredHeight
        view.layout(0, 0, view.measuredWidth, stickyHeaderHeight)
    }
}

