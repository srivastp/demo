package com.example.demo.services;

import com.example.demo.config.DialPadMapperProperties;
import com.example.demo.exceptions.InvalidParameterException;
import com.example.demo.models.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class MainService {
    private final DialPadMapperProperties dialPadMapper;

    @Autowired
    public MainService(DialPadMapperProperties dialPadMapper) {
        this.dialPadMapper = dialPadMapper;
    }

    public Result getPhoneCombinations(String aPhoneNumber, int page, int size) throws InvalidParameterException {
        validateArgs(aPhoneNumber, page, size);
        List<String> combos = process(aPhoneNumber.trim().split(""));
        int start = (page * size) > combos.size() ? combos.size() - size : (page * size);
        int end = (start + size) > combos.size() ? combos.size() : (start + size);
        return new Result(combos.size(), combos.subList(start, end));
    }

    private List<String> process(String[] c) {
        HashMap<String, List<String>> mapper = dialPadMapper.getData();
        if (c.length == 1) {
            List aList = mapper.get(c[0]);
            aList.add(c[0]);
            return aList;
        } else {
            String symbol = c[0];
            List<String> options = mapper.get(symbol) != null ? mapper.get(symbol) : new ArrayList();
            options.add(symbol);
            List<String> z = process(Arrays.copyOfRange(c, 1, c.length));
            return z.stream()
                    .map(x -> concatData(x, options))
                    .flatMap(Collection::stream)
                    .distinct()
                    .collect(Collectors.toList());
        }
    }

    private List<String> concatData(String x, List<String> o) {
        return o.stream()
                .map(y -> y + x)
                .collect(Collectors.toList());
    }

    private void validateArgs(String aPhoneNumber, int page, int size) throws InvalidParameterException {
        validateForNotNull(aPhoneNumber);
        validateInput(aPhoneNumber);
        validateForNonNegative(page, size);
    }

    private void validateForNonNegative(int... n) throws InvalidParameterException {
        for (int i = 0; i < n.length; i++) {
            if (n[i] < 0)
                throw new InvalidParameterException("Input cannot be negative");
        }
    }

    private void validateForNotNull(String aPhoneNumber) {
        if (aPhoneNumber == null || aPhoneNumber.trim().length() == 0)
            throw new NullPointerException("PhoneNumber input cannot be null or empty");
    }

    private boolean validateInput(String input) throws InvalidParameterException {
        input = input.replaceAll("\\s", "");
        Pattern pattern7 = Pattern.compile("^\\d{10}$");
        Matcher matcher7 = pattern7.matcher(input);
        Pattern pattern10 = Pattern.compile("^\\d{7}$");
        Matcher matcher10 = pattern10.matcher(input);
        if (!matcher7.matches() && !matcher10.matches())
            throw new InvalidParameterException("Invalid input");
        return true;
    }
}
