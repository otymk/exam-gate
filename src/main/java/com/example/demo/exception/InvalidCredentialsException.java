package com.example.demo.exception;

public class InvalidCredentialsException extends RuntimeException {
	public InvalidCredentialsException() {
		super("認証情報が正しくありません");
	}
}
