package com.app.coin_dispenser.domain;

import java.math.BigDecimal;

public class Note {
    private BigDecimal amountOwning;
    private BigDecimal capturedAmount;

    public Note(BigDecimal amountOwning, BigDecimal capturedAmount) {
        this.amountOwning = amountOwning;
        this.capturedAmount = capturedAmount;
    }

    public BigDecimal getAmountOwning() {
        return amountOwning;
    }

    public void setAmountOwning(BigDecimal amountOwning) {
        this.amountOwning = amountOwning;
    }

    public BigDecimal getCapturedAmount() {
        return capturedAmount;
    }

    public void setCapturedAmount(BigDecimal capturedAmount) {
        this.capturedAmount = capturedAmount;
    }
}
