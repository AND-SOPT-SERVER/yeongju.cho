package org.sopt.week1;

import org.sopt.week1.Main.UI.InvalidInputException;
import org.sopt.week1.Main.UI.DiaryBodyLengthException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DiaryValidator {
    public static void validate(final String body){
        if(body.trim().isEmpty()){
            throw new InvalidInputException();
        }
        if(body.length() > 30){
            throw new DiaryBodyLengthException();
        }
    }

    public static int countGraphemeClusters(String body) {
        String regex = "\\X";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(body);

        int count = 0;
        while (matcher.find()) {
            count++;
        }
        return count;
    }
}