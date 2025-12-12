package sunshine.domain;

import java.util.Arrays;
import java.util.Optional;

public enum City {
    SEOUL("Seoul", "서울", 37.5665, 126.9780),
    TOKYO("Tokyo", "도쿄", 35.6762, 139.6503),
    NEW_YORK("NewYork", "뉴욕", 40.7128, -74.0060),
    PARIS("Paris", "파리", 48.8566, 2.3522),
    LONDON("London", "런던", 51.5074, -0.1278);

    private final String name;
    private final String koreanName;
    private final double latitude;
    private final double longitude;

    City(String name, String koreanName, double latitude, double longitude) {
        this.name = name;
        this.koreanName = koreanName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public String getKoreanName() {
        return koreanName;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public static Optional<City> fromName(String name) {
        return Arrays.stream(values())
                .filter(city -> city.name.equalsIgnoreCase(name))
                .findFirst();
    }
}
