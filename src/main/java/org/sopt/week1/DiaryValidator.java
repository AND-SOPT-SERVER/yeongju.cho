package org.sopt.week1;

import org.sopt.week1.Main.UI.InvalidInputException;
import org.sopt.week1.Main.UI.DiaryBodyLengthException;

public class DiaryValidator {
    public static void validate(final String body){
        if(body.trim().isEmpty()){
            throw new InvalidInputException();
        }
        if(body.length() >= 30){
            throw new DiaryBodyLengthException();
        }
    }
}