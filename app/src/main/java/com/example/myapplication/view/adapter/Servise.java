package com.example.myapplication.view.adapter;


import java.util.List;
import java.util.Map;

public class Servise {

    private String name; // название
    private String cenaUslugi;  // столица

    private int IkonUslugi; // ресурс флага

    public int getOrganization_id() {
        return id;
    }

    public void setOrganization_id(int id) {
        this.id = id;
    }

    public String getValuta() {
        return valuta;
    }

    public void setValuta(String valuta) {
        this.valuta = valuta;
    }

    public String getTime_available_from() {
        return time_available_from;
    }

    public void setTime_available_from(String time_available_from) {
        this.time_available_from = time_available_from;
    }

    public String getTime_available_to() {
        return time_available_to;
    }

    public void setTime_available_to(String time_available_to) {
        this.time_available_to = time_available_to;
    }

    public String getOpisnie() {
        return opisnie;
    }

    public void setOpisnie(String opisnie) {
        this.opisnie = opisnie;
    }

    public Boolean getIsaktiv() {
        return isaktiv;
    }

    public void setIsaktiv(Boolean isaktiv) {
        this.isaktiv = isaktiv;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String getOptionsCaunt() {
        return optionsCaunt;
    }

    public void setOptionsCaunt(String optionsCaunt) {
        this.optionsCaunt = optionsCaunt;
    }



    private int id;
    private String valuta;
    private String time_available_from;
    private String time_available_to;
    private String opisnie;
    private Boolean isaktiv;
    private String options;
    private String optionsCaunt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<OptionsServis> getOptionsSevis() {
        return optionsSevis;
    }

    public void setOptionsSevis(List<OptionsServis> optionsSevis) {
        this.optionsSevis = optionsSevis;
    }

    private List<OptionsServis> optionsSevis;




    public Servise(String name, String capital, int flag,int organization_id, List<OptionsServis> optionsSevis){
       this.optionsSevis = optionsSevis;
        this.name=name;
        this.cenaUslugi =capital;
        this.IkonUslugi =flag;
        this.id=organization_id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCenaUslugi() {
        return this.cenaUslugi;
    }

    public void setCenaUslugi(String cenaUslugi) {
        this.cenaUslugi = cenaUslugi;
    }

    public int getIkonUslugi() {
        return this.IkonUslugi;
    }

    public void setIkonUslugi(int ikonUslugi) {
        this.IkonUslugi = ikonUslugi;
    }
}