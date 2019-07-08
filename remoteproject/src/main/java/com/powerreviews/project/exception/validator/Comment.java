package com.powerreviews.project.exception.validator;

import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = CommentValidator.class)
public @interface Comment {
	 String message() default "Comment is not allowed.";

	    Class<?>[] groups() default {};
	 
	    Class<? extends Payload>[] payload() default {};
}
