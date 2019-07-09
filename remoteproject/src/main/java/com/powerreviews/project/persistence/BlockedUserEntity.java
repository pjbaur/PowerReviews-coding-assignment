package com.powerreviews.project.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity(name = "blockeduser")
@JsonIgnoreProperties({ "id" })
public class BlockedUserEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer blockedUserId;

    @Column
    private Integer userId;
    
    public BlockedUserEntity(){}
    
    public BlockedUserEntity(Integer userId){
      this.userId = userId;
    }

    public Integer getBlockedUserId() {
        return blockedUserId;
    }
    
	public Integer getUserId() {
      return userId;
    }

    public void setUserId(Integer userId) {
      this.userId = userId;
    }

  @Override
	public String toString() {
		return String.format("UserEntity {blockedUserId=%s, userId=%s}",
		    blockedUserId, userId);
	}
}