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

public class LuckyField extends Field {

    // Lucky fields won't have names as property fields have, instead they will have ID numbers, so the names of the
    // Lucky fields will be like Lucky Field 1, Lucky Field 2 etc.
    private static int ID = 1;

    /** When a new LuckyField object gets created, the static ID number will be passed as a String to the
     * constructor of the parameter, and this number will be incremented for the next object that can be created.
     * @param amount Determines how much money the players will earn when they get to this field.
     */
    public LuckyField(int amount) {
        super(("Lucky Field " + Integer.toString(ID++)), amount); // The ID will be incremented after passing its value to the name.
    }

    /** the purpose of this method was declared on 'Field' class. */
    @Override
    public String getClassName() {
        return "LuckyField";
    }
}
