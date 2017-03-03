package com.example.Adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.Util.SortModel;
import com.example.sortlistview.R;
import com.example.sortlistview.R.drawable;
import com.example.sortlistview.R.id;
import com.example.sortlistview.R.layout;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.Toast;

/**
 *  适配器
 */
public class SortAdapter extends BaseAdapter implements SectionIndexer {
	private List<SortModel> list = null;
	private Context mContext;
	private boolean isNeedCheck;
	private SortModel model;
	private String number;

	public boolean isNeedCheck() {
		return isNeedCheck;
	}

	public void setNeedCheck(boolean isNeedCheck) {
		this.isNeedCheck = isNeedCheck;
	}

	public SortAdapter(Context mContext, List<SortModel> list) {
		this.mContext = mContext;
		this.list = list;
	}

	public void updateListView(List<SortModel> list,List<SortModel> list1) {
		/*this.list = list;
		notifyDataSetChanged();*/
		if(!list.isEmpty()){
		this.list = list;
		notifyDataSetChanged();
		}else{
			Toast.makeText(mContext, "很抱歉，没有找到该用户！", Toast.LENGTH_SHORT).show();
		}
	}

	public int getCount() {
		return this.list.size();
	}

	public Object getItem(int position) {
		return list.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View view, ViewGroup arg2) {
		ViewHolder viewHolder = null;
		final SortModel mContent = list.get(position);
		if (view == null) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(mContext).inflate(R.layout.item, null);
			viewHolder.tvTitle = (TextView) view
					.findViewById(R.id.tv_user_item_name);
			viewHolder.tvLetter = (TextView) view.findViewById(R.id.catalog);
			viewHolder.checked = (ImageView) view
					.findViewById(R.id.iv_user_item_check);
			viewHolder.icon = (ImageView) view
					.findViewById(R.id.iv_user_item_icon);
			viewHolder.sex = (ImageView) view
					.findViewById(R.id.iv_user_item_sex);
			viewHolder.tvNumber = (TextView) view.findViewById(R.id.number_tv);
			viewHolder.callPhone = (ImageButton) view.findViewById(R.id.ibtncallPhone);
			viewHolder.sendMessage = (ImageButton) view.findViewById(R.id.ibtnsendMessage);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}

		int section = getSectionForPosition(position);

		if (position == getPositionForSection(section)) {
			viewHolder.tvLetter.setVisibility(View.VISIBLE);
			viewHolder.tvLetter.setText(mContent.getSortLetters());
		} else {
			viewHolder.tvLetter.setVisibility(View.GONE);
		}
		model = list.get(position);
		if (isNeedCheck) {
			viewHolder.checked.setVisibility(View.VISIBLE);
			if (model.isChecked())
				viewHolder.checked.setImageResource(R.drawable.round_checked);
			else
				viewHolder.checked.setImageResource(R.drawable.round_unchecked);
		} else {
			viewHolder.checked.setVisibility(View.GONE);
		}

		number = model.getNumber();
		viewHolder.icon.setImageResource(model.getPicture());
		viewHolder.tvNumber.setText(number);
		viewHolder.tvTitle.setText(model.getName());
		// 头像采用imageloader加载
		viewHolder.sex.setImageResource(model.getSex() == 0 ? R.drawable.boy
				: R.drawable.girl);
		
		viewHolder.callPhone.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				model = list.get(position);
				Intent callIntent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+model.getNumber().toString()));
				
				mContext.startActivity(callIntent);
			}
		});
		
		viewHolder.sendMessage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				model = list.get(position);
				Intent sendMessageIntent = new Intent(Intent.ACTION_SENDTO,Uri.parse("smsto:"+model.getNumber()));
				mContext.startActivity(sendMessageIntent);
			}
		});
		return view;

	}

	final static class ViewHolder {
		TextView tvLetter;
		TextView tvTitle;
		TextView tvNumber;
		ImageButton callPhone,sendMessage;
		ImageView checked, icon, sex;
	}

	/**
	 * 得到首字母的ascii值
	 */
	public int getSectionForPosition(int position) {
		return list.get(position).getSortLetters().charAt(0);
	}

	public int getPositionForSection(int section) {
		for (int i = 0; i < getCount(); i++) {
			String sortStr = list.get(i).getSortLetters();
			char firstChar = sortStr.toUpperCase().charAt(0);
			if (firstChar == section) {
				return i;
			}
		}

		return -1;
	}

	public String getAlpha(String str) {
		String sortStr = str.trim().substring(0, 1).toUpperCase();
		if (sortStr.matches("[A-Z]")) {
			return sortStr;
		} else {
			return "#";
		}
	}

	@Override
	public Object[] getSections() {
		return null;
	}
}