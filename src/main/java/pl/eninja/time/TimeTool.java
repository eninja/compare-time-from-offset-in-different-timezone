package pl.eninja.time;


public class TimeTool {
    static final int MINUTES_IN_HOUR = 60;
    static final int MINUTES_IN_DAY = 1440;
    private TimeTool() {
    }

    static Either<NumberFormatException, Integer> convertTimeSpanToMinutes(String timeSpan) {
        try {
            float timeOffset = Float.parseFloat(timeSpan);
            double hoursToMinutes = Math.floor(timeOffset) * MINUTES_IN_HOUR;
            float fractionalHourToMinutes = (timeOffset % 1) * MINUTES_IN_HOUR;
            return Either.ofOk((int) (hoursToMinutes + fractionalHourToMinutes));
        } catch (NumberFormatException e) {
            return Either.ofError(e);
        }
    }

    static int futureToToday(int timeInMinutes) {
        if (timeInMinutes > MINUTES_IN_DAY) {
            return futureToToday(timeInMinutes % MINUTES_IN_DAY);
        }
        return timeInMinutes;
    }

    static int normalizeTimeWithinTheDay(int timeInMinutes) {
        return futureToToday(pastToPresentOrFuture(timeInMinutes));
    }

    static int pastToPresentOrFuture(int timeInMinutes) {
        if (timeInMinutes < 0) {
            return pastToPresentOrFuture(timeInMinutes + MINUTES_IN_DAY);
        }
        return timeInMinutes;
    }

    static int calculateTimeDifference(int time1, int time2) {
        int diff = futureToToday(Math.abs(time1 - time2));
        return findSmallestTimeDifference(diff);
    }

    private static int findSmallestTimeDifference(int diff) {
        return Math.min(diff, MINUTES_IN_DAY - diff);
    }
}
