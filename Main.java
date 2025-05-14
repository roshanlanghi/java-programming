import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.border.*;
import okhttp3.*;
import org.json.JSONObject;
import java.io.IOException;

class RoundedBorder extends AbstractBorder {
    private int radius;
    private Color color;

    RoundedBorder(int radius, Color color) {
        this.radius = radius;
        this.color = color;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D)g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(color);
        g2d.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        g2d.dispose();
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(radius/2, radius/2, radius/2, radius/2);
    }
}

public class Main {
    private static final Color BACKGROUND_COLOR = new Color(18, 18, 32);
    private static final Color FOREGROUND_COLOR = new Color(255, 255, 255);
    private static final Color ACCENT_COLOR = new Color(86, 192, 224);
    private static final Color CARD_COLOR = new Color(28, 28, 44);

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame("Weather Forecast");
        frame.setLayout(new BorderLayout(10, 10));
        frame.setSize(340, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(BACKGROUND_COLOR);
        frame.setLocationRelativeTo(null);

        // North Panel
        JPanel northPanel = new JPanel(new FlowLayout());
        northPanel.setBackground(BACKGROUND_COLOR);
        JLabel cityLabel = new JLabel("City: ");
        cityLabel.setForeground(FOREGROUND_COLOR);

        JComboBox<String> citySelect = new JComboBox<>(new String[]{
            "New York", "London", "Tokyo", "Paris", "Sydney"
        });
        citySelect.setBackground(new Color(60, 63, 65));
        citySelect.setForeground(FOREGROUND_COLOR);

        northPanel.add(cityLabel);
        northPanel.add(citySelect);
        frame.add(northPanel, BorderLayout.NORTH);

        // Center Panel
        JPanel centerPanel = new JPanel(new GridLayout(5, 1, 5, 5));
        centerPanel.setBackground(CARD_COLOR);
        centerPanel.setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(20, CARD_COLOR),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JLabel iconLabel = new JLabel("☀️", SwingConstants.CENTER);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 64));
        JLabel tempLabel = new JLabel("72°F", SwingConstants.CENTER);
        JLabel humidityLabel = new JLabel("Humidity: 65%", SwingConstants.CENTER);
        JLabel conditionLabel = new JLabel("Partly Cloudy", SwingConstants.CENTER);
        JLabel dateLabel = new JLabel(new SimpleDateFormat("EEE, MMM d, yyyy").format(new Date()), SwingConstants.CENTER);

        tempLabel.setFont(new Font("Arial", Font.BOLD, 42));
        conditionLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        humidityLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        iconLabel.setForeground(FOREGROUND_COLOR);
        tempLabel.setForeground(FOREGROUND_COLOR);
        humidityLabel.setForeground(FOREGROUND_COLOR);
        conditionLabel.setForeground(FOREGROUND_COLOR);
        dateLabel.setForeground(FOREGROUND_COLOR);

        centerPanel.add(iconLabel);
        centerPanel.add(tempLabel);
        centerPanel.add(humidityLabel);
        centerPanel.add(conditionLabel);
        centerPanel.add(dateLabel);
        frame.add(centerPanel, BorderLayout.CENTER);

        // South Panel
        JPanel southPanel = new JPanel(new FlowLayout());
        southPanel.setBackground(BACKGROUND_COLOR);
        JButton refreshButton = new JButton("Refresh");
        refreshButton.setBackground(ACCENT_COLOR);
        refreshButton.setForeground(FOREGROUND_COLOR);
        refreshButton.setFont(new Font("Arial", Font.BOLD, 14));
        refreshButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        refreshButton.setFocusPainted(false);
        refreshButton.setBorderPainted(false);
        refreshButton.setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(30, ACCENT_COLOR),
            BorderFactory.createEmptyBorder(10, 25, 10, 25)
        ));

        refreshButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                refreshButton.setBackground(ACCENT_COLOR.brighter());
            }
            public void mouseExited(MouseEvent e) {
                refreshButton.setBackground(ACCENT_COLOR);
            }
        });

        southPanel.add(refreshButton);
        frame.add(southPanel, BorderLayout.SOUTH);

        refreshButton.addActionListener(e -> {
            String city = (String) citySelect.getSelectedItem();
            String apiKey = "YOUR_API_KEY"; // Replace this with your actual API key
            String url = String.format("https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=metric", city, apiKey);

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(url).build();

            client.newCall(request).enqueue(new Callback() {
                public void onFailure(Call call, IOException ex) {
                    ex.printStackTrace();
                }

                public void onResponse(Call call, Response response) throws IOException {
                    if (!response.isSuccessful()) return;
                    String jsonData = response.body().string();
                    JSONObject json = new JSONObject(jsonData);
                    SwingUtilities.invokeLater(() -> {
                        try {
                            double temp = json.getJSONObject("main").getDouble("temp");
                            int humidity = json.getJSONObject("main").getInt("humidity");
                            String condition = json.getJSONArray("weather").getJSONObject(0).getString("main");

                            tempLabel.setText(String.format("%.1f°C", temp));
                            humidityLabel.setText("Humidity: " + humidity + "%");
                            conditionLabel.setText(condition);

                            String icon = switch (condition.toLowerCase()) {
                                case "clear" -> "☀️";
                                case "clouds" -> "☁️";
                                case "rain" -> "🌧️";
                                case "snow" -> "❄️";
                                case "thunderstorm" -> "⛈️";
                                case "drizzle" -> "🌦️";
                                default -> "⛅";
                            };

                            iconLabel.setText(icon);
                        } catch (Exception err) {
                            err.printStackTrace();
                        }
                    });
                }
            });
        });

        frame.setVisible(true);
    }
}
