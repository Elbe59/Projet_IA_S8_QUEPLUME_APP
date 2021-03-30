package fr.hei.que_plume_app.entity;

import java.time.LocalDateTime;

public class ErreurIA {

    private String type_trouve;
    private String couleur_trouvee;
    private String type_reel;
    private String couleur_reel;
    private String date;
    private String id;
    private String image;

    public ErreurIA(){}

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

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

    public String getCouleur_reel() {
        return couleur_reel;
    }

    public void setCouleur_reel(String couleur_reel) {
        this.couleur_reel = couleur_reel;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
