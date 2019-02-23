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

import java.io.*;
import java.util.Scanner;

/** This class will open the file, read all the input and close the file. And if any error occurs during these operations,
 * the method that has problem, will return false, and the main program will stop running. */

public class ReadFile {

    private static Scanner fileReader;

    /** Tries opening the file, in case it cannot, returns false, and the program stops running.
     * @param fileName The file name to open the file.
     * */
    public static boolean openFile(String fileName) {
        try {
            fileReader = new Scanner(new File(fileName));
            System.out.println("File successfully opened.");
        } catch (FileNotFoundException e) {
            System.out.println("File could not been opened.");
            return false;
        }
        return true;
    }

    /** Reads all the data from the file, by order:
     * number of fields,
     * type of fields and their additional information (if the type is property, reads the city name, otherwise reads the amount),
     * number of players,
     * the names of the players and their strategies,
     * number of dices,
     * the dices,
     * And during all this reading, if the file contains an invalid data, (for example an invalid field type, or a dice
     * that is greater than 6, or less than 1, or an invalid strategy), or the ordering of the inputs are wrong returns false,
     *
     * @return true if there is no problem with reading the file., returns false if there was an problem with reading
     * the file, also this causes the main program to stop working.
     */
    public static boolean readFile() {
        try {
            while (fileReader.hasNext()) {
                int number = fileReader.nextInt();
                fileReader.nextLine();
                System.out.println("There are " + number + " fields.");
                for (int i = 0; i < number; i++) {
                    String typeOfField = fileReader.next();
                    switch (typeOfField) {
                        case "PropertyField":
                            String cityName = fileReader.next();
                            CapitalyGame.addFields(new PropertyField(cityName));
                            break;
                        case "LuckyField": {
                            int amount = fileReader.nextInt();
                            fileReader.nextLine();
                            CapitalyGame.addFields(new LuckyField(amount));
                            break;
                        }
                        case "ServiceField": {
                            int amount = fileReader.nextInt();
                            fileReader.nextLine();
                            CapitalyGame.addFields(new ServiceField(amount));
                            break;
                        }
                        default:
                            System.out.println("Invalid field input.");
                            return false;
                    }
                }
                number = fileReader.nextInt(); // number of players
                fileReader.nextLine();
                for (int i = 0; i < number; i++) {
                    String nameOfPlayer = fileReader.next();
                    String nameOfStrategy = fileReader.next();
                    Player.Strategy strategy;

                    switch (nameOfStrategy) {
                        case "Greedy":
                            strategy = Player.Strategy.GREEDY;
                            break;
                        case "Careful":
                            strategy = Player.Strategy.CAREFUL;
                            break;
                        case "Tactical":
                            strategy = Player.Strategy.TACTICAL;
                            break;
                        default:
                            System.out.println("Invalid strategy input.");
                            return false;
                    }

                    CapitalyGame.addPlayer(new Player(nameOfPlayer, strategy));
                }
                int numberOfDices = fileReader.nextInt();
                for(int i=0; i<numberOfDices; i++) {
                    // If the input is less than 1, or greater than 6, returns false.
                    if(!CapitalyGame.addRollDice(fileReader.nextInt())) {
                        System.out.println("The dice must be between 1 and 6.");
                        return false;
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("There was an error with reading the input.");
            return false;
            // quits the program
        }

        return true;
    }

    /** Closes the file */
    public static void closeFile() {
        fileReader.close();
        System.out.println("File has been closed.");
    }

}
