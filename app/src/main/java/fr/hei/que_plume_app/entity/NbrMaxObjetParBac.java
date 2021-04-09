package fr.hei.que_plume_app.entity;

import java.util.HashMap;
import java.util.Map;

public class NbrMaxObjetParBac {
    private Integer nbrMax_boite_blanc;
    private Integer nbrMax_boite_noir;
    private Integer nbrMax_couvercle_blanc;
    private Integer nbrMax_couvercle_noir;
    private Integer nbrMax_goupille_gris;
    private Integer nbrMax_goupille_rouge;

    public NbrMaxObjetParBac(){}

    public Integer getNbrMax_boite_blanc() {
        return nbrMax_boite_blanc;
    }

    public void setNbrMax_boite_blanc(Integer nbrMax_boite_blanc) {
        this.nbrMax_boite_blanc = nbrMax_boite_blanc;
    }

    public Integer getNbrMax_boite_noir() {
        return nbrMax_boite_noir;
    }

    public void setNbrMax_boite_noir(Integer nbrMax_boite_noir) {
        this.nbrMax_boite_noir = nbrMax_boite_noir;
    }

    public Integer getNbrMax_couvercle_blanc() {
        return nbrMax_couvercle_blanc;
    }

    public void setNbrMax_couvercle_blanc(Integer nbrMax_couvercle_blanc) {
        this.nbrMax_couvercle_blanc = nbrMax_couvercle_blanc;
    }

    public Integer getNbrMax_couvercle_noir() {
        return nbrMax_couvercle_noir;
    }

    public void setNbrMax_couvercle_noir(Integer nbrMax_couvercle_noir) {
        this.nbrMax_couvercle_noir = nbrMax_couvercle_noir;
    }

    public Integer getNbrMax_goupille_gris() {
        return nbrMax_goupille_gris;
    }

    public void setNbrMax_goupille_gris(Integer nbrMax_goupille_gris) {
        this.nbrMax_goupille_gris = nbrMax_goupille_gris;
    }

    public Integer getNbrMax_goupille_rouge() {
        return nbrMax_goupille_rouge;
    }

    public void setNbrMax_goupille_rouge(Integer nbrMax_goupille_rouge) {
        this.nbrMax_goupille_rouge = nbrMax_goupille_rouge;
    }

    public Map<String,Integer> getHashMapNbrObjetParBac(){
        Map<String,Integer> mapMaxNbrObjet = new HashMap<String,Integer>();
        mapMaxNbrObjet.put("goupille_gris", getNbrMax_goupille_gris());
        mapMaxNbrObjet.put("goupille_rouge",getNbrMax_goupille_rouge());
        mapMaxNbrObjet.put("couvercle_noir",getNbrMax_couvercle_noir());
        mapMaxNbrObjet.put("couvercle_blanc",getNbrMax_couvercle_blanc());
        mapMaxNbrObjet.put("boite_noir", getNbrMax_boite_noir());
        mapMaxNbrObjet.put("boite_blanc", getNbrMax_boite_blanc());
        return mapMaxNbrObjet;
    }

}
