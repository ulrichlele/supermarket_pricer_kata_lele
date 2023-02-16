package io.lele.supermarket.pricer.application.port.out;


import java.util.Optional;
import java.util.Set;

public interface BaseCrudPort<D, ID>  {

    D create(D domain);

    D update(D domain);

    Optional<D> findById(ID reference);

    boolean recordExists(ID reference);

    Set<D> findAll();
}
