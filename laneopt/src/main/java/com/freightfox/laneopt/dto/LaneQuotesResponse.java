package com.freightfox.laneopt.dto;

import java.util.List;

public class LaneQuotesResponse {

    private Long laneId;
    private String origin;
    private String destination;
    private QuoteResponse cheapestTransporter;
    private List<QuoteResponse> allQuotes;

    public LaneQuotesResponse() {
    }

    public LaneQuotesResponse(
            Long laneId,
            String origin,
            String destination,
            QuoteResponse cheapestTransporter,
            List<QuoteResponse> allQuotes) {
        this.laneId = laneId;
        this.origin = origin;
        this.destination = destination;
        this.cheapestTransporter = cheapestTransporter;
        this.allQuotes = allQuotes;
    }

    public Long getLaneId() {
        return laneId;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public QuoteResponse getCheapestTransporter() {
        return cheapestTransporter;
    }

    public List<QuoteResponse> getAllQuotes() {
        return allQuotes;
    }

    public static class QuoteResponse {

        private Long transporterId;
        private String transporterName;
        private Long quote;

        public QuoteResponse() {
        }

        public QuoteResponse(Long transporterId, String transporterName, Long quote) {
            this.transporterId = transporterId;
            this.transporterName = transporterName;
            this.quote = quote;
        }

        public Long getTransporterId() {
            return transporterId;
        }

        public String getTransporterName() {
            return transporterName;
        }

        public Long getQuote() {
            return quote;
        }
    }
}