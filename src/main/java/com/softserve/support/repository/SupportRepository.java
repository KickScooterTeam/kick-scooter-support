package com.softserve.support.repository;

import com.softserve.support.model.Support;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SupportRepository extends JpaRepository<Support, UUID> {
    List<Support> getByScooterId(UUID scooterId);
}
