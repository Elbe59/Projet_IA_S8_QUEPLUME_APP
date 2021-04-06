package fr.hei.que_plume_app.entity;

import java.util.HashMap;
import java.util.Map;

public class ActualData {


    private Integer true_goupille_grise;
    private Integer true_goupille_rouge;
    private Integer true_couvercle_noir;
    private Integer true_couvercle_blanc;
    private Integer true_boite_noire;
    private Integer true_boite_blanche;


    private Integer false_goupille_grise;
    private Integer false_goupille_rouge;
    private Integer false_couvercle_noir;
    private Integer false_couvercle_blanc;
    private Integer false_boite_noire;
    private Integer false_boite_blanche;


    public ActualData(){}

    public Map<String, Integer> getMaHashMapDataActuel() {
        Map<String,Integer> maHashMapDataActuel = new HashMap<String,Integer>();
        maHashMapDataActuel.put("true_goupille_grise",getTrue_goupille_grise());
        maHashMapDataActuel.put("true_goupille_rouge",getTrue_goupille_rouge());
        maHashMapDataActuel.put("true_couvercle_noir",getTrue_couvercle_noir());
        maHashMapDataActuel.put("true_couvercle_blanc",getTrue_couvercle_blanc());
        maHashMapDataActuel.put("true_boite_noire",getTrue_boite_noire());
        maHashMapDataActuel.put("true_boite_blanche",getTrue_boite_blanche());
        maHashMapDataActuel.put("false_goupille_grise",getFalse_goupille_grise());
        maHashMapDataActuel.put("false_goupille_rouge",getFalse_goupille_rouge());
        maHashMapDataActuel.put("false_couvercle_noir",getFalse_couvercle_noir());
        maHashMapDataActuel.put("false_couvercle_blanc",getFalse_couvercle_blanc());
        maHashMapDataActuel.put("false_boite_noire",getFalse_boite_noire());
        maHashMapDataActuel.put("false_boite_blanche",getFalse_boite_blanche());
        return maHashMapDataActuel;
    }


    public Integer getTrue_goupille_grise() {
        return true_goupille_grise;
    }

    public void setTrue_goupille_grise(Integer true_goupille_grise) {
        this.true_goupille_grise = true_goupille_grise;
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

    public Integer getTrue_boite_noire() {
        return true_boite_noire;
    }

    public void setTrue_boite_noire(Integer true_boite_noire) {
        this.true_boite_noire = true_boite_noire;
    }

    public Integer getTrue_boite_blanche() {
        return true_boite_blanche;
    }

    public void setTrue_boite_blanche(Integer true_boite_blanche) {
        this.true_boite_blanche = true_boite_blanche;
    }

    public Integer getFalse_goupille_grise() {
        return false_goupille_grise;
    }

    public void setFalse_goupille_grise(Integer false_goupille_grise) {
        this.false_goupille_grise = false_goupille_grise;
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

    public Integer getFalse_boite_noire() {
        return false_boite_noire;
    }

    public void setFalse_boite_noire(Integer false_boite_noire) {
        this.false_boite_noire = false_boite_noire;
    }

    public Integer getFalse_boite_blanche() {
        return false_boite_blanche;
    }

    public void setFalse_boite_blanche(Integer false_boite_blanche) {
        this.false_boite_blanche = false_boite_blanche;
    }
}
