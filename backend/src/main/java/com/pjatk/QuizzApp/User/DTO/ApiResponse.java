package com.pjatk.QuizzApp.User.DTO;

public record ApiResponse<T>(String status, String message, T data ) {}
