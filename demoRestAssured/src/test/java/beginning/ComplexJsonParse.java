package beginning;

import files.ReUsableMethods;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {
    public static void main(String[] args) {
        JsonPath js = new JsonPath(ReUsableMethods.coursePrice());
        //print number of courses
        int count = js.getInt("courses.size()");
        System.out.println(count);

        //print purchaseAmount

        int totalAmount = js.getInt("dashboard.purchaseAmount");
        System.out.println(totalAmount);

        //print the title of first course
        String titleFirstCourse = js.get("courses[0].title");
        System.out.println(titleFirstCourse);

        int anInt = js.getInt("courses[0].size()");
        System.out.println(anInt);

        //print all course titles and their respective prices
        for (int i=0; i<count; i++){
            System.out.println(js.get("courses[" + i + "].title").toString() + ": "+js.get("courses[" + i + "].price").toString());
            System.out.println();
        }
        /*
        for (int i=0; i<count; i++){
            String courseTitles = js.get("courses[" + i + "].title");
            System.out.println(courseTitles);

            System.out.println(js.get("courses[" + i + "].price").toString());
        }

         */

        //print number of RSP copies
        for (int i=0;i<count;i++){
            String courseTitle = js.get("courses["+i+"].title");
            if (courseTitle.equalsIgnoreCase("RPA")){
                int copies = js.get("courses[" + i + "].copies");
                System.out.println(copies);
                break;
            }
        }
    }
}
