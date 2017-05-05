package ru.alex.st.hh.programm;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ru.alex.st.hh.config.ConfigurationBuilder;
import ru.alex.st.hh.config.SpyderConfiguration;
import ru.alex.st.hh.web.spyder.WebSpyder;

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
		SpyderConfiguration config = new ConfigurationBuilder()
						.setDepth(3)
						.setDiskStoragePath("D:/develop/Temp")
						.setStartUrl("https://ru.wikipedia.org/wiki")
						.setLocale("ru_RU")
						.build();
		
		WebSpyder spyder = new WebSpyder(config);
		spyder.loadPages();
		
		
		
		LOGGER.info("Finished");
	}

}
