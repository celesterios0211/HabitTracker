public class HourlyHabit extends Habit {
    public HourlyHabit(String name, String category) {
        super(name, category);
    }

    @Override
    public String getFrequency() {
        return "Hourly";
    }
}