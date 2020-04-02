package com.wangj.italker.frags.main;

import com.wangj.common.app.BaseFragment;
import com.wangj.common.widget.GalleryView;
import com.wangj.italker.R;

import butterknife.BindView;

public class ActivityFragment extends BaseFragment {

    @BindView(R.id.galleyView)
    GalleryView galleryView;

    @Override
    protected int getContentLayoutId() {

        System.out.println("加载界面");

        return R.layout.fragment_activity;
    }

    @Override
    protected void initData() {
        super.initData();

        galleryView.setup(getLoaderManager(), new GalleryView.SelectedChangeListener() {
            @Override
            public void onSelectedCountChanged(int count) {

            }
        });
    }
}
