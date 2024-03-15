package com.smartcut.app.util;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Locale;

@RequiredArgsConstructor
@Component
public final class MessageUtil {

  private final MessageSource messageSource;
  private final Logger loggerClass = LoggerFactory.getLogger(MessageUtil.class);

  public String getMessage(String code) {
    return this.message(code);
  }

  public String getMessage(String code, Object ...arg) {
    return this.message(code, arg);
  }

  private String message(String code,@Nullable Object ...arg) {
    try {
      return messageSource.getMessage(code, arg, Locale.getDefault());
    } catch (NoSuchMessageException e) {
      loggerClass.warn(e.getMessage());
    }
    return "";
  }
}
