package com.salesmanager.core.business.repositories.system;

import com.salesmanager.core.model.system.SystemTimerTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SystemTimerTaskRepository extends JpaRepository<SystemTimerTask, Integer> {
    List<SystemTimerTask> findAllByAvailableIsTrue();
}
