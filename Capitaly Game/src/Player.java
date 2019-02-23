// Ulaş Onat Alakent
//
// yl9i70
//
// Capitaly Game
//
// 2018/10/15 03:55:00
//
// This solution was submitted and prepared by Ulaş Onat Alakent, yl9i70 for the
// Capitaly Game assignment of the Practical software engineering I. course.
//
// I declare that this solution is my own work.
//
// I have not copied or used third party solutions.
//
// I have not passed my solution to my classmates, neither  made it public.
//
// Students’ regulation of Eötvös Loránd University (ELTE Regulations
// Vol. II. 74/C. § ) states that as long as a student presents another
// student’s work - or at least the significant part of it - as his/her own
// performance, it will count as a disciplinary fault. The most serious
// consequence of a disciplinary fault can be dismissal of the student from
// the University.

import java.util.*;

/** The Player class contains the name, money and the strategy information of a player. Also contains a member named 'location'
 * of type Field which points at the location that the player is on. */
public class Player {
    private String name;
    private int money;
    private Strategy strategy; // Creating an instance of the type we declared on Line 9.
    private Field location; // Each player will have a location.

    /** Note that, while creating a base class named 'Player' and extending three class from that is quite doable,
     *  since all players will have mostly same features but will only be different when it comes to execute the buying move,
     *  creating an enum for different strategies is what I have chosen to do. */
    public enum Strategy {GREEDY, CAREFUL, TACTICAL} // Creating the strategies.

    public Player(String name, Strategy strategy) {
        this.name = name;
        this.money = 10000;
        this.strategy = strategy;

        // Everyone will start from the first position.
        this.location = CyclicalBoard.getFields().iterator().next();

    }

    /** This will only be useful for 'Tactical' players. */
    private boolean willSkip = false;

    /** calls the setNewLocation method to set the new location of the player, then outputs location changing information, at last
     * calls executeTheMove to basically apply the move.
     * @param dice the dice number to pass as a parameter to setNewLocation method that is located in this method.
     * @return true if the player doesn't run out of money, return false if he does.
     */
    public boolean move(int dice) {

        Field oldLocation = this.location;

        setNewLocation(dice); // sets the new location of the player.

        // Outputting the location changing information:
        int round = CyclicalBoard.getRoundNumber();
        System.out.println(round + ". round: " + getName() + " moved to " + this.location + " from " + oldLocation + " by playing " + dice);

        // if executeTheMove() returns false, this method also returns false, so that we inform that this player has lost.
        if(!executeTheMove()) {
            return false;
        }

        System.out.println(); // since each move() method will contain several lines of code, it's good to separate each method calling.
        return true;
    }

    /** Using an iterator in the set, we are setting the new location of the player. How this method works is,
     * firstly it creates an iterator and assigns it to the location of this player on the fields set.
     * Then iterates n times (where n = dice number) in the set, also makes sure that if the iterator comes to the end of
     * the set, then sets the location to the start of the board.
     *
     * @param dice changes the location according to this parameter
     */
    public void setNewLocation(int dice) {
        Iterator<Field> iterator = CyclicalBoard.getFields().iterator();
        while(!(iterator.next().equals(this.location))) {
            // Continues until iterator matches the location.
            // No need to write anything to inside the while loop.
        }
        int loopNumber = 0;
        for(int i=0; i<dice; i++) {
            loopNumber++;
            if(iterator.hasNext()) {
                // If last looping, then assign the location.
                if(loopNumber == dice) {
                    this.location = iterator.next();
                } else {
                    iterator.next();
                }
            } else {
                // Then we are back to start of the board.
                iterator = CyclicalBoard.getFields().iterator();
                this.location = iterator.next();
            }
        }
    }

    /** creates a unique hashCode for each object that will extend this class, also takes the advantage of the hashCode of String class.*/
    @Override
    public int hashCode() {
        return this.name.hashCode() + 31;
    }

    /** overriding the equals method, since the objects of the classes that extend this class will be elements on a set. */
    // Each player must have unique name
    @Override
    public boolean equals(Object o) {
        if(this == o) // If this and o are the same reference, no need to look more, return true.
            return true;
        if(o == null || this.getClass() != o.getClass()) // If the o is null, or this and o are not the type of same class, return false.
            return false;

        return this.getName().equals(((Player) o).getName());
    }


    /** This method gets called in move() method, and this method determines the type of the new location of the player.
     * if it is the type of property, calls the executeTheMoveProperty() method since there are lots of stuff to do in that case,
     * just to make this method less complicated. But in three cases, makes the necessary changes, outputs the information about the
     * changing.
     * @return returns false if the player is unable to pay the service field or the rent of a property, returns true
     * if he did not run out of money.
     */
    public boolean executeTheMove() {

        // There will be three cases for the move, property, lucky, or service. Another method for property is written
        // since it is much more complex than the others.

        switch (this.location.getClassName()) {
            case "PropertyField":
                // If the player runs out of money, executeTheMoveProperty will return false, so this method will also
                // return false after outputting the information.
                if(!executeTheMoveProperty()) {
                    System.out.println(getName() + " is unable to pay the rent of " + this.location.getAmount());
                    System.out.println(getName() + "'s money was only " + money);
                    return false;
                }
                // We don't need to display any information here as we are doing on other cases below, because executeTheMoveProperty
                // does it already.
                break;
            case "ServiceField":
                if(!(money >= this.location.getAmount())) {
                    // If the player is unable to pay the service fee, then he will lose (so we're also returning false here)
                    System.out.println(getName() + " is unable to pay the service field of " + this.location.getAmount());
                    System.out.println(getName() + "'s " + " money was only " + money);
                    return false;
                }
                System.out.println(getName() + " came to a service field and has paid " + this.location.getAmount());
                money -= this.location.getAmount();
                displayRemainingBalance(4000);
                break;

            case "LuckyField":
                System.out.println(getName() + " came to a lucky field and has earned " + this.location.getAmount());
                money += this.location.getAmount();
                displayNewBalance(this.location.getAmount());
                break;

        }
        return true;
    }



    // Returns false if he runs out of money.

    /** This method gets called in executeTheMove() method, so it firstly creates a PropertyField reference to be used in this method,
     * instead of typing (PropertyField) before the location every time, then creates a boolean variable and gets the information
     * if this player has visited this location at least once before,
     * in case he did not, it adds this player to the array list of 'playersWhoVisited'
     * to make sure that the next time this method will be called, hasVisited will have the value of 'true'.
     *
     * Then checks the following conditions:
     * - If there is an owner in the new location that is not this player, in that case he pays the money (if he has money),
     * otherwise he loses and quits the game.
     *
     * - If that is not the case, it means that the strategy will matter upon his move, therefore we'll consider the following
     * options in each strategies:
     *
     * - If there is no owner, or he is at his location. (and this has three conditions in it)
     *   - if he is on his property, but he does not have any house on it.
     *   - if it is the first time he is here.
     *   - if he is on a property that is set as available because the player who had it failed the game.
     *   (Note that this is a special case, if the player comes in a place that he has visited before and there is no owner of this
     *   area now, he can both buy the property and build a house on it at one-time. Also note that buying the both property and build
     *   a house on it is only and only doable in this case.)
     *
     * - Or he is on his property and has a house on it.
     *
     * What needs to be considered in this method is that, it sets the player's balance information, outputs the changing on both
     * the player himself and the owner (if the player comes to the property of an owner), and does all of it according to their strategies.
     * Also on every possibility of purchasing, considers if the player has visited this location before or not, if he did not,
     * does not allow him to build a house on it.
     * */
    public boolean executeTheMoveProperty() {
        PropertyField field = ((PropertyField) (this.location));
        boolean hasVisited = field.getPlayersWhoVisited().contains(this);
        if (!hasVisited) {
            field.getPlayersWhoVisited().add(this);
        }

        // If there is a owner which is not the player itself.
        if (field.getOwner() != null && !(field.getOwner().equals(this))) {
            Player owner = field.getOwner();
            // If there is a house on the property, it means he has to pay 2000.
            if (field.isThereHouse()) {
                if(this.money >= 2000) {
                    this.money -= 2000;
                    owner.addMoney(2000);
                    System.out.println(getName() + " has paid 2000 to " + owner);
                    displayRemainingBalance(2000);
                    displayCurrentBalance(owner);
                } else {
                    // If player loses, the owner will get its money from the bank (Capitaly rule)
                    owner.addMoney(2000);
                    System.out.println("The owner " + owner + " got 2000 by bank.");
                    displayCurrentBalance(owner);
                    return false;
                }
            }
            // If the owner does not have a house on this property, the player will have to pay just 500.
            else {
                if(this.money >= 500) {
                    this.money -= 500;
                    owner.addMoney(500);
                    System.out.println(getName() + " has paid 500 to " + owner);
                    displayRemainingBalance(500);
                    displayCurrentBalance(owner);
                } else {
                    // If player loses, the owner will get its money from the bank (Capitaly rule)
                    owner.addMoney(500);
                    displayCurrentBalance(owner);
                }
            }
        }
        // If there is not an owner or its his property.
        else {

            // Greedy means that if he will buy everything that he could.
            if (this.strategy == Strategy.GREEDY) {
                // If he is on his property, but he has no house on it.
                if ((!(field.isThereHouse())) && ((this.equals(field.getOwner())))) {
                    if (this.money >= 4000) {
                        this.money -= 4000;
                        field.setOwner(this);
                        field.buildHouse();
                        System.out.println("Since the player has visited " + field + " before, " + getMoney() + " bought a house.");
                        displayRemainingBalance(4000);
                    } else {
                        System.out.println(getName() + " came to property his property that he had purchased, but he did not have enough money to build a house.");
                        System.out.println(getName() + "'s money was " + money + " (needs " + (4000-money) + " more)");
                    }
                    // If it is the very first time that he is here. (which means there is no owner)
                } else if (!hasVisited) {
                    if (this.money >= 1000) {
                        field.setOwner(this);
                        money -= 1000;
                        System.out.println(getName() + " has purchased this property.");
                        displayRemainingBalance(4000);
                    } else {
                        System.out.println(getName() + " could not purchase this property due to lack of cash.");
                        System.out.println(getName() + "s money was " + money + " (needs " + (1000-money) + " more)");
                    }
                }
                // He is on a property that is free now (someone else had here before)
                else if((!(this.equals(field.getOwner())))) {
                    if (this.money >= 5000) {
                        System.out.println(getName() + " has purchased both the property and a house.");
                        field.setOwner(this);
                        field.buildHouse();
                        money -= 5000;
                        displayRemainingBalance(4000);
                    } else if(this.money >= 1000) {
                        System.out.println(getName() + " has purchased the property.");
                        field.setOwner(this);
                        money -= 1000;
                        displayRemainingBalance(4000);
                    } else {
                        System.out.println(getName() + " came to a property that is available, but he did not have enough money to do some action.");
                    }
                }
                // If he is on his property and has a house on it,
                else {
                    System.out.println(getName() + " has all the stuff here, nothing he can do.");
                    displayCurrentBalance(this);
                }
            }

            // Careful means that he will only buy if the purchasing costs at most half of his money.
            else if(this.strategy == Strategy.CAREFUL) {
                // If he is on his property, but he has no house on it.
                if ((!(field.isThereHouse())) && ((this.equals(field.getOwner())))) {
                    if (this.money >= 8000) {
                        this.money -= 4000;
                        field.setOwner(this);
                        field.buildHouse();
                        System.out.println("Since player has visited " + field + " before, he bought a house.");
                        System.out.println("Because he had at least double money of the cost.");
                        displayRemainingBalance(4000);
                    } else {
                        System.out.println("He could not build a house, but he did not have at least double money of the cost.");
                        displayCurrentBalance(this);
                    }
                    // If it is the very first time that he is here. (which means there is no owner)
                } else if (!hasVisited) {
                    if (this.money >= 2000) {
                        System.out.println("Because he had at least double money of the cost.");
                        field.setOwner(this);
                        money -= 1000;
                        System.out.println("He has purchased this property.");
                        displayRemainingBalance(1000);
                    } else {
                        System.out.println("He could not buy this property, because he did not have at least double money of the cost.");
                        System.out.println("His money was " + money);
                    }
                }
                // He is on a property that is free now (someone else had here before)
                else if ((!(this.equals(field.getOwner())))) {
                    if (this.money >= 10000) {
                        System.out.println("He has purchased both the property and a house.");
                        System.out.println("Because he had at least double money of the cost.");
                        field.setOwner(this);
                        field.buildHouse();
                        money -= 5000;
                        displayRemainingBalance(5000);
                    } else if (this.money >= 2000) {
                        System.out.println("He has purchased the property.");
                        System.out.println("Because he had at least double money of the cost.");
                        field.setOwner(this);
                        money -= 1000;
                        displayRemainingBalance(1000);
                    } else {
                        System.out.println("He came to a property that is available, but he did not have at least double money of the cost.");
                        System.out.println("His money was " + money);
                    }
                }
                // If he is on his property and has a house on it,
                else {
                    System.out.println("He has all the stuff here, nothing he can do.");
                    System.out.println("Note, his current money is: " + money);
                }
            }

                // Else, if he is a tactical player,
                else {
                     // If the code comes to this line, it means he has the possibility of buying something, therefore
                // we'll check if he will skip this round, if he will, we make sure that he will not skip the following possibility of him,
                // by assigning the 'willSkip' variable to false.

                // If he skips,
                if (willSkip) {
                    System.out.println("He skipped this round because he is a tactical player.");
                    willSkip = false;
                    return true; // ends the method.
                }

                // If he does not skip,
                 else {
                    System.out.println("He won't skip this round.");
                    willSkip = true;
                    // If he is on his property, but he has no house on it.
                    if ((!(field.isThereHouse())) && ((this.equals(field.getOwner())))) {
                        if (this.money >= 4000) {
                            this.money -= 4000;
                            field.setOwner(this);
                            field.buildHouse();
                            System.out.println("Since player has visited " + field + " before, he bought a house.");
                            System.out.println("His remaining money: " + money + "(" + (money + 4000) + " - 4000)");
                        } else {
                            System.out.println("He came to property his property that he had purchased, but he did not have enough money to build a house.");
                            System.out.println("His money was " + money + " (need " + (4000 - money) + " more)");
                        }
                        // If it is the very first time that he is here. (which means there is no owner)
                    } else if (!hasVisited) {
                        if (this.money >= 1000) {
                            field.setOwner(this);
                            money -= 1000;
                            System.out.println("He has purchased this property.");
                            displayRemainingBalance(1000);
                        } else {
                            System.out.println("He could not purchase this property due to lack of cash.");
                            System.out.println("His money was " + money + " (needs " + (1000 - money) + " more)");
                        }
                    }
                    // He is on a property that is free now (someone else had here before)
                    else if ((!(this.equals(field.getOwner())))) {
                        if (this.money >= 5000) {
                            System.out.println("He has purchased both the property and a house.");
                            field.setOwner(this);
                            field.buildHouse();
                            money -= 5000;
                            displayRemainingBalance(5000);
                        } else if (this.money >= 2000) {
                            System.out.println("He has purchased the property.");
                            field.setOwner(this);
                            money -= 1000;
                            displayRemainingBalance(1000);
                        } else {
                            System.out.println("He came to a property that is available, but he did not have enough money to do some action.");
                        }
                    }
                }
            }

        }
        return true;
    }

    // Getters

    public String getName() {
        return name;
    }

    public int getMoney() {
        return money;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    /** This method gets called to add some amount of money that is passed as parameter, to the 'money' value of the Player. */
    public void addMoney(int value) {
        money += value;
    }

    @Override
    public String toString() {
        return name;
    }


    /** This method is just to avoid some repeated code while outputting the information. */
    // Since we'll call the following method only from this class, there's no harm on making it private.
    private void displayCurrentBalance(Player player) {
        System.out.println("The current balance of " + player.name + " is: " + player.money);
    }

    /** This method is just to avoid some repeated code while outputting the information. */
    private void displayRemainingBalance(int difference) {
        System.out.println("The remaining balance of " + name + " is: " + (money+difference) + " - " + difference + " = " + money);
    }

    /** This method is just to avoid some repeated code while outputting the information. */
    // The previous method will be outputted in the case where a player got a rent from other player, or he moved to a lucky field.
    private void displayNewBalance(int difference) {
        System.out.println("The new balance of " + name + " is: " + (money-difference) + " + " + difference + " = " + money);
    }
}
