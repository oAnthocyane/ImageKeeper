package antne.imagekeeper.resourceserver.repository;

import antne.imagekeeper.resourceserver.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * The interface User repository.
 * <p>
 * This repository works with the given user in database and interacts with it using methods such as:
 *    <ul>save</ul>
 *    <ul>findById</ul>
 *    <ul>findByUsername/ul>
 * </p>
 */
public interface UserRepository extends CrudRepository<User, Long> {

    /**
     * Find by user id optional.
     *
     * @param id the id
     * @return the optional
     */
    Optional<User> findByUserId(long id);

}
