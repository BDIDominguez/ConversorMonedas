package modelo;

public class Moneda {
    private String currencyCode;
    private String currencyName;
    private String locale;
    private Double conversionRate;

    public Moneda(String currencyCode, String currencyName, String locale, Double conversionRate) {
        this.currencyCode = currencyCode;
        this.currencyName = currencyName;
        this.locale = locale;
        this.conversionRate = conversionRate;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public Double getConversionRate() {
        return conversionRate;
    }

    public void setConversionRate(Double conversionRate) {
        this.conversionRate = conversionRate;
    }

    @Override
    public String toString() {
        return "Moneda{" +
                "currencyCode='" + currencyCode + '\'' +
                ", currencyName='" + currencyName + '\'' +
                ", locale='" + locale + '\'' +
                ", conversionRate=" + conversionRate +
                '}';
    }
}
