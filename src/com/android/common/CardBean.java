package com.android.common;

public class CardBean {
	private String title;
	private int card;
	private String txt;

	public CardBean() {

	}

	public CardBean(String title, int card, String txt) {
		super();
		this.title = title;
		this.card = card;
		this.txt = txt;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getCard() {
		return card;
	}

	public void setCard(int card) {
		this.card = card;
	}

	public String getTxt() {
		return txt;
	}

	public void setTxt(String txt) {
		this.txt = txt;
	}

}
