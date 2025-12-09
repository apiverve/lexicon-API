// Converter.java

// To use this code, add the following Maven dependency to your project:
//
//
//     com.fasterxml.jackson.core     : jackson-databind          : 2.9.0
//     com.fasterxml.jackson.datatype : jackson-datatype-jsr310   : 2.9.0
//
// Import this package:
//
//     import com.apiverve.data.Converter;
//
// Then you can deserialize a JSON string with
//
//     WordLexiconAnalyzerData data = Converter.fromJsonString(jsonString);

package com.apiverve.lexicon.data;

import java.io.IOException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

public class Converter {
    // Date-time helpers

    private static final DateTimeFormatter DATE_TIME_FORMATTER = new DateTimeFormatterBuilder()
            .appendOptional(DateTimeFormatter.ISO_DATE_TIME)
            .appendOptional(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
            .appendOptional(DateTimeFormatter.ISO_INSTANT)
            .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SX"))
            .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssX"))
            .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            .toFormatter()
            .withZone(ZoneOffset.UTC);

    public static OffsetDateTime parseDateTimeString(String str) {
        return ZonedDateTime.from(Converter.DATE_TIME_FORMATTER.parse(str)).toOffsetDateTime();
    }

    private static final DateTimeFormatter TIME_FORMATTER = new DateTimeFormatterBuilder()
            .appendOptional(DateTimeFormatter.ISO_TIME)
            .appendOptional(DateTimeFormatter.ISO_OFFSET_TIME)
            .parseDefaulting(ChronoField.YEAR, 2020)
            .parseDefaulting(ChronoField.MONTH_OF_YEAR, 1)
            .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
            .toFormatter()
            .withZone(ZoneOffset.UTC);

    public static OffsetTime parseTimeString(String str) {
        return ZonedDateTime.from(Converter.TIME_FORMATTER.parse(str)).toOffsetDateTime().toOffsetTime();
    }
    // Serialize/deserialize helpers

    public static WordLexiconAnalyzerData fromJsonString(String json) throws IOException {
        return getObjectReader().readValue(json);
    }

    public static String toJsonString(WordLexiconAnalyzerData obj) throws JsonProcessingException {
        return getObjectWriter().writeValueAsString(obj);
    }

    private static ObjectReader reader;
    private static ObjectWriter writer;

    private static void instantiateMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        SimpleModule module = new SimpleModule();
        module.addDeserializer(OffsetDateTime.class, new JsonDeserializer<OffsetDateTime>() {
            @Override
            public OffsetDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
                String value = jsonParser.getText();
                return Converter.parseDateTimeString(value);
            }
        });
        mapper.registerModule(module);
        reader = mapper.readerFor(WordLexiconAnalyzerData.class);
        writer = mapper.writerFor(WordLexiconAnalyzerData.class);
    }

    private static ObjectReader getObjectReader() {
        if (reader == null) instantiateMapper();
        return reader;
    }

    private static ObjectWriter getObjectWriter() {
        if (writer == null) instantiateMapper();
        return writer;
    }
}

// WordLexiconAnalyzerData.java

package com.apiverve.lexicon.data;

import com.fasterxml.jackson.annotation.*;

public class WordLexiconAnalyzerData {
    private String word;
    private boolean isAlphagram;
    private String sortedForm;
    private boolean isPalindrome;
    private boolean hasAnagram;
    private boolean isIsogram;
    private boolean isPangramCandidate;
    private boolean isScrabbleValid;
    private boolean canBePalindromeAnagram;

    @JsonProperty("word")
    public String getWord() { return word; }
    @JsonProperty("word")
    public void setWord(String value) { this.word = value; }

    @JsonProperty("isAlphagram")
    public boolean getIsAlphagram() { return isAlphagram; }
    @JsonProperty("isAlphagram")
    public void setIsAlphagram(boolean value) { this.isAlphagram = value; }

    @JsonProperty("sortedForm")
    public String getSortedForm() { return sortedForm; }
    @JsonProperty("sortedForm")
    public void setSortedForm(String value) { this.sortedForm = value; }

    @JsonProperty("isPalindrome")
    public boolean getIsPalindrome() { return isPalindrome; }
    @JsonProperty("isPalindrome")
    public void setIsPalindrome(boolean value) { this.isPalindrome = value; }

    @JsonProperty("hasAnagram")
    public boolean getHasAnagram() { return hasAnagram; }
    @JsonProperty("hasAnagram")
    public void setHasAnagram(boolean value) { this.hasAnagram = value; }

    @JsonProperty("isIsogram")
    public boolean getIsIsogram() { return isIsogram; }
    @JsonProperty("isIsogram")
    public void setIsIsogram(boolean value) { this.isIsogram = value; }

    @JsonProperty("isPangramCandidate")
    public boolean getIsPangramCandidate() { return isPangramCandidate; }
    @JsonProperty("isPangramCandidate")
    public void setIsPangramCandidate(boolean value) { this.isPangramCandidate = value; }

    @JsonProperty("isScrabbleValid")
    public boolean getIsScrabbleValid() { return isScrabbleValid; }
    @JsonProperty("isScrabbleValid")
    public void setIsScrabbleValid(boolean value) { this.isScrabbleValid = value; }

    @JsonProperty("canBePalindromeAnagram")
    public boolean getCanBePalindromeAnagram() { return canBePalindromeAnagram; }
    @JsonProperty("canBePalindromeAnagram")
    public void setCanBePalindromeAnagram(boolean value) { this.canBePalindromeAnagram = value; }
}