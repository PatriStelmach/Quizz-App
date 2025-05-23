package com.pjatk.QuizzApp.email;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EmailTemplateName
{
    ACTIVATE_ACCOUNT("activate account");
    private final String name;

}
