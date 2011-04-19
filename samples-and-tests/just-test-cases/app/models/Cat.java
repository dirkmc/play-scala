package models;

// POJO
public class Cat {
    public Long kittenCount;
    public String name;
    public boolean isNeutered;
    public transient String ignoreMe = "ignore me";
    public static String ignoreMeToo = "ignore me too";

    public Cat(String name, Long kittenCount, boolean isNeutered) {
        this.name = name;
        this.kittenCount = kittenCount;
        this.isNeutered = isNeutered;
    }

}
