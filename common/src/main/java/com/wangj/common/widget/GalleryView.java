package com.wangj.common.widget;

import android.content.Context;
import android.telephony.mbms.MbmsErrors;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wangj.common.R;
import com.wangj.common.widget.recycler.RecyclerAdapter;

public class GalleryView extends RecyclerView {

    private Adapter mAdapter;

    public GalleryView(Context context) {
        super(context);
        initView();
    }

    public GalleryView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public GalleryView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }


    /**
     * 初始化
     */
    private void initView() {
        setLayoutManager(new GridLayoutManager(getContext(),4));
        //初始化适配器
        setAdapter(mAdapter);
        mAdapter.setListener(new RecyclerAdapter.AdapterListener<Image>() {
            @Override
            public void onItemClick(RecyclerAdapter.ViewHolder<Image> holder, Image image) {

            }

            @Override
            public void onItemLong(RecyclerAdapter.ViewHolder<Image> holder, Image image) {

            }
        });

    }

    /**
     * 设置图片类
     */
    private static class Image{

    }


    /**
     * 适配器
     */
    private class Adapter extends RecyclerAdapter<Image>{

        @Override
        protected int getItemViewType(int position, Image image) {
            return R.layout.cell_galley;
        }

        @Override
        protected ViewHolder<Image> onCreateViewHolder(View root, int viewType) {
            return null;
        }

        @Override
        public void update(Image image, ViewHolder<Image> holder) {

        }
    }


    /**
     * ViewHolder
     */
    private class ViewHolder extends RecyclerAdapter.ViewHolder<Image> {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        protected void OnBind(Image image) {

        }
    }


}
