package fr.hei.que_plume_app.entity;

import java.util.HashMap;
import java.util.Map;

// Un objet AjoutData, contient le remplissage actuel des bacs ainsi que certains paramètre qui définissent si il y a
// ou non une erreur dans un des bacs.
public class ActualData {


    private Integer true_goupille_gris;
    private Integer true_goupille_rouge;
    private Integer true_couvercle_noir;
    private Integer true_couvercle_blanc;
    private Integer true_boite_noir;
    private Integer true_boite_blanc;


    private Integer false_goupille_gris;
    private Integer false_goupille_rouge;
    private Integer false_couvercle_noir;
    private Integer false_couvercle_blanc;
    private Integer false_boite_noir;
    private Integer false_boite_blanc;


    public ActualData(){}
// HashMap qui permet de lier le nom dans la base de donnée avec la valeur correspondante pour chacun des paramètres
    public Map<String, Integer> getMaHashMapDataActuel() {
        Map<String,Integer> maHashMapDataActuel = new HashMap<String,Integer>();
        maHashMapDataActuel.put("true_goupille_gris", getTrue_goupille_gris());
        maHashMapDataActuel.put("true_goupille_rouge",getTrue_goupille_rouge());
        maHashMapDataActuel.put("true_couvercle_noir",getTrue_couvercle_noir());
        maHashMapDataActuel.put("true_couvercle_blanc",getTrue_couvercle_blanc());
        maHashMapDataActuel.put("true_boite_noir", getTrue_boite_noir());
        maHashMapDataActuel.put("true_boite_blanc", getTrue_boite_blanc());
        maHashMapDataActuel.put("false_goupille_gris", getFalse_goupille_gris());
        maHashMapDataActuel.put("false_goupille_rouge",getFalse_goupille_rouge());
        maHashMapDataActuel.put("false_couvercle_noir",getFalse_couvercle_noir());
        maHashMapDataActuel.put("false_couvercle_blanc",getFalse_couvercle_blanc());
        maHashMapDataActuel.put("false_boite_noir", getFalse_boite_noir());
        maHashMapDataActuel.put("false_boite_blanc", getFalse_boite_blanc());
        return maHashMapDataActuel;
    }

    // Affichage en string pour les logs
    public String actualDataToString(){
        return (false_boite_blanc +" - " + false_boite_noir +" - " + false_couvercle_blanc +" - " + false_couvercle_noir +" - " + false_goupille_gris +" - " + false_goupille_rouge +" - " + true_boite_blanc +" - " + true_boite_noir +" - " + true_couvercle_blanc +" - " + true_couvercle_noir +" - " + true_goupille_gris +" - " + true_goupille_rouge);
    }


    public Integer getTrue_goupille_gris() {
        return true_goupille_gris;
    }

    public void setTrue_goupille_gris(Integer true_goupille_gris) {
        this.true_goupille_gris = true_goupille_gris;
    }

    public Integer getTrue_goupille_rouge() {
        return true_goupille_rouge;
    }

    public void setTrue_goupille_rouge(Integer true_goupille_rouge) {
        this.true_goupille_rouge = true_goupille_rouge;
    }

    public Integer getTrue_couvercle_noir() {
        return true_couvercle_noir;
    }

    public void setTrue_couvercle_noir(Integer true_couvercle_noir) {
        this.true_couvercle_noir = true_couvercle_noir;
    }

    public Integer getTrue_couvercle_blanc() {
        return true_couvercle_blanc;
    }

    public void setTrue_couvercle_blanc(Integer true_couvercle_blanc) {
        this.true_couvercle_blanc = true_couvercle_blanc;
    }

    public Integer getTrue_boite_noir() {
        return true_boite_noir;
    }

    public void setTrue_boite_noir(Integer true_boite_noir) {
        this.true_boite_noir = true_boite_noir;
    }

    public Integer getTrue_boite_blanc() {
        return true_boite_blanc;
    }

    public void setTrue_boite_blanc(Integer true_boite_blanc) {
        this.true_boite_blanc = true_boite_blanc;
    }

    public Integer getFalse_goupille_gris() {
        return false_goupille_gris;
    }

    public void setFalse_goupille_gris(Integer false_goupille_gris) {
        this.false_goupille_gris = false_goupille_gris;
    }

    public Integer getFalse_goupille_rouge() {
        return false_goupille_rouge;
    }

    public void setFalse_goupille_rouge(Integer false_goupille_rouge) {
        this.false_goupille_rouge = false_goupille_rouge;
    }

    public Integer getFalse_couvercle_noir() {
        return false_couvercle_noir;
    }

    public void setFalse_couvercle_noir(Integer false_couvercle_noir) {
        this.false_couvercle_noir = false_couvercle_noir;
    }

    public Integer getFalse_couvercle_blanc() {
        return false_couvercle_blanc;
    }

    public void setFalse_couvercle_blanc(Integer false_couvercle_blanc) {
        this.false_couvercle_blanc = false_couvercle_blanc;
    }

    public Integer getFalse_boite_noir() {
        return false_boite_noir;
    }

    public void setFalse_boite_noir(Integer false_boite_noir) {
        this.false_boite_noir = false_boite_noir;
    }

    public Integer getFalse_boite_blanc() {
        return false_boite_blanc;
    }

    public void setFalse_boite_blanc(Integer false_boite_blanc) {
        this.false_boite_blanc = false_boite_blanc;
    }
}
