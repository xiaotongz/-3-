package com.zjweu.service;

import javax.mail.MessagingException;

public interface IMailService {


    Boolean sendTextMail(String tos, String subject, String content) throws MessagingException;

    void checkMail(String receiveEmail, String subject, String emailMsg);
}
