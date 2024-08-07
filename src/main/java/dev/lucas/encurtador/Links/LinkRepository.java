package dev.lucas.encurtador.Links;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LinkRepository extends JpaRepository<Link, UUID> {
    Link findByUrlNova(String urlNova );
}
