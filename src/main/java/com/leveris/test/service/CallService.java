package com.leveris.test.service;

import com.leveris.test.dto.Call;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class CallService {

    @Value("${number.format.default.plus}${number.format.default.code}")
    private String DEFAULT_CODE_WITH_PLUS;
    @Value("${number.format.local.lenght}")
    private Integer LOCAL_LENGTH;
    @Value("${number.format.default.plus}")
    private String DEFAULT_PLUS;
    @Value("${call.date.format}")
    private String TIME_FORMAT;
    @Value("#{${number.format.local.lenght} + ${number.format.code.lenght}}")
    private Integer LOCAL_WITH_CODE_LENGTH;
    @Value("${call.save.directory.path}")
    private String DEFAULT_PATH;

    /**
     * Formats number of {@link Call} object and returns it.
     *
     * @param  call a Call object
     * @return a Call object with formatted number
     */
    public Call formatNumber(Call call) {
        String formatted = call.getNumber()
                .replaceAll("[[^0-9+]]", "")
                .replaceFirst("[+]", "00");
        if (formatted.length() == LOCAL_LENGTH) {
            formatted = DEFAULT_CODE_WITH_PLUS + formatted;
        }
        if (formatted.length() == LOCAL_WITH_CODE_LENGTH) {
            formatted = DEFAULT_PLUS + formatted;
        }
        call.setNumber(formatted);
        return call;
    }

    /**
     * Saves {@link Call} object.
     *
     * @param  call a Call object
     * @return a Call object with formatted number
     */
    public void saveCall(Call call) {
        byte[] strToBytes = call.getNumber().getBytes();
        byte[] separator = System.getProperty("line.separator").getBytes();
        byte[] dateBytes = LocalDateTime.now().format(DateTimeFormatter.ofPattern(TIME_FORMAT)).getBytes();

        StringBuilder filename = new StringBuilder();
        filename.append(DEFAULT_PATH);
        if (!StringUtils.isEmpty(call.getFirstName())) {
            filename.append(call.getFirstName().toUpperCase());
            filename.append("_");
        }
        filename.append(call.getLastName().toUpperCase());
        filename.append(".txt");

        try (BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(filename.toString()))) {
            outputStream.write(strToBytes);
            outputStream.write(separator);
            outputStream.write(dateBytes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
