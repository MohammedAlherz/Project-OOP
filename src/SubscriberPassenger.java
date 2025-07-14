public class SubscriberPassenger extends Passenger{

    //empty constructor
    public SubscriberPassenger() {}
    //constructor with parameters
    public SubscriberPassenger(String name, String id, Car car, double tripCost) {
        super(name, id, car, tripCost);
    }
    @Override
    public void reservedCar(Car car) {
        if(car.getMaxCapacity() == 0) {
            throw new IllegalArgumentException("Car is full, cannot reserve.");
        }else {

            double discount = car.getRoute().getPrice() * 0.5; // 50% discount
            setTripCost(car.getRoute().getPrice() - discount);
        }
    }

    @Override
    public void displayInfo() {
        System.out.println("Subscriber Passenger Information:");
        System.out.println("Name: " + getName());
        System.out.println("ID: " + getId());
        System.out.println("Reserved Car Code: " + getReservedCar().getCode());
        System.out.println("Trip Cost: " + getTripCost());
        System.out.println("Route Start Address: " + getReservedCar().getRoute().getStartAddress());
        System.out.println("Route Destination Address: " + getReservedCar().getRoute().getDestinationAddress());
    }
}
