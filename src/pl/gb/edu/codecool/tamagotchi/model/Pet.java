package pl.gb.edu.codecool.tamagotchi.model;

public class Pet {

    private String picture;
    private String background;
    private double energy;
    private double hunger;
    private double hygiene;

    private boolean sleeping;
    private boolean eating;
    private boolean bathing;

    public Pet() {
        initialParameters();
    }

    private void initialParameters() {
        picture = "Gollum_happy.png";
        energy = 1;
        hunger = 1;
        hygiene = 1;

        sleeping = false;
        eating = false;
        bathing =false;
    }

    synchronized public void sleep() {
        while (sleeping) {
            System.out.println("Sleep " + "\tENERGY\t" + energy);
            if (energy < 1) {
                energy += 0.01;
            } else {
                energy = 1;
                break;
            }
            petIsLive();
        }
        sleeping = false;
        notify();
    }

    synchronized public void eat() {
        int endTime = 5;
        for (int i = 0; i < endTime; i++) {
            System.out.println("Eating " + i + "\tHUNGER\t" + hunger);
            if (hunger < 1) {
                hunger += 0.1;
            } else {
                hunger = 1;
                waitForEndAction(i, endTime, "eat");
                break;
            }
            petIsLive();
        }
        eating = false;
        notify();
    }

    synchronized public void bath() {
        int endTime = 5;
        for (int i = 0; i < endTime; i++) {
            System.out.println("Bath " + i + "\tHYGIENE\t" + hygiene);
            if (hygiene < 1) {
                hygiene += 0.05;
            } else {
                hygiene = 1;
                waitForEndAction(i, endTime, "bath");
                break;
            }
            petIsLive();
        }
        bathing = false;
        notify();
    }

    public void petIsLive() {
        decreaseHunger();
        decreaseHygiene();
        decreaseEnergy();
        sleepPerSecond();
    }

    private void waitForEndAction(int start, int end, String action) {
        for (int i = start; i < end; i++) {
            petIsLive();
            System.out.println("Waiting for " + action + " end " + i);
        }
    }

    private void sleepPerSecond() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    synchronized private void decreaseEnergy() {
        energy -= 0.005;
    }

    public void decreaseHunger() {
        hunger -= 0.05;
    }

    public void decreaseHygiene() {
        hygiene -= 0.001;
    }

    public double getEnergy() {
        return energy;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void setEnergy(double energy) {
        this.energy = energy;
    }

    public double getHunger() {
        return hunger;
    }

    public void setHunger(double hunger) {
        this.hunger = hunger;
    }

    public double getHygiene() {
        return hygiene;
    }

    public void setHygiene(double hygiene) {
        this.hygiene = hygiene;
    }

    public boolean isSleeping() {
        return sleeping;
    }

    public void setSleeping(boolean sleeping) {
        this.sleeping = sleeping;
    }

    public boolean isEating() {
        return eating;
    }

    public void setEating(boolean eating) {
        this.eating = eating;
    }

    public boolean isBathing() {
        return bathing;
    }

    public void setBathing(boolean bathing) {
        this.bathing = bathing;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }
}
