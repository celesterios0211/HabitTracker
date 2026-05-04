public class WeeklyHabit extends Habit {
    public WeeklyHabit(String name, String category) {
        super(name, category);
    }

    @Override
    public String getFrequency() {
        return "Weekly";
    }
}