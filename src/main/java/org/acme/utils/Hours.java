package org.acme.utils;

public enum Hours {
    EIGHT_AM_TO_FOUR_PM("08:00 AM - 04:00 PM"),
    NINE_AM_TO_FIVE_PM("09:00 AM - 05:00 PM"),
    TEN_AM_TO_SIX_PM("10:00 AM - 06:00 PM"),
    ELEVEN_AM_TO_SEVEN_PM("11:00 AM - 07:00 PM"),
    TWELVE_PM_TO_EIGHT_PM("12:00 PM - 08:00 PM"),
    ONE_PM_TO_NINE_PM("01:00 PM - 09:00 PM"),
    TWO_PM_TO_TEN_PM("02:00 PM - 10:00 PM"),
    THREE_PM_TO_ELEVEN_PM("03:00 PM - 11:00 PM");

    private final String horario;

    Hours(String horario) {
        this.horario = horario;
    }

    public String getHorario() {
        return horario;
    }
}
