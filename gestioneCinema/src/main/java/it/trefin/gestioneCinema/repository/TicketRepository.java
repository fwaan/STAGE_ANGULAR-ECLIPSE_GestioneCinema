package it.trefin.gestioneCinema.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import it.trefin.gestioneCinema.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Integer>{

	@Query(value = "SELECT COUNT(*) FROM ticket WHERE customer_id = :customer_id", nativeQuery = true)
	long countForCustomer(@Param("customer_id") long customer_id);

	@Query(value = "SELECT * FROM ticket WHERE projection_id_sala = :hall AND projection_id_timestamp = :projection_time", nativeQuery = true)
	List<Ticket> getAllByProjection(@Param("hall") String hall, @Param("projection_time") LocalDateTime projection_time);
}
