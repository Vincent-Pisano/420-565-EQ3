package com.eq3.backend.utils;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

public class UtilsGenerator {

    public final static Font LARGE_BOLD = new Font(Font.FontFamily.HELVETICA, 32, Font.BOLD);
    public final static Font MEDIUM_BOLD = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
    public final static Font STANDARD = new Font(Font.FontFamily.HELVETICA, 12);

    public final static BaseColor BG_COLOR = new BaseColor(230,230,230);

    public final static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public final static DateFormat FORMATTER_START_END = new SimpleDateFormat("dd-MM-yyyy");

    public final static float WIDTH = 150F;
    public final static float[] COLUMN_WIDTH = {WIDTH};
    public final static float WIDTH_SIGNATURE_TITLE = 600F;
    public final static float HEIGHT_SIGNATURE_TITLE = 50F;
    public final static float WIDTH_SIGNATURE_TABLE = 500F;
    public final static float WIDTH_SIGNATURE_IMAGE = 200F;
    public final static float HEIGHT_SIGNATURE_IMAGE = 75F;
    public final static float X_SIGNATURE_TABLE = 35F;

    public final static float Y_SIGNATURE_TITLE_MONITOR = 675F;
    public final static float Y_SIGNATURE_TABLE_MONITOR = 625F;

    public final static float Y_SIGNATURE_TITLE_STUDENT = 500F;
    public final static float Y_SIGNATURE_TABLE_STUDENT = 450F;

    public final static float Y_SIGNATURE_TITLE_INTERNSHIP_MANAGER = 325F;
    public final static float Y_SIGNATURE_TABLE_INTERNSHIP_MANAGER = 275F;

    public final static float NO_SPACE = 0f;
    public final static float TINY_SPACE = 10f;
    public final static float SMALLER_SPACE = 15f;
    public final static float SMALL_SPACE = 20f;
    public final static float BELOW_MEDIUM_SPACE = 30f;
    public final static float MEDIUM_SPACE = 40f;
    public final static float LARGE_SPACE = 50f;
    public final static float START_SPACE = 325f;
    public final static float END_SPACE = 350f;

    public final static int PADDING_TABLE_CONDITIONS = 7;
    public final static int PADDING_TABLE_SIGNATURES = 3;

    public final static String CONTRACT_TITLE = "CONTRAT DE STAGE";
    public final static String AGREEMENT_BETWEEN = "ENTENTE DE STAGE INTERVENUE ENTRE LES PARTIES SUIVANTES";
    public final static String STUDENT = "L’étudiant(e): ";
    public final static String INTERNSHIP_MANAGER = "Le gestionnaire de stage: ";
    public final static String MONITOR = "L’employeur: ";
    public final static String DATE = "Date";
    public final static String AND = "et";
    public final static String SALARY = "SALAIRE";
    public final static String EMPTY = " ";
    public final static String PARTICIPANTS = "Dans le cadre de la formule ATE, les parties citées ci-dessous :";
    public final static String COMA = ",";
    public final static String DURATION = "DURÉE DU STAGE";
    public final static String TERMS_OF_CONDITION = "Conviennent des conditions de stage suivantes :";
    public final static String PLACE = "ENDROIT DU STAGE";
    public final static String ADDRESS = "Adresse : ";
    public final static String START_DATE = "Date de début : ";
    public final static String END_DATE = "Date de fin : ";
    public final static String NUMBER_WEEKS = "Nombre de semaines : ";
    public final static String WORK_HOURS = "HORAIRE DE TRAVAIL";
    public final static String WORKSHIFT_TYPE = "Type d'horaire: ";
    public final static String DAY = "Jour";
    public final static String NIGHT = "Nuit";
    public final static String FLEXIBLE = "Flexible";
    public final static String NOTHING = "";
    public final static String AND_DAY = " (et ";
    public final static String END_DAY = " jours)";
    public final static String DOUBLE_FORMAT = "%.2f";
    public final static String TOTAL_HOURS = "Nombre d'heures total par semaine: ";
    public final static String HOURS = " heures";
    public final static String SALARY_HOURS = "Salaire horaire: ";
    public final static String DOLLAR = "$";
    public final static String TASKS_RESPONSIBILITIES = "TACHES ET RESPONSABILITES DU STAGIAIRE";
    public final static String RESPONSIBILITIES = "RESPONSABILITES";
    public final static String SIGNATURES = "SIGNATURES";
    public final static String ONCE_SIGNED = "En foi de quoi les parties ont signé, ";
    public final static String COLLEGE_COMMITMENTS = "Le Collège s’engage à :";
    public final static String ENTERPRISE_COMMITMENTS = "L’entreprise s’engage à :";
    public final static String STUDENT_COMMITMENTS = "L’étudiant s’engage à :";
    public final static String CONTRACT_CONDITIONS = "Les parties s’engagent à respecter cette entente de stage";
}
