package com.talent.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import com.talent.TalentTrackApplication;
import com.talent.UserRequest.EmployeeDataUserRequest;
import com.talent.UserRequest.EmployeeRatingRequest;
import com.talent.entity.EmployeeData;
import com.talent.entity.EmployeeProfile;
import com.talent.entity.MailHistory;
import com.talent.entity.Rating;
import com.talent.repository.EmployeeDataRepository;
import com.talent.repository.EmployeeProfileRepository;
import com.talent.repository.MailHistoryRepository;
import com.talent.repository.RatingRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;

@Service
@EnableScheduling
@Slf4j
public class EmployeeDataService {

	private final static String designation = "TRAINEE";
	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired
	private EmployeeDataRepository employeeDataRepository;
	@Autowired
	private EmployeeProfileRepository employeeProfileRepository;
	@Autowired
	private RatingRepository ratingRepository;
	@Autowired
	private TemplateEngine templateEngine;
	@Autowired
	private MailHistoryRepository mailHistoryRepository;

	private static final Logger logger = LoggerFactory.getLogger(TalentTrackApplication.class);
	// Fetching Less Performance Candidates

	public List<EmployeeData> getLessThanMinReaingEmployeeDataBasedOnDesig() {

		// Find out Before Month
		LocalDate currentDate = LocalDate.now();
		LocalDate startDate = currentDate.minusMonths(1).withDayOfMonth(1);
		System.out.println(startDate);
		List<EmployeeData> LessPerformanceCandidates = employeeDataRepository
				.findByEmpSkillsDesignationAndEmpRatingTechnicalLessThanAndEmpRatingStartDate(designation, 2.50,
						startDate);
		logger.info("Fetch deta", LessPerformanceCandidates);
		return LessPerformanceCandidates;
	}

	
	// Fetch All Trainees Data
	public Object getAllTraineesData() {
		List<EmployeeData> TraineeData = employeeDataRepository.findByEmpSkillsDesignation(designation);
		logger.info("Fetch deta", TraineeData);

		return TraineeData;
	}

	// Fetch All Employees Data
	public Object getAllEmployeesData() {
		List<EmployeeData> TraineeData = employeeDataRepository.findByEmpSkillsDesignation("EMPLOYEE");
		logger.info("Fetch deta", TraineeData);

		return TraineeData;
	}

	// Fetch Employee or Trainee Data By id
	public EmployeeData getEmployeeOrTraineeById(Long id) throws Exception {
		EmployeeData EmployeeData = employeeDataRepository.findById(id).orElseThrow(() -> new Exception());
		logger.info("Fetch deta", EmployeeData);

		return EmployeeData;
	}

	// Send Mail to Less Performance Trainees

	@Scheduled(cron = "0 0 7 1-10 * ?") // Scheduled Every Month 1st to 10th 7am
	// @Scheduled(fixedRate = 1000*60*2)
	public void sendMail() throws MessagingException {

		// Fetching Less performance Trainees Data
		List<EmployeeData> employeeDeta = getLessThanMinReaingEmployeeDataBasedOnDesig();
		if (employeeDeta != null) {
			for (EmployeeData employee : employeeDeta) {
				String Email = employee.getEmpEmail();

				// for Storing sentMail date
				LocalDate currentDate = LocalDate.now();
				LocalDate firstDayOfCurrentMonth = currentDate.withDayOfMonth(1);

				// for checking purpose create MailHistroy entity and set data with this month
				// date
				MailHistory historyy = mailHistoryRepository.findByEmpMailAndSentDateAfterOrSentDateEquals(
						employee.getEmpEmail(), firstDayOfCurrentMonth, firstDayOfCurrentMonth);
				// checking mail history this month mailsended ornot
				if (historyy == null) {
					org.thymeleaf.context.Context context = new org.thymeleaf.context.Context();
					context.setVariable("userName", employee.getEmpName());
					context.setVariable("Message", "Your monthly performance is very less please improve ");
					// Process the email template
					String emailContent = templateEngine.process("email-template.html", context);
					MimeMessage message = javaMailSender.createMimeMessage();
					MimeMessageHelper helper = new MimeMessageHelper(message, true);
					helper.setTo(employee.getEmpEmail());
					helper.setSubject("Monthly Performance Results");
					helper.setText(emailContent, true);

					javaMailSender.send(message);
					MailHistory history = new MailHistory();
					history.setEmpMail(Email);
					history.setSentDate(LocalDate.now());

					logger.info("Fetch deta", mailHistoryRepository.save(history));

				}
			}
		}
	}

	public EmployeeData inserEmployee(EmployeeDataUserRequest request) throws Exception {
		// TODO Auto-generated method stub
		log.info("inside inserEmployee EmployeeDataService");
		EmployeeData employeeData = new EmployeeData();
		employeeData.setEmpEmail(request.getEmpEmail());
		employeeData.setEmpName(request.getEmpName());

		EmployeeProfile empProfile = setSkills(employeeDataRepository.save(employeeData).getEmployeeId(),
				request.getEmpSkills());
		employeeData.setEmpSkills(empProfile);
		return employeeDataRepository.save(employeeData);

	}

	private EmployeeProfile setSkills(Long id, EmployeeProfile empSkills) throws Exception {
		EmployeeData emplData = getEmployeeOrTraineeById(id);
		empSkills.setEmployeeData(emplData);

		return employeeProfileRepository.save(empSkills);
	}

	public Rating addRating(EmployeeRatingRequest request, Long id) throws Exception {
		log.info("inside addRating EmployeeDataService");
		EmployeeData emplData = getEmployeeOrTraineeById(id);
		Rating rating = new Rating();
		rating.setCommunication(request.getCommunication());
		rating.setEmployeeData(emplData);
		rating.setEndDate(request.getEndDate());
		rating.setStartDate(request.getStartDate());
		rating.setTechnical(request.getTechnical());
		emplData.setEmpRating(null);
		return ratingRepository.save(rating);
	}

	public List<Rating> MonthlyData(LocalDate date) {
		log.info("inside MonthlyData EmployeeDataService");
		return ratingRepository.findByStartDate(date);

	}

}
