package ru.alex.st.hh.programm;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Programm {

	private static final Logger LOGGER = LogManager.getLogger(Programm.class);


	/*
	 * Задание:
	 * 
	 * · Написать паука, который параллельно выкачивает все статьи википедии по
	 * ссылкам и сохраняет на диске.
	 * 
	 * · В параметре паука задаем входной url и глубину выкачивания.
	 * 
	 * · Плюс к этому сделать простой поиск по слову в скаченных статьях, без
	 * использования фреймворков полнотекстового поиска.
	 * 
	 * · Желательно решение близкое к production-quality: конфиги, логи, чтобы
	 * можно было анализировать ошибки в оффлайн и тп
	 */

	public static void main(String args[]) {
		String startUrlString = "https://ru.wikipedia.org/wiki";
		try {
			int i = 0;
			String s = null;
			URL startUrl = new URL(startUrlString);
			try (InputStream is = startUrl.openStream();
			                InputStreamReader isr = new InputStreamReader(is);
			                BufferedReader br = new BufferedReader(isr)) {
				while ((s = br.readLine()) != null) LOGGER.info(String.format("%s:%s", ++i, s));
			}
		} catch (Exception ex) {

		}

	}

}
