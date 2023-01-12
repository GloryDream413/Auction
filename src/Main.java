import java.util.Scanner;
import java.lang.Math;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) throws Exception {
        int quantity;// variable includes the QUs
        int cashamount;// variable includes the MUs
        int usercashamount;// variable includes user's rest MUs
        int computercashamount;// variable includes computer's rest MUs
        int userBid = 0; // variable for User's Bid
        int computerBid = 0; // variable for Computer's Bid
        int userGettingQuantity = 0; // QUs which user getting.
        int computerGettingQuantity = 0; // QUs which computer getting.
        LocalDateTime date;
        int nCurrentSecond = 0;

        Scanner reader = new Scanner(System.in);
        quantity = reader.nextInt(); // input the QUs
        cashamount = reader.nextInt(); // input the MUs
        usercashamount = computercashamount = cashamount;// Initialize the user and computer's MUs
        // So if quantity isn't 2n number, then doesn't calculate
        if (quantity % 2 != 0) {
            System.out.println("Input again QU\nQU must be 2n number.");
            reader.close();
        }
        usercashamount = computercashamount = cashamount;
        System.out.printf("Total QU : %d & Total MU : %d\n", quantity, cashamount);
        Strategy myStrategy = new Strategy();
        myStrategy.init(quantity, cashamount);
        // In this part auction result between user & computer is displayed.
        while (quantity > 0) {
            if (quantity == 2) {
                // If the rest QUs are 2 then user and computer bids their all rest MUs
                computerBid = computercashamount;
                userBid = usercashamount;
                myStrategy.setNextbid(userBid);

            } else {
                // get the userBid and computer's bid
                date = LocalDateTime.now();
                nCurrentSecond = date.toLocalTime().toSecondOfDay();
                if (computercashamount / 5 == 0) {
                    computerBid = Math.abs(((int) (Math.random() * computercashamount) * nCurrentSecond)
                            % computercashamount);
                } else {
                    computerBid = Math.abs(((int) (Math.random() * computercashamount) * nCurrentSecond)
                            % (computercashamount / 5));
                }
                computercashamount -= computerBid;// removes the computer's MUs that he is already bid
                userBid = myStrategy.placeBid();// This function call
                usercashamount -= userBid;
            }

            // In this part determines who won the bot.
            myStrategy.bids(userBid, computerBid);
            if (userBid > computerBid) {
                userGettingQuantity += 2;
                System.out.printf(" User get 2 QU\n");
            } else if (userBid < computerBid) {
                computerGettingQuantity += 2;
                System.out.printf(" Computer get 2 QU\n");
            } else {
                userGettingQuantity += 1;
                computerGettingQuantity += 1;
                System.out.printf(" They get 1 QU each\n");
            }
            quantity -= 2;
        }

        // Finally, determines if won in this auction.
        System.out.printf("UserGettingQuantity:%d ComputerGettingQuantity:%d\n", userGettingQuantity,
                computerGettingQuantity);
        if (userGettingQuantity > computerGettingQuantity) {
            System.out.println("So User Win the auction.\n");
        } else if (userGettingQuantity < computerGettingQuantity) {
            System.out.println("So Computer Win the auction.\n");
        } else {
            System.out.println("So They are the same.\n");
        }
        reader.close();
    }
}