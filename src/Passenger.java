public abstract class Passenger {
    private String name;
    private String Id;
    private Car reservedCar;;
    private double tripCost;

    //empty constructor
    public Passenger() {}
    //constructor with parameters
    public Passenger(String name, String Id, Car reservedCar, double tripCost) {
        this.name = name;
        this.Id = Id;
        this.reservedCar = reservedCar;
        this.tripCost = tripCost;
    }

    //getters and setters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getId() {
        return Id;
    }
    public void setId(String id) {
        Id = id;
    }
    public Car getReservedCar() {
        return reservedCar;
    }
    public void setReservedCar(Car reservedCar) {
        this.reservedCar = reservedCar;
    }
    public double getTripCost() {
        return tripCost;
    }
    public void setTripCost(double tripCost) {
        this.tripCost = tripCost;
    }
    public abstract void reservedCar(Car car);
    public abstract void displayInfo();

}
