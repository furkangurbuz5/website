package com.gurbuz.website.exceptions;

public class ProductNotFoundException extends RuntimeException{
        public ProductNotFoundException(String s) {
                super(s);
        }
}
