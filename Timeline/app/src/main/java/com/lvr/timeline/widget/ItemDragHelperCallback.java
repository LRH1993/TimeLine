/*
 * Copyright (c) 2016 咖枯 <kaku201313@163.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.lvr.timeline.widget;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;

public class ItemDragHelperCallback extends ItemTouchHelper.Callback {
    private OnItemMoveListener mOnItemMoveListener;
    private OnItemSwipeListener mOnItemSwipeListener;

    public interface OnItemMoveListener {
        void onItemMove(int fromPosition, int toPosition);
    }
    public interface OnItemSwipeListener {
        void onItemSwipe(int position);
    }

    //相当于 set 设置监听 传入TimeAdapter中的OnItemMoveListener对象
    public ItemDragHelperCallback(OnItemMoveListener onItemMoveListener,OnItemSwipeListener onItemSwipeListener) {
        mOnItemMoveListener = onItemMoveListener;
        mOnItemSwipeListener = onItemSwipeListener;
    }
    //返回true 允许拖拽
    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }
    //返回true 允许滑动
    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        //根据recyclerView的布局，进行设置拖拽的方向
        int dragFlags = setDragFlags(recyclerView);
        //不允许进行滑动
        int swipeFlags = setSwipeFlags(recyclerView);
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    private int setSwipeFlags(RecyclerView recyclerView) {
        int swipeFlags = 0;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager ) {
            swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        }
        return swipeFlags;
    }

    private int setDragFlags(RecyclerView recyclerView) {
        int dragFlags;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager || layoutManager instanceof StaggeredGridLayoutManager) {
            dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        } else {
            dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        }
        return dragFlags;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        mOnItemMoveListener.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return false;

    }


    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);

    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

        mOnItemSwipeListener.onItemSwipe(viewHolder.getAdapterPosition());
    }
}
