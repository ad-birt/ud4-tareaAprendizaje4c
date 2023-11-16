package ejercicios;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import entidades.Course;
import entidades.Student;
import entidades.Tuition;
import entidades.University;

public class Ud4TareaAprendizaje4cEjercicio1 {

	/**
	 * 1. ManyToMany bidireccional entre entidades Student y Course
	 * Crea un nuevo curso y a�ade un alumno al curso 
	 */
	public static void main(String[] args) {

		// crea sessionFactory y session
		StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
			    .configure( "hibernate.cfg.xml" )
			    .build();

		Metadata metadata = new MetadataSources( standardRegistry )
			    .addAnnotatedClass( Student.class )
			    .addAnnotatedClass( Tuition.class )
			    .addAnnotatedClass( University.class )
			    .addAnnotatedClass( Course.class )
			    .getMetadataBuilder()
			    .build();

		SessionFactory sessionFactory = metadata.getSessionFactoryBuilder()
			    .build();    
		
		Session session = sessionFactory.openSession();
		
		try {			
			// crea un objeto Student y Course
			System.out.println("Creando un nuevo curso y a�adiendo un alumno...");
			
			Student student = session.get(Student.class, 13);
			Course course = createCourse();
						
			student.getCourses().add(course);
			course.getStudents().add(student);//asociaci�n bidireccional para mantener la coherencia en ambos lados
			
											
			// comienza la transacci�n
			session.beginTransaction();
			
			// guarda el objeto Student y el curso
			System.out.println("Guardando el curso...");
						
			session.persist(course);
			
			// hace commit de la transaccion
			session.getTransaction().commit();	
			
			// Inicia una nueva transacci�n y recupera el curso de la base de datos para verificar los estudiantes asociados.
			// Esta parte est� comentada temporalmente para evitar operaciones adicionales de base de datos durante la demostraci�n.
			// Si necesitas verificar que la relaci�n ManyToMany se ha establecido correctamente, puedes descomentar estas l�neas.
			// session.beginTransaction();
			// Course dbCourse= (Course) session.get(Course.class, course.getId());
			// System.out.println(dbCourse.getStudents().iterator().next().getLastName());
			
			System.out.println("Hecho!");
		}
		catch ( Exception e ) {
			// rollback ante alguna excepci�n
			System.out.println("Realizando Rollback");
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		finally {
			session.close();
			sessionFactory.close();
		}
	}
	private static Course createCourse() {
		Course tempCourse = new Course();
				
		tempCourse.setName("Bases de datos");
		tempCourse.setCredits(6);
		return tempCourse;		
	}
}




