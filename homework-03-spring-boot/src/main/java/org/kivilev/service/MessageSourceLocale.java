package org.kivilev.service;

import org.kivilev.config.PollsProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;

@Component
public class MessageSourceLocale {

    private final MessageSource messageSource;
    private final PollsProperties pollsProperties;

    public MessageSourceLocale(MessageSource messageSource, PollsProperties pollsProperties) {
        this.messageSource = messageSource;
        this.pollsProperties = pollsProperties;
    }

    public String getMessage(String code, Object[] args) throws NoSuchMessageException {
        return messageSource.getMessage(code, args, pollsProperties.getLocale());
    }
}
