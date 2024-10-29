Annotations Used:

	@Entity: Marks the class as a JPA entity.
	@Table: Specifies the table name.
	@Id: Denotes the primary key.
	@GeneratedValue: Defines the generation strategy for the primary key.
	@Column: Maps the field to a database column.
	@ManyToOne: Establishes a many-to-one relationship with another entity.
	@JoinColumn: Specifies the foreign key column.

Relationships:

	The Role entity has a one-to-many relationship with Utilisateur.
	The Utilisateur entity has a many-to-one relationship with Role, Cours, and Inscription.
	The Cours entity has a many-to-one relationship with Utilisateur (as an instructor).
	The Inscription entity establishes relationships between Utilisateur and Cours.
	The Note entity establishes relationships between Utilisateur and Cours.

Data Types:

	Fields like date_naissance, date_debut, and date_fin are represented as Date objects in Java, with the @Temporal annotation to specify their date type.

• Languages : Java, HTML, CSS, JavaScript
• Framework: Spring Boot 
• ORM : Hibernate
• Base de Données : MySQL
• Server : Apache Tomcat v10.1

The database used is entitled gestion_scolarite and use the port 3306.
The username of the connection is root, and the password is cytech0001
