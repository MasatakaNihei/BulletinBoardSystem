package beans;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import validator.LengthValidatorAnotation;

public class ContributionBean {
	private String id;
	
	@LengthValidatorAnotation(max = 50, message = "タイトルは50文字以内です。")
	@NotNull(message = "タイトルが入力されていません。")
	@Pattern(regexp = ".*[^\\s|　]+.*", message = "空白のみのタイトルは無効です。")
	private String title;
	
	@LengthValidatorAnotation(max = 1000, message = "本文は1000文字以内です。")
	@NotNull(message = "本文が入力されていません。")
	@Pattern(regexp = ".*[^\\s|　]+.*", message = "空白のみの本文は無効です。")
	private String text;
	
	@LengthValidatorAnotation(max = 10, message = "カテゴリ名は10文字以内です。")
	@NotNull(message = "カテゴリが入力されていません。")
	@Pattern(regexp = ".*[^\\s|　]+.*", message = "空白のみのカテゴリは無効です。")
	private String category;
	
	private String userId;
	private String userName;
	private String userBranchId;
	private String userBranchName;
	private String userPositionId;
	private String userPositionName;
	private Date createdAt;
	private Date updatedAt;
	private String isDeleted;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserBranchId() {
		return userBranchId;
	}
	public void setUserBranchId(String userBranchId) {
		this.userBranchId = userBranchId;
	}
	public String getUserBranchName() {
		return userBranchName;
	}
	public void setUserBranchName(String userBranchName) {
		this.userBranchName = userBranchName;
	}
	public String getUserPositionId() {
		return userPositionId;
	}
	public void setUserPositionId(String userPositionId) {
		this.userPositionId = userPositionId;
	}
	public String getUserPositionName() {
		return userPositionName;
	}
	public void setUserPositionName(String userPositionName) {
		this.userPositionName = userPositionName;
	}

	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	public String getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}
	

}
