package fr.hei.que_plume_app.entity;

import java.time.LocalDateTime;

public class ErreurIA {

    private String type;
    private String couleur;
    private String type_reel;
    private String couleur_reel;
    private LocalDateTime date;
    private int id;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public String getType_reel() {
        return type_reel;
    }

    public void setType_reel(String type_reel) {
        this.type_reel = type_reel;
    }

    public String getCouleur_reel() {
        return couleur_reel;
    }

    public void setCouleur_reel(String couleur_reel) {
        this.couleur_reel = couleur_reel;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ErreurIA(int _id, String _type, String _type_reel, String _couleur_reel, String _couleur, LocalDateTime _date)
    {
        this.id = _id;
        this.type=_type;
        this.couleur = _couleur;
        this.date = _date;
        this.couleur_reel = _couleur_reel;
        this.type_reel = _type_reel;
    }
}
