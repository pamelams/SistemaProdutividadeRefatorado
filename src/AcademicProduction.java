public class AcademicProduction {
    private String title;
    private int yearOfPublication;

    public AcademicProduction(String title, int yearOfPublication) {
        this.title = title;
        this.yearOfPublication = yearOfPublication;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }
    public void setYearOfPublication(int yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }
    public int getYearOfPublication() {
        return yearOfPublication;
    }
}
