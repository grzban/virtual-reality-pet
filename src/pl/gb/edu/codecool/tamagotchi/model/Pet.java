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
        petIsHappy();
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
            petIsLiving();
        }
        sleeping = false;
        notify();
    }

    synchronized public void eat() {
        while (hunger < 1) {
            System.out.println("Eating HUNGER\t" + hunger);
            hunger += 0.1;
            petIsLiving();
            sleepPerSecond();
        }
        hunger = 1;
        eating = false;
        notify();
    }

    synchronized public void bath() {
        while (hygiene < 1) {
            System.out.println("Bath HYGIENE\t" + hygiene);
            hygiene += 0.05;
            petIsLiving();
            sleepPerSecond();
        }
        hygiene = 1;
        bathing = false;
        notify();
    }

    public void petIsLiving() {
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
        if (energy > 0) {
            energy -= energyModifier * 0.001;
        } else {
            energy = 0;
            alive = false;
        }
    }

    private void changeEnergyModifier() {
        if (hunger <= 0) {
            energyModifier = 10;
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

    public void petIsAngry() {
        setPicture("Gollum_angry.png");
        setBackground("red");
    }

    public void petIsHappy() {
        setPicture("Gollum_happy.png");
        setBackground("green");
    }

    public void petIsSad() {
        setPicture("Gollum_imploring.png");
        setBackground("orange");
    }

    public void petIsDead() {
        setPicture("Gollum_death.jpg");
        setBackground("black");
    }

    public void petIsSleeping() {
        setPicture("Gollum_sleeping.jpg");
    }

    public void petIsEating() {
        setPicture("Gollum_eating.jpg");
    }

    public void petIsBathing() {
        setPicture("Gollum_bathing.jpg");
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
