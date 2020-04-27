package lexicon;

public class City {
    private int id;
    private String name;
    private String code;
    private String district;
    private int population;

    public City(int id, String name, String code, String district, int population) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.district = district;
        this.population = population;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getDistrict() {
        return district;
    }

    public int getPopulation() {
        return population;
    }
}
