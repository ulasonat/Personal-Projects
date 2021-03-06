// Ulaş Onat Alakent
//
// yl9i70
//
// Hunting Game (Task 3)
//
// 2018/11/18 22:56:06
//
// This solution was submitted and prepared by Ulaş Onat Alakent, yl9i70 for the
// Hunting Game (Task 3) assignment of the Practical software engineering I. course.
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

package yl9i70;

public class Board {

    private Area[][] board;
    private final int sizeOfBoard;

    /** Sets sizeOfBoard to be 'n' and fills in the 'board' array field. */
    public Board(int n) { // n = length of the one edge of the board.
        sizeOfBoard = n;
        board = new Area[n][n];

        for (int i=0; i<n; i++) {
            for (int j=0; j<n; j++) {
                board[i][j] = new Area();
            }
        }
    }

    /** @return returns the length of the one edge of the board */
    public int getBoardSize() {
        return sizeOfBoard;
    }

}
