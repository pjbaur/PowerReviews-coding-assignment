package com.powerreviews.project.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity(name = "restaurant")
@JsonInclude(Include.NON_NULL)
public class RestaurantEntity extends ResourceSupport {
	//TODO - Use Spring's auto-generated ID.
	
    @Id
    @GeneratedValue
    private Integer restaurantId;

    @Column
    private String name;

    @Column
    private String type;

    @Column
    private String latitude;

    @Column
    private String longitude;
    
    public RestaurantEntity(){}

    public RestaurantEntity(Integer restaurantId, String name, String type, String latitude, String longitude) {
        this.restaurantId = restaurantId;
        this.name = name;
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

	@Override
	public String toString() {
		return String.format("Restaurant [restaurantId=%s, name=%s, type=%s, latitude=%s, longitude=%s]", restaurantId,
				name, type, latitude, longitude);
	}
}