package visvikis.ioannis.interviewcalculator;


/*
    Will be used to perform the calculations
 */
public class TheModel
{
    //will be fed back to the user after the operation is performed
    private String result;



    public String calculate(String[] args) throws IllegalArgumentException
    {
        String result;

        String first = args[0];
        char operation = args[1].charAt(0);
        String second = args[2];

        double a = Double.parseDouble(first);
        double b = Double.parseDouble(second);

        switch (operation) {

            case '+':
                result = Double.toString(a + b);
                break;
            case '-':
                result = Double.toString(a - b);
                break;
            case '*':
                result = Double.toString(a * b);
                break;
            case '/':
                if(b != 0)
                    result = Double.toString(a / b);
                else
                    throw new IllegalArgumentException("Can not divide by zero");
                break;

            default:
                result = "This was not considered";
        }

        return result;
    }





}
