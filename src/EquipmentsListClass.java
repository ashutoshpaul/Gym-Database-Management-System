
public class EquipmentsListClass {
	private String equipmentName = null;
	private String equipmentNum = null;
	private String equipmentCost = null;
	private String equipmentAvailability = null;
	private String equipmentDOI = null;
	
	public EquipmentsListClass(String equipmentName , String equipmentNum , String equipmentCost , String equipmentAvailability , String equipmentDOI) {
        this.equipmentName = equipmentName;
        this.equipmentNum = equipmentNum;
        this.equipmentCost = equipmentCost;
        this.equipmentAvailability = equipmentAvailability;
        this.equipmentDOI = equipmentDOI;
    }

    public String getEquipmentName() {
        return equipmentName;
    }
    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }
    public String getEquipmentNum() {
        return equipmentNum;
    }
    public void setEquipmentNum(String equipmentNum) {
        this.equipmentNum = equipmentNum;
    }
    public String getEquipmentCost() {
        return equipmentCost;
    }
    public void setEquipmentCost(String equipmentCost) {
        this.equipmentCost = equipmentCost;
    }
    public String getEquipmentAvailability() {
        return equipmentAvailability;
    }
    public void setEquipmentAvailability(String equipmentAvailability) {
        this.equipmentAvailability = equipmentAvailability;
    }
    
    public String getEquipmentDOI() {
        return equipmentDOI;
    }
    public void setEquipmentDOI(String equipmentDOI) {
        this.equipmentDOI = equipmentDOI;
    }
}
