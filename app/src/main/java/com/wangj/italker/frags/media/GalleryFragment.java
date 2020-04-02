package com.wangj.italker.frags.media;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.wangj.common.tools.UiTool;
import com.wangj.common.widget.GalleryView;
import com.wangj.italker.R;

import net.qiujuer.genius.ui.Ui;

public class GalleryFragment extends BottomSheetDialogFragment implements GalleryView.SelectedChangeListener {


    private GalleryView mGalleryView;

    private OnSelectedListener mListener;

    protected int getContentLayoutId() {
        return R.layout.fragment_gallery;
    }

    public GalleryFragment() {
    }

    public GalleryFragment(OnSelectedListener listener) {
        setListener(listener);

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //先使用官方默认的
        return new TransStatusBootomSheetDialog(getContext());

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        mGalleryView = root.findViewById(R.id.galleryView);
        return root;

    }

    @Override
    public void onStart() {
        super.onStart();
        mGalleryView.setup(getLoaderManager(), this);
    }

    @Override
    public void onSelectedCountChanged(int count) {
        //如果选中一张图片
        if (count > 0) {
            //关闭
            dismiss();
            if (mListener != null) {
                //得到路径
                String[] paths = mGalleryView.getSelectedPath();
                //返回第一张
                mListener.onSlectedImage(paths[0]);
                //取消和唤起者之间的引用，加快内存回收
                mListener = null;
            }
        }

    }


    /**
     * 设置监听并返回自己
     *
     * @param listener
     * @return
     */
    public GalleryFragment setListener(OnSelectedListener listener) {
        mListener = listener;
        return this;
    }

    /**
     * 开放接口
     */
    public interface OnSelectedListener {
        //实现选中图片
        void onSlectedImage(String path);
    }


    public static class TransStatusBootomSheetDialog extends BottomSheetDialog {

        public TransStatusBootomSheetDialog(@NonNull Context context) {
            super(context);
        }

        public TransStatusBootomSheetDialog(@NonNull Context context, int theme) {
            super(context, theme);
        }

        protected TransStatusBootomSheetDialog(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
            super(context, cancelable, cancelListener);
        }


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            final Window window = getWindow();
            if (window == null) {
                return;
            }
            //得到屏幕高度
            int screenHeight = UiTool.getScreenHeight(getOwnerActivity());
            //得到状态栏的高度
            int statuHeight = UiTool.getStatusBarHeight(getOwnerActivity());
            //计算dialogheight的高度并设置
            int dialogHeight = screenHeight - statuHeight;
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                    dialogHeight <= 0 ? ViewGroup.LayoutParams.MATCH_PARENT : dialogHeight);


        }
    }

}
