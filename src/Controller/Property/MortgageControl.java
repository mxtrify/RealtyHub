package Controller.Property;

import Entity.Property;

public class MortgageControl {
    private Property property;

    public MortgageControl() {
        this.property = new Property();
    }

    public double getMortgage(double loanAmount, double interestRate, int loanTerm) {
        return property.calculateMortgage(loanAmount, interestRate, loanTerm);
    }
}