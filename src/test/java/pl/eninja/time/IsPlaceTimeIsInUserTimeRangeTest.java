package pl.eninja.time;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.eninja.time.IsPlaceTimeIsInUserTimeRange.check;


class IsPlaceTimeIsInUserTimeRangeTest {
    @ParameterizedTest
    @MethodSource("providePlaceTimeIsCloseEnoughToUserTime")
    void placeTimeIsCloseEnoughToUserTime(String userTimeSpan, String placeTimeSpan, String timeSpanStart, String timeSpanEnd) {
        assertTrue(check(
                userTimeSpan, placeTimeSpan, timeSpanStart, timeSpanEnd
        ));
    }

    private static Stream<Arguments> providePlaceTimeIsCloseEnoughToUserTime() {
        return Stream.of(
                Arguments.of("0.0", "0.0", "0", "0"),
                Arguments.of("0.0", "0.0", "-12", "12"),
                Arguments.of("-12.0", "12.0", "0", "0"),
                Arguments.of("12.0", "-12.0", "0", "0"),
                Arguments.of("5.0", "7.0", "0", "2.0"),
                Arguments.of("-5.0", "2.0", "0", "7.0"),
                Arguments.of("-5.0", "-6.0", "-1.0", "0"),
                Arguments.of("-14.0", "12.0", "0", "2.0"),
                Arguments.of("0.0", "0.0", "-1.0", "1.0"),
                Arguments.of("0.0", "1.0", "0", "1.0"),
                Arguments.of("0.0", "-1.0", "-1.0", "0"),
                Arguments.of("12.0", "13.0", "0", "1.0"),
                Arguments.of("-12.0", "-11.0", "0", "1.0"),
                Arguments.of("1.0", "3.0", "0", "2.0"),
                Arguments.of("0.0", "-23.0", "0", "1.0"),
                Arguments.of("0.0", "23.0", "-1.0", "0"),
                Arguments.of("0.0", "0.0", "-24", "0"),
                Arguments.of("0.0", "-5.0", "-7", "-5"),
                Arguments.of("-1.0", "-6.0", "-7", "-5"),
                Arguments.of("1.0", "-4.0", "-7", "-5"),
                Arguments.of("0.0", "5.0", "5", "7"),
                Arguments.of("1.0", "6.0", "5", "7"),
                Arguments.of("-1.0", "4.0", "5", "7")
                );
    }

    @ParameterizedTest
    @MethodSource("providePlaceTimeIsNotCloseEnoughToUserTime")
    void placeTimeIsNotCloseEnoughToUserTime(String userTimeSpan, String placeTimeSpan, String timeSpanStart, String timeSpanEnd) {
        assertFalse(check(
                userTimeSpan, placeTimeSpan, timeSpanStart, timeSpanEnd
        ));
    }

    private static Stream<Arguments> providePlaceTimeIsNotCloseEnoughToUserTime() {
        return Stream.of(
                Arguments.of("0.0", "1.0", "0", "0"),
                Arguments.of("0.0", "-1.0", "0", "0"),
                Arguments.of("1.0", "-1.0", "0", "0"),
                Arguments.of("1.0", "2.0", "0", "0"),
                Arguments.of("0.0", "8.0", "-7", "7"),
                Arguments.of("1.0", "10.0", "-7", "7"),
                Arguments.of("1.0", "-10.0", "-7", "7"),
                Arguments.of("-1.0", "-10.0", "-7", "7"),
                Arguments.of("-1.0", "10.0", "-7", "7")
        );
    }
}
