package com.leveris.test.controller;

import com.leveris.test.dto.Call;
import com.leveris.test.facade.CallFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class CallController {

    @Autowired
    private CallFacade callFacade;

    @RequestMapping(value = "/addCall", method = RequestMethod.POST)
    public Object addCall(@RequestBody @Valid Call call, BeanPropertyBindingResult errors) throws NoSuchMethodException, MethodArgumentNotValidException {
        if (!callFacade.handleCall(call, errors)) {
            throw new MethodArgumentNotValidException(
                    new MethodParameter(this.getClass().getDeclaredMethod("addCall", Call.class, BeanPropertyBindingResult.class), 0), errors);
        }
        return ResponseEntity.ok().body(call);
    }

}
