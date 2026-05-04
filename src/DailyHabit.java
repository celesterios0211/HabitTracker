public class DailyHabit extends Habit {
    public DailyHabit(String name, String category) {
        super(name, category);
    }

    @Override
    public String getFrequency() {
        return "Daily";
    }
}