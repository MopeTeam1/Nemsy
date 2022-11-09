package com.example.nemsy;


public class Bill {
    String BILL_ID;
    String BILL_NO;
    String BILL_NAME;
    String COMMITTEE;
    String PROPOSE_DT;
    String PROC_RESULT;
    String AGE;
    String DETAIL_LINK;
    String PROPOSER;
    String MEMBER_LIST;
    String RST_PROPOSER;
    String PUBL_PROPOSER;
    String COMMITTEE_ID;

    public Bill(String BILL_NAME, String PROPOSE_DT, String AGE, String DETAIL_LINK, String PROPOSER) {
        this.BILL_NAME = BILL_NAME;
        this.PROPOSE_DT = PROPOSE_DT;
        this.AGE = AGE;
        this.DETAIL_LINK = DETAIL_LINK;
        this.PROPOSER = PROPOSER;
    }
}
