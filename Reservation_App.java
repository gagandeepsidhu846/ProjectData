import io.muserver.*;
import io.muserver.rest.*;
import com.google.gson.Gson;
import java.util.*;
import static io.muserver.MuServerBuilder.muServer;

public class SimpleReservationApp {
    private static final List<Booking> bookings = Collections.synchronizedList(new ArrayList<>());
    private static final Gson gson = new Gson();

    public static void main(String[] args) {
        MuServer server = muServer()
            .withHttpPort(8080)
            .addHandler(Method.POST, "/bookings", (request, response, pathParams) -> {
                Booking booking = gson.fromJson(request.readBodyAsString(), Booking.class);
                bookings.add(booking);
                response.write("Booking added successfully");
            })
            .addHandler(Method.GET, "/bookings", (request, response, pathParams) -> {
                String date = request.query().get("date");
                if (date == null) {
                    response.status(400).write("Missing 'date' parameter");
                    return;
                }
                List<Booking> result = new ArrayList<>();
                synchronized (bookings) {
                    for (Booking b : bookings) {
                        if (b.getDate().equals(date)) {
                            result.add(b);
                        }
                    }
                }
                response.contentType("application/json").write(gson.toJson(result));
            })
            .start();

        System.out.println("Server started at " + server.uri());
    }

    static class Booking {
        private String customerName;
        private int tableSize;
        private String date;
        private String time;

        public Booking() {}

        public String getCustomerName() { return customerName; }
        public void setCustomerName(String customerName) { this.customerName = customerName; }

        public int getTableSize() { return tableSize; }
        public void setTableSize(int tableSize) { this.tableSize = tableSize; }

        public String getDate() { return date; }
        public void setDate(String date) { this.date = date; }

        public String getTime() { return time; }
        public void setTime(String time) { this.time = time; }
    }
}
