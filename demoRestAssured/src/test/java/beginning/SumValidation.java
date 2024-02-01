package beginning;

import files.ReUsableMethods;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class SumValidation {
    @Test
    public void sumOfCourses(){
        int sum = 0;
        JsonPath js = new JsonPath(ReUsableMethods.coursePrice());
        int count = js.get("courses.size()");
        for (int i=0;i<count;i++){
            int price = js.get("courses["+i+"].price");
            int copies = js.get("courses["+i+"].copies");
            int amount = price*copies;
            System.out.println(amount);
            sum = sum + amount;
        }
        System.out.println(sum);
        int purchaseAmount = js.getInt("dashboard.purchaseAmount");
        assertEquals(sum, purchaseAmount);
    }
}
