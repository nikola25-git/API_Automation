package booking.files;

public class Payload {

    public static String bookingBody1() {
        return  "{\n" +
                "    \"firstname\" : \"Petar\",\n" +
                "    \"lastname\" : \"Kojotic\",\n" +
                "    \"totalprice\" : 99,\n" +
                "    \"depositpaid\" : true,\n" +
                "    \"bookingdates\" : {\n" +
                "        \"checkin\" : \"2018-01-01\",\n" +
                "        \"checkout\" : \"2019-01-01\"\n" +
                "    },\n" +
                "    \"additionalneeds\" : \"Brekkie\"\n" +
                "}";
    }

    public static String bookingBody2 () {
        return "{\n" +
                "    \"firstname\" : \"Petar\",\n" +
                "    \"lastname\" : \"Kojotic\",\n" +
                "    \"totalprice\" : 71,\n" +
                "    \"depositpaid\" : false,\n" +
                "    \"bookingdates\" : {\n" +
                "        \"checkin\" : \"2018-01-01\",\n" +
                "        \"checkout\" : \"2019-01-01\"\n" +
                "    },\n" +
                "    \"additionalneeds\" : \"Dinner\"\n" +
                "}";
    }

    public static String patchBody() {
        return "{\n" +
                "\n" +
                "    \"lastname\" : \"Petrovic\"\n" +
                "\n" +
                "}";
    }

    public static String token() {
        return "{\n" +
                "    \"username\" : \"admin\",\n" +
                "    \"password\" : \"password123\"\n" +
                "}";
    }

    public static String updateBody() {
        return "{\n" +
                "    \"firstname\" : \"Petar\",\n" +
                "    \"lastname\" : \"Kojotic\",\n" +
                "    \"totalprice\" : 88,\n" +
                "    \"depositpaid\" : true,\n" +
                "    \"bookingdates\" : {\n" +
                "        \"checkin\" : \"2018-01-01\",\n" +
                "        \"checkout\" : \"2019-01-01\"\n" +
                "    },\n" +
                "    \"additionalneeds\" : \"Brekkie\"\n" +
                "}";
    }
}
