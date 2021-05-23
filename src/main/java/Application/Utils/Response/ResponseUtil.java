package Application.Utils.Response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {

    public static ResponseEntity<String> notFoundId(String entity) {
        return new ResponseEntity<>(String.format("%s with this id not found", entity), HttpStatus.NOT_FOUND);
    }
}
