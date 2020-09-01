package persistence;

import java.util.List;
import java.util.Optional;

import entity.Student;
import entity.Subject;

public class StudentDAO {

	private List<Student> mockedStudents;
	private List<Subject> mockedSubjects;

	public StudentDAO(List<Student> mockedStudents, List<Subject> mockedSubjects) {
		this.setMockedStudents(mockedStudents);
		this.setMockedSubjects(mockedSubjects);
	}

	public List<Student> findAll() {
		// assuming this method performs a query to a database
		// to simplify, we return a mocked response
		return this.getMockedStudents();
	}

	public Optional<Student> findById(Long id) {
		// assuming this method performs a query to a database
		// to simplify, we return a mocked response
		return this.getMockedStudents().parallelStream().findFirst();
	}

	public Optional<Subject> findSubjectById(Long subjectId) {
		// assuming this method performs a query to a database
		// to simplify, we return a mocked response
		return this.mockedSubjects
		.parallelStream()
		.filter(s -> s.getId().equals(subjectId))
		.findFirst();
	}

	public void addSubjectToStudent(Student st, Long subId) {
		// assuming this method performs a query to a database
		// to simplify, we return a mocked response
		this.findSubjectById(subId)
		.ifPresent(subject -> 
			this.mockedStudents.parallelStream()
			.filter(s -> st.getId().equals(s.getId()))
			.findFirst()
			.ifPresent(s -> s.getSubjects().add(subject))
		);
	}

	public List<Student> getMockedStudents() {
		return mockedStudents;
	}

	public void setMockedStudents(List<Student> mockedStudents) {
		this.mockedStudents = mockedStudents;
	}

	public List<Subject> getMockedSubjects() {
		return mockedSubjects;
	}

	public void setMockedSubjects(List<Subject> mockedSubjects) {
		this.mockedSubjects = mockedSubjects;
	}

}
