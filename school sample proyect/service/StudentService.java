package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import entity.Student;
import error.UserAlreadyWithSubjectException;
import persistence.StudentDAO;

public class StudentService {

	private StudentDAO students;

	public StudentService(StudentDAO students) {
		this.students = students;
	}

	public Map<Character, List<Student>>getStudentsGrupedByLastName(){
		return this.students
		.findAll()
		.stream()
		.collect(Collectors.groupingBy(s -> s.getLastName().charAt(0), Collectors.toList()));
	}
	
	public List<Student> getStudentsInSubject(Long subjectId) {
		return this.students
		.findSubjectById(subjectId)
		.map( subj -> 
			this.students
			.findAll()
			.stream()
			.filter( s -> s.getSubjects().contains(subj))
			.collect(Collectors.toList())
		).orElse(new ArrayList<Student>());
	}
	
	public void addSubjectToStudent(Long subjectId, Long studentId) {
		//Ideally, this should be a restriction in the database.
		//In order to accomplish exercise C) verification
		//we check in code before saving that users can't be associated multiple times with the same subject
		Optional<Student> student = this.students.findById(studentId);
		
		student.map(s -> s.getSubjects()
				.stream()
				.filter(sub -> sub.getId().equals(subjectId))
				.findFirst().get())
		.ifPresent(s2 -> {
			throw new UserAlreadyWithSubjectException(s2.getName() + "-" + subjectId);
		});
		
		
		this.students.addSubjectToStudent(student.get(), subjectId);
	}
	
	public StudentDAO getStudents() {
		return students;
	}

	public void setStudents(StudentDAO students) {
		this.students = students;
	}

}
