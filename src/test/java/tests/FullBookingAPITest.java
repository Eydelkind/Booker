package tests;

import io.restassured.response.Response;
import models.AuthRequest;
import models.Booking;
import org.testng.annotations.Test;
import utils.BookingDataFactory;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class FullBookingAPITest {

    private static final String BASE_URL = "https://restful-booker.herokuapp.com";

    @Test
    public void fullBookingCRUDTest() {
        Booking booking = BookingDataFactory.createDefaultBooking();

        Response createResponse = createBooking(booking);
        int bookingId = createResponse.then().extract().path("bookingid");

        verifyCreateResponse(createResponse);

        Response getResponse = getBookingById(bookingId);
        verifyGetResponse(getResponse, "Jim", "Brown", 111, true,
                "2026-03-22", "2026-03-25", "Breakfast");

        String token = createToken();

        Booking updatedBooking = BookingDataFactory.createUpdatedBooking();
        Response updateResponse = updateBooking(bookingId, token, updatedBooking);
        verifyUpdateResponse(updateResponse);

        Response getUpdatedResponse = getBookingById(bookingId);
        verifyGetResponse(getUpdatedResponse, "James", "Brown", 222, false,
                "2026-04-01", "2026-04-10", "Lunch");

        Response patchResponse = patchBookingFirstname(bookingId, token, "John");
        patchResponse.then()
                .log().all()
                .statusCode(200)
                .body("firstname", equalTo("John"));

        Response getPatchedResponse = getBookingById(bookingId);
        getPatchedResponse.then()
                .log().all()
                .statusCode(200)
                .body("firstname", equalTo("John"));

        Response deleteResponse = deleteBooking(bookingId, token);
        deleteResponse.then()
                .log().all()
                .statusCode(201);

        given()
                .log().all()
                .baseUri(BASE_URL)
                .header("Accept", "application/json")
                .when()
                .get("/booking/" + bookingId)
                .then()
                .log().all()
                .statusCode(404);
    }

    private Response createBooking(Booking booking) {
        return given()
                .log().all()
                .baseUri(BASE_URL)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .body(booking)
                .when()
                .post("/booking");
    }

    private Response getBookingById(int bookingId) {
        return given()
                .log().all()
                .baseUri(BASE_URL)
                .header("Accept", "application/json")
                .when()
                .get("/booking/" + bookingId);
    }

    private String createToken() {
        AuthRequest auth = new AuthRequest("admin", "password123");

        return given()
                .log().all()
                .baseUri(BASE_URL)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .body(auth)
                .when()
                .post("/auth")
                .then()
                .log().all()
                .statusCode(200)
                .body("token", notNullValue())
                .extract()
                .path("token");
    }

    private Response updateBooking(int bookingId, String token, Booking booking) {
        return given()
                .log().all()
                .baseUri(BASE_URL)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Cookie", "token=" + token)
                .body(booking)
                .when()
                .put("/booking/" + bookingId);
    }

    private Response patchBookingFirstname(int bookingId, String token, String firstname) {
        String patchBody = """
                {
                  "firstname": "%s"
                }
                """.formatted(firstname);

        return given()
                .log().all()
                .baseUri(BASE_URL)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Cookie", "token=" + token)
                .body(patchBody)
                .when()
                .patch("/booking/" + bookingId);
    }

    private Response deleteBooking(int bookingId, String token) {
        return given()
                .log().all()
                .baseUri(BASE_URL)
                .header("Cookie", "token=" + token)
                .when()
                .delete("/booking/" + bookingId);
    }

    private void verifyCreateResponse(Response response) {
        response.then()
                .log().all()
                .statusCode(200)
                .body("bookingid", notNullValue())
                .body("booking.firstname", equalTo("Jim"))
                .body("booking.lastname", equalTo("Brown"))
                .body("booking.totalprice", equalTo(111))
                .body("booking.depositpaid", equalTo(true))
                .body("booking.bookingdates.checkin", equalTo("2026-03-22"))
                .body("booking.bookingdates.checkout", equalTo("2026-03-25"))
                .body("booking.additionalneeds", equalTo("Breakfast"));
    }

    private void verifyUpdateResponse(Response response) {
        response.then()
                .log().all()
                .statusCode(200)
                .body("firstname", equalTo("James"))
                .body("lastname", equalTo("Brown"))
                .body("totalprice", equalTo(222))
                .body("depositpaid", equalTo(false))
                .body("bookingdates.checkin", equalTo("2026-04-01"))
                .body("bookingdates.checkout", equalTo("2026-04-10"))
                .body("additionalneeds", equalTo("Lunch"));
    }

    private void verifyGetResponse(
            Response response,
            String firstname,
            String lastname,
            int totalPrice,
            boolean depositPaid,
            String checkin,
            String checkout,
            String additionalNeeds
    ) {
        response.then()
                .log().all()
                .statusCode(200)
                .body("firstname", equalTo(firstname))
                .body("lastname", equalTo(lastname))
                .body("totalprice", equalTo(totalPrice))
                .body("depositpaid", equalTo(depositPaid))
                .body("bookingdates.checkin", equalTo(checkin))
                .body("bookingdates.checkout", equalTo(checkout))
                .body("additionalneeds", equalTo(additionalNeeds));
    }
}