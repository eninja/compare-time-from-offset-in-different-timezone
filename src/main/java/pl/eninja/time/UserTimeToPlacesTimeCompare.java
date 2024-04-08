package pl.eninja.time;

import static pl.eninja.time.TimeTool.*;

public class UserTimeToPlacesTimeCompare {

    private UserTimeToPlacesTimeCompare() {
    }

    public static int compare(String userTimeSpan, String place1TimeSpan, String place2TimeSpan) {
        var input = checkInput(userTimeSpan, place1TimeSpan, place2TimeSpan);
        if (input.isError()) return input.getError();
        var result = tryConvertTimeSpansToTime(userTimeSpan, place1TimeSpan, place2TimeSpan);
        if (result.isError()) return result.getError();
        var results = result.getOk();

        int userTime = normalizeTimeWithinTheDay(results.userTime.getOk());
        int place1Time = normalizeTimeWithinTheDay(results.place1Time.getOk());
        int place2Time = normalizeTimeWithinTheDay(results.place2Time.getOk());

        int timeDiffBetweenUserAndPlace1 = calculateTimeDifference(userTime, place1Time);
        int timeDiffBetweenUserAndPlace2 = calculateTimeDifference(userTime, place2Time);

        return Integer.compare(timeDiffBetweenUserAndPlace1, timeDiffBetweenUserAndPlace2);
    }

    private static Either<Integer, Boolean> checkInput(String userTimeSpan, String place1TimeSpan, String place2TimeSpan) {
        if (userTimeSpan == null) return Either.ofError(0);
        if (place1TimeSpan == null && place2TimeSpan == null) return Either.ofError(0);
        if (place1TimeSpan == null) return Either.ofError(1);
        if (place2TimeSpan == null) return Either.ofError(-1);
        return Either.ofOk(Boolean.TRUE);
    }

    private static Either<Integer, Result> tryConvertTimeSpansToTime(String userTimeSpan, String place1TimeSpan, String place2TimeSpan) {
        var userTimeSpanToMinutes = convertTimeSpanToMinutes(userTimeSpan);
        var place1TimeSpanToMinutes = convertTimeSpanToMinutes(place1TimeSpan);
        var place2TimeSpanToMinutes = convertTimeSpanToMinutes(place2TimeSpan);
        if (userTimeSpanToMinutes.isError() || place1TimeSpanToMinutes.isError() || place2TimeSpanToMinutes.isError()) {
            return Either.ofError(0);
        }
        return Either.ofOk(
                new Result(userTimeSpanToMinutes, place1TimeSpanToMinutes, place2TimeSpanToMinutes)
        );
    }

    private record Result(
            Either<NumberFormatException, Integer> userTime,
            Either<NumberFormatException, Integer> place1Time,
            Either<NumberFormatException, Integer> place2Time
    ) {
    }
}