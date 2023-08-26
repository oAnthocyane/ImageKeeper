package antne.imagekeeper.resourceserver.repository;

import antne.imagekeeper.resourceserver.model.Group;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * The interface Group repository.
 *
 * <p>
 * This repository works with the given group in database and interacts with it using methods such as:
 *     <ul>save</ul>
 *     <ul>findById</ul>
 * </p>
 */
public interface GroupRepository extends CrudRepository<Group, Long> {
    /**
     * Find by name optional.
     *
     * @param name the name
     * @return the optional
     */
    Optional<Group> findByName(String name);
}
