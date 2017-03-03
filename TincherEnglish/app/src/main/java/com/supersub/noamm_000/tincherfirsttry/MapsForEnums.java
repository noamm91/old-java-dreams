package com.supersub.noamm_000.tincherfirsttry;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by noamm_000 on 31/08/2016.
 */
public class MapsForEnums {
    // Building 2 one-way maps in order to functionality make bidirectional map for education
    // Making the same for each enum value

    static Map<String, Teacher.Education> stringToEducation;
    static {
        stringToEducation = new HashMap<>();
        stringToEducation.put("תיכונית", Teacher.Education.High_school_Diploma);
        stringToEducation.put("תואר ראשון", Teacher.Education.First_Degree);
        stringToEducation.put("מעל תואר ראשון", Teacher.Education.Second_Degree_Or_Above);
        stringToEducation.put("ללא", Teacher.Education.None);
    }

    static Map<Teacher.Education, String> educationToString;
    static {
        educationToString = new HashMap<>();
        educationToString.put(Teacher.Education.High_school_Diploma, "תיכונית");
        educationToString.put(Teacher.Education.First_Degree, "תואר ראשון");
        educationToString.put(Teacher.Education.Second_Degree_Or_Above, "מעל תואר ראשון");
        educationToString.put(Teacher.Education.None, "ללא");
    }

    static Map<String, Teacher.AOI> stringToAOI;
    static  {
        stringToAOI = new HashMap<>();
        stringToAOI.put("השרון", Teacher.AOI.HaSharon);
        stringToAOI.put("המרכז", Teacher.AOI.HaMerkaz);
        stringToAOI.put("השפלה", Teacher.AOI.HaShfela);
        stringToAOI.put("הדרום", Teacher.AOI.HaDarom);
        stringToAOI.put("הצפון", Teacher.AOI.HaTzafon);
    }

    static Map<Teacher.AOI, String> aoiToString;
    static {
        aoiToString = new HashMap<>();
        aoiToString.put(Teacher.AOI.HaSharon, "השרון");
        aoiToString.put(Teacher.AOI.HaMerkaz, "המרכז");
        aoiToString.put(Teacher.AOI.HaShfela, "השפלה");
        aoiToString.put(Teacher.AOI.HaDarom, "הדרום");
        aoiToString.put(Teacher.AOI.HaTzafon, "הצפון");
    }

    static Map<String, Teacher.SchoolType> stringToSchoolType;
    static {
        stringToSchoolType = new HashMap<>();
        stringToSchoolType.put("ממלכתי", Teacher.SchoolType.State);
        stringToSchoolType.put("דתי", Teacher.SchoolType.Religious);
        stringToSchoolType.put("דמוקרטי", Teacher.SchoolType.Democratic);
        stringToSchoolType.put("לא משנה", Teacher.SchoolType.Nevermind);
    }

    static Map<Teacher.SchoolType, String> schoolTypeToString;
    static {
        schoolTypeToString = new HashMap<>();
        schoolTypeToString.put(Teacher.SchoolType.State, "ממלכתי");
        schoolTypeToString.put(Teacher.SchoolType.Religious, "דתי" );
        schoolTypeToString.put(Teacher.SchoolType.Democratic, "דמוקרטי");
        schoolTypeToString.put(Teacher.SchoolType.Nevermind, "לא משנה");
    }

}
