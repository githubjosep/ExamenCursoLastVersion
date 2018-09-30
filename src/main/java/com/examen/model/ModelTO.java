package com.examen.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.examen.util.FileUtil;

public class ModelTO implements Comparable {

	Date date;
	Boolean isLastThursday;
	BigDecimal apertura;
	BigDecimal cierre;

	public void ModelTO() {

	}

	public ModelTO(Date date, BigDecimal papertura, BigDecimal pcierre) {
		Calendar cal = null;
		Date mydate = null;

		cal = Calendar.getInstance();
		cal.setTime(date);

		int pyear = cal.get(Calendar.YEAR);
		int pmonth = cal.get(Calendar.MONTH);
			
		mydate = FileUtil.getLastThursday(pyear, pmonth+1);

		this.date = date;
		this.apertura = papertura;
		this.cierre = pcierre;

		
		
		if (this.date.equals(mydate)) {
			this.isLastThursday = Boolean.TRUE;
		} else {
			this.isLastThursday = Boolean.FALSE;
		}

	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the apertura
	 */
	public BigDecimal getApertura() {
		return apertura;
	}

	/**
	 * @param apertura the apertura to set
	 */
	public void setApertura(BigDecimal apertura) {
		this.apertura = apertura;
	}

	/**
	 * @return the cierre
	 */
	public BigDecimal getCierre() {
		return cierre;
	}

	/**
	 * @param cierre the cierre to set
	 */
	public void setCierre(BigDecimal cierre) {
		this.cierre = cierre;
	}



	/**
	 * @return the isLastThursday
	 */
	public Boolean IsLastThursday() {
		return isLastThursday;
	}

	/**
	 * @param isLastThursday the isLastThursday to set
	 */
	public void setIsLastThursday(Boolean isLastThursday) {
		this.isLastThursday = isLastThursday;
	}

	@Override
	public int compareTo(Object obj) {
		Date myDate = ((ModelTO)obj).getDate();
		
		return this.date.compareTo(myDate);
	}


}
