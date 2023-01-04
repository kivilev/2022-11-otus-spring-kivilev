package org.kivilev.service;

import lombok.AllArgsConstructor;
import org.kivilev.config.PollsProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MessageSourceLocale {

    private final MessageSource messageSource;
    private final PollsProperties pollsProperties;

    public String getMessage(String code, Object[] args) throws NoSuchMessageException {
        return messageSource.getMessage(code, args, pollsProperties.getLocale());
    }

    public String getMessage(String code) throws NoSuchMessageException {
        return messageSource.getMessage(code, new Object[]{}, pollsProperties.getLocale());
    }
}
