package tests;

import models.Booking;
import models.BookingDates;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class DraftBookingAPITest {

    final String URL = "https://restful-booker.herokuapp.com";

    @Test
    public void createAndGetBookingTest() {

        BookingDates dates = new BookingDates("2026-03-22", "2026-03-25");

        Booking booking = new Booking(
                "Jim",
                "Brown",
                111,
                true,
                dates,
                "Breakfast"
        );

        int bookingId =
                given()
                        .log().all()
                        .baseUri(URL)
                        .header("Content-Type", "application/json")
                        .header("Accept", "application/json")
                        .body(booking)
                        .when()
                        .post("/booking")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .body("bookingid", notNullValue())
                        .body("booking.firstname", equalTo("Jim"))
                        .body("booking.lastname", equalTo("Brown"))
                        .body("booking.bookingdates.checkin", equalTo("2026-03-22"))
                        .extract()
                        .path("bookingid");

        given()
                .log().all()
                .baseUri(URL)
                .header("Accept", "application/json")
                .when()
                .get("/booking/" + bookingId)
                .then()
                .log().all()
                .statusCode(200)
                .body("firstname", equalTo("Jim"))
                .body("lastname", equalTo("Brown"))
                .body("totalprice", equalTo(111))
                .body("depositpaid", equalTo(true))
                .body("bookingdates.checkin", equalTo("2026-03-22"))
                .body("bookingdates.checkout", equalTo("2026-03-25"))
                .body("additionalneeds", equalTo("Breakfast"));
    }
}
