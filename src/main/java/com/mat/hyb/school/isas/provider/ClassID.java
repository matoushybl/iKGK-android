package com.mat.hyb.school.isas.provider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matous on 30.4.14 for iSAS.
 */
public enum ClassID {
    PRIMAA("prA", 90),
    SEKA("skA", 85),
    TEA("teA", 83),
    KRA("krA", 79),
    KNA("knA", 74),
    SXA("sxA", 69),
    SXB("sxB", 70),
    SPA("spA", 66),
    SPB("spB", 67),
    OKA("okA", 63),
    OKB("okB", 64),
    ONEA("1A", 91),
    ONEB("1B", 92),
    TWOA("2A", 86),
    TWOB("2B", 87),
    THREEA("3A", 81),
    THREEB("3B", 82),
    FOURA("4A", 77),
    FOURB("4B", 78);

    private String name;
    private int id;

    ClassID(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public static List<String> getNames() {
        List<String> names = new ArrayList<String>();
        for (ClassID enu : ClassID.values()) {
            names.add(enu.getName());
        }
        return names;
    }

    public static ClassID getEnumFromString(String string) {
        return valueOf(string);
    }

    public static ClassID getEnumByName(String name) {
        for (ClassID id : ClassID.values()) {
            if (id.getName().equals(name)) {
                return id;
            }
        }
        return SXA;
    }
}
