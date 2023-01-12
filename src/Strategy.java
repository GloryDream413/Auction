import java.time.LocalDateTime;
import java.lang.Math;

public class Strategy implements Bidder {
    int m_quantity; // variable includes the total QUs
    int m_cashamount; // vaiable includes the total MUs
    int m_prevquantity; // variable includes the QUs have already auctioned
    int m_prevcashamount; // variable includes the MUs that user has already consumed.
    int m_nextBid; // variable which includes the user's next bid.
    boolean m_bFisrtBid;// variable if the userbid is first or not.

    // In this function sets the QUs and MUs
    public void init(int quantity, int cash) {
        m_quantity = quantity;
        m_cashamount = cash;
        m_prevcashamount = m_prevquantity = 0;
    }

    // returns the user's next bid
    public int placeBid() {
        int nextBid = 0;
        int nAvaliable = 0;
        LocalDateTime date;
        int nCurrentSecond = 0;
        int isBid = 0;// determine if bid or not.

        int nRestAmout = m_cashamount - m_prevcashamount;
        nAvaliable = (m_quantity - m_prevquantity) / 2;

        // ----------------------------------------- Strategy to determine the bids
        // amount. -----------------------------------------
        // That means your bid is first. First bid must be powerful.
        if (nAvaliable > 0) {
            if (nRestAmout == m_cashamount) {
                if (nAvaliable == 1) {
                    nextBid = nRestAmout;
                } else {
                    nextBid = (nRestAmout / nAvaliable) * 2;
                }
            } else {
                date = LocalDateTime.now();
                nCurrentSecond = date.toLocalTime().toSecondOfDay();
                isBid = ((int) (Math.random() * 2) * nCurrentSecond) % 2;
                if (isBid > 0) {
                    nextBid = (nRestAmout / nAvaliable) * 2;
                } else {
                    nextBid = (int) ((nRestAmout / nAvaliable) * 0.5);
                }
            }
        }

        m_nextBid = nextBid;
        m_prevcashamount += m_nextBid;
        m_prevquantity += 2;
        return m_nextBid;
    }

    // displays the user and computer's MUs
    public void bids(int own, int other) {
        System.out.printf("UserBid:%d ComputerBid:%d ", own, other);
    }

    // set the bid user has been bidded
    public void setNextbid(int cash) {
        m_nextBid = cash;
        m_prevcashamount += m_nextBid;
        m_prevquantity += 2;
    }
}
