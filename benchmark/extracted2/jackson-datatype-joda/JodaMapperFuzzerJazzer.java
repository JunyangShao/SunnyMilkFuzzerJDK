import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaMapper;
import org.joda.time.*;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.time.Period;
import java.time.YearMonth;
import java.util.*;

public class JodaMapperFuzzerJazzer {

    public static SerializationFeature[] serializationFeatures = new SerializationFeature[] { SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS, SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS, SerializationFeature.WRITE_DATES_WITH_CONTEXT_TIME_ZONE, SerializationFeature.WRITE_DATES_WITH_ZONE_ID };

    public static Class[] classes = { DummyClass.class, DateTimeZone.class, Date.class, DateTime.class, Duration.class, Instant.class, LocalDateTime.class, LocalDate.class, LocalTime.class, Period.class, ReadablePeriod.class, ReadableDateTime.class, ReadableInstant.class, Interval.class, MonthDay.class, YearMonth.class };

    public static void fuzzerTestOneInput(FuzzedDataProvider data) {
        JodaMapper jodaMapper = new JodaMapper();
        jodaMapper.setWriteDatesAsTimestamps(data.consumeBoolean());
        List<SerializationFeature> pickedValues = data.pickValues(serializationFeatures, data.consumeInt(0, serializationFeatures.length));
        for (SerializationFeature feature : pickedValues) {
            jodaMapper.enable(feature);
        }
        ObjectReader reader = jodaMapper.readerFor(data.pickValue(classes));
        try {
            reader.readValue(data.consumeRemainingAsString());
        } catch (JsonProcessingException | IllegalArgumentException | ArithmeticException e) {
        }
    }

    public static class DummyClass {

        public Date date;

        public TimeZone timeZone;

        public Calendar calendar;

        public Locale locale;

        public Duration duration;

        public LocalDateTime localDateTime;

        public LocalDate localDate;

        public LocalTime localTime;

        public Period period;

        public ReadablePeriod readablePeriod;

        public ReadableDateTime readableDateTime;

        public ReadableInstant readableInstant;

        public Interval instant;

        public MonthDay monthDay;

        public YearMonth yearMonth;

        public DateTimeZone dateTimeZone;
    }
}
