package micro_facturacion.facturacion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import micro_facturacion.facturacion.model.Factura;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> {
    
}
