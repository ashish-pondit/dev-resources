package org.tutorial.chapter7.exercise1;

public class BMICalculator {
    private String name;
    private final double weightInKG;
    private final double heightInMeter;

    private final double bmi;

    public BMICalculator(String name, double weightInKG, double heightInMeter) {
        this.name = name;
        this.weightInKG = weightInKG;
        this.heightInMeter = heightInMeter;
        bmi=weightInKG/(heightInMeter*heightInMeter);
    }

    public double getBmi() {
        return bmi;
    }

    public String getName(){
        return name;
    }

    public void showBMI(){
        System.out.println("Hello, "+name);
        System.out.printf("Your BMI is %.2f\n",bmi);
        if(bmi<18.5){
            System.out.println("You are underweight");
        }else if(bmi<24.9){
            System.out.println("Your BMI is in normal range");
        }else if(bmi<29.9){
            System.out.println("You are overweight");
        }else {
            System.out.println("You are obese");
        }
    }
}
