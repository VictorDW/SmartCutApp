package com.smartcut.app.Util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class  {

  @Autowired
  public (MessageSource messageSource) {
    MessageUtil.loadMessageSource(messageSource);
  }

  public String getMessage(String code) {
    return MessageUtil.message(code);
  }

  public String getMessage(String code, Object ...arg) {
    return MessageUtil.message(code, arg);
  }
}
