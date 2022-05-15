package com.cronparser;

import com.cronparser.domain.CronApplication;

public class Main {

    private static final CronApplication application = CronApplication.builder().build();

    public static void main(String[] args) {
        /*
        if (args.length != 1) {
            System.err.println("Expected [minute] [hour] [day of month] [day of week] [command] but got :" + Arrays.toString(args));
            return;
        }
        */
        if (args == null || args.length ==0) {
            System.err.println("usage: please provide a valid cron expression");
            return;
        }

        String[] expr = args[0].split("\\s+");

        /*
        if (expr.length != 6) {
            System.err.println("Expected [minute] [hour] [day of month] [day of week] [command] but got :" + args[0]);
            return;
        }
        */

        application.run(expr);
    }
}