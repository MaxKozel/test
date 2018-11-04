package com.leveris.test;

import com.leveris.test.dto.Call;
import com.leveris.test.service.CallService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:test.properties")
public class ServiceTests {

    @Autowired
    private CallService callService;

    @Value("${call.save.directory.path}")
    private String DIRECTORY_PATH;

    @Value("${call.date.format}")
    private String TIME_FORMAT;
    @Test
    public void format() {
        Call call = new Call();
        Map<String,String> validSet = new HashMap();
        validSet.put("123456789","00420123456789");
        validSet.put("1(23)4-5-6-7-8-9","00420123456789");
        validSet.put("1()2(34), 567-89","00420123456789");
        validSet.put("1(234),,,56-789","00420123456789");
        validSet.put("375123456789","00375123456789");
        validSet.put("+ 375 12 345 67-89","00375123456789");
        validSet.put("(375)(123)(456)  789","00375123456789");

        for(String number : validSet.keySet()){
            call.setNumber(number);
            callService.formatNumber(call);
            assert(call.getNumber().equals(validSet.get(number)));
        }
    }

    @Test
    public void save() throws IOException {
        Call call = new Call();
        call.setFirstName("Maksim");
        call.setLastName("Kozel");
        call.setNumber("00375123456789");


        callService.saveCall(call);
        BufferedReader file = new BufferedReader(new FileReader(DIRECTORY_PATH + "MAKSIM_KOZEL.txt"));
        assert(file.readLine().equals("00375123456789"));
        try {
            LocalTime.parse(file.readLine(), DateTimeFormatter.ofPattern(TIME_FORMAT));
            assert(true);
        } catch (Exception e){
            assert(false);
        }
    }
}
