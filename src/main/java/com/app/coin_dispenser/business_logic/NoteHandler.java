package com.app.coin_dispenser.business_logic;

import com.app.coin_dispenser.domain.Currency;
import com.app.coin_dispenser.domain.Note;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

public class NoteHandler {

    private Map<BigDecimal, Integer> denominations = new HashMap<>();

    public String calculateDenominationBreakDown(Note note) {

        BigDecimal total = calculateDifference(note.getCapturedAmount(), note.getAmountOwning())
                .setScale(2, RoundingMode.CEILING);
        BigDecimal difference = total;

        for (Currency c : Currency.values()) {

            if (total.compareTo(c.getValue()) >= 0) {
                total = denominate(total, c.getValue());
            }
        }

        //From here, we just formatting the string for displaying. Not logic per se

        //Sort the map by key
        Map<BigDecimal, Integer> sorted = denominations.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByKey()))
                .collect(toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2, LinkedHashMap::new));


        StringBuilder denominationData = new StringBuilder();

        for (Map.Entry<BigDecimal, Integer> entry : sorted.entrySet()) {

            if (entry.getKey().doubleValue() % 1 == 0) {
                denominationData.append(entry.getValue()).append(" x R").append(entry.getKey()).append(":");
            } else {
                denominationData.append(entry.getValue()).append(" x ").append(entry.getKey().multiply(new BigDecimal(100))).append("cent:");
            }

        }

        return difference + "#" + denominationData;
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
