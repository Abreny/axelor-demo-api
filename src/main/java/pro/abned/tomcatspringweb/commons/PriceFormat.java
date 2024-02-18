package pro.abned.tomcatspringweb.commons;

import java.text.DecimalFormat;

public class PriceFormat {
    public static String format(Double rawPrice) {
        return new DecimalFormat("#.##").format(rawPrice);
    }
}
