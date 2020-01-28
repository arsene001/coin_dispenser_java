package com.app.coin_dispenser.business_logic;

import com.app.coin_dispenser.domain.Currency;
import com.app.coin_dispenser.domain.Note;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class NoteHandler {

    private Map<BigDecimal, Integer> denominations = new HashMap<BigDecimal, Integer>();

    public Map<BigDecimal, Integer> calculateDenominationBreakDown(Note note) {

        BigDecimal total = calculateDifference(note.getCapturedAmount(), note.getAmountOwning());

        for (Currency c : Currency.values()) {

            if (total.compareTo(c.getValue()) >= 0) {
                total = denominate(total, c.getValue());
            }
        }

        return denominations;
    }

    private BigDecimal calculateDifference(BigDecimal capturedAmount, BigDecimal amountOwning) {
        return capturedAmount.subtract(amountOwning);
    }

    private BigDecimal denominate(BigDecimal total, BigDecimal denomination) {

        int count = 0;

        while (total.compareTo(denomination) >= 0) {

            total = total.subtract(denomination);
            count++;
        }

        denominations.put(denomination, count);

        return total;
    }
}
