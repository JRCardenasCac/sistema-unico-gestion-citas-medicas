package com.techcompany.sugcm.util.constants;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

import static java.time.DayOfWeek.*;

public class Constants {
    public static final String PROFILE_PATIENT = "PATIENT";
    public static final String PROFILE_DOCTOR = "DOCTOR";
    public static final String PROFILE_ADMINISTRATOR = "ADMINISTRATOR";
    public static final List<DayOfWeek> DAY_OF_WEEK = List.of(MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY);
    public static final LocalTime START_TIME_WORK = LocalTime.of(8, 0);
    public static final LocalTime END_TIME_WORK= LocalTime.of(18, 0);
}
