public class NonSubscriberPassenger extends Passenger{
    private boolean hasDiscountCoupon;

    //empty constructor
    public NonSubscriberPassenger() {}
    //constructor with parameters
    public NonSubscriberPassenger(String name, String id, Car reservedCar, double tripCost, boolean hasDiscountCoupon) {
        super(name, id, reservedCar, tripCost);
        this.hasDiscountCoupon = hasDiscountCoupon;
    }

    //setters and getters
    public boolean isHasDiscountCoupon() {
        return hasDiscountCoupon;
    }
    public void setHasDiscountCoupon(boolean hasDiscountCoupon) {
        this.hasDiscountCoupon = hasDiscountCoupon;
    }


    @Override
    public void reservedCar(Car car) {
        if(car.getMaxCapacity() <= 0) {
            throw new IllegalArgumentException("Car is full, cannot reserve.");
        }

        if(hasDiscountCoupon){
            double discount = car.getRoute().getPrice() * 0.1; // 10% discount
            double finalPrice = car.getRoute().getPrice() - discount;
            setTripCost(finalPrice);
        } else {
            System.out.println("No discount coupon available. The price is: " + car.getRoute().getPrice());
        }
    }

    @Override
    public void displayInfo() {
        System.out.println("Non-Subscriber Passenger Information:");
        System.out.println("Name: " + getName());
        System.out.println("ID: " + getId());
        System.out.println("Reserved Car Code: " + getReservedCar().getCode());
        System.out.println("Trip Cost: " + getTripCost());
        System.out.println("Route Start Address: " + getReservedCar().getRoute().getStartAddress());
        System.out.println("Route Destination Address: " + getReservedCar().getRoute().getDestinationAddress());
        System.out.println("Has Discount Coupon: " + ((hasDiscountCoupon)? "Yes 10%" : "No"));
    }
}
