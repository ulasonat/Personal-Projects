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

/** This class contains the main method that gets executed. Also it has 4 static members that stores the players,
 * eliminated players, dices and also one scanner to get input from the user. */
public class CapitalyGame {

    // Using LinkedHashSet is a good idea, so that the players and the fields will be ordered according to
    // their insertion order, therefore we'll have a predictable iteration order.

    private static Set<Player> players = new LinkedHashSet<>();
    private static Set<Player> eliminatedPlayers = new LinkedHashSet<>();
    private static ArrayList<Integer> rollDices = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    /** Firstly, the file will be opened and all the input will be read. Note that if any error occurs during opening or reading
     * the file, the methods that does the opening and reading will return false, and in that case the program will stop running.
     * After completing the reading, the file will be closed, then user will need to type a number, after that
     * the game will be simulated until the round number that the user has entered, and in this simulation, every move in every round
     * will be printed out detailed, also while game is being simulated, if the dices were not enough to finish the game,
     * an exception will be thrown and handled. One other important thing is that if the user enters a round number which is
     * more than the round number where the game ends, the ending random will be outputted (as excepted), so if the games ends in
     * 23. round and the user wants to see the result of 57. rounds, 23. round's results will be outputted. */
    public static void main(String[] args) {

        String fileName = "test_cases/case1.txt";
        if(!ReadFile.openFile(fileName)) {
            // If there was an error with opening the file, the program ends.
            System.out.println("Program quitting.");
            return;
        }

        if(!ReadFile.readFile()) {
            // If there was an error with reading the input from the user, the program ends.
            System.out.println("Program quitting..");
            return;
        }

        ReadFile.closeFile();

        Iterator<Integer> diceIterator = rollDices.iterator();

        boolean gameIsOn = true;

        // The Task: Print out what we know about each player after a given number of rounds (balance, owned properties).

        // Getting the input from the user.
        System.out.println("\nThe game will be simulated until the round number you will type.");
        System.out.print("Please type the number of the round that you'd like the see the result of: ");
        int roundInput = 0;
        try {
            roundInput = scanner.nextInt();
            // If the input is less than 1, getting the input will continue until it's not.
            while (roundInput < 1) {
                System.out.println("The number must be between 1 and " + roundInput + ", please type again: ");
                roundInput = scanner.nextInt();
            }
            // If the data types don't match, the program will end.
        } catch (InputMismatchException e) {
            System.out.println("You needed to type an integer, please run the program again and type an integer.");
            return;
            // If EOF is pressed, the program will end.
        } catch (NoSuchElementException e) {
            System.out.println("CTRL+D is pressed, therefore the input stream of the program closed. Please run the program again.");
            return;
        }

            // Since the game will be simulated until the round number that the user has entered, we create a for loop for that.
            for(int i=0; i<roundInput; i++) {
                for (Player p : players) {
                    // Checking if there's enough dice to continue by adding a try-catch block, quits the game if there's not enough dice.
                    try {
                        if (!(p.move(diceIterator.next()))) { // if move method return false, that means the player leaves the game.
                            System.out.println(p.getName() + " RUNS OUT OF MONEY, REMAINING PLAYER(S): " + (players.size() - 1));
                            eliminatePlayer(p, players);
                            if (players.size() == 1) {
                                System.out.println("END OF GAME | THE WINNER IS: " + players.iterator().next().getName());
                                gameIsOn = false; // to quit the game
                                break;
                            } else {
                                break;
                            }
                        }
                    }
                    // In case there wasn't enough dice to read from the file.
                    catch (NoSuchElementException e) {
                        System.out.println("The dices were not enough to finish the game, quitting the game.");
                        gameIsOn = false;
                        break;
                    }
                }
                CyclicalBoard.incrementRoundNumber(); // increments the round number.
                if(!gameIsOn) {
                    break;
                }
            }

            // Outputting the result of the task:
        System.out.println("The information of the players after " + (CyclicalBoard.getRoundNumber() - 1) + ". round: ");
            // Firstly, outputting the eliminated players list (if there is/are):
        if(eliminatedPlayers.size() > 0) {
            System.out.println("\nEliminated player(s) list: ");
            for(Player e : eliminatedPlayers) {
                System.out.println(e); // since toString is overridden in 'Player' class, we could simply write the name of the object.
            }
        }
        // Outputs the balance and owned properties information of each player:
            for(Player p : players) {
                System.out.println();
                    System.out.println(p.getName() + ": ");
                    System.out.println("Balance: " + p.getMoney());
                    System.out.println("Owned properties: ");
                    boolean isThereProperty = false;
                    for(Field f : CyclicalBoard.getFields()) {
                        if(f.getClassName().equals("PropertyField")) {
                            PropertyField propertyField = ((PropertyField) (f));
                            if((p.equals(propertyField.getOwner()))) {
                                System.out.println(propertyField + ((propertyField.isThereHouse()) ? " (with a house)" : " (without a house)"));
                                isThereProperty = true;
                            }
                        }
                    }
                    // If there was not even one property that the player had, the following message will be outputted.
                    if(!isThereProperty) {
                        System.out.println(p.getName() + " has no properties.");
                    }
            }




    }

    /** Adds player to 'players' set object, also does not add a player that is already in the set.
     * This method gets called while reading the file. */
    public static void addPlayer(Player player) {
        if(players.add(player)) {
            System.out.println("The player " + player.getName() + " has been added with the strategy of " + player.getStrategy());
        } else {
            System.out.println("Such a player already exists.");
        }
        System.out.println();
    }

    /** Adds dice number to 'dices' array list object.
     * This method gets called while reading the file.
     * @return true if adding was successful, returns false if the number is not between 1 and 6. */
    public static boolean addRollDice(int dice) {
        if(dice >= 1 && dice <= 6) {
            rollDices.add(dice);
            return true;
        }
        return false;
    }

    /** Using the static method of CyclicalBoard class named addField, adds fields to the static member of CyclicalBoard class.
     * This method gets called while reading the file.
     * @param field is the field that will be added. */
    public static void addFields(Field field) {
        if(CyclicalBoard.addField(field)) {
            System.out.println("The field " + field.getName() + " has been added.");
            if(!(field.getClassName().equals("PropertyField"))) {
                System.out.println("The amount of it: " + field.getAmount());
            }
        } else {
            System.out.println("Such a field already exists.");
        }
        System.out.println();
    }

    /** This method gets called when a player runs out of money, it removes that player from 'players' set object,
     * Also adds that removed player to eliminatedPlayers set so that when outputting the n-th round information (the task)
     * we will be able to output the eliminated players separately.
     * After doing those, the method checks every single property, and if the removed player has a property,
     * sets the owner of that property to null, also if there's a house on that property, destroys the house.
     * So that all the properties of the loser player become available again, then outputs which properties are available again.
     * @param p the player that will be removed.
     * @param players the players set object that the player will be removed from.
     */
    private static void eliminatePlayer(Player p, Set<Player> players) {
        eliminatedPlayers.add(p);
        players.remove(p);
        Set<PropertyField> hisOldFields = new LinkedHashSet<>();
        // Eliminated player's all property will be available again.
        for(Field f : CyclicalBoard.getFields()) {
            if(f.getClassName().equals("PropertyField")) {
                PropertyField propertyField = ((PropertyField) (f));
                if((p.equals(propertyField.getOwner()))) {
                    hisOldFields.add(propertyField);
                    propertyField.setOwner(null);
                    if(propertyField.isThereHouse()) {
                        propertyField.destroyHouse();
                    }
                }
            }
        }

        // In case he did not have any property.
        if(hisOldFields.size() == 0) {
            System.out.println(p + " did not have any property.");
        }
        // If he did,
        else {
            System.out.print(p + "'s properties " + ((hisOldFields.size() > 1) ? "were " : "was "));
            for(PropertyField p2 : hisOldFields) {
                System.out.print(p2 + " ");
            }
            System.out.println();
            System.out.println("Now they are all available again.");
        }
        System.out.println();
    }
}
