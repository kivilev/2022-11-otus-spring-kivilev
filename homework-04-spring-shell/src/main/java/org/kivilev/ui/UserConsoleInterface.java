package org.kivilev.ui;

import lombok.RequiredArgsConstructor;
import org.kivilev.service.MessageSourceLocale;
import org.kivilev.service.OutputStreamData;
import org.kivilev.service.PollApplicationService;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

@ShellComponent
@RequiredArgsConstructor
public class UserConsoleInterface {
    private boolean isAgreedToUseTestingSystem;

    private final OutputStreamData outputStreamData;
    private final PollApplicationService pollApplicationService;
    private final MessageSourceLocale messageSourceLocale;

    @ShellMethod(value = "Info about testing system", key = {"ab", "about"})
    public void about() {
        outputStreamData.println("Homework 04. Author: Kivilev DS.");
    }

    @ShellMethod(value = "Run testing system", key = {"r", "run"})
    @ShellMethodAvailability("isRunAccessible")
    public void run() {
        pollApplicationService.run();
    }

    @ShellMethod(value = "Agree for using testing system", key = {"a", "agree"})
    public void agree() {
        isAgreedToUseTestingSystem = true;
        outputStreamData.println(messageSourceLocale.getMessage("uci.agree"));
    }

    public Availability isRunAccessible() {
        return isAgreedToUseTestingSystem ? Availability.available() : Availability.unavailable(messageSourceLocale.getMessage("uci.not.permit"));
    }
}
