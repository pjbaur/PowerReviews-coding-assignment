package com.powerreviews.project.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "user")
public class UserEntity {
	//TODO - We want the Id to be assigned automatically. Delete the Setter and update Constructor.
    @Id
    private Integer id;

    @Column
    private String name;

    public UserEntity(){}

    public UserEntity(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
    	// TODO - Do I want a { or a [ ?
        return "UserEntity{" +
                "id=" + id +
                "name='" + name + '\'' +
                '}';
    }
}
