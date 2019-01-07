package pl.gb.edu.codecool.tamagotchi.model;

public class Game implements Runnable {
    private Thread gameThreat;
    private Pet pet;

    public Game() {
        pet = new Pet();
        gameThreat = new Thread(this, "GAME");
    }

    @Override
    public void run() {
        while (isPetAlive()) {
            pet.petIsLive();
            System.out.println("ENERGY\t" + pet.getEnergy() + "\tHYGIENE\t" + pet.getHygiene() + "\tHUNGER\t" + pet.getHygiene());

            if(pet.isBathing()) {
                pet.bath();
            }

            if (pet.isSleeping()) {
                pet.sleep();
            }

            if (pet.isEating()) {
                pet.eat();
            }
        }
        pet.setPicture("Gollum_death.jpg");
        pet.setBackground("black");
        System.out.println("Koniec wątku gry");
    }

    public boolean isPetAlive() {
        if (pet.getEnergy() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public Thread getGameThreat() {
        return gameThreat;
    }

    public Pet getPet() {
        return pet;
    }
}
