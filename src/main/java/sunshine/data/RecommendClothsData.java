package sunshine.data;

public record RecommendClothsData(
        String forYoung,
        String forMiddle,
        String forElder
) {
    public static RecommendClothsData empty() {
        return new RecommendClothsData("", "", "");
    }
}
