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

public class CommentValidator implements ConstraintValidator<Comment, String> {

	org.slf4j.Logger logger = LoggerFactory.getLogger(CommentValidator.class);
	
	//TODO - Put these words into a properties file or a database
	List<String> forbiddenWords = Arrays.asList("lit", "hella", "chill", "bro");
	
	List<String> getWordList(String string) {
		String reduced = string.toLowerCase().replaceAll("[^\\s\\w]", "");

		List<String> wordList = Splitter
				.on(CharMatcher.breakingWhitespace())
				.trimResults()
				.trimResults(CharMatcher.anyOf(""))
				.omitEmptyStrings()
				.splitToList(reduced);
		
		logger.info("" + wordList);
		
		return wordList;
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		List<String> commentWordList = getWordList(value);

		return !commentWordList
				.stream()
				.anyMatch(forbiddenWords::contains);
	}
}