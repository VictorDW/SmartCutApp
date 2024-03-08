package com.smartcut.app.Util;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.lang.Nullable;

import java.util.Locale;

public final class MessageUtil {
  @Getter
  private static MessageSource messageSource;
  private static final Logger loggerClass = LoggerFactory.getLogger(MessageUtil.class);

  private MessageUtil(){}

  public static void loadMessageSource(MessageSource messageSource) {
      MessageUtil.messageSource = messageSource;
  }

  public static String message(String code,@Nullable Object ...arg) {
    try {
      return messageSource.getMessage(code, arg, Locale.getDefault());
    } catch (NoSuchMessageException e) {
      loggerClass.warn(e.getMessage());
    }
    return "";
  }
}
