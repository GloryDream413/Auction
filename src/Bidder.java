public interface Bidder {
    // In this function sets the QUs and MUs
    void init(int quantity, int cash);

    // returns the user's next bid
    int placeBid();

    // displays the user and computer's MUs
    void bids(int own, int other);

    // set the bid user has been auctioned
    void setNextbid(int cash);
            }
