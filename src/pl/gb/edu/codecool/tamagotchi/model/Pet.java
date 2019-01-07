package pl.gb.edu.codecool.tamagotchi.model;

public class Pet {

    private String picture;
    private String background;
    private double energy;
    private double hunger;
    private double hygiene;
    private double energyModifier;

    private boolean sleeping;
    private boolean eating;
    private boolean bathing;
    private boolean alive;

    public Pet() {
        initialParameters();
    }

    private void initialParameters() {
        picture = "Gollum_happy.png";
        background = "green";
        energy = 1;
        hunger = 1;
        hygiene = 1;

        sleeping = false;
        eating = false;
        bathing = false;
        alive = true;

        energyModifier = 1;
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
        int endTime = 25;
        for (int i = 0; i < endTime; i++) {
            System.out.println("Eating " + i + "\tHUNGER\t" + hunger);
            if (hunger < 1) {
                hunger += 0.1;
            } else {
                hunger = 1;
                break;
            }
            petIsLive();
        }
        eating = false;
        notify();
    }

    synchronized public void bath() {
        int endTime = 15;
        for (int i = 0; i < endTime; i++) {
            System.out.println("Bath " + i + "\tHYGIENE\t" + hygiene);
            if (hygiene < 1) {
                hygiene += 0.05;
            } else {
                hygiene = 1;
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

    private void sleepPerSecond() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    synchronized private void decreaseEnergy() {
        changeEnergyModifier();
        if (alive) {
            energy -= energyModifier * 0.001;
        } else {
            energy = 0;
            alive = false;
        }
    }

    private void changeEnergyModifier() {
        if (hunger <= 0) {
            energyModifier = 3;
        } else {
            energyModifier = 1;
        }
    }

    synchronized public void decreaseHunger() {
        if (hunger <= 0) {
            hunger = 0;
        } else {
            hunger -= 0.005;
        }
    }

    synchronized public void decreaseHygiene() {
        if (hygiene <= 0) {
            hygiene = 0;
        } else {
            hygiene -= 0.0005;
        }
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

    public double getHygiene() {
        return hygiene;
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

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
}
