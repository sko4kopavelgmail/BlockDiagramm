package Method;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

public class Counter {


    public double getMediana() {
        return mediana;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public double getBtmKvartil() {
        return btmKvartil;
    }

    public double getUppKvartil() {
        return uppKvartil;
    }

    //--- номинальные величины
    private double mediana;        //медиана
    private double min;
    private double max;
    private double btmKvartil;     //нижний квартиль
    private double uppKvartil;     //верхний квартиль

    public double getM_mediana() {
        return M_mediana;
    }

    public double getM_btmKvartil() {
        return M_btmKvartil;
    }

    public double getM_uppKvartil() {
        return M_uppKvartil;
    }

    //--- промасштабированные величины
    private double M_mediana;        //медиана
    private double M_min;
    private double M_max;
    private double M_btmKvartil;     //нижний квартиль
    private double M_uppKvartil;     //верхний квартиль

    private double[] function;

    public Counter(double[] function) {
        this.function = function;
        Arrays.sort(this.function);
        if (function.length % 2 == 0) {
            mediana = function[function.length / 2] / 2 + function[function.length / 2 + 1] / 2;
        } else {
            mediana = function.length / 2 + 0.5;
        }
        min = function[0];
        max = function[function.length - 1];
        btmKvartil = function[Math.round(function.length / 4)];
        uppKvartil = function[(int) Math.round(function.length * 0.75)];
        //-----
        this.function = mashtab(function);
        if (function.length % 2 == 0) {
            M_mediana = function[function.length / 2] / 2 + function[function.length / 2 + 1] / 2;
        } else {
            M_mediana = function.length / 2 + 0.5;
        }
        M_min = function[0];
        M_max = function[function.length - 1];
        M_btmKvartil = function[Math.round(function.length / 4)];
        M_uppKvartil = function[(int) Math.round(function.length * 0.75)];

    }

    private double[] mashtab(double[] mass) {
        double buf = mass[mass.length - 1];
        for (int i = mass.length - 1; i >= 0; i--) {
            mass[i] /= buf;
        }
        return mass;
    }

    @Override
    public String toString() {
        String s = "";
        for (double d : function) {
            s += new BigDecimal(String.valueOf(d)).setScale(3, RoundingMode.UP).doubleValue() + " | ";
        }

        String res = "Нормализованная функция: " + s + "\r\n" +
                "Нижний квартиль: " + btmKvartil + "\r\n" +
                "Верхний квартиль: " + uppKvartil + "\r\n" +
                "Медиана: " + mediana + "\r\n" +
                "Максимальный эллемент: " + max + "\r\n" +
                "Минимальный эллемент: " + min + "\r\n" +
                "Межквартильное расстояние: " + (uppKvartil - btmKvartil);
        return res;
    }
}