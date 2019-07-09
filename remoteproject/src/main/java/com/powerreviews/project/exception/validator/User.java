package com.powerreviews.project.exception.validator;

import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = UserValidator.class)
public @interface User {
	 String message() default "User is not allowed to submit reviews.";

	    Class<?>[] groups() default {};
	 
	    Class<? extends Payload>[] payload() default {};
}
