package com.shengping.paomanager.adapter;

import java.util.LinkedList;
import java.util.List;
import com.shengping.paomanager.Activity_SelectPhoto;
import com.shengping.paomanager.R;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

public class Adapter_SelectPhoto extends CommonAdapter<String>
{

	/**
	 * 用户选择的图片，存储为图片的完整路径
	 */
	public static List<String> mSelectedImage = new LinkedList<String>();
	private int  selectType=0;//选择模式0是只能�?�择�?张图片，1是可以�?�择3张图�?
	private ImageView currentSelect,currentView;
	/**
	 * 文件夹路�?
	 */
	private String mDirPath;
	private Context context;
	public Adapter_SelectPhoto(Context context, List<String> mDatas, int itemLayoutId,
			String dirPath,int selectType)
	{
		super(context, mDatas, itemLayoutId);
		this.mDirPath = dirPath;
		this.context=context;
		mSelectedImage.clear();
		this.selectType=selectType;
	}

	@Override
	public void convert(final ViewHolder helper, final String item)
	{
		//设置no_pic
		helper.setImageResource(R.id.id_item_image, R.drawable.pictures_no);
		//设置no_selected
		helper.setImageResource(R.id.id_item_select,
						R.drawable.picture_unselected);
		//设置图片
		helper.setImageByUrl(R.id.id_item_image, mDirPath + "/" + item);
		
		final ImageView mImageView = helper.getView(R.id.id_item_image);
		final ImageView mSelect = helper.getView(R.id.id_item_select);
		
		mImageView.setColorFilter(null);
		//设置ImageView的点击事�?
		mImageView.setOnClickListener(new OnClickListener()
		{
			//选择，则将图片变暗，反之则反�?
			@Override
			public void onClick(View v)
			{

				// 已经选择过该图片
				if (mSelectedImage.contains(mDirPath + "/" + item))
				{
					mSelectedImage.remove(mDirPath + "/" + item);
					mSelect.setImageResource(R.drawable.picture_unselected);
					mImageView.setColorFilter(null);
					if(Activity_SelectPhoto.currentCount!=0){
						Activity_SelectPhoto.currentCount--;
					}
				} else
				// 未�?�择该图�?
				{
					if(selectType==1){
						if(Activity_SelectPhoto.currentCount>=3){
							Toast.makeText(context, "亲，只能上传三张图片哦！",Toast.LENGTH_SHORT).show();
						}else{
							mSelectedImage.add(mDirPath + "/" + item);
							mSelect.setImageResource(R.drawable.pictures_selected);
							mImageView.setColorFilter(Color.parseColor("#77000000"));
							Activity_SelectPhoto.currentCount++;
						}
					}else{
						mSelectedImage.clear();
						if(currentSelect!=null){
							currentSelect.setImageResource(R.drawable.picture_unselected);
							currentView.setColorFilter(null);
						}
						mSelectedImage.add(mDirPath + "/" + item);
						mSelect.setImageResource(R.drawable.pictures_selected);
						mImageView.setColorFilter(Color.parseColor("#77000000"));
						currentView=mImageView;
						currentSelect=mSelect;
					}
				}

			}
		});
		
		/**
		 * 已经选择过的图片，显示出选择过的效果
		 */
		if (mSelectedImage.contains(mDirPath + "/" + item))
		{
			mSelect.setImageResource(R.drawable.pictures_selected);
			mImageView.setColorFilter(Color.parseColor("#77000000"));
		}

	}
}
