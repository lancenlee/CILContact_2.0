package com.example.View;

import com.example.Util.SortModel;
import com.example.sortlistview.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PeopleShow extends Activity{
	
	private TextView tvName,tvSex,tvClassHome,tvQQ,tvNumber,tvPhone,tvFrom,tvBirthday,tvEmail;
	private SortModel model;
	private ImageView imgPicture;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.showpeople);
		initView();
		initData();
	}
	public void initView(){
		imgPicture = (ImageView) findViewById(R.id.img_peopleshow);
		tvName = (TextView) findViewById(R.id.tv_name);
		tvSex = (TextView) findViewById(R.id.tv_sex);
		tvClassHome = (TextView) findViewById(R.id.tv_classhome);
		tvQQ = (TextView) findViewById(R.id.tv_qq);
		tvPhone = (TextView) findViewById(R.id.tv_number);
		tvFrom = (TextView) findViewById(R.id.tv_from);
		tvBirthday = (TextView) findViewById(R.id.tv_birthday);
		tvEmail = (TextView) findViewById(R.id.tv_email);
	}
	
	public void initData(){
		model = (SortModel) getIntent().getSerializableExtra("model");
		imgPicture.setImageResource(model.getPicture());
		tvName.setText(model.getName());
		int x = model.getSex();
		if(x==0){
		  tvSex.setText("男");
		}else{
			tvSex.setText("女");
		}
		tvClassHome.setText(model.getClassHome());
		tvQQ.setText(model.getQq());
		tvPhone.setText(model.getNumber());
		tvFrom.setText(model.getFrom());
		if(model.getBirthday().equals("")){
			tvBirthday.setVisibility(View.GONE);
		}else{
			tvBirthday.setVisibility(View.VISIBLE);
			tvBirthday.setText(model.getBirthday());
		}
		//tvBirthday.setText(model.getBirthday());
		if(model.getEmail().equals("")){
			tvEmail.setVisibility(View.GONE);
		}else{
			tvEmail.setVisibility(View.VISIBLE);
			tvEmail.setText(model.getEmail());
		}
		//tvEmail.setText(model.getEmail());
		//Toast.makeText(this, model.getNumber().toString(), Toast.LENGTH_SHORT).show();
	}
	
}
