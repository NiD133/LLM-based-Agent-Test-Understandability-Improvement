package org.jsoup.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import java.util.regex.Pattern;
import static org.junit.jupiter.api.Assertions.*;

public class TokenQueueTest_testQuotedPattern {

    /**
     * Verifies that CSS :matches() selectors handle special regex characters
     * correctly when the pattern is produced by Pattern.quote().
     *
     * The three cases exercise different positions of the special characters:
     *   1. A backslash-escaped closing paren at the start: "\) foo1"
     *   2. A raw opening paren at the start: "( foo2"
     *   3. A digit followed by a closing paren: "1) foo3"
     *
     * Pattern.quote() wraps the literal in \Q...\E so that the regex engine
     * treats every character as a literal, including '(' and ')'.
     */
    @Test
    public void testQuotedPattern() {
        // Arrange: document whose divs each contain a text with a special character
        String html = "<div>\\) foo1</div><div>( foo2</div><div>1) foo3</div>";
        Document doc = Jsoup.parse(html);

        // Build quoted patterns that treat the special characters as literals
        String patternBackslashParen = Pattern.quote("\\)");  // matches literal "\)"
        String patternOpenParen      = Pattern.quote("(");    // matches literal "("
        String patternDigitParen     = Pattern.quote("1)");   // matches literal "1)"

        // Act & Assert: each selector must return the div whose text matches the quoted pattern
        String textMatchingBackslashParen = doc.select("div:matches(" + patternBackslashParen + ")").get(0).childNode(0).toString();
        assertEquals("\\) foo1", textMatchingBackslashParen);

        String textMatchingOpenParen = doc.select("div:matches(" + patternOpenParen + ")").get(0).childNode(0).toString();
        assertEquals("( foo2", textMatchingOpenParen);

        String textMatchingDigitParen = doc.select("div:matches(" + patternDigitParen + ")").get(0).childNode(0).toString();
        assertEquals("1) foo3", textMatchingDigitParen);
    }
}
