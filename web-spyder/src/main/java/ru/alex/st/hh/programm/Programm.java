package ru.alex.st.hh.programm;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ru.alex.st.hh.config.ConfigurationBuilder;
import ru.alex.st.hh.config.MessageSource;
import ru.alex.st.hh.config.SpyderConfiguration;
import ru.alex.st.hh.disk.DiskPageWriter;

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
//		String startUrlString = "https://ru.wikipedia.org/wiki";
//		try {
//			int i = 0;
//			String s = null;
//			URL startUrl = new URL(startUrlString);
//			try (InputStream is = startUrl.openStream();
//			                InputStreamReader isr = new InputStreamReader(is);
//			                BufferedReader br = new BufferedReader(isr)) {
//				while ((s = br.readLine()) != null) LOGGER.info("{}: {}", ++i, s));
//			}
//		} catch (Exception ex) {
//
//		}
		
		SpyderConfiguration config = new ConfigurationBuilder()
						.setDepth(3)
						.setDiskStoragePath("D:/develop/Temp")
						.setStartUrl("https://ru.wikipedia.org/wiki")
						.setLocale("ru_RU")
						.build();
		
		DiskPageWriter writer = new DiskPageWriter(config);
		writer.writePage(config.getStartUrl(), "1.txt");
		

	}

}
