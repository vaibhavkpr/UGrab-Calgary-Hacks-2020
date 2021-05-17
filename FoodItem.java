public class FoodItem {
    private String name;
    private double price;
    private String type;
    private String storeName;
    private String restriction;
    private String image = "images/burger.png";

    public FoodItem(String name, double price, String type, String storeName, String restriction) {
        this.name = name;
        this.price = price;
        this.type = type;
        this.storeName = storeName;
        this.restriction = restriction;
    }

    public FoodItem(String name, double price, String type, String storeName, String restriction, String image) {
        this.name = name;
        this.price = price;
        this.type = type;
        this.storeName = storeName;
        this.restriction = restriction;
        this.image = image;
    }

    public FoodItem(FoodItem copy) {
        this.name = copy.getName();
        this.price = copy.getPrice();
        this.type = copy.getType();
        this.storeName = copy.getStoreName();
        this.restriction = copy.getRestriction();
        this.image = copy.getImage();
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return "{" +
            " name='" + getName() + "'" +
            ", price='" + getPrice() + "'" +
            ", type='" + getType() + "'" +
            ", storeName='" + getStoreName() + "'" +
            ", restriction='" + getRestriction() + "'" +
            ", image='" + getImage() + "'" +
            "}";
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return this.image;
    }


    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStoreName() {
        return this.storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getRestriction() {
        return this.restriction;
    }

    public void setRestriction(String restriction) {
        this.restriction = restriction;
    }

}