package com.cronparser.test;


import com.cronparser.domain.*;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static uk.org.webcompere.systemstubs.SystemStubs.tapSystemErrAndOut;

class CronApplicationTest {

    private final CronArgumentsValidator argumentsValidator = mock(CronArgumentsValidator.class);
    private final CronExpressionParser expressionParser = mock(CronExpressionParser.class);
    private final CronResultFormatter formatter = mock(CronResultFormatter.class);

    private final CronApplication app = CronApplication.builder()
            .validator(argumentsValidator)
            .parser(expressionParser)
            .formatter(formatter)
            .build();

    @Test
    void shouldPrintUsageMessageIfInvalidExpressionPassed() throws Exception {
        String expectedMessage = "usage: please provide a cron expression as an argument";
        String[] args = new String[0];
        doThrow(new CronParserException(expectedMessage)).when(argumentsValidator).sanitize(args);

        String output = tapSystemErrAndOut(() -> app.run(args));

        assertThat(output).contains(expectedMessage);
    }

    @Test
    void shouldPrintFormattedResultsIfValidExpressionPassed() throws Exception {
        String[] args = new String[7];
        String[] sanitizedArgs = givenSanitizedArguments(args);
        CronResult result = givenExpressionParsedToResult(sanitizedArgs);
        String expectedFormattedResult = givenExpectedFormattedResult(result);

        String output = tapSystemErrAndOut(() -> app.run(args));

        assertThat(output).contains(expectedFormattedResult);
    }

    private String[] givenSanitizedArguments(String[] args) {
        String[] sanitizedArgs = new String[6];
        given(argumentsValidator.sanitize(args)).willReturn(sanitizedArgs);
        return sanitizedArgs;
    }

    private CronResult givenExpressionParsedToResult(String[] sanitizedArgs) {
        CronResult result = mock(CronResult.class);
        given(expressionParser.parse(sanitizedArgs)).willReturn(result);
        return result;
    }

    private String givenExpectedFormattedResult(CronResult result) {
        String expectedFormattedResult = "formatted-result";
        given(formatter.format(result)).willReturn(expectedFormattedResult);
        return expectedFormattedResult;
    }

}
