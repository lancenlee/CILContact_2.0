package com.example.Util;

import java.io.Serializable;

import android.graphics.Picture;

public class SortModel implements Serializable {

	private String name;
	private String sortLetters;
	private String number;
	private String qq;
	private String birthday;
	private String from;
	private String email;
	private String classhome;
	

	public SortModel(String name, String sortLetters, boolean isChecked,
			String iconUrl, int sex) {
		super();
		this.name = name;
		this.sortLetters = sortLetters;
		this.isChecked = isChecked;
		this.iconUrl = iconUrl;
		this.sex = sex;
	}

	public SortModel() {
		super();
	}
	
	public void setClassHome(String classhome){
		this.classhome = classhome;
	}
	
	public String getClassHome(){
		return classhome;
	}
	
	public void setEmail(String email){
		this.email = email;
	}
	
	public String getEmail(){
		return email;
	}
	public void setQq(String qq){
		this.qq = qq;
	}
	
	public String getQq(){
		return qq;
	}
	
	public void setBirthday(String birthday){
		this.birthday = birthday;
	}
	
	public String getBirthday(){
		return birthday;
	}
	
	public void setFrom(String from){
		this.from = from;
	}
	
	public String getFrom(){
		return from;
	}
	
	public String getNumber(){
		return number;
	}
	
	public void setNumber(String number){
		this.number = number;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	private boolean isChecked;
	private String iconUrl;
	private int sex; // 0 男 1 女
	private int picture;
	
	public void setPicture(int picture){
		this.picture = picture;
	}
	
	public int getPicture(){
		return picture;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSortLetters() {
		return sortLetters;
	}

	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
	}
}
