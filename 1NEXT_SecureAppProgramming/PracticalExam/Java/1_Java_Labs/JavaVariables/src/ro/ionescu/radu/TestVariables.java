package ro.ionescu.radu;

public class TestVariables {

    public static void main(String[] values){
        System.out.println("It's started");

        int intVariable = 23;
        short shortVariable = 25;

        //shortVariable = intVariable;

        //float floatVariable = 23.4;
        float floatVariable = (float)23.4;
        floatVariable = 34.5f;

        //intVariable = "hello"; //it's a statically type language

        int flag = 100;
        if(flag == 10){
            System.out.println("It's 10");
        }

        //setting a byte to 10010000
        byte binaryValue = -112; //complement by 2
        binaryValue = (byte)0x90;
        binaryValue = (byte)((byte)1 << 7) | (byte)((byte)1 << 4);


        //checking if the 3rd bit is 1
        byte byteValue = 67;
        byte bitMask = (byte)0x04;
        if((byteValue & bitMask) == 0)
            System.out.println("The 3rd bit is NOT 0");
        else
            System.out.println("The 3rd bit is 0");

        System.out.println("The end");

        //defaults
        int ontherInt;
        //ontherInt += 24;  //compiler error

        //arrays
        //int values[10]; //they don't exist here
        int array1[];
        int[] array2;
        array2 = new int[10];
        int[] IV = new int[]{0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01};

        //shallow copy
        array1 = IV;

        int value = 23;
        String message = value+"";

        System.out.println("The array is:");
        for(int i = 0; i < array1.length; i++){
            System.out.print(array1[i] + " ");
        }

        IV[0] = 99;

        System.out.println("\nThe array is:");
        for(int i = 0; i < array1.length; i++) {
            System.out.print(array1[i] + " ");
        }

        //block of code
        {
            int a = 10;
        }

        //intVariable = a;

        //primitives wrappers
        //boxing
        int localInt = 45;
        Integer oInterger = 23;
        oInterger = localInt;
        Double oDouble = 45.7;

        //unboxing
        localInt = oInterger;

        //shallow copy for wrappers
        Integer intObject1 = 23;
        Integer intObject2 = intObject1;

        //because Integer is immutable - you will create a new Integer
        intObject1 = 45;

        //String is also immutable
        String firstName = "John";
        String lastName = "Doe";
        String fullName = "";
        fullName = firstName + " " + lastName;

        String newName = new String("John");       //referencing the same const value
        if(firstName == newName)
            System.out.println("They are equal");
        else
            System.out.println("\nReference: They are different !!!");

        if(firstName.equals(newName))
            System.out.println("Content: They are equal");
        else
            System.out.println("Content: They are different !!!");


        Integer smallInt1 = 34;     //small integers are special
        Integer smallInt2 = 34;
        //Integer smallInt2 = new Integer(34);

        if(smallInt1 == smallInt2)
            System.out.println("Small integers: They are equal");
        else
            System.out.println("Small integers: They are different !!!");



    }
}
