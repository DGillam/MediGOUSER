package com.example.delivery.Model;

import java.util.ArrayList;
import java.util.HashMap;

public class Deliveries {
    String id;
    Boolean current;
    String deliveryDate;
    ArrayList<String> emailPatients;
    String emailPharmacist;
    HashMap<String, ArrayList<String>> medicines;
    String robotID;
    String pharmacy;

    public Deliveries(String id, Boolean current, String deliveryDate, ArrayList<String> emailPatients, String emailPharmacist, HashMap<String, ArrayList<String>> medicines, String robotID, String pharmacy) {
        this.id = id;
        this.current = current;
        this.deliveryDate = deliveryDate;
        this.emailPatients = emailPatients;
        this.emailPharmacist = emailPharmacist;
        this.medicines = medicines;
        this.robotID = robotID;
        this.pharmacy = pharmacy;
    }

    public Deliveries() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getCurrent() {
        return current;
    }

    public void setCurrent(Boolean current) {
        this.current = current;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public ArrayList<String> getEmailPatients() {
        return emailPatients;
    }

    public void setEmailPatients(ArrayList<String> emailPatients) {
        this.emailPatients = emailPatients;
    }

    public String getEmailPharmacist() {
        return emailPharmacist;
    }

    public void setEmailPharmacist(String emailPharmacist) {
        this.emailPharmacist = emailPharmacist;
    }

    public HashMap<String, ArrayList<String>> getMedicines() {
        return medicines;
    }

    public void setMedicines(HashMap<String, ArrayList<String>> medicines) {
        this.medicines = medicines;
    }

    public String getRobotID() {
        return robotID;
    }

    public void setRobotID(String robotID) {
        this.robotID = robotID;
    }

    public String getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(String pharmacy) {
        this.pharmacy = pharmacy;
    }
}



