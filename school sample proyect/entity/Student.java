package entity;

import java.util.Set;

public class Student {

	private Long id;
	private Set<Subject> subjects;
	private String name;
	private String lastName;

	public Student() {

	}

	public Student(Long id, Set<Subject> subjects, String name, String lastName) {
		super();
		this.id = id;
		this.subjects = subjects;
		this.name = name;
		this.lastName = lastName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<Subject> getSubjects() {
		return subjects;
	}

	public void setSubjects(Set<Subject> subjects) {
		this.subjects = subjects;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}
