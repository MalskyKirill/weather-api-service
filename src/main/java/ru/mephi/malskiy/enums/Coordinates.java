package ru.mephi.malskiy.enums;

public enum Coordinates {
    MSС("lat=55.75&lon=37.62", "MSC"),
    SPB("lat=59.57&lon=30.19", "SPB");
    private final String location;
    private final String code;

    Coordinates(String location, String code) {
        this.location = location;
        this.code = code;
    }

    public String getLocation() {
        return location;
    }

    public static Coordinates findByCode(String code) {
        for (Coordinates coordinate : values()) {
            if (coordinate.code.equals(code)) {
                return coordinate;
            }
        }

        return null;
    }


}
