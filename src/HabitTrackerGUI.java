import java.awt.*;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class HabitTrackerGUI extends JFrame {

    private ArrayList<Habit> habits;

    private JTextField habitNameField;
    private JComboBox<String> categoryBox;
    private JComboBox<String> frequencyBox;

    private JTable habitTable;
    private DefaultTableModel tableModel;

    private JProgressBar progressBar;
    private JLabel progressLabel;

    private final String FILE_NAME = "habits.txt";

    public HabitTrackerGUI() {
        habits = new ArrayList<>();

        setTitle("Habit Tracker App");
        setSize(950, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(245, 247, 250));

        JLabel titleLabel = new JLabel("My Habit Tracker", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(new Color(45, 52, 54));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new GridLayout(12, 1, 8, 8));
        inputPanel.setBackground(Color.WHITE);
        inputPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        habitNameField = new JTextField();

        categoryBox = new JComboBox<>(new String[]{
                "School", "Health", "Work", "Personal", "Fitness", "Chores", "Other"
        });

        frequencyBox = new JComboBox<>(new String[]{
                "Daily", "Weekly", "Hourly"
        });

        JButton addButton = new JButton("Add Habit");
        JButton completeButton = new JButton("Mark Complete");
        JButton editButton = new JButton("Edit Habit");
        JButton deleteButton = new JButton("Delete Habit");
        JButton saveButton = new JButton("Save Habits");

        addButton.setBackground(new Color(76, 175, 80));
        addButton.setForeground(Color.WHITE);

        completeButton.setBackground(new Color(33, 150, 243));
        completeButton.setForeground(Color.WHITE);

        editButton.setBackground(new Color(255, 193, 7));
        deleteButton.setBackground(new Color(244, 67, 54));
        deleteButton.setForeground(Color.WHITE);

        inputPanel.add(new JLabel("Habit Name:"));
        inputPanel.add(habitNameField);

        inputPanel.add(new JLabel("Category:"));
        inputPanel.add(categoryBox);

        inputPanel.add(new JLabel("Frequency:"));
        inputPanel.add(frequencyBox);

        inputPanel.add(addButton);
        inputPanel.add(completeButton);
        inputPanel.add(editButton);
        inputPanel.add(deleteButton);
        inputPanel.add(saveButton);

        mainPanel.add(inputPanel, BorderLayout.WEST);

        String[] columns = {
                "Name", "Category", "Frequency", "Streak", "Completed", "Last Completed"
        };

        tableModel = new DefaultTableModel(columns, 0);
        habitTable = new JTable(tableModel);
        habitTable.setFont(new Font("Arial", Font.PLAIN, 14));
        habitTable.setRowHeight(25);

        JScrollPane scrollPane = new JScrollPane(habitTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Your Habits"));

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        bottomPanel.setBackground(new Color(245, 247, 250));

        progressLabel = new JLabel("Progress: 0 completed habits", SwingConstants.CENTER);
        progressLabel.setFont(new Font("Arial", Font.BOLD, 15));

        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);

        bottomPanel.add(progressLabel);
        bottomPanel.add(progressBar);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        addButton.addActionListener(e -> addHabit());
        completeButton.addActionListener(e -> markHabitComplete());
        editButton.addActionListener(e -> editHabit());
        deleteButton.addActionListener(e -> deleteHabit());
        saveButton.addActionListener(e -> saveHabits());

        loadHabits();
        refreshTable();
    }

    private void addHabit() {
        String name = habitNameField.getText().trim();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a habit name.");
            return;
        }

        String category = categoryBox.getSelectedItem().toString();
        String frequency = frequencyBox.getSelectedItem().toString();

        Habit habit = createHabit(name, category, frequency);
        habits.add(habit);

        habitNameField.setText("");
        refreshTable();
        saveHabits();
    }

    private Habit createHabit(String name, String category, String frequency) {
        if (frequency.equals("Daily")) {
            return new DailyHabit(name, category);
        } else if (frequency.equals("Weekly")) {
            return new WeeklyHabit(name, category);
        } else {
            return new HourlyHabit(name, category);
        }
    }

    private void markHabitComplete() {
        int selectedRow = habitTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a habit first.");
            return;
        }

        String today = LocalDate.now().toString();
        habits.get(selectedRow).markComplete(today);

        refreshTable();
        saveHabits();
    }

    private void editHabit() {
        int selectedRow = habitTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a habit to edit.");
            return;
        }

        Habit habit = habits.get(selectedRow);

        String newName = JOptionPane.showInputDialog(this, "Enter new habit name:", habit.getName());

        if (newName == null || newName.trim().isEmpty()) {
            return;
        }

        String newCategory = JOptionPane.showInputDialog(this, "Enter new category:", habit.getCategory());

        if (newCategory == null || newCategory.trim().isEmpty()) {
            return;
        }

        habit.setName(newName.trim());
        habit.setCategory(newCategory.trim());

        refreshTable();
        saveHabits();
    }

    private void deleteHabit() {
        int selectedRow = habitTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a habit to delete.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this habit?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            habits.remove(selectedRow);
            refreshTable();
            saveHabits();
        }
    }

    private void refreshTable() {
        tableModel.setRowCount(0);

        int totalCompletions = 0;

        for (Habit habit : habits) {
            tableModel.addRow(new Object[]{
                    habit.getName(),
                    habit.getCategory(),
                    habit.getFrequency(),
                    habit.getStreak(),
                    habit.getCompletedCount(),
                    habit.getLastCompletedDate()
            });

            totalCompletions += habit.getCompletedCount();
        }

        progressLabel.setText("Total Habits: " + habits.size() +
                " | Total Completions: " + totalCompletions);

        int progress = 0;

        if (!habits.isEmpty()) {
            int completedHabits = 0;

            for (Habit habit : habits) {
                if (habit.getCompletedCount() > 0) {
                    completedHabits++;
                }
            }

            progress = (completedHabits * 100) / habits.size();
        }

        progressBar.setValue(progress);
        progressBar.setString(progress + "% of habits completed at least once");
    }

    private void saveHabits() {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME));

            for (Habit habit : habits) {
                writer.println(
                        habit.getName() + "," +
                        habit.getCategory() + "," +
                        habit.getFrequency() + "," +
                        habit.getStreak() + "," +
                        habit.getCompletedCount() + "," +
                        habit.getLastCompletedDate()
                );
            }

            writer.close();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving habits.");
        }
    }

    private void loadHabits() {
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return;
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length == 6) {
                    String name = parts[0];
                    String category = parts[1];
                    String frequency = parts[2];
                    int streak = Integer.parseInt(parts[3]);
                    int completedCount = Integer.parseInt(parts[4]);
                    String lastCompletedDate = parts[5];

                    Habit habit = createHabit(name, category, frequency);
                    habit.setStreak(streak);
                    habit.setCompletedCount(completedCount);
                    habit.setLastCompletedDate(lastCompletedDate);

                    habits.add(habit);
                }
            }

            reader.close();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading habits.");
        }
    }
}