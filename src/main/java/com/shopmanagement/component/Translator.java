package com.shopmanagement.component;

import com.shopmanagement.util.StringUtil;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;


@Component
public class Translator {

    private static ResourceBundleMessageSource messageSource;
    private static ResourceBundleMessageSource logMessageSource;

    public Translator(ResourceBundleMessageSource messageSource, ResourceBundleMessageSource logMessageSource) {
        Translator.messageSource = messageSource;
        Translator.logMessageSource = logMessageSource;
    }

    public static String getMessage(String msgCode) {
        return messageSource.getMessage(msgCode, null, LocaleContextHolder.getLocale());
    }

    public static String getMessageWithParam(String msgCode, String... params) {
        return StringUtil.normalizeMessage(messageSource.getMessage(msgCode, params, LocaleContextHolder.getLocale()));
    }

    public static String getLogMessage(String logCode) {
        return logMessageSource.getMessage(logCode, null, Locale.US);
    }

    public static String getLogMessageWithParam(String logCode, String... params) {
        return StringUtil.normalizeMessage(logMessageSource.getMessage(logCode, params, Locale.US));
    }
}
