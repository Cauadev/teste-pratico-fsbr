package com.cauadev.teste_pratico_fsbr.domain.parking.enums;

public enum ParkingSpotType {
    COMMON("Comum"),
    VIP("VIP");

    private final String description;

    ParkingSpotType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
