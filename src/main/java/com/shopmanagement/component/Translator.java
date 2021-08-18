package com.shopmanagement.component;

import com.shopmanagement.util.StringUtil;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;


@Component
public class Translator {

    private static ResourceBundleMessageSource messageSource;

    public Translator(ResourceBundleMessageSource messageSource) {
        Translator.messageSource = messageSource;
    }

    public static String getMessage(String msgCode) {
        return messageSource.getMessage(msgCode, null, LocaleContextHolder.getLocale());
    }

    public static String getMessageWithParam(String msgCode, String... params) {
        return StringUtil.normalizeMessage(messageSource.getMessage(msgCode, params, LocaleContextHolder.getLocale()));
    }
}
