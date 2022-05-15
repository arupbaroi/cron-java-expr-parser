package com.cronparser.domain;

import lombok.Builder;

@Builder
public class CronApplication {

    @Builder.Default
    private final CronArgumentsValidator validator = new CronArgumentsValidator();

    @Builder.Default
    private final CronExpressionParser parser = new CronExpressionParser();

    @Builder.Default
    private final CronResultFormatter formatter = new CronResultFormatter();

    public void run(String[] args) {
        try {
            String[] sanitizedArgs = validator.sanitize(args);
            CronResult result = parser.parse(sanitizedArgs);
            print(result);
        } catch (CronParserException e) {
            printErrorMessage(e);
        }
    }

    private void print(CronResult result) {
        System.out.println(formatter.format(result));
    }

    private void printErrorMessage(Throwable e) {
        System.err.println(e.getMessage());
    }

}
