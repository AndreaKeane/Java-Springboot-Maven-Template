package com.example.garden.repository;

import ch.qos.logback.core.util.StringUtil;

public enum PlantType {

    BROCCOLI,
    CHARD,
    EGGPLANT,
    KALE,
    MARIGOLD,
    SPINACH,
    TOMATO,

    UNKNOWN;

    public static PlantType fromString(String type) {
        type = StringUtil.nullStringToEmpty(type);
        String cleanString = type.replace(" ", "").toUpperCase();
        switch (cleanString) {
            case "BROCCOLI":
                return PlantType.BROCCOLI;
            case "CHARD":
                return PlantType.CHARD;
            case "EGGPLANT":
                return PlantType.EGGPLANT;
            case "KALE":
                return PlantType.KALE;
            case "MARIGOLD":
                return PlantType.MARIGOLD;
            case "SPINACH":
                return PlantType.SPINACH;
            case "TOMATO":
                return PlantType.TOMATO;
            default:
                return PlantType.UNKNOWN;
        }
    }
}
