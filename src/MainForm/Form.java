package MainForm;

import Method.Counter;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class Form extends JFrame {

    private Color graphic = Color.BLACK;
    private Color os = Color.BLACK;

    private Color background = Color.WHITE;
    private String fun;
    private Counter counter;

    class draw extends JPanel {
        Counter counter;

        draw(Counter counter) {
            this.counter = counter;
        }

        public void paintComponent(Graphics g) {
            int wight = 315;
            int height = 450;
            super.paintComponent(g);
            setBackground(background);
            draw panel = new draw(this.counter);
            add(panel, BorderLayout.CENTER);

            g.setColor(os);
            g.drawLine(7, height - 7, wight - 15, height - 7);
            g.drawLine(wight - 15, height - 7, wight - 25, height - 1);
            g.drawLine(wight - 15, height - 7, wight - 25, height - 13);

            g.drawLine(7, height - 7, 7, 15);
            g.drawLine(7, 15, 0, 25);
            g.drawLine(7, 15, 13, 25);
            g.drawString("X", wight - 15, height - 15);
            g.drawString("Y", 15, 15);

            for (int i = 0, j = (int) Math.round(counter.getMin()); i < height - 0.1 * height; i += height / 10, j += (int) (Math.round(counter.getMax() - Math.round(counter.getMin())) / 9)) {
                g.drawLine(7, height - i, 13, height - i);
                if (i == 360)
                    g.drawString(String.valueOf(counter.getMax()), 14, height - i);
                else
                    g.drawString(j + "", 14, height - i);
            }

            g.setColor(graphic);
            String buf;
            //--- макс
            g.drawLine((int) Math.round(wight * 0.4), (int) Math.round(height * 0.1),
                    (int) Math.round(wight * 0.6), (int) Math.round(height * 0.1));
            buf = "MAX = " + counter.getMax();
            g.drawString(buf, (int) Math.round(wight * 0.6) + 3, (int) Math.round(height * 0.1));

            //--- мин
            g.drawLine((int) Math.round(wight * 0.4), (int) Math.round(height * 0.9),
                    (int) Math.round(wight * 0.6), (int) Math.round(height * 0.9));
            buf = "MIN = " + counter.getMin();
            g.drawString(buf, (int) Math.round(wight * 0.6) + 3, (int) Math.round(height * 0.9));
            height -= (int) Math.round(height * 0.1);




            //--- нижний квартиль
            g.drawLine((int) Math.round(wight * 0.2), (int) Math.round(height * (1 - counter.getM_uppKvartil())),
                    (int) Math.round(wight * 0.2), (int) Math.round(height * (1 - counter.getM_btmKvartil())));
            g.drawString("Н. Квартиль" + counter.getBtmKvartil(), (int) Math.round(wight * 0.35),
                    (int) Math.round(height * (1 - counter.getM_btmKvartil())) - 1);


            //--- верхний квартиль
            g.drawLine((int) Math.round(wight * 0.8), (int) Math.round(height * (1 - counter.getM_uppKvartil())),
                    (int) Math.round(wight * 0.8), (int) Math.round(height * (1 - counter.getM_btmKvartil())));
            g.drawString("В. Квартиль" + counter.getUppKvartil(), (int) Math.round(wight * 0.35),
                    (int) Math.round(height * (1 - counter.getM_uppKvartil())) + 12);

            g.drawLine((int) Math.round(wight * 0.2), (int) Math.round(height * (1 - counter.getM_uppKvartil())),
                    (int) Math.round(wight * 0.8), (int) Math.round(height * (1 - counter.getM_uppKvartil())));

            g.drawLine((int) Math.round(wight * 0.2), (int) Math.round(height * (1 - counter.getM_btmKvartil())),
                    (int) Math.round(wight * 0.8), (int) Math.round(height * (1 - counter.getM_btmKvartil())));

            //--- медиана
            g.drawLine((int) Math.round(wight * 0.2), (int) Math.round(height * (1 - counter.getM_mediana())),
                    (int) Math.round(wight * 0.8), (int) Math.round(height * (1 - counter.getM_mediana())));
            g.drawString("Медиана" + counter.getMediana(), (int) Math.round(wight * 0.35),
                    (int) Math.round(height * (1 - counter.getM_mediana())) - 1);

            g.drawLine((int) Math.round(wight * 0.5), (int) Math.round(height * 0.11),
                    (int) Math.round(wight * 0.5), (int) Math.round(height * (1 - counter.getM_uppKvartil())));

            g.drawLine((int) Math.round(wight * 0.5), height,
                    (int) Math.round(wight * 0.5), (int) Math.round(height * (1 - counter.getM_btmKvartil())));


        }

    }

    public Form() {
        String resultString = JOptionPane.showInputDialog(null, "Введите значения. Если желаете загрузить в файл, нажмите ОК",
                "Ввод значений", JOptionPane.QUESTION_MESSAGE);

        if (resultString.isEmpty()) {
            JFileChooser fileChooser = new JFileChooser();
            int ret = fileChooser.showDialog(null, "Выберите файл");
            if (ret == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    FileReader fileReader = new FileReader(file);
                    BufferedReader bufferedReader = new BufferedReader(fileReader);
                    fun = bufferedReader.readLine();
                    fileReader.close();
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                counter = new Counter(strToDouble(fun));
            }
        } else {
            counter = new Counter(strToDouble(resultString));
        }

        setTitle("Блоковые диаграммы");
        setSize(320, 503);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        Container container = new Container();
        container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));
        JButton save = new JButton("Сохранить");
        JButton show = new JButton("Показать результаты");
        JButton Qcolor = new JButton("Цвет");
        container.add(save);
        container.add(show);
        container.add(Qcolor);
        add(container, BorderLayout.SOUTH);
        save.addActionListener(l -> {
            try {
                FileWriter writer = new FileWriter("D:/output.txt");
                writer.write(counter.toString());
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
        show.addActionListener(l ->
            JOptionPane.showMessageDialog(this, counter.toString(), "Результаты", JOptionPane.INFORMATION_MESSAGE)
        );
        Qcolor.addActionListener(l -> {
            graphic = JColorChooser.showDialog(this, "Выберите цвет графика", Color.BLACK);
            os = JColorChooser.showDialog(this, "Выберите цвет оси", Color.BLACK);
            repaint();
        });
        add(new draw(counter), BorderLayout.CENTER);
        setVisible(true);
    }

    private double[] strToDouble(String str) {
        double[] mass = new double[str.split(" ").length];
        int i = 0;
        for (String buf : str.split(" ")) {
            mass[i] = Double.parseDouble(buf);
            i++;
        }
        return mass;
    }

}