package pl.gb.edu.codecool.tamagotchi.model;

public class Game implements Runnable {

    private Pet pet;

    public Game(Pet pet) {
       this.pet = pet;
    }

    @Override
    public void run() {
        while (isPetAlive()) {
            pet.petIsLiving();
            System.out.println("ENERGY\t" + pet.getEnergy() + "\tHYGIENE\t" + pet.getHygiene() + "\tHUNGER\t" + pet.getHunger());

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
        return pet.isAlive();
    }
}
