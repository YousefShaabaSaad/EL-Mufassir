package com.yousef.el_mufassir.model;

public class Constants {
    public final String NUM_OF_OPEN_SOURA="numOfOpenSoura";
    public final String NUM_OF_OPEN_AYA="numOfOpenAya";
    public final String NEW_USERS="New Users";
    public final String BLOCK_USER="Block Users";
    public final String OPEN="Open";
    public final String QURAN="Quran";
    public final String NUMBER_OF_SOURA="Number Of Soura";
    public final String CHANNEL_ID = "المُفَسِّر";
    public final String CHANNEL = "Channel";
    public final String NUMBER = "Number";
    public final String NAME = "Name";
    public final String AYA = "Aya";
    public final String LOCK = "lock";
    public final String TAFSEER_TABLE="tafseerTable";
    public final String TAFSEER_ID="IdTafseer";
    public final String AYA_TEXT="ayaText";
    public final String TAFSEER_TEXT="TafseerText";
    public final String TAFSEER_LOCK="lockTafseer";
    public final String NEW="New";
    public final String EDIT="Edit";
    public final String BLOCK="Block";
    public final String NO="No";
    public final String YES="Yes";

    public static Constants constants;

    private Constants (){
    }

    public static Constants newInstance(){
        if(constants==null){
            constants=new Constants();
        }
        return constants;
    }
}
