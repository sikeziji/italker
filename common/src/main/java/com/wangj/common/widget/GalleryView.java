package com.wangj.common.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wangj.common.R;
import com.wangj.common.widget.recycler.RecyclerAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class GalleryView extends RecyclerView {

    private Adapter mAdapter = new Adapter();
    private static final int LOADER_ID = 0x0100;
    private static final int MAX_IMAGE_COUNT = 3; // 最大选中图片数量
    private static final int MIN_IMAGE_FILE_SIZE = 10 * 1024; // 最小的图片大小
    private LoaderCallback loaderCallback = new LoaderCallback();
    private SelectedChangeListener mListener;
    private List<Image> mSelectedImages = new LinkedList<>();

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
        setLayoutManager(new GridLayoutManager(getContext(), 4));
        //初始化适配器
        setAdapter(mAdapter);
        mAdapter.setListener(new RecyclerAdapter.AdapterListenerImpl<Image>() {
            @Override
            public void onItemClick(RecyclerAdapter.ViewHolder<Image> holder, Image image) {
                //Cell 点击操作，如果可以点击，那么更新对应选中的Cell的状态
                // 然后更新界面，同理：如果不能点击（已达到最大选中数量） 那么不刷新界面
                if (onItemSelectClick(image)) {
                    holder.updateData(image);
                }
            }
        });

    }


    /**
     * Cell点击的具体逻辑
     *
     * @param image Image
     * @return True，代表我进行了数据更改，你需要刷新；反之不刷新
     */
    @SuppressLint("StringFormatMatches")
    private boolean onItemSelectClick(Image image) {
        // 是否需要进行刷新
        boolean notifyRefresh;
        if (mSelectedImages.contains(image)) {
            // 如果之前在那么现在就移除
            mSelectedImages.remove(image);
            //设置当前image状态为false
            image.isSelect = false;
            // 状态已经改变则需要更新
            notifyRefresh = true;
        } else {
            //当前选中的image是否为最大值
            if (mSelectedImages.size() >= MAX_IMAGE_COUNT) {
                // 得到提示文字，提示最多选择多少
                String str = getResources().getString(R.string.label_gallery_select_max_size);
                // 格式化填充
                str = String.format(str, MAX_IMAGE_COUNT);
                Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
                notifyRefresh = false;
            } else {
                //否则进行添加图片
                mSelectedImages.add(image);
                //当前image设置选中
                image.isSelect = true;
                //开始更新界面
                notifyRefresh = true;
            }
        }

        // 如果数据有更改，
        // 那么我们需要通知外面的监听者我们的数据选中改变了
        if (notifyRefresh)
            notifySelectChanged();
        return true;
    }

    /**
     * 初始化方法
     * @param loaderManager loader管理器
     * @return  返回一个Loader_id  用于销毁loader
     */
    public int setup(LoaderManager loaderManager,SelectedChangeListener listener){
        this.mListener = listener;
        loaderManager.initLoader(LOADER_ID,null,loaderCallback);
        return LOADER_ID;
    }

    /**
     * 得到选中的图片的全部地址
     *
     * @return 返回一个数组
     */
    public String[] getSelectedPath() {
        String[] paths = new String[mSelectedImages.size()];
        int index = 0;
        for (Image image : mSelectedImages) {
            paths[index++] = image.path;
        }
        return paths;
    }

    /**
     * 可以进行清空选中图片
     */
    public void clear(){
        for (Image image : mSelectedImages) {
            //先重置状态
            image.isSelect = false;
        }
        //清空缓存
        mSelectedImages.clear();
        //更新数据
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 通知Adapter数据更改的方法
     *
     * @param images 新的数据
     */
    private void updateSource(List<Image> images) {
        mAdapter.replace(images);
    }

    /**
     *
     * 通知选中状态改变
     */
    private void notifySelectChanged() {
        // 得到监听者，并判断是否有监听者，然后进行回调数量变化
        SelectedChangeListener listener = mListener;
        if (listener != null) {
            listener.onSelectedCountChanged(mSelectedImages.size());
        }
    }

    private class LoaderCallback implements LoaderManager.LoaderCallbacks<Cursor>{

        private final  String[] IMAGE_PROJECTION = new String[]{
                MediaStore.Images.Media._ID, // Id
                MediaStore.Images.Media.DATA, // 图片路径
                MediaStore.Images.Media.DATE_ADDED // 图片的创建时间ø
        };

        @NonNull
        @Override
        public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
            //创建一个loader
            if (id==LOADER_ID) {
                return new CursorLoader(getContext(),
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        IMAGE_PROJECTION,
                        null,
                        null,
                        IMAGE_PROJECTION[2] + " DESC"); // 倒序查询
            }
            return null;
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            // 当Loader加载完成时
            List<Image> images = new ArrayList<>();
            // 判断是否有数据
            if (data != null) {
                int count = data.getCount();
                if (count > 0) {//有数据
                    // 移动游标到开始
                    data.moveToFirst();

                    // 得到对应的列的Index坐标
                    int indexId = data.getColumnIndexOrThrow(IMAGE_PROJECTION[0]);
                    int indexPath = data.getColumnIndexOrThrow(IMAGE_PROJECTION[1]);
                    int indexDate = data.getColumnIndexOrThrow(IMAGE_PROJECTION[2]);

                    do {
                        // 循环读取，直到没有下一条数据
                        int id = data.getInt(indexId);
                        String path = data.getString(indexPath);
                        long dateTime = data.getLong(indexDate);

                        File file = new File(path);
                        if (!file.exists() || file.length() < MIN_IMAGE_FILE_SIZE) {
                            // 如果没有图片，或者图片大小太小，则跳过
                            continue;
                        }


                        // 添加一条新的数据
                        Image image = new Image();
                        image.id = id;
                        image.path = path;
                        image.date = dateTime;
                        images.add(image);


                    } while (data.moveToNext());
                }
            }
            updateSource(images);
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
            // 当Loader销毁或者重置了, 进行界面清空
            updateSource(null);
        }
    }


    /**
     * 设置图片类
     */
    public static class Image {
        //数据的ID
        int id;
        //图片的路径
        String path;
        //图片的擦护功能键日期
        long date;
        //是否选中
        boolean isSelect;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Image image = (Image) o;
            return Objects.equals(path, image.path);
        }

        @Override
        public int hashCode() {
            return Objects.hash(path);
        }
    }


    /**
     * 适配器
     */
    private class Adapter extends RecyclerAdapter<Image> {

        @Override
        protected int getItemViewType(int position, Image image) {
            return R.layout.cell_galley;
        }

        @Override
        protected ViewHolder<Image> onCreateViewHolder(View root, int viewType) {
            return new GalleryView.ViewHolder(root);
        }

    }


    /**
     * ViewHolder
     */
    private class ViewHolder extends RecyclerAdapter.ViewHolder<Image> {

        private ImageView mPic;
        private View mShade;
        private CheckBox mSelected;

        public ViewHolder(View itemView) {
            super(itemView);

            mPic =  itemView.findViewById(R.id.im_image);
            mShade = itemView.findViewById(R.id.view_shade);
            mSelected =  itemView.findViewById(R.id.cb_select);
        }

        @Override
        protected void OnBind(Image image) {
            Glide.with(getContext())
                    .load(image.path) // 加载路径
                    .diskCacheStrategy(DiskCacheStrategy.NONE) // 不使用缓存，直接从原图加载
                    .centerCrop() // 居中剪切
                    .placeholder(R.color.grey_200) // 默认颜色
                    .into(mPic);

            mShade.setVisibility(image.isSelect ? VISIBLE : INVISIBLE);
            mSelected.setChecked(image.isSelect);
            mSelected.setVisibility(VISIBLE);
        }
    }

    /**
     * 对外的一个监听器
     */
    public interface  SelectedChangeListener{
        void onSelectedCountChanged(int count);
    }


}
