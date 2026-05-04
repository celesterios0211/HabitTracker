public abstract class Habit {
    private String name;
    private String category;
    private int streak;
    private int completedCount;
    private String lastCompletedDate;

    public Habit(String name, String category) {
        this.name = name;
        this.category = category;
        this.streak = 0;
        this.completedCount = 0;
        this.lastCompletedDate = "Not completed yet";
    }

    public void markComplete(String date) {
        completedCount++;
        streak++;
        lastCompletedDate = date;
    }

    public abstract String getFrequency();

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public int getStreak() {
        return streak;
    }

    public int getCompletedCount() {
        return completedCount;
    }

    public String getLastCompletedDate() {
        return lastCompletedDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setStreak(int streak) {
        this.streak = streak;
    }

    public void setCompletedCount(int completedCount) {
        this.completedCount = completedCount;
    }

    public void setLastCompletedDate(String lastCompletedDate) {
        this.lastCompletedDate = lastCompletedDate;
    }
}