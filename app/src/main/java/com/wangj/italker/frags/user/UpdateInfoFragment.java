package com.wangj.italker.frags.user;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.wangj.common.app.Application;
import com.wangj.common.app.BaseFragment;
import com.wangj.common.widget.GalleryView;
import com.wangj.common.widget.PortraitView;
import com.wangj.factory.Factory;
import com.wangj.factory.net.UploadHelper;
import com.wangj.italker.R;
import com.wangj.italker.frags.media.GalleryFragment;
import com.yalantis.ucrop.UCrop;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * 用户更新信息的Fragment
 */
public class UpdateInfoFragment extends BaseFragment {
    @BindView(R.id.im_portrait)
    PortraitView mPortraitView;


    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_update_info;
    }


    @OnClick(R.id.im_portrait)
    void onPortraitClick() {
        new GalleryFragment()
                .setListener(new GalleryFragment.OnSelectedListener() {
                    @Override
                    public void onSlectedImage(String path) {
                        UCrop.Options options = new UCrop.Options();
                        //设置图片处理的格式
                        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
                        //设置压缩后的图片精度
                        options.setCompressionQuality(96);

                        // 得到头像的缓存地址
                        File dPath = Application.getPortraitTmpFile();


                        UCrop.of(Uri.fromFile(new File(path)), Uri.fromFile(dPath))
                                .withAspectRatio(1, 1) // 1比1比例
                                .withMaxResultSize(520, 520) // 返回最大的尺寸
                                .withOptions(options) // 相关参数
                                .start(getActivity());
                    }
                })
                //show 的时候建议使用getChildFragmentManager
                //tag  GalleryFragment class 名
                .show(getChildFragmentManager(), GalleryFragment.class.getName());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //回调成功
        System.out.println("回调成功");
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            final Uri resultUri = UCrop.getOutput(data);

            if (resultUri != null) {
                loadPortrait(resultUri);

            }
        } else if (requestCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
        }
    }

    private void loadPortrait(Uri resultUri) {


        Glide.with(this)
                .asBitmap()
                .load(resultUri)
                .centerCrop()
                .into(mPortraitView);

        //对图片进行上传
        String localPath = resultUri.getPath();


        Factory.runOnAsync(new Runnable() {
            @Override
            public void run() {
                String url = UploadHelper.uploadPortrait(localPath);
                System.out.println(url);
            }
        });
    }
}
