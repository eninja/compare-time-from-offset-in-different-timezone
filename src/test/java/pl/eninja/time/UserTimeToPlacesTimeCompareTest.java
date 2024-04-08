package pl.eninja.time;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.eninja.time.UserTimeToPlacesTimeCompare.compare;


class UserTimeToPlacesTimeCompareTest {

    @ParameterizedTest
    @MethodSource("provideTimeSpansForBothPlacesAndUserResultFirstPlaceDiffSmaller")
    void firstPlaceTimeSpanIsCloserToUserTimeSpan(String timeSpan1, String timeSpan2, String userTimeSpan) {
        assertEquals(-1, compare(
                        userTimeSpan, timeSpan1, timeSpan2
                )
        );
    }

    private static Stream<Arguments> provideTimeSpansForBothPlacesAndUserResultFirstPlaceDiffSmaller() {
        return Stream.of(
                Arguments.of("5.0", "7.0", "-1.0"),
                Arguments.of("5.0", "7.0", "1.0"),
                Arguments.of("5.0", "7.0", "0.0"),

                Arguments.of("-5.0", "-7.0", "-1.0"),
                Arguments.of("-5.0", "-7.0", "1.0"),
                Arguments.of("-5.0", "-7.0", "0.0"),

                Arguments.of("2.0", "-7.0", "-1.0"),
                Arguments.of("2.0", "-7.0", "1.0"),
                Arguments.of("2.0", "-7.0", "0.0"),

                Arguments.of("-2.0", "7.0", "-1.0"),
                Arguments.of("-2.0", "7.0", "1.0"),
                Arguments.of("-2.0", "7.0", "0.0"),

                Arguments.of("13.0", "-9.0", "9.0")
        );
    }

    @ParameterizedTest
    @MethodSource("provideTimeSpansForBothPlacesAndUserResultEqualDiff")
    void bothPlaceTimeSpanIsCloseToUserTimeSpan(String timeSpan1, String timeSpan2, String userTimeSpan) {
        assertEquals(0, compare(
                        userTimeSpan, timeSpan1, timeSpan2
                )
        );
    }

    private static Stream<Arguments> provideTimeSpansForBothPlacesAndUserResultEqualDiff() {
        return Stream.of(
                Arguments.of("-11.0", "13.0", "9.0"),
                Arguments.of("-12.0", "12.0", "0.0"),
                Arguments.of("-14.0", "10.0", "12.0")
        );
    }

    @ParameterizedTest
    @MethodSource("provideInvalidTimeSpansResultEqualDiff")
    void invalidTimeSpansResultEqualTimeDifference(String timeSpan1, String timeSpan2, String userTimeSpan, String userTimeSpan2) {
        assertEquals(0, compare(
                        userTimeSpan, timeSpan1, timeSpan2
                )
        );
    }

    private static Stream<Arguments> provideInvalidTimeSpansResultEqualDiff() {
        return Stream.of(
                Arguments.of("0", "0", "0", "1"),
                Arguments.of("0", "0", "1", "0"),
                Arguments.of("0", "0", null, "0"),
                Arguments.of("0", "0", "0", null),
                Arguments.of("0", "0", null, null),
                Arguments.of(null, null, "0", "0")
        );
    }

    @ParameterizedTest
    @MethodSource("provideInvalidTimeSpansResultFirstPlaceIsCloserToUser")
    void invalidTimeSpansResultFirstDcIsCloserToUser(String timeSpan1, String timeSpan2, String userTimeSpan, String userTimeSpan2) {
        assertEquals(-1, compare(
                        userTimeSpan, timeSpan1, timeSpan2
                )
        );
    }

    private static Stream<Arguments> provideInvalidTimeSpansResultFirstPlaceIsCloserToUser() {
        return Stream.of(
                Arguments.of("0", null, "0", "0")
        );
    }
    @ParameterizedTest
    @MethodSource("provideInvalidTimeSpansResultSecondPlaceIsCloserToUser")
    void invalidTimeSpansResultSecondPlaceIsCloserToUser(String timeSpan1, String timeSpan2, String userTimeSpan, String userTimeSpan2) {
        assertEquals(1, compare(
                        userTimeSpan, timeSpan1, timeSpan2
                )
        );
    }

    private static Stream<Arguments> provideInvalidTimeSpansResultSecondPlaceIsCloserToUser() {
        return Stream.of(
                Arguments.of(null, "0", "0", "0")
        );
    }

}