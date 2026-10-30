package ub.accommodation.ports;

import java.util.Optional;
import ub.accommodation.domain.Application;

/**
 * <b>Driven port</b> (DP-01). Persistence contract for accommodation applications.
 * {@code AllocationService} loads the application and saves it back as approved when allocation succeeds.
 *
 * <p><b>Contract:</b> never returns {@code null}; participates in the caller's transaction.
 */
public interface ApplicationRepository {

    /** @return the application, or empty if the id is unknown */
    Optional<Application> findById(String applicationId);

    /** Insert or update the application and return the stored value. */
    Application save(Application application);
}
