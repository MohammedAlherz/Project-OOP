public class Car {
    private String code;
    private Route route;
    private int maxCapacity;

    //empty constructor
    public Car() {}
    //constructor with parameters
    public Car(String code, Route route, int maxCapacity) {
        this.code = code;
        this.route = route;
        this.maxCapacity = maxCapacity;
    }
    //getters and setters
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public Route getRoute() {
        return route;
    }
    public void setRoute(Route route) {
        this.route = route;
    }
    public int getMaxCapacity() {
        return maxCapacity;
    }
    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public void decreaseCapacity() {
        if (maxCapacity > 0) {
            maxCapacity--;
        } else {
            System.out.println("Capacity is already at zero.");
        }
    }

    @Override
    public String toString() {
        return "Car{" +
                "code='" + code + '\'' +
                ", route=" + route +
                ", maxCapacity=" + maxCapacity +
                '}';
    }
}
