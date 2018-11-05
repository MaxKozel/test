package com.leveris.test.facade;

import com.leveris.test.dto.Call;
import com.leveris.test.service.CallService;
import com.leveris.test.validation.NumberValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;

@Component
public class CallFacade {

    @Autowired
    private CallService callService;
    @Autowired
    private NumberValidator numberValidator;

    /**
     * Formats number and validates it. Valid number being saved
     *
     * @param call   {@link Call} object needs to be handled
     * @param errors an {@link BeanPropertyBindingResult} object
     * @return true in case when handled successfully, otherwise returns false
     */
    public boolean handleCall(Call call, BeanPropertyBindingResult errors) {
        callService.formatNumber(call);
        numberValidator.validate(call, errors);
        if (errors.getErrorCount() == 0) {
            callService.saveCall(call);
            return true;
        }
        return false;
    }
}
