<h2 align="center">Developer Hiring Exam </h2>
<h4 align="center">Joaquin Rodriguez </h4>



<h3 align="center">Excercise 1 </h4>

<b>A)</b>
“Create the diagram that describes the domain model and relationships between entities.” 

Diagram file on “[school-diagram-domain-model.png](https://github.com/JRDG/tc-hiring-exam/blob/master/exam%20resources/Ex.1-school-diagrams/school-diagram-domain-model.png)”

![school-diagram-domain-model.png](https://github.com/JRDG/tc-hiring-exam/blob/master/exam%20resources/Ex.1-school-diagrams/school-diagram-domain-model.png?raw=true)

---

<b>B)</b> 
“Returns all the students grouped by the first letter of last name”

Method on file “[StudentService line 21](https://github.com/JRDG/tc-hiring-exam/blob/master/school%20sample%20proyect/service/StudentService.java#L21)”
```java
public Map<Character, List<Student>>getStudentsGrupedByLastName(){
	return this.students
	.findAll()
	.stream()
	.collect(Collectors.groupingBy(s -> s.getLastName().charAt(0), Collectors.toList()));
}
```

---

<b>C)</b> 
“Return  all the students taking specific subject”

Method on file “[StudentService line 28](https://github.com/JRDG/tc-hiring-exam/blob/master/school%20sample%20proyect/service/StudentService.java#L28)”
```java
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
```

Duplicates are checked at saving by method [“addSubjectToStudent” on file “StudentService”](https://github.com/JRDG/tc-hiring-exam/blob/master/school%20sample%20proyect/service/StudentService.java#L40)

```java
public void addSubjectToStudent(Long subjectId, Long studentId) {
	//Ideally, this should be a restriction in the database.
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
```

---

<b>D)</b> 
“Create two different database table structure diagrams”

<b>First diagram:</b> 

Diagram file on “[db-model-1.png](https://github.com/JRDG/tc-hiring-exam/blob/master/exam%20resources/Ex.1-school-diagrams/db-model-1.png)”

![db-model-1.png](https://github.com/JRDG/tc-hiring-exam/blob/master/exam%20resources/Ex.1-school-diagrams/db-model-1.png?raw=true)

Pros:
- Maintains the same structure than the class diagram, modifications are easier to map to the code
- Easier to understand and validate relations and restrictions
- In the future, modifications that involves differences between entities are easier to implement

Cons:
- Joins with multiple tables will degrade performance
- Insertions of rows involves multiples tables

<b> Second diagram:</b> 

Diagram file on “[db-model-2.png](https://github.com/JRDG/tc-hiring-exam/blob/master/exam%20resources/Ex.1-school-diagrams/db-model-2.png)”

![db-model-2.png](https://github.com/JRDG/tc-hiring-exam/blob/master/exam%20resources/Ex.1-school-diagrams/db-model-2.png?raw=true)

Pros:
- Query simplification: less joins means better performance and easier to write queries
- Inserting a new row involves less tables

Cons:
- It’s difficult to maintain restrictions and data consistency
- Extra steps may be needed in order to map data structure to project entity classes

---

<b>E)</b> 

The first change you can do to the query is remove the first join since the data needed is on the “person” table, then you can limit the fields fetched to first_name and last_name instead of all fields (*). If not all rows are needed at once, it’s always a good idea to do pagination using limit and offset clauses to fetch less rows.
Also you can add an index to the field used in the ‘where’ clause for better performance.

---

<b>F)</b> 

A solution for this scenario is the creation of a materialized view from that query to gain performance since it's not updated frequently, this is a common solution in scenarios like historic summaries or reports where info it’s not only updated with low frequency but it’s also not very important to have the latest information. Other solutions mentioned on exercise E are also valid (indexes since tables are not updated frequently, or paginated queries to return partial information).
Also, a non-sql related solution might be the usage of an external cache service (memcache, redis, etc.)

---

<b>G)</b> 

To calculate the age of student it can be useful to use an already created function in the sql engine, for example:
```sql
SELECT *
FROM student s 
WHERE EXTRACT ( year FROM age(current_date , s.date_of_birth) ) BETWEEN 19 AND 21;
```
One way to optimize the query would be to add an index on the date_of_birth field and calculate the max and min dates of birth in the moment of performing the query to avoid performing extra calculations on each student row. 

---

<b>H)</b> 
Business logic in the database is achieved with the use of stored procedures, triggers and integrity restrictions with the main advantage of gaining performance ,centralizing the core business logic to avoid replication/maintenance in multiple applications and provide security with sensible operations, at the cost of added complexity when you have requirements that can be difficult to solve with sql/plsql and in some case maintenance, testing or debugging can be frustrating.

---

---

<h3 align="center">Excercise 2</h4>

<b>A)</b>

Diagram file on “[booking-domain-model.png](https://github.com/JRDG/tc-hiring-exam/blob/master/exam%20resources/Ex.2-booking-diagrams/booking-domain-model.png)”

![booking-domain-model.png](https://github.com/JRDG/tc-hiring-exam/blob/master/exam%20resources/Ex.2-booking-diagrams/booking-domain-model.png)

---

<b>B)</b>

User interface for booking application:

[Login screen](https://github.com/JRDG/tc-hiring-exam/blob/master/exam%20resources/Ex.2-booking-user-interface/login.PNG)

![Login screen](https://github.com/JRDG/tc-hiring-exam/blob/master/exam%20resources/Ex.2-booking-user-interface/login.PNG?raw=true)

[Home view](https://github.com/JRDG/tc-hiring-exam/blob/master/exam%20resources/Ex.2-booking-user-interface/home.PNG)

![Home view](https://github.com/JRDG/tc-hiring-exam/blob/master/exam%20resources/Ex.2-booking-user-interface/home.PNG?raw=true)

[Welcome popup with indications](https://github.com/JRDG/tc-hiring-exam/blob/master/exam%20resources/Ex.2-booking-user-interface/welcome-pop-up.PNG)

![Welcome popup with indications](https://github.com/JRDG/tc-hiring-exam/blob/master/exam%20resources/Ex.2-booking-user-interface/welcome-pop-up.PNG?raw=true)

[Creating new reservation](https://github.com/JRDG/tc-hiring-exam/blob/master/exam%20resources/Ex.2-booking-user-interface/new-reservation-1.png)

![Creating new reservation](https://github.com/JRDG/tc-hiring-exam/blob/master/exam%20resources/Ex.2-booking-user-interface/new-reservation-1.png?raw=true)

[Selecting time](https://github.com/JRDG/tc-hiring-exam/blob/master/exam%20resources/Ex.2-booking-user-interface/new-reservation-2.png)

![Selecting time](https://github.com/JRDG/tc-hiring-exam/blob/master/exam%20resources/Ex.2-booking-user-interface/new-reservation-2.png?raw=true)

[Selecting room](https://github.com/JRDG/tc-hiring-exam/blob/master/exam%20resources/Ex.2-booking-user-interface/new-reservation-3.png)

![Selecting room](https://github.com/JRDG/tc-hiring-exam/blob/master/exam%20resources/Ex.2-booking-user-interface/new-reservation-3.png?raw=true)

---

<b>C)</b>

```java
public void verifyReservationBeforeSave(LocalTime start,LocalTime end,Long roomId) {
	//Time restriction check
	
	//Not less than 15 min
	//Not more than 180 min (3hs)
	long minutes = ChronoUnit.MINUTES.between(start,end);
	
	if(minutes < MIN_ALLOWED || minutes > MAX_ALLOWED)
		throw new InvalidDurationException();
	
	//check reservations that overlaps in time for the same room
	
	//query the database to get all reservations in a room
	reservations.findReservationsByRoomId(roomId)
	.stream()
	.filter(r -> {
		return r.getStart().isBefore(end) && r.getEnd().isAfter(start);
		//this can be avoided by adding the time conditions directly to the first query
		//and just checking if a matching record exists on database
	})
	.findFirst()
	.ifPresent(r -> {
		throw new OverlapException(" reservation id:"+r.getId()+" for room id:"+roomId);
	});
}
```
