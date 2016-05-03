package com.android.common;

public class HomeNews {
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImagefile() {
		return imagefile;
	}

	public void setImagefile(String imagefile) {
		this.imagefile = imagefile;
	}

	private int id;
	private String description;
	private String imagefile;
	private String num;

	public HomeNews(int id, String description, String imagefile) {
		// TODO Auto-generated constructor stub
		super();
		this.id = id;
		this.description = description;
		this.imagefile = imagefile;
	}

	public HomeNews() {
		super();
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	@Override
	public String toString() {
		return "HomeNews [id=" + id + ", description=" + description + ", imagefile=" + imagefile + ", num=" + num
				+ "]";
	}

}
