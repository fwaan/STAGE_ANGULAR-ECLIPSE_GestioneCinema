package it.trefin.gestioneCinema.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import it.trefin.gestioneCinema.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Integer>{

	@Query(value = "SELECT COUNT() FROM ticket WHERE customer_id = :customer_id", nativeQuery = true)
	long countForCustomer(long id);
	
	//controllare la sequence
	@Query(value = "INSERT INTO ticket(hall, projection_time, customer_id, ticket_type, payment_method) VALUES(:hall, :projection_time, :customer_id, :ticket_type, :payment_method)", nativeQuery = true)
	void insert(String hall, LocalDateTime projection_time, long customer_id, String ticket_type, String payment_method);

	@Query(value = "SELECT t.seat, t.type, t.customer_id, t.payment_method, p.hall FROM ticket t JOIN projection p ON t.projection_id_sala = p.hall AND t.projection_id_timestamp = p.projection_time WHERE projection_time = :projection_time AND hall = :hall", nativeQuery = true)
	List<Ticket> getAllByProjection(String hall, LocalDateTime projection_time);
}
