package com.leveris.test;

import com.leveris.test.dto.Call;
import com.leveris.test.facade.CallFacade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.BeanPropertyBindingResult;


@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:test.properties")
public class FacadeTest {

    @Autowired
    private CallFacade callFacade;

    @Test
    public void withValidationError() throws Exception {
        Call call = new Call();
        call.setLastName("Kozel");
        call.setNumber("1234567890");

        BeanPropertyBindingResult bpbr = new BeanPropertyBindingResult(call,"call");
        callFacade.handleCall(call, bpbr);

        assert(bpbr.getErrorCount() > 0);
    }

    @Test
    public void withValidationError2() throws Exception {
        Call call = new Call();
        call.setLastName("Kozel");
        call.setNumber("-13752923456789");

        BeanPropertyBindingResult bpbr = new BeanPropertyBindingResult(call,"call");
        callFacade.handleCall(call, bpbr);

        assert(bpbr.getErrorCount() > 0);
    }

    @Test
    public void withoutValidationError() throws Exception {
        Call call = new Call();
        call.setLastName("Kozel");
        call.setNumber("123456789");

        BeanPropertyBindingResult bpbr = new BeanPropertyBindingResult(call,"call");
        callFacade.handleCall(call, bpbr);

        assert(bpbr.getErrorCount() == 0);
    }

}
