package com.mat.hyb.school.isas.provider;

import org.androidannotations.annotations.EBean;

/**
 * Created by matous on 5.12.13.
 */
@EBean
public class ClassIdProvider {

    private int[] substitutionClassIds = new int[19];
    private String[] classNames = new String[19];

    public ClassIdProvider() {
        initialiseArrays();
    }

    public String[] getClassNames() {
        return classNames;
    }

    public int getClassId(int id) {
        return substitutionClassIds[id];
    }

    private void initialiseArrays() {
        // 1A
        substitutionClassIds[0] = 91;
        classNames[0] = "1A";
        // 1B
        substitutionClassIds[1] = 92;
        classNames[1] = "1B";
        // prA
        substitutionClassIds[2] = 90;
        classNames[2] = "prA";
        // 2A
        substitutionClassIds[3] = 86;
        classNames[3] = "2A";
        // 2B
        substitutionClassIds[4] = 87;
        classNames[4] = "2B";
        // skA
        substitutionClassIds[5] = 85;
        classNames[5] = "skA";
        // 3A
        substitutionClassIds[6] = 81;
        classNames[6] = "3A";
        // 3B
        substitutionClassIds[7] = 82;
        classNames[7] = "3B";
        // teA
        substitutionClassIds[8] = 83;
        classNames[8] = "teA";
        // 4A
        substitutionClassIds[9] = 77;
        classNames[9] = "4A";
        // 4B
        substitutionClassIds[10] = 78;
        classNames[10] = "4B";
        // krA
        substitutionClassIds[11] = 79;
        classNames[11] = "krA";
        // knA
        substitutionClassIds[12] = 74;
        classNames[12] = "knA";
        // sxA
        substitutionClassIds[13] = 69;
        classNames[13] = "sxA";
        // sxB
        substitutionClassIds[14] = 70;
        classNames[14] = "sxB";
        // spA
        substitutionClassIds[15] = 66;
        classNames[15] = "spA";
        // spB
        substitutionClassIds[16] = 67;
        classNames[16] = "spB";
        // okA
        substitutionClassIds[17] = 63;
        classNames[17] = "okA";
        // okB
        substitutionClassIds[18] = 64;
        classNames[18] = "okB";
    }
}