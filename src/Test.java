import java.io.File; // Import the File class
import java.util.Scanner;
import java.lang.Math;
import java.time.LocalDateTime;

public class Test {
    public static void main(String[] args) throws Exception {
        int quantity = 0;// variable includes the QUs
        int cashamount = 0;// variable includes the MUs
        int usercashamount = 0;// variable includes user's rest MUs
        int computercashamount = 0;// variable includes computer's rest MUs
        int userBid = 0; // variable for User's Bid
        int computerBid = 0; // variable for Computer's Bid
        int userGettingQuantity = 0; // QUs which user getting.
        int computerGettingQuantity = 0; // QUs which computer getting.
        LocalDateTime date;
        int nCurrentSecond = 0;
        Scanner reader;
        String testcasePath = new java.io.File(".").getCanonicalPath();

        // Variables for test
        int userReadBidAmount = 0;
        int computerRealBidAmount = 0;
        int nBidTimes = 0;
        int nToBidTimes = 0;

        while (true) {
            quantity = cashamount = usercashamount = computercashamount = userBid = computerBid = userGettingQuantity = computerGettingQuantity = userReadBidAmount = computerRealBidAmount = nBidTimes = nToBidTimes = 0;
            System.out.println("Input the testcase number:(0~20)\n");
            reader = new Scanner(System.in);
            int nTestNumber = reader.nextInt(); // input the QUs
            if (nTestNumber == 0) {
                reader.close();
                break;
            }
            if (nTestNumber < 1 || nTestNumber > 20) {
                System.out.println("Incorrect testcase number.\n");
                continue;
            }
            String strRealTestCasePath = testcasePath + "\\bin\\testcases\\";
            strRealTestCasePath += Integer.toString(nTestNumber);
            strRealTestCasePath += ".testcase";
            File myObj = new File(strRealTestCasePath);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] part = data.split(" ");
                quantity = Integer.parseInt(part[0]);
                cashamount = Integer.parseInt(part[1]);
            }
            myReader.close();
            usercashamount = computercashamount = cashamount;// Initialize the user and computer's MUs
            nToBidTimes = quantity / 2;// Times to bid
            // So if quantity isn't 2n number, then doesn't calculate
            if (quantity < 0) {
                System.out.println("QUs must be positive.\n");
                if (nTestNumber == 5 || nTestNumber == 7) {
                    System.out.println("Ok.\n");
                    continue;
                }
            }

            if (cashamount < 0) {
                System.out.println("MUs must be positive.\n");
                if (nTestNumber == 6 || nTestNumber == 7) {
                    System.out.println("Ok.\n");
                    continue;
                }
            }

            if (quantity % 2 != 0) {
                System.out.println("Input again QU\nQU must be 2n number.");
                if (nTestNumber == 1) {
                    System.out.println("Ok.\n");
                }
            } else {
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

                        userReadBidAmount += userBid;
                        computerRealBidAmount += computerBid;
                        nBidTimes++;
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

                        userReadBidAmount += userBid;
                        computerRealBidAmount += computerBid;
                        nBidTimes++;
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

                // Test this program is correct
                if (userReadBidAmount != cashamount) {
                    System.out.println("UserBidAmout is incorrect.\nNot Ok.\n");
                } else if (computerRealBidAmount != cashamount) {
                    System.out.println("ComputerBidAmout is incorrect.\nNot Ok.\n");
                } else if (nBidTimes != nToBidTimes) {
                    System.out.println("BidTimes is incorrect.\nNot Ok.\n");
                } else {
                    System.out.println("Ok.\n");
                }
            }
        }
    }
}