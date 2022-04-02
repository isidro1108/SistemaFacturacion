/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities.CustomResponses;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author euris
 */
public class FormStatus {
    private boolean valid;
    private List<String> nameInvalidFields;
    private String message;

    public FormStatus() {
        this.valid = true;
        this.nameInvalidFields = new ArrayList<>();
        this.message = "";
    }
    
    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public List<String> getNameInvalidFields() {
        return nameInvalidFields;
    }

    public void setNameInvalidFields(List<String> nameInvalidFields) {
        this.nameInvalidFields = nameInvalidFields;
    }
    
    public void addNameInvalidField(String name) {
        this.nameInvalidFields.add(name);
    }
    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    public void clearStatus() {
        this.valid = true;
        this.nameInvalidFields = new ArrayList<>();
        this.message = "";
    }
}
