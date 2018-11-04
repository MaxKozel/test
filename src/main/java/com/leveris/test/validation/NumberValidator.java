package com.leveris.test.validation;

import com.leveris.test.dto.Call;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * The class implement additional custom validation after formatting number of {@link Call}
 *
 * @see com.leveris.test.service.CallService
 */
@Component
public class NumberValidator implements Validator {

    @Value("#{${number.format.local.lenght} + ${number.format.code.lenght} + ${number.format.plus.lenght}}")
    private Integer FULL_LENGTH;

    @Override
    public boolean supports(Class<?> paramClass) {
        return Call.class.equals(paramClass);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Call call = (Call) obj;
        if (call.getNumber().length() != 14 || !call.getNumber().startsWith("00")) {
            errors.rejectValue("number", "Format", "Bad format of number");
        }
    }
}