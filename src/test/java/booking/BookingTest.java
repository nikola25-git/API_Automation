package booking;

import booking.files.Payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class BookingTest {

    @Test(priority = 10)
    public void healthCheck() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        given().log().all()
                .when().get("/ping")
                .then().log().all()
                .assertThat().statusCode(201);
    }

    @Test(priority = 20)
    public void getBookingIds() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        given().log().all()
                .when().get("/booking")
                .then().log().all()
                .assertThat().statusCode(200);
    }

    @Test(priority = 30)
    public void createBooking() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        given().log().all()
                .header("Content-Type", "application/json")
                .body(Payload.bookingBody1())
                .when().post("/booking")
                .then().log().all()
                .assertThat().statusCode(200);

    }


    @Test(priority = 40)
    public void getBooking() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        String response =
                given().log().all()
                        .header("Content-Type", "application/json")
                        .body(Payload.bookingBody2())
                        .when().post("/booking")
                        .then().log().all()
                        .assertThat().statusCode(200)
                        .extract().response().asString();

        System.out.println("RESPONSE: " + response);
        JsonPath js = new JsonPath(response);

        String bookingID = js.getString("bookingid");
        System.out.println("ID: " + bookingID);
        Assert.assertTrue(response.contains(bookingID));

        String responseAfterGetMethod =
                given().log().all()
                        .when().get("/booking/" + bookingID)
                        .then().log().all()
                        .assertThat().statusCode(200)
                        .extract().response().asString();

        JsonPath jsAfterGetMethod = new JsonPath(responseAfterGetMethod);
        String totalprice = jsAfterGetMethod.getString("totalprice");

        System.out.println("total price: " + totalprice);


        Assert.assertTrue(responseAfterGetMethod.contains(totalprice));
        Assert.assertFalse(jsAfterGetMethod.getString("firstname").isBlank());
        Assert.assertFalse(jsAfterGetMethod.getString("lastname").isBlank());
        Assert.assertFalse(jsAfterGetMethod.getString("totalprice").isBlank());
        Assert.assertFalse(jsAfterGetMethod.getString("depositpaid").isBlank());
        Assert.assertFalse(jsAfterGetMethod.getString("bookingdates").isBlank());
        Assert.assertFalse(jsAfterGetMethod.getString("bookingdates.checkin").isBlank());
        Assert.assertFalse(jsAfterGetMethod.getString("bookingdates.checkout").isBlank());
        Assert.assertFalse(jsAfterGetMethod.getString("additionalneeds").isBlank());

    }

    @Test(priority = 41)
    public void updateBooking() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        String response =
                given().log().all()
                        .header("Content-Type", "application/json")
                        .body(Payload.bookingBody2())
                        .when().post("/booking")
                        .then().log().all()
                        .assertThat().statusCode(200)
                        .extract().response().asString();

        System.out.println("RESPONSE: " + response);
        JsonPath js = new JsonPath(response);

        String bookingID = js.getString("bookingid");

        String createToken =
                given().log().all()
                        .header("Content-Type", "application/json")
                        .body(Payload.token())
                        .when().post("/auth")
                        .then().log().all()
                        .assertThat().statusCode(200)
                        .extract().response().asString();

        JsonPath jsAfterToken = new JsonPath(createToken);
        String token = jsAfterToken.getString("token");
        System.out.println(token);
        Assert.assertFalse(token.isBlank());


        String responseAfterUpdate =
                given().log().all()
                        .header("Content-Type", "application/json")
                        .header("Cookie", "token=" + token)
                        .body(Payload.updateBody())
                        .when().put("/booking/" + bookingID)
                        .then().log().all()
                        .assertThat().statusCode(200)
                        .extract().response().asString();

        JsonPath jsAfterUpdate = new JsonPath(responseAfterUpdate);
        String additionalneedsAfterUpdate = jsAfterUpdate.getString("additionalneeds");
        Assert.assertEquals(additionalneedsAfterUpdate, "Brekkie");
    }

    @Test(priority = 50)
    public void partialUpdateBooking() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        String response =
                given().log().all()
                        .header("Content-Type", "application/json")
                        .body(Payload.bookingBody2())
                        .when().post("/booking")
                        .then().log().all()
                        .assertThat().statusCode(200)
                        .extract().response().asString();

        System.out.println("RESPONSE: " + response);
        JsonPath js = new JsonPath(response);

        String bookingID = js.getString("bookingid");

        String createToken =
                given().log().all()
                        .header("Content-Type", "application/json")
                        .body(Payload.token())
                        .when().post("/auth")
                        .then().log().all()
                        .assertThat().statusCode(200)
                        .extract().response().asString();

        JsonPath jsAfterToken = new JsonPath(createToken);
        String token = jsAfterToken.getString("token");
        System.out.println(token);
        Assert.assertFalse(token.isBlank());


        String responseAfterPatch =
                given().log().all()
                        .header("Content-Type", "application/json")
                        .header("Cookie", "token=" + token)
                        .body(Payload.patchBody())
                        .when().patch("/booking/" + bookingID)
                        .then().log().all()
                        .assertThat().statusCode(200)
                        .extract().response().asString();

        JsonPath jsAfterPatch = new JsonPath(responseAfterPatch);
        String lastnameAfterPatch = jsAfterPatch.getString("lastname");
        Assert.assertEquals(lastnameAfterPatch, "Petrovic");
    }

    @Test(priority = 60)
    public void deleteBooking() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";

        String response =
                given().log().all()
                        .header("Content-Type", "application/json")
                        .body(Payload.bookingBody2())
                        .when().post("/booking")
                        .then().log().all()
                        .assertThat().statusCode(200)
                        .extract().response().asString();

        System.out.println("RESPONSE: " + response);
        JsonPath js = new JsonPath(response);

        String bookingID = js.getString("bookingid");

        String createToken =
                given().log().all()
                        .header("Content-Type", "application/json")
                        .body(Payload.token())
                        .when().post("/auth")
                        .then().log().all()
                        .assertThat().statusCode(200)
                        .extract().response().asString();
        JsonPath jsAfterToken = new JsonPath(createToken);
        String token = jsAfterToken.getString("token");

        given().log().all()
                .header("Cookie", "token=" + token)
                .when().delete("/booking/" + bookingID)
                .then().log().all()
                .assertThat().statusCode(201);


                given().log().all()
                        .when().get("/booking/" + bookingID)
                        .then().log().all()
                        .assertThat().statusCode(404);

    }

}
