package be.vinci.pae.ihm.filters;

import jakarta.ws.rs.NameBinding;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *This class mark resources or methods that require authorization.
 */
@NameBinding
@Retention(RetentionPolicy.RUNTIME)
public @interface Authorize {
}