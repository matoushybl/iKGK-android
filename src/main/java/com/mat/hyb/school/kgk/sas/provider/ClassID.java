package com.mat.hyb.school.kgk.sas.provider;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:hyblmatous@gmail.com">Matous Hybl</a>
 */
public enum ClassID {
    PRA("prA", 95),
    SKA("skA", 90),
    TEA("teA", 85),
    KRA("krA", 83),
    KNA("knA", 79),
    SXA("sxA", 74),
    SPA("spA", 69),
    SPB("spB", 70),
    OKA("okA", 66),
    OKB("okB", 67),
    ONEA("1A", 96),
    ONEB("1B", 97),
    TWOA("2A", 91),
    TWOB("2B", 92),
    THREEA("3A", 86),
    THREEB("3B", 87),
    FOURA("4A", 81),
    FOURB("4B", 82);

    private String name;
    private int id;

    ClassID(String name, int id) {
        this.name = name;
        this.id = id;
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

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
