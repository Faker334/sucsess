package com.example.myapplication;


import java.sql.Time;
import java.util.List;

public class State {

    private String name; // название
    private String cenaUslugi;  // столица

    private int IkonUslugi; // ресурс флага

    public String getOrganization_id() {
        return organization_id;
    }

    public void setOrganization_id(String organization_id) {
        this.organization_id = organization_id;
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

    public List<String> getOptionsTipe() {
        return optionsTipe;
    }

    public void setOptionsTipe(List<String> optionsTipe) {
        this.optionsTipe = optionsTipe;
    }

    public List<String> getOptionsName() {
        return optionsName;
    }

    public void setOptionsName(List<String> optionsName) {
        this.optionsName = optionsName;
    }

    public List<String> getOptionsValue() {
        return optionsValue;
    }

    public void setOptionsValue(List<String> optionsValue) {
        this.optionsValue = optionsValue;
    }

    private String organization_id;
    private String valuta;
    private String time_available_from;
    private String time_available_to;
    private String opisnie;
    private Boolean isaktiv;
    private String options;
    private String optionsCaunt;
    private List<String> optionsTipe;
    private List<String> optionsName;
    private List<String> optionsValue;



    public State(String name, String capital, int flag){

        this.name=name;
        this.cenaUslugi =capital;
        this.IkonUslugi =flag;
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