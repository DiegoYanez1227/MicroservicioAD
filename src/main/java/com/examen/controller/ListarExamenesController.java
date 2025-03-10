package com.examen.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.examen.marzo2025.modelo.Examen;
import edu.examen.marzo2025.persistencia.ExamenesDAO;
import edu.examen.marzo2025.persistencia.ExamenesDAOImpl;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/examenes")
public class ListarExamenesController {
	
	ExamenesDAO modelo= new ExamenesDAOImpl();
	
	/**
	 * Devuelve el listado de exámenes que hay en la base de datos. 
	 * Lo que devuelve es el nombre de los exámenes
	 * La petición es: http://localhost:8081/examenes/listar
	 * @return
	 */
    @GetMapping("/listar")
    public List<String> listarExamenes() {
    	List<Examen> examenes = modelo.recuperarExamenes();
        List<String> listaString = new ArrayList<>();

        if (examenes != null) {
            for (Examen examen : examenes) {
            	listaString.add(String.valueOf(examen.getId()));
            }
        } else {
           System.out.println("Ha ocurrido un error");
        }
        
        return listaString;
    }
    
    /**
     * Devuelve el número de preguntas que hay en la base de datos para el examen solicitado
     * La petición es: http://localhost:8081/examenes/preguntas/{idExamen} donde {id_examen} es el número del examen por el que se pregunta.
     * Un ejemplo de petición, para el examen 3, sería: http://localhost:8081/examenes/preguntas/3
     * @param idExamen
     * @return
     */
    @GetMapping("/preguntas/{idExamen}")
    public int numeroPreguntas(@PathVariable("idExamen") int idExamen) {
    	
        return modelo.numeroPreguntasDelExamen(idExamen);
    }
    
    /**
     * Añade la pregunta indicada al examen indicado.
     * La petición es: http://localhost:8081/examenes/aniadir/{idExamen}/{idPregunta}  donde {idExamen} e {idPregunta} son los identificadores del examen y la pregunta respectivmente.
     * Un ejemplo de petición, para el examen 4 y la pregunta 8, sería: http://localhost:8081/examenes/aniadir/4/8
     * 
     * @param idExamen
     * @param idPregunta
     * @return
     */
    @GetMapping("/aniadir/{idExamen}/{idPregunta}")
    public String aniadirPregunta(@PathVariable("idExamen") int idExamen, @PathVariable("idPregunta") int idPregunta) {
        // Aquí va el código para guardar en la base de datos la pregunta en el examen.
    	if(modelo.aniadirPreguntaAExamen(idExamen, idPregunta)) {
    		return "Pregunta " + idPregunta + " añadida al examen " + idExamen;
        
    	}
        return "No se ha efectuado el añadido del la pregunta "+idPregunta+ " al examen "+idExamen;
    }
}
