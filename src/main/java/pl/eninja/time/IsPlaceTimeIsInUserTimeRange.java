package pl.eninja.time;

public class IsPlaceTimeIsInUserTimeRange {

    private IsPlaceTimeIsInUserTimeRange() {
    }

    public static boolean check(String userTimeSpan, String placeTimeSpan, String timeSpanStart, String timeSpanEnd) {
        var input = checkInput(userTimeSpan, placeTimeSpan, timeSpanStart, timeSpanEnd);
        if (input.isError()) return input.getError();
        var result = tryConvertTimeSpansToTime(userTimeSpan, placeTimeSpan, timeSpanStart, timeSpanEnd);
        if (result.isError()) return result.getError();

        var results = result.getOk();
        int userTimeInMinutes = TimeTool.normalizeTimeWithinTheDay(results.userTime.getOk());
        int placeTimeInMinutes = TimeTool.normalizeTimeWithinTheDay(results.placeTime.getOk());
        int lowerBound = results.timeSpanStart.getOk() == 0 ? userTimeInMinutes : TimeTool.futureToToday((userTimeInMinutes + results.timeSpanStart.getOk() + TimeTool.MINUTES_IN_DAY));
        int upperBound = results.timeSpanEnd.getOk() == 0 ? userTimeInMinutes : TimeTool.futureToToday((userTimeInMinutes + results.timeSpanEnd.getOk() + TimeTool.MINUTES_IN_DAY));

        if (lowerBound == userTimeInMinutes && upperBound == userTimeInMinutes) {
            return placeTimeInMinutes == userTimeInMinutes;
        } else if (lowerBound < upperBound) {
            return placeTimeInMinutes >= lowerBound && placeTimeInMinutes <= upperBound;
        } else if (lowerBound > upperBound) {
            return placeTimeInMinutes >= lowerBound || placeTimeInMinutes <= upperBound;
        } else {
            return true;
        }
    }

    private static Either<Boolean, Boolean> checkInput(String userTimeSpan, String placeTimeSpan, String timeSpanStart, String timeSpanEnd) {
        if (userTimeSpan == null || timeSpanStart == null || timeSpanEnd == null) return Either.ofError(true);
        if (placeTimeSpan == null) return Either.ofError(false);
        return Either.ofOk(true);
    }

    private static Either<Boolean, Result> tryConvertTimeSpansToTime(String userTimeSpan, String placeTimeSpan, String timeSpanStart, String timeSpanEnd) {
        var userTimeInMinutes = TimeTool.convertTimeSpanToMinutes(userTimeSpan);
        var placeTimeInMinutes = TimeTool.convertTimeSpanToMinutes(placeTimeSpan);
        var timeSpanStartMinutes = TimeTool.convertTimeSpanToMinutes(timeSpanStart);
        var timeSpanEndMinutes = TimeTool.convertTimeSpanToMinutes(timeSpanEnd);
        if (userTimeInMinutes.isError() || placeTimeInMinutes.isError() || timeSpanStartMinutes.isError() || timeSpanEndMinutes.isError()) {
            return Either.ofError(false);
        }
        return Either.ofOk(
                new Result(userTimeInMinutes, placeTimeInMinutes, timeSpanStartMinutes, timeSpanEndMinutes)
        );
    }

    private record Result(
            Either<NumberFormatException, Integer> userTime,
            Either<NumberFormatException, Integer> placeTime,
            Either<NumberFormatException, Integer> timeSpanStart,
            Either<NumberFormatException, Integer> timeSpanEnd
    ) {
    }
}
