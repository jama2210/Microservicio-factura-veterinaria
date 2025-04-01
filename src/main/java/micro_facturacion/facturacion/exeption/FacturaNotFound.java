package micro_facturacion.facturacion.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FacturaNotFound extends RuntimeException {
    public FacturaNotFound(Long id) {
        super("La factura N° " + id + " no fue encontrada");
    }
}
