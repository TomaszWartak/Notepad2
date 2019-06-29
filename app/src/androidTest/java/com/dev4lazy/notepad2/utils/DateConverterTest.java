package com.dev4lazy.notepad2.utils;

import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.util.Date;

import static org.junit.Assert.*;

public class DateConverterTest {
    DateConverter dateConverter = null;

    @Before
    public void Init() {
        dateConverter = new DateConverter();
    }

    @Test
    public void long2Date() {
        Date date = null;
        try {
            date = dateConverter.string2Date("2019-06-13");
        } catch (ParseException e){
            e.printStackTrace();
        }
        assertEquals(date, dateConverter.long2Date(1560376800000L));
    }

    @Test
    public void date2Long() {
        Date date = null;
        try {
            date = dateConverter.string2Date("2019-06-13");
        } catch (ParseException e){
            e.printStackTrace();
        }
        assertEquals(new Long(1560376800000L), dateConverter.date2Long(date));
    }

    @Test
    public void date2String() {
        Date date = dateConverter.long2Date(1560376800000L);
        assertEquals("2019-06-13", dateConverter.date2String(date));
    }

    @Test
    public void string2Date() {
        Date date1 = dateConverter.long2Date(1560376800000L);
        Date date2 = null;
        try {
            date2 = dateConverter.string2Date("2019-06-13");
        } catch (ParseException e){
            e.printStackTrace();
        }
        assertEquals(date1, date2);
    }
}