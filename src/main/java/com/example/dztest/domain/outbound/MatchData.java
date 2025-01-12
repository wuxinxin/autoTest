package com.example.dztest.domain.outbound;

import lombok.Data;

@Data
public class MatchData {
    private String matchSource;
    private String matchValue;

    public MatchData(String matchSource, String matchValue) {
        this.matchSource = matchSource;
        this.matchValue = matchValue;
    }

    public String getMatchSource() {
        return matchSource;
    }

    public String getMatchValue() {
        return matchValue;
    }
}
