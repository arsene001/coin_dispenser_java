package com.app.coin_dispenser.domain;

import java.math.BigDecimal;

public enum Currency {
    HUNDRED_RANDS(new BigDecimal(100)),
    FIFTY_RANDS(new BigDecimal(50)),
    TWENTY_RANDS(new BigDecimal(20)),
    TEN_RANDS(new BigDecimal(10)),
    FIVE_RANDS(new BigDecimal(5)),
    TWO_RANDS(new BigDecimal(2)),
    ONE_RAND(new BigDecimal(1)),
    FIFTY_CENTS(new BigDecimal(0.5)),
    TWENTY_FIVE_CENTS(new BigDecimal(0.25)),//Though there is no 25 cents in RAND
    TEN_CENTS(new BigDecimal(0.1)),
    FIVE_CENTS(new BigDecimal(0.05));

    private final BigDecimal value;

    Currency(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getValue() {
        return this.value;
    }
}
