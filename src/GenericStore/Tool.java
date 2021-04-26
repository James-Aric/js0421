package GenericStore;

/**
 * Class representing a tool object.
 * Mostly used to try and keep the RentalAgreement class a bit cleaner
 */
public class Tool {
    private final String toolType;
    private final String brand;
    private final String toolCode;

    public Tool(String toolCode) throws Exception {
        this.toolCode = toolCode;
        this.toolType = this.toolType();
        this.brand = this.toolBrand();
    }

    /**
     * Gets the type of the tool based on the tool code
     * @return - String representing tool type
     */
    private String toolType() throws Exception {
        switch (this.getToolCode().substring(0, 3)) {
            case "LAD":
                return "Ladder";

            case "CHN":
                return "Chainsaw";

            case "JAK":
                return "Jackhammer";
            default:
                throw new Exception("Invalid tool code entered: " + this.toolCode);
        }
    }

    /**
     * Gets the brand of the tool based on the tool code
     * @return - String representing tool brand
     */
    private String toolBrand() throws Exception {
        switch (this.getToolCode().substring(3)) {
            case "W":
                return "Werner";

            case "S":
                return "Stihl";

            case "R":
                return "Ridgid";

            case "D":
                return "DeWalt";

            default:
                throw new Exception("Invalid tool code entered: " + this.toolCode);
        }
    }

    public String getToolCode() {
        return this.toolCode;
    }
    public String getType() { return this.toolType; }
    public String getBrand() { return this.brand; }

    /**
     * Returns a boolean representing if the tool charges for weekend days
     * @return - boolean
     */
    public boolean weekendCharge() {
        switch (this.toolType) {
            case "Ladder":
                return true;

            case "Chainsaw":
                return false;

            case "Jackhammer":
                return false;
        }
        return false;
    }

    /**
     * Returns a boolean representing if the tool charges for holidays
     * @return - boolean
     */
    public boolean holidayCharge() {
        switch (this.toolType) {
            case "Ladder":
                return false;

            case "Chainsaw":
                return true;

            case "Jackhammer":
                return false;
        }
        return false;
    }
}
