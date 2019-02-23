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

import java.util.LinkedHashSet;
import java.util.Set;

/** This class represents the cyclical board, and it contains the fields, the round number.*/
public class CyclicalBoard {

    private static int roundNumber = 1;

    // We'll need to access fields without having an instance of 'CyclicalBoard' class, therefore we declare it 'static'.
    // We need to initialize the set to a LinkedHashSet, so that the order of the fields will be according to their
    // insertion order.
    private static Set<Field> fields =  new LinkedHashSet<>();

    /** This static method will be called from main method to add fields to fields Set object. */
    public static boolean addField(Field field) {
        // Since 'Field' declared abstract, we know that a class that extended Field will be the parameter.
        return fields.add(field);
    }

    // Getters
    public static Set<Field> getFields() {
        return fields;
    }

    public static int getRoundNumber() {
        return roundNumber;
    }

    /** Increments the roundNumber */
    public static void incrementRoundNumber() {
        roundNumber++;
    }



}
