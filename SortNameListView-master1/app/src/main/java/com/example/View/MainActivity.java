package com.example.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Adapter.SortAdapter;
import com.example.Util.DataContact;
import com.example.Util.SortModel;
import com.example.sortlistview.CharacterParser;
import com.example.sortlistview.PinyinComparator;
import com.example.sortlistview.R;
import com.example.sortlistview.SideBar;
import com.example.sortlistview.R.array;
import com.example.sortlistview.R.id;
import com.example.sortlistview.R.layout;
import com.example.sortlistview.SideBar.OnTouchingLetterChangedListener;

public class MainActivity extends Activity implements OnClickListener {
	private ListView sortListView;
	private SideBar sideBar; // 右边的引导
	private TextView dialog;
	private EditText mClearEditText;
	private SortAdapter adapter; // 排序的适配器
	private SortModel model;

	private CharacterParser characterParser;
	private List<SortModel> SourceDateList; // 数据

	private PinyinComparator pinyinComparator;
	private LinearLayout xuanfuLayout; // 顶部悬浮的layout
	private TextView xuanfaText, QunFa; // 悬浮的文字， 和右上角的群发
	private int lastFirstVisibleItem = -1;
	private boolean isNeedChecked; // 是否需要出现选择的按钮
	private TextView number;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initViews();
	}

	private void initViews() {
		characterParser = CharacterParser.getInstance();

		pinyinComparator = new PinyinComparator();
		xuanfuLayout = (LinearLayout) findViewById(R.id.top_layout);
		xuanfaText = (TextView) findViewById(R.id.top_char);
		sideBar = (SideBar) findViewById(R.id.sidrbar);
		dialog = (TextView) findViewById(R.id.dialog);
		QunFa = (TextView) findViewById(R.id.qunfa);
		mClearEditText = (EditText) findViewById(R.id.filter_edit);
		QunFa.setOnClickListener(this);
		sideBar.setTextView(dialog);

		mClearEditText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				filterData(s.toString());
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
		
		/**
		 * 为右边添加触摸事件
		 */
		sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {

			@Override
			public void onTouchingLetterChanged(String s) {
				int position = adapter.getPositionForSection(s.charAt(0));
				if (position != -1) {
					sortListView.setSelection(position);
				}

			}
		});

		sortListView = (ListView) findViewById(R.id.country_lvcountry);
		sortListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				if (!isNeedChecked) {
					model = new SortModel();
					model = SourceDateList.get(position);
					//Toast.makeText(MainActivity.this, model.getName(), Toast.LENGTH_SHORT).show();
					Intent peopleShowIntent = new Intent(MainActivity.this,PeopleShow.class);
					peopleShowIntent.putExtra("model", model);
					startActivity(peopleShowIntent);
				} else {
					SourceDateList.get(position).setChecked(
							!SourceDateList.get(position).isChecked());
					adapter.notifyDataSetChanged(); // 这样写效率很低， 以后可以改成
													// RecycleView 直接notify
													// item的状态
				}

			}

		});

		//SourceDateList = filledData(getResources().getStringArray(R.array.date));// 填充数据
		SourceDateList = filledData(DataContact.getPeoples());
		
		Collections.sort(SourceDateList, pinyinComparator);
		adapter = new SortAdapter(this, SourceDateList);
		sortListView.setAdapter(adapter);

		/**
		 * 设置滚动监听， 实时跟新悬浮的字母的值
		 */
		sortListView.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				int section = adapter.getSectionForPosition(firstVisibleItem);
				int nextSecPosition = adapter
						.getPositionForSection(section + 1);
				if (firstVisibleItem != lastFirstVisibleItem) {
					MarginLayoutParams params = (MarginLayoutParams) xuanfuLayout
							.getLayoutParams();
					params.topMargin = 0;
					xuanfuLayout.setLayoutParams(params);
					xuanfaText.setText(String.valueOf((char) section));
				}
				if (nextSecPosition == firstVisibleItem + 1) {
					View childView = view.getChildAt(0);
					if (childView != null) {
						int titleHeight = xuanfuLayout.getHeight();
						int bottom = childView.getBottom();
						MarginLayoutParams params = (MarginLayoutParams) xuanfuLayout
								.getLayoutParams();
						if (bottom < titleHeight) {
							float pushedDistance = bottom - titleHeight;
							params.topMargin = (int) pushedDistance;
							xuanfuLayout.setLayoutParams(params);
						} else {
							if (params.topMargin != 0) {
								params.topMargin = 0;
								xuanfuLayout.setLayoutParams(params);
							}
						}
					}
				}
				lastFirstVisibleItem = firstVisibleItem;
			}
		});

	}

	/**
	 * 填充数据
	 * 
	 * @param date
	 * @return
	 */
	private List<SortModel> filledData(String[] date) {
		List<SortModel> mSortList = new ArrayList<SortModel>();
		String[] Froms = DataContact.getFroms();
		String[] QQs = DataContact.getQqs();
		String[] ClassHomes = DataContact.getClassHomes();
		String[] Birthdays = DataContact.getBirthdays();
		String[] Emails = DataContact.getEmails();
		
		int[] Sexs = DataContact.getSexs();
		int[] Pictures = DataContact.getPictures();
		String[] Numbers = DataContact.getNumbers();
		for (int i = 0; i < date.length; i++) {
			SortModel sortModel = new SortModel();
			sortModel.setName(date[i]);
			sortModel.setSex(Sexs[i]);
			sortModel.setNumber(Numbers[i]);
			sortModel.setPicture(Pictures[i]);
			sortModel.setQq(QQs[i]);
			sortModel.setClassHome(ClassHomes[i]);
			sortModel.setFrom(Froms[i]);
			sortModel.setBirthday(Birthdays[i]);
			sortModel.setEmail(Emails[i]);
			String pinyin = characterParser.getSelling(date[i]);
			String sortString = pinyin.substring(0, 1).toUpperCase();

			if (sortString.matches("[A-Z]")) {
				sortModel.setSortLetters(sortString.toUpperCase());
			} else {
				sortModel.setSortLetters("#");
			}

			mSortList.add(sortModel);
		}
		return mSortList;

	}

	/**
	 * 过滤数据
	 * @param filterStr
	 */
	private void filterData(String filterStr) {
		List<SortModel> filterDateList = new ArrayList<SortModel>();
		filterStr = filterStr.toLowerCase();
		if (TextUtils.isEmpty(filterStr)) {
			filterDateList = SourceDateList;
		} else {
			filterDateList.clear();
			for (SortModel sortModel : SourceDateList) {
				String name = sortModel.getName();
				if (name.indexOf(filterStr.toString()) != -1
						|| characterParser.getSelling(name).startsWith(
								filterStr.toString())) {
					//Log.d("SorctionAdapter", characterParser.getSelling(name));
					filterDateList.add(sortModel);
				}
			}
		}

		Collections.sort(filterDateList, pinyinComparator);
		adapter.updateListView(filterDateList,SourceDateList);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.qunfa:
			if (isNeedChecked) {
				adapter.setNeedCheck(false);
				isNeedChecked = false;
			} else {

				adapter.setNeedCheck(true);
				isNeedChecked = true;
			}
			adapter.notifyDataSetChanged();
			break;

		default:
			break;
		}

	}

}
