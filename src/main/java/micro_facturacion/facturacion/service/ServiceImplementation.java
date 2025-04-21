package micro_facturacion.facturacion.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import micro_facturacion.facturacion.exeption.FacturaNotFound;
import micro_facturacion.facturacion.model.Factura;
import micro_facturacion.facturacion.repository.FacturaRepository;

@Service
public class ServiceImplementation implements FacturaService {
    @Autowired 
    private FacturaRepository facturaRepository;

    @Override
    public Factura createFactura(Factura CreateFactura) {
        return facturaRepository.save(CreateFactura);
    }

    @Override
    public List<Factura> buscarFactura() {
        return facturaRepository.findAll();
    }

    @Override
    public Optional<Factura> buscarId(Long id) {
        return facturaRepository.findById(id);
    }

    @Override
    public void eliminarFactura(Long id) {
        facturaRepository.deleteById(id);
    }

    @Override
    public Factura updateFactura(Long id, Factura updateFactura) {
        if(facturaRepository.existsById(id)) {
            updateFactura.setId(id);
            return facturaRepository.save(updateFactura);
        } else {return null;}
    }
}
