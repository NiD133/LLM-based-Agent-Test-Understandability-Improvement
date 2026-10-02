package org.jsoup.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TagSetTest_canParseWithGeneralCustomization {

    /**
     * Registers an {@code onNewTag} customizer that makes every unknown tag self-closing,
     * then verifies the customization is applied during parsing:
     * <ul>
     *   <li>{@code <custom-data />} is unknown, so it is treated as self-closing and serializes empty.</li>
     *   <li>{@code <script />} is a known tag, so the customizer leaves it alone and it consumes the
     *       following text as its content.</li>
     * </ul>
     */
    @Test
    void canParseWithGeneralCustomization() {
        Parser parser = Parser.htmlParser();
        parser.tagSet().onNewTag(tag -> {
            if (!tag.isKnownTag())
                tag.set(Tag.SelfClose);
        });

        Document doc = Jsoup.parse("<custom-data />Bar <script />Text", parser);

        assertEquals("<custom-data></custom-data>Bar\n<script>Text</script>", doc.body().html());
    }
}
