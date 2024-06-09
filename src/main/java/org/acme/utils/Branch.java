package org.acme.utils;

public enum Branch {
    BUENOS_AIRES("Buenos Aires"),
    CORDOBA("Córdoba"),
    ROSARIO("Rosario"),
    MENDOZA("Mendoza"),
    LA_PLATA("La Plata"),
    SAN_MIGUEL_DE_TUCUMAN("San Miguel de Tucumán"),
    MAR_DEL_PLATA("Mar del Plata"),
    SALTA("Salta"),
    SANTA_FE("Santa Fe"),
    SAN_JUAN("San Juan");

    private final String location;

    Branch(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }
}
