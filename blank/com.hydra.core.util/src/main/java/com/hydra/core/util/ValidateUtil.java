package com.hydra.core.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;

import com.hydra.core.util.pojo.ValidationResult;

public class ValidateUtil {
    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    public static <T> ValidationResult validate(T object) {
        ValidationResult res = new ValidationResult();
        Set<ConstraintViolation<T>> set = VALIDATOR.validate(object, Default.class);
        if (set != null && set.size() > 0) {
            res.setHasErrors(true);
            Map<String, String> errs = MapUtil.getHashMap();
            for (ConstraintViolation<T> cv : set) {
                errs.put(BeanUtil.toString(cv.getPropertyPath()), cv.getMessage());
            }
            res.setErrorMsg(errs);
        }
        return res;
    }

    public static <T> ValidationResult validate(T object, String propertyName) {
        ValidationResult res = new ValidationResult();
        Set<ConstraintViolation<T>> set = VALIDATOR.validateProperty(object, propertyName, Default.class);
        if (set != null && set.size() > 0) {
            res.setHasErrors(true);
            Map<String, String> errs = MapUtil.getHashMap();
            List<String> list = new ArrayList<String>();
            for (ConstraintViolation<T> cv : set) {
                list.add(cv.getMessage());
            }
            errs.put(propertyName, BeanUtil.toString(list));
            res.setErrorMsg(errs);
        }
        return res;
    }

    private ValidateUtil() {
        throw new UnsupportedOperationException();
    }
}
