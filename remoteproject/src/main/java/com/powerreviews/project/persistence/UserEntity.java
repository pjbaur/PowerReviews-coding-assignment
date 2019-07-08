package com.powerreviews.project.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity(name = "user")
@JsonIgnoreProperties({ "id" })
public class UserEntity {
	//TODO - Use Spring's auto-generated ID.
    @Id
    @GeneratedValue
    private Integer userId;

    @Column
    private String name;

    public UserEntity(){}

    public UserEntity(String name) {
        this.name = name;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	@Override
	public String toString() {
		return String.format("UserEntity {userId=%s, name=%s}", userId, name);
	}
}
