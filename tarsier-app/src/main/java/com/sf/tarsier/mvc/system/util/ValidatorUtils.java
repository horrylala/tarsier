package com.sf.tarsier.mvc.system.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.collections.CollectionUtils;

public class ValidatorUtils {
	private static final Validator VALIDATOR;

	private ValidatorUtils() {}

	static {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		VALIDATOR = factory.getValidator();
	}

	/**
	 * 校验对象属性是否合法
	 * 例：List<ValidateResult> list = ValidatorUtils.validate(dto);
		if (CollectionUtils.isNotEmpty(list)) {
			return ResultUtil.error(list.get(0).getMessage());
		}
	 * 
	 * @param t 需校验的类实例
	 * @param groups 校验组
	 * @return
	 */
	public static <T> List<ValidateResult> validate(T t, Class<?>... groups) {
		Set<ConstraintViolation<T>> set = VALIDATOR.validate(t, groups);
		return getValidtionList(set);
	}

	private static <T> List<ValidateResult> getValidtionList(Set<ConstraintViolation<T>> set) {
		if (CollectionUtils.isNotEmpty(set)) {
			List<ValidateResult> results = new ArrayList<>();
			for (ConstraintViolation<T> constraintViolation : set) {
				String field = constraintViolation.getPropertyPath().toString();
				String message = constraintViolation.getMessage();
				results.add(new ValidateResult(field, message));
			}
			return results;
		}
		return Collections.emptyList();
	}

}
