package com.examen.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.examen.model.ModelTO;
import lombok.extern.log4j.Log4j;

import static java.time.DayOfWeek.THURSDAY;
import static java.time.temporal.TemporalAdjusters.lastInMonth;

@Log4j
public class FileUtil {

	public static synchronized ArrayList<ModelTO> devuelveRegistrosFichero(String sfile) throws IOException {
		BufferedReader br = null;
		ModelTO myObj = null;
		ArrayList<ModelTO> listaModel = new ArrayList<ModelTO>();
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
		
		log.info("Inicio Lectura Fichero ....");
		try {
			br = new BufferedReader(new FileReader(sfile));
			String line = br.readLine();
			while (null != line) {
				String[] datos = line.split(";", 3);

				if (!datos[0].equals("Fecha")) {
					
					Date mydaate = null;
					try {
						mydaate = formatter.parse(datos[0]);
					} catch (ParseException e) {
						log.error("Error al convertir la fecha: " + datos[0]);
					}
					
					myObj = new ModelTO(mydaate  ,new BigDecimal( datos[2] ) , new BigDecimal(datos[1]));

					listaModel.add(myObj);
				}	

				line = br.readLine();
			}

		} catch (IOException e) {
			log.error("Error al leer del fichero");
			throw e;

			
		} finally {
			log.info("Fin Lectura Fichero ....");
			if (null != br)
				br.close();
		}

		return listaModel;
	}
	public static Date getLastThursday(int year , int month) {
		int NumUltDia = 0;
		Calendar calendar = Calendar.getInstance();
		
		if (month == 1 || month == 3|| month == 5|| month == 7|| month == 8|| month == 10|| month == 12) {
			NumUltDia=31;
		} else {
			if (month == 2) {
				if ((year % 4 == 0) && ((year % 100 != 0) || (year % 400 == 0))) {
					NumUltDia=29;
				} else {
					NumUltDia=28;
				}
			} else {
				NumUltDia=31;	
				
			}
		}
		
		calendar.set(year, month-1, NumUltDia);
		int DiaSemana = 0;
		
		boolean prueba = true;
		while ( prueba ) {
			DiaSemana = calendar.get(Calendar.DAY_OF_WEEK);
			
			if (DiaSemana == 5) {// jueves
				prueba = false;
			   break;
		   } else {
			   calendar.add(Calendar.DAY_OF_WEEK, -1);
		   }
		}   
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);	
		
		return calendar.getTime();
	}
	
}
