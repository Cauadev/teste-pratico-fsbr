package com.cauadev.teste_pratico_fsbr.domain.parking.enums;

public enum ParkingSpotStatus {
    AVAILABLE("Disponível"),
    RESERVED("Reservado");

    private final String description;

    ParkingSpotStatus(String description) {
        this.description = description;
    }


    public String getDescription() {
        return description;
    }
}
