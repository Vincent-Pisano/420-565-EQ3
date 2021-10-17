package com.eq3.backend.utils;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;

import java.time.format.DateTimeFormatter;

public class UtilsGenerator {

    public final static Font LARGE_BOLD = new Font(Font.FontFamily.HELVETICA, 32, Font.BOLD);
    public final static Font MEDIUM_BOLD = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
    public final static Font STANDARD = new Font(Font.FontFamily.HELVETICA, 12);

    public final static BaseColor BG_COLOR = new BaseColor(230,230,230);

    public final static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public final static String CONTRACT_TITLE = "CONTRAT DE STAGE";
    public final static String AGREEMENT_BETWEEN = "ENTENTE DE STAGE INTERVENUE ENTRE LES PARTIES SUIVANTES";
    public final static String STUDENT = "L’étudiant(e): ";
    public final static String INTERNSHIP_MANAGER = "Le gestionnaire de stage: ";
    public final static String EMPLOYER = "L’employeur: ";
    public final static String DATE = "Date";
    public final static String AND = "et";
    public final static String SALARY = "SALAIRE";
    public final static String EMPTY = " ";
    public final static String PARTICIPANTS = "Dans le cadre de la formule ATE, les parties citées ci-dessous :";

}
