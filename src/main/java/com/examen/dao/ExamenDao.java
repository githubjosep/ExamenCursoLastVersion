package com.examen.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.examen.model.ModelTO;
import com.examen.util.FileUtil;
import lombok.extern.log4j.Log4j;
   
@Log4j    
public class ExamenDao {
	/* static final Logger logger = lLogger.getLogger(StudentDao.class); */
	static Properties prop = null;

	static final Logger logger = Logger.getLogger(ExamenDao.class);
	// static FileInputStream input = null;
	static FileInputStream input = null;
	
	private BigDecimal retorno = new BigDecimal("0");
	private BigDecimal InvertidoMenosComision = null; 
	private BigDecimal valortTotal = new BigDecimal("0");
	
	static String sFilename;
	
	private int pThursday = 0;
	
	
	static {
		
		PropertyConfigurator.configure("log4j.properties");
		
		prop = new Properties();
		try {
			input = new FileInputStream("config.properties");
			// input = ExamenDao.class.getResourceAsStream("/config.properties");
			prop.load(input);
			sFilename = prop.getProperty("filename");
		} catch (IOException e) {
			/* logger.error(e.getMessage()); */
			throw new ExceptionInInitializerError(e);
		}
	}

	
	
	
	public BigDecimal calculaCapitalFinalObtenido() throws IOException {
		
		log.info("Inicio Calculo Capital final Obtenido....");	
		return calculaCapitarFinal(FileUtil.devuelveRegistrosFichero(sFilename));
	}

	private synchronized BigDecimal calculaCapitarFinal(ArrayList<ModelTO> pList) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");

		
		/****** ordenamos el Arranylist por fecha ****/
		
		
		log.info("Inicio Reordenamos el ArrayList por Fecha del Objeto ModelTO");
		
		Collections.sort(pList);
		
		log.info("Inicio Reordenamos el ArrayList por Fecha del Objeto ModelTO");
		
		pThursday = 0;

		try {
			Date mydate = formatter.parse("28-dic-2017");
			
			pList.forEach(obj -> {
				if (obj.getDate().before(mydate)) {
						
					if(obj.IsLastThursday() == Boolean.TRUE) {
						pThursday = 1;
					} else {
						if (pThursday == 1) {
							InvertidoMenosComision =new BigDecimal("49"); 
							InvertidoMenosComision = InvertidoMenosComision.setScale(3, RoundingMode.HALF_UP);
							
							// Calculamos el total de acciones. (50 Euros - 2%) / Precio Apertura
							InvertidoMenosComision = InvertidoMenosComision.divide(obj.getApertura(),  RoundingMode.HALF_UP);	
							retorno = retorno.add(InvertidoMenosComision);
							
							pThursday = 0;
							//System.out.println("Día siguiente al último Jueves de mes. Día: "  + obj.getDate() +". Valor Apertura: " + obj.getApertura() + ". Acciones:" + InvertidoMenosComision + " Acciones Acum: " + retorno );
							log.info("Día siguiente al último Jueves de mes. Día: "  + obj.getDate() +". Valor Apertura: " + obj.getApertura() + ". Acciones:" + InvertidoMenosComision + " Acciones Acum: " + retorno );
						}
					}
				} else {
					valortTotal = retorno.multiply(obj.getCierre());
					valortTotal = valortTotal.setScale(2, RoundingMode.HALF_UP);
					
					//System.out.println(" ");
					//System.out.println("El valor total a dia : " + obj.getDate().toString() + " es: Acciones: " + retorno + " y valor a cierre: " + obj.getCierre() + " Total: " + valortTotal);
					
					log.info(" ");
					log.info("El valor total a dia : " + obj.getDate().toString() + " es: Acciones: " + retorno + " y valor a cierre: " + obj.getCierre() + " Total: " + valortTotal);	
				}

			});
			
		} catch (ParseException e1) {
			//System.out.println("Error en el parser de la fecha");
			log.error("Error en el parser de la fecha");
		}
		
		log.info(" ");
		log.info("Final Calculo Capital final Obtenido....");
		
		return valortTotal;
	}

}
