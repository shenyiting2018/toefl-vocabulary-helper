package com.yiting.toeflvoc.beans;

public class UserStatsBean {
	
	private int userId;
	
	private String fullName;

	private int level0Count;
	
	private int level1Count;
	
	private int level2Count;
	
	private int level3Count;
	
	private int level4Count;

	private int proficiencyPercentage;

	public UserStatsBean(int userId, String fullName, int level0Count, int level1Count, int level2Count,
			int level3Count, int level4Count, int proficiencyPercentage) {
		super();
		this.userId = userId;
		this.fullName = fullName;
		this.level0Count = level0Count;
		this.level1Count = level1Count;
		this.level2Count = level2Count;
		this.level3Count = level3Count;
		this.level4Count = level4Count;
		this.proficiencyPercentage = proficiencyPercentage;
	}

	public UserStatsBean() {
		super();
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public int getLevel0Count() {
		return level0Count;
	}

	public void setLevel0Count(int level0Count) {
		this.level0Count = level0Count;
	}

	public int getLevel1Count() {
		return level1Count;
	}

	public void setLevel1Count(int level1Count) {
		this.level1Count = level1Count;
	}

	public int getLevel2Count() {
		return level2Count;
	}

	public void setLevel2Count(int level2Count) {
		this.level2Count = level2Count;
	}

	public int getLevel3Count() {
		return level3Count;
	}

	public void setLevel3Count(int level3Count) {
		this.level3Count = level3Count;
	}

	public int getLevel4Count() {
		return level4Count;
	}

	public void setLevel4Count(int level4Count) {
		this.level4Count = level4Count;
	}

	public int getProficiencyPercentage() {
		return proficiencyPercentage;
	}

	public void setProficiencyPercentage(int proficiencyPercentage) {
		this.proficiencyPercentage = proficiencyPercentage;
	}

}
