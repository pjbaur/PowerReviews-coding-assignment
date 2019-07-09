package com.powerreviews.project.exception.validator;

import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.LoggerFactory;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterators;
import com.powerreviews.project.controller.RestaurantController;

public class UserValidator implements ConstraintValidator<Comment, Integer> {

	org.slf4j.Logger logger = LoggerFactory.getLogger(UserValidator.class);
	
	//TODO - This is a stub -- Put these words into a properties file or a database
	List<Integer> getBlockedUserList() {
		return Arrays.asList(3, 4);
	}

	@Override
	public boolean isValid(Integer userId, ConstraintValidatorContext context) {

		return !getBlockedUserList().contains(userId);
	}
}