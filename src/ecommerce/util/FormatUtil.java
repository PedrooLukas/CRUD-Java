package ecommerce.util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class FormatUtil {
    private static final NumberFormat CURRENCY_FORMAT = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static String formatCurrency(BigDecimal value) {
        return CURRENCY_FORMAT.format(value);
    }

    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(DATE_TIME_FORMAT);
    }

    public static String formatDate(LocalDateTime dateTime) {
        return dateTime.format(DATE_FORMAT);
    }

    public static String formatCPF(String cpf) {
        String cleanCPF = cpf.replaceAll("[^0-9]", "");
        if (cleanCPF.length() != 11) {
            return cpf;
        }
        return String.format("%s.%s.%s-%s",
                cleanCPF.substring(0, 3),
                cleanCPF.substring(3, 6),
                cleanCPF.substring(6, 9),
                cleanCPF.substring(9));
    }

    public static String formatPhone(String phone) {
        String cleanPhone = phone.replaceAll("[^0-9]", "");
        if (cleanPhone.length() == 11) {
            return String.format("(%s) %s-%s",
                    cleanPhone.substring(0, 2),
                    cleanPhone.substring(2, 7),
                    cleanPhone.substring(7));
        } else if (cleanPhone.length() == 10) {
            return String.format("(%s) %s-%s",
                    cleanPhone.substring(0, 2),
                    cleanPhone.substring(2, 6),
                    cleanPhone.substring(6));
        }
        return phone;
    }

    public static void printSeparator() {
        System.out.println("=".repeat(60));
    }

    public static void printHeader(String title) {
        printSeparator();
        System.out.println(centerText(title, 60));
        printSeparator();
    }

    public static String centerText(String text, int width) {
        int padding = (width - text.length()) / 2;
        return " ".repeat(Math.max(0, padding)) + text;
    }
}
