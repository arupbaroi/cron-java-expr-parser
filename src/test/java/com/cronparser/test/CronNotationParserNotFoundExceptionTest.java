package com.cronparser.test;


import com.cronparser.domain.CronNotationParserNotFoundException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CronNotationParserNotFoundExceptionTest {

    @Test
    void shouldReturnMessage() {
        String input = "my-input";

        Throwable error = new CronNotationParserNotFoundException(input);

        assertThat(error.getMessage()).isEqualTo("notation parser not found for value my-input");
    }

}

