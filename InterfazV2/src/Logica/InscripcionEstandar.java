package Logica;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="INSCRIPCION_ESTANDAR")
public class InscripcionEstandar extends Inscripcion implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int id;
	@OneToOne
	@JoinColumn(name="INSCRIPCIONES", referencedColumnName="id")
	Inscripcion codigo_inscripcion;
	public InscripcionEstandar() {
		//super(partido, jugador, inscripto);
		// TODO Auto-generated constructor stub
	}
	
}
