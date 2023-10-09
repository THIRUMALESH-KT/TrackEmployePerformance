package com.talent.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.talent.entity.MailHistory;

public interface MailHistoryRepository extends JpaRepository<MailHistory, Long> {

	MailHistory findByEmpMailAndSentDateAfterOrSentDateEquals(String empEmail, LocalDate firstDayOfCurrentMonth,
			LocalDate firstDayOfCurrentMonth2);

}
