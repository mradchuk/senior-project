class Complex3 {

    private String firstName;
    private String lastName;
    private double[] scores;
    private double average;

    public Complex3(String firstName, String lastName, double score1, double score2, double score3, double score4, double score5) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.scores = new double[5];

        scores[0] = score1;
        scores[1] = score2;
        scores[2] = score3;
        scores[3] = score4;
        scores[4] = score5;

    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public double findAverage() {

        double sum = 0.00;

        for(int i = 0; i < scores.length; i++) {
            sum += scores[i];
        }

        return (sum / 5.0);

    }

    public char findLetterGrade() {

        if (findAverage() >= 90)
            return 'A';
        else if (findAverage() >= 80)
            return 'B';
        else if (findAverage() >= 70)
            return 'C';
        else if (findAverage() >= 60)
            return 'D';
        else
            return 'F';

    }

    public void changeScore(int index, double newScore) {
        scores[index] = newScore;
    }

    public String strOutput() {

        String result = "";

        result += getFirstName() + "\t" + getLastName();

        for(int i = 0; i < scores.length; i++) {
            result += "\t\t" + scores[i];
        }

        String decimalFormatFindAverage = String.format("%.2f", findAverage());

        result += "\t\t" + decimalFormatFindAverage + "\t\t" + findLetterGrade();

        return result;

    }

    public static void main(String[] args) {

        Complex3[] obj = new Complex3[3];

        obj[0] = new Complex3("Student", "1", 23.23, 45.56, 78.69, 89.45, 95.56);
        obj[1] = new Complex3("Student", "2", 45.34, 67.45, 34.87, 67.89, 45.78);
        obj[2] = new Complex3("Student", "3", 56.34, 98.67, 95.34, 45.67, 79.09);

        double result = 0.00;

        System.out.println("Name\t\t\tTest1 \t\tTest2 \t\tTest3 \t\tTest4 \t\tTest5\t\tAverage\t\tGrade");
        System.out.println("_____________________________________________________________________________________________");

        for(int i = 0; i < obj.length; i++) {
            System.out.print(obj[i].strOutput());
            System.out.println();
            result += obj[i].findAverage();
        }

        String decimalFormatClassAverage = String.format("%.2f", result / obj.length);

        System.out.println();
        System.out.println("Class average is " + decimalFormatClassAverage);

    }

}


