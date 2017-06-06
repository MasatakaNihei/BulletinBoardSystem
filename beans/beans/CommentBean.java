package beans;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import validator.LengthValidatorAnotation;

public class CommentBean {
	private String Id;
	
	@LengthValidatorAnotation(min =0, max = 500, message = "コメントは500文字以内です。")
	@NotNull(message = "コメントが入力されていません。")
	@Pattern(regexp = ".*[^\\s|　]+.*", message = "空白のみのコメントは無効です。")
	private String text;
	
	private String contributionId;
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
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getContributionId() {
		return contributionId;
	}
	public void setContributionId(String contributionId) {
		this.contributionId = contributionId;
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
