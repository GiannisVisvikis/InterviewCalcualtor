package visvikis.ioannis.interviewcalculator;


//Class that holds information about each row of the spinner
class CurrencyInfo {

    private String code;
    private String flagPath;

    public CurrencyInfo(String code, String lowOrHighRes){
        this.code = code;
        this.flagPath = lowOrHighRes + "/" + code + ".png"; //example (...assets...)/48/USD.png
    }

    public String getCode() {
        return code;
    }

    public String getFlagPath() {
        return flagPath;
    }

}
