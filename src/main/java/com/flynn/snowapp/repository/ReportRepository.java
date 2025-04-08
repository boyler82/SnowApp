package com.flynn.snowapp.repository;

import com.flynn.snowapp.model.Report;
import com.flynn.snowapp.model.ReportStatus;
import com.flynn.snowapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {

     List<Report> findByStatus(ReportStatus status);
     List<Report> findByCreatedBy(User user);
}