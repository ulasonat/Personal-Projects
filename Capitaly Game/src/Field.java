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

/** This is the abstract 'Field' class that contains 1 method that needs to be overridden by subclasses in order to extend this class
 * and not be an abstract class. This class has common members (name and amount) that each field should have.*/
public abstract class Field {
    private String name;
    private int amount;

    public Field(String name, int amount) {
        this.name = name;
        this.amount = amount;
    }

    // Since we'll use the objects of the classes that are derived from 'Field' as keys in the Map, we need to override the
    // hashCode and equals methods.

    /** creates a unique hashCode for each object that will extend this class, also takes the advantage of the hashCode of String class.*/
    @Override
    public final int hashCode() {
        return this.name.hashCode() + 31;
    }

    /** overriding the equals method, since the objects of the classes that extend this class will be elements on a set. */
    @Override
    public final boolean equals(Object o) {
        if(this == o) // If this and o are the same reference, no need to look more, return true.
            return true;
        if(o == null || this.getClass() != o.getClass()) // If the o is null, or this and o are not the type of same class, return false.
            return false;

        return this.name.equals(((Field) o).getName());
    }

    // Getters

    public final String getName() {
        return name;
    }

    public final int getAmount() {
        return amount;
    }

    @Override
    public final String toString() {
        return this.name;
    }

    /** Since three classes extend 'Field' class, and in this program we'll be using a Field reference to access that three
     * class' objects, we will want to know which class' object we are going to deal with, therefore each class that will extend
     * class will have to override this method and return the name of itself. (the class name) */
    public abstract String getClassName();
}
