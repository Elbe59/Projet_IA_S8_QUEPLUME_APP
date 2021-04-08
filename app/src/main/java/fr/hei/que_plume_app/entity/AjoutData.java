package fr.hei.que_plume_app.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class AjoutData {

    private String type_trouve;
    private String couleur_trouvee;
    private String type_reel;
    private String couleur_reelle;
    private String date;


    public AjoutData(){}


    public String getType_trouve() {
        return type_trouve;
    }

    public void setType_trouve(String type_trouve) {
        this.type_trouve = type_trouve;
    }

    public String getCouleur_trouvee() {
        return couleur_trouvee;
    }

    public void setCouleur_trouvee(String couleur_trouvee) {
        this.couleur_trouvee = couleur_trouvee;
    }

    public String getType_reel() {
        return type_reel;
    }

    public void setType_reel(String type_reel) {
        this.type_reel = type_reel;
    }

    public String getCouleur_reelle() {
        return couleur_reelle;
    }

    public void setCouleur_reelle(String couleur_reel) {
        this.couleur_reelle = couleur_reel;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) throws ParseException {
        this.date = date;
    }

    public String toString(){
        return (couleur_reelle +" - " + couleur_trouvee +" - " + date  +" - " + type_reel +" - " + type_trouve);
    }


}
