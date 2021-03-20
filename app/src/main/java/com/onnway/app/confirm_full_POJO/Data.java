package com.onnway.app.confirm_full_POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("assign_id")
    @Expose
    private String assign_id;
    @SerializedName("laod_type")
    @Expose
    private String laodType;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("destination")
    @Expose
    private String destination;
    @SerializedName("truck_type")
    @Expose
    private String truckType;
    @SerializedName("truck_type2")
    @Expose
    private String truckType2;
    @SerializedName("schedule")
    @Expose
    private String schedule;
    @SerializedName("weight")
    @Expose
    private String weight;
    @SerializedName("material")
    @Expose
    private String material;
    @SerializedName("freight")
    @Expose
    private String freight;
    @SerializedName("other_charges")
    @Expose
    private String otherCharges;
    @SerializedName("cgst")
    @Expose
    private String cgst;
    @SerializedName("sgst")
    @Expose
    private String sgst;
    @SerializedName("insurance")
    @Expose
    private String insurance;
    @SerializedName("paid_percent")
    @Expose
    private String paidPercent;
    @SerializedName("paid_amount")
    @Expose
    private String paidAmount;
    @SerializedName("pickup_address")
    @Expose
    private String pickupAddress;
    @SerializedName("pickup_city")
    @Expose
    private String pickupCity;
    @SerializedName("pickup_pincode")
    @Expose
    private String pickupPincode;
    @SerializedName("pickup_phone")
    @Expose
    private String pickupPhone;
    @SerializedName("drop_address")
    @Expose
    private String dropAddress;
    @SerializedName("drop_city")
    @Expose
    private String dropCity;
    @SerializedName("drop_pincode")
    @Expose
    private String dropPincode;
    @SerializedName("drop_phone")
    @Expose
    private String dropPhone;
    @SerializedName("remarks")
    @Expose
    private String remarks;
    @SerializedName("length")
    @Expose
    private String length;
    @SerializedName("width")
    @Expose
    private String width;
    @SerializedName("height")
    @Expose
    private String height;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("vehicle_number")
    @Expose
    private String vehicleNumber;
    @SerializedName("driver_number")
    @Expose
    private String driverNumber;
    @SerializedName("pod")
    @Expose
    private List<Pod> pod = null;
    @SerializedName("doc")
    @Expose
    private List<Doc> doc = null;
    @SerializedName("lr")
    @Expose
    private List<Lr> lr = null;
    @SerializedName("invoice")
    @Expose
    private List<Invoice> invoice = null;
    @SerializedName("promo_code")
    @Expose
    private String promo_code;
    @SerializedName("pvalue")
    @Expose
    private String pvalue;
    @SerializedName("insurance_used")
    @Expose
    private String insurance_used;
    @SerializedName("per80")
    @Expose
    private String per80;
    @SerializedName("per100")
    @Expose
    private String per100;
    @SerializedName("material_image")
    @Expose
    private String material_image;
    @SerializedName("truckTypeDetails")
    @Expose
    private String truckTypeDetails;
    @SerializedName("truckCapacity")
    @Expose
    private String truckCapacity;
    @SerializedName("boxLength")
    @Expose
    private String boxLength;
    @SerializedName("boxWidth")
    @Expose
    private String boxWidth;
    @SerializedName("boxArea")
    @Expose
    private String boxArea;
    @SerializedName("selectedArea")
    @Expose
    private String selectedArea;
    @SerializedName("remainingArea")
    @Expose
    private String remainingArea;
    @SerializedName("selected")
    @Expose
    private String selected;
    @SerializedName("remarks2")
    @Expose
    private String remarks2;
    @SerializedName("discount")
    @Expose
    private String discount;
    @SerializedName("discvalue")
    @Expose
    private String discvalue;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLaodType() {
        return laodType;
    }

    public void setLaodType(String laodType) {
        this.laodType = laodType;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getTruckType() {
        return truckType;
    }

    public void setTruckType(String truckType) {
        this.truckType = truckType;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getFreight() {
        return freight;
    }

    public void setFreight(String freight) {
        this.freight = freight;
    }

    public String getOtherCharges() {
        return otherCharges;
    }

    public void setOtherCharges(String otherCharges) {
        this.otherCharges = otherCharges;
    }

    public String getCgst() {
        return cgst;
    }

    public void setCgst(String cgst) {
        this.cgst = cgst;
    }

    public String getSgst() {
        return sgst;
    }

    public void setSgst(String sgst) {
        this.sgst = sgst;
    }

    public String getInsurance() {
        return insurance;
    }

    public void setInsurance(String insurance) {
        this.insurance = insurance;
    }

    public String getPaidPercent() {
        return paidPercent;
    }

    public void setPaidPercent(String paidPercent) {
        this.paidPercent = paidPercent;
    }

    public String getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(String paidAmount) {
        this.paidAmount = paidAmount;
    }

    public String getPickupAddress() {
        return pickupAddress;
    }

    public void setPickupAddress(String pickupAddress) {
        this.pickupAddress = pickupAddress;
    }

    public String getPickupCity() {
        return pickupCity;
    }

    public void setPickupCity(String pickupCity) {
        this.pickupCity = pickupCity;
    }

    public String getPickupPincode() {
        return pickupPincode;
    }

    public void setPickupPincode(String pickupPincode) {
        this.pickupPincode = pickupPincode;
    }

    public String getPickupPhone() {
        return pickupPhone;
    }

    public void setPickupPhone(String pickupPhone) {
        this.pickupPhone = pickupPhone;
    }

    public String getDropAddress() {
        return dropAddress;
    }

    public void setDropAddress(String dropAddress) {
        this.dropAddress = dropAddress;
    }

    public String getDropCity() {
        return dropCity;
    }

    public void setDropCity(String dropCity) {
        this.dropCity = dropCity;
    }

    public String getDropPincode() {
        return dropPincode;
    }

    public void setDropPincode(String dropPincode) {
        this.dropPincode = dropPincode;
    }

    public String getDropPhone() {
        return dropPhone;
    }

    public void setDropPhone(String dropPhone) {
        this.dropPhone = dropPhone;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getDriverNumber() {
        return driverNumber;
    }

    public void setDriverNumber(String driverNumber) {
        this.driverNumber = driverNumber;
    }

    public List<Pod> getPod() {
        return pod;
    }

    public void setPod(List<Pod> pod) {
        this.pod = pod;
    }

    public List<Doc> getDoc() {
        return doc;
    }

    public void setDoc(List<Doc> doc) {
        this.doc = doc;
    }

    public List<Lr> getLr() {
        return lr;
    }

    public void setLr(List<Lr> lr) {
        this.lr = lr;
    }

    public List<Invoice> getInvoice() {
        return invoice;
    }

    public void setInvoice(List<Invoice> invoice) {
        this.invoice = invoice;
    }

    public String getAssign_id() {
        return assign_id;
    }

    public void setAssign_id(String assign_id) {
        this.assign_id = assign_id;
    }

    public String getPromo_code() {
        return promo_code;
    }

    public String getPvalue() {
        return pvalue;
    }

    public void setPromo_code(String promo_code) {
        this.promo_code = promo_code;
    }

    public void setPvalue(String pvalue) {
        this.pvalue = pvalue;
    }

    public String getInsurance_used() {
        return insurance_used;
    }

    public void setInsurance_used(String insurance_used) {
        this.insurance_used = insurance_used;
    }

    public String getPer80() {
        return per80;
    }

    public String getPer100() {
        return per100;
    }

    public void setPer80(String per80) {
        this.per80 = per80;
    }

    public void setPer100(String per100) {
        this.per100 = per100;
    }

    public String getMaterial_image() {
        return material_image;
    }

    public void setMaterial_image(String material_image) {
        this.material_image = material_image;
    }

    public String getTruckCapacity() {
        return truckCapacity;
    }

    public String getBoxLength() {
        return boxLength;
    }

    public String getTruckTypeDetails() {
        return truckTypeDetails;
    }

    public String getBoxArea() {
        return boxArea;
    }

    public String getBoxWidth() {
        return boxWidth;
    }

    public String getRemainingArea() {
        return remainingArea;
    }

    public String getSelected() {
        return selected;
    }

    public String getSelectedArea() {
        return selectedArea;
    }

    public void setBoxLength(String boxLength) {
        this.boxLength = boxLength;
    }

    public void setTruckCapacity(String truckCapacity) {
        this.truckCapacity = truckCapacity;
    }

    public void setBoxArea(String boxArea) {
        this.boxArea = boxArea;
    }

    public void setBoxWidth(String boxWidth) {
        this.boxWidth = boxWidth;
    }

    public void setRemainingArea(String remainingArea) {
        this.remainingArea = remainingArea;
    }

    public void setTruckTypeDetails(String truckTypeDetails) {
        this.truckTypeDetails = truckTypeDetails;
    }

    public void setSelectedArea(String selectedArea) {
        this.selectedArea = selectedArea;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    public String getRemarks2() {
        return remarks2;
    }

    public void setRemarks2(String remarks2) {
        this.remarks2 = remarks2;
    }

    public String getTruckType2() {
        return truckType2;
    }

    public void setTruckType2(String truckType2) {
        this.truckType2 = truckType2;
    }

    public String getDiscount() {
        return discount;
    }

    public String getDiscvalue() {
        return discvalue;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public void setDiscvalue(String discvalue) {
        this.discvalue = discvalue;
    }
}

