package com.cronparser.test;

import com.cronparser.domain.CronArgumentsValidator;
import com.cronparser.domain.CronParserException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class CronArgumentsValidatorTest {

    private static final String USAGE_MESSAGE = "usage: please provide a valid cron expression";

    private final CronArgumentsValidator sanitizer = new CronArgumentsValidator();

    @Test
    void shouldThrowExceptionWithUsageMessageIfNoArgumentsPassed() {
        String[] args = new String[0];

        Throwable error = catchThrowable(() -> sanitizer.sanitize(args));

        assertThat(error).isInstanceOf(CronParserException.class)
                .hasMessage(USAGE_MESSAGE);
    }

    @Test
    void shouldThrowExceptionWithInvalidMessageIfLessThanSixArgumentsPassed() {
        String[] args = new String[5];

        Throwable error = catchThrowable(() -> sanitizer.sanitize(args));

        assertThat(error).isInstanceOf(CronParserException.class)
                .hasMessage(toExpectedErrorMessage(args));
    }

    @Test
    void shouldReturnArgsIfSixArgumentsPassed() {
        String[] args = new String[6];

        String[] sanitized = sanitizer.sanitize(args);

        assertThat(sanitized).isEqualTo(args);
    }

    @Test
    void shouldReturnLastSixArgsIfMoreThanSixArgumentsPassed() {
        String[] args = new String[]{"1", "2", "3", "4", "5", "6", "7"};

        String[] sanitized = sanitizer.sanitize(args);

        assertThat(sanitized).contains("2", "3", "4", "5", "6", "7");
    }

    private static String toExpectedErrorMessage(String[] args) {
        String expression = String.join(" ", args);
        return String.format("%s, invalid cron expression provided %s", USAGE_MESSAGE, expression);
    }

}
