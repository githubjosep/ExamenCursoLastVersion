package com.examen.principal;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;

import com.examen.dao.ExamenDao;
import com.examen.model.ModelTO;

public class Main {
	public static void main(String[] args) {
		ExamenDao mObj = new ExamenDao();

		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");

		BigDecimal result = new BigDecimal("0");
		result = result.setScale(2, RoundingMode.HALF_UP);


		try {
			result = mObj.calculaCapitalFinalObtenido();
			result = result.setScale(2, RoundingMode.HALF_UP);
			//System.out.println("El valor final es: " + result);
			
		} catch (IOException e) {
			System.out.println(e);
		}

	}
}
