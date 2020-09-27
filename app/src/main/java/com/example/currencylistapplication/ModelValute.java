package com.example.currencylistapplication;

public class ModelValute {
    private String NumCode,CharCode,Nominal,Name,Value,Previous;
    public ModelValute( String numCode, String charCode, String nominal, String name, String value, String previous) {

        NumCode = numCode;
        CharCode = charCode;
        Nominal = nominal;
        Name = name;
        Value = value;
        Previous = previous;


        }
    public String getNumCode () {
        return NumCode;
    }

    public void setNumCode (String numCode){
        NumCode = numCode;
    }

    public String getCharCode () {
        return CharCode;
    }

    public void setCharCode (String charCode){
        CharCode = charCode;
    }

    public String getNominal () {
        return Nominal;
    }

    public void setNominal (String nominal){
        Nominal = nominal;
    }

    public String getName () {
        return Name;
    }

    public void setName (String name){
        Name = name;
    }

    public String getValue () {
        return Value;
    }

    public void setValue (String value){
        Value = value;
    }

    public String getPrevious () {
        return Previous;
    }

    public void setPrevious (String previous){
        Previous = previous;
    }

}
