package com.pjatk.QuizzApp.Exceptions;

import lombok.Data;

@Data
public class CustomException
{
    private int code;
    private String message;
}
