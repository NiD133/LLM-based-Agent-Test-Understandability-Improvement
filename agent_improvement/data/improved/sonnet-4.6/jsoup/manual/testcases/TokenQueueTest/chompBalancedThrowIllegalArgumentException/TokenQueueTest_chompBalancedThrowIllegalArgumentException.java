package org.jsoup.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TokenQueueTest_chompBalancedThrowIllegalArgumentException {

    /**
     * chompBalanced throws IllegalArgumentException when the close marker does not match
     * the open marker, leaving the queue positioned after "unbalanced".
     *
     * Input  : "unbalanced(something(or another)) else"
     * consumeTo("(") drains "unbalanced", leaving "(something(or another)) else".
     * chompBalanced('(', '+') looks for '+' as the closer, never finds a balanced one,
     * and must report the unmatched content in its error message.
     */
    @Test
    public void chompBalancedThrowIllegalArgumentException() {
        TokenQueue tq = new TokenQueue("unbalanced(something(or another)) else");
        tq.consumeTo("(");

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> tq.chompBalanced('(', '+')
        );

        assertEquals(
            "Did not find balanced marker at 'something(or another)) else'",
            exception.getMessage()
        );
    }
}
